package Vue.ajouter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controleur.GestionFenetrePrincipale;
import Controleur.Ajouter.GestionAjouterFacture;
import Modele.Facture;
import Modele.BienLouable;
import Modele.Entreprise;
import Modele.dao.DaoBienLouable;
import Modele.dao.DaoEntreprise;
import Vue.Utils;
import java.util.List;

public class FenetreAjouterFacture extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	protected JTextField textNature;
	protected JTextField textDateDevis;
	protected JComboBox<String> comboSiret;
	protected JComboBox<String> comboNumFiscale;
	protected JTextField textNumDevis;
	protected JComboBox<String> comboModePaiement;
	protected JComboBox<String> comboType;
	protected JTextField textMontant;
	protected JTextField textNumFac;
	protected JTextField textDate_Facture;
	protected JTextField txtMontantDevis;

	protected JCheckBox checkBox ;
	protected JCheckBox cbxPayerLocataire;

	protected JTextField idTravaux;
	protected JTextField raison, Id_Impots, AnneImpots, TypeImpots, IdAssurance, TypeAssurance,
	Annee, Id_Variables, service, fournisseur, typeVariable, prixUnitaire;

	private JPanel panelTravaux;
	private JPanel panelRaison , panelId_Impot,panelAnneImpot, panelTypeImpot,panelRecup, panelAssurance,panelType_Assurance,panelAnneAssurance
	,panelId_Variable,panelService, panelFournisseur, panelTypeVar, panelPrixUnitaire;
	private JComboBox<String> comboTypeVariable;

	private GestionAjouterFacture gestion;
	private GestionFenetrePrincipale gfp;
	private Facture facture;
	private JPanel form;
	
	protected JButton boutonEnregistrer;
	protected JButton boutonAnnuler;

	public FenetreAjouterFacture(GestionFenetrePrincipale gfp) {
		super("Ajouter Facture", true, true, true, true);

		this.gfp = gfp;
		this.gestion = new GestionAjouterFacture(this, gfp);

		setSize(500, 650);
		setLayout(new BorderLayout(15, 15));
		getContentPane().setBackground(Color.WHITE);

		/* ===================== TITRE ===================== */
		JLabel lblTitre = Utils.creerTitre("Ajouter une facture");
		add(lblTitre, BorderLayout.NORTH);

		/* ===================== FORMULAIRE ===================== */
		form = new JPanel();
		form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
		form.setOpaque(false);
		form.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

		// Champs principaux
		textNumFac       = Utils.creerTextField("Numéro facture", 200, 25);
		textDate_Facture = Utils.creerTextField("YYYY-MM-DD", 200, 25);
		textMontant      = Utils.creerTextField("Montant", 200, 25);
		textNumDevis 	 = Utils.creerTextField("Numéro devis", 200, 25);
		txtMontantDevis  = Utils.creerTextField("Montant devis", 200, 25);
		textNature 		 = Utils.creerTextField("Nature", 200, 25);
		textDateDevis 	 = Utils.creerTextField("YYYY-MM-DD", 200, 25);
		// ComboBox pour SIRET et NumFiscale
		comboSiret = new JComboBox<>();
		comboNumFiscale = new JComboBox<>();

		// Charger les biens louables
		chargerBiensLouables();

		comboType = new JComboBox<>(new String[]{"Impots", "Variable", "Travaux", "Assurances"});
		comboModePaiement = new JComboBox<>(new String[]{"CB", "Chèque"});

		cbxPayerLocataire = new JCheckBox("Payer par le locataire");
		cbxPayerLocataire.setOpaque(false);

		// Champs Travaux
		idTravaux = Utils.creerTextField("ID Travaux", 200, 25);
		raison    = Utils.creerTextField("Raison", 200, 25);

		//champs impots
		Id_Impots  = Utils.creerTextField("Id Impots", 200, 25);
		AnneImpots =Utils.creerTextField("Anne", 200, 25);
		TypeImpots = Utils.creerTextField("Type", 200, 25);
		checkBox   = new JCheckBox();

		//champs assurances
		IdAssurance   = Utils.creerTextField("Id Assurance", 200, 25);
		TypeAssurance = Utils.creerTextField("Type", 200, 25);
		Annee         = Utils.creerTextField("Annee", 200, 25);

		//champs variable
		Id_Variables = Utils.creerTextField("Id Variable", 200, 25);
		service = Utils.creerTextField("Service", 200, 25);
		fournisseur = Utils.creerTextField("Fournisseur", 200, 25);
		typeVariable = Utils.creerTextField("Type", 200, 25);
		prixUnitaire = Utils.creerTextField("Prix unitaire", 200, 25);
		Id_Variables      = Utils.creerTextField("Id Variable", 200, 25);
		service           = Utils.creerTextField("Service", 200, 25);
		fournisseur       = Utils.creerTextField("Fournisseur", 200, 25);
		comboTypeVariable = new JComboBox<>(new String[]{"EAU", "GAZ", "ELECTRICITE"});
		comboTypeVariable.setName("comboTypeVariable");
		comboTypeVariable.setPreferredSize(new Dimension(200, 28));
		comboTypeVariable.setVisible(false);

		//panel associé
		//--travaux
		panelTravaux = creerLigne("Id travaux :", idTravaux);
		panelRaison  = creerLigne("Raison :", raison);

		//-- impots 
		panelId_Impot  = creerLigne("Impots : ",Id_Impots);
		panelAnneImpot =	creerLigne("Anne : ",AnneImpots);
		panelTypeImpot = creerLigne("Type : ",TypeImpots);
		panelRecup	   =	creerLigne("Recuperable (0/N) : ",checkBox);

		//-----assurances
		panelAssurance      = creerLigne("Id Assurance : ",IdAssurance);
		panelType_Assurance	= creerLigne("Type : ",TypeAssurance);
		panelAnneAssurance	= creerLigne("Annee : ",Annee);

		//--variable
		panelId_Variable	= creerLigne("Id Variable : ",Id_Variables);
		panelService	= creerLigne("Service : ",service);
		panelFournisseur	= creerLigne("Fournisseur : ",fournisseur);
		panelTypeVar	= creerLigne("Type : ",typeVariable);
		panelPrixUnitaire	= creerLigne("Prix unitaire : ",prixUnitaire);
		panelId_Variable = creerLigne("Id Variable : ",Id_Variables);
		panelService	 = creerLigne("Service : ",service);
		panelFournisseur = creerLigne("Fournisseur : ",fournisseur);
		panelTypeVar     = creerLigne("Type : ", comboTypeVariable);

		// Rendre tous les panels conditionnels invisibles initialement
		panelTravaux.setVisible(false);
		panelRaison.setVisible(false);
		panelId_Impot.setVisible(false);
		panelAnneImpot.setVisible(false);
		panelTypeImpot.setVisible(false);
		panelRecup.setVisible(false);
		panelAssurance.setVisible(false);
		panelType_Assurance.setVisible(false);
		panelAnneAssurance.setVisible(false);
		panelId_Variable.setVisible(false);
		panelService.setVisible(false);
		panelFournisseur.setVisible(false);
		panelTypeVar.setVisible(false);
		panelPrixUnitaire.setVisible(false);


		/* ===================== AJOUT DES CHAMPS ===================== */
		form.add(creerLigne("Numéro facture :", textNumFac));
		form.add(Box.createVerticalStrut(2));
		form.add(creerLigne("Date facture :", textDate_Facture));
		form.add(Box.createVerticalStrut(2));
		form.add(creerLigne("Type facture :", comboType));
		form.add(Box.createVerticalStrut(2));
		form.add(creerLigne("Montant :", textMontant));
		form.add(Box.createVerticalStrut(2));
		form.add(creerLigne("Mode de paiement :", comboModePaiement));
		form.add(Box.createVerticalStrut(2));
		form.add(creerLigne("Numéro devis :", textNumDevis));
		form.add(Box.createVerticalStrut(2));
		form.add(creerLigne("Montant devis :", txtMontantDevis));
		form.add(Box.createVerticalStrut(2));
		form.add(creerLigne("N° SIRET :", comboSiret));
		form.add(Box.createVerticalStrut(2));
		form.add(creerLigne("Nature :", textNature));
		form.add(Box.createVerticalStrut(2));
		form.add(creerLigne("Date devis :", textDateDevis));
		form.add(Box.createVerticalStrut(2));
		form.add(creerLigne("Numéro fiscal bien :", comboNumFiscale));

		// Les panels conditionnels seront ajoutés et rendus visibles/invisibles selon le type
		// dans la méthode afficherChampsSelonType()

		// Ajouter tous les panels conditionnels (initialement invisibles)
		form.add(panelTravaux);
		form.add(Box.createVerticalStrut(2));
		form.add(panelRaison);
		form.add(Box.createVerticalStrut(2));
		form.add(panelId_Impot);
		form.add(Box.createVerticalStrut(2));
		form.add(panelAnneImpot);
		form.add(Box.createVerticalStrut(2));
		form.add(panelTypeImpot);
		form.add(Box.createVerticalStrut(2));
		form.add(panelRecup);
		form.add(Box.createVerticalStrut(2));
		form.add(panelAssurance);
		form.add(Box.createVerticalStrut(2));
		form.add(panelType_Assurance);
		form.add(Box.createVerticalStrut(2));
		form.add(panelAnneAssurance);
		form.add(Box.createVerticalStrut(2));
		form.add(panelId_Variable);
		form.add(Box.createVerticalStrut(2));
		form.add(panelService);
		form.add(Box.createVerticalStrut(2));
		form.add(panelFournisseur);
		form.add(Box.createVerticalStrut(2));
		form.add(panelTypeVar);
		form.add(Box.createVerticalStrut(2));
		form.add(panelPrixUnitaire);

		// Créer et ajouter le panel checkbox
		JPanel panelCheckbox = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelCheckbox.setOpaque(false);
		panelCheckbox.add(cbxPayerLocataire);
		form.add(Box.createVerticalStrut(2));
		form.add(panelCheckbox);

		add(form, BorderLayout.CENTER);

		/* ===================== COMPORTEMENT ===================== */
		comboType.addActionListener(e -> afficherChampsSelonType());
		afficherChampsSelonType();

		/* ===================== BOUTONS ===================== */
		JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
		panelBoutons.setOpaque(false);

		boutonEnregistrer = Utils.creerBoutonPrimaire("Valider");
		boutonEnregistrer.setName("btnValider");
		boutonEnregistrer.addActionListener(gestion);

		boutonAnnuler = Utils.creerBouton("Annuler");
		boutonAnnuler.setName("btnAnnuler");
		boutonAnnuler.addActionListener(gestion);

		panelBoutons.add(boutonEnregistrer);
		panelBoutons.add(boutonAnnuler);

		add(panelBoutons, BorderLayout.SOUTH);
	}

	/* ===================== LOGIQUE D’AFFICHAGE ===================== */
	public void afficherChampsSelonType() {
		Object selectedItem = comboType.getSelectedItem();
		if (selectedItem == null) {
			return; // Si rien n'est sélectionné, ne rien faire
		}
		String type = selectedItem.toString();

		// Masquer tous les panels conditionnels
		panelTravaux.setVisible(false);
		panelRaison.setVisible(false);
		panelId_Impot.setVisible(false);
		panelAnneImpot.setVisible(false);
		panelTypeImpot.setVisible(false);
		panelRecup.setVisible(false);
		panelAssurance.setVisible(false);
		panelType_Assurance.setVisible(false);
		panelAnneAssurance.setVisible(false);
		panelId_Variable.setVisible(false);
		panelPrixUnitaire.setVisible(false);
		panelService.setVisible(false);
		panelFournisseur.setVisible(false);
		panelTypeVar.setVisible(false);  // Caché par défaut

		// Afficher les panels selon le type sélectionné
		switch (type) {
		case "Travaux":
			panelTravaux.setVisible(true);
			panelRaison.setVisible(true);
			break;

		case "Impots":
			panelId_Impot.setVisible(true);
			panelAnneImpot.setVisible(true);
			panelTypeImpot.setVisible(true);
			panelRecup.setVisible(true);
			break;

		case "Assurances":
			panelAssurance.setVisible(true);
			panelType_Assurance.setVisible(true);
			panelAnneAssurance.setVisible(true);
			break;

		case "Variable":
			panelId_Variable.setVisible(true);
			panelService.setVisible(true);
			panelFournisseur.setVisible(true);
			panelTypeVar.setVisible(true);
			panelPrixUnitaire.setVisible(true);
			panelTypeVar.setVisible(true);  // Maintenant visible avec le JComboBox
			comboTypeVariable.setVisible(true);  // Assurer la visibilité
			break;
		}

		// Revalider et repeindre le formulaire
		form.revalidate();
		form.repaint();
	}


	/* ===================== UTILITAIRE ===================== */
	public JPanel creerLigne(String label, JComponent comp) {
		JPanel p = new JPanel(new BorderLayout(10, 5));
		p.setOpaque(false);

		JLabel lbl = Utils.creerLabel(label);
		lbl.setPreferredSize(new Dimension(180, 30));

		p.add(lbl, BorderLayout.WEST);
		p.add(comp, BorderLayout.CENTER);
		p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

		return p;
	}

	private void chargerBiensLouables() {
		try {
			// Charger SIRET depuis Entreprise
			DaoEntreprise daoEntreprise = new DaoEntreprise();
			List<Entreprise> entreprises = daoEntreprise.findAll();

			for (Entreprise ent : entreprises) {
				comboSiret.addItem(ent.getSiret());
			}

			// Charger Numéro fiscal depuis BienLouable
			DaoBienLouable daoBien = new DaoBienLouable();
			List<BienLouable> biens = daoBien.findAll();

			for (BienLouable bien : biens) {
				comboNumFiscale.addItem(bien.getNumero_fiscale());
			}
		} catch (Exception e) {
			System.err.println("Erreur chargement biens/entreprises : " + e.getMessage());
			e.printStackTrace();
		}
	}

	/* ===================== GETTERS ===================== */
	public JComboBox<String> getComboType() {
		return comboType;
	}

	public String getIdTravaux() {
		return idTravaux.getText();
	}

	public String getRaison() {
		return raison.getText();
	}
	public Facture getFacture() {
		return facture;
	}

	public String getNature() {
		return textNature.getText();
	}

	public String getDateDevis() {
		return textDateDevis.getText();
	}

	public String getNumDevis() {
		return textNumDevis.getText();
	}

	public String getTextModePaiement() {
		return (String) comboModePaiement.getSelectedItem();
	}

	public String getTextMontant() {
		return textMontant.getText();
	}

	public String getTextNumFac() {
		return textNumFac.getText();
	}

	public String getTextDate_Facture() {
		return textDate_Facture.getText();
	}

	public String getTextSiret() {
		Object selected = comboSiret.getSelectedItem();
		return selected != null ? selected.toString() : "";
	}

	public String getTextNumFiscale() {
		Object selected = comboNumFiscale.getSelectedItem();
		return selected != null ? selected.toString() : "";
	}

	public double getTxtMontantDevis() {
		double montant_devis = Double.parseDouble(this.txtMontantDevis.getText().trim());
		return montant_devis;
	}

	public int getAnneImpots() {
		int anneImpots = Integer.parseInt(AnneImpots.getText().trim());
		return anneImpots;
	}

	public boolean getCbxPayerLocataire() {
		return cbxPayerLocataire.isSelected();
	}

	public GestionFenetrePrincipale getGfp() {
		return gfp;
	}

	public int getValeurCheckBox() {
		return checkBox.isSelected() ? 1 : 0;
	}

	public JTextField getId_Impots() {
		return Id_Impots;
	}

	public JTextField getTypeImpots() {
		return TypeImpots;
	}

	public JTextField getIdAssurance() {
		return IdAssurance;
	}

	public JTextField getTypeAssurance() {
		return TypeAssurance;
	}

	public GestionAjouterFacture getGestion() {
		return gestion;
	}

	public int getAnnee() {
		int anneAssurance = Integer.parseInt(Annee.getText().trim());
		return anneAssurance;
	}

	public JTextField getId_Variables() {
		return Id_Variables;
	}

	public JTextField getService() {
		return service;
	}

	public JTextField getFournisseur() {
		return fournisseur;
	}

	public String getPrixUnitaire() {
		return prixUnitaire.getText();
	}


	
	public String getTypeVariable() {
		return (String) comboTypeVariable.getSelectedItem();
	}

	public void afficherSucces(String msg) {
		JOptionPane.showMessageDialog(this, msg, "Succès", JOptionPane.INFORMATION_MESSAGE);
	}

	public void afficherErreur(String msg) {
		JOptionPane.showMessageDialog(this, msg);
	}

}
