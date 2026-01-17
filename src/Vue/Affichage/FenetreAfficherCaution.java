package Vue.Affichage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

import Controleur.Affichage.GestionAfficherCaution;
import Vue.Utils;

public class FenetreAfficherCaution extends JInternalFrame {

    private static final long serialVersionUID = 1L;

    private JTable tableCaution;
    private DefaultTableModel model;

    private JButton btnFermer;
    private JButton btnAjouterCaution;
    private JButton btnModifierCaution;
    private JButton btnArchiverCaution;

    private GestionAfficherCaution gac;

    public FenetreAfficherCaution(JDesktopPane desktop) {
        super("Liste des cautions", true, true, true, true);
        setSize(900, 500);

        this.gac = new GestionAfficherCaution(this, desktop);

        // ======== PANNEAU PRINCIPAL ==========  
        JPanel panneauPrincipal = new JPanel(new BorderLayout(20, 20));
        panneauPrincipal.setBackground(Color.WHITE);
        panneauPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        setContentPane(panneauPrincipal);

        // ======== TITRE ==========  
        panneauPrincipal.add(Utils.creerTitre("Cautions enregistrées"), BorderLayout.NORTH);

        // ======== TABLE ========  
        String[] colonnes = {
            "ID Caution", "Nom", "Prénom", "Ville", "Téléphone", "Mail", "ID Contrat"
        };

        model = Utils.creerTableModelNonEditable(colonnes);
        tableCaution = new JTable(model);
        Utils.styliserTable(tableCaution);

        panneauPrincipal.add(Utils.creerScrollPane(tableCaution, "Résultats"), BorderLayout.CENTER);

        // ======== BOUTONS ========  
        JPanel panelBoutons = Utils.creerPanelBoutons(FlowLayout.CENTER);

        btnAjouterCaution = Utils.creerBoutonAvecAction("Ajouter Caution", "btnAjouterCaution", gac);
        btnModifierCaution = Utils.creerBoutonAvecAction("Modifier Caution", "btnModifierCaution", gac);
        btnArchiverCaution = Utils.creerBoutonAvecAction("Archiver Caution", "btnArchiverCaution", gac);
        btnFermer = Utils.creerBoutonAvecAction("Fermer", "btnFermerCaution", gac);

        // Taille uniforme des boutons
        Utils.setTailleBouton(btnAjouterCaution, 140, 25);
        Utils.setTailleBouton(btnModifierCaution, 140, 25);
        Utils.setTailleBouton(btnArchiverCaution, 140, 25);
        Utils.setTailleBouton(btnFermer, 140, 25);

        panelBoutons.add(btnAjouterCaution);
        panelBoutons.add(btnModifierCaution);
        panelBoutons.add(btnArchiverCaution);
        panelBoutons.add(btnFermer);

        panneauPrincipal.add(panelBoutons, BorderLayout.SOUTH);

        // ======== CHARGEMENT DES DONNÉES ========  
        try {
            gac.chargerCautions();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ======== GETTERS ========  
    public JTable getTableCaution() { return tableCaution; }
    public DefaultTableModel getModel() { return model; }
    public JButton getBtnFermer() { return btnFermer; }
    public JButton getBtnAjouterCaution() { return btnAjouterCaution; }
    public JButton getBtnModifierCaution() { return btnModifierCaution; }
    public JButton getBtnArchiverCaution() { return btnArchiverCaution; }
    public GestionAfficherCaution getGac() { return gac; }
}
