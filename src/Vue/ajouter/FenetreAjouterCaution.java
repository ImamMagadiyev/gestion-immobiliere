package Vue.ajouter;

import javax.swing.*;
import java.awt.*;

import Controleur.Ajouter.GestionAjouterCaution;
import Vue.Utils;

public class FenetreAjouterCaution extends JDialog {

    private static final long serialVersionUID = 1L;

    protected JTextField champIdCaution;
    protected JTextField champNom;
    protected JTextField champPrenom;
    protected JTextField champAdresse;
    protected JTextField champVille;
    protected JTextField champCodePostale;
    protected JTextField champProfession;
    protected JComboBox<String> champTypeContrat;
    protected JTextField champDateNaissance;
    protected JComboBox<String> champIdContrat;
    protected JTextField champMail;
    protected JTextField champTelephone;
    protected JTextField champQualite;

    protected JButton btnEnregistrer;
    protected JButton btnAnnuler;
    protected JLabel titre;

    /**
     * FenetreAjouterCaution : JDialog pour ajouter une caution.
     * 
     * Cette fenêtre permet de saisir les informations principales d'une caution :
     * identité, adresse, profession, type de contrat, date de naissance, contrat lié,
     * mail, téléphone et qualité bailleur.
     * 
     * Les contrats peuvent être ajoutés dynamiquement dans la combo via remplirContrats.
     * Les boutons "Enregistrer" et "Annuler" permettent respectivement d'ajouter la caution
     * via le contrôleur GestionAjouterCaution ou de fermer la fenêtre.
     */
    public FenetreAjouterCaution(Frame parent) {
        super(parent, "Ajouter caution", true);

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        titre = Utils.creerTitre("Ajouter caution");
        add(titre, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(0, 2, 12, 10));
        form.setOpaque(false);
        form.setBorder(BorderFactory.createEmptyBorder(12, 18, 12, 18));

        champIdCaution = Utils.creerTextField("ID Caution", 100, 25);
        champNom = Utils.creerTextField("Nom", 100, 25);
        champPrenom = Utils.creerTextField("Prénom", 100, 25);
        champAdresse = Utils.creerTextField("Adresse", 100, 25);
        champVille = Utils.creerTextField("Ville", 100, 25);
        champCodePostale = Utils.creerTextField("Code postal", 100, 25);
        champProfession = Utils.creerTextField("Profession", 100, 25);
        
        champTypeContrat = new JComboBox<>();
        champTypeContrat.addItem("Sélectionner un type");
        champTypeContrat.addItem("CDI");
        champTypeContrat.addItem("CDD");
        champTypeContrat.addItem("Alternance");
        champTypeContrat.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        champTypeContrat.setPreferredSize(new Dimension(100, 25));
        
        champDateNaissance = Utils.creerTextField("Date naissance", 100, 25);

        champIdContrat = new JComboBox<>();
        champIdContrat.addItem("Sélectionner un contrat");

        champMail = Utils.creerTextField("Mail", 100, 25);
        champTelephone = Utils.creerTextField("Téléphone", 100, 25);
        champQualite = Utils.creerTextField("Qualité bailleur", 100, 25);

        form.add(Utils.creerLabel("ID Caution :"));
        form.add(champIdCaution);
        form.add(Utils.creerLabel("Nom :"));
        form.add(champNom);
        form.add(Utils.creerLabel("Prénom :"));
        form.add(champPrenom);
        form.add(Utils.creerLabel("Adresse :"));
        form.add(champAdresse);
        form.add(Utils.creerLabel("Ville :"));
        form.add(champVille);
        form.add(Utils.creerLabel("Code postal :"));
        form.add(champCodePostale);
        form.add(Utils.creerLabel("Profession :"));
        form.add(champProfession);
        form.add(Utils.creerLabel("Type contrat :"));
        form.add(champTypeContrat);
        form.add(Utils.creerLabel("Date naissance :"));
        form.add(champDateNaissance);
        form.add(Utils.creerLabel("Contrat :"));
        form.add(champIdContrat);
        form.add(Utils.creerLabel("Mail :"));
        form.add(champMail);
        form.add(Utils.creerLabel("Téléphone :"));
        form.add(champTelephone);
        form.add(Utils.creerLabel("Qualité bailleur :"));
        form.add(champQualite);

        add(new JScrollPane(form), BorderLayout.CENTER);

        JPanel boutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 12));
        boutons.setOpaque(false);

        btnEnregistrer = Utils.creerBouton("Enregistrer");
        btnAnnuler = Utils.creerBouton("Annuler");

        btnEnregistrer.setName("btnEnregistrerCaution");
        btnAnnuler.setName("btnAnnulerCaution");

        boutons.add(btnEnregistrer);
        boutons.add(btnAnnuler);

        add(boutons, BorderLayout.SOUTH);

        setSize(500, 640);
        setLocationRelativeTo(parent);

        new GestionAjouterCaution(this, null);
    }

    // ========= GETTERS =========
    public JButton getBtnEnregistrer() { return btnEnregistrer; }
    public JButton getBtnAnnuler() { return btnAnnuler; }

    public String getIdCaution() { return champIdCaution.getText().trim(); }
    public String getNom() { return champNom.getText().trim(); }
    public String getPrenom() { return champPrenom.getText().trim(); }
    public String getAdresse() { return champAdresse.getText().trim(); }
    public String getVille() { return champVille.getText().trim(); }
    public String getCodePostale() { return champCodePostale.getText().trim(); }
    public String getProfession() { return champProfession.getText().trim(); }
    public String getTypeContrat() { return (String) champTypeContrat.getSelectedItem(); }
    public String getDateNaissance() { return champDateNaissance.getText().trim(); }
    public String getIdContrat() { return (String) champIdContrat.getSelectedItem(); }

    /**
     * Retourne l'index du contrat sélectionné sans l'item par défaut.
     * Le -1 permet d'avoir le bon index par rapport à la liste reçue par le contrôleur.
     */
    public int getIdContratIndex() { return champIdContrat.getSelectedIndex() - 1; }

    public JComboBox<String> getChampIdContrat() { return champIdContrat; }
    public String getMail() { return champMail.getText().trim(); }

    /**
     * Remplit la ComboBox des contrats.
     * Méthode appelée par le contrôleur après récupération des IDs contrats.
     */
    public void remplirContrats(java.util.List<String> liste) {
        champIdContrat.removeAllItems();
        champIdContrat.addItem("Sélectionner un contrat");
        for (String contrat : liste) {
            champIdContrat.addItem(contrat);
        }
    }

    public String getTelephone() { return champTelephone.getText().trim(); }
    public String getQualite() { return champQualite.getText().trim(); }

    /**
     * Affiche un message d'erreur sous forme de popup.
     * Utilisé si une saisie est invalide ou si l'ajout échoue.
     */
    public void afficherErreur(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Erreur", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Affiche un message de confirmation après une action réussie.
     * Utilisé quand la caution a bien été ajoutée.
     */
    public void afficherSucces(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Succès", JOptionPane.INFORMATION_MESSAGE);
    }
}
