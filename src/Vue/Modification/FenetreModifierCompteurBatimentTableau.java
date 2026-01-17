package Vue.Modification;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Controleur.Modifier.GestionModifierCompteurBatimentTableau;
import Modele.Batiment;
import Vue.Utils;

public class FenetreModifierCompteurBatimentTableau extends JInternalFrame {

    private static final long serialVersionUID = 1L;

    private JTable table;
    private DefaultTableModel model;
    private JButton btnModifier, btnSupprimer, btnFermer;
    private Batiment bat;
    private JDesktopPane desktop;

    /**
     * Initialise la fenêtre interne affichant le tableau des compteurs
     * d'un bâtiment, configure les boutons pour modifier, supprimer ou fermer,
     * et connecte le tableau au contrôleur dédié pour gérer les actions.
     */

    public FenetreModifierCompteurBatimentTableau(Batiment bat, JDesktopPane desktop) {
        super("Compteurs du bien " + bat.getIdBatiment(), true, true, true, true);
        this.bat = bat;
        this.desktop = desktop;


        setSize(650, 350);
        setLayout(new BorderLayout(10, 10));

        // -------- TABLE --------
        model = new DefaultTableModel(
            new Object[]{"ID", "Type", "Index", "Variable"}, 0
        ) {
        		@Override
                public boolean isCellEditable(int row, int column) {
                    return false; 
                }
        };
        
        
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
        new GestionModifierCompteurBatimentTableau(this);
    }

    // -------- GETTERS --------
    public JTable getTableCompteur() { return table; }
    public DefaultTableModel getModel() { return model; }
    public JButton getBtnModifier() { return btnModifier; }
    public JButton getBtnSupprimer() { return btnSupprimer; }
    public JButton getBtnFermer() { return btnFermer; }
    public Batiment getBatiment() { return bat; }
    public JDesktopPane getDesktop() {
        return desktop;
    }

}
