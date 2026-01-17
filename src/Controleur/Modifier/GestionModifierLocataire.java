package Controleur.Modifier;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import Modele.Locataire;
import Modele.dao.DaoLocataire;
import Vue.Utils;
import Vue.Modification.FenetreModifierLocataire;

public class GestionModifierLocataire implements ActionListener {

	private FenetreModifierLocataire flm;
	private Locataire locataire;

	public GestionModifierLocataire(FenetreModifierLocataire flm) {
		this.flm = flm;
		this.locataire = flm.getLocataire();

		flm.setTitle("Modifier un locataire");

		System.out.println("[DEBUG] Initialisation GestionModifierLocataire pour : " + locataire.getNom());

		flm.getBtnModifier().addActionListener(this);
		flm.getBtnAnnuler().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String name = ((java.awt.Component) e.getSource()).getName();
		System.out.println("DEBUG: Bouton cliqué avec name = " + name);
		
		if (name == null) {
			// Si le nom est null, vérifier le texte du bouton
			if (e.getSource() instanceof javax.swing.JButton) {
				String text = ((javax.swing.JButton) e.getSource()).getText();
				System.out.println("DEBUG: Texte du bouton = " + text);
				if ("Modifier".equals(text)) {
					modifierLocataire();
					return;
				} else if ("Annuler".equals(text)) {
					try {
						flm.setClosed(true);
					} catch (java.beans.PropertyVetoException e1) {
						e1.printStackTrace();
					}
					return;
				}
			}
			return;
		}

		switch (name) {
		case "btnModifierLocataire":
			System.out.println("[DEBUG] Bouton 'Modifier Locataire' cliqué pour : " + locataire.getNom());
			modifierLocataire();
			break;

		case "btnAnnuler":
			try {
				flm.setClosed(true);
			} catch (java.beans.PropertyVetoException e1) {
				e1.printStackTrace();
			}
			break;
		}
	}

	private void modifierLocataire() {
		// Validations avant le try-catch
		if (flm.getNomField().getText().trim().isEmpty() ||
				flm.getPrenomField().getText().trim().isEmpty() ||
				flm.getDateNaissanceField().getText().trim().isEmpty() ||
				flm.getEmailField().getText().trim().isEmpty() ||
				flm.getAdresseField().getText().trim().isEmpty() ||
				flm.getVilleField().getText().trim().isEmpty() ||
				flm.getCodePostalField().getText().trim().isEmpty() ||
				flm.getTelephoneField().getText().trim().isEmpty() ||
				flm.getGenreCombo().getSelectedItem() == null) {

			flm.afficherErreur("Veuillez remplir tous les champs obligatoires.");
			return;
		}

		// Validation de la date de naissance
		String dateNaissanceStr = flm.getDateNaissanceField().getText().trim();
		try {
			Utils.parseLocalDate(dateNaissanceStr);
		} catch (Exception e) {
			flm.afficherErreur("Format date invalide (AAAA-MM-JJ attendu)");
			return;
		}

		// Mise à jour du locataire
		try {
			locataire.setNom(flm.getNomField().getText().trim());
			locataire.setPrenom(flm.getPrenomField().getText().trim());
			locataire.setDate_naissance(dateNaissanceStr);
			locataire.setGenre(flm.getGenreCombo().getSelectedItem().toString().charAt(0));
			locataire.setEmail(flm.getEmailField().getText().trim());
			locataire.setAdresse(flm.getAdresseField().getText().trim());
			locataire.setVille(flm.getVilleField().getText().trim());
			locataire.setCode_postale(flm.getCodePostalField().getText().trim());
			locataire.setTelephone(flm.getTelephoneField().getText().trim());

			DaoLocataire dao = new DaoLocataire();
			dao.update(locataire);
			System.out.println("[DEBUG] Locataire modifié avec succès : " + locataire.getNom());

			flm.afficherSucces("Locataire modifié avec succès.");
			
			// Actualiser la table dans la fenetre principale
			if (flm.getGfp() != null) {
				flm.getGfp().afficherTableauLocataire();
			}
			
			// Afficher le message de succès
			flm.afficherSucces("Locataire modifié avec succès.");
			
			// Fermer la fenêtre après succès dans un thread séparé
			System.out.println("DEBUG: Fermeture de la fenêtre...");
			new Thread(() -> {
				try {
					Thread.sleep(500); // Attendre un peu
					flm.setClosed(true);
				} catch (java.beans.PropertyVetoException | InterruptedException e1) {
					e1.printStackTrace();
				}
			}).start();
			
		} catch (SQLException ex) {
			flm.afficherErreur("Erreur BD : " + ex.getMessage());

			System.out.println("[DEBUG] Erreur modification locataire : " + locataire.getNom() + " => " + ex.getMessage());
			return;

		}
	}
}
