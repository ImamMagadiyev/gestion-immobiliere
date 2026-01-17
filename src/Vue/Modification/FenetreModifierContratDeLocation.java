package Vue.Modification;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.JDesktopPane;

import Controleur.GestionFenetrePrincipale;
import Controleur.Modifier.GestionModifierContratDeLocation;
import Modele.BienLouable;
import Modele.ContratDeLocation;
import Modele.Locataire;
import Modele.dao.DaoBienLouable;
import Modele.dao.DaoLocataire;
import Vue.ajouter.FenetreAjouterContratDeLocation;

public class FenetreModifierContratDeLocation extends FenetreAjouterContratDeLocation {

    private static final long serialVersionUID = 1L;

    /**
     * Initialise la fenêtre pour modifier un contrat de location existant,
     * préremplit les champs avec les informations actuelles
     * et configure le bouton pour enregistrer les modifications via le contrôleur dédié.
     */

    public FenetreModifierContratDeLocation(GestionFenetrePrincipale gfp,
                                            JDesktopPane desktop,
                                            ContratDeLocation contrat) {
        super(gfp, desktop, false);

        setTitle("Modifier un contrat de location");
        titre.setText("Modifier contrat de location");

        // Charger d'abord les locataires et biens AVANT de faire setSelectedItem
        try {
            DaoLocataire daoLoc = new DaoLocataire();
            List<Locataire> listeLocataires = daoLoc.findAll();
            remplirLocataires(listeLocataires);
            
            DaoBienLouable daoBien = new DaoBienLouable();
            List<BienLouable> listeBiens = daoBien.findAll();
            remplirBiens(listeBiens);
        } catch (Exception ex) {
            System.err.println("Erreur chargement données : " + ex.getMessage());
            ex.printStackTrace();
        }

        // Maintenant que les combos sont remplies, on peut sélectionner les valeurs
        getChampIdContrat().setText(contrat.getIdContrat());
        getChampIdContrat().setEnabled(false);

        getChampDateDebut().setText(contrat.getDate_debut());
        
        String trimestreValue = convertNumberToTrimestre(contrat.getTrimestre());
        getChampTrimestre().setSelectedItem(trimestreValue);
        
        getChampDateSortie().setText(contrat.getDate_sortie());
        getChampLoyer().setText(String.valueOf(contrat.getLoyer_aPayer()));
        getChampIRL().setText(String.valueOf(contrat.getIRL()));
        getChampProvisions().setText(String.valueOf(contrat.getProvisions_charges()));
        getChampDuree().setText(String.valueOf(contrat.getDuree()));		
        
        // Sélectionner le locataire et griser
        getChampIdLocataire().setSelectedItem(contrat.getId_locataire());
        getChampIdLocataire().setEnabled(false);
        
        // Sélectionner le bien fiscal et griser
        getChampNumeroFiscale().setSelectedItem(contrat.getNumero_fiscale());
        getChampNumeroFiscale().setEnabled(false);
        
        griserComboBox();
        
        new GestionModifierContratDeLocation(this, contrat);
    }

    public void griserComboBox() {
        Color couleurGris = new Color(220, 220, 220);
        getChampIdLocataire().setBackground(couleurGris);
        getChampIdLocataire().setForeground(Color.BLACK);
        getChampIdLocataire().setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        getChampNumeroFiscale().setBackground(couleurGris);
        getChampNumeroFiscale().setForeground(Color.BLACK);
        getChampNumeroFiscale().setFont(new Font("Segoe UI", Font.BOLD, 13));
    }
    
    private String convertNumberToTrimestre(String trimestreStr) {
        if (trimestreStr == null || trimestreStr.isEmpty()) return "T1";
        if (trimestreStr.equals("1")) return "T1";
        if (trimestreStr.equals("2")) return "T2";
        if (trimestreStr.equals("3")) return "T3";
        if (trimestreStr.equals("4")) return "T4";
        return "T1";
    }
}