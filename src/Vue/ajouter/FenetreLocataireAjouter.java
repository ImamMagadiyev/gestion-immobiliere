package Vue.ajouter;

import java.awt.*;
import javax.swing.*;

import Controleur.GestionFenetrePrincipale;
import Modele.Locataire;
import Modele.dao.DaoLocataire;
import Vue.Utils;

public class FenetreLocataireAjouter extends JInternalFrame {

    private static final long serialVersionUID = 1L;

    // Form fields
    private JTextField champIdLocataire, champNom, champPrenom, champDateNaissance,
            champEmail, champAdresse, champVille, champCodePostal, champTelephone;
    private JComboBox<String> champGenre;
    protected JLabel titre;
    private GestionFenetrePrincipale gfp;

    // Buttons
    private JButton btnAjouter, btnAnnuler;

    /**
     * Initialise la fenêtre pour ajouter un nouveau locataire,
     * crée et organise les champs de saisie pour toutes les informations du locataire,
     * configure les boutons pour ajouter ou annuler,
     * et connecte le formulaire au contrôleur dédié pour gérer l'ajout.
     */

    public FenetreLocataireAjouter() {
        this(null);
    }
    
    public FenetreLocataireAjouter(GestionFenetrePrincipale gfp) {
    	
        super("Ajouter un locataire", true, true, true, true);
        this.gfp = gfp;
        setSize(500, 550);
        setVisible(true);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(new Color(245, 245, 245));

        // ---------------- Titre ----------------
        titre = Utils.creerTitre("Ajouter un locataire");
        add(titre, BorderLayout.NORTH);

        // ---------------- Formulaire ----------------
        JPanel panelForm = new JPanel(new GridLayout(0, 2, 20, 15));
        panelForm.setOpaque(false);
        panelForm.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        champIdLocataire = Utils.creerTextField("Identifiant locataire", 150, 25);
        champNom = Utils.creerTextField("Nom", 150, 25);
        champPrenom = Utils.creerTextField("Prénom", 150, 25);
        champDateNaissance = Utils.creerTextField("YYYY-MM-DD", 150, 25);
        champGenre = new JComboBox<>();
        champGenre.addItem("H");
        champGenre.addItem("F");
        champGenre.addItem("A"); // Autre

        champEmail = Utils.creerTextField("Email", 150, 25);
        champAdresse = Utils.creerTextField("Adresse", 150, 25);
        champVille = Utils.creerTextField("Ville", 150, 25);
        champCodePostal = Utils.creerTextField("Code Postal", 150, 25);
        champTelephone = Utils.creerTextField("Téléphone", 150, 25);

        // Add labels + fields to form
        panelForm.add(Utils.creerLabel("ID Locataire :")); panelForm.add(champIdLocataire);
        panelForm.add(Utils.creerLabel("Nom :")); panelForm.add(champNom);
        panelForm.add(Utils.creerLabel("Prénom :")); panelForm.add(champPrenom);
        panelForm.add(Utils.creerLabel("Date de naissance :")); panelForm.add(champDateNaissance);
        panelForm.add(Utils.creerLabel("Genre :")); panelForm.add(champGenre);
        panelForm.add(Utils.creerLabel("Email :")); panelForm.add(champEmail);
        panelForm.add(Utils.creerLabel("Adresse :")); panelForm.add(champAdresse);
        panelForm.add(Utils.creerLabel("Ville :")); panelForm.add(champVille);
        panelForm.add(Utils.creerLabel("Code Postal :")); panelForm.add(champCodePostal);
        panelForm.add(Utils.creerLabel("Téléphone :")); panelForm.add(champTelephone);

        add(panelForm, BorderLayout.CENTER);

        // ---------------- Boutons ----------------
        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panelBoutons.setOpaque(false);

        btnAjouter = Utils.creerBoutonPrimaire("Ajouter");
        btnAjouter.setName("btnAjouter");
        btnAnnuler = Utils.creerBouton("Annuler");
        btnAnnuler.setName("btnAnnuler");

        panelBoutons.add(btnAjouter);
        panelBoutons.add(btnAnnuler);

        add(panelBoutons, BorderLayout.SOUTH);

        // ---------------- Actions ----------------
        btnAnnuler.addActionListener(e -> dispose());

        // Call controller only if this is not a subclass (i.e., for Ajouter, not Modifier)
        if (this.getClass() == FenetreLocataireAjouter.class) {
            new Controleur.Ajouter.GestionAjouterLocataire(this);
        }
    }

    // ---------------- Getters ----------------
    public JButton getBtnAjouter() { return btnAjouter; }
    public JButton getBtnAnnuler() { return btnAnnuler; }
    public JLabel getTitre() { return titre; }

    public JTextField getIdField() { return champIdLocataire; }
    public JTextField getNomField() { return champNom; }
    public JTextField getPrenomField() { return champPrenom; }
    public JTextField getDateNaissanceField() { return champDateNaissance; }
    public JComboBox<String> getGenreCombo() { return champGenre; }
    public JTextField getEmailField() { return champEmail; }
    public JTextField getAdresseField() { return champAdresse; }
    public JTextField getVilleField() { return champVille; }
    public JTextField getCodePostalField() { return champCodePostal; }
    public JTextField getTelephoneField() { return champTelephone; }

    // ---------------- Méthodes pour afficher messages ----------------
    /**
     * Affiche une boîte de dialogue d'erreur avec un message personnalisé.
     * Utilisée pour signaler une validation incorrecte ou un problème lors de l'ajout.
     */
    public void afficherErreur(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Erreur", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Affiche une boîte de dialogue de confirmation (succès) avec un message personnalisé.
     * Utilisée après une création réussie (ex : ajout en base validé).
     */
    public void afficherSucces(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Succès", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public GestionFenetrePrincipale getGestionFenetrePrincipale() {
        return gfp;
    }
}
