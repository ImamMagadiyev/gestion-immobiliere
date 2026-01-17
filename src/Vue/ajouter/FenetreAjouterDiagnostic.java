package Vue.ajouter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import Modele.BienLouable;
import Modele.Entreprise;
import Vue.Utils;

public class FenetreAjouterDiagnostic extends JDialog {

    private static final long serialVersionUID = 1L;

    protected JTextField champReference;
    protected JTextField champDateValidite;
    protected JTextField champTypeDiagnostic;
    protected JComboBox<String> champSiret;
    protected JComboBox<String> champNumeroFiscale;
    protected JButton btnEnregistrer;
    protected JButton btnAnnuler;
    protected JLabel titre;

    /**
     * FenetreAjouterDiagnostic : JDialog pour ajouter un diagnostic.
     * 
     * Cette fenêtre permet de saisir les informations principales d'un diagnostic :
     * référence, date de validité, type, numéro fiscal du bien et SIRET de l'entreprise.
     * 
     * Les listes des biens louables et des entreprises sont remplies dynamiquement
     * via les méthodes remplirBienLouable et remplirEntreprise appelées par le contrôleur.
     * 
     * Les boutons "Enregistrer" et "Annuler" permettent respectivement d'ajouter
     * le diagnostic ou de fermer la fenêtre.
     */
    public FenetreAjouterDiagnostic(Frame parent) {
        super(parent, "Ajouter diagnostic", true);

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        titre = Utils.creerTitre("Ajouter diagnostic");
        add(titre, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(0, 2, 12, 10));
        form.setOpaque(false);
        form.setBorder(BorderFactory.createEmptyBorder(12, 18, 12, 18));

        champReference      = Utils.creerTextField("Référence", 100, 25);
        champDateValidite   = Utils.creerTextField("Date validité (YYYY-MM-DD)", 100, 25);
        champTypeDiagnostic = Utils.creerTextField("Type diagnostic", 100, 25);
        champNumeroFiscale = new JComboBox<>();
        champNumeroFiscale.addItem("Sélectionner un numero fiscale");
        champNumeroFiscale.setName("champNumeroFiscale");
        champSiret          = new JComboBox<String>();
        champSiret.addItem("Sélectionner un siret");
        champSiret.setName("champSiret");

        form.add(Utils.creerLabel("Référence :"));
        form.add(champReference);
        form.add(Utils.creerLabel("Date validité :"));
        form.add(champDateValidite);
        form.add(Utils.creerLabel("Type diagnostic :"));
        form.add(champTypeDiagnostic);
        form.add(Utils.creerLabel("Numéro fiscale :"));
        form.add(champNumeroFiscale);
        form.add(Utils.creerLabel("SIRET :"));
        form.add(champSiret);

        add(new JScrollPane(
                form,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        ), BorderLayout.CENTER);

        JPanel boutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 12));
        boutons.setOpaque(false);

        btnEnregistrer = Utils.creerBouton("Enregistrer");
        btnAnnuler = Utils.creerBouton("Annuler");

        btnEnregistrer.setName("btnEnregistrerDiagnostic");
        btnAnnuler.setName("btnAnnulerDiagnostic");

        boutons.add(btnEnregistrer);
        boutons.add(btnAnnuler);
        add(boutons, BorderLayout.SOUTH);

        setSize(500, 480);  // ← Définit la taille fixe
        setLocationRelativeTo(parent);
    }
    
    /**
     * Remplit la ComboBox des biens louables avec leurs numéros fiscaux.
     * Méthode appelée par le contrôleur après récupération des biens en base.
     */
    public void remplirBienLouable(List<BienLouable> liste) {
        champNumeroFiscale.removeAllItems();
        champNumeroFiscale.addItem("Sélectionner un numero fiscale");

        for (BienLouable bl : liste) {
            champNumeroFiscale.addItem(bl.getNumero_fiscale());
        }
    }

    /**
     * Remplit la ComboBox des entreprises avec leurs numéros SIRET.
     * Appelée par le contrôleur pour permettre la sélection d'une entreprise.
     */
    public void remplirEntreprise(List<Entreprise> liste) {
        champSiret.removeAllItems();
        champSiret.addItem("Sélectionner un siret");
        for (Entreprise e : liste) {
            champSiret.addItem(e.getSiret());
        }
    }

    public JButton getBtnEnregistrer() {
        return btnEnregistrer;
    }

    public JButton getBtnAnnuler() {
        return btnAnnuler;
    }

    public String getReference() {
        return champReference.getText().trim();
    }

    public String getDateValidite() {
        return champDateValidite.getText().trim();
    }

    public String getTypeDiagnostic() {
        return champTypeDiagnostic.getText().trim();
    }

    public String getNumeroFiscale() {
        return (String) champNumeroFiscale.getSelectedItem();
    }

    public String getSiret() {
        return (String) champSiret.getSelectedItem();
    }

    /**
     * Retourne l'index du bien sélectionné sans l'item par défaut.
     * Le -1 permet d'aligner l'index avec la liste récupérée depuis la base.
     */
    public int getNumeroFiscaleIndex() { 
        return champNumeroFiscale.getSelectedIndex() - 1; 
    }

    /**
     * Retourne l'index de l'entreprise sélectionnée sans l'item par défaut.
     * Utilisé par le contrôleur pour récupérer la bonne entreprise.
     */
    public int getSiretIndex() { 
        return champSiret.getSelectedIndex() - 1; 
    }

    /**
     * Affiche un message d'erreur sous forme de popup.
     * Utilisé en cas de saisie invalide ou de problème lors de l'ajout.
     */
    public void afficherErreur(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Erreur", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Affiche un message de confirmation après une action réussie.
     * Utilisé lorsque le diagnostic a bien été ajouté.
     */
    public void afficherSucces(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Succès", JOptionPane.INFORMATION_MESSAGE);
    }
}
