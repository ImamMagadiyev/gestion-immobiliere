package Controleur.Modifier;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import Controleur.Affichage.GestionAfficherCaution;
import Modele.Caution;
import Modele.dao.DaoCaution;
import Vue.Modification.FenetreModifierCaution;

public class GestionModifierCaution implements ActionListener {

    private FenetreModifierCaution vue;
    private Caution caution;
    private GestionAfficherCaution gestionAfficher;

    public GestionModifierCaution(FenetreModifierCaution vue, Caution caution, GestionAfficherCaution gestionAfficher) {
        this.vue = vue;
        this.caution = caution;
        this.gestionAfficher = gestionAfficher;

        vue.getBtnEnregistrer().addActionListener(this);
        vue.getBtnAnnuler().addActionListener(e -> {
            System.out.println("[DEBUG] Bouton 'Annuler' cliqué, fermeture de la fenêtre");
            vue.dispose();
        });

        System.out.println("[DEBUG] GestionModifierCaution initialisé pour la caution : " + caution.getIdContrat());
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Component src = (Component) e.getSource();
        String name = src.getName();
        if (name == null) return;

        if ("btnModifierCaution".equals(name)) {
            System.out.println("[DEBUG] Bouton 'Modifier caution' cliqué");
            modifierCaution();
        }
    }

    private void modifierCaution() {

        caution.setNom(vue.getNom());
        caution.setPrenom(vue.getPrenom());
        caution.setAdresse(vue.getAdresse());
        caution.setVille(vue.getVille());
        caution.setCode_postale(vue.getCodePostale());
        caution.setProfession(vue.getProfession());
        caution.setType_contrat_travail(vue.getTypeContrat());
        caution.setDate_naissance(vue.getDateNaissance());
        caution.setAdresseElectronique(vue.getMail());
        caution.setNumeroTel(vue.getTelephone());
        caution.setQualiteBailleur(vue.getQualite());
        caution.setIdContrat(vue.getIdContrat());

        try {
            DaoCaution dao = new DaoCaution();
            dao.update(caution);

            System.out.println("[DEBUG] Caution mise à jour dans la base : " + caution.getIdContrat());
            vue.afficherSucces("Caution modifiée.");
            gestionAfficher.chargerCautions();
        } catch (SQLException ex) {
            vue.afficherErreur("Erreur lors de la modification : " + ex.getMessage());
            System.out.println("[DEBUG] Erreur SQL lors de la modification : " + ex.getMessage());
        }

        // Fermer la fenêtre dans tous les cas
        vue.dispose();
    }
}
