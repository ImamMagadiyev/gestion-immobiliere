package Controleur.Ajouter;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

import Controleur.Affichage.GestionAfficherDiagnostics;
import Modele.BienLouable;
import Modele.Diagnostic;
import Modele.Entreprise;
import Modele.dao.DaoBienLouable;
import Modele.dao.DaoDiagnostic;
import Modele.dao.DaoEntreprise;
import Vue.Utils;
import Vue.ajouter.FenetreAjouterDiagnostic;

public class GestionAjouterDiagnostic implements ActionListener {

    private FenetreAjouterDiagnostic vue;
    private GestionAfficherDiagnostics gestionAfficher;
    private List<BienLouable> listeBienLouable;
    private List<Entreprise> listeEntreprise;



    public GestionAjouterDiagnostic(FenetreAjouterDiagnostic vue, GestionAfficherDiagnostics gestionAfficher) {
        this.vue = vue;
        this.gestionAfficher = gestionAfficher;
        this.vue.getBtnEnregistrer().addActionListener(this);
        this.vue.getBtnAnnuler().addActionListener(this);
        chargerBienLouable();
        chargerEntreprise();
        
        // Vérifier l'état des données pour griser les boutons si nécessaire
        verifierEtatDonnees();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object src = e.getSource();
        if (!(src instanceof Component)) return;

        Component c = (Component) src;
        String name = c.getName();
        if (name == null) return;

        switch (name) {

            case "btnEnregistrerDiagnostic":
            	if (ajouterDiagnostic()) {
            		vue.afficherSucces("Diagnostic enregistré.");
            		if (gestionAfficher != null) {
            			try {
            				gestionAfficher.chargerDiagnostics();
            			} catch (Exception ex) {
            				ex.printStackTrace();
            			}
            		}
            		vue.dispose();
            	}
                break;

            case "btnAnnulerDiagnostic":
                vue.dispose();
                break;

            default:
                System.out.println("Action inconnue : " + name);
        }
    }
    
    private void chargerBienLouable() {
    	try {
        	DaoBienLouable dao = new DaoBienLouable();
        	listeBienLouable = dao.findAll();
        	vue.remplirBienLouable(listeBienLouable);    		verifierEtatDonnees();    	} catch (Exception ex) {
    		vue.afficherErreur("Erreur chargement bien louable : " + ex.getMessage());
    	}
    }
    private void chargerEntreprise() {
    	try {
    		DaoEntreprise dao = new DaoEntreprise();
    		listeEntreprise = dao.findAll();
    		vue.remplirEntreprise(listeEntreprise);
    		verifierEtatDonnees();
    	}catch (Exception ex) {
    		vue.afficherErreur("Erreur chargement entreprises : " + ex.getMessage());
    	}
    }
    
    // -------------------------------------------------------
    //         VÉRIFICATION DE L'ÉTAT DES DONNÉES
    // -------------------------------------------------------
    private void verifierEtatDonnees() {
        try {
            DaoBienLouable daoBien = new DaoBienLouable();
            DaoEntreprise daoEntreprise = new DaoEntreprise();
            
            List<BienLouable> listeBiens = daoBien.findAll();
            List<Entreprise> listeEntreprises = daoEntreprise.findAll();
            
            boolean hasBiens = !listeBiens.isEmpty();
            boolean hasEntreprises = !listeEntreprises.isEmpty();
            
            // Griser le bouton si pas de biens ou pas d'entreprises
            vue.getBtnEnregistrer().setEnabled(hasBiens && hasEntreprises);
            
            // Afficher un message d'information si nécessaire
            if (!hasBiens && !hasEntreprises) {
                vue.afficherErreur("Aucun bien louable et aucune entreprise trouvé dans la base de données. Veuillez d'abord créer des biens et des entreprises.");
            } else if (!hasBiens) {
                vue.afficherErreur("Aucun bien louable trouvé dans la base de données. Veuillez d'abord créer des biens.");
            } else if (!hasEntreprises) {
                vue.afficherErreur("Aucune entreprise trouvée dans la base de données. Veuillez d'abord créer des entreprises.");
            }
            
        } catch (Exception ex) {
            System.err.println("Erreur vérification état données : " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    private boolean ajouterDiagnostic() {

        if (vue.getReference().isEmpty() ||
            vue.getDateValidite().isEmpty() ||
            vue.getTypeDiagnostic().isEmpty() ||
            vue.getNumeroFiscaleIndex() < 0 ||
            vue.getSiretIndex() < 0 ) {

            vue.afficherErreur("Veuillez remplir tous les champs obligatoires.");
            return false;
        }
        
        LocalDate date;
        try {
            date = Utils.parseLocalDate(vue.getDateValidite());
        } catch (Exception e) {
            vue.afficherErreur("Format date invalide (AAAA-MM-JJ attendu)");
            return false;
        }

        Diagnostic dia = new Diagnostic(
            vue.getReference(),
            date,
            vue.getTypeDiagnostic(),
            vue.getNumeroFiscale(),
            vue.getSiret(),
            false
        );

        try {
            DaoDiagnostic dao = new DaoDiagnostic();
            System.out.println("DEBUG : Tentative d'insertion du diagnostic -> " + dia);
            dao.create(dia);
            System.out.println("DEBUG : Diagnostic inséré avec succès");
            return true;
        } catch (Exception ex) {
            vue.afficherErreur("Erreur lors de l'ajout en BD : " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    
}
