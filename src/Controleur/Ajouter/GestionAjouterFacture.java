package Controleur.Ajouter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDesktopPane;

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
import Controleur.GestionFenetrePrincipale;
import Vue.ajouter.FenetreAjouterFacture;
import Vue.ajouter.FenetreAjouterInfosComplementaires;


	
public class GestionAjouterFacture implements ActionListener {
	private FenetreAjouterFacture fen;
	private GestionFenetrePrincipale gfp;
	private FenetreAjouterInfosComplementaires facture ;
	private String numFact ;
	public GestionAjouterFacture(FenetreAjouterFacture fen,  GestionFenetrePrincipale gfp
			) {
		this.fen = fen;
		this.gfp = gfp;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bt =(JButton) e.getSource();
		String texte = bt.getText();
		switch(texte) {
		case "Valider" :
			ajoutFacture();
			if (fen.getGfp() != null) {
				fen.getGfp().afficherTableFactures();
			}
			break;
		case "Annuler":
			this.fen.dispose();
			break;
		case "Ajouter complémentarités" :
			this.facture = new FenetreAjouterInfosComplementaires(this.fen, fen.getTextNumFac());
			// On récupère le bureau (JDesktopPane) où se trouve déjà AjouterFacture
			JDesktopPane desktop = this.fen.getDesktopPane(); 

			if (desktop != null) {
				desktop.add(facture); // Ajoute la petite fenêtre au même bureau
				facture.setVisible(true);
				try {
					facture.setSelected(true); // Lui donne le focus
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
			// Note : On ne fait SURTOUT PAS this.fen.dispose() ici !
			break;
		}
	}

	
	private void ajoutFacture() {
		
		try {
			String txtdateDevis = fen.getDateDevis();
			String typeFacture =  fen.getComboType().getSelectedItem().toString().trim();
			System.out.println("DEBUG: typeFacture = '" + typeFacture + "'"); // DEBUG
			String nature = fen.getNature();
			String mode_paiement = fen.getTextModePaiement();
			String num_devis = fen.getNumDevis();
			//----------transformation du montant en double
			String mt =fen.getTextMontant();
			Double montant = mt.isEmpty() ? 0 : Double.parseDouble(mt);
			//---------------------------------------------------------
			boolean payer_par_locataire =   fen.getCbxPayerLocataire();
			numFact = fen.getTextNumFac();
			String numFiscale = fen.getTextNumFiscale();
			Date dateFacture = null;
			Date dateDevis = null;

			// numero_fiscale du bien louable
			String siret = fen.getTextSiret();
			//siret de el'entreprise
			String txtDateFacture = fen.getTextDate_Facture();
			
			double montant_Devis = fen.getTxtMontantDevis();
			//tranformation de la dateFacture en Date
			if (txtDateFacture != null && !txtDateFacture.isBlank()) {
				try {
					dateFacture = Date.valueOf(txtDateFacture);
				} catch (Exception e) {
					fen.afficherErreur("Format date invalide (AAAA-MM-JJ attendu)");
					return;
				}
			}
			if (txtdateDevis != null && !txtdateDevis.isBlank()) {
				try {
					dateDevis = Date.valueOf(txtdateDevis);
				} catch (Exception e) {
					fen.afficherErreur("Format date invalide (AAAA-MM-JJ attendu)");
					return;
				}
			}


			Facture facture = new Facture(numFact, dateFacture,typeFacture,montant, mode_paiement,num_devis,nature,dateDevis,payer_par_locataire,siret,numFiscale,montant_Devis,false);

			DaoFacture dao = new DaoFacture();
			System.out.println("DEBUG : Tentative d'insertion de la facture -> " + facture);
			dao.create(facture);
			System.out.println("DEBUG : Facture insérée avec succès");

			insererTypeFacture(typeFacture);

			if (gfp != null) {
				gfp.afficherTableFactures ();
				gfp.afficherTableauAssurances();
				gfp.afficherTableTravauxFac();
				gfp.afficherTableImpots();
				gfp.afficherTableVariable();

				fen.afficherSucces("Facture ajoutée avec succès !");
				fen.dispose();
			}

		} catch (NumberFormatException e) {
			fen.afficherErreur("Le montant doit être un nombre valide.");
		} catch (IllegalArgumentException e) {
			fen.afficherErreur("Format de date invalide. Utilisez le format YYYY-MM-DD.");
		} catch (Exception e) {
			fen.afficherErreur("Erreur lors de l'ajout de la facture : " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void insererTypeFacture(String type) {
		try {
			switch(type) {
			
			case "Travaux" :
				String id = this.fen.getIdTravaux();
				String Raison = this.fen.getRaison();
				Travaux t = new Travaux(id,Raison,numFact);
				DaoTravaux daoTravaux = new DaoTravaux();
				System.out.println("DEBUG : Insertion Travaux -> " + t);
				daoTravaux.create(t);
				System.out.println("DEBUG : Travaux insérés avec succès");
				break;

			case "Impots" :
				String idImpot = this.fen.getId_Impots().getText();
				String typeImpot = this.fen.getTypeImpots().getText();
				int anne =  this.fen.getAnneImpots();
				int recuperable = this.fen.getValeurCheckBox();
				Impots impot = new Impots(idImpot,recuperable,anne,typeImpot,numFact);
				DaoImpots daoImpots = new DaoImpots();
				System.out.println("DEBUG : Insertion Impots -> " + impot);
				daoImpots.create(impot);
				System.out.println("DEBUG : Impots insérés avec succès");
				break;

			case "Assurances" :
				String idAssurance = this.fen.getIdAssurance().getText();
				String typeAssurance = this.fen.getTypeAssurance().getText();
				int annee = this.fen.getAnnee();
				Assurance assurance = new Assurance(idAssurance,typeAssurance,annee,numFact);
				DaoAssurance daoAssurance = new DaoAssurance();
				System.out.println("DEBUG : Insertion Assurance -> " + assurance);
				daoAssurance.create(assurance);
				System.out.println("DEBUG : Assurances insérées avec succès");
				break;

			case "Variable" : 
				String idVariable = this.fen.getId_Variables().getText();
				String fournisseur = this.fen.getFournisseur().getText();
				String service = this.fen.getService().getText();
				String genre = this.fen.getTypeVariable();
				String prixUnitaireText = this.fen.getPrixUnitaire();
				double prixUnitaire = prixUnitaireText.isEmpty() ? 0 : Double.parseDouble(prixUnitaireText);

				Variable variable = new Variable(idVariable,service,fournisseur,genre,numFact,prixUnitaire);
				
				System.out.println("DEBUG Variable: ID=" + idVariable + " pour Facture=" + numFact);

				DaoVariable daoVar = new DaoVariable();
				System.out.println("DEBUG : Insertion Variable -> " + variable);
				daoVar.create(variable);
				System.out.println("DEBUG : Variable insérée avec succès");
				break;

			case "Agence" :
				// Les factures agence ne nécessitent pas d'insertion spécifique
				// Seule la facture principale est enregistrée
				System.out.println("DEBUG : Facture agence enregistrée avec succès");
				break;

			}

		}catch(SQLException sqlE) {
			sqlE.printStackTrace();
		}
	}
}
