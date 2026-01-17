package Vue.ajouter;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Controleur.GestionFenetrePrincipale;
import Controleur.Ajouter.GestionAjouterEntreprise;
import Modele.Entreprise;
import Vue.Utils;

public class FenetreAjouterEntreprise extends FenetreAjoutBase {

    private static final long serialVersionUID = 1L;

    private Entreprise entrepriseTemp;
    private GestionFenetrePrincipale gfp;

    // Champs du formulaire
    private JTextField champSiret, champNom, champVille, champMail, champAdresse, champSpecialite, champCodePostale;

    private JButton btnAjouter, btnAnnuler;
    
    /**
     * FenetreAjouterEntreprise : JInternalFrame pour ajouter une nouvelle entreprise.
     * 
     * Cette fenêtre affiche un formulaire permettant de saisir les informations principales 
     * d'une entreprise : SIRET, nom, ville, mail, adresse, spécialité et code postal.
     * 
     * Les boutons "Ajouter" et "Annuler" permettent respectivement de créer l'entreprise 
     * via le contrôleur GestionAjouterEntreprise ou de fermer la fenêtre.
     */
    public FenetreAjouterEntreprise(GestionFenetrePrincipale gfp, JDesktopPane desktop) {
        super("Ajouter une entreprise", "Nouvelle entreprise", 500, 500, desktop);

        this.gfp = gfp;

        champSiret = Utils.creerTextField("SIRET", 200, 25);
        champNom = Utils.creerTextField("Nom", 200, 25);
        champVille = Utils.creerTextField("Ville", 200, 25);
        champMail = Utils.creerTextField("Mail", 200, 25);
        champAdresse = Utils.creerTextField("Adresse", 200, 25);
        champSpecialite = Utils.creerTextField("Spécialité", 200, 25);
        champCodePostale = Utils.creerTextField("Code postal", 200, 25);

        form.add(creerLigne("SIRET :", champSiret, 150));
        form.add(Utils.creerEspacementVertical(8));
        form.add(creerLigne("Nom :", champNom, 150));
        form.add(Utils.creerEspacementVertical(8));
        form.add(creerLigne("Ville :", champVille, 150));
        form.add(Utils.creerEspacementVertical(8));
        form.add(creerLigne("Mail :", champMail, 150));
        form.add(Utils.creerEspacementVertical(8));
        form.add(creerLigne("Adresse :", champAdresse, 150));
        form.add(Utils.creerEspacementVertical(8));
        form.add(creerLigne("Spécialité :", champSpecialite, 150));
        form.add(Utils.creerEspacementVertical(8));
        form.add(creerLigne("Code postal :", champCodePostale, 150));

        btnAjouter = Utils.creerBoutonPrimaire("Ajouter");
        btnAjouter.setName("btnAjouter");
        panelBoutons.add(btnAjouter);

        btnAnnuler = Utils.creerBouton("Annuler");
        btnAnnuler.setName("btnAnnuler");
        panelBoutons.add(btnAnnuler);

        new GestionAjouterEntreprise(this);
    }

    // ---------------- Getters ----------------
    public JTextField getChampSiret() { return champSiret; }
    public JTextField getChampNom() { return champNom; }
    public JTextField getChampVille() { return champVille; }
    public JTextField getChampMail() { return champMail; }
    public JTextField getChampAdresse() { return champAdresse; }
    public JTextField getChampSpecialite() { return champSpecialite; }
    public JTextField getChampCodePostale() { return champCodePostale; }

    public JButton getBtnAjouter() { return btnAjouter; }
    public JButton getBtnAnnuler() { return btnAnnuler; }
    public JLabel getTitre() { return titre; }

    public JDesktopPane getDesktop() { return desktop; }

    /**
     * Stocke temporairement l'entreprise en cours de création.
     * Utilisé par le contrôleur après la validation du formulaire.
     */
    public void setEntrepriseTemp(Entreprise e) { this.entrepriseTemp = e; }

    /**
     * Retourne l'entreprise temporaire créée.
     * Permet au contrôleur de récupérer l'objet après l'ajout.
     */
    public Entreprise getEntrepriseTemp() { return entrepriseTemp; }

    public GestionFenetrePrincipale getGfp() { return gfp; }

    // ---------------- Messages ----------------
    /**
     * Affiche un message d'erreur sous forme de popup.
     * Utilisé en cas de problème de saisie ou d'échec lors de l'ajout.
     */
    public void afficherErreur(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Erreur", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Affiche un message de confirmation après une action réussie.
     * Utilisé lorsque l'entreprise a bien été ajoutée.
     */
    public void afficherSucces(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Succès", JOptionPane.INFORMATION_MESSAGE);
    }
}
