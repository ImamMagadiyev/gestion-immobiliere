package Vue;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.OverlayLayout;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import Controleur.GestionFenetrePrincipale;
import Modele.ContratDeLocation;
import Modele.Facture;
import Modele.Locataire;
import Modele.Variable;
import Modele.dao.DaoContratDeLocation;
import Modele.dao.DaoFacture;
import Modele.dao.DaoLocataire;
import Modele.dao.DaoVariable;
import Vue.Affichage.FenetreAfficherChargesLot;



public class FenetrePrincipale extends JFrame {

	private static final long serialVersionUID = 1L;

	private JDesktopPane desktopPane;
	private JPanel contenuCentral;
	private CardLayout cardLayout;
	private JPanel blocageContenu;

	private GestionFenetrePrincipale gestionFenetrePrincipale;
	private JTextField champPeriode;

	public JButton btnAccueil;
	public JButton btnBiens;
	public JButton btnLocations;
	public JButton btnBaux;
	public JButton btnEntreprises;
	public JButton btnFactures;
	public JButton boutonImporterCSV;
	public JButton btnLocataires;
	public JButton btnArchives;
	private JButton btnRechercher;

	public JMenuItem itemDeconnexion;
	public JMenuItem itemQuitter;

	private JTable tableauImpot;
	private JTable tableauFactures;
	private JTable tableauLogement;
	private JTable tableauBatiment;
	private JTable tableauLocataires;
	private JTable tableauEntreprises;
	private JTable tableauLoyer;
	private JTable tableauTravaux;
	private JTable tableauBaux;
	private JTable tableauVariable;
	private JTable tableauCompteurArchives;
	private JTable tableauLocationArchives;
	private JTable tableauLocataireArchives;
	private JTable tableauDiagnosticArchives;
	private JTable tableauFactureArchives;
	private JTable tableauTravauxArchives;
	private JTable tableauContratDeLocationArchives;

	private JTable tableauCautionArchives;
	private JTable tableauAssurance;
	private JTable tableauAgence;
	private JTabbedPane ongletsFactures;

	private JComboBox<String> comboType;
	private JComboBox<String> comboBienFactures;
	
	// Variables pour détecter le double-clic manuel
	private long dernierClickFactures = 0;
	private int dernierRow = -1;

	public FenetrePrincipale() {
		initFrame();
		this.gestionFenetrePrincipale = new GestionFenetrePrincipale(this);
		initMenueBarDuHaut();
		initCentralCards();

		montrerPage("ACCUEIL");
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void initFrame() {
		setTitle("Accueil - Gestion Locative");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1300, 800);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().setBackground(Utils.DARK_GRAY);
	}

	private void initMenueBarDuHaut() {
		int hauteurBarre = 60;
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Utils.WHITE_BG);
		menuBar.setPreferredSize(new Dimension(menuBar.getWidth(), hauteurBarre));
		menuBar.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0, 0, 2, 0, Utils.PRIMARY_BLUE),
				BorderFactory.createEmptyBorder(8, 15, 8, 15)
				));
		menuBar.setOpaque(true);

		JLabel labelLogo = chargerLogo(45);
		labelLogo.setVerticalAlignment(SwingConstants.CENTER);
		labelLogo.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 20));
		menuBar.add(labelLogo);
		menuBar.add(Box.createHorizontalStrut(15));

		Font menuFont = Utils.MENU_FONT;
		Color menuColor = Utils.TEXT_DARK;

		// Créer tous les boutons du menu en une boucle
		String[] menuNames = {"Accueil", "Biens", "Locations", "Baux", "Entreprises", "Factures", "Locataires", "Archives"};
		for (String name : menuNames) {
			JButton btn = createStyledButton(name, menuFont, menuColor);
			btn.setName("btn" + name);
			btn.addActionListener(gestionFenetrePrincipale);
			menuBar.add(btn);
			menuBar.add(Box.createHorizontalStrut(12));
			
			// Assigner à la variable d'instance correspondante
			try {
				java.lang.reflect.Field field = this.getClass().getDeclaredField("btn" + name);
				field.setAccessible(true);
				field.set(this, btn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		JMenu menuFichier = createStyledMenu("Compte", menuFont, menuColor);
		itemDeconnexion = createStyledMenuItem("Déconnexion", menuFont);
		itemDeconnexion.setName("itemDeconnexion");
		itemDeconnexion.addActionListener(gestionFenetrePrincipale);

		itemQuitter = createStyledMenuItem("Quitter", menuFont);
		itemQuitter.setName("itemQuitter");
		itemQuitter.addActionListener(gestionFenetrePrincipale);

		menuFichier.add(itemDeconnexion);
		menuFichier.addSeparator();
		menuFichier.add(itemQuitter);
		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(menuFichier);

		setJMenuBar(menuBar);
	}

	private void initCentralCards() {
		cardLayout = new CardLayout();

		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setLayout(new OverlayLayout(layeredPane));

		contenuCentral = new JPanel(cardLayout);
		contenuCentral.setBackground(Utils.LIGHT_GRAY);

		JPanel pageAccueil = creerPageAccueil();
		JPanel pageBiens = creerPageBiens();
		JPanel pageLocations = creerPageLocations();
		JPanel pageBaux = creerPageBaux();
		JPanel pageEntreprises = creerPageCharges();
		JPanel pageFactures = creerPageFactures();
		JPanel pageLocataires = creerPageLocataires();
		JPanel pageArchives = creerPageArchives();

		contenuCentral.add(pageAccueil, "ACCUEIL");
		contenuCentral.add(pageBiens, "BIENS");
		contenuCentral.add(pageLocations, "LOCATIONS");
		contenuCentral.add(pageBaux, "BAUX");
		contenuCentral.add(pageEntreprises, "ENTREPRISES");
		contenuCentral.add(pageFactures, "FACTURES");
		contenuCentral.add(pageLocataires, "LOCATAIRES");
		contenuCentral.add(pageArchives, "ARCHIVES");

		desktopPane = new JDesktopPane();
		desktopPane.setOpaque(false);

		// --- panneau de blocage juste au-dessus du contenu, mais sous le desktopPane ---
		blocageContenu = new JPanel();
		blocageContenu.setOpaque(false);
		blocageContenu.setVisible(false); // caché par défaut

		// Ces listeners vides permettent de consommer les clics / mouvements / molette
		blocageContenu.addMouseListener(new java.awt.event.MouseAdapter() {});
		blocageContenu.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {});
		blocageContenu.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
			@Override
			public void mouseWheelMoved(java.awt.event.MouseWheelEvent e) {
				// on ne fait rien, on bloque juste l'évènement
			}
		});

		// Important pour l’OverlayLayout : alignement identique au contenu
		blocageContenu.setAlignmentX(contenuCentral.getAlignmentX());
		blocageContenu.setAlignmentY(contenuCentral.getAlignmentY());

		// Ordre des couches : contenu -> blocage -> desktopPane (JInternalFrame)
		layeredPane.add(contenuCentral, Integer.valueOf(0));  // pages
		layeredPane.add(blocageContenu, Integer.valueOf(1));  // bloque les clics sur les pages
		layeredPane.add(desktopPane, Integer.valueOf(2));     // JInternalFrame au-dessus

		getContentPane().add(layeredPane, BorderLayout.CENTER);
	}

	public void montrerPage(String nomPage) {
		cardLayout.show(contenuCentral, nomPage);
	}

	public JDesktopPane getDesktopPane() {
		return desktopPane;
	}

	private JButton createStyledButton(String text, Font font, Color color) {
		JButton button = new JButton(text);
		button.setFont(font);
		button.setForeground(color);
		button.setBackground(Utils.WHITE_BG);
		button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
		button.setFocusPainted(false);
		button.setOpaque(true);

		button.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				button.setBackground(Utils.LIGHT_BLUE);
				button.setForeground(Utils.PRIMARY_BLUE);
			}
			public void mouseExited(MouseEvent evt) {
				button.setBackground(new Color(245, 247, 250));
				button.setForeground(color);
			}
		});

		return button;
	}

	private JMenu createStyledMenu(String texte, Font font, Color color) {
		JMenu menu = new JMenu(texte);
		menu.setFont(font);
		menu.setForeground(color);
		menu.setBackground(Utils.WHITE_BG);
		menu.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
		menu.setOpaque(true);

		menu.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				menu.setForeground(Utils.CHART_BLUE1);
			}
			public void mouseExited(MouseEvent evt) {
				menu.setForeground(color);
			}
		});

		return menu;
	}

	private JMenuItem createStyledMenuItem(String texte, Font font) {
		JMenuItem item = new JMenuItem(texte);
		item.setFont(font);
		item.setForeground(Utils.TEXT_LIGHT);
		item.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		item.setBackground(Color.WHITE);

		item.addMouseListener(new MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				item.setBackground(Utils.LIGHT_BLUE);
				item.setForeground(Utils.CHART_BLUE1);
			}
			public void mouseExited(MouseEvent evt) {
				item.setBackground(Color.WHITE);
				item.setForeground(Utils.TEXT_LIGHT);
			}
		});

		return item;
	}

	// Méthode helper pour assigner une variable d'instance via réflection
	private void assignFieldValue(String fieldName, Object value) {
		try {
			java.lang.reflect.Field field = this.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(this, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private JLabel chargerLogo(int hauteur) {
		JLabel labelLogo = new JLabel();
		try {
			String chemin = "src/icon/logo (2).png"; 
			ImageIcon icon = new ImageIcon(chemin);
			if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
				Image image = icon.getImage();
				int largeur = (int) (image.getWidth(null) * (hauteur / (double) image.getHeight(null)));
				Image imgRedim = image.getScaledInstance(largeur, hauteur, Image.SCALE_SMOOTH);
				labelLogo.setIcon(new ImageIcon(imgRedim));
				labelLogo.setHorizontalAlignment(SwingConstants.CENTER);
			} else {
				labelLogo.setText("LOGO");
				labelLogo.setFont(Utils.BOLD_14_FONT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			labelLogo.setText("LOGO");
		}
		return labelLogo;
	}

	//////////////////////////////////////////////////
	//												//
	//                 PAGE ACCEUIL					//
	//												//
	//////////////////////////////////////////////////

	private JPanel creerPageAccueil() {
		JPanel panneauPrincipal = creerPanneauPage(30);

		JPanel ligneHaut = new JPanel();
		ligneHaut.setBackground(Utils.PRIMARY_BLUE);  
		ligneHaut.setPreferredSize(new Dimension(getWidth(), 2));  
		panneauPrincipal.add(ligneHaut, BorderLayout.NORTH); 

		panneauPrincipal.add(creerEnTetePage("Accueil"), BorderLayout.NORTH);

		JPanel zoneContenuCentral = new JPanel(new BorderLayout(20, 0));
		zoneContenuCentral.setOpaque(false);
		zoneContenuCentral.setBorder(BorderFactory.createEmptyBorder(30, 20, 20, 20));

		JPanel panneauGauche = creerPanneauActivitesRecent();
		zoneContenuCentral.add(panneauGauche, BorderLayout.WEST);

		JPanel panneauDroite = creerPanneauActionRapides();
		zoneContenuCentral.add(panneauDroite, BorderLayout.EAST); 

		panneauPrincipal.add(zoneContenuCentral, BorderLayout.CENTER);

		this.boutonImporterCSV = Utils.creerBoutonPrimaire("Importer CSV");
		boutonImporterCSV.setName("btnImporterCSV");

		boutonImporterCSV.addActionListener(this.gestionFenetrePrincipale);
		JPanel panneauCSV = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panneauCSV.setOpaque(false);
		panneauCSV.add(boutonImporterCSV);

		panneauPrincipal.add(panneauCSV, BorderLayout.SOUTH);

		return panneauPrincipal;
	}

	private JPanel creerPanneauActivitesRecent() {
		JPanel panneauPrincipal = new JPanel();
		panneauPrincipal.setLayout(new BoxLayout(panneauPrincipal, BoxLayout.Y_AXIS));
		panneauPrincipal.setOpaque(false);
		panneauPrincipal.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		panneauPrincipal.setPreferredSize(new Dimension(480, 600));

		// Titre principal
		JLabel titreActivites = new JLabel("Activités récentes");
		titreActivites.setFont(Utils.TITLE_FONT);
		titreActivites.setForeground(new Color(0, 102, 204));
		titreActivites.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
		panneauPrincipal.add(titreActivites);

		// Section Derniers baux
		JPanel sectionBaux = creerSectionActivite("Derniers baux", "baux");
		panneauPrincipal.add(sectionBaux);
		panneauPrincipal.add(Box.createVerticalStrut(15));

		// Section Derniers locataires
		JPanel sectionLocataires = creerSectionActivite("Derniers locataires", "locataires");
		panneauPrincipal.add(sectionLocataires);
		panneauPrincipal.add(Box.createVerticalStrut(15));

		// Section Dernières factures
		JPanel sectionFactures = creerSectionActivite("Dernières factures", "factures");
		panneauPrincipal.add(sectionFactures);

		return panneauPrincipal;
	}

	private JPanel creerSectionActivite(String titreSection, String type) {
		JPanel panneauSection = new JPanel();
		panneauSection.setLayout(new BorderLayout());
		panneauSection.setBackground(Color.WHITE);
		panneauSection.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(0, 102, 204), 1),
				BorderFactory.createEmptyBorder(10, 12, 10, 12)
				));

		// Titre de la section
		JLabel labelTitre = new JLabel(titreSection);
		labelTitre.setFont(Utils.BOLD_14_FONT);
		labelTitre.setForeground(new Color(50, 50, 50));
		labelTitre.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
		panneauSection.add(labelTitre, BorderLayout.NORTH);

		// Liste des activités
		JPanel listeActivites = new JPanel();
		listeActivites.setLayout(new BoxLayout(listeActivites, BoxLayout.Y_AXIS));
		listeActivites.setOpaque(false);

		try {
			List<String> activites = chargerActivitesRecent(type);
			if (activites == null || activites.isEmpty()) {
				JLabel labelVide = new JLabel("Aucune activité récente");
				labelVide.setFont(new Font("Segoe UI", Font.ITALIC, 12));
				labelVide.setForeground(new Color(150, 150, 150));
				listeActivites.add(labelVide);
			} else {
				for (String activite : activites) {
					if (activite != null && !activite.trim().isEmpty()) {
						JLabel labelActivite = new JLabel("• " + activite);
						labelActivite.setFont(Utils.NORMAL_12_FONT);
						labelActivite.setForeground(new Color(70, 70, 70));
						labelActivite.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 0));
						listeActivites.add(labelActivite);
					}
				}
				if (listeActivites.getComponentCount() == 0) {
					JLabel labelVide = new JLabel("Aucune activité récente");
					labelVide.setFont(new Font("Segoe UI", Font.ITALIC, 12));
					labelVide.setForeground(new Color(150, 150, 150));
					listeActivites.add(labelVide);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JLabel labelErreur = new JLabel("Erreur: " + e.getMessage());
			labelErreur.setFont(Utils.ITALIC_11_FONT);
			labelErreur.setForeground(Color.RED);
			listeActivites.add(labelErreur);
		} catch (Exception e) {
			e.printStackTrace();
			JLabel labelErreur = new JLabel("Erreur inattendue");
			labelErreur.setFont(new Font("Segoe UI", Font.ITALIC, 12));
			labelErreur.setForeground(Color.RED);
			listeActivites.add(labelErreur);
		}

		JScrollPane scrollPane = new JScrollPane(listeActivites);
		scrollPane.setBorder(null);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setPreferredSize(new Dimension(0, 120));
		scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

		panneauSection.add(scrollPane, BorderLayout.CENTER);

		return panneauSection;
	}

	private JPanel creerPanneauActionRapides() {
		JPanel panneauPrincipal = new JPanel();
		panneauPrincipal.setLayout(new BoxLayout(panneauPrincipal, BoxLayout.Y_AXIS));
		panneauPrincipal.setOpaque(false);
		panneauPrincipal.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		panneauPrincipal.setPreferredSize(new Dimension(500, 600));

		// Titre
		JLabel titrActionRapides = new JLabel("Actions rapides");
		titrActionRapides.setFont(Utils.TITLE_FONT);
		titrActionRapides.setForeground(new Color(0, 102, 204));
		titrActionRapides.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		panneauPrincipal.add(titrActionRapides);

		Color couleurBouton = new Color(0, 102, 204);

		// Bouton Ajouter Facture
		JPanel panelFacture = creerBoutonActionRapide("Ajouter Facture", couleurBouton);
		panelFacture.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (gestionFenetrePrincipale != null) {
					gestionFenetrePrincipale.openAjouterFacture();
				}
			}
		});
		panneauPrincipal.add(panelFacture);
		panneauPrincipal.add(Box.createVerticalStrut(15));

		// Bouton Ajouter Locataire
		JPanel panelLocataire = creerBoutonActionRapide("Ajouter Locataire", couleurBouton);
		panelLocataire.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (gestionFenetrePrincipale != null) {
					gestionFenetrePrincipale.openAjouterLocataire();
				}
			}
		});
		panneauPrincipal.add(panelLocataire);
		panneauPrincipal.add(Box.createVerticalStrut(15));

		// Bouton Ajouter Bien
		JPanel panelBien = creerBoutonActionRapide("Ajouter Bien Louable", couleurBouton);
		panelBien.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (gestionFenetrePrincipale != null) {
					gestionFenetrePrincipale.openAjouterBien();
				}
			}
		});
		panneauPrincipal.add(panelBien);
		panneauPrincipal.add(Box.createVerticalStrut(15));

		// Bouton Calculer Charges
		JPanel panelCharges = creerBoutonActionRapide("Calculer Charges", couleurBouton);
		panelCharges.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (gestionFenetrePrincipale != null) {
					gestionFenetrePrincipale.openCalculerCharges();
				}
			}
		});
		panneauPrincipal.add(panelCharges);

		panneauPrincipal.add(Box.createVerticalGlue());

		return panneauPrincipal;
	}

	private JPanel creerBoutonActionRapide(String titre, Color couleur) {
		JPanel panneau = new JPanel();
		panneau.setLayout(new BorderLayout(10, 5));
		panneau.setBackground(couleur);
		panneau.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createLineBorder(couleur, 1),
			BorderFactory.createEmptyBorder(15, 15, 15, 15)
		));
		panneau.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		panneau.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

		// Titre du bouton
		JLabel labelTitre = new JLabel(titre);
		labelTitre.setFont(Utils.BOLD_12_FONT);
		labelTitre.setForeground(Color.WHITE);

		panneau.add(labelTitre, BorderLayout.CENTER);

		// Effet hover
		panneau.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				panneau.setBackground(couleur.darker());
			}

			@Override
			public void mouseExited(MouseEvent e) {
				panneau.setBackground(couleur);
			}
		});
		return panneau;
	}

	private List<String> chargerActivitesRecent(String type) throws SQLException {
		List<String> activites = new java.util.ArrayList<>();
		int limite = 5;

		try {
			switch (type) {
			case "baux":
				DaoContratDeLocation daoContrat = new DaoContratDeLocation();
				List<ContratDeLocation> contrats = daoContrat.findAll();
				if (contrats != null && !contrats.isEmpty()) {
					int maxBaux = Math.min(limite, contrats.size());
					for (int i = 0; i < maxBaux; i++) {
						ContratDeLocation contrat = contrats.get(i);
						if (contrat != null) {
							String idContrat = contrat.getIdContrat() != null ? contrat.getIdContrat() : "N/A";
							String numFiscal = contrat.getNumero_fiscale() != null ? contrat.getNumero_fiscale() : "N/A";
							activites.add("Bail " + idContrat + " - " + numFiscal);
						}
					}
				}
				break;

			case "locataires":
				DaoLocataire daoLocataire = new DaoLocataire();
				List<Locataire> locataires = daoLocataire.findAll();
				if (locataires != null && !locataires.isEmpty()) {
					int maxLoc = Math.min(limite, locataires.size());
					for (int i = 0; i < maxLoc; i++) {
						Locataire loc = locataires.get(i);
						if (loc != null) {
							String nom = loc.getNom() != null ? loc.getNom() : "";
							String prenom = loc.getPrenom() != null ? loc.getPrenom() : "";
							String id = loc.getIdLocataire() != null ? loc.getIdLocataire() : "N/A";
							activites.add(nom + " " + prenom + " (" + id + ")");
						}
					}
				}
				break;

			case "factures":
				DaoFacture daoFacture = new DaoFacture();
				List<Facture> factures = daoFacture.findAll();
				if (factures != null && !factures.isEmpty()) {
					int maxFact = Math.min(limite, factures.size());
					for (int i = 0; i < maxFact; i++) {
						Facture facture = factures.get(i);
						if (facture != null) {
							String numero = facture.getNumero() != null ? facture.getNumero() : "N/A";
							double montant = facture.getMontant();
							activites.add("Facture " + numero + " - " + String.format("%.2f", montant) + "€");
						}
					}
				}
				break;
			}
		} catch (SQLException e) {
			System.err.println("Erreur SQL lors du chargement des activités (" + type + "): " + e.getMessage());
			throw e;
		}

		return activites;
	}



	//////////////////////////////////////////////////
	//												//
	//                  PAGE BIENS     				//
	//												//
	//////////////////////////////////////////////////

	private JPanel creerPageBiens() {
		JPanel panneauPrincipal = creerPanneauPage(40);

		panneauPrincipal.add(creerEnTetePage("Gestion des biens"), BorderLayout.NORTH);

		JPanel zoneCentrale = new JPanel();
		zoneCentrale.setLayout(new BoxLayout(zoneCentrale, BoxLayout.Y_AXIS));
		zoneCentrale.setOpaque(false);
		zoneCentrale.add(Utils.creerEspacementVertical(10));

		// ===================================================
		// BLOC BÂTIMENT
		// ===================================================
		String[] colonnesBatiment = {"ID bâtiment", "Adresse", "Ville", "Code postal"};
		JTable[] refBatiment = new JTable[1];
		JPanel blocBatiment = creerBlocTableAvecReference("Batiment", colonnesBatiment, new Object[0][0], refBatiment);
		tableauBatiment = refBatiment[0];
		gestionFenetrePrincipale.afficherTableauBatiment();

		JPanel boutonsBatiment = creerPanneauBoutonsVerticaux("Batiment", "Ajouter", "Modifier", "Supprimer");

		JPanel wrapperBat = new JPanel(new BorderLayout());
		wrapperBat.setOpaque(false);
		wrapperBat.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
		wrapperBat.add(boutonsBatiment, BorderLayout.NORTH);

		blocBatiment.add(wrapperBat, BorderLayout.EAST);

		zoneCentrale.add(blocBatiment);
		zoneCentrale.add(Utils.creerEspacementVertical(15));

		// ===================================================
		// BLOC Biens Louable
		// ===================================================
		String[] colonnesLogement = { "Numéro fiscal", "Type", "Surface", "Pièces", "ID bâtiment" };
		JTable[] refLogement = new JTable[1];
		JPanel blocBienLouable = creerBlocTableAvecReference("Bien Louable", colonnesLogement, new Object[0][0], refLogement);
		tableauLogement = refLogement[0];
		gestionFenetrePrincipale.afficherTableauBienLouable();
		
		Utils.ajouterDoubleClic(
			    tableauLogement,
			    0, // colonne "Numéro fiscal"
			    numeroFiscal -> gestionFenetrePrincipale.ouvrirDetailBienLouable(numeroFiscal)
			);

		JPanel boutonsBienLouable = creerPanneauBoutonsVerticaux("BienLouable", "Ajouter", "Modifier", "Supprimer");

		JPanel wrapperBienLouable = new JPanel(new BorderLayout());
		wrapperBienLouable.setOpaque(false);
		wrapperBienLouable.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0)); 
		wrapperBienLouable.add(boutonsBienLouable, BorderLayout.NORTH);

		blocBienLouable.add(wrapperBienLouable, BorderLayout.EAST);
		zoneCentrale.add(blocBienLouable);
		zoneCentrale.add(Utils.creerEspacementVertical(15));

		JPanel panneauBas = creerPanneauBoutons("Biens",
			new String[]{"Afficher diagnostics", "btnAfficherDiagnostics", "secondaire"},
			new String[]{"Afficher Compteur", "btnAfficherCompteur", "secondaire"}
		);
		panneauBas.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		
		panneauPrincipal.add(panneauBas, BorderLayout.SOUTH);

		panneauPrincipal.add(zoneCentrale, BorderLayout.CENTER);

		return panneauPrincipal;
	}

	//////////////////////////////////////////////////
	//												//
	//                PAGE LOCATIONS    			//
	//												//
	//////////////////////////////////////////////////

	private JPanel creerPageLocations() {
		JPanel panneauPrincipal = creerPanneauPage(30);

		// ===== TITRE =====
		JLabel labelTitre = Utils.creerTitre("Mes Locations");

		JPanel ligneBas = new JPanel();
		ligneBas.setBackground(new Color(0, 102, 204));
		ligneBas.setPreferredSize(new Dimension(getWidth(), 2));

		JPanel panneauTitre = new JPanel(new BorderLayout());
		panneauTitre.setOpaque(false);
		panneauTitre.add(labelTitre, BorderLayout.CENTER);
		panneauTitre.add(ligneBas, BorderLayout.SOUTH);

		panneauPrincipal.add(panneauTitre, BorderLayout.NORTH);

		// ===== CONTENU =====
		JPanel zoneContenu = new JPanel(new BorderLayout());
		zoneContenu.setOpaque(false);
		zoneContenu.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

		panneauPrincipal.add(panneauTitre, BorderLayout.NORTH);

		String[] nomsColonnes = {
				"Id Locataire",
				"Numero Fiscale",
				"Date de paiement",
				"Montant provision",
				"Montant loyer",
				"Mois",
				"Montant régularisation"
		};

		JTable[] refLoyer = new JTable[1];
		JPanel blocLoyer = creerBlocTableAvecReference("Mes locations", nomsColonnes, new Object[0][0], refLoyer);
		tableauLoyer = refLoyer[0];
		tableauLoyer.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		JScrollPane panneauDefilement = (JScrollPane) blocLoyer.getComponent(0);
		zoneContenu.add(panneauDefilement, BorderLayout.CENTER);

		/* ===== BOUTONS BAS ===== */
		JPanel panneauBas = creerPanneauBoutons("Locations",
			new String[]{"Inserer", "btnInserer_Locations"},
			new String[]{"Modifier", "btnModifier_Locations"},
			new String[]{"Archiver", "btnArchiver_Locations"},
			new String[]{"Supprimer", "btnSupprimer_Locations"}
		);

		/* ===== ASSEMBLAGE ===== */
		panneauPrincipal.add(zoneContenu, BorderLayout.CENTER);
		panneauPrincipal.add(panneauBas, BorderLayout.SOUTH);

		return panneauPrincipal;

	}

	//////////////////////////////////////////////////
	//												//
	//                 PAGE BAUX   					//
	//												//
	//////////////////////////////////////////////////

	private JPanel creerPageBaux() {
		JPanel panneauPrincipal = creerPanneauPage(30);

		panneauPrincipal.add(creerEnTetePage("Gestion des baux"), BorderLayout.NORTH);

		JPanel zoneContenu = new JPanel(new BorderLayout());
		zoneContenu.setOpaque(false);
		zoneContenu.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

		// ===== NOM DES COLONNES (sans "Archivé") =====
		String[] nomsColonnes = {
				"ID Contrat",
				"Date début",
				"Trimestre",
				"Date sortie",
				"Loyer",
				"IRL",
				"Provisions",
				"Solde",
				"Durée",
				"Numéro fiscal",
				"ID Locataire"
		};

		JTable[] refBaux = new JTable[1];
		JPanel blocBaux = creerBlocTableAvecReference("Baux", nomsColonnes, new Object[0][0], refBaux);
		tableauBaux = refBaux[0];
		tableauBaux.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		zoneContenu.add(blocBaux, BorderLayout.CENTER);

		// ===== BOUTONS BAS =====
		JPanel panneauBas = creerPanneauBoutons("Baux",
			new String[]{"Ajouter", "btnAjouter_Baux"},
			new String[]{"Modifier", "btnModifier_Baux"},
			new String[]{"Archiver", "btnArchiver_Baux"},
			new String[]{"Afficher Caution", "btnAfficherCaution", "secondaire"},
			new String[]{"Calculer IRL", "btnCalculerIRL", "secondaire"}
		);
		
		// ===== ASSEMBLAGE =====
		panneauPrincipal.add(zoneContenu, BorderLayout.CENTER);
		panneauPrincipal.add(panneauBas, BorderLayout.SOUTH);

		return panneauPrincipal;
	}

	////////////////////////////////////////////////////
	//									    		  //
	//               PAGE ENTREPRISES		    	  //
	//											      //
	////////////////////////////////////////////////////

	private JPanel creerPageCharges() {
		JPanel panneauPrincipal = creerPanneauPage(30);

		panneauPrincipal.add(creerEnTetePage("Gestion des entreprises"), BorderLayout.NORTH);

		String[] colonnes = {
				"SIRET", "Nom", "Ville", "Mail", "Adresse", "Spécialité", "Code postal"
		};

		DefaultTableModel model = new DefaultTableModel(colonnes, 0);
		tableauEntreprises = new JTable(model);
		Utils.styliserTable(tableauEntreprises);

		JScrollPane scrollPane = Utils.creerScrollPane(tableauEntreprises, "Entreprises");
		scrollPane.setPreferredSize(new Dimension(1000, 400));

		// Panneau central avec espacement et contenu
		JPanel panneauCentre = new JPanel(new BorderLayout());
		panneauCentre.setOpaque(false);
		panneauCentre.add(Utils.creerEspacementVertical(20), BorderLayout.NORTH);

		JPanel zoneCentre = new JPanel(new BorderLayout());
		zoneCentre.setOpaque(false);
		zoneCentre.add(scrollPane, BorderLayout.CENTER);

		panneauCentre.add(zoneCentre, BorderLayout.CENTER);
		panneauPrincipal.add(panneauCentre, BorderLayout.CENTER);

		JPanel panneauBas = creerPanneauBoutons("Entreprise",
			new String[]{"Ajouter", "btnAjouterEntreprise"},
			new String[]{"Modifier", "btnModifierEntreprise"},
			new String[]{"Supprimer", "btnSupprimerEntreprise"}
		);
		panneauBas.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

		panneauPrincipal.add(panneauBas, BorderLayout.SOUTH);

		return panneauPrincipal;
	}

	//////////////////////////////////////////////////
	//												//
	//                 PAGE FACTURES				//
	//												//
	//////////////////////////////////////////////////


	private JPanel creerPageFactures() {
		JPanel panneauPrincipal = creerPanneauPage(30);

		panneauPrincipal.add(creerEnTetePage("Gestion des factures"), BorderLayout.NORTH);

		this.ongletsFactures = new JTabbedPane(); // Ne pas mettre "JTabbedPane" devant pour utiliser la variable d'instance onglets.setFont(new Font("Segoe UI", Font.PLAIN, 13));

		ongletsFactures.addTab("Factures", creerOngletFacture());
		ongletsFactures.addTab("Impots", creerOngletImpots());
		ongletsFactures.addTab("Travaux", creerOngletTravauxFac());
		ongletsFactures.addTab("Variable", creerOngletVariable());
		ongletsFactures.addTab("Assurance", creerOngletAssurance());
		ongletsFactures.addTab("Agence", creerOngletAgence());
		//onglets.addTab("Charges", creerOngletCharges());

		panneauPrincipal.add(ongletsFactures, BorderLayout.CENTER);

		return panneauPrincipal;

	}

	public JPanel creerOngletFacture() {

		//----------------------------------------
		JPanel panel = new JPanel(new BorderLayout());
		panel.setOpaque(false);
		String[] nomsColonnes = {"Bien","Numero", "Date Facture", "Type", "Montant", "Mode paiement", "Numero Devis", "Nature", "Date Devis", "Montant devis"};
		DefaultTableModel model = new DefaultTableModel(nomsColonnes, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Rendre toutes les cellules non-éditables
			}
		};

		tableauFactures = new JTable(model);

		Utils.styliserTable(tableauFactures);
		
		// Ajouter MouseListener pour double-clic sur les factures
		tableauFactures.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (e.getClickCount() == 2) {
					int row = tableauFactures.getSelectedRow();
					System.out.println("DEBUG: DOUBLE-CLIC détecté! Row=" + row);
					if (row >= 0) {
						try {
							System.out.println("DEBUG: Avant création FenetreAfficherChargesLot");
							FenetreAfficherChargesLot fenetre = new FenetreAfficherChargesLot(gestionFenetrePrincipale);
							System.out.println("DEBUG: Fenêtre créée, avant add au desktop");
							JDesktopPane desktop = FenetrePrincipale.this.getDesktopPane();
							System.out.println("DEBUG: Desktop = " + desktop);
							if (desktop != null) {
								desktop.add(fenetre);
								fenetre.setVisible(true);
								fenetre.toFront();
								
								// Bloquer le fond quand la fenêtre s'ouvre
								FenetrePrincipale.this.setContenuBloque(true);
								
								// Débloquer le fond quand la fenêtre se ferme
								fenetre.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
									@Override
									public void internalFrameClosed(javax.swing.event.InternalFrameEvent e) {
										FenetrePrincipale.this.setContenuBloque(false);
									}
								});
								
								System.out.println("DEBUG: Fenêtre charges ouverte avec succès");
							} else {
								System.out.println("DEBUG: ERREUR - Desktop est null!");
							}
						} catch (Exception ex) {
							System.out.println("DEBUG: Erreur ouverture fenêtre charges: " + ex.getMessage());
							ex.printStackTrace();
						}
					}
				}
			}
		});
		
		JScrollPane panneauDefilement = Utils.creerScrollPane(tableauFactures, "Factures");
		panneauDefilement.setPreferredSize(new Dimension(800, 200));

		JPanel zoneContenu = new JPanel(new BorderLayout(0, 10));
		zoneContenu.setOpaque(false);
		zoneContenu.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		zoneContenu.add(panneauDefilement, BorderLayout.CENTER);     
		gestionFenetrePrincipale.afficherTableFactures();

		JPanel panneauFiltres = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
		panneauFiltres.setOpaque(false);
		panneauFiltres.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		//-----------------------------------------------------------------------------------------------------
		//-------------------------filtre


		JLabel labelType = Utils.creerLabel("Type :");
		labelType.setFont(Utils.NORMAL_11_FONT);
		panneauFiltres.add(labelType);

		this.comboType = Utils.creerComboBox(150, 25);
		comboType.setPreferredSize(new Dimension(130, 25));
		comboType.addItem("Tous");
		comboType.addItem("Travaux");
		comboType.addItem("Variable");
		comboType.addItem("Assurance");
		comboType.addItem("Impots");

		panneauFiltres.add(comboType);

		JLabel labelLocataire = Utils.creerLabel("Biens :");
		labelLocataire.setFont(Utils.NORMAL_11_FONT);
		panneauFiltres.add(labelLocataire);
		this.comboBienFactures = Utils.creerComboBox(150, 25);
		comboBienFactures.setPreferredSize(new Dimension(160, 25));
		panneauFiltres.add(comboBienFactures);

		JLabel labelPeriode = Utils.creerLabel("Periode :");
		labelPeriode.setFont(Utils.NORMAL_12_FONT);
		panneauFiltres.add(labelPeriode);

		this.champPeriode = Utils.creerTextField("Période", 150, 25);
		champPeriode.setPreferredSize(new Dimension(120, 25));
		panneauFiltres.add(champPeriode);

		this.btnRechercher = Utils.creerBoutonPrimaire("Rechercher");
		btnRechercher.setName("btnRechercher_Factures");
		btnRechercher.addActionListener(gestionFenetrePrincipale);
		panneauFiltres.add(btnRechercher);

		zoneContenu.add(panneauFiltres, BorderLayout.NORTH);  
		try {

			DaoFacture daoFacture = new DaoFacture();
			List<Facture> factures = daoFacture.findAll();
			this.gestionFenetrePrincipale.chargerBiens(factures);
		}catch(SQLException sqlE) {
			sqlE.printStackTrace();
			JOptionPane.showMessageDialog(this, "Erreur de chargement");

		}
		//------------------------------------------------------------------------------------------------ //


		JPanel panneauBoutons = new JPanel(new BorderLayout());
		panneauBoutons.setOpaque(false);
		panneauBoutons.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

		JPanel panneauGauche = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		panneauGauche.setOpaque(false);

		JButton boutonChargerFac = Utils.creerBoutonPrimaire("Charger les factures");
		boutonChargerFac.setName("btnCharger_Factures");
		boutonChargerFac.setPreferredSize(new Dimension(220, 32));
		boutonChargerFac.addActionListener(gestionFenetrePrincipale);

		panneauGauche.add(boutonChargerFac);

		JButton boutonCalculerCharges = Utils.creerBoutonPrimaire("Calculer charges");
		boutonCalculerCharges.setName("btnCalculerCharges_Factures");
		boutonCalculerCharges.setPreferredSize(new Dimension(150, 32));
		boutonCalculerCharges.addActionListener(gestionFenetrePrincipale);
		panneauGauche.add(boutonCalculerCharges);
		panneauBoutons.add(panneauGauche, BorderLayout.WEST);

		JPanel panneauDroit = creerPanneauBoutons("Factures",
			new String[]{"Ajouter", "btnAjouter_Factures"},
			new String[]{"Modifier", "btnModifier_Factures"},
			new String[]{"Archiver", "btnArchiver_Factures"},
			new String[]{"Supprimer", "btnSupprimer_Factures"}
		);
		panneauDroit.setBorder(null); // Reset border for RIGHT alignment
		panneauDroit.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0));

		panneauBoutons.add(panneauDroit, BorderLayout.EAST);
		zoneContenu.add(panneauBoutons, BorderLayout.SOUTH);  
		panel.add(zoneContenu, BorderLayout.CENTER);  

		return panel;

	}

	public JPanel creerOngletAssurance() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setOpaque(false);

		// Adaptation des colonnes selon (id_assurance, type, annee, numero)
		String[] colonnes = {
				"ID Assurance", "Type", "Année", "Numéro"
		};

		JTable[] refAssurance = new JTable[1];
		creerBlocTableAvecReference("Archives assurances", colonnes, new Object[0][0], refAssurance);
		tableauAssurance = refAssurance[0];
		gestionFenetrePrincipale.afficherTableauAssurances();

		JScrollPane scroll = Utils.creerScrollPane(
				tableauAssurance, "Archives assurances"
				);

		// Adaptation des boutons et de leurs noms (Name) pour le contrôleur
		JPanel actions = creerPanneauBoutons("Archives_Assurances",
			new String[]{"Charger", "btnChargerArchives_Assurances"},
			new String[]{"Exporter", "btnExporterArchives_Assurances", "secondaire"}
		);
		actions.setBorder(null); // Réinitialiser border pour ce cas spécial

		panel.add(scroll, BorderLayout.CENTER);
		panel.add(actions, BorderLayout.SOUTH);

		return panel;
	}

	public JPanel creerOngletAgence() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setOpaque(false);

		// Colonnes pour les factures agence
		String[] colonnes = {
				"Agence", "Numero", "Date Facture", "Type", "Montant", "Mode paiement"
		};

		DefaultTableModel model = new DefaultTableModel(colonnes, 0) {
			@Override
			public boolean isCellEditable(int r, int c) { return false; }
		};

		JTable[] refAgence = new JTable[1];
		creerBlocTableAvecReference("Factures agence", colonnes, new Object[0][0], refAgence);
		tableauAgence = refAgence[0];
		gestionFenetrePrincipale.afficherTableauFacturesAgence();

		JScrollPane scroll = Utils.creerScrollPane(
				tableauAgence, "Factures agence"
				);

		JButton btnAjouter = Utils.creerBoutonPrimaire("Ajouter");
		btnAjouter.setName("btnAjouterFactureAgence");
		btnAjouter.addActionListener(gestionFenetrePrincipale);

		JButton btnExporter = Utils.creerBouton("Exporter");
		btnExporter.setName("btnExporterFacturesAgence");
		btnExporter.addActionListener(gestionFenetrePrincipale);

		JPanel actions = Utils.creerPanelActions(btnAjouter, btnExporter);

		panel.add(scroll, BorderLayout.CENTER);
		panel.add(actions, BorderLayout.SOUTH);

		return panel;
	}


	public JPanel creerOngletVariable() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setOpaque(false);

		// Adaptation des colonnes avec le nouveau champ prix unitaire
		String[] colonnes = {
				"Numero", "Id_Variable", "Service", "Fournisseur", "Type Variable", "Prix Unitaire"
		};

		DefaultTableModel model = new DefaultTableModel(colonnes, 0) {
			@Override
			public boolean isCellEditable(int r, int c) { 
				// Seule la colonne 5 (Prix Unitaire) est éditable
				return c == 5; 
			}
		};

		tableauVariable = new JTable(model);
		Utils.styliserTable(tableauVariable);
		
		// Ajouter un listener pour sauvegarder les modifications de prix
		tableauVariable.getModel().addTableModelListener(e -> {
			int row = e.getFirstRow();
			int col = e.getColumn();
			if (col == 5) { // Colonne Prix Unitaire
				try {
					String idVariable = (String) tableauVariable.getValueAt(row, 1);
					String prixStr = (String) tableauVariable.getValueAt(row, 5);
					double prix = Double.parseDouble(prixStr);
					
					DaoVariable dao = new DaoVariable();
					Variable var = dao.findById(idVariable);
					if (var != null) {
						var.setPrix_unitaire(prix);
						dao.update(var);
						JOptionPane.showMessageDialog(panel, "Prix unitaire mis à jour!", 
							"Succès", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(panel, "Veuillez entrer un nombre valide", 
						"Erreur", JOptionPane.WARNING_MESSAGE);
					this.gestionFenetrePrincipale.afficherTableVariable();
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(panel, "Erreur: " + ex.getMessage(), 
						"Erreur", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		// Appel de la méthode de gestion correspondante 
		this.gestionFenetrePrincipale.afficherTableVariable();
		JScrollPane scroll = Utils.creerScrollPane(
				tableauVariable, "Liste des Variable"
				);

		JButton btnExporter = Utils.creerBouton("Exporter");
		btnExporter.setName("btnExporterVariable");
		btnExporter.addActionListener(gestionFenetrePrincipale);

		JPanel actions = Utils.creerPanelActions(btnExporter);

		panel.add(scroll, BorderLayout.CENTER);
		panel.add(actions, BorderLayout.SOUTH);

		return panel;
	}

	public JPanel creerOngletTravauxFac() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setOpaque(false);

		String[] colonnes = {"Numero", "Raison", "Id_Travaux"};
		DefaultTableModel model = new DefaultTableModel(colonnes, 0) {
			@Override
			public boolean isCellEditable(int r, int c) { return false; }
		};

		JTable[] refTravaux = new JTable[1];
		creerBlocTableAvecReference("Liste des Travaux", colonnes, new Object[0][0], refTravaux);
		this.tableauTravaux = refTravaux[0]; 
		this.gestionFenetrePrincipale.afficherTableTravauxFac();

		JScrollPane scroll = Utils.creerScrollPane(tableauTravaux, "Liste des Travaux");

		// ... (Ajout des boutons comme dans votre code) ...

		panel.add(scroll, BorderLayout.CENTER);
		return panel;
	}

	public JPanel creerOngletImpots() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setOpaque(false);
		// Adaptation des colonnes selon (id_assurance, type, annee, numero)
		String[] colonnes = {
				"NumeroFac","Id_Impots", "Anne", "Type", "Recuperable impots"
		};
		DefaultTableModel model = new DefaultTableModel(colonnes, 0) {
			@Override
			public boolean isCellEditable(int r, int c) { return false; }
		};
		JTable[] refImpot = new JTable[1];
		creerBlocTableAvecReference("Liste des Impots", colonnes, new Object[0][0], refImpot);
		tableauImpot = refImpot[0];
		this.gestionFenetrePrincipale.afficherTableImpots();
		JScrollPane scroll = Utils.creerScrollPane(
				tableauImpot, "Liste des Impots"
				);
		// Adaptation des boutons et de leurs noms (Name) pour le contrôleur
		JButton btnCharger = Utils.creerBoutonPrimaire("Charger");
		btnCharger.setName("btnChargerImpots");
		btnCharger.addActionListener(gestionFenetrePrincipale);

		JButton btnExporter = Utils.creerBouton("Exporter");
		btnExporter.setName("btnExporterImpots");
		btnExporter.addActionListener(gestionFenetrePrincipale);

		JPanel actions = Utils.creerPanelActions(btnCharger, btnExporter);

		panel.add(scroll, BorderLayout.CENTER);
		panel.add(actions, BorderLayout.SOUTH);

		return panel;
	}


	//////////////////////////////////////////////////
	//												//
	//              PAGE LOCATAIRES     			//
	//												//
	//////////////////////////////////////////////////	

	private JPanel creerPageLocataires() {
		JPanel panneauPrincipal = creerPanneauPage(30);

		// Titre
		JLabel labelTitre = Utils.creerTitre("Gestion des locataires");

		// Ligne en dessous du titre
		JPanel ligneBas = new JPanel();
		ligneBas.setBackground(new Color(0, 102, 204));
		ligneBas.setPreferredSize(new Dimension(getWidth(), 2));

		// Panneau pour le titre avec la ligne
		JPanel panneauTitre = new JPanel(new BorderLayout());
		panneauTitre.setOpaque(false);
		panneauTitre.add(labelTitre, BorderLayout.CENTER);
		panneauTitre.add(ligneBas, BorderLayout.SOUTH);

		panneauPrincipal.add(panneauTitre, BorderLayout.NORTH);

		String[] colonnes = {
				"ID", "Nom", "Prenom", "Date Naissance", "Genre", "Email", "Adresse", "Ville", "Code Postale", "Telephone"
		};

		DefaultTableModel model = new DefaultTableModel(colonnes, 0);
		tableauLocataires = new JTable(model);
		Utils.styliserTable(tableauLocataires);
		gestionFenetrePrincipale.afficherTableauLocataire();

		JScrollPane scrollPane = Utils.creerScrollPane(tableauLocataires, "Locataires");
		scrollPane.setPreferredSize(new Dimension(800, 400));

		// Panneau central avec espacement et contenu
		JPanel panneauCentre = new JPanel(new BorderLayout());
		panneauCentre.setOpaque(false);
		panneauCentre.add(Utils.creerEspacementVertical(20), BorderLayout.NORTH);

		JPanel zoneCentre = new JPanel(new BorderLayout());
		zoneCentre.setOpaque(false);
		zoneCentre.add(scrollPane, BorderLayout.CENTER);

		panneauCentre.add(zoneCentre, BorderLayout.CENTER);
		panneauPrincipal.add(panneauCentre, BorderLayout.CENTER);

		JPanel panneauBas = creerPanneauBoutons("Locataire",
			new String[]{"Ajouter", "btnAjouterLocataire"},
			new String[]{"Modifier", "btnModifierLocataire"},
			new String[]{"Archiver", "btnArchiverLocataire"},
			new String[]{"Supprimer", "btnSupprimerLocataire"}
		);
		panneauBas.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Ajustement espacement

		panneauPrincipal.add(panneauBas, BorderLayout.SOUTH);

		return panneauPrincipal;
	}


	//////////////////////////////////////////////////
	//  											//
	//				PAGE  ARCHIVES 	            	//
	//         PAGE COMPTEURS ARCHIVÉS              //
	//  											//
	//////////////////////////////////////////////////

	private JPanel creerPageArchives() {
		JPanel panneauPrincipal = creerPanneauPage(30);

		JTabbedPane onglets = new JTabbedPane();
		onglets.setFont(Utils.MENU_FONT);

	    onglets.addTab("Compteurs", creerOngletCompteurs());
	    onglets.addTab("Locataires", creerOngletLocataires());
	    onglets.addTab("Locations", creerOngletLocations());
	    onglets.addTab("Diagnostics", creerOngletDiagnostics());
	    onglets.addTab("Factures", creerOngletFactures());
	    onglets.addTab("Cautions", creerOngletCautions());
	    onglets.addTab("Baux", creerOngletContratDeLocation());

		panneauPrincipal.add(onglets, BorderLayout.CENTER);

		return panneauPrincipal;
	}

	private JPanel creerOngletCompteurs() {

		JPanel panel = new JPanel(new BorderLayout());
		panel.setOpaque(false);

		String[] colonnes = {
				"ID Compteur", "Type", "Index", "Variable", "Bien", "Bâtiment"
		};

		JTable[] refCompteur = new JTable[1];
		JPanel blocCompteur = creerBlocTableAvecReference("Archives compteurs", colonnes, new Object[0][0], refCompteur);
		tableauCompteurArchives = refCompteur[0];
		gestionFenetrePrincipale.afficherTableauCompteursArchives();
		JScrollPane scroll = (JScrollPane) blocCompteur.getComponent(0);

		JButton btnRafraichir = Utils.creerBoutonPrimaire("Rafraichir");
		btnRafraichir.setName("btnRafraichirArchives_Compteurs");
		btnRafraichir.addActionListener(gestionFenetrePrincipale);


		JButton btnRestaurer = Utils.creerBouton("Restaurer");
		btnRestaurer.setName("btnRestaurerArchives_Compteurs");
		btnRestaurer.addActionListener(gestionFenetrePrincipale);

		JPanel actions = Utils.creerPanelActions(btnRafraichir, btnRestaurer);


		panel.add(scroll, BorderLayout.CENTER);
		panel.add(actions, BorderLayout.SOUTH);

		return panel;
	}

	private JPanel creerOngletCautions() {

		JPanel panel = new JPanel(new BorderLayout());
		panel.setOpaque(false);

		 String[] colonnes = {
		            "ID Caution", "Nom", "Prénom", "Ville", "Téléphone", "Mail", "ID Contrat"
		        };

		JTable[] refCaution = new JTable[1];
		JPanel blocCaution = creerBlocTableAvecReference("Archives cautions", colonnes, new Object[0][0], refCaution);
		tableauCautionArchives = refCaution[0];
		gestionFenetrePrincipale.afficherTableauCautionArchives();

		JScrollPane scroll = Utils.creerScrollPane(
				tableauCautionArchives, "Archives cautions"
				);

	    JButton btnRafraichir = Utils.creerBoutonPrimaire("Rafraichir");
	    btnRafraichir.setName("btnRafraichirArchives_Caution");
	    btnRafraichir.addActionListener(gestionFenetrePrincipale);


		JButton btnRestaurer = Utils.creerBouton("Restaurer");
		btnRestaurer.setName("btnRestaurerArchives_Caution");
		btnRestaurer.addActionListener(gestionFenetrePrincipale);

	    JPanel actions = Utils.creerPanelActions(btnRafraichir, btnRestaurer);


		panel.add(scroll, BorderLayout.CENTER);
		panel.add(actions, BorderLayout.SOUTH);

		return panel;
	}

	private JPanel creerOngletLocataires() {

		JPanel panel = new JPanel(new BorderLayout());
		panel.setOpaque(false);

		String[] colonnes = {
				"ID", "Nom", "Prénom", "Email", "Téléphone", "Ville"
		};

		JTable[] refLocataire = new JTable[1];
		creerBlocTableAvecReference("Archives locataires", colonnes, new Object[0][0], refLocataire);
		tableauLocataireArchives = refLocataire[0];
		gestionFenetrePrincipale.afficherTableauLocatairesArchives();

		JScrollPane scroll = Utils.creerScrollPane(
				tableauLocataireArchives, "Archives locataires"
				);

		JButton btnRafraichir = Utils.creerBoutonPrimaire("Rafraichir");
		btnRafraichir.setName("btnRafraichirArchives_Locataires");
		btnRafraichir.addActionListener(gestionFenetrePrincipale);
		
	    JButton btnRestaurer = Utils.creerBouton("Restaurer");
	    btnRestaurer.setName("btnRestaurerArchives_Locataire");
	    btnRestaurer.addActionListener(gestionFenetrePrincipale);

	    JPanel actions = Utils.creerPanelActions(btnRafraichir, btnRestaurer);

		panel.add(scroll, BorderLayout.CENTER);
		panel.add(actions, BorderLayout.SOUTH);

		return panel;
	}


	private JPanel creerOngletLocations() {

		JPanel panel = new JPanel(new BorderLayout());
		panel.setOpaque(false);

		String[] colonnes = {
				"Locataire", "Bien", "Date paiement",
				"Loyer", "Provisions", "Régularisation"
		};

		JTable[] refLocation = new JTable[1];
		creerBlocTableAvecReference("Archives locations", colonnes, new Object[0][0], refLocation);
		tableauLocationArchives = refLocation[0];
		gestionFenetrePrincipale.afficherTableauLocationArchive();
		JScrollPane scroll = Utils.creerScrollPane(
				tableauLocationArchives, "Archives locations"
				);

		JButton btnRafraichir = Utils.creerBoutonPrimaire("Rafraichir");
		btnRafraichir.setName("btnRafraichirArchives_Locations");
		btnRafraichir.addActionListener(gestionFenetrePrincipale);


		JButton btnRestaurer = Utils.creerBouton("Restaurer");
		btnRestaurer.setName("btnRestaurerArchives_Locations");
		btnRestaurer.addActionListener(gestionFenetrePrincipale);

		JPanel actions = Utils.creerPanelActions(btnRafraichir, btnRestaurer);

		panel.add(scroll, BorderLayout.CENTER);
		panel.add(actions, BorderLayout.SOUTH);

		return panel;
	}

	private JPanel creerOngletDiagnostics() {

		JPanel panel = new JPanel(new BorderLayout());
		panel.setOpaque(false);

		String[] colonnes = {
				"Référence", "Date validité", "Type diagnostic", 
				"Bien", "Entreprise"
		};


		JTable[] refDiagnostic = new JTable[1];
		creerBlocTableAvecReference("Archives diagnostics", colonnes, new Object[0][0], refDiagnostic);
		tableauDiagnosticArchives = refDiagnostic[0];
		gestionFenetrePrincipale.afficherTableauDiagnosticArchive();
		JScrollPane scroll = Utils.creerScrollPane(
				tableauDiagnosticArchives, "Archives diagnostics"
				);

		JButton btnRafraichir = Utils.creerBoutonPrimaire("Rafraichir");
		btnRafraichir.setName("btnRafraichirArchives_Diagnostics");
		btnRafraichir.addActionListener(gestionFenetrePrincipale);


		JButton btnRestaurer = Utils.creerBouton("Restaurer");
		btnRestaurer.setName("btnRestaurerArchives_Diagnostics");
		btnRestaurer.addActionListener(gestionFenetrePrincipale);

		JPanel actions = Utils.creerPanelActions(btnRafraichir, btnRestaurer);

		panel.add(scroll, BorderLayout.CENTER);
		panel.add(actions, BorderLayout.SOUTH);

		return panel;
	}


	private JPanel creerOngletFactures() {

		JPanel panel = new JPanel(new BorderLayout());
		panel.setOpaque(false);

		String[] colonnes = {
				"Numéro", "Date Facture", "Type", "Montant", "Mode paiement", "Numero Devis", "Nature", "Date Devis"
		};

		JTable[] refFacture = new JTable[1];
		creerBlocTableAvecReference("Archives factures", colonnes, new Object[0][0], refFacture);
		tableauFactureArchives = refFacture[0];
		gestionFenetrePrincipale.afficherTableauFactureArchive();

		JScrollPane scroll = Utils.creerScrollPane(
				tableauFactureArchives, "Archives factures"
				);

		JButton btnRafraichir = Utils.creerBoutonPrimaire("Rafraichir");
		btnRafraichir.setName("btnRafraichirArchives_Factures");
		btnRafraichir.addActionListener(gestionFenetrePrincipale);

		JButton btnRestaurer = Utils.creerBouton("Exporter");
		btnRestaurer.setName("btnRestaurerArchives_Locations");
		btnRestaurer.addActionListener(gestionFenetrePrincipale);

		JPanel actions = Utils.creerPanelActions(btnRafraichir, btnRestaurer);

		panel.add(scroll, BorderLayout.CENTER);
		panel.add(actions, BorderLayout.SOUTH);

		return panel;
	}

	private JPanel creerOngletContratDeLocation() {

		JPanel panel = new JPanel(new BorderLayout());
		panel.setOpaque(false);

		String[] colonnes = {
				"ID", "Date début", "Date Sortie", "Loyer", "Numéro fiscale", "ID Locataire"
		};

		DefaultTableModel model = new DefaultTableModel(colonnes, 0) {
			public boolean isCellEditable(int r, int c) { return false; }
		};

		JTable[] refContrat = new JTable[1];
		creerBlocTableAvecReference("Archives contrats de location", colonnes, new Object[0][0], refContrat);
		tableauContratDeLocationArchives = refContrat[0];
		gestionFenetrePrincipale.afficherTableauContratDeLocationArchives();

		JScrollPane scroll = Utils.creerScrollPane(
				tableauContratDeLocationArchives, "Archives contrats de location"
				);

		JButton btnRafraichir = Utils.creerBoutonPrimaire("Rafraichir");
		btnRafraichir.setName("btnRafraichirArchives_ContratDeLocation");
		btnRafraichir.addActionListener(gestionFenetrePrincipale);
		
	    JButton btnRestaurer = Utils.creerBouton("Restaurer");
	    btnRestaurer.setName("btnRestaurerArchives_ContratDeLocation");
	    btnRestaurer.addActionListener(gestionFenetrePrincipale);

	    JPanel actions = Utils.creerPanelActions(btnRafraichir, btnRestaurer);

		panel.add(scroll, BorderLayout.CENTER);
		panel.add(actions, BorderLayout.SOUTH);

		return panel;
	}


	// ====================================
	// GETTERS SUR LES TABLEAUX
	// ====================================

	public GestionFenetrePrincipale getGestionFenetrePrincipale() {
		return this.gestionFenetrePrincipale;
	}

	public JTable getTableauBienLouable() {
		return tableauLogement;
	}

	public JTable getTableauLoyer() {
		return tableauLoyer;
	}

	public JTable getTableauLocataire() {
		return tableauLocataires;
	}

	public JTable getTableauBatiment() {
		return tableauBatiment;
	}

	public JTable getTableauFacture() {
		return tableauFactures;
	}

	public JTable getTableauTravaux() {
		return tableauTravaux;
	}
	
	public JTable getTableauEntreprises() {
		return tableauEntreprises;
	}

	public JTable getTableauCompteurArchiver() {
		return tableauCompteurArchives;
	}

	public JTable getTableauAssurance() {
		return tableauAssurance;
	}

	public JTable getTableauAgence() {
		return tableauAgence;
	}

	public JTable getTableauLocationArchives() {
		return tableauLocationArchives;
	}

	public JTable getTableauLocataireArchives() {
		return tableauLocataireArchives;
	}

	public JTable getTableauDiagnosticArchives() {
		return tableauDiagnosticArchives;
	}

	public JTable getTableauFactureArchives() {
		return tableauFactureArchives;
	}

	public JTable getTableauTravauxArchives() {
		return tableauTravauxArchives;
	}
	
	public JTable getTableauCautionArchives() {
		return tableauCautionArchives;
	}
	
	public JTable getTableauContratDeLocationArchives() {
		return tableauContratDeLocationArchives;
	}

	public JComboBox<String> getComboType() {
		return  comboType;
	}

	public String getChampPeriode() {
		return champPeriode.getText();
	}

	public JComboBox<String> getcomboBienFactures() {
		return  comboBienFactures;
	}

	public JTable getTableauBaux() {
		return tableauBaux;
	}

	public JTable getTableauEntreprise() {
		return tableauEntreprises;
	}

	public JTable getTableauImpot() {
		return tableauImpot;
	}
	// Ajouter le getter
	public JTabbedPane getOngletsFactures() {
		return this.ongletsFactures;
	}


	// ============================================
	// METHODES POUR LE BLOCAGE
	// ============================================
	public void setContenuBloque(boolean bloque) {
		if (blocageContenu != null) {
			blocageContenu.setVisible(bloque);
		}

		// Bloquer AUSSI le menu principal
		if (getJMenuBar() != null) {
			getJMenuBar().setEnabled(!bloque);
		}

		// Bloquer tous les boutons du menu (pour qu'ils ne soient pas cliquable)
		btnAccueil.setEnabled(!bloque);
		btnBiens.setEnabled(!bloque);
		btnLocations.setEnabled(!bloque);
		btnBaux.setEnabled(!bloque);
		btnEntreprises.setEnabled(!bloque);
		btnFactures.setEnabled(!bloque);
		btnLocataires.setEnabled(!bloque);
		btnArchives.setEnabled(!bloque);
		boutonImporterCSV.setEnabled(!bloque);
	}

	public JTable getTableauVariable() {
		return tableauVariable;
	}

	/**
	 * Crée un bloc tableau réutilisable avec titre, colonnes et données
	 * Élimine la duplication du code de création de tableaux
	 */
	private JPanel creerBlocTable(String titre, String[] colonnes, Object[][] donnees) {
		JPanel blocPanel = new JPanel(new BorderLayout());
		blocPanel.setBackground(Color.WHITE);
		blocPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
				BorderFactory.createEmptyBorder(15, 15, 15, 15)
				));

		// Créer le tableau
		JTable table = new JTable(Utils.creerTableModelNonEditable(colonnes));
		Utils.styliserTable(table);

		// Ajouter les données
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		for (Object[] row : donnees) {
			model.addRow(row);
		}

		// Créer le scroll pane
		JScrollPane scroll = Utils.creerScrollPane(table, titre);
		blocPanel.add(scroll, BorderLayout.CENTER);

		return blocPanel;
	}

	/**
	 * Version qui retourne aussi le tableau pour le stocker dans une variable de classe
	 */
	private JPanel creerBlocTableAvecReference(String titre, String[] colonnes, Object[][] donnees, JTable[] tableRef) {
		JPanel blocPanel = new JPanel(new BorderLayout());
		blocPanel.setBackground(Color.WHITE);
		blocPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
				BorderFactory.createEmptyBorder(15, 15, 15, 15)
				));

		// Créer le tableau
		JTable table = new JTable(Utils.creerTableModelNonEditable(colonnes));
		Utils.styliserTable(table);
		tableRef[0] = table; // Stocker la référence

		// Ajouter les données
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		for (Object[] row : donnees) {
			model.addRow(row);
		}

		// Créer le scroll pane
		JScrollPane scroll = Utils.creerScrollPane(table, titre);
		blocPanel.add(scroll, BorderLayout.CENTER);

		return blocPanel;
	}

	/**
	 * Crée un panneau avec des boutons d'action configurés automatiquement
	 * @param prefixe Le préfixe pour les noms des boutons (ex: "Locations", "Baux")
	 * @param boutons Tableau de tableaux contenant [label, nomBouton] ou [label, nomBouton, typeBouton]
	 * @return JPanel contenant les boutons configurés
	 */
	private JPanel creerPanneauBoutons(String prefixe, String[]... boutons) {
		JPanel panneauBas = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
		panneauBas.setOpaque(false);
		panneauBas.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

		for (String[] btnInfo : boutons) {
			String label = btnInfo[0];
			String nomBouton = btnInfo[1];
			boolean isPrimaire = btnInfo.length < 3 || btnInfo[2].equals("primaire");
			
			JButton bouton = isPrimaire ? Utils.creerBoutonPrimaire(label) : Utils.creerBouton(label);
			bouton.setName(nomBouton);
			bouton.addActionListener(gestionFenetrePrincipale);
			panneauBas.add(bouton);
		}

		return panneauBas;
	}

	/**
	 * Crée un en-tête de page avec titre et ligne bleue de séparation
	 * @param titre Le titre de la page
	 * @return JPanel contenant le titre et la ligne de séparation
	 */
	private JPanel creerEnTetePage(String titre) {
		JLabel labelTitre = Utils.creerTitre(titre);

		// Ligne bleue sous le titre
		JPanel ligneBas = new JPanel();
		ligneBas.setBackground(new Color(0, 102, 204));
		ligneBas.setPreferredSize(new Dimension(getWidth(), 2));

		// Panneau assemblant titre + ligne
		JPanel panneauTitre = new JPanel(new BorderLayout());
		panneauTitre.setOpaque(false);
		panneauTitre.add(labelTitre, BorderLayout.CENTER);
		panneauTitre.add(ligneBas, BorderLayout.SOUTH);

		return panneauTitre;
	}

	private JPanel creerPanneauBoutonsVerticaux(String prefixe, String... labels) {
		JPanel boutons = new JPanel();
		boutons.setLayout(new BoxLayout(boutons, BoxLayout.Y_AXIS));
		boutons.setOpaque(false);

		for (int i = 0; i < labels.length; i++) {
			JButton btn = Utils.creerBoutonPrimaireBiens(labels[i]);
			btn.setName("btn" + labels[i] + prefixe);
			btn.addActionListener(gestionFenetrePrincipale);
			boutons.add(btn);
			if (i < labels.length - 1) {
				boutons.add(Utils.creerEspacementVertical(8));
			}
		}

		return boutons;
	}

	private JPanel creerPanneauPage(int padding) {
		JPanel panneauPrincipal = new JPanel(new BorderLayout());
		panneauPrincipal.setBackground(new Color(240, 240, 240));
		panneauPrincipal.setBorder(BorderFactory.createEmptyBorder(20, padding, 20, padding));
		return panneauPrincipal;
	}
}