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
import Modele.CompteurBien;
import Modele.dao.DaoCompteurBien;
import Vue.Utils;

public class FenetreAfficherCompteurBien extends JInternalFrame {

    private static final long serialVersionUID = 1L;

    private JTable tableau;
    private JButton btnFermer;

    public FenetreAfficherCompteurBien(GestionFenetrePrincipale gfp) {
        super("Compteurs par bien louable", true, true, true, true);

        setSize(750, 400);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(Color.WHITE);

        // =====================
        // TITRE
        // =====================
        add(Utils.creerTitre("Consommation par bien louable"), BorderLayout.NORTH);

        // =====================
        // ZONE CENTRALE
        // =====================
        JPanel zoneCentrale = new JPanel();
        zoneCentrale.setLayout(new BorderLayout());
        zoneCentrale.setOpaque(false);

        DefaultTableModel model = Utils.creerTableModelNonEditable(
                new String[]{"Numéro fiscal", "Indice", "Index valeur", "Prix unitaire", "Type", 
                            "Consommation", "Prix total"}
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
            DaoCompteurBien dao = new DaoCompteurBien();
            DefaultTableModel model = (DefaultTableModel) tableau.getModel();
            model.setRowCount(0); // Vider le modèle

            for (CompteurBien compteur : dao.findAll()) {
                model.addRow(new Object[]{
                        compteur.getNumero_fiscale(),
                        compteur.getIndice(),
                        compteur.getIndex_val(),
                        compteur.getPrix_unitaire(),
                        compteur.getType(),
                        compteur.getConsommation(),
                        compteur.getPrix_total()
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
