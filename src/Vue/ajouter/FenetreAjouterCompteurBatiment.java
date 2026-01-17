package Vue.ajouter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JTextField;

import Controleur.Ajouter.GestionAjouterCompteurBatiment;
import Modele.Batiment;
import Modele.Variable;
import Modele.dao.DaoVariable;
import Vue.Utils;

public class FenetreAjouterCompteurBatiment extends JDialog {

    private static final long serialVersionUID = 1L;

    private JTextField champId, champIndex;
    private JComboBox<String> comboType, comboVariable;
    private JButton btnAjouter, btnAnnuler;
    private Batiment batiment;

    
    /**
     * Fenetre_AjouterCompteurBatiment : JDialog modal pour ajouter un compteur à un bâtiment existant.
     *
     * Cette fenêtre permet de saisir les informations d'un compteur : 
     * ID, type (Eau, Électricité, Gaz), index initial et variable.
     * Le bâtiment associé est affiché mais non modifiable.
     *
     * Les boutons "Ajouter" et "Annuler" permettent respectivement de créer le compteur 
     * via le contrôleur GestionAjouterCompteurBatiment ou de fermer la fenêtre.
     *
     * @param parent La fenêtre parente (Frame ou Dialog)
     * @param batiment Le bâtiment auquel le compteur sera rattaché (non null)
     */
    public FenetreAjouterCompteurBatiment(Frame parent, Batiment batiment) {
        super(parent, "Ajouter un compteur au bâtiment", true);
        this.batiment = batiment;

        // Définir layout et apparence de base
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(Color.WHITE);

        // ---------------- Titre ----------------
        JLabel titre = Utils.creerTitre("Nouveau compteur pour bâtiment");
        add(titre, BorderLayout.NORTH);

        // ---------------- Formulaire ----------------
        JPanel form = new JPanel(new GridLayout(0, 2, 20, 15));
        form.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        form.setOpaque(false);

        champId = Utils.creerTextField("ID compteur", 150, 25);
        comboType = Utils.creerComboBox(150, 25);
        comboType.addItem("EAU");
        comboType.addItem("ELECTRICITE");
        comboType.addItem("GAZ");
        champIndex = Utils.creerTextField("Index initial", 150, 25);
        
        // Charger les variables depuis la base de données
        comboVariable = Utils.creerComboBox(150, 25);
        chargerVariables();

        form.add(Utils.creerLabel("ID compteur :"));
        form.add(champId);
        form.add(Utils.creerLabel("Type :"));
        form.add(comboType);
        form.add(Utils.creerLabel("Index initial :"));
        form.add(champIndex);
        form.add(Utils.creerLabel("Variable :"));
        form.add(comboVariable);

        if (batiment != null) {
            JTextField champBat = Utils.creerTextField("Bâtiment associé", 150, 25);
            champBat.setText(batiment.getIdBatiment());
            champBat.setEditable(false);
            form.add(Utils.creerLabel("Bâtiment :"));
            form.add(champBat);
        }

        add(form, BorderLayout.CENTER);

        // ---------------- Boutons ----------------
        JPanel boutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        boutons.setOpaque(false);

        // Appliquer une icône aux boutons et améliorer leur apparence
        btnAjouter = Utils.creerBoutonPrimaire("Ajouter");
        btnAnnuler = Utils.creerBouton("Annuler");

        // Améliorer la taille des boutons
        btnAjouter.setPreferredSize(new Dimension(120, 40));
        btnAnnuler.setPreferredSize(new Dimension(120, 40));

        boutons.add(btnAjouter);
        boutons.add(btnAnnuler);

        add(boutons, BorderLayout.SOUTH);

        // Définir la taille APRÈS avoir ajouté tous les composants
        setSize(700, 450);
        setLocationRelativeTo(parent);

        // ---------------- Contrôleur ----------------
        new GestionAjouterCompteurBatiment(this);
    }

    // ---------------- GETTERS ----------------
    public JTextField getChampId() { return champId; }
    public JTextField getChampIndex() { return champIndex; }
    public JComboBox<String> getComboVariable() { return comboVariable; }
    public JComboBox<String> getComboType() { return comboType; }
    public JButton getBtnAjouter() { return btnAjouter; }
    public JButton getBtnAnnuler() { return btnAnnuler; }
    public Batiment getBatiment() { return batiment; }
    
    /**
     * Retourne l'ID du compteur saisi (sans espaces).
     * Utilisé par le contrôleur pour créer le compteur.
     */
    public String getIdCompteur() {
        return champId.getText().trim();
    }

    /**
     * Retourne le type choisi dans la ComboBox (Eau / Électricité / Gaz).
     * Permet au contrôleur de récupérer le bon type de compteur.
     */
    public String getTypeCompteur() {
        return (String) comboType.getSelectedItem();
    }

    /**
     * Retourne l'index initial saisi (sans espaces).
     * Sert au contrôleur pour enregistrer la valeur de départ du compteur.
     */
    public String getIndex() {
        return champIndex.getText().trim();
    }

    /**
     * Retourne la variable sélectionnée (sans espaces).
     * Utilisé si on veut lier le compteur à une variable.
     */
    public String getIdVariable() {
        Object selected = comboVariable.getSelectedItem();
        return selected != null ? selected.toString().trim() : "";
    }

    /**
     * Retourne l'identifiant du bâtiment associé.
     * Comme le bâtiment est déjà connu, on récupère directement dans l'objet batiment.
     */
    public String getId() {
        return batiment.getIdBatiment();
    }
    
    /**
     * Charge les variables depuis la base de données et les affiche dans le ComboBox.
     */
    private void chargerVariables() {
        try {
            DaoVariable daoVar = new DaoVariable();
            List<Variable> variables = daoVar.findAll();
            
            for (Variable var : variables) {
                comboVariable.addItem(var.getId_Variable());
            }
            
            if (comboVariable.getItemCount() == 0) {
                comboVariable.addItem("Aucune variable disponible");
            }
        } catch (Exception e) {
            System.err.println("[ERROR] Erreur lors du chargement des variables: " + e.getMessage());
            e.printStackTrace();
            comboVariable.addItem("Erreur de chargement");
        }
    }

    // ---------------- Dialogues ----------------
    /**
     * Affiche un message d'erreur sous forme de popup.
     * Utilisé en cas de saisie invalide ou d'erreur lors de l'ajout.
     */
    public void afficherErreur(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Erreur", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Affiche un message de confirmation après une action réussie.
     * Utilisé quand le compteur a bien été ajouté.
     */
    public void afficherSucces(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Succès", JOptionPane.INFORMATION_MESSAGE);
    }
}
