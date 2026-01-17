package Controleur.Ajouter;

import java.util.ArrayList;
import java.util.List;

import Controleur.Affichage.GestionAfficherCaution;
import Modele.Caution;
import Modele.ContratDeLocation;
import Modele.dao.DaoCaution;
import Modele.dao.DaoContratDeLocation;
import Vue.ajouter.FenetreAjouterCaution;

public class GestionAjouterCaution extends GestionControleurAjoutBase<FenetreAjouterCaution> {

    private GestionAfficherCaution gestionAfficher;

    public GestionAjouterCaution(FenetreAjouterCaution vue, GestionAfficherCaution gestionAfficher) {
        super(vue);
        this.gestionAfficher = gestionAfficher;
        chargerContrats();
        verifierEtatDonnees();
    }

    @Override
    protected void enregistrerBoutons() {
        vue.getBtnEnregistrer().addActionListener(this);
        vue.getBtnAnnuler().addActionListener(this);
    }

    @Override
    protected void traiterAction(String nomBouton) {
        if ("btnEnregistrerCaution".equals(nomBouton)) {
            if (ajouterCaution()) {
                vue.afficherSucces("Caution enregistrée.");
                if (gestionAfficher != null) {
                    try {
                        gestionAfficher.chargerCautions();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                vue.dispose();
            }
        }
    }

    private void chargerContrats() {
        try {
            DaoContratDeLocation dao = new DaoContratDeLocation();
            List<ContratDeLocation> listeContrats = dao.findAll();
            List<String> idsContrats = new ArrayList<>();
            
            for (ContratDeLocation c : listeContrats) {
                idsContrats.add(c.getIdContrat());
            }
            
            vue.remplirContrats(idsContrats);
            verifierEtatDonnees();
        } catch (Exception ex) {
            System.err.println("Erreur lors du chargement des contrats : " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    // -------------------------------------------------------
    //         VÉRIFICATION DE L'ÉTAT DES DONNÉES
    // -------------------------------------------------------
    private void verifierEtatDonnees() {
        try {
            DaoContratDeLocation daoContrat = new DaoContratDeLocation();
            List<ContratDeLocation> listeContrats = daoContrat.findAll();
            
            boolean hasContrats = !listeContrats.isEmpty();
            
            // Griser le bouton si pas de contrats
            vue.getBtnEnregistrer().setEnabled(hasContrats);
            
            // Afficher un message d'information si nécessaire
            if (!hasContrats) {
                vue.afficherErreur("Aucun contrat de location trouvé dans la base de données. Veuillez d'abord créer des contrats de location.");
            }
            
        } catch (Exception ex) {
            System.err.println("Erreur vérification état données : " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private boolean ajouterCaution() {
        if (champsSontVides(vue.getIdCaution(), vue.getNom(), vue.getPrenom()) ||
                vue.getTypeContrat().equals("Sélectionner un type") ||
                vue.getIdContratIndex() < 0) {
            afficherErreurChampsObligatoires();
            return false;
        }

        Caution c = new Caution(
            vue.getIdCaution(),
            vue.getNom(),
            vue.getPrenom(),
            vue.getAdresse(),
            vue.getVille(),
            vue.getCodePostale(),
            vue.getProfession(),
            vue.getTypeContrat(),
            vue.getDateNaissance(),
            vue.getIdContrat(),
            vue.getMail(),
            vue.getTelephone(),
            vue.getQualite(),
            false
        );

        try {
            DaoCaution dao = new DaoCaution();
            dao.create(c);
            return true;

        } catch (Exception ex) {
            vue.afficherErreur("Erreur lors de l'ajout en BD : " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }
}
