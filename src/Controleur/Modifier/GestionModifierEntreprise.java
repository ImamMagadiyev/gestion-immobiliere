package Controleur.Modifier;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Modele.Entreprise;
import Modele.dao.DaoEntreprise;
import Vue.Modification.FenetreModifierEntreprise;

public class GestionModifierEntreprise implements ActionListener {

    private FenetreModifierEntreprise vue;
    private Entreprise entreprise;

    public GestionModifierEntreprise(FenetreModifierEntreprise vue) {
        this.vue = vue;
        this.entreprise = vue.getEntreprise();

        System.out.println("[DEBUG] Initialisation GestionModifierEntreprise pour l'entreprise : " + entreprise.getNom());

        vue.getBtnModifier().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = ((java.awt.Component)e.getSource()).getName();
        if (name == null) return;

        if (name.equals("btnModifierEntreprise")) {
            System.out.println("[DEBUG] Bouton 'Modifier Entreprise' cliqué pour : " + entreprise.getNom());
            modifierEntreprise();
        }
    }

    private void modifierEntreprise() {
        try {
            entreprise.setNom(vue.getChampNom().getText().trim());
            entreprise.setVille(vue.getChampVille().getText().trim());
            entreprise.setMail(vue.getChampMail().getText().trim());
            entreprise.setAdresse(vue.getChampAdresse().getText().trim());
            entreprise.setSpecialite(vue.getChampSpecialite().getText().trim());
            entreprise.setCode_postale(vue.getChampCodePostale().getText().trim());

            DaoEntreprise dao = new DaoEntreprise();
            dao.update(entreprise);

            System.out.println("[DEBUG] Entreprise modifiée avec succès : " + entreprise.getNom());

            if (vue.getGfp() != null)
                vue.getGfp().afficherTableauEntreprise();

            vue.afficherSucces("Entreprise modifiée avec succès !");

        } catch (Exception ex) {
            vue.afficherErreur("Erreur lors de la modification : " + ex.getMessage());
            System.out.println("[DEBUG] Erreur modification entreprise : " + entreprise.getNom() + " => " + ex.getMessage());
            ex.printStackTrace();
        }

        // Fermer la fenêtre dans tous les cas
        vue.dispose();
    }
}
