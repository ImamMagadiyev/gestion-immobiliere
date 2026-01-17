package Controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import Modele.dao.UtOracleDataSource;
import Vue.FenetreConnexion;
import Vue.FenetrePrincipale;

public class GestionConnexion implements ActionListener {

	private FenetreConnexion fc;

	// Constructeur prenant en paramètre la fenêtre de connexion
	public GestionConnexion(FenetreConnexion fc) {
		this.fc = fc;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton boutonClicke = (JButton) e.getSource();
		switch (boutonClicke.getText()) {

		case "Se connecter":
			// Récupération des informations de connexion depuis la fenêtre pricipale
			String login = this.fc.getNomUtilisateur();
			String mdp = this.fc.getMdp();
			try {
				// Tentative de création de l'accès avec les informations de connexion
				UtOracleDataSource.creerAcces(login, mdp);

				// Si la connexion réussit, ouvrir la fenêtre principale et fermer la fenêtre de connexion
				FenetrePrincipale fenetrePrincipale = new FenetrePrincipale();
				fenetrePrincipale.setVisible(true);

				// Utiliser le gestionnaire pour charger les graphiques de statistiques
				fenetrePrincipale.montrerPage("ACCUEIL");
				this.fc.dispose();
			} catch (SQLException e1) {
				// En cas d'échec de connexion, afficher un message d'erreur
				JOptionPane.showMessageDialog(this.fc, "Login ou mot de passe incorrect", "Erreur", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}
			break;

		case "Annuler":
			// En cas d'annulation, fermer la fenêtre de connexion
			this.fc.dispose();
			break;
		}
	}
}
