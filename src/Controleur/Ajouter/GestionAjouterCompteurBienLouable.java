package Controleur.Ajouter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import Modele.Compteur;
import Modele.dao.DaoCompteur;
import Vue.ajouter.FenetreAjouterCompteurBienLouable;

public class GestionAjouterCompteurBienLouable implements ActionListener {

    private FenetreAjouterCompteurBienLouable vue;

    public GestionAjouterCompteurBienLouable(FenetreAjouterCompteurBienLouable vue) {
        this.vue = vue;
        vue.getBtnAjouter().addActionListener(this);
        vue.getBtnAnnuler().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        
        if (btn == vue.getBtnAjouter()) {
            ajouterCompteurBienLouable();
        } else if (btn == vue.getBtnAnnuler()) {

            vue.dispose();

            SwingUtilities.invokeLater(() -> {
                vue.dispose();
            });

        }
    }

    private void ajouterCompteurBienLouable() {
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
        		vue.getId(),
        		null,
        		false
        		);

        try {
            DaoCompteur dao = new DaoCompteur();
            System.out.println("DEBUG : Tentative d'insertion du compteur -> " + comp);
            dao.create(comp);
            vue.afficherSucces("Compteur bien louable ajouté !\n" + comp.toString());

            vue.dispose();
        } catch (Exception ex) {
            vue.afficherErreur("Erreur lors de l'ajout en BD : " + ex.getMessage());
            ex.printStackTrace();
        }
    }        
}
