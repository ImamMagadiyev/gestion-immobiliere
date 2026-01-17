package Controleur.Affichage;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import Modele.ReleveCompteur;
import Modele.dao.DaoReleveCompteur;
import Vue.Affichage.FenetreAfficherReleveCompteur;
import Vue.ajouter.FenetreAjouterReleveCompteur;

public class GestionAfficherReleveCompteur implements ActionListener {

    private FenetreAfficherReleveCompteur vue;

    public GestionAfficherReleveCompteur(FenetreAfficherReleveCompteur vue) {
        this.vue = vue;

        chargerRelevesEnArrierePlan();
    }

    private void chargerRelevesEnArrierePlan() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                chargerReleves();
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
            
            case "btnAjouterReleve":
                // Vérifier qu'il y a des compteurs avant d'ouvrir la fenêtre
                try {
                    Modele.dao.DaoCompteur daoCompteur = new Modele.dao.DaoCompteur();
                    java.util.List<Modele.Compteur> listeCompteurs = daoCompteur.findAll();
                    
                    if (listeCompteurs.isEmpty()) {
                        javax.swing.JOptionPane.showMessageDialog(vue, "Aucun compteur trouvé dans la base de données. Veuillez d'abord créer des compteurs.", "Information", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }
                } catch (Exception ex) {
                    javax.swing.JOptionPane.showMessageDialog(vue, "Erreur lors de la vérification des compteurs : " + ex.getMessage(), "Erreur", javax.swing.JOptionPane.ERROR_MESSAGE);
                    break;
                }
                
                // Ouvrir la fenêtre seulement si des compteurs existent
                ajouterReleve();
                break;
            
            case "btnSupprimerReleve":
                supprimerReleveSelectionnee();
                break;
                
            case "btnFermerReleve":
                vue.dispose();
                break;
            }
       
    }

    // ===================== LOGIQUE PRINCIPALE =====================
    private void chargerReleves() throws SQLException {

        DaoReleveCompteur dao = new DaoReleveCompteur();
        List<ReleveCompteur> releves = dao.findAll();

        DefaultTableModel model =
            (DefaultTableModel) vue.getTableauReleves().getModel();

        model.setRowCount(0);

        for (ReleveCompteur r : releves) {
            model.addRow(new Object[] {
                r.getId_compteur(),
                r.getDate_releve(),
                r.getIndice()
            });
        }
    }

    private void ajouterReleve() {
        FenetreAjouterReleveCompteur far = new FenetreAjouterReleveCompteur(vue.getGfp(), vue.getDesktop());
        vue.getDesktop().add(far);
        far.setVisible(true);
    }

    private void supprimerReleveSelectionnee() {

        JTable table = vue.getTableauReleves();
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                vue,
                "Veuillez sélectionner un relevé à supprimer.",
                "Aucune sélection",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String idCompteur = table.getValueAt(row, 0).toString();
        String dateReleve = table.getValueAt(row, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(
            vue,
            "Voulez-vous vraiment supprimer ce relevé ?",
            "Confirmation",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            DaoReleveCompteur dao = new DaoReleveCompteur();
            dao.deleteByIdAndDate(idCompteur, LocalDate.parse(dateReleve));
            chargerRelevesEnArrierePlan();

            JOptionPane.showMessageDialog(
                vue,
                "Relevé supprimé avec succès.",
                "Suppression",
                JOptionPane.INFORMATION_MESSAGE
            );

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                vue,
                "Erreur lors de la suppression du relevé.",
                "Erreur",
                JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }

}
