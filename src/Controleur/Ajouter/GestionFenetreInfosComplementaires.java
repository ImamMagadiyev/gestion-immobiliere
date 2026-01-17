package Controleur.Ajouter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Modele.Assurance;
import Modele.Impots;
import Modele.Travaux;
import Modele.Variable;
import Vue.ajouter.FenetreAjouterInfosComplementaires;

public class GestionFenetreInfosComplementaires implements ActionListener {
	private FenetreAjouterInfosComplementaires fen;

	public GestionFenetreInfosComplementaires(FenetreAjouterInfosComplementaires fen) {
		this.fen = fen;
	}

	public FenetreAjouterInfosComplementaires getFen() {
		return fen;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String typeChoisie = this.fen.getTypeFacture();
		
		String numFac = this.fen.getNumFacture();
		switch(typeChoisie) {
		case "Travaux" :
			String id = this.fen.getIdTravaux().getText();
			String Raison = this.fen.getRaison().getText();
			System.out.println("[DEBUG] Création Travaux : ID=" + id + ", Raison=" + Raison);
			Travaux t = new Travaux(id,Raison,numFac);
			this.fen.setInstances(t);
			break;

		case "Impots" :
			String idImpot = this.fen.getImpots().getText();
			String type = this.fen.getTypeImpots().getText();
			int anne = (int) this.fen.getAnneImpots();
			int recuperable = this.fen.getValeurCheckBox();
			System.out.println("[DEBUG] Création Impots : ID=" + idImpot + ", Type=" + type 
                    + ", Année=" + anne + ", Récupérable=" + recuperable);
			Impots impot = new Impots(idImpot,recuperable,anne,type,numFac);
			this.fen.setInstances(impot);
			break;

		case "Assurance" :
			String idAssurance = this.fen.getIdAssurance().getText();
			String typeAssurance = this.fen.getTypeAssurance().getText();
			int annee = this.fen.getAnnee();
			System.out.println("[DEBUG] Création Assurance : ID=" + idAssurance + ", Type=" + typeAssurance 
                    + ", Année=" + annee);
			Assurance assurance = new Assurance(idAssurance,typeAssurance,annee,numFac);
			this.fen.setInstances(assurance);
			break;
		case "Variable" : 
			String idVariable = this.fen.getId_Variables().getText();
			String fournisseur = this.fen.getFournisseur().getText();
			String service = this.fen.getService().getText();
			String genre = this.fen.getTypeVariable().getText();
			System.out.println("[DEBUG] Création Variable : ID=" + idVariable + ", Service=" + service 
                    + ", Fournisseur=" + fournisseur + ", Genre=" + genre);
			Variable variable = new Variable(idVariable,service,fournisseur,genre,numFac);
			this.fen.setInstances(variable);
			break;
		}
		this.fen.dispose();
	}


}
