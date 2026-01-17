package Controleur.Ajouter;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;
import java.util.List;

import Modele.BienLouable;
import Modele.ContratDeLocation;
import Modele.Locataire;
import Modele.Loyer;
import Modele.dao.DaoBienLouable;
import Modele.dao.DaoContratDeLocation;
import Modele.dao.DaoLocataire;
import Modele.dao.DaoLoyer;
import Vue.Utils;
import Vue.ajouter.FenetreAjouterLocation;

public class GestionAjouterLocation extends GestionControleurAjoutBase<FenetreAjouterLocation> {

    public GestionAjouterLocation(FenetreAjouterLocation vue) {
        super(vue);
        
        vue.getChampNumeroFiscal().setEnabled(false);
        vue.getChampMontantProvision().setEnabled(false);
        vue.getChampMontantLoyer().setEnabled(false);
        vue.getChampMontantRegularisation().setEnabled(false);
        
        chargerLocataires();
        chargerBiens();
        
        vue.getChampIdLocataire().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    remplirDonneesDepuisLocataire();
                }
            }
        });
        
        verifierEtatDonnees();
    }

    @Override
    protected void enregistrerBoutons() {
        vue.getBtnAjouter().addActionListener(this);
        vue.getBtnAnnuler().addActionListener(this);
    }

    @Override
    protected void traiterAction(String nomBouton) {
        if ("btnAjouter".equals(nomBouton)) {
            ajouterLoyer();
        }
    }

    // -------------------------------------------------------
    //         CHARGEMENT DES LOCATAIRES
    // -------------------------------------------------------
    private void chargerLocataires() {
        try {
            DaoLocataire dao = new DaoLocataire();
            List<Locataire> listeLocataires = dao.findAll();
            DaoContratDeLocation daoContrat = new DaoContratDeLocation();
            
            // Filtrer pour ne garder que les locataires avec un contrat actif
            List<Locataire> locatairesAvecContrat = new java.util.ArrayList<>();
            for (Locataire l : listeLocataires) {
                try {
                    ContratDeLocation contrat = daoContrat.findByIdLocataire(l.getIdLocataire());
                    if (contrat != null) {
                        locatairesAvecContrat.add(l);
                    }
                } catch (Exception e) {
                    // Ignorer si erreur sur ce locataire
                }
            }
            
            System.out.println("Locataires avec contrat actif : " + locatairesAvecContrat.size());
            vue.remplirLocataires(locatairesAvecContrat);
        } catch (Exception ex) {
            System.err.println("Erreur chargement locataires : " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // -------------------------------------------------------
    //         CHARGEMENT DES BIENS LOUABLES
    // -------------------------------------------------------
    private void chargerBiens() {
        try {
            DaoBienLouable dao = new DaoBienLouable();
            List<BienLouable> listeBiens = dao.findAll();
            System.out.println("Biens chargés : " + listeBiens.size());
            vue.remplirBiens(listeBiens);
        } catch (Exception ex) {
            System.err.println("Erreur chargement biens : " + ex.getMessage());
            ex.printStackTrace();
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
            
        } catch (Exception ex) {
            System.err.println("Erreur vérification état données : " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // -------------------------------------------------------
    //    REMPLISSAGE AUTO DES DONNÉES DU CONTRAT
    // -------------------------------------------------------
    private void remplirDonneesDepuisLocataire() {
        String idLocataire = (String) vue.getChampIdLocataire().getSelectedItem();
        
        // Vérifier que une sélection valide a été faite
        if (idLocataire == null || idLocataire.equals("Selectionner un locataire")) {
            // Nettoyer les champs et les réactiver (sauf regularisation qui reste grisée)
            vue.getChampNumeroFiscal().setSelectedIndex(0);
            vue.getChampMontantProvision().setText("");
            vue.getChampMontantLoyer().setText("");
            
            // Réactiver les champs
            vue.getChampNumeroFiscal().setEnabled(true);
            vue.getChampMontantProvision().setEnabled(true);
            vue.getChampMontantLoyer().setEnabled(true);
            // Regularisation reste toujours grisée
            vue.getChampMontantRegularisation().setEnabled(false);
            vue.getChampMontantRegularisation().setText("");
            return;
        }

        try {
            // Récupérer le contrat de location actif du locataire
            DaoContratDeLocation daoContrat = new DaoContratDeLocation();
            ContratDeLocation contrat = daoContrat.findByIdLocataire(idLocataire);
            
            if (contrat != null) {
                System.out.println("Contrat trouvé pour le locataire : " + idLocataire);
                
                // Remplir le bien fiscal
                String numeroFiscal = contrat.getNumero_fiscale();
                if (numeroFiscal != null && !numeroFiscal.isEmpty()) {
                    vue.getChampNumeroFiscal().setSelectedItem(numeroFiscal);
                    System.out.println("Bien fiscal sélectionné : " + numeroFiscal);
                }
                
                // Remplir le montant provision
                double montantProvision = contrat.getProvisions_charges();
                vue.getChampMontantProvision().setText(String.valueOf(montantProvision));
                System.out.println("Montant provision : " + montantProvision);
                
                // Remplir le montant loyer
                double montantLoyer = contrat.getLoyer_aPayer();
                vue.getChampMontantLoyer().setText(String.valueOf(montantLoyer));
                System.out.println("Montant loyer : " + montantLoyer);
                
                // Griser les champs auto-remplis
                vue.getChampNumeroFiscal().setEnabled(false);
                vue.getChampMontantProvision().setEnabled(false);
                vue.getChampMontantLoyer().setEnabled(false);
                // Regularisation reste toujours grisée
                vue.getChampMontantRegularisation().setEnabled(false);
                vue.getChampMontantRegularisation().setText("");
            } else {
                System.out.println("Aucun contrat trouvé pour le locataire : " + idLocataire);
                // Nettoyer les champs si pas de contrat
                vue.getChampNumeroFiscal().setSelectedIndex(0);
                vue.getChampMontantProvision().setText("");
                vue.getChampMontantLoyer().setText("");
                
                // Réactiver les champs
                vue.getChampNumeroFiscal().setEnabled(true);
                vue.getChampMontantProvision().setEnabled(true);
                vue.getChampMontantLoyer().setEnabled(true);
                // Regularisation reste toujours grisée
                vue.getChampMontantRegularisation().setEnabled(false);
                vue.getChampMontantRegularisation().setText("");
            }
        } catch (Exception ex) {
            System.err.println("Erreur lors du remplissage des données : " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (!(src instanceof Component)) return;

        String name = ((Component) src).getName();
        if (name == null) return;

        switch (name) {
            case "btnAjouter":
                ajouterLoyer();
                break;

            case "btnAnnuler":
                vue.dispose();
                break;
        }
    }

    private void ajouterLoyer() {
        if (vue.getChampIdLocataire().getSelectedItem().equals("Sélectionner un locataire") ||
            vue.getNumerFiscalSelectionnee().equals("Sélectionner un bien") ||
            vue.getChampDatePaiement().getText() == null ||
            vue.getChampMontantProvision().getText().trim().isEmpty() ||
            vue.getChampMontantLoyer().getText().trim().isEmpty() ||
            vue.getChampMois().getText().trim().isEmpty()) {

            vue.afficherErreur("Veuillez remplir tous les champs obligatoires.");
            return;
        }

        try {
            String idLocataire = ((String) vue.getChampIdLocataire().getSelectedItem()).trim();
            String numeroFiscal = vue.getNumerFiscalSelectionnee().trim();
            String texteDate = vue.getChampDatePaiement().getText().trim();
            LocalDate datePaiement = Utils.parseLocalDate(texteDate);

            double montantProvision = Double.parseDouble(vue.getChampMontantProvision().getText().trim());
            double montantLoyer = Double.parseDouble(vue.getChampMontantLoyer().getText().trim());
            String mois = vue.getChampMois().getText().trim();
            double montantRegularisation = vue.getChampMontantRegularisation().getText().trim().isEmpty() 
                                          ? 0.0 
                                          : Double.parseDouble(vue.getChampMontantRegularisation().getText().trim());

            Loyer loyer = new Loyer(idLocataire, numeroFiscal, datePaiement,
                                    montantProvision, montantLoyer, mois, montantRegularisation, false);
            System.out.println("DEBUG : Loyer à insérer -> " + loyer);
            DaoLoyer dao = new DaoLoyer();
            dao.create(loyer);
            System.out.println("DEBUG : Loyer inséré avec succès");

            vue.setLoyerTemp(loyer);

            if (vue.getGfp() != null)
                vue.getGfp().afficherTableauLoyer();

        } catch (NumberFormatException ex) {
            vue.afficherErreur("Les montants doivent être des nombres valides.");
        } catch (Exception ex) {
            vue.afficherErreur("Erreur lors de l'ajout en BD : " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
