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

import Controleur.Ajouter.GestionAjouterDiagnostic;
import Modele.Diagnostic;
import Modele.dao.DaoDiagnostic;
import Vue.Affichage.FenetreAfficherDiagnostics;
import Vue.Modification.FenetreModifierDiagnostic;
import Vue.ajouter.FenetreAjouterDiagnostic;


public class GestionAfficherDiagnostics implements ActionListener {

    private FenetreAfficherDiagnostics vue;
    private JDesktopPane desktop;

    public GestionAfficherDiagnostics(FenetreAfficherDiagnostics vue, JDesktopPane desktop) {
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

       
            case "btnAjouterDiagnostic":
                // Vérifier qu'il y a des biens louables et des entreprises avant d'ouvrir la fenêtre
                try {
                    Modele.dao.DaoBienLouable daoBien = new Modele.dao.DaoBienLouable();
                    Modele.dao.DaoEntreprise daoEntreprise = new Modele.dao.DaoEntreprise();
                    
                    java.util.List<Modele.BienLouable> listeBiens = daoBien.findAll();
                    java.util.List<Modele.Entreprise> listeEntreprises = daoEntreprise.findAll();
                    
                    if (listeBiens.isEmpty() && listeEntreprises.isEmpty()) {
                        javax.swing.JOptionPane.showMessageDialog(vue, "Aucun bien louable et aucune entreprise trouvé dans la base de données. Veuillez d'abord créer des biens et des entreprises.", "Information", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                        break;
                    } else if (listeBiens.isEmpty()) {
                        javax.swing.JOptionPane.showMessageDialog(vue, "Aucun bien louable trouvé dans la base de données. Veuillez d'abord créer des biens.", "Information", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                        break;
                    } else if (listeEntreprises.isEmpty()) {
                        javax.swing.JOptionPane.showMessageDialog(vue, "Aucune entreprise trouvée dans la base de données. Veuillez d'abord créer des entreprises.", "Information", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }
                } catch (Exception ex) {
                    javax.swing.JOptionPane.showMessageDialog(vue, "Erreur lors de la vérification des données : " + ex.getMessage(), "Erreur", javax.swing.JOptionPane.ERROR_MESSAGE);
                    break;
                }
                
                // Ouvrir la fenêtre seulement si les données sont disponibles
                Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(vue);
                FenetreAjouterDiagnostic fad = new FenetreAjouterDiagnostic(parentFrame);
                new GestionAjouterDiagnostic(fad, this);
                fad.setVisible(true);
                break;
            
            case "btnModifierDiagnostic":
            	modifierDiagnostic();
            	break;
            case "btnArchiverDiagnostic":
            	archiverDiagnostic();
            	break;
            case "btnFermerDiagnostics":
                vue.dispose();
                break;
        }
    }



    public void chargerDiagnostics() throws SQLException {
    	DaoDiagnostic dao = new DaoDiagnostic();
        List<Diagnostic> dia = dao.findAll();
        
        DefaultTableModel model = (DefaultTableModel) vue.getTableDiagnostics().getModel();


    //vue.afficherDiagnostics();

        model.setRowCount(0);
        

        for (Diagnostic d : dia) {
                model.addRow(new Object[] {
                    d.getReference(),
                    d.getDate_valide(),
                    d.getType_diagnostic(),
                    d.getNumero_Fiscale(),
                    d.getSiret()
                });
         
        
      }
    }
    
    public void modifierDiagnostic() {
		int row = vue.getTableDiagnostics().getSelectedRow(); 
		if (row == -1) {
			JOptionPane.showMessageDialog(vue, "Veuillez sélectionner un bien à modifier.", "Erreur", JOptionPane.WARNING_MESSAGE);
		}

		String reference = vue.getTableDiagnostics().getValueAt(row, 0).toString();

		try {
			DaoDiagnostic dao = new DaoDiagnostic();
			Diagnostic dia = dao.findById(reference);

			if (dia == null) {
				JOptionPane.showMessageDialog(vue, "Bien introuvable dans la base.", "Erreur", JOptionPane.ERROR_MESSAGE);
			}


			Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(vue);
			FenetreModifierDiagnostic fmd = new FenetreModifierDiagnostic(parentFrame, dia, this);
			fmd.setVisible(true);
		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(vue, "Erreur lors de la récupération du bien : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}
    
    private void archiverDiagnostic() {

        JTable table = vue.getTableDiagnostics();

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                vue,
                "Veuillez sélectionner un diagnostic à archiver.",
                "Aucune sélection",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        String idDiagnostic = table.getValueAt(row, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(
            vue,
            "Voulez-vous vraiment archiver ce diagnostic ?",
            "Confirmation",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            DaoDiagnostic daoDia = new DaoDiagnostic();
            daoDia.archiverById(idDiagnostic);   
            chargerDiagnostics();

            JOptionPane.showMessageDialog(
                vue,
                "Diagnostic archivé avec succès.",
                "Archivage",
                JOptionPane.INFORMATION_MESSAGE
            );

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                vue,
                "Erreur lors de l'archivage du diagnostic.",
                "Erreur",
                JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }

}
