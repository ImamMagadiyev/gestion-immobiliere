package Vue.Modification;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Modele.Compteur;
import Modele.dao.DaoCompteur;
import Vue.Utils;

public class FenetreModifierCompteurBatiment extends JDialog {

    private static final long serialVersionUID = 1L;

    private JComboBox<String> comboType;
    private JTextField txtIndex, txtVariable;
    private JButton btnValider, btnAnnuler;
    private Compteur compteur;
    private Runnable callback;

    /**
     * Initialise la fenêtre pour modifier un compteur d'un bâtiment,
     * préremplit les champs avec les données actuelles du compteur,
     * et configure les boutons pour valider ou annuler la modification.
     */
    public FenetreModifierCompteurBatiment(
            Compteur compteur,
            Runnable callback,
            Frame parent
    ) {
        super(parent, "Modifier compteur " + compteur.getId_compteur(), true);
        this.compteur = compteur;
        this.callback = callback;

        setSize(420, 260);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(parent);

        // -------- FORMULAIRE --------
        JPanel panelForm = new JPanel(new GridLayout(3, 1, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        panelForm.setOpaque(false);

        // -------- TYPE --------
        comboType = Utils.creerComboBox(200, 28);
        comboType.addItem("Eau");
        comboType.addItem("Gaz");
        comboType.addItem("Électricité");
        comboType.setSelectedItem(compteur.getType());

        JPanel panelType = new JPanel(new BorderLayout(5, 5));
        panelType.setOpaque(false);
        panelType.add(Utils.creerLabel("Type"), BorderLayout.NORTH);
        panelType.add(comboType, BorderLayout.CENTER);

        panelForm.add(panelType);

        // INDEX
        txtIndex = Utils.creerTextField(null, 200, 28);
        txtIndex.setText(String.valueOf(compteur.getIndex()));
        panelForm.add(Utils.creerChampAvecLabel("Index", txtIndex));

        // VARIABLE
        txtVariable = Utils.creerTextField(null, 200, 28);
        txtVariable.setText(compteur.getId_variable());
        panelForm.add(Utils.creerChampAvecLabel("Variable", txtVariable));

        add(panelForm, BorderLayout.CENTER);

        // -------- BOUTONS --------
        btnValider = Utils.creerBoutonPrimaire("Valider");
        btnAnnuler = Utils.creerBouton("Annuler");

        JPanel panelBtn = Utils.creerPanelBoutons(FlowLayout.CENTER);
        panelBtn.add(btnValider);
        panelBtn.add(btnAnnuler);

        add(panelBtn, BorderLayout.SOUTH);

        // -------- ACTIONS --------
        btnValider.addActionListener(e -> valider());
        btnAnnuler.addActionListener(e -> dispose());
    }

    /**
     * Valide la modification du compteur.
     * 
     * Met à jour l'objet Compteur avec les nouvelles valeurs saisies,
     * enregistre les changements en base via DaoCompteur,
     * puis exécute le callback pour rafraîchir l'affichage parent.
     */
    private void valider() {
        try {
            compteur.setType(comboType.getSelectedItem().toString());
            compteur.setIndex(Double.parseDouble(txtIndex.getText()));
            compteur.setId_variable(txtVariable.getText());

            new DaoCompteur().update(compteur);

            callback.run();
            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
