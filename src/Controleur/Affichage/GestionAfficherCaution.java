package Controleur.Affichage;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import Controleur.Ajouter.GestionAjouterCaution;
import Controleur.Modifier.GestionModifierCaution;
import Modele.Caution;
import Modele.dao.DaoCaution;
import Vue.Affichage.FenetreAfficherCaution;
import Vue.Modification.FenetreModifierCaution;
import Vue.ajouter.FenetreAjouterCaution;

public class GestionAfficherCaution implements ActionListener {

    private FenetreAfficherCaution vue;
    private JDesktopPane desktop;

    public GestionAfficherCaution(FenetreAfficherCaution vue, JDesktopPane desktop) {
        this.vue = vue;
        this.desktop = desktop;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object src = e.getSource();
        if (!(src instanceof Component)) return;

        String name = ((Component) src).getName();
        if (name == null) return;

        switch (name) {

            case "btnAjouterCaution":
                // Vérifier qu'il y a des contrats de location avant d'ouvrir la fenêtre
                try {
                    Modele.dao.DaoContratDeLocation daoContrat = new Modele.dao.DaoContratDeLocation();
                    java.util.List<Modele.ContratDeLocation> listeContrats = daoContrat.findAll();
                    
                    if (listeContrats.isEmpty()) {
                        javax.swing.JOptionPane.showMessageDialog(vue, "Aucun contrat de location trouvé dans la base de données. Veuillez d'abord créer des contrats de location.", "Information", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }
                } catch (Exception ex) {
                    javax.swing.JOptionPane.showMessageDialog(vue, "Erreur lors de la vérification des contrats : " + ex.getMessage(), "Erreur", javax.swing.JOptionPane.ERROR_MESSAGE);
                    break;
                }
                
                // Ouvrir la fenêtre seulement si des contrats existent
                Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(vue);
                FenetreAjouterCaution fac = new FenetreAjouterCaution(parentFrame);
                new GestionAjouterCaution(fac, this);
                fac.setVisible(true);
                break;

            case "btnModifierCaution":
                modifierCaution();
                break;

            case "btnArchiverCaution":
                archiverCaution();
                break;

            case "btnFermerCaution":
                vue.dispose();
                break;
        }
    }

    // ===================== CHARGEMENT =====================
    public void chargerCautions() throws SQLException {

        DaoCaution dao = new DaoCaution();
        List<Caution> cautions = dao.findAll();

        DefaultTableModel model = vue.getModel();
        model.setRowCount(0);

        for (Caution c : cautions) {
            model.addRow(new Object[] {
                c.getId_caution(),
                c.getNom(),
                c.getPrenom(),
                c.getVille(),
                c.getNumeroTel(),
                c.getAdresseElectronique(),
                c.getIdContrat()
            });
        }
    }

    // ===================== MODIFIER =====================
    private void modifierCaution() {

        JTable table = vue.getTableCaution();
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                vue,
                "Veuillez sélectionner une caution à modifier.",
                "Aucune sélection",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String idCaution = table.getValueAt(row, 0).toString();

        try {
            DaoCaution dao = new DaoCaution();
            Caution caution = dao.findById(idCaution);

            if (caution == null) {
                JOptionPane.showMessageDialog(
                    vue,
                    "Caution introuvable dans la base.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            Frame parentFrame2 = (Frame) SwingUtilities.getWindowAncestor(vue);
            FenetreModifierCaution fmc =
                new FenetreModifierCaution(parentFrame2, caution);
            new GestionModifierCaution(fmc, caution, this);

            fmc.setVisible(true);

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                vue,
                "Erreur lors de la récupération de la caution : " + ex.getMessage(),
                "Erreur",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ===================== ARCHIVER =====================
    private void archiverCaution() {
        JTable table = vue.getTableCaution();
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                vue,
                "Veuillez sélectionner une caution à archiver.",
                "Aucune sélection",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String idCaution = table.getValueAt(row, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(
            vue,
            "Voulez-vous vraiment archiver cette caution ?",
            "Confirmation",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            DaoCaution dao = new DaoCaution();
            dao.archiverById(idCaution);

            chargerCautions();

            JOptionPane.showMessageDialog(
                vue,
                "Caution archivée avec succès.",
                "Archivage",
                JOptionPane.INFORMATION_MESSAGE
            );

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                vue,
                "Erreur lors de l'archivage de la caution.",
                "Erreur",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
