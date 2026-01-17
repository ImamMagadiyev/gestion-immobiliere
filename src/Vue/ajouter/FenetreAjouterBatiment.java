package Vue.ajouter;

import javax.swing.*;

import Controleur.GestionFenetrePrincipale;
import Controleur.Ajouter.GestionAjouterBatiment;

import java.awt.*;
import Vue.Utils;

/**
 * Fenetre_AjouterBatiment : JInternalFrame pour ajouter un bâtiment dans le système.
 *
 * Cette fenêtre permet de saisir toutes les informations nécessaires pour créer un bâtiment :
 * - Identifiant du bâtiment
 * - Adresse
 * - Ville
 * - Code postal
 * - Période de construction
 *
 * La fenêtre contient trois boutons principaux :
 * - "Ajouter" : pour créer le bâtiment via le contrôleur GestionAjouterBatiment
 * - "Annuler" : pour fermer la fenêtre
 * - "Ajouter un compteur" : pour ajouter un compteur au bâtiment (désactivé tant que le bâtiment n'est pas créé)
 *
 * La fenêtre est intégrée dans un JDesktopPane et communique avec le gestionnaire principal
 * via l'objet GestionFenetrePrincipale.
 *
 * Elle inclut également des méthodes utilitaires pour créer les lignes de formulaire et
 * afficher des messages d'erreur ou de succès.
 *
 * @param gfp Le gestionnaire de la fenêtre principale (GestionFenetrePrincipale)
 * @param desktop Le JDesktopPane parent dans lequel la fenêtre sera affichée
 */

public class FenetreAjouterBatiment extends FenetreAjoutBase {

	   private static final long serialVersionUID = 1L;

	    private JTextField champId;
	    private JTextField champAdresse;
	    private JTextField champVille;
	    private JTextField champCodePostal;
	    private JTextField champPeriodeDeConstruction;
	    private JButton btnAjouter;
	    protected JButton btnAjouterCompteur;
	    private JButton btnAnnuler;
	    
	    private GestionFenetrePrincipale gfp;

        /**
         * FenetreAjouterBatiment : constructeur de la fenêtre d'ajout bâtiment.
         * Initialise tous les champs, les boutons et branche le contrôleur GestionAjouterBatiment.
         */
	    public FenetreAjouterBatiment(GestionFenetrePrincipale gfp, JDesktopPane desktop) {
	        super("Ajouter un bâtiment", "Nouveau bâtiment", 500, 450, desktop);
	    	this.gfp = gfp;

	        champId = Utils.creerTextField("Identifiant du bâtiment", 200, 28);
	        champAdresse = Utils.creerTextField("Adresse", 200, 28);
	        champVille = Utils.creerTextField("Ville", 200, 28);
	        champCodePostal = Utils.creerTextField("Code postal", 200, 28);
	        champPeriodeDeConstruction = Utils.creerTextField("Période de construction (AAAA-MM-JJ)", 200, 28);

	        form.add(creerLigne("Identifiant :", champId, 180));
	        form.add(Box.createVerticalStrut(8));
	        form.add(creerLigne("Adresse :", champAdresse, 180));
	        form.add(Box.createVerticalStrut(8));
	        form.add(creerLigne("Ville :", champVille, 180));
	        form.add(Box.createVerticalStrut(8));
	        form.add(creerLigne("Code postal :", champCodePostal, 180));
	        form.add(Box.createVerticalStrut(8));
	        form.add(creerLigne("Période de construction :", champPeriodeDeConstruction, 180));

	        btnAjouter = Utils.creerBoutonPrimaire("Ajouter");
	        btnAjouter.setName("btnAjouterBatiment");
	        panelBoutons.add(btnAjouter);

	        btnAjouterCompteur = Utils.creerBouton("Ajouter un compteur");
	        btnAjouterCompteur.setName("btnAjouterCompteur");
	        btnAjouterCompteur.setEnabled(false);
	        panelBoutons.add(btnAjouterCompteur);

	        btnAnnuler = Utils.creerBouton("Annuler");
	        btnAnnuler.setName("btnAnnulerBatiment");
	        panelBoutons.add(btnAnnuler);

	        new GestionAjouterBatiment(this);
	    }


	    public JButton getBtnAjouter() {
	        return btnAjouter;
	    }

	    public JButton getBtnAnnuler() {
	        return btnAnnuler;
	    }
	    
	    

	    public JButton getBtnAjouterCompteur() {
			return btnAjouterCompteur;
		}

	    /**
	     * Retourne l'identifiant saisi du bâtiment (sans espaces).
	     * Utilisé par le contrôleur pour créer le bâtiment.
	     */
		public String getId() {
	        return champId.getText().trim();
	    }

	    public String getAdresse() {
	        return champAdresse.getText().trim();
	    }

	    public String getVille() {
	        return champVille.getText().trim();
	    }

	    public JPanel getPanelBoutons() {
	        return panelBoutons;
	    }

	    public String getCodePostal() {
	        return champCodePostal.getText().trim();
	    }
	    
	    public String getPeriodeDeConstruction() {
	        return champPeriodeDeConstruction.getText().trim();
	    }

	    public JPanel getFormPanel() {
	        return form;
	    }
	    
	    public GestionFenetrePrincipale getGfp() {
			return gfp;
		}


		public JTextField getIdField() { return champId; }
	    public JTextField getAdresseField() { return champAdresse; }
	    public JTextField getVilleField() { return champVille; }
	    public JTextField getCodePostalField() { return champCodePostal; }
	    public JTextField getPeriodeDeConstructionField() { return champPeriodeDeConstruction; }
	    public JDesktopPane getDesktopPane() { return desktop; }

	    public JButton getBtnAjouterField() { return btnAjouter; }

        /**
         * Affiche un message d'erreur sous forme de popup.
         * Utilisé si une saisie est invalide ou si l'ajout en base échoue.
         */
	    public void afficherErreur(String msg) {
	        JOptionPane.showMessageDialog(this, msg, "Erreur", JOptionPane.WARNING_MESSAGE);
	    }

        /**
         * Affiche un message de confirmation après une action réussie.
         * Utilisé quand le bâtiment a bien été ajouté.
         */
	    public void afficherSucces(String msg) {
	        JOptionPane.showMessageDialog(this, msg, "Succès", JOptionPane.INFORMATION_MESSAGE);
	    }
}
