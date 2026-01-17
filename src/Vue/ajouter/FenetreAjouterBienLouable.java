package Vue.ajouter;

import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controleur.GestionFenetrePrincipale;
import Controleur.Ajouter.GestionAjouterBienLouable;
import Modele.Batiment;
import Vue.Utils;

public class FenetreAjouterBienLouable extends FenetreAjoutBase {

	private static final long serialVersionUID = 1L;

	private GestionFenetrePrincipale gfp;

	private JTextField champNumeroFiscal, champAdresse, champCodePostal, champVille,
	champSurface, champPieces, champPeriodeConstruction, champEtage, champPorte;
	private JComboBox<String> champType, champBatiment;
	private JButton btnAjouter, btnAnnuler;

	protected JButton btnAjouterCompteur;
	private JPanel panelSurface, panelPieces, panelEtage, panelPorte;


	public FenetreAjouterBienLouable(GestionFenetrePrincipale gfp, JDesktopPane desktop) {
		super("Ajouter un bien louable", "Nouveau bien louable", 500, 600, desktop);
		this.gfp = gfp;


		// Champs du formulaire
		champNumeroFiscal = Utils.creerTextField("Numéro fiscal", 200, 28);
		champAdresse = Utils.creerTextField("Adresse", 200, 28);
		champCodePostal = Utils.creerTextField("Code postal", 200, 28);
		champVille = Utils.creerTextField("Ville", 200, 28);
		champSurface = Utils.creerTextField("Surface (m²)", 200, 28);
		champPieces = Utils.creerTextField("Nombre de pièces", 200, 28);
		champPeriodeConstruction = Utils.creerTextField("Période de construction", 200, 28);
		champEtage = Utils.creerTextField("Étage", 200, 28);
		champPorte = Utils.creerTextField("Porte", 200, 28);

		champNumeroFiscal = Utils.creerTextField("Numéro fiscal", 150, 25);
		champAdresse = Utils.creerTextField("Adresse", 150, 25);
		champCodePostal = Utils.creerTextField("Code postal", 150, 25);
		champVille = Utils.creerTextField("Ville", 150, 25);
		champSurface = Utils.creerTextField("Surface (m²)", 150, 25);
		champPieces = Utils.creerTextField("Nombre de pièces", 150, 25);
		champPeriodeConstruction = Utils.creerTextField("Période de construction", 150, 25);
		champEtage = Utils.creerTextField("Étage", 150, 25);
		champPorte = Utils.creerTextField("Porte", 150, 25);

		champType = new JComboBox<>();
		champType.addItem("Appartement");
		champType.addItem("Garage");
		champType.setName("champType");
		champType.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		champType.setPreferredSize(new Dimension(200, 28));

		champBatiment = new JComboBox<>();
		champBatiment.addItem("Sélectionner un bâtiment");
		champBatiment.setName("champBatiment");
		champBatiment.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		champBatiment.setPreferredSize(new Dimension(200, 28));


		// Créer les panels pour surface, pièces, étage et porte AVANT de les ajouter
		panelSurface = creerLigne("Surface :", champSurface, 180);
		panelPieces = creerLigne("Nombre de pièces :", champPieces, 180);
		panelEtage = creerLigne("Étage :", champEtage, 180);
		panelPorte = creerLigne("Porte :", champPorte, 180);

		// Ajout des champs dans l'ordre
		form.add(creerLigne("Numéro fiscal :", champNumeroFiscal, 180));
		form.add(Box.createVerticalStrut(8));
		form.add(creerLigne("Type de bien :", champType, 180));
		form.add(Box.createVerticalStrut(8));
		form.add(creerLigne("Bâtiment associé :", champBatiment, 180));
		form.add(Box.createVerticalStrut(8));
		form.add(creerLigne("Adresse :", champAdresse, 180));
		form.add(Box.createVerticalStrut(8));
		form.add(creerLigne("Code postal :", champCodePostal, 180));
		form.add(Box.createVerticalStrut(8));
		form.add(creerLigne("Ville :", champVille, 180));
		form.add(Box.createVerticalStrut(8));

		// Ajout conditionnel des panels (Surface et Pièces avant Étage et Porte)
		form.add(panelSurface);
		form.add(Box.createVerticalStrut(8));
		form.add(panelPieces);
		form.add(Box.createVerticalStrut(8));
		form.add(panelEtage);
		form.add(Box.createVerticalStrut(8));
		form.add(panelPorte);
		form.add(Box.createVerticalStrut(8));
		form.add(creerLigne("Période construction :", champPeriodeConstruction, 180));

		btnAjouter = Utils.creerBoutonPrimaire("Ajouter");
		btnAjouter.setName("btnAjouter");
		panelBoutons.add(btnAjouter);

		btnAnnuler = Utils.creerBouton("Annuler");
		btnAnnuler.setName("btnAnnuler");
		panelBoutons.add(btnAnnuler);

		btnAjouterCompteur = Utils.creerBouton("Ajouter un compteur");
		btnAjouterCompteur.setName("btnAjouterCompteur");
		btnAjouterCompteur.setEnabled(false);
		panelBoutons.add(btnAjouterCompteur);

		new GestionAjouterBienLouable(this);
		afficherChampsSelonType();
	}

	/**
	 * Affiche ou cache les champs selon le type de bien sélectionné.
	 * - Appartement : tous les champs visibles
	 * - Garage : "Surface" et "Nombre de pièces" visibles, "Étage" et "Porte" masqués
	 */
	public void afficherChampsSelonType() {
	    String type = getType();
	    boolean estAppartement = "Appartement".equals(type);
	    boolean estGarage = "Garage".equals(type);
	    
	    // Pour Appartement : tout visible
	    panelSurface.setVisible(estAppartement);
	    panelPieces.setVisible(estAppartement);
	    panelEtage.setVisible(estAppartement);
	    panelPorte.setVisible(estAppartement);
	    
	    // Pour Garage : Surface et Nombre de pièces visibles, Étage et Porte masqués
	    if (estGarage) {
	        panelSurface.setVisible(true);
	        panelPieces.setVisible(true);
	        panelEtage.setVisible(false);
	        panelPorte.setVisible(false);
	        
	        // Mise à null des valeurs des champs masqués
	        champEtage.setText("");
	        champPorte.setText("");
	    }
	    
	    // Réorganiser l'affichage
	    form.revalidate();
	    form.repaint();
	}

	/**
	 * Remplit la ComboBox des bâtiments avec leurs identifiants.
	 * Méthode appelée par le contrôleur après récupération des bâtiments en base.
	 */
	public void remplirBatiments(List<Batiment> liste) {
		champBatiment.removeAllItems();
		champBatiment.addItem("Sélectionner un bâtiment");
		for (Batiment b : liste) {
			champBatiment.addItem(b.getIdBatiment());
		}
	}

	/**
	 * Remplit automatiquement les champs adresse/ville/code postal/période de construction
	 * à partir du bâtiment sélectionné.
	 * Si aucun bâtiment n'est sélectionné (null), on vide les champs.
	 */
	public void remplirChampsBatiment(Batiment bat) {
		if (bat != null) {
			champAdresse.setText(bat.getAdresse());
			champVille.setText(bat.getVille());
			champCodePostal.setText(bat.getCodePostale());
			champPeriodeConstruction.setText(bat.getPeriodeDeConstruction().toString());
		} else {
			champAdresse.setText("");
			champVille.setText("");
			champCodePostal.setText("");
			champPeriodeConstruction.setText("");
		}
	}



	// ---------------- Getters ----------------
	public JTextField getChampNumeroFiscal() { return champNumeroFiscal; }
	public JTextField getAdresseField() { return champAdresse; }
	public JTextField getCodePostalField() { return champCodePostal; }
	public JTextField getVilleField() { return champVille; }
	public JTextField getChampSurface() { return champSurface; }
	public JTextField getChampPieces() { return champPieces; }
	public JTextField getChampPeriodeConstruction() { return champPeriodeConstruction; }
	public JTextField getChampEtage() { return champEtage; }
	public JTextField getChampPorte() { return champPorte; }

	public JComboBox<String> getChampType() { return champType; }
	public JComboBox<String> getChampBatiment() { return champBatiment; }

	public JPanel getPanelSurface() { return panelSurface; }
	public JPanel getPanelPieces() { return panelPieces; }
	public JPanel getFormPanel() { return form; }
	public JPanel getPanelEtage() { return panelEtage; }
	public JPanel getPanelPorte() { return panelPorte; }


	public JButton getBtnAjouter() { return btnAjouter; }
	public JButton getBtnAnnuler() { return btnAnnuler; }
	public JButton getBtnAjouterCompteur() { return btnAjouterCompteur; }

	public JDesktopPane getDesktopPane() { return desktop; }

	public GestionFenetrePrincipale getGfp() { return gfp; }

	/**
	 * Retourne l'index du bâtiment sélectionné sans l'item par défaut.
	 * Le -1 permet d'avoir le bon index par rapport à la liste envoyée par le contrôleur.
	 */
	public int getBatimentIndex() { return champBatiment.getSelectedIndex() - 1; }

	public String getNumeroFiscal() { return champNumeroFiscal.getText().trim(); }
	public String getAdresse() { return champAdresse.getText().trim(); }
	public String getCodePostal() { return champCodePostal.getText().trim(); }
	public String getVille() { return champVille.getText().trim(); }
	public String getSurface() { return champSurface.getText().trim(); }
	public String getPieces() { return champPieces.getText().trim(); }
	public String getType() { return (String) champType.getSelectedItem(); }
	public String getPeriodeConstruction() { return champPeriodeConstruction.getText().trim(); }
	public String getEtage() { return champEtage.getText().trim(); }
	public String getPorte() { return champPorte.getText().trim(); }
	public String getBatiment() { return (String) champBatiment.getSelectedItem(); }

	/**
	 * Affiche un message d'erreur sous forme de popup.
	 * Utilisé si une saisie est invalide ou s'il y a un problème lors de l'ajout.
	 */
	public void afficherErreur(String msg) { JOptionPane.showMessageDialog(this, msg, "Erreur", JOptionPane.WARNING_MESSAGE); }

	/**
	 * Affiche un message de confirmation après une action réussie.
	 * Utilisé quand le bien louable a bien été ajouté.
	 */
	public void afficherSucces(String msg) { JOptionPane.showMessageDialog(this, msg, "Succès", JOptionPane.INFORMATION_MESSAGE); }
}
