package Vue.Affichage;

import javax.swing.*;
import java.awt.*;

import Controleur.Affichage.GestionAfficherReleveCompteur;
import Controleur.GestionFenetrePrincipale;
import Vue.Utils;

public class FenetreAfficherReleveCompteur extends JInternalFrame {

    private static final long serialVersionUID = 1L;

    private JTable tableauReleves;
    private JButton btnAjouter, btnSupprimer, btnFermer;
    private JDesktopPane desktop;
    private GestionFenetrePrincipale gfp;
    private GestionAfficherReleveCompteur grc;

    public FenetreAfficherReleveCompteur(GestionFenetrePrincipale gfp, JDesktopPane desktop) {
        super("Liste des relevés de compteur", true, true, true, true);

        this.gfp = gfp;
        this.desktop = desktop;

        setSize(750, 550);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(Color.WHITE);

        // ===== TITRE =====
        JLabel titre = Utils.creerTitre("Tous les relevés de compteur");
        add(titre, BorderLayout.NORTH);

        // ===== ZONE CENTRALE =====
        JPanel zoneCentrale = new JPanel(new BorderLayout());
        zoneCentrale.setOpaque(false);

        // ===== TABLEAU AVEC SCROLL =====
        String[] colonnes = { "ID compteur", "Date relevé", "Index" };
        tableauReleves = Utils.creerTableauAvecScroll(colonnes, "Relevés de compteur", zoneCentrale);

        add(zoneCentrale, BorderLayout.CENTER);

        // ===== PANEL BOUTONS =====
        JPanel panelBoutons = Utils.creerPanelBoutons(FlowLayout.CENTER);

        grc = new GestionAfficherReleveCompteur(this);

        btnAjouter = Utils.creerBoutonAvecAction("Ajouter", "btnAjouterReleve", grc);
        btnSupprimer = Utils.creerBoutonAvecAction("Supprimer", "btnSupprimerReleve", grc);
        btnFermer = Utils.creerBoutonAvecAction("Fermer", "btnFermerReleve", grc);

        panelBoutons.add(btnAjouter);
        panelBoutons.add(btnSupprimer);
        panelBoutons.add(btnFermer);

        add(panelBoutons, BorderLayout.SOUTH);
    }

    // ===== GETTERS =====
    public JTable getTableauReleves() { return tableauReleves; }
    public JButton getBtnAjouter() { return btnAjouter; }
    public JButton getBtnSupprimer() { return btnSupprimer; }
    public JButton getBtnFermer() { return btnFermer; }
    public JDesktopPane getDesktop() { return desktop; }
    public GestionFenetrePrincipale getGfp() { return gfp; }
    public GestionAfficherReleveCompteur getGrc() { return grc; }
}
