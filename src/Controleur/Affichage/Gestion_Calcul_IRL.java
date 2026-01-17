package Controleur.Affichage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import Controleur.GestionFenetrePrincipale;
import Modele.ContratDeLocation;
import Modele.dao.DaoContratDeLocation;
import Vue.FenetrePrincipale;
import Vue.Calculer.CalculerIRL;

public class Gestion_Calcul_IRL implements ActionListener {
	
	private FenetrePrincipale principale;
	private CalculerIRL fen;
	private GestionFenetrePrincipale gfp;
	public Gestion_Calcul_IRL(CalculerIRL fen) {
		this.fen = fen;
		this.principale = fen.getFen();
		this.gfp = fen.getGfp();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		JButton btn = (JButton) e.getSource();
		String texte = btn.getText();
		switch(texte) {
		case "Annuler":
			this.fen.dispose();
			break;
		case "Calculer":
			double ancienIRL = this.fen.getAncienIrl();
			double irl = this.fen.getIRL();
			double ancienLoy = this.fen.getLoyer();
			calculer(irl,ancienLoy,ancienIRL);
		}
	}


	public void calculer(double irl, double ancienLoy, double ancienIRL) throws IllegalArgumentException{
		try {
			if(irl < ancienIRL) {
				JOptionPane.showInternalMessageDialog(fen, "Vous pouvez pas mettre un irl plus bas que l'ancien");
				return;
			}
			
			// Vérifier que ancienIRL n'est pas zéro ou très petit
			if(ancienIRL <= 0) {
				JOptionPane.showInternalMessageDialog(fen, "Erreur : l'ancien IRL est invalide");
				return;
			}
			
			double new_montant = ancienLoy * (irl / ancienIRL);
			
			// Vérifier que le résultat est valide et pas trop grand
			if(Double.isInfinite(new_montant) || Double.isNaN(new_montant) || new_montant > 999999999) {
				JOptionPane.showInternalMessageDialog(fen, "Erreur : le calcul du loyer produit un résultat invalide. Vérifiez les valeurs d'IRL.");
				return;
			}
			
			ContratDeLocation c = this.fen.getContrat();
			c.setLoyer_aPayer(new_montant);
			c.setIRL(irl);
			DaoContratDeLocation daoContrat = new DaoContratDeLocation();
			daoContrat.update(c);
			JOptionPane.showInternalMessageDialog(fen, "Vous avez calculer le nouveau montant du loyer");
			this.fen.dispose();
			this.gfp.afficherTableauBaux();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
