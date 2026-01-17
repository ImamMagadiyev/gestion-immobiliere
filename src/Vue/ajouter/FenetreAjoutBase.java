package Vue.ajouter;

import javax.swing.*;
import java.awt.*;

import Vue.Utils;

/**
 * FenetreAjoutBase : classe de base pour les fenêtres d'ajout.
 * Encapsule l'initialisation commune de toutes les fenêtres d'ajout :
 * - Taille, layout, couleur de fond
 * - Création du titre
 * - Création du formulaire (panel vertical)
 * 
 * Les sous-classes n'ont qu'à ajouter les champs spécifiques au formulaire.
 */
public class FenetreAjoutBase extends JInternalFrame {
	
	private static final long serialVersionUID = 1L;
	
	protected JLabel titre;
	protected JPanel form;
	protected JDesktopPane desktop;
	protected JPanel panelBoutons;
	
	/**
	 * Initialise la base d'une fenêtre d'ajout avec la structure commune.
	 * 
	 * @param titre_fenetre Le titre de la fenêtre (ex: "Ajouter un bâtiment")
	 * @param titre_contenu Le titre du contenu (ex: "Nouveau bâtiment")
	 * @param width La largeur de la fenêtre
	 * @param height La hauteur de la fenêtre
	 * @param desktop Le JDesktopPane parent
	 */
	public FenetreAjoutBase(String titre_fenetre, String titre_contenu, int width, int height, JDesktopPane desktop) {
		super(titre_fenetre, true, true, true, true);
		
		this.desktop = desktop;
		
		setSize(width, height);
		setVisible(true);
		setLayout(new BorderLayout(10, 10));
		getContentPane().setBackground(Color.WHITE);
		
		// Titre
		titre = Utils.creerTitre(titre_contenu);
		add(titre, BorderLayout.NORTH);
		
		// Formulaire
		form = new JPanel();
		form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
		form.setOpaque(false);
		form.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
		add(form, BorderLayout.CENTER);
		
		// Panel boutons (à remplir par les sous-classes)
		panelBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
		panelBoutons.setOpaque(false);
		add(panelBoutons, BorderLayout.SOUTH);
	}
	
	/**
	 * Retourne le panel du formulaire pour que les sous-classes puissent y ajouter des champs.
	 */
	public JPanel getForm() {
		return form;
	}
	
	/**
	 * Retourne le panel des boutons pour que les sous-classes puissent y ajouter des boutons.
	 */
	public JPanel getPanelBoutons() {
		return panelBoutons;
	}
	
	/**
	 * Crée une ligne du formulaire avec un label à gauche et un composant à droite.
	 * Permet d'uniformiser l'affichage.
	 */
	protected JPanel creerLigne(String label, JComponent comp, int labelWidth) {
		JPanel p = new JPanel(new BorderLayout(10, 5));
		p.setOpaque(false);
		JLabel lbl = Utils.creerLabel(label);
		lbl.setPreferredSize(new Dimension(labelWidth, 30));
		p.add(lbl, BorderLayout.WEST);
		p.add(comp, BorderLayout.CENTER);
		p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		return p;
	}
}
