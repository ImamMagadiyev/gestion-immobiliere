package Controleur.Ajouter;

import java.util.List;

import Modele.Facture;
import Modele.Travaux;
import Modele.dao.DaoFacture;
import Modele.dao.DaoTravaux;
import Vue.ajouter.FenetreAjouterTravaux;

public class GestionAjouterTravaux extends GestionControleurAjoutBase<FenetreAjouterTravaux> {

    private List<Facture> listeFactures;

    public GestionAjouterTravaux(FenetreAjouterTravaux vue) {
        super(vue);
        chargerFactures();
        verifierEtatDonnees();
    }

    @Override
    protected void enregistrerBoutons() {
        vue.getBtnAjouter().addActionListener(this);
        vue.getBtnAnnuler().addActionListener(this);
    }

    @Override
    protected void traiterAction(String nomBouton) {
        if ("btnValiderTravaux".equals(nomBouton)) {
            ajouterTravaux();
        }
    }

    // ====== CHARGEMENT FACTURES ======
    private void chargerFactures() {
        try {
            DaoFacture dao = new DaoFacture();
            listeFactures = dao.findAll();
            
            System.out.println("[DEBUG] Nombre de factures chargées : " + listeFactures.size());
            for (Facture f : listeFactures) {
                System.out.println("[DEBUG] Facture : " + f.getNumero());
            }
            
            vue.remplirFacture(listeFactures);
            verifierEtatDonnees();
        } catch (Exception e) {
            vue.afficherErreur("Erreur chargement factures : " + e.getMessage());
        }
    }
    
    // -------------------------------------------------------
    //         VÉRIFICATION DE L'ÉTAT DES DONNÉES
    // -------------------------------------------------------
    private void verifierEtatDonnees() {
        try {
            DaoFacture daoFacture = new DaoFacture();
            List<Facture> listeFactures = daoFacture.findAll();
            
            boolean hasFactures = !listeFactures.isEmpty();
            
            // Griser le bouton si pas de factures
            vue.getBtnAjouter().setEnabled(hasFactures);
            
            // Afficher un message d'information si nécessaire
            if (!hasFactures) {
                vue.afficherErreur("Aucune facture trouvée dans la base de données. Veuillez d'abord créer des factures.");
            }
            
        } catch (Exception ex) {
            System.err.println("Erreur vérification état données : " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void ajouterTravaux() {
        if (champsSontVides(vue.getIdTravaux(), vue.getRaison()) || vue.getFactureIndex() < 0) {
            afficherErreurChampsObligatoires();
            return;
        }

        Travaux travaux = new Travaux(
            vue.getIdTravaux(),
            vue.getRaison(),
            vue.getNumeroFacture()
        );

        try {
            DaoTravaux dao = new DaoTravaux();
            dao.create(travaux);
            vue.afficherSucces("Travaux ajouté avec succès.");
            vue.dispose();

        } catch (Exception e) {
            vue.afficherErreur("Erreur lors de l'ajout : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
