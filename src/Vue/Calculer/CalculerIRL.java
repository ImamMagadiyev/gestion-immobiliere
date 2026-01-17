package Vue.Calculer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.text.DecimalFormat;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Controleur.GestionFenetrePrincipale;
import Controleur.Affichage.Gestion_Calcul_IRL;
import Modele.ContratDeLocation;
import Vue.FenetrePrincipale;
import Vue.Utils;

public class CalculerIRL extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	
	private Gestion_Calcul_IRL gestion;
	private FenetrePrincipale fen;
	private JPanel form;
	private JTextField txtLoyer;
	private JTextField txtAncienIrl;
	private JTextField txtIRL;
	private JTextField txtNouveauMontant;
	private final GestionFenetrePrincipale gfp;
	private ContratDeLocation contrat;
	private DecimalFormat df = new DecimalFormat("#.##");
	
	public CalculerIRL(FenetrePrincipale fen, GestionFenetrePrincipale gfp,ContratDeLocation  contrat) {
		this.fen = fen;
		this.gfp = gfp;
		this.contrat = contrat;
		setTitle("Calculer l'IRL");
		setClosable(true);
		setResizable(true);
		setIconifiable(true);
		setSize(500, 400);
		setLayout(new BorderLayout(10, 10));

		/* ===================== TITRE ===================== */
		JLabel titre = Utils.creerTitre("Calculer l'IRL");
		add(titre, BorderLayout.NORTH);
		
		/* ===================== CONTENU ===================== */
		form = new JPanel();
		form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
		form.setOpaque(false);
		
		// Initialisation des champs
		txtLoyer = Utils.creerTextField("Loyer", 200, 25);
		txtLoyer.setText(String.valueOf(this.contrat.getLoyer_aPayer()));
		txtLoyer.setEditable(false);
		
		txtAncienIrl = Utils.creerTextField("Ancien IRL", 200, 25);
		txtAncienIrl.setText(String.valueOf(this.contrat.getIRL()));
		txtAncienIrl.setEditable(false);
		
		txtIRL = Utils.creerTextField("Nouvel IRL", 200, 25);
		
		txtNouveauMontant = Utils.creerTextField("Nouveau Montant", 200, 25);
		txtNouveauMontant.setEditable(false);
		
		// Ajouter listener pour calcul automatique
		txtIRL.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) { calculerAutomatiquement(); }
			@Override
			public void removeUpdate(DocumentEvent e) { calculerAutomatiquement(); }
			@Override
			public void changedUpdate(DocumentEvent e) { calculerAutomatiquement(); }
		});
		
		// Champs de saisie
		form.add(Utils.creerLigne("Loyer :", txtLoyer));
		form.add(Utils.creerEspacementVertical(12));
		form.add(Utils.creerLigne("Ancien IRL :", txtAncienIrl));
		form.add(Utils.creerEspacementVertical(12));
		form.add(Utils.creerLigne("Nouvel IRL :", txtIRL));
		form.add(Utils.creerEspacementVertical(12));
		form.add(Utils.creerLigne("Nouveau Montant :", txtNouveauMontant));
		
		add(form, BorderLayout.CENTER);
		
		/* ===================== BOUTONS ===================== */
		JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
		panelBoutons.setOpaque(false);

		JButton btCalculer = Utils.creerBoutonPrimaire("Calculer");
		btCalculer.setName("btnCalculer");

		JButton btnAnnuler = Utils.creerBouton("Annuler");
		btnAnnuler.setName("btnAnnuler");
		
		this.gestion = new Gestion_Calcul_IRL(this);
		
		btCalculer.addActionListener(this.gestion);
		btnAnnuler.addActionListener(gestion);
		
		panelBoutons.add(btCalculer);
		panelBoutons.add(btnAnnuler);
		add(panelBoutons, BorderLayout.SOUTH);

	}



	public Gestion_Calcul_IRL getGestion() {
		return gestion;
	}

	public FenetrePrincipale getFen() {
		return fen;
	}

	public double getLoyer() {
		return Double.parseDouble(txtLoyer.getText());
	}

	public double getIRL() {
		return Double.parseDouble(txtIRL.getText());
	}

	public double getNouveauMontant() {
		return Double.parseDouble(txtNouveauMontant.getText());
	}

	public double getAncienIrl() {
		return Double.parseDouble(txtAncienIrl.getText());
	}
	public GestionFenetrePrincipale getGfp() {
		return gfp;
	}



	public ContratDeLocation getContrat() {
		return contrat;
	}



	public void setContrat(ContratDeLocation contrat) {
		this.contrat = contrat;
	}
	
	/**
	 * Calcule automatiquement le nouveau montant en fonction de l'IRL saisi
	 */
	private void calculerAutomatiquement() {
		try {
			String irlText = txtIRL.getText().trim();
			
			if (irlText.isEmpty()) {
				txtNouveauMontant.setText("");
				return;
			}
			
			double loyer = getLoyer();
			double ancienIRL = getAncienIrl();
			double nouvelIRL = Double.parseDouble(irlText);
			
			if (nouvelIRL <= 0) {
				txtNouveauMontant.setText("");
				return;
			}
			
			if (ancienIRL <= 0) {
				txtNouveauMontant.setText("");
				return;
			}
			
			double nouveauMontant = loyer * (nouvelIRL / ancienIRL);
			txtNouveauMontant.setText(df.format(nouveauMontant));
			
		} catch (NumberFormatException e) {
			txtNouveauMontant.setText("");
		}
	}
	
	/**
	 * Valide les données saisies avant enregistrement
	 */
	public boolean validerSaisie() {
		if (txtIRL.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, 
				"Veuillez entrer le nouvel IRL", 
				"Champ vide", 
				JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		try {
			double nouvelIRL = Double.parseDouble(txtIRL.getText().trim());
			if (nouvelIRL <= 0) {
				JOptionPane.showMessageDialog(this, 
					"L'IRL doit être un nombre positif", 
					"Valeur invalide", 
					JOptionPane.WARNING_MESSAGE);
				return false;
			}
			return true;
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, 
				"L'IRL doit être un nombre valide (ex: 125.50)", 
				"Format invalide", 
				JOptionPane.WARNING_MESSAGE);
			return false;
		}
	}}