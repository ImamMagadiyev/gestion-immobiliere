package Controleur.Ajouter;

import java.sql.SQLException;

import Controleur.GestionFenetrePrincipale;
import Modele.Locataire;
import Modele.dao.DaoLocataire;
import Vue.ajouter.FenetreLocataireAjouter;

public class GestionAjouterLocataire extends GestionControleurAjoutBase<FenetreLocataireAjouter> {

	public GestionAjouterLocataire(FenetreLocataireAjouter vue) {
		super(vue);
	}

	@Override
	protected void enregistrerBoutons() {
		vue.getBtnAjouter().addActionListener(this);
		vue.getBtnAnnuler().addActionListener(this);
	}

	@Override
	protected void traiterAction(String nomBouton) {
		if ("btnAjouter".equals(nomBouton)) {
			ajouterLocataire();
		}
	}

	private void ajouterLocataire() {
		if (champsSontVides(
				vue.getIdField().getText(),
				vue.getNomField().getText(),
				vue.getPrenomField().getText(),
				vue.getDateNaissanceField().getText(),
				vue.getEmailField().getText(),
				vue.getAdresseField().getText(),
				vue.getVilleField().getText(),
				vue.getCodePostalField().getText(),
				vue.getTelephoneField().getText()) ||
				vue.getGenreCombo().getSelectedItem() == null) {
			
			afficherErreurChampsObligatoires();
			return;
		}

		try {
			Locataire loc = new Locataire(
					vue.getIdField().getText().trim(),
					vue.getNomField().getText().trim(),
					vue.getPrenomField().getText().trim(),
					vue.getDateNaissanceField().getText().trim(),
					vue.getGenreCombo().getSelectedItem().toString().charAt(0),
					vue.getEmailField().getText().trim(),
					vue.getAdresseField().getText().trim(),
					vue.getVilleField().getText().trim(),
					vue.getCodePostalField().getText().trim(),
					vue.getTelephoneField().getText().trim(),
					false
					);

			DaoLocataire dao = new DaoLocataire();
			dao.create(loc);
			vue.afficherSucces("Locataire ajouté avec succès !");
			
			GestionFenetrePrincipale gfp = vue.getGestionFenetrePrincipale();
			if (gfp != null) {
				gfp.afficherTableauLocataire();
			}
			
			vue.dispose();

		} catch (SQLException ex) {
			vue.afficherErreur("Erreur lors de l'ajout : " + ex.getMessage());
			ex.printStackTrace();
		}
	}

}
