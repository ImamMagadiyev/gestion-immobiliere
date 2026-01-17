package Controleur.Modifier;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import Modele.Assurance;
import Modele.Facture;
import Modele.Impots;
import Modele.Travaux;
import Modele.Variable;
import Modele.dao.DaoAssurance;
import Modele.dao.DaoFacture;
import Modele.dao.DaoImpots;
import Modele.dao.DaoTravaux;
import Modele.dao.DaoVariable;
import Vue.Modification.FenetreModifieFacture;

public class GestionModificationFacture implements ActionListener {
	private FenetreModifieFacture fen;
	private Facture facture;
	private Impots impot;
	private Variable variable;
	private Travaux tr;
	private Assurance assurance;

	public GestionModificationFacture(FenetreModifieFacture fen, Facture facture) {
		this.fen = fen;
		this.facture = facture;
		impot = this.fen.getImpot();
		variable = this.fen.getV();
		tr = this.fen.getTravaux();
		assurance = this.fen.getAssurance();
	}

	public FenetreModifieFacture getFen() {
		return fen;
	}

	public Facture getFacture() {
		return facture;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bt =(JButton) e.getSource();
		String texte = bt.getText();
		switch(texte) {
		case "Enregistrer" :
			modifierFacture();
			break;
		case "Annuler":
			System.out.println("[DEBUG] Bouton Annuler cliqué - fermeture de la fenêtre");
			this.fen.dispose();
			break;
		}
	}

	private void modifierFacture() {
		try {
			String typeFacture = this.fen.getType();
			String nature = this.fen.getNature();
			String mode_paiement = this.fen.getTextModePaiement();
			String num_devis = this.fen.getNumDevis();

			boolean PayerParLoc = this.fen.getCbxPayerLocataire();

			java.sql.Date dateFacture = null;
			String txtDateFacture = fen.getTextDate_Facture();
			if (txtDateFacture != null && !txtDateFacture.isBlank()) {
				dateFacture = java.sql.Date.valueOf(txtDateFacture);
			}
			java.sql.Date dateDevis = null;
			String txtdateDevis = fen.getDateDevis();
			if (txtdateDevis != null && !txtdateDevis.isBlank()) {
				dateDevis = java.sql.Date.valueOf(txtdateDevis);
			}
			

			System.out.println("[DEBUG] Modification Facture : Type=" + typeFacture 
                    + ", Nature=" + nature 
                    + ", ModePaiement=" + mode_paiement 
                    + ", NumDevis=" + num_devis
                    + ", DateFacture=" + dateFacture 
                    + ", DateDevis=" + dateDevis);
			
			facture.setDate_facture(dateFacture);
			facture.setDate_devis(dateDevis);
			facture.setMode_paiement(mode_paiement);
			facture.setNature(nature);
			facture.setType_travaux(typeFacture);
			facture.setNumero_devis(num_devis);
			facture.setPayer_par_locataire(PayerParLoc);
			DaoFacture dao = new DaoFacture();
			dao.update(facture);

			if (typeFacture.equalsIgnoreCase("Travaux")) {
				tr = this.fen.getTravaux();
				if (tr != null) {
					String raison = this.fen.getRaison().getText();
					tr.setRaison(raison);
					System.out.println("[DEBUG] Modification Travaux : ID=" + tr.getId_Travaux() 
                    + ", Raison=" + raison);
					
					DaoTravaux daoTr = new DaoTravaux();
					daoTr.update(tr);
				}
			} else if (typeFacture.equalsIgnoreCase("Impots")) {
				impot = this.fen.getImpot();
				if (impot != null) {
					String typeImp = this.fen.getTypeImpots().getText();
					int anne = Integer.parseInt(this.fen.getAnneImpots().getText());
					int recuperable = this.fen.getRecup();
					impot.setAnnee(anne);
					impot.setType(typeImp);
					impot.setRecuperable_impot(recuperable);
					System.out.println("[DEBUG] Modification Impots : ID=" + impot.getIdImpots() 
                    + ", Type=" + typeImp 
                    + ", Année=" + anne 
                    + ", Recuperable=" + recuperable);
					DaoImpots daoImp = new DaoImpots();
					daoImp.update(impot);
				}
			} else if (typeFacture.equalsIgnoreCase("Variable")) {
				variable = this.fen.getV();
				if (variable != null) {
					String service = this.fen.getService().getText();
					String fournisseur = this.fen.getFournisseur().getText();
					String typeVar = this.fen.getTypeVariable().getText();
					variable.setEau_gaz_electricite(typeVar);
					variable.setFournisseur(fournisseur);
					variable.setService(service);
					
					System.out.println("[DEBUG] Modification Variable : ID=" + variable.getId_Variable() 
                    + ", Service=" + service 
                    + ", Fournisseur=" + fournisseur 
                    + ", Type=" + typeVar);
					
					DaoVariable daoVar = new DaoVariable();
					daoVar.update(variable);
				}
			} else if (typeFacture.equalsIgnoreCase("Assurance")) {
				assurance = this.fen.getAssurance();
				if (assurance != null) {
					String typeAss = this.fen.getTypeAssurance().getText();
					int anneAss = Integer.parseInt(this.fen.getAnnee().getText());
					assurance.setAnnee(anneAss);
					assurance.setType(typeAss);
					
					System.out.println("[DEBUG] Modification Assurance : ID=" + assurance.getId_assurance() 
                    + ", Type=" + typeAss 
                    + ", Année=" + anneAss);
					
					DaoAssurance daoAss = new DaoAssurance();
					daoAss.update(assurance);
				}
			}

			this.fen.getGfp().afficherTableFactures();
			JOptionPane.showMessageDialog(fen, "Facture modifiée avec succès");
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(fen, "Erreur lors de la modification");
		}

		// Fermer la fenêtre dans tous les cas
		this.fen.dispose();
	}
}