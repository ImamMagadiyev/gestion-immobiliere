package Controleur.Ajouter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

import Modele.Compteur;
import Modele.dao.DaoCompteur;
import Vue.ajouter.FenetreAjouterCompteurBatiment;

public class GestionAjouterCompteurBatiment implements ActionListener {

    private FenetreAjouterCompteurBatiment vue;

    public GestionAjouterCompteurBatiment(FenetreAjouterCompteurBatiment vue) {
        this.vue = vue;
        vue.getBtnAjouter().addActionListener(this);
        vue.getBtnAnnuler().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        switch (btn.getText()) {
            case "Ajouter":
                ajouterCompteurBatiment();
                break;
            case "Annuler":
                vue.dispose();
                break;
        }
    }

    private void ajouterCompteurBatiment() {
        if (vue.getChampId().getText().isEmpty() ||
            vue.getChampIndex().getText().isEmpty() || vue.getComboVariable().getSelectedIndex() < 0) {
            vue.afficherErreur("Tous les champs doivent être remplis.");
            return;
        }
        
        double index;
        try {
            index = Double.parseDouble(vue.getIndex());
        } catch (NumberFormatException e) {
            vue.afficherErreur("Index invalide.");
            return;
        }
        
        Compteur comp = new Compteur(
        		vue.getIdCompteur(),
        		vue.getTypeCompteur(),
        		index,
        		vue.getIdVariable(),
        		null,
        		vue.getId(),
        		false
        		);

        try {
            DaoCompteur dao = new DaoCompteur();
            System.out.println("DEBUG : Tentative d'insertion du compteur -> " + comp);

            dao.create(comp);

            System.out.println("DEBUG : Compteur inséré avec succès");


            vue.afficherSucces("Compteur bien louable ajouté !\n" + comp.toString());
            vue.dispose();
        } catch (Exception ex) {
            vue.afficherErreur("Erreur lors de l'ajout en BD : " + ex.getMessage());
            ex.printStackTrace();
        }
    }      
}
