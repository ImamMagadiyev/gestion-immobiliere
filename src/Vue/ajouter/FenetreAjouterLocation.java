package Vue.ajouter;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import Modele.Loyer;
import Modele.BienLouable;
import Modele.Locataire;
import Controleur.GestionFenetrePrincipale;
import Controleur.Ajouter.GestionAjouterLocation;
import Vue.Utils;

public class FenetreAjouterLocation extends FenetreAjoutBase {

	private static final long serialVersionUID = 1L;

	private Loyer loyerTemp;
	private GestionFenetrePrincipale gfp;

	private JTextField champDatePaiement,
	champMontantProvision, champMontantLoyer, champMois, champMontantRegularisation;
	private JComboBox<String> champIdLocataire, champNumeroFiscal;

	private JButton btnAjouter, btnAnnuler;

	public FenetreAjouterLocation(GestionFenetrePrincipale gfp, JDesktopPane desktop) {
		super("Ajouter un loyer", "Nouveau loyer", 500, 450, desktop);

		this.gfp = gfp;

		champIdLocataire = new JComboBox<>();
		champIdLocataire.addItem("Selectionner un locataire");
		champIdLocataire.setName("champIdLocataire");
		champIdLocataire.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		champIdLocataire.setPreferredSize(new Dimension(200, 25));
		
		champNumeroFiscal = new JComboBox<>();
		champNumeroFiscal.addItem("Selectionner un bien");
		champNumeroFiscal.setName("champNumeroFiscal");
		champNumeroFiscal.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		champNumeroFiscal.setPreferredSize(new Dimension(200, 25));
		
		champDatePaiement = Utils.creerTextField("Date de paiement (YYYY-MM-DD)", 200, 25);
		champMontantProvision = Utils.creerTextField("Montant provision", 200, 25);
		champMontantLoyer = Utils.creerTextField("Montant loyer", 200, 25);
		champMois = Utils.creerTextField("Mois (ex: Janvier 2025)", 200, 25);
		champMontantRegularisation = Utils.creerTextField("Montant regularisation", 200, 25);

		form.add(creerLigne("ID Locataire :", champIdLocataire, 180));
		form.add(Utils.creerEspacementVertical(8));
		form.add(creerLigne("Numero fiscal :", champNumeroFiscal, 180));
		form.add(Utils.creerEspacementVertical(8));
		form.add(creerLigne("Date de paiement :", champDatePaiement, 180));
		form.add(Utils.creerEspacementVertical(8));
		form.add(creerLigne("Montant provision :", champMontantProvision, 180));
		form.add(Utils.creerEspacementVertical(8));
		form.add(creerLigne("Montant loyer :", champMontantLoyer, 180));
		form.add(Utils.creerEspacementVertical(8));
		form.add(creerLigne("Mois :", champMois, 180));
		form.add(Utils.creerEspacementVertical(8));
		form.add(creerLigne("Montant regularisation :", champMontantRegularisation, 180));

		add(form, BorderLayout.CENTER);

		btnAjouter = Utils.creerBoutonPrimaire("Ajouter");
		btnAjouter.setName("btnAjouter");
		panelBoutons.add(btnAjouter);

		btnAnnuler = Utils.creerBouton("Annuler");
		btnAnnuler.setName("btnAnnuler");
		panelBoutons.add(btnAnnuler);

		if (this.getClass() == FenetreAjouterLocation.class) {
			new GestionAjouterLocation(this);
		}
	}

	/**
	 * Remplit la liste déroulante des locataires à partir d'une liste fournie.
	 * Méthode appelée par le contrôleur après récupération des locataires en base.
	 */
	public void remplirLocataires(List<Locataire> liste) {
		champIdLocataire.removeAllItems();
		champIdLocataire.addItem("Selectionner un locataire");
		for (Locataire l : liste) {
			champIdLocataire.addItem(l.getIdLocataire());
		}
	}

	/**
	 * Remplit la liste déroulante des biens louables à partir d'une liste fournie.
	 * Méthode appelée par le contrôleur après récupération des biens en base.
	 */
	public void remplirBiens(List<BienLouable> liste) {
		champNumeroFiscal.removeAllItems();
		champNumeroFiscal.addItem("Selectionner un bien");
		for (BienLouable b : liste) {
			champNumeroFiscal.addItem(b.getNumero_fiscale());
		}
	}

	public JComboBox<String> getChampIdLocataire() { return champIdLocataire; }
	public JComboBox<String> getChampNumeroFiscal() { return champNumeroFiscal; }
	public String getIdLocataireSelectionnee() { return (String) champIdLocataire.getSelectedItem(); }
	public String getNumerFiscalSelectionnee() { return (String) champNumeroFiscal.getSelectedItem(); }
	public JTextField getChampDatePaiement() { return champDatePaiement; }
	public JTextField getChampMontantProvision() { return champMontantProvision; }
	public JTextField getChampMontantLoyer() { return champMontantLoyer; }
	public JTextField getChampMois() { return champMois; }
	public JTextField getChampMontantRegularisation() { return champMontantRegularisation; }

	public JButton getBtnAjouter() { return btnAjouter; }
	public JButton getBtnAnnuler() { return btnAnnuler; }

	public JDesktopPane getDesktop() { return desktop; }

	/**
	 * Stocke temporairement un objet Loyer dans la fenêtre.
	 * Sert notamment à conserver un loyer créé/sélectionné avant une action suivante
	 */
	public void setLoyerTemp(Loyer loyer) { this.loyerTemp = loyer; }
	public Loyer getLoyerTemp() { return loyerTemp; }

	public GestionFenetrePrincipale getGfp() { return gfp; }

	/**
	 * Affiche un message d'erreur sous forme de popup.
	 * Utilisé lorsque la saisie est invalide ou qu'une opération échoue.
	 */
	public void afficherErreur(String msg) {
		JOptionPane.showMessageDialog(this, msg, "Erreur", JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * Affiche un message de confirmation indiquant qu'une action s'est bien déroulée.
	 */
	public void afficherSucces(String msg) {
		JOptionPane.showMessageDialog(this, msg, "Succes", JOptionPane.INFORMATION_MESSAGE);
	}

}
