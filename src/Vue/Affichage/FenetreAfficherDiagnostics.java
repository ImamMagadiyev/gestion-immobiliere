package Vue.Affichage;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

import Controleur.Affichage.GestionAfficherDiagnostics;
import Vue.Utils;

public class FenetreAfficherDiagnostics extends JInternalFrame {

    private static final long serialVersionUID = 1L;

    private JTable tableDiagnostics;
    private JButton btnFermer;
    private JButton btnAjouterDiagnostic;
    private JButton btnModifierDiagnostic;
    private JButton btnArchiverDiagnostic;

    private GestionAfficherDiagnostics gad;

    /**
     * Fenêtre interne affichant la liste des diagnostics réalisés.
     * Permet d'ajouter, modifier, archiver ou fermer la fenêtre.
     * Utilise GestionAfficherDiagnostics pour gérer les actions et le chargement des données.
     */
    public FenetreAfficherDiagnostics(JDesktopPane desktop) {
        super("Liste des diagnostics", true, true, true, true);

        setSize(800, 500);

        this.gad = new GestionAfficherDiagnostics(this, desktop);

        // ======== PANNEAU PRINCIPAL ========
        JPanel panneauPrincipal = Utils.creerPanelColore(Color.WHITE);
        panneauPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        setContentPane(panneauPrincipal);
        panneauPrincipal.setLayout(new BorderLayout(0, 20));

        // ======== TITRE ========
        panneauPrincipal.add(Utils.creerTitre("Diagnostics effectués"), BorderLayout.NORTH);


        // ======== TABLEAU ========
        String[] colonnes = {"Référence",  "Date validité", "Type", "Numéro fiscal", "Entreprise (SIRET)"};
        tableDiagnostics = Utils.creerTableauAvecScroll(colonnes, "Résultats", panneauPrincipal);


        // ======== PANEL BOUTONS ========
        JPanel panelBoutons = Utils.creerPanelBoutons(FlowLayout.CENTER);

        // On utilise créerBoutonAvecAction pour éviter de répéter addActionListener et setName
        btnAjouterDiagnostic = Utils.creerBoutonAvecAction("Ajouter Diagnostic", "btnAjouterDiagnostic", gad);
        btnModifierDiagnostic = Utils.creerBoutonAvecAction("Modifier Diagnostic", "btnModifierDiagnostic", gad);
        btnArchiverDiagnostic = Utils.creerBoutonAvecAction("Archiver Diagnostic", "btnArchiverDiagnostic", gad);
        btnFermer = Utils.creerBoutonAvecAction("Fermer", "btnFermerDiagnostics", gad);

        // Ajuster la taille si besoin (utilise Utils.setTailleBouton)
        Utils.setTailleBouton(btnAjouterDiagnostic, 160, 25);
        Utils.setTailleBouton(btnModifierDiagnostic, 160, 25);
        Utils.setTailleBouton(btnArchiverDiagnostic, 160, 25);

        panelBoutons.add(btnAjouterDiagnostic);
        panelBoutons.add(btnModifierDiagnostic);
        panelBoutons.add(btnArchiverDiagnostic);
        panelBoutons.add(btnFermer);

        panneauPrincipal.add(panelBoutons, BorderLayout.SOUTH);

        // ======== CHARGEMENT DES DONNÉES ========
        try {
            gad.chargerDiagnostics();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ======== GETTERS ========
    public JButton getBtnFermer() { return btnFermer; }
    public JButton getBtnAjouterDiagnostic() { return btnAjouterDiagnostic; }
    public JButton getBtnModifierDiagnostic() { return btnModifierDiagnostic; }
    public JButton getBtnArchiverDiagnostic() { return btnArchiverDiagnostic; }
    public JTable getTableDiagnostics() { return tableDiagnostics; }
    public GestionAfficherDiagnostics getGad() { return gad; }
}
