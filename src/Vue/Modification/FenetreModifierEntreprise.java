package Vue.Modification;

import javax.swing.*;
import Modele.Entreprise;
import Controleur.GestionFenetrePrincipale;
import Controleur.Modifier.GestionModifierEntreprise;
import Vue.ajouter.FenetreAjouterEntreprise;

public class FenetreModifierEntreprise extends FenetreAjouterEntreprise {

    private static final long serialVersionUID = 1L;

    private Entreprise entreprise;
    private JButton btnModifier;
    private GestionFenetrePrincipale gfp;
    
    /**
     * Initialise la fenêtre de modification d'une entreprise existante,
     * préremplit les champs avec les informations actuelles
     * et configure le bouton pour valider les modifications via le contrôleur dédié.
     */


    public FenetreModifierEntreprise(GestionFenetrePrincipale gfp, JDesktopPane desktop, Entreprise entreprise) {
        super(gfp, desktop);

        this.entreprise = entreprise;
        this.gfp = gfp;

        setTitle("Modifier une entreprise");
        getTitre().setText("Modifier une entreprise");

        getChampSiret().setText(entreprise.getSiret());
        getChampSiret().setEditable(false); 
        getChampNom().setText(entreprise.getNom());
        getChampVille().setText(entreprise.getVille());
        getChampMail().setText(entreprise.getMail());
        getChampAdresse().setText(entreprise.getAdresse());
        getChampSpecialite().setText(entreprise.getSpecialite());
        getChampCodePostale().setText(entreprise.getCode_postale());

        btnModifier = getBtnAjouter();
        btnModifier.setText("Modifier");
        btnModifier.setName("btnModifierEntreprise");

        new GestionModifierEntreprise(this);
    }

    // ---------------- Getters ----------------
    public JButton getBtnModifier() { return btnModifier; }
    public Entreprise getEntreprise() { return entreprise; }
    public GestionFenetrePrincipale getGfp() { return gfp; }

    // ---------------- Messages ----------------
    public void afficherErreur(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Erreur", JOptionPane.WARNING_MESSAGE);
    }

    public void afficherSucces(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Succès", JOptionPane.INFORMATION_MESSAGE);
    }
}
