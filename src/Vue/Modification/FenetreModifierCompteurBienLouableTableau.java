package Vue.Modification;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import Controleur.Modifier.GestionModifierCompteurBienLouableTableau;
import Modele.BienLouable;
import Vue.Utils;

public class FenetreModifierCompteurBienLouableTableau extends JInternalFrame {

    private static final long serialVersionUID = 1L;

    private JTable table;
    private DefaultTableModel model;
    private JButton btnModifier, btnSupprimer, btnFermer;
    private BienLouable bien;
    private JDesktopPane desktop;
    
    /**
     * Initialise la fenêtre interne affichant le tableau des compteurs
     * d'un bien louable, configure les boutons pour modifier, supprimer ou fermer,
     * et connecte le tableau au contrôleur dédié pour gérer les actions.
     */


    public FenetreModifierCompteurBienLouableTableau(BienLouable bien, JDesktopPane desktop) {
        super("Compteurs du bien " + bien.getNumero_fiscale(), true, true, true, true);
        this.bien = bien;
        this.desktop = desktop;


        setSize(650, 350);
        setLayout(new BorderLayout(10, 10));

        // -------- TABLE --------
        model = new DefaultTableModel(
            new Object[]{"ID", "Type", "Index", "Variable"}, 0
        );
        table = new JTable(model);
        Utils.styliserTable(table);

        JScrollPane scroll = Utils.creerScrollPane(table, "Liste des compteurs");
        add(scroll, BorderLayout.CENTER);

        // -------- BOUTONS --------
        btnModifier = Utils.creerBouton("Modifier");
        btnSupprimer = Utils.creerBouton("Supprimer");
        btnFermer   = Utils.creerBouton("Fermer");
        
        btnModifier.setName("BTN_MODIFIER");
        btnSupprimer.setName("BTN_SUPPRIMER");
        btnFermer.setName("BTN_FERMER");

        JPanel panelBtn = Utils.creerPanelBoutons(FlowLayout.CENTER);
        panelBtn.add(btnModifier);
        panelBtn.add(btnSupprimer);
        panelBtn.add(btnFermer);

        add(panelBtn, BorderLayout.SOUTH);

        // -------- CONTROLEUR --------
        new GestionModifierCompteurBienLouableTableau(this);
    }

    // -------- GETTERS --------
    public JTable getTableCompteur() { return table; }
    public DefaultTableModel getModel() { return model; }
    public JButton getBtnModifier() { return btnModifier; }
    public JButton getBtnSupprimer() { return btnSupprimer; }
    public JButton getBtnFermer() { return btnFermer; }
    public BienLouable getBien() { return bien; }
    public JDesktopPane getDesktop() {
        return desktop;
    }

}
