package Controleur.Ajouter;

import java.awt.Frame;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import Modele.Batiment;
import Modele.BienLouable;
import Modele.dao.DaoBatiment;
import Modele.dao.DaoBienLouable;
import Vue.ajouter.FenetreAjouterBienLouable;
import Vue.ajouter.FenetreAjouterCompteurBienLouable;

public class GestionAjouterBienLouable extends GestionControleurAjoutBase<FenetreAjouterBienLouable> {

    private List<Batiment> listeBatiments;

    public GestionAjouterBienLouable(FenetreAjouterBienLouable vue) {
        super(vue);
        vue.getChampBatiment().addActionListener(e -> mettreAJourChampsBatiment());
        vue.getBtnAjouterCompteur().setEnabled(false);
        chargerBatiments();
        verifierEtatDonnees();
        afficherChampsSelonType();
    }

    @Override
    protected void enregistrerBoutons() {
        vue.getChampType().addActionListener(this);
        vue.getBtnAjouter().addActionListener(this);
        vue.getBtnAjouterCompteur().addActionListener(this);
        vue.getBtnAnnuler().addActionListener(this);
    }

    @Override
    protected void traiterAction(String nomBouton) {
        switch (nomBouton) {
            case "champType":
                afficherChampsSelonType();
                break;
            case "btnAjouter":
                ajouterBien();
                break;
            case "btnAjouterCompteur":
                ouvrirAjoutCompteur();
                break;
        }
    }

    // ---------------------------------------------------------------------
    //                 CHARGEMENT DES BATIMENTS
    // ---------------------------------------------------------------------
    private void chargerBatiments() {
        try {
            DaoBatiment dao = new DaoBatiment();
            listeBatiments = dao.findAll();
            vue.remplirBatiments(listeBatiments);
            verifierEtatDonnees();
        } catch (Exception ex) {
            vue.afficherErreur("Erreur chargement bâtiments : " + ex.getMessage());
        }
    }
    
    // ---------------------------------------------------------------------
    //         VÉRIFICATION DE L'ÉTAT DES DONNÉES
    // ---------------------------------------------------------------------
    private void verifierEtatDonnees() {
        try {
            DaoBatiment daoBatiment = new DaoBatiment();
            List<Batiment> listeBatiments = daoBatiment.findAll();
            
            boolean hasBatiments = !listeBatiments.isEmpty();
            
            // Griser le bouton si pas de bâtiments
            vue.getBtnAjouter().setEnabled(hasBatiments);
            
            // Afficher un message d'information si nécessaire
            if (!hasBatiments) {
                vue.afficherErreur("Aucun bâtiment trouvé dans la base de données. Veuillez d'abord créer des bâtiments.");
            }
            
        } catch (Exception ex) {
            System.err.println("Erreur vérification état données : " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // ---------------------------------------------------------------------
    //         MISE À JOUR AUTOMATIQUE DES CHAMPS DU BÂTIMENT
    // ---------------------------------------------------------------------
    private void mettreAJourChampsBatiment() {
        int index = vue.getBatimentIndex();

        if (index >= 0 && index < listeBatiments.size()) {
            vue.remplirChampsBatiment(listeBatiments.get(index));
        } else {
            vue.remplirChampsBatiment(null);
        }
    }

    // ---------------------------------------------------------------------
    //                         ACTIONS
    // ---------------------------------------------------------------------
    private void ajouterBien() {
        if (champsSontVides(vue.getNumeroFiscal(), vue.getAdresse(), 
                           vue.getCodePostal(), vue.getVille()) ||
            vue.getBatimentIndex() < 0) {
            afficherErreurChampsObligatoires();
            return;
        }

        // Validation pour Appartement et Garage : Surface et Pièces obligatoires
        if ((vue.getType().equals("Appartement") || vue.getType().equals("Garage")) &&
            (vue.getSurface().isEmpty() || vue.getPieces().isEmpty())) {

            vue.afficherErreur("Surface et nombre de pièces sont obligatoires.");
            return;
        }
        
        // Validation spécifique pour Appartement : Étage et Porte obligatoires
        if (vue.getType().equals("Appartement") &&
            (vue.getEtage().isEmpty() || vue.getPorte().isEmpty())) {

            vue.afficherErreur("Étage et porte sont obligatoires pour un appartement.");
            return;
        }

        Batiment bat = listeBatiments.get(vue.getBatimentIndex());

        // Pour un Garage, Étage = 0 et Porte = null
        int etage = 0;
        String porte = null;
        
        if (vue.getType().equals("Appartement")) {
            etage = vue.getEtage().isEmpty() ? 0 : Integer.parseInt(vue.getEtage());
            porte = vue.getPorte();
        }

        BienLouable bien = new BienLouable(
            vue.getNumeroFiscal(),
            vue.getType(),
            vue.getSurface().isEmpty() ? 0 : Integer.parseInt(vue.getSurface()),
            vue.getPieces().isEmpty() ? 0 : Integer.parseInt(vue.getPieces()),
            bat.getIdBatiment(),
            etage,
            porte
        );

        try {
            DaoBienLouable dao = new DaoBienLouable();
            dao.create(bien);

            if (vue.getGfp() != null)
                vue.getGfp().afficherTableauBienLouable();
            
            vue.getBtnAjouterCompteur().setEnabled(true);
            vue.afficherSucces("Bien louable ajouté !\n" + bien.toString());
            vue.dispose();

        } catch (Exception ex) {
            vue.afficherErreur("Erreur lors de l'ajout en BD : " + ex.getMessage());
        }
    }

    private void afficherChampsSelonType() {
        boolean estAppartement = vue.getType().equals("Appartement");
        boolean estGarage = vue.getType().equals("Garage");
        
        vue.getPanelSurface().setVisible(estAppartement || estGarage);
        vue.getPanelPieces().setVisible(estAppartement || estGarage);
        vue.getPanelEtage().setVisible(estAppartement);
        vue.getPanelPorte().setVisible(estAppartement);
        
        if (estGarage) {
            vue.getChampEtage().setText("");
            vue.getChampPorte().setText("");
        }
        
        vue.getFormPanel().revalidate();
        vue.getFormPanel().repaint();
    }

    // ---------------------------------------------------------------------
    //                 AJOUT COMPTEUR 
    // ---------------------------------------------------------------------
    private void ouvrirAjoutCompteur() {
        try {
            DaoBienLouable dao = new DaoBienLouable();
            BienLouable bien = dao.findById(vue.getNumeroFiscal());

            if (bien == null) {
                vue.afficherErreur("Veuillez d'abord enregistrer le bien.");
                return;
            }

            Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(vue);
            FenetreAjouterCompteurBienLouable facbl =
                new FenetreAjouterCompteurBienLouable(parentFrame, bien);

            facbl.setVisible(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                null,
                ex.getMessage(),
                "Erreur",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
