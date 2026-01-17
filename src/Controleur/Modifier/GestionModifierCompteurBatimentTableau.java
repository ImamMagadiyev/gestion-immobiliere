package Controleur.Modifier;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import Modele.Compteur;
import Modele.dao.DaoCompteur;
import Modele.dao.requetes.Select.RequeteSelectAllByBatimentCompteur;
import Vue.Modification.FenetreModifierCompteurBatiment;
import Vue.Modification.FenetreModifierCompteurBatimentTableau;

public class GestionModifierCompteurBatimentTableau implements ActionListener {

    private FenetreModifierCompteurBatimentTableau vue;
    private DaoCompteur dao = new DaoCompteur();

    public GestionModifierCompteurBatimentTableau(FenetreModifierCompteurBatimentTableau vue) {
        this.vue = vue;

        vue.getBtnModifier().addActionListener(this);
        vue.getBtnSupprimer().addActionListener(this);
        vue.getBtnFermer().addActionListener(this);

        System.out.println("[DEBUG] Initialisation GestionModifierCompteurBatimentTableau pour le batiment : " + vue.getBatiment().getIdBatiment());
        chargerCompteurs(); 
    }

    private void chargerCompteurs() {
        try {
            DefaultTableModel model = vue.getModel();
            model.setRowCount(0);

            List<Compteur> liste = dao.find(
                new RequeteSelectAllByBatimentCompteur(),
                vue.getBatiment().getIdBatiment()
            );

            System.out.println("[DEBUG] " + liste.size() + " compteurs chargés pour le batiment " + vue.getBatiment().getIdBatiment());

            for (Compteur c : liste) {
                model.addRow(new Object[] {
                    c.getId_compteur(),
                    c.getType(),
                    c.getIndex(),
                    c.getId_variable()
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vue, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            System.out.println("[DEBUG] Erreur chargement compteurs : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String name = ((JComponent) e.getSource()).getName();
        if (name == null) return;

        switch (name) {
            case "BTN_MODIFIER":
                System.out.println("[DEBUG] Bouton 'Modifier' cliqué");
                if (vue.getDesktop() == null) {
                    JOptionPane.showMessageDialog(vue, "DesktopPane non initialisé");
                    return;
                }
                modifier();
                break;

            case "BTN_SUPPRIMER":
                System.out.println("[DEBUG] Bouton 'Supprimer' cliqué");
                supprimer();
                break;

            case "BTN_FERMER":
                System.out.println("[DEBUG] Bouton 'Fermer' cliqué, fermeture de la fenêtre");
                vue.dispose();
                break;
        }
    }

    private void modifier() {
        int row = vue.getTableCompteur().getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(vue, "Sélectionnez un compteur.");
            return;
        }

        String id = vue.getTableCompteur().getValueAt(row, 0).toString();
        System.out.println("[DEBUG] Modification du compteur sélectionné : " + id);

        try {
            Compteur c = dao.findById(id);

            Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(vue);
            FenetreModifierCompteurBatiment fmcb =
                new FenetreModifierCompteurBatiment(
                    c,
                    this::chargerCompteurs,
                    parentFrame
                );

            fmcb.setVisible(true);

        } catch (Exception ex) {
            System.out.println("[DEBUG] Erreur lors de l'ouverture de la fenêtre de modification : " + ex.getMessage());
            ex.printStackTrace(); 
            JOptionPane.showMessageDialog(vue, ex.getMessage());
        }
    }

    private void supprimer() {
        int row = vue.getTableCompteur().getSelectedRow();
        if (row == -1) return;

        String id = vue.getTableCompteur().getValueAt(row, 0).toString();
        System.out.println("[DEBUG] Suppression du compteur sélectionné : " + id);

        if (JOptionPane.showConfirmDialog(vue,
                "Supprimer ce compteur ?", "Confirmation",
                JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION)
            return;

        try {
            Compteur c = dao.findById(id);
            dao.delete(c);
            System.out.println("[DEBUG] Compteur supprimé : " + id);
            chargerCompteurs(); 
        } catch (Exception ex) {
            System.out.println("[DEBUG] Erreur suppression compteur : " + ex.getMessage());
            JOptionPane.showMessageDialog(vue, ex.getMessage());
        }
    }
}
