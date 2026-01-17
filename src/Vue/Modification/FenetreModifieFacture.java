package Vue.Modification;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controleur.GestionFenetrePrincipale;
import Controleur.Modifier.GestionModificationFacture;
import Modele.Assurance;
import Modele.Facture;
import Modele.Impots;
import Modele.Travaux;
import Modele.Variable;
import Modele.dao.DaoAssurance;
import Modele.dao.DaoImpots;
import Modele.dao.DaoTravaux;
import Modele.dao.DaoVariable;
import Vue.Utils;
import Vue.ajouter.FenetreAjouterFacture;


public class FenetreModifieFacture extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	private final JTextField textNature;
	private final JTextField textDateDevis;
	private final JComboBox<String> comboSiret;
	private final JTextField textNumDevis;
	private final JComboBox<String> comboModePaiement;
	private final JTextField textMontant;
	private final JTextField textType;
	private final JTextField textNumFac;
	private final JTextField textDate_Facture;
	private final JTextField txtMontant_Devis;
	private final JComboBox<String> comboNumFiscale;
	private final JCheckBox cbxPayerLocataire;
	private JPanel form;
	private JTextField idTravaux;
	private JTextField raison, Id_Impots, AnneImpots, TypeImpots, IdAssurance, TypeAssurance,
	Annee, Id_Variables, service, fournisseur, typeVariable;

	private JPanel panelTravaux;
	private JPanel panelRaison , panelId_Impot,panelAnneImpot, panelTypeImpot,panelRecup, panelAssurance,panelType_Assurance,panelAnneAssurance
	,panelId_Variable,panelService, panelFournisseur, panelTypeVar;

	private JCheckBox cbx;
	private final GestionModificationFacture gestion;
	private final Facture facture;
	private Travaux travaux;
	private Impots impot;
	private Assurance assurance;
	private Variable variable;
	private final GestionFenetrePrincipale gfp;

	/**
	 * Initialise la fenêtre pour modifier une facture existante,
	 * préremplit les champs avec les informations actuelles de la facture,
	 * configure le bouton pour valider les modifications,
	 * et connecte la fenêtre au contrôleur dédié pour gérer les actions.
	 */
	public FenetreModifieFacture(GestionFenetrePrincipale gfp, Facture facture) {
		super("Modifier Facture", true, true, true, true);

		this.gfp = gfp;
		this.facture = facture;

		try {
			if (facture.getTypeFacture().equalsIgnoreCase("Travaux")) {
				DaoTravaux daoTrav = new DaoTravaux();
				for (Travaux t : daoTrav.findAll()) {
					if(t.getNumero().equals(facture.getNumero())) {
						this.travaux = t;
						break;
					}
				}
			}else if (facture.getTypeFacture().equalsIgnoreCase("Impots")) {
				DaoImpots daoImp = new DaoImpots();
				for (Impots i: daoImp.findAll()) {
					if (i.getNumero().equals(facture.getNumero())) {
						this.impot =  i;
						break;

					}
				}
			}else if (facture.getTypeFacture().equalsIgnoreCase("Assurance")) {
				DaoAssurance daoAssur = new DaoAssurance();
				for (Assurance a : daoAssur.findAll()) {
					if(a.getNumero().equals(facture.getNumero())) {
						this.assurance = a;
						break;

					}
				}
			}else if (facture.getTypeFacture().equalsIgnoreCase("Variable")) {				
				DaoVariable daoVar = new DaoVariable();
				// On cherche la variable correspondante dans toute la liste
				for (Variable var : daoVar.findAll()) {
					if (var.getNumero().equalsIgnoreCase(facture.getNumero())) {
						this.variable = var;
						break;

					}
				}
			}
		}catch(SQLException e1) {
			e1.printStackTrace();
		}

		setSize(500, 650);
		setLayout(new BorderLayout(15, 15));
		getContentPane().setBackground(Color.WHITE);

		/* ===================== TITRE ===================== */
		JLabel titre = Utils.creerTitre("Modifier facture");
		add(titre, BorderLayout.NORTH);

		/* ===================== CONTENU ===================== */
		form = new JPanel();
		form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
		form.setOpaque(false);
		form.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

		// Initialisation des champs
		textNumFac = Utils.creerTextField("Numéro facture", 200, 25);
		textNumFac.setEditable(false);
		textDate_Facture = Utils.creerTextField("YYYY-MM-DD", 200, 25);
		textType = Utils.creerTextField("Type facture", 200, 25);
		textType.setEditable(false);
		textType.setFocusable(false); // Empêche même la sélection du curseur
		textType.setBackground(new Color(240, 240, 240));
		textMontant = Utils.creerTextField("Montant", 200, 25);
		textMontant.setEditable(false);
		txtMontant_Devis = Utils.creerTextField("Montant devis", 200, 240);

		comboModePaiement = new JComboBox<>(new String[]{"CB", "cheque"});
		comboModePaiement.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		comboModePaiement.setPreferredSize(new Dimension(200, 25));

		textNumDevis = Utils.creerTextField("Numéro devis", 200, 25);

		comboSiret = new JComboBox<>();
		comboSiret.addItem("Sélectionner une entreprise");
		comboSiret.setName("comboSiret");
		comboSiret.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		comboSiret.setPreferredSize(new Dimension(200, 25));
		comboSiret.setEnabled(false);

		textNature = Utils.creerTextField("Nature", 200, 25);
		textDateDevis = Utils.creerTextField("YYYY-MM-DD", 200, 25);

		comboNumFiscale = new JComboBox<>();
		comboNumFiscale.addItem("Sélectionner un bien");
		comboNumFiscale.setName("comboNumFiscale");
		comboNumFiscale.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		comboNumFiscale.setPreferredSize(new Dimension(200, 25));
		comboNumFiscale.setEnabled(false);

		cbxPayerLocataire = new JCheckBox("Payer par locataire");
		cbxPayerLocataire.setFont(new Font("Segoe UI", Font.PLAIN, 13));

		// Champs Travaux
		idTravaux = Utils.creerTextField("ID Travaux", 200, 25);
		raison = Utils.creerTextField("Raison", 200, 25);

		//champs impots
		Id_Impots = Utils.creerTextField("Id Impots", 200, 25);
		AnneImpots =Utils.creerTextField("Anne", 200, 25);
		TypeImpots = Utils.creerTextField("Type", 200, 25);
		cbx = new JCheckBox();

		//champs assurances
		IdAssurance = Utils.creerTextField("Id Assurance", 200, 25);
		TypeAssurance = Utils.creerTextField("Type", 200, 25);
		Annee = Utils.creerTextField("Annee", 200, 25);

		//champs variable
		Id_Variables = Utils.creerTextField("Id Variable", 200, 25);
		service = Utils.creerTextField("Service", 200, 25);
		fournisseur = Utils.creerTextField("Fournisseur", 200, 25);
		typeVariable = Utils.creerTextField("Type", 200, 25);

		//panel associé
		//--travaux
		panelTravaux = Utils.creerLigne("Id travaux :", idTravaux);
		panelRaison = Utils.creerLigne("Raison :", raison);

		//-- impots
		panelId_Impot = Utils.creerLigne("Impots : ",Id_Impots);
		panelAnneImpot =	Utils.creerLigne("Anne : ",AnneImpots);
		panelTypeImpot	= Utils.creerLigne("Type : ",TypeImpots);
		panelRecup =	Utils.creerLigne("Recuperable (0/N) : ",cbx);

		//-----assurances
		panelAssurance=	Utils.creerLigne("Id Assurance : ",IdAssurance);
		panelType_Assurance	=Utils.creerLigne("Type : ",TypeAssurance);
		panelAnneAssurance	=Utils.creerLigne("Annee : ",Annee);

		//--variable
		panelId_Variable	= Utils.creerLigne("Id Variable : ",Id_Variables);
		panelService	= Utils.creerLigne("Service : ",service);
		panelFournisseur	= Utils.creerLigne("Fournisseur : ",fournisseur);
		panelTypeVar	= Utils.creerLigne("Type : ",typeVariable);



		// Ajout des champs avec espacement
		form.add(Utils.creerLigne("Numéro facture :", textNumFac));
		form.add(Utils.creerEspacementVertical(8));
		form.add(Utils.creerLigne("Date facture :", textDate_Facture));
		form.add(Utils.creerEspacementVertical(8));
		form.add(Utils.creerLigne("Type facture :", textType));
		form.add(Utils.creerEspacementVertical(8));
		form.add(Utils.creerLigne("Montant :", textMontant));
		form.add(Utils.creerEspacementVertical(8));
		form.add(Utils.creerLigne("Mode de paiement :", comboModePaiement));
		form.add(Utils.creerEspacementVertical(8));
		form.add(Utils.creerLigne("Numéro devis :", textNumDevis));
		form.add(Utils.creerEspacementVertical(8));
		form.add(Utils.creerLigne("Montant devis", txtMontant_Devis));
		form.add(Utils.creerEspacementVertical(8));
		form.add(Utils.creerLigne("N° SIRET :", comboSiret));
		form.add(Utils.creerEspacementVertical(8));
		form.add(Utils.creerLigne("Nature :", textNature));
		form.add(Utils.creerEspacementVertical(8));
		form.add(Utils.creerLigne("Date devis :", textDateDevis));
		form.add(Utils.creerEspacementVertical(8));
		form.add(Utils.creerLigne("Numéro fiscal bien :", comboNumFiscale));
		form.add(Utils.creerEspacementVertical(8));

		JPanel panelCheckbox = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelCheckbox.setOpaque(false);
		panelCheckbox.add(cbxPayerLocataire);
		form.add(panelCheckbox);

		add(form, BorderLayout.CENTER);

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


		remplirDonnees();
		/* ===================== COMPORTEMENT ===================== */
		afficherChampsSelonType();

		/* ===================== BOUTONS ===================== */
		JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
		panelBoutons.setOpaque(false);

		JButton bttValider = Utils.creerBoutonPrimaire("Enregistrer");
		bttValider.setName("btnModifierFacture");

		JButton btnAnnuler = Utils.creerBouton("Annuler");
		btnAnnuler.setName("btnAnnuler");

		// *** Créer le contrôleur APRÈS tous les composants ***
		this.gestion = new GestionModificationFacture(this, facture);

		bttValider.addActionListener(this.gestion);
		btnAnnuler.addActionListener(gestion);

		panelBoutons.add(bttValider);
		panelBoutons.add(btnAnnuler);
		add(panelBoutons, BorderLayout.SOUTH);

		// Pré-remplir les données
		remplirDonnees();

	}

	/* ===================== UTILITAIRES ===================== */
	/**
	 * Crée une ligne du formulaire avec un label à gauche et un composant à droite.
	 * Permet d'uniformiser l'affichage et d'éviter de répéter la mise en page pour chaque champ.
	 */


	/**
	 * Remplit les champs du formulaire avec les valeurs de la facture à modifier.
	 * Sert à afficher les infos actuelles dès l'ouverture de la fenêtre.
	 */
	private void remplirDonnees() {
		textNumFac.setText(facture.getNumero());
		if (facture.getDate_facture() != null) {
			textDate_Facture.setText(facture.getDate_facture().toString());
		}
		textType.setText(facture.getTypeFacture() != null ? facture.getTypeFacture() : "");
		textMontant.setText(String.valueOf(facture.getMontant()));
		txtMontant_Devis.setText(String.valueOf(facture.getMontantDevis()));
		
		if (facture.getMode_paiement() != null) {
			comboModePaiement.setSelectedItem(facture.getMode_paiement());
		}

		textNumDevis.setText(facture.getNumero_devis() != null ? facture.getNumero_devis() : "");

		if (facture.getSiret() != null) {
			comboSiret.addItem(facture.getSiret());
			comboSiret.setSelectedItem(facture.getSiret());
		}

		textNature.setText(facture.getNature() != null ? facture.getNature() : "");

		if (facture.getDate_devis() != null) {
			textDateDevis.setText(facture.getDate_devis().toString());
		}

		if (facture.getNumero_fiscale() != null) {
			comboNumFiscale.addItem(facture.getNumero_fiscale());
			comboNumFiscale.setSelectedItem(facture.getNumero_fiscale());
		}

		cbxPayerLocataire.setSelected(facture.getPayer_par_locataire());

		String type = facture.getTypeFacture();
		if (type.equalsIgnoreCase("Travaux")) {
			idTravaux.setText(travaux.getId_Travaux().toString());
			//le rendre non modifiable
			idTravaux.setEditable(false);
			idTravaux.setFocusable(false);
			idTravaux.setBackground(new Color(240, 240, 240));
			raison.setText(travaux.getRaison());


		} else if (type.equalsIgnoreCase("Impots")) {
			Id_Impots.setText(impot.getIdImpots());
			Id_Impots.setEditable(false);
			Id_Impots.setFocusable(false);
			Id_Impots.setBackground(new Color(240, 240, 240));

			AnneImpots.setText(String.valueOf(impot.getAnnee()));
			TypeImpots.setText(impot.getType());


		} else if (type.equalsIgnoreCase("Assurance") || textType.getText().equalsIgnoreCase("Assurances")) {

			IdAssurance.setText(assurance.getId_assurance());
			IdAssurance.setEditable(false);
			IdAssurance.setFocusable(false);
			IdAssurance.setBackground(new Color(240, 240, 240));
			TypeAssurance.setText(assurance.getType());
			Annee.setText(String.valueOf(assurance.getAnnee()));


		} else if (type.equalsIgnoreCase("Variable")) {

			Id_Variables.setText(this.variable.getId_Variable());
			Id_Variables.setEditable(false);
			Id_Variables.setFocusable(false);
			Id_Variables.setBackground(new Color(240, 240, 240));
			service.setText(this.variable.getService());
			fournisseur.setText(this.variable.getFournisseur());
			typeVariable.setText(this.variable.getEau_gaz_electricite());
		}


	}


	/**
	 * Affiche un message d'erreur sous forme de popup.
	 * Utilisé pour prévenir l'utilisateur en cas de saisie invalide ou d'échec.
	 */
	public void afficherErreur(String msg) {
		JOptionPane.showMessageDialog(this, msg, "Erreur", JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * Affiche un message de confirmation après une action réussie.
	 */
	public void afficherSucces(String msg) {
		JOptionPane.showMessageDialog(this, msg, "Succès", JOptionPane.INFORMATION_MESSAGE);
	}
	public void afficherChampsSelonType() {
		String type = textType.getText();
		if (type == null) return;

		// Masquer tout par défaut
		setPanelsVisible(false);

		// Utilisation de equalsIgnoreCase pour parer aux variations "Variable" vs "variable"
		if (type.equalsIgnoreCase("Travaux")) {
			panelTravaux.setVisible(true);
			panelRaison.setVisible(true);
		} else if (type.equalsIgnoreCase("Impots")) {
			panelId_Impot.setVisible(true);
			panelAnneImpot.setVisible(true);
			panelTypeImpot.setVisible(true);
			panelRecup.setVisible(true);
		} else if (type.equalsIgnoreCase("Assurance") || type.equalsIgnoreCase("Assurances")) {
			panelAssurance.setVisible(true);
			panelType_Assurance.setVisible(true);
			panelAnneAssurance.setVisible(true);
		} else if (type.equalsIgnoreCase("Variable")) {
			panelId_Variable.setVisible(true);
			panelService.setVisible(true);
			panelFournisseur.setVisible(true);
			panelTypeVar.setVisible(true);
		}

		form.revalidate();
		form.repaint();
	}
	private void setPanelsVisible(boolean visible) {
		panelTravaux.setVisible(visible);
		panelRaison.setVisible(visible);
		panelId_Impot.setVisible(visible);
		panelAnneImpot.setVisible(visible);
		panelTypeImpot.setVisible(visible);
		panelRecup.setVisible(visible);
		panelAssurance.setVisible(visible);
		panelType_Assurance.setVisible(visible);
		panelAnneAssurance.setVisible(visible);
		panelId_Variable.setVisible(visible);
		panelService.setVisible(visible);
		panelFournisseur.setVisible(visible);
		panelTypeVar.setVisible(visible);
	}
	/* ===================== GETTERS ===================== */
	public Travaux getTravaux() {
		return travaux;
	}

	public Impots getImpot() {
		return impot;
	}

	public Assurance getAssurance() {
		return assurance;
	}

	public Variable getV() {
		return this.variable;
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

	public String getType() {
		return textType.getText();
	}

	public String getTextNumFac() {
		return textNumFac.getText();
	}

	public String getTextDate_Facture() {
		return textDate_Facture.getText();
	}

	public String getTextSiret() {
		return (String) comboSiret.getSelectedItem();
	}

	public String getTextNumFiscale() {
		return (String) comboNumFiscale.getSelectedItem();
	}

	public JComboBox<String> getComboSiret() {
		return comboSiret;
	}

	public JComboBox<String> getComboNumFiscale() {
		return comboNumFiscale;
	}

	public boolean  getCbxPayerLocataire() {
		return cbxPayerLocataire.isSelected();
		
		
		
	}

	public Facture getFacture() {
		return facture;
	}

	public GestionFenetrePrincipale getGfp() {
		return gfp;
	}

	public JTextField getTextNature() {
		return textNature;
	}

	public JTextField getTextDateDevis() {
		return textDateDevis;
	}

	public JTextField getTextNumDevis() {
		return textNumDevis;
	}

	public JTextField getTextType() {
		return textType;
	}

	public JTextField getIdTravaux() {
		return idTravaux;
	}

	public JTextField getRaison() {
		return raison;
	}

	public JTextField getId_Impots() {
		return Id_Impots;
	}

	public JTextField getAnneImpots() {
		return AnneImpots;
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

	public JTextField getAnnee() {
		return Annee;
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

	public JTextField getTypeVariable() {
		return typeVariable;
	}
	public int getRecup() {
		if (this.cbx.isSelected()) {
			return 1;
	}
	return 0;
	}
}
