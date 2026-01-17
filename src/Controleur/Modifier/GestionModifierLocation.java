package Controleur.Modifier;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import Modele.BienLouable;
import Modele.Locataire;
import Modele.Loyer;
import Modele.dao.DaoBienLouable;
import Modele.dao.DaoLocataire;
import Modele.dao.DaoLoyer;
import Vue.Modification.FenetreModifierLocation;

public class GestionModifierLocation implements ActionListener {

    private FenetreModifierLocation fml;
    private Loyer loyer;

    public GestionModifierLocation(FenetreModifierLocation fml) {
        this.fml = fml;
        this.loyer = fml.getLoyer();

        fml.getBtnModifier().addActionListener(this);
        fml.getBtnAnnuler().addActionListener(this);
        
        fml.griserComboBox();
        remplirLocataires();
        remplirBien();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = ((java.awt.Component)e.getSource()).getName();
        if (name == null) return;

        switch (name) {
            case "btnModifierLoyer":
                modifierLoyer();
                break;

            case "btnAnnuler":
                fml.dispose();
                break;
        }
    }
    
    public void remplirLocataires() {
        // Remplir la ComboBox des locataires
        try {
            DaoLocataire daoLocataire = new DaoLocataire();
            List<Locataire> listeLocataires = daoLocataire.findAll();
            fml.remplirLocataires(listeLocataires);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void remplirBien() {
        // Remplir la ComboBox des biens louables
        try {
            DaoBienLouable dao = new DaoBienLouable();
            List<BienLouable> listeBiens = dao.findAll();
            fml.remplirBiens(listeBiens);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void modifierLoyer() {
        try {
            double montantProvision = Double.parseDouble(fml.getChampMontantProvision().getText().trim());
            double montantLoyer = Double.parseDouble(fml.getChampMontantLoyer().getText().trim());
            String mois = fml.getChampMois().getText().trim();
            double montantRegularisation = fml.getChampMontantRegularisation().getText().trim().isEmpty()
                                          ? 0.0
                                          : Double.parseDouble(fml.getChampMontantRegularisation().getText().trim());

            loyer.setMontant_provision(montantProvision);
            loyer.setMontant_loyer(montantLoyer);
            loyer.setMois(mois);
            loyer.setMontant_regularisation(montantRegularisation);

            DaoLoyer dao = new DaoLoyer();
            dao.update(loyer);

            // 🔹 Debug minimal important
            System.out.println("[DEBUG] Loyer modifié avec succès pour idLocataire=" + loyer.getIdLocataire() +
                               ", numero_fiscale=" + loyer.getNumero_fiscale() +
                               ", date_paiement=" + loyer.getDate_paiement());

            fml.afficherSucces("Loyer modifié avec succès.");
            
            // Actualiser la table dans la fenetre principale
            if (fml.getGfp() != null) {
                fml.getGfp().afficherTableauLoyer();
            }

        } catch (NumberFormatException ex) {
            System.out.println("[DEBUG] Erreur format montant pour loyer idLocataire=" + loyer.getIdLocataire() +
                               ", numero_fiscale=" + loyer.getNumero_fiscale() +
                               ", date_paiement=" + loyer.getDate_paiement() +
                               " => " + ex.getMessage());
            fml.afficherErreur("Les montants doivent être des nombres valides.");
        } catch (SQLException ex) {
            System.out.println("[DEBUG] Erreur BD modification loyer pour idLocataire=" + loyer.getIdLocataire() +
                               ", numero_fiscale=" + loyer.getNumero_fiscale() +
                               ", date_paiement=" + loyer.getDate_paiement() +
                               " => " + ex.getMessage());
            fml.afficherErreur("Erreur BD : " + ex.getMessage());
        }
        
        // Fermer la fenêtre dans tous les cas
        fml.dispose();
    }
}
