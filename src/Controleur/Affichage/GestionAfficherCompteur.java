package Controleur.Affichage;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import Modele.Compteur;
import Modele.dao.DaoCompteur;
import Vue.Affichage.FenetreAfficherCompteur;
import Vue.Affichage.FenetreAfficherReleveCompteur;

public class GestionAfficherCompteur implements ActionListener {

    private FenetreAfficherCompteur vue;

    public GestionAfficherCompteur(FenetreAfficherCompteur vue) {
        this.vue = vue;

        chargerCompteurEnArrierePlan();
    }

    private void chargerCompteurEnArrierePlan() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                chargerCompteurs();
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Component src = (Component) e.getSource();
        String name = src.getName();
        if (name == null) return;

            switch (name) {
            
            case "btnArchiverCompteur":
                archiverCompteurSelectionne();
                break;
            
            case "btnReleveCompteur":
            	FenetreAfficherReleveCompteur far = new FenetreAfficherReleveCompteur(vue.getGfp(), vue.getDesktop());
            	vue.getDesktop().add(far);
                far.setVisible(true);
                break;
                
            case "btnFermerCompteur":
                vue.dispose();
                break;
            }
       
    }

    // ===================== LOGIQUE PRINCIPALE =====================
    private void chargerCompteurs() throws SQLException {

        DaoCompteur dao = new DaoCompteur();
        List<Compteur> compteurs = dao.findAll();

        DefaultTableModel modelBat =
            (DefaultTableModel) vue.getTableauBatiment().getModel();
        DefaultTableModel modelBien =
            (DefaultTableModel) vue.getTableauBienLouable().getModel();

        modelBat.setRowCount(0);
        modelBien.setRowCount(0);

        for (Compteur c : compteurs) {

            if (c.getIdBatiment() != null) {
                modelBat.addRow(new Object[] {
                    c.getId_compteur(),
                    c.getType(),
                    c.getIndex(),
                    c.getId_variable(),
                    c.getIdBatiment()
                });
            }
            else if (c.getNumero_fiscale() != null) {
                modelBien.addRow(new Object[] {
                    c.getId_compteur(),
                    c.getType(),
                    c.getIndex(),
                    c.getId_variable(),
                    c.getNumero_fiscale()
                });
            }
        }
    }
    
    private void archiverCompteurSelectionne() {

        JTable tableBat = vue.getTableauBatiment();
        JTable tableBien = vue.getTableauBienLouable();

        int rowBat = tableBat.getSelectedRow();
        int rowBien = tableBien.getSelectedRow();

        if (rowBat == -1 && rowBien == -1) {
            JOptionPane.showMessageDialog(
                vue,
                "Veuillez sélectionner un compteur à archiver.",
                "Aucune sélection",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String idCompteur;

        if (rowBat != -1) {
            idCompteur = tableBat.getValueAt(rowBat, 0).toString();
        } else {
            idCompteur = tableBien.getValueAt(rowBien, 0).toString();
        }

        int confirm = JOptionPane.showConfirmDialog(
            vue,
            "Voulez-vous vraiment archiver ce compteur ?",
            "Confirmation",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            DaoCompteur dao = new DaoCompteur();
            dao.archiverById(idCompteur);   
            chargerCompteurs();

            JOptionPane.showMessageDialog(
                vue,
                "Compteur archivé avec succès.",
                "Archivage",
                JOptionPane.INFORMATION_MESSAGE
            );

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                vue,
                "Erreur lors de l'archivage du compteur.",
                "Erreur",
                JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }

}
