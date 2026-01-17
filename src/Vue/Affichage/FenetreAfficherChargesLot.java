package Vue.Affichage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Controleur.GestionFenetrePrincipale;
import Modele.ChargesLot;
import Modele.dao.DaoChargesLot;
import Vue.Utils;

public class FenetreAfficherChargesLot extends JInternalFrame {

    private static final long serialVersionUID = 1L;

    private JTable tableau;
    private JButton btnFermer;

    public FenetreAfficherChargesLot(GestionFenetrePrincipale gfp) {
        super("Charges par lot et année", true, true, true, true);

        setSize(700, 400);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(Color.WHITE);

        // =====================
        // TITRE
        // =====================
        add(Utils.creerTitre("Charges par lot et année"), BorderLayout.NORTH);

        // =====================
        // ZONE CENTRALE
        // =====================
        JPanel zoneCentrale = new JPanel();
        zoneCentrale.setLayout(new BorderLayout());
        zoneCentrale.setOpaque(false);

        DefaultTableModel model = Utils.creerTableModelNonEditable(
                new String[]{"Numéro fiscal", "Année", "Type", "Taxe ordinaires lot", "Taxes autres lot", "Taxe OM lot"}
        );
        tableau = new JTable(model);
        Utils.styliserTable(tableau);

        // Charger les données
        chargerDonnees();

        zoneCentrale.add(Utils.creerScrollPane(tableau, ""), BorderLayout.CENTER);
        add(zoneCentrale, BorderLayout.CENTER);

        // =====================
        // BOUTONS
        // =====================
        JPanel panelBoutons = Utils.creerPanelBoutons(FlowLayout.CENTER);
        btnFermer = Utils.creerBouton("Fermer");
        btnFermer.addActionListener(e -> dispose());
        panelBoutons.add(btnFermer);

        add(panelBoutons, BorderLayout.SOUTH);
    }

    private void chargerDonnees() {
        try {
            DaoChargesLot dao = new DaoChargesLot();
            DefaultTableModel model = (DefaultTableModel) tableau.getModel();
            model.setRowCount(0); // Vider le modèle

            for (ChargesLot charge : dao.findAll()) {
                model.addRow(new Object[]{
                        charge.getNumero_fiscale(),
                        charge.getAnnee(),
                        charge.getType(),
                        charge.getTaxe_ordinaires_lot(),
                        charge.getTaxes_autres_lot(),
                        charge.getTaxe_om_lot()
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public JTable getTableau() {
        return tableau;
    }
}
