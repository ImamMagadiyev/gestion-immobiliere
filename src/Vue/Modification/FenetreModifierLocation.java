package Vue.Modification;

import java.awt.Color;
import java.awt.Font;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;

import Controleur.GestionFenetrePrincipale;
import Controleur.Modifier.GestionModifierLocation;
import Modele.Loyer;
import Vue.ajouter.FenetreAjouterLocation;

public class FenetreModifierLocation extends FenetreAjouterLocation {

    private static final long serialVersionUID = 1L;

    private Loyer loyer;
    private JButton btnModifier;
    private GestionFenetrePrincipale gfp;

    public FenetreModifierLocation(GestionFenetrePrincipale gfp, JDesktopPane desktop, Loyer loyer) {
        super(gfp, desktop);

        this.loyer = loyer;
        this.gfp = gfp;

        setTitle("Modifier un loyer");
        titre.setText("Modifier loyer");

        
        getChampIdLocataire().setSelectedItem(loyer.getIdLocataire());
        getChampIdLocataire().setEnabled(false);

     
        getChampNumeroFiscal().setSelectedItem(loyer.getNumero_fiscale());
        getChampNumeroFiscal().setEnabled(false);

        // Champ date paiement : convertir LocalDate en texte
        LocalDate datePaiement = loyer.getDate_paiement();
        getChampDatePaiement().setText(datePaiement != null ? datePaiement.toString() : "");
        getChampDatePaiement().setEnabled(false);

        // Champs montants et mois
        getChampMontantProvision().setText(String.valueOf(loyer.getMontant_provision()));
        getChampMontantLoyer().setText(String.valueOf(loyer.getMontant_loyer()));
        getChampMois().setText(loyer.getMois());
        getChampMontantRegularisation().setText(String.valueOf(loyer.getMontant_regularisation()));

        // Bouton Modifier
        btnModifier = getBtnAjouter();
        btnModifier.setText("Modifier");
        btnModifier.setName("btnModifierLoyer");

        // Associer le contrôleur spécifique
        new GestionModifierLocation(this);
    }

    public JButton getBtnModifier() {
        return btnModifier;
    }

    public Loyer getLoyer() {
        return loyer;
    }

    public GestionFenetrePrincipale getGfp() {
        return gfp;
    }

    public void afficherErreur(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Erreur", JOptionPane.WARNING_MESSAGE);
    }

    public void afficherSucces(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Succès", JOptionPane.INFORMATION_MESSAGE);
    }

    public void griserComboBox() {
        Color couleurGris = new Color(220, 220, 220);
        getChampNumeroFiscal().setBackground(couleurGris);
        getChampNumeroFiscal().setForeground(Color.BLACK);
        getChampNumeroFiscal().setFont(new Font("Segoe UI", Font.BOLD, 13));
    }
}
