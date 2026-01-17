package Controleur.Modifier;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.SwingUtilities;

import Modele.Batiment;
import Modele.Compteur;
import Modele.dao.DaoBatiment;
import Modele.dao.DaoCompteur;
import Vue.Utils;
import Vue.Modification.FenetreModifierBatiment;
import Vue.Modification.FenetreModifierCompteurBatimentTableau;
import Vue.ajouter.FenetreAjouterCompteurBatiment;

public class GestionModifierBatiment implements ActionListener {

    private FenetreModifierBatiment vue;
    private Batiment batiment;

    public GestionModifierBatiment(FenetreModifierBatiment vue, Batiment batiment) {
        this.vue = vue;
        this.batiment = batiment;

        vue.getBtnModifier().addActionListener(this);
        vue.getBtnAnnuler().addActionListener(this);
        vue.getBtnModifierCompteur().addActionListener(this);
        vue.getBtnAjouterCompteur().addActionListener(this);
        
        System.out.println("[DEBUG] Initialisation de GestionModifierBatiment pour le bâtiment : " + batiment.getIdBatiment());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Component src = (Component) e.getSource();
        String name = src.getName();
        if (name == null) return;

        switch (name) {
        case "btnModifierBatiment":
            System.out.println("[DEBUG] Bouton 'Modifier bâtiment' cliqué");
            modifierBatiment();
            break;
        case "btnAjouterCompteur":
            System.out.println("[DEBUG] Bouton 'Ajouter compteur' cliqué");
            Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(vue);
            FenetreAjouterCompteurBatiment facb =
                    new FenetreAjouterCompteurBatiment(parentFrame, batiment);
            facb.setVisible(true);
            break;

        case "btnModifierCompteur":
            System.out.println("[DEBUG] Bouton 'Modifier compteur' cliqué");
            try {
                modifierCompteur();
            } catch (SQLException e1) {
                e1.printStackTrace();
                vue.afficherErreur("Erreur lors de la modification du compteur.");
            }
            break;

        case "btnAnnulerBatiment":
            System.out.println("[DEBUG] Bouton 'Annuler' cliqué, fermeture de la fenêtre");
            vue.dispose();
            break;
        }
    }

    private void modifierBatiment() {
        try {
            batiment.setAdresse(vue.getAdresse());
            batiment.setVille(vue.getVille());
            batiment.setCodePostale(vue.getCodePostal());
            
            LocalDate periodeConstruction;
            try {
                periodeConstruction = Utils.parseLocalDate(vue.getPeriodeDeConstruction());
            } catch (Exception e) {
                vue.afficherErreur("Format date invalide (AAAA-MM-JJ attendu)");
                return;
            }
            
            batiment.setPeriodeDeConstruction(periodeConstruction);

            DaoBatiment daoBat = new DaoBatiment();
            daoBat.update(batiment);

            System.out.println("[DEBUG] Bâtiment mis à jour dans la base : " + batiment.getIdBatiment());

            vue.getGfp().afficherTableauBatiment();
            vue.afficherSucces("Bâtiment modifié.");
            
        } catch (SQLException ex) {
            vue.afficherErreur("Erreur lors de la modification : " + ex.getMessage());
        }
        
        vue.dispose();
    }

    private void modifierCompteur() throws SQLException {
        DaoCompteur daoC = new DaoCompteur();
        Compteur compteur = daoC.findByBatiment(batiment.getIdBatiment());

        if (compteur == null) {
            vue.afficherErreur("Aucun compteur trouvé.");
            return;
        }

        FenetreModifierCompteurBatimentTableau fmcbt =
        	    new FenetreModifierCompteurBatimentTableau(
        	        batiment,
        	        vue.getDesktop()
        	    );

        System.out.println("[DEBUG] Fenêtre modification compteur ouverte pour le compteur : " + compteur.getId_compteur());
        vue.getDesktop().add(fmcbt);
        fmcbt.setVisible(true);
    }
}
