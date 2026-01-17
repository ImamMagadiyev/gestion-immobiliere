package Vue.ajouter;

import Controleur.Ajouter.GestionAjouterContratDeLocation;
import Controleur.GestionFenetrePrincipale;
import Modele.BienLouable;
import Modele.Locataire;
import Vue.Utils;
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class FenetreAjouterContratDeLocation extends FenetreAjoutBase {

    private static final long serialVersionUID = 1L;

    private GestionFenetrePrincipale gfp;

    // Champs
    private JTextField champIdContrat;
    private JTextField champDateDebut;
    private JComboBox<String> champTrimestre;
    private JTextField champDateSortie;
    private JTextField champLoyer, champIRL, champProvisions;
    private JComboBox<String> champNumeroFiscale;
    private JTextField champDuree;
    private JComboBox<String> champIdLocataire;
    private JCheckBox chkSolde;

    private JButton btnAjouter, btnAnnuler;

    /**
     * FenetreAjouterContratDeLocation : JInternalFrame pour ajouter un nouveau contrat de location.
     * 
     * Cette fenêtre affiche un formulaire complet permettant de saisir toutes les informations
     * d'un contrat de location : identifiant, dates, trimestre, loyer, IRL, provisions, durée,
     * numéro fiscal, identifiant du locataire et états (solde tout compte effectué / archivé).
     * 
     * Les boutons "Ajouter" et "Annuler" permettent respectivement de créer le contrat via
     * le contrôleur GestionAjouterContratDeLocation ou de fermer la fenêtre.
     */

    /**
     * Constructeur principal utilisé pour l'ajout.
     * Appelle le constructeur protégé avec creerControleur = true pour créer le contrôleur d'ajout.
     */
    public FenetreAjouterContratDeLocation(GestionFenetrePrincipale gfp, JDesktopPane desktop) {
        this(gfp, desktop, true);  // true = créer le contrôleur d'ajout
    }

    /**
     * Constructeur utilisé aussi pour la modification.
     * Le boolean creerControleur permet de choisir si on crée GestionAjouterContratDeLocation ou pas
     * (utile pour la fenêtre Modifier qui ne doit pas avoir le contrôleur d'ajout).
     */
    protected FenetreAjouterContratDeLocation(GestionFenetrePrincipale gfp, JDesktopPane desktop, boolean creerControleur) {
        super("Ajouter un contrat de location", "Nouveau contrat de location", 500, 590, desktop);

        this.gfp = gfp;

        // Création des champs
        champIdContrat = Utils.creerTextField("ID Contrat", 200, 28);
        
        champDateDebut = Utils.creerTextField("Date début (YYYY-MM-DD)", 200, 28);
        
        champTrimestre = new JComboBox<>();
        champTrimestre.addItem("Sélectionner un trimestre");
        champTrimestre.addItem("T1");
        champTrimestre.addItem("T2");
        champTrimestre.addItem("T3");
        champTrimestre.addItem("T4");
        champTrimestre.setName("champTrimestre");
        champTrimestre.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        champTrimestre.setPreferredSize(new Dimension(200, 28));
        
        champDateSortie = Utils.creerTextField("Date sortie (YYYY-MM-DD)", 200, 28);
        champLoyer = Utils.creerTextField("Loyer à payer (€)", 200, 28);
        champIRL = Utils.creerTextField("IRL", 200, 28);
        champProvisions = Utils.creerTextField("Provisions charges (€)", 200, 28);
        
        champNumeroFiscale = new JComboBox<>();
        champNumeroFiscale.addItem("Sélectionner un bien");
        champNumeroFiscale.setName("champNumeroFiscale");

        champNumeroFiscale.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        champNumeroFiscale.setPreferredSize(new Dimension(200, 28));
        
        champDuree = Utils.creerTextField("Durée du contrat", 200, 28);
        
        champIdLocataire = new JComboBox<>();
        champIdLocataire.addItem("Sélectionner un locataire");
        champIdLocataire.setName("champIdLocataire");
        champIdLocataire.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        champIdLocataire.setPreferredSize(new Dimension(200, 28));

        // Checkboxes
        chkSolde = new JCheckBox("Solde tout compte effectué");

        chkSolde.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        // Ajout au formulaire
        form.add(creerLigne("ID Contrat :", champIdContrat, 180));
        form.add(Box.createVerticalStrut(8));
        form.add(creerLigne("Date début :", champDateDebut, 180));
        form.add(Box.createVerticalStrut(8));
        form.add(creerLigne("Trimestre :", champTrimestre, 180));
        form.add(Box.createVerticalStrut(8));
        form.add(creerLigne("Date sortie :", champDateSortie, 180));
        form.add(Box.createVerticalStrut(8));
        form.add(creerLigne("Loyer à payer :", champLoyer, 180));
        form.add(Box.createVerticalStrut(8));
        form.add(creerLigne("IRL :", champIRL, 180));
        form.add(Box.createVerticalStrut(8));
        form.add(creerLigne("Provisions charges :", champProvisions, 180));
        form.add(Box.createVerticalStrut(8));
        form.add(creerLigne("Numéro fiscal :", champNumeroFiscale, 180));
        form.add(Box.createVerticalStrut(8));
        form.add(creerLigne("Durée :", champDuree, 180));
        form.add(Box.createVerticalStrut(8));
        form.add(creerLigne("ID Locataire :", champIdLocataire, 180));
        form.add(Box.createVerticalStrut(12));

        JPanel panelCheckboxes = new JPanel();
        panelCheckboxes.setLayout(new BoxLayout(panelCheckboxes, BoxLayout.Y_AXIS));
        panelCheckboxes.setOpaque(false);
        panelCheckboxes.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        chkSolde.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panelCheckboxes.add(chkSolde);
        form.add(panelCheckboxes);

        getContentPane().add(form, BorderLayout.CENTER);

        btnAjouter = Utils.creerBoutonPrimaire("Ajouter");
        btnAjouter.setName("btnAjouter");
        panelBoutons.add(btnAjouter);
        
        btnAnnuler = Utils.creerBouton("Annuler");
        btnAnnuler.setName("btnAnnuler");
        panelBoutons.add(btnAnnuler);

        // Créer le contrôleur d'ajout seulement si c'est une fenêtre d'ajout (pas de modification)
        if (creerControleur) {
            new GestionAjouterContratDeLocation(this);
        }
    }

    /**
     * Remplit la ComboBox des locataires avec leurs identifiants.
     * Méthode appelée par le contrôleur après récupération des locataires en base.
     */
    public void remplirLocataires(List<Locataire> liste) {
        champIdLocataire.removeAllItems();
        champIdLocataire.addItem("Sélectionner un locataire");
        for (Locataire l : liste) {
            champIdLocataire.addItem(l.getIdLocataire());
        }
    }

    /**
     * Remplit la ComboBox des biens louables avec leurs numéros fiscaux.
     * Méthode appelée par le contrôleur pour afficher les biens disponibles.
     */
    public void remplirBiens(List<BienLouable> liste) {
        champNumeroFiscale.removeAllItems();
        champNumeroFiscale.addItem("Sélectionner un bien");
        for (BienLouable b : liste) {
            champNumeroFiscale.addItem(b.getNumero_fiscale());
        }
    }

    // Getters
    public JTextField getChampIdContrat() { return champIdContrat; }
    public JTextField getChampDateDebut() { return champDateDebut; }
    public String getTrimestreSelectionnee() { return (String) champTrimestre.getSelectedItem(); }
    public JComboBox<String> getChampTrimestre() { return champTrimestre; }
    public JTextField getChampDateSortie() { return champDateSortie; }
    public JTextField getChampLoyer() { return champLoyer; }
    public JTextField getChampIRL() { return champIRL; }
    public JTextField getChampProvisions() { return champProvisions; }
    public String getNumeroFiscaleSelectionnee() { return (String) champNumeroFiscale.getSelectedItem(); }
    public JComboBox<String> getChampNumeroFiscale() { return champNumeroFiscale; }
    public JTextField getChampDuree() { return champDuree; }
    public JComboBox<String> getChampIdLocataire() { return champIdLocataire; }
    public String getIdLocataireSelectionnee() { return (String) champIdLocataire.getSelectedItem(); }

    public JCheckBox getChkSolde() { return chkSolde; }
    public JButton getBtnAjouter() { return btnAjouter; }
    public JButton getBtnAnnuler() { return btnAnnuler; }

    public GestionFenetrePrincipale getGfp() { return gfp; }

    /**
     * Affiche un message d'erreur sous forme de popup.
     * Utilisé en cas de saisie invalide ou si l'ajout échoue.
     */
    public void afficherErreur(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Erreur", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Affiche un message de confirmation après une action réussie.
     * Utilisé quand le contrat a bien été ajouté.
     */
    public void afficherSucces(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Succès", JOptionPane.INFORMATION_MESSAGE);
    }
}
