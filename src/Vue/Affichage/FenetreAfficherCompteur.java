package Vue.Affichage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Controleur.GestionFenetrePrincipale;
import Controleur.Affichage.GestionAfficherCompteur;
import Vue.Utils;
import Vue.Affichage.FenetreAfficherCompteurLot;
import Vue.Affichage.FenetreAfficherCompteurBien;

public class FenetreAfficherCompteur extends JInternalFrame {

    private static final long serialVersionUID = 1L;

    private JTable tableauBatiment;
    private JTable tableauBienLouable;

    private JButton btnArchiver;
    private JButton btnReleve;
    private JButton btnFermer;

    private GestionAfficherCompteur gac;
    private GestionFenetrePrincipale gfp;
    private JDesktopPane desktop;
    
    // Variables pour détecter les double-clics manuels
    private long dernierClickBatiment = 0;
    private int dernierRowBatiment = -1;
    private long dernierClickBien = 0;
    private int dernierRowBien = -1;

    public FenetreAfficherCompteur(GestionFenetrePrincipale gfp, JDesktopPane desktop) {
        super("Liste des compteurs", true, true, true, true);

        this.gfp = gfp;
        this.desktop = desktop;

        setSize(750, 550);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(Color.WHITE);

        // =====================
        // CONTRÔLEUR (AVANT les boutons)
        // =====================
        gac = new GestionAfficherCompteur(this);

        // =====================
        // TITRE
        // =====================
        add(Utils.creerTitre("Tous les compteurs"), BorderLayout.NORTH);

        // =====================
        // ZONE CENTRALE
        // =====================
        JPanel zoneCentrale = new JPanel();
        zoneCentrale.setLayout(new BoxLayout(zoneCentrale, BoxLayout.Y_AXIS));
        zoneCentrale.setOpaque(false);
        zoneCentrale.add(Utils.creerEspacementVertical(10));

        // ===== TABLE COMPTEURS BÂTIMENT =====
        DefaultTableModel modelBat = Utils.creerTableModelNonEditable(
                new String[]{"ID compteur", "Type", "Index", "Id variable", "ID bâtiment"}
        );
        tableauBatiment = new JTable(modelBat);
        Utils.styliserTable(tableauBatiment);
        
        // MouseListener pour double-clic sur tableauBatiment
        tableauBatiment.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = tableauBatiment.getSelectedRow();
                long currentTime = System.currentTimeMillis();
                
                if (row == dernierRowBatiment && (currentTime - dernierClickBatiment) < 300) {
                    System.out.println("DEBUG: DOUBLE-CLIC tableauBatiment - Row=" + row);
                    if (row >= 0) {
                        try {
                            FenetreAfficherCompteurLot fenetre = new FenetreAfficherCompteurLot(gfp);
                            desktop.add(fenetre);
                            fenetre.setVisible(true);
                            fenetre.toFront();
                            System.out.println("DEBUG: Fenêtre compteur lot ouverte");
                            dernierClickBatiment = 0;
                        } catch (Exception ex) {
                            System.out.println("DEBUG: Erreur fenêtre compteur lot: " + ex.getMessage());
                            ex.printStackTrace();
                        }
                    }
                } else {
                    dernierClickBatiment = currentTime;
                    dernierRowBatiment = row;
                }
            }
        });

        JPanel blocBat = new JPanel(new BorderLayout());
        blocBat.setBackground(Color.WHITE);
        blocBat.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        blocBat.add(Utils.creerScrollPane(tableauBatiment, "Compteurs des bâtiments"), BorderLayout.CENTER);

        zoneCentrale.add(blocBat);
        zoneCentrale.add(Utils.creerEspacementVertical(15));

        // ===== TABLE COMPTEURS BIENS LOUABLES =====
        DefaultTableModel modelBien = Utils.creerTableModelNonEditable(
                new String[]{"ID compteur", "Type", "Index", "Id variable", "Numéro fiscal"}
        );
        tableauBienLouable = new JTable(modelBien);
        Utils.styliserTable(tableauBienLouable);
        
        // MouseListener pour double-clic sur tableauBienLouable
        tableauBienLouable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = tableauBienLouable.getSelectedRow();
                long currentTime = System.currentTimeMillis();
                
                if (row == dernierRowBien && (currentTime - dernierClickBien) < 300) {
                    System.out.println("DEBUG: DOUBLE-CLIC tableauBienLouable - Row=" + row);
                    if (row >= 0) {
                        try {
                            FenetreAfficherCompteurBien fenetre = new FenetreAfficherCompteurBien(gfp);
                            desktop.add(fenetre);
                            fenetre.setVisible(true);
                            fenetre.toFront();
                            System.out.println("DEBUG: Fenêtre compteur bien ouverte");
                            dernierClickBien = 0;
                        } catch (Exception ex) {
                            System.out.println("DEBUG: Erreur fenêtre compteur bien: " + ex.getMessage());
                            ex.printStackTrace();
                        }
                    }
                } else {
                    dernierClickBien = currentTime;
                    dernierRowBien = row;
                }
            }
        });

        JPanel blocBien = new JPanel(new BorderLayout());
        blocBien.setBackground(Color.WHITE);
        blocBien.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        blocBien.add(Utils.creerScrollPane(tableauBienLouable, "Compteurs des biens louables"), BorderLayout.CENTER);

        zoneCentrale.add(blocBien);

        add(zoneCentrale, BorderLayout.CENTER);

        // =====================
        // BOUTONS
        // =====================
        JPanel panelBoutons = Utils.creerPanelBoutons(FlowLayout.CENTER);

        btnArchiver = Utils.creerBoutonAvecAction(
                "Archiver", "btnArchiverCompteur", gac
        );
        btnReleve = Utils.creerBoutonAvecAction(
                "Releve", "btnReleveCompteur", gac
        );
        btnFermer = Utils.creerBoutonAvecAction(
                "Fermer", "btnFermerCompteur", gac
        );

        panelBoutons.add(btnArchiver);
        panelBoutons.add(btnReleve);
        panelBoutons.add(btnFermer);

        add(panelBoutons, BorderLayout.SOUTH);
    }

    // =====================
    // GETTERS
    // =====================
    public JTable getTableauBatiment() { return tableauBatiment; }
    public JTable getTableauBienLouable() { return tableauBienLouable; }
    public JButton getBtnArchiver() { return btnArchiver; }
    public JButton getBtnReleve() { return btnReleve; }
    public JButton getBtnFermer() { return btnFermer; }
    public JDesktopPane getDesktop() { return desktop; }
    public GestionFenetrePrincipale getGfp() { return gfp; }
}
