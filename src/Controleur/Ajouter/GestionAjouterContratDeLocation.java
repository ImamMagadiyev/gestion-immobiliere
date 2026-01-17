package Controleur.Ajouter;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import Modele.BienLouable;
import Modele.ContratDeLocation;
import Modele.Locataire;
import Modele.dao.DaoBienLouable;
import Modele.dao.DaoContratDeLocation;
import Modele.dao.DaoLocataire;
import Vue.Utils;
import Vue.ajouter.FenetreAjouterContratDeLocation;

public class GestionAjouterContratDeLocation implements ActionListener {

    private FenetreAjouterContratDeLocation vue;
    private List<Locataire> listeLocataires;

    public GestionAjouterContratDeLocation(FenetreAjouterContratDeLocation vue) {
        this.vue = vue;
        
        // --- LISTENERS ---
        vue.getBtnAjouter().addActionListener(this);
        vue.getBtnAnnuler().addActionListener(this);
        
        // Charger les locataires existants au démarrage
        chargerLocataires();
        // Charger les biens louables existants au démarrage
        chargerBiens();
        
        // Vérifier l'état des données pour griser les boutons si nécessaire
        verifierEtatDonnees();
    }

    // -------------------------------------------------------
    //         CHARGEMENT DES LOCATAIRES EXISTANTS
    // -------------------------------------------------------
    private void chargerLocataires() {
        try {
            DaoLocataire dao = new DaoLocataire();
            // Charger les locataires non archivés
            listeLocataires = dao.findAll();
            // Ajouter aussi les locataires archivés
            listeLocataires.addAll(dao.findAllArchives());
            vue.remplirLocataires(listeLocataires);
            verifierEtatDonnees();
        } catch (Exception ex) {
            vue.afficherErreur("Erreur chargement locataires : " + ex.getMessage());
        }
    }

    // -------------------------------------------------------
    //         CHARGEMENT DES BIENS LOUABLES EXISTANTS
    // -------------------------------------------------------
    private void chargerBiens() {
        try {
            DaoBienLouable dao = new DaoBienLouable();
            // Charger tous les biens
            List<BienLouable> listeBiens = dao.findAll();
            vue.remplirBiens(listeBiens);
            verifierEtatDonnees();
        } catch (Exception ex) {
            vue.afficherErreur("Erreur chargement biens : " + ex.getMessage());
        }
    }

    // -------------------------------------------------------
    //         VÉRIFICATION DE L'ÉTAT DES DONNÉES
    // -------------------------------------------------------
    private void verifierEtatDonnees() {
        try {
            DaoLocataire daoLoc = new DaoLocataire();
            DaoBienLouable daoBien = new DaoBienLouable();
            
            List<Locataire> listeLocataires = daoLoc.findAll();
            List<BienLouable> listeBiens = daoBien.findAll();
            
            boolean hasLocataires = !listeLocataires.isEmpty();
            boolean hasBiens = !listeBiens.isEmpty();
            
            // Griser le bouton si pas de locataires ou pas de biens
            vue.getBtnAjouter().setEnabled(hasLocataires && hasBiens);
            
            // Afficher un message d'information si nécessaire
            if (!hasLocataires && !hasBiens) {
                vue.afficherErreur("Aucun locataire et aucun bien trouvé dans la base de données. Veuillez d'abord créer des locataires et des biens.");
            } else if (!hasLocataires) {
                vue.afficherErreur("Aucun locataire trouvé dans la base de données. Veuillez d'abord créer des locataires.");
            } else if (!hasBiens) {
                vue.afficherErreur("Aucun bien trouvé dans la base de données. Veuillez d'abord créer des biens.");
            }
            
        } catch (Exception ex) {
            System.err.println("Erreur vérification état données : " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Component src = (Component) e.getSource();
        if (src.getName() == null) return;

        switch (src.getName()) {
            case "btnAjouter":
                ajouter();
                break;
            case "btnAnnuler":
                vue.dispose();
                break;
        }
    }

    private void ajouter() {
        try {
            String idContrat = vue.getChampIdContrat().getText().trim();
            String idLocataireSelectionnee = vue.getIdLocataireSelectionnee();
            String numeroFiscaleSelectionnee = vue.getNumeroFiscaleSelectionnee();
            String trimestreSelectionnee = vue.getTrimestreSelectionnee();
            
            // Vérifier que l'ID Contrat a été saisi
            if (idContrat.isEmpty()) {
                vue.afficherErreur("Veuillez entrer un ID Contrat.");
                return;
            }
            
            // Vérifier qu'un locataire a été sélectionné
            if (idLocataireSelectionnee.equals("Sélectionner un locataire")) {
                vue.afficherErreur("Veuillez sélectionner un locataire.");
                return;
            }
            
            // Vérifier qu'un bien a été sélectionné
            if (numeroFiscaleSelectionnee.equals("Sélectionner un bien")) {
                vue.afficherErreur("Veuillez sélectionner un bien.");
                return;
            }
            
            // ⚠️ À la création : date_sortie et durée sont optionnels (vides)
            // Ils seront remplis en modification
            String dateDebut = vue.getChampDateDebut().getText().trim();
            String dateSortie = vue.getChampDateSortie().getText().trim();
            
            // Validation de la date de début
            try {
                Utils.parseLocalDate(dateDebut);
            } catch (Exception e) {
                vue.afficherErreur("Format date de début invalide (AAAA-MM-JJ attendu)");
                return;
            }
            
            // Validation de la date de sortie si elle n'est pas vide
            if (!dateSortie.isEmpty()) {
                try {
                    Utils.parseLocalDate(dateSortie);
                } catch (Exception e) {
                    vue.afficherErreur("Format date de sortie invalide (AAAA-MM-JJ attendu)");
                    return;
                }
            } else {
                dateSortie = null;  // NULL en base de données
            }
            
            // Validation des champs numériques obligatoires (Loyer, Provisions)
            String loyerText = vue.getChampLoyer().getText().trim();
            String provisionsText = vue.getChampProvisions().getText().trim();
            
            if (loyerText.isEmpty()) {
                vue.afficherErreur("Le loyer est obligatoire.");
                return;
            }
            if (provisionsText.isEmpty()) {
                vue.afficherErreur("Les provisions sont obligatoires.");
                return;
            }
            
            Double loyer = Double.parseDouble(loyerText);
            Double provisions = Double.parseDouble(provisionsText);
            
            // IRL et Durée sont optionnels
            String dureeText = vue.getChampDuree().getText().trim();
            Integer duree = dureeText.isEmpty() ? 0 : Integer.parseInt(dureeText);
            
            String irlText = vue.getChampIRL().getText().trim();
            Double irl = irlText.isEmpty() ? 0.0 : Double.parseDouble(irlText);

            ContratDeLocation c = new ContratDeLocation(
                idContrat,  // ID saisi par l'utilisateur
                dateDebut,
                trimestreSelectionnee,
                dateSortie,  // Peut être NULL
                loyer,
                irl,  // Peut être 0.0 si non fourni
                provisions,
                vue.getChkSolde().isSelected(),
                duree,  // 0 si vide
                numeroFiscaleSelectionnee,
                idLocataireSelectionnee,
                false  // archive = false pour les nouveaux contrats
            );

            DaoContratDeLocation dao = new DaoContratDeLocation();
            System.out.println("DEBUG : Tentative d'insertion du contrat -> " + c);
            dao.create(c);
            System.out.println("DEBUG : Contrat inséré avec succès");

            // 🔄 Rafraîchissement automatique du tableau
            if (vue.getGfp() != null) {
                vue.getGfp().afficherTableauBaux();
            }

            vue.afficherSucces("Contrat ajouté avec succès !");
            vue.dispose();

        } catch (NumberFormatException ex) {
            vue.afficherErreur("Erreur : loyer et provisions doivent être des nombres valides. IRL et durée peuvent être laissés vides.");
        } catch (Exception ex) {
            ex.printStackTrace();
            vue.afficherErreur("Erreur lors de l'ajout du contrat : " + ex.getMessage());
        }
    }
}
