package Controleur.Ajouter;

import java.awt.Frame;
import java.time.LocalDate;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import Modele.Batiment;
import Modele.dao.DaoBatiment;
import Vue.ajouter.FenetreAjouterBatiment;
import Vue.ajouter.FenetreAjouterCompteurBatiment;

public class GestionAjouterBatiment extends GestionControleurAjoutBase<FenetreAjouterBatiment> {

    public GestionAjouterBatiment(FenetreAjouterBatiment vue) {
        super(vue);
    }

    @Override
    protected void enregistrerBoutons() {
        vue.getBtnAjouter().addActionListener(this);
        vue.getBtnAnnuler().addActionListener(this);
        vue.getBtnAjouterCompteur().addActionListener(this);
    }

    @Override
    protected void traiterAction(String nomBouton) {
        switch (nomBouton) {
            case "btnAjouterBatiment":
                ajouterBatiment();
                break;
            case "btnAjouterCompteur":
                ouvrirAjoutCompteur();
                break;
        }
    }

    private void ajouterBatiment() {
        if (champsSontVides(vue.getId(), vue.getAdresse(), vue.getCodePostal(), 
                           vue.getVille(), vue.getPeriodeDeConstruction())) {
            afficherErreurChampsObligatoires();
            return;
        }

        LocalDate dateConstruction;
        try {
            dateConstruction = LocalDate.parse(vue.getPeriodeDeConstruction());
        } catch (Exception e) {
            vue.afficherErreur("Format date invalide (AAAA-MM-JJ attendu)");
            return;
        }

        Batiment bat = new Batiment(
            vue.getId(),
            vue.getAdresse(),
            vue.getVille(),
            vue.getCodePostal(),
            dateConstruction
        );

        try {
            DaoBatiment daoBat = new DaoBatiment();
            daoBat.create(bat);

            if (vue.getGfp() != null)
                vue.getGfp().afficherTableauBatiment();

            vue.afficherSucces("Bâtiment ajouté !\n" + bat);
            vue.getBtnAjouterCompteur().setEnabled(true);

        } catch (Exception ex) {
            vue.afficherErreur("Erreur BD : " + ex.getMessage());
        }
    }

    private void ouvrirAjoutCompteur() {
        try {
            DaoBatiment daoBat = new DaoBatiment();
            Batiment bat = daoBat.findById(vue.getId());

            if (bat == null) {
                vue.afficherErreur("Veuillez d'abord enregistrer le bâtiment.");
                return;
            }

            Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(vue);
            FenetreAjouterCompteurBatiment facb =
                new FenetreAjouterCompteurBatiment(parentFrame, bat);

            facb.setVisible(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
