package Controleur.Modifier;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Modele.ContratDeLocation;
import Modele.dao.DaoContratDeLocation;
import Vue.Utils;
import Vue.Modification.FenetreModifierContratDeLocation;

public class GestionModifierContratDeLocation implements ActionListener {

    private FenetreModifierContratDeLocation vue;
    private ContratDeLocation contrat;

    public GestionModifierContratDeLocation(FenetreModifierContratDeLocation vue, ContratDeLocation contrat) {
        this.vue = vue;
        this.contrat = contrat;
        vue.getBtnAjouter().addActionListener(this);
        vue.getBtnAnnuler().addActionListener(this);
        
        vue.griserComboBox();

        System.out.println("[DEBUG] Initialisation GestionModifierContratDeLocation pour le contrat : " + contrat.getIdContrat());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Component src = (Component) e.getSource();
        if ("btnAjouter".equals(src.getName())) {
            System.out.println("[DEBUG] Bouton 'Ajouter/Modifier' cliqué pour le contrat : " + contrat.getIdContrat());
            modifier();
        } else if ("btnAnnuler".equals(src.getName())) {
            System.out.println("[DEBUG] Bouton 'Annuler' cliqué, fermeture de la fenêtre");
            vue.dispose();
        }
    }

    private void modifier() {
        try {
            // Validation des dates
            String dateDebut = vue.getChampDateDebut().getText().trim();
            String dateSortie = vue.getChampDateSortie().getText().trim();
            
            try {
                Utils.parseLocalDate(dateDebut);
            } catch (Exception e) {
                vue.afficherErreur("Format date de début invalide (AAAA-MM-JJ attendu)");
                return;
            }
            
            if (!dateSortie.isEmpty()) {
                try {
                    Utils.parseLocalDate(dateSortie);
                } catch (Exception e) {
                    vue.afficherErreur("Format date de sortie invalide (AAAA-MM-JJ attendu)");
                    return;
                }
            }

            // Debug important : avant mise à jour
            System.out.println("[DEBUG] Modification des champs du contrat : " + contrat.getIdContrat());

            // Mettre à jour seulement les champs modifiables
            contrat.setDate_debut(dateDebut);
            contrat.setTrimestre((String) vue.getChampTrimestre().getSelectedItem());
            contrat.setDate_sortie(dateSortie);
            contrat.setLoyer_aPayer(Double.parseDouble(vue.getChampLoyer().getText().trim()));
            contrat.setIRL(Double.parseDouble(vue.getChampIRL().getText().trim()));
            contrat.setProvisions_charges(Double.parseDouble(vue.getChampProvisions().getText().trim()));
            contrat.setDuree(Integer.parseInt(vue.getChampDuree().getText().trim()));
            contrat.setSolde_tout_compte_effectue(vue.getChkSolde().isSelected());

            DaoContratDeLocation dao = new DaoContratDeLocation();
            dao.update(contrat);

            // Debug important : succès de modification
            System.out.println("[DEBUG] Contrat modifié avec succès : " + contrat.getIdContrat());

            // Rafraîchissement du tableau
            if (vue.getGfp() != null) {
                vue.getGfp().afficherTableauBaux();
            }

            vue.afficherSucces("Contrat modifié avec succès !");

        } catch (NumberFormatException ex) {
            vue.afficherErreur("Erreur : les champs numériques sont incorrects (loyer, IRL, provisions, durée).");
            System.out.println("[DEBUG] NumberFormatException lors de la modification du contrat : " + contrat.getIdContrat());
        } catch (Exception ex) {
            vue.afficherErreur("Erreur modification : " + ex.getMessage());
            System.out.println("[DEBUG] Exception lors de la modification du contrat : " + contrat.getIdContrat() + " => " + ex.getMessage());
            ex.printStackTrace();
        }
        
        // Fermer la fenêtre dans tous les cas
        vue.dispose();
    }
}
