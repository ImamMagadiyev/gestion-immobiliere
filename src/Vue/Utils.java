package Vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import Modele.Batiment;
import Modele.BienLouable;
import Modele.Compteur;
import Modele.ContratDeLocation;
import Modele.Facture;
import Modele.Loyer;

public class Utils {

	// ========================================
	// FONTS
	// ========================================
	private static final Font POLICE_STANDARD = new Font("Segoe UI", Font.PLAIN, 14);
	private static final Font POLICE_TITRE = new Font("Segoe UI", Font.BOLD, 28);
	private static final Font POLICE_LABEL = new Font("Segoe UI", Font.PLAIN, 12);

	public static final Font MENU_FONT = new Font("Segoe UI", Font.PLAIN, 14);
	public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 18);
	public static final Font BOLD_14_FONT = new Font("Segoe UI", Font.BOLD, 14);
	public static final Font BOLD_12_FONT = new Font("Segoe UI", Font.BOLD, 12);
	public static final Font BOLD_11_FONT = new Font("Segoe UI", Font.BOLD, 11);
	public static final Font BOLD_10_FONT = new Font("Segoe UI", Font.BOLD, 10);
	public static final Font NORMAL_12_FONT = new Font("Segoe UI", Font.PLAIN, 12);
	public static final Font NORMAL_11_FONT = new Font("Segoe UI", Font.PLAIN, 11);
	public static final Font NORMAL_10_FONT = new Font("Segoe UI", Font.PLAIN, 10);
	public static final Font NORMAL_9_FONT = new Font("Segoe UI", Font.PLAIN, 9);
	public static final Font ITALIC_12_FONT = new Font("Segoe UI", Font.ITALIC, 12);
	public static final Font ITALIC_11_FONT = new Font("Segoe UI", Font.ITALIC, 11);

	// ========================================
	// DIMENSIONS
	// ========================================
	private static final Dimension TAILLE_BOUTON = new Dimension(120, 32);

	public static final int WINDOW_WIDTH = 1450;
	public static final int WINDOW_HEIGHT = 950;
	public static final int MENU_BAR_HEIGHT = 60;
	public static final int HORIZONTAL_STRUT = 12;
	public static final int VERTICAL_STRUT = 15;
	public static final int BUTTON_HEIGHT = 35;
	public static final int PADDING_20 = 20;
	public static final int PADDING_30 = 30;
	public static final int PADDING_40 = 40;
	public static final int PADDING_15 = 15;
	public static final int PADDING_10 = 10;

	// ========================================
	// COULEURS
	// ========================================
	// Couleurs principales
	public static final Color PRIMARY_BLUE = new Color(0, 102, 204);
	public static final Color DARK_BLUE = new Color(0, 51, 102);
	public static final Color LIGHT_BLUE = new Color(230, 235, 245);
	
	// Couleurs de fond
	public static final Color LIGHT_GRAY = new Color(240, 240, 240);
	public static final Color WHITE_BG = new Color(245, 247, 250);
	public static final Color DARK_GRAY = new Color(211, 211, 211);
	
	// Couleurs de bordure et détails
	public static final Color BORDER_GRAY = new Color(220, 220, 220);
	public static final Color TEXT_DARK = new Color(70, 70, 70);
	public static final Color TEXT_GRAY = new Color(150, 150, 150);
	public static final Color TEXT_LIGHT = new Color(60, 60, 60);
	public static final Color GRID_GRAY = new Color(220, 220, 220);
	
	// Couleurs pour les graphiques
	public static final Color CHART_BLUE1 = new Color(70, 130, 180);
	public static final Color CHART_BLUE2 = new Color(100, 149, 237);
	public static final Color CHART_BLUE3 = new Color(65, 105, 225);
	public static final Color CHART_BLUE4 = new Color(30, 144, 255);
	public static final Color CHART_BLUE5 = new Color(25, 118, 210);
	
	// Couleurs pour les camemberts
	public static final Color PIE_RED = new Color(255, 99, 71);
	public static final Color PIE_BLUE = new Color(70, 130, 180);
	public static final Color PIE_GREEN = new Color(144, 238, 144);
	public static final Color PIE_YELLOW = new Color(255, 215, 0);
	public static final Color PIE_PURPLE = new Color(138, 43, 226);
	public static final Color PIE_DARK_GREEN = new Color(34, 139, 34);

	// ========================================
	// MESSAGES
	// ========================================
	public static final String MSG_ERREUR_CONNEXION = "Erreur de connexion à la base de données";
	public static final String MSG_AUCUNE_DONNEE = "Aucune donnée trouvée";
	public static final String MSG_ERREUR_SQL = "Erreur lors de la requête SQL";

	// ========================================
	// BORDER SIZE
	// ========================================
	public static final int BORDER_SIZE_1 = 1;
	public static final int BORDER_SIZE_2 = 2; 

	public static JTextField creerTextField(String textePlaceholder, int largeur, int hauteur) {
		JTextField textField = new JTextField();
		textField.setFont(POLICE_STANDARD);
		textField.setForeground(TEXT_DARK);
		textField.setBackground(Color.WHITE);
		textField.setBorder(BorderFactory.createLineBorder(PRIMARY_BLUE, 1));
		if (textePlaceholder != null && !textePlaceholder.isEmpty()) {
			textField.setToolTipText(textePlaceholder);
		}
		textField.setPreferredSize(new Dimension(largeur, hauteur)); 
		return textField;
	}


	public static JPanel creerChampAvecLabel(String label, JComponent champ) {
		JPanel panel = new JPanel(new BorderLayout(10, 5));
		panel.setOpaque(false);

		JLabel jLabel = new JLabel(label);
		jLabel.setFont(POLICE_LABEL);
		jLabel.setForeground(TEXT_DARK);

		panel.add(jLabel, BorderLayout.NORTH);
		panel.add(champ, BorderLayout.CENTER);

		return panel;
	}


	public static JButton creerBouton(String texte) {
		JButton bouton = new JButton(texte);
		bouton.setFont(POLICE_STANDARD);
		bouton.setForeground(TEXT_DARK);        
		bouton.setBackground(new Color(200, 200, 200));

		bouton.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(PRIMARY_BLUE, 1),
				BorderFactory.createEmptyBorder(6, 12, 6, 12)
				));
		bouton.setFocusPainted(false);
		bouton.setOpaque(true);

		// Calculer la largeur nécessaire pour le texte
		FontMetrics fm = new JLabel().getFontMetrics(POLICE_STANDARD);
		int textWidth = fm.stringWidth(texte);
		int buttonWidth = Math.max(125, textWidth + 32); // Au moins 125px, ou texte + padding (augmenté à 32 pour plus d'espace)

		bouton.setPreferredSize(new Dimension(buttonWidth, 27));
		bouton.setMinimumSize(new Dimension(buttonWidth, 27));

		bouton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				bouton.setBackground(new Color(255, 239, 179));
			}
			public void mouseExited(MouseEvent evt) {
				bouton.setBackground(new Color(200, 200, 200));
			}
		});
		return bouton;
	}


	public static JButton creerBoutonPrimaire(String texte) {
		JButton button = new JButton(texte);

		button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		button.setForeground(Color.WHITE);  
		button.setBackground(new Color(0, 0, 139));  
		button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
		button.setFocusPainted(false);
		button.setOpaque(true);  

		// Calculer la largeur nécessaire pour le texte
		Font font = new Font("Segoe UI", Font.PLAIN, 14);
		FontMetrics fm = new JLabel().getFontMetrics(font);
		int textWidth = fm.stringWidth(texte);
		int buttonWidth = Math.max(120, textWidth + 32); // Au moins 120px, ou texte + padding

		button.setPreferredSize(new Dimension(buttonWidth, 30));
		button.setMinimumSize(new Dimension(buttonWidth, 30)); 

		button.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				button.setBackground(new Color(70, 130, 180));  
				button.setForeground(Color.WHITE);
			}
			public void mouseExited(java.awt.event.MouseEvent evt) {
				button.setBackground(new Color(0, 0, 139));  
				button.setForeground(Color.WHITE);
			}
		});

		return button;
	}


	public static JLabel creerLabel(String texte) {
		JLabel label = new JLabel(texte);
		label.setFont(POLICE_LABEL);
		label.setForeground(TEXT_DARK);
		return label;
	}

	public static JLabel creerTitre(String texte) {
		JLabel titre = new JLabel(texte, SwingConstants.CENTER);
		titre.setFont(new Font("Georgia", Font.BOLD, 24));
		titre.setForeground(new Color(40, 40, 40));
		titre.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0, 0, 2, 0, PRIMARY_BLUE),
				BorderFactory.createEmptyBorder(12, 10, 12, 10)
				));
		return titre;
	}

	public static JPanel creerPanelTitre(String titre) {
		JPanel panel = new JPanel();
		panel.setOpaque(true);
		panel.setBackground(Color.WHITE);
		panel.setBorder(BorderFactory.createLineBorder(PRIMARY_BLUE, 2));
		return panel;
	}

	public static JPanel creerPanelBordure() {
		JPanel panel = new JPanel();
		panel.setOpaque(true);
		panel.setBackground(Color.WHITE);
		panel.setBorder(BorderFactory.createLineBorder(PRIMARY_BLUE, 1));
		return panel;
	}

	public static JPanel creerPanelColore(Color couleurFond) {
		JPanel panel = new JPanel();
		panel.setBackground(couleurFond);
		panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		return panel;
	}


	public static <T> JComboBox<T> creerComboBox(int largeur, int hauteur) {
		JComboBox<T> comboBox = new JComboBox<>();
		comboBox.setFont(POLICE_STANDARD);
		comboBox.setForeground(TEXT_DARK);
		comboBox.setBackground(Color.WHITE);
		comboBox.setBorder(BorderFactory.createLineBorder(PRIMARY_BLUE, 1));
		comboBox.setPreferredSize(new Dimension(largeur, hauteur)); // Taille personnalisée
		return comboBox;
	}

	public static JPanel creerPanelBoutons(int alignement) {
		JPanel panel = new JPanel(new FlowLayout(alignement, 10, 10));
		panel.setOpaque(false);
		return panel;
	}

	public static Component creerEspacementVertical(int hauteur) {
		return Box.createVerticalStrut(hauteur);
	}

	public static Component creerEspacementHorizontal(int largeur) {
		return Box.createHorizontalStrut(largeur);
	}

	public static JScrollPane creerScrollPane(JTable table, String titre) {
		JScrollPane scroll = new JScrollPane(table);
		scroll.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(
						BorderFactory.createLineBorder(PRIMARY_BLUE, 1),
						" " + titre + " ",
						0, 0,
						new Font("Segoe UI", Font.BOLD, 14),
						PRIMARY_BLUE
						),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)
				));

		scroll.getViewport().setBackground(Color.WHITE);
		scroll.setOpaque(true);
		
		return scroll;
	}

	public static LocalDate parseLocalDate(String texte) {
		texte = texte.trim();
		try {
			return LocalDate.parse(texte, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		} catch (DateTimeParseException e1) {
			try {
				LocalDateTime dt = LocalDateTime.parse(texte, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				return dt.toLocalDate();
			} catch (DateTimeParseException e2) {
				throw new RuntimeException("Impossible de parser la date : " + texte, e2);
			}
		}
	}

    public static void styliserTable(JTable table) {
        table.setFont(POLICE_STANDARD);  
        table.setRowHeight(26);  
        table.setShowHorizontalLines(true);  
        table.setGridColor(new Color(230, 230, 230));  

        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(230, 240, 255));  
	        header.setForeground(TEXT_DARK);  
        header.setFont(new Font("Segoe UI", Font.BOLD, 13)); 
        header.setReorderingAllowed(false);  

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tbl, Object value,
                    boolean isSelected, boolean hasFocus, int row, int col) {

                Component c = super.getTableCellRendererComponent(tbl, value, isSelected, hasFocus, row, col);

                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 245));
                }
                return c;
            }
        });        
    }

    public static Component creerEspace(int largeur, int hauteur) {
        return Box.createRigidArea(new Dimension(largeur, hauteur));
    }
    
    public static LocalDate parseIsoDate(String texte) {
        return LocalDate.parse(texte.trim());
    }
    public static void changerTitre(JLabel labelTitre, String nouveauTitre) {
        labelTitre.setText(nouveauTitre);
    }

	public static JPanel creerPanelActions(JButton... boutons) {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		panel.setOpaque(false);

		for (JButton bouton : boutons) {
			panel.add(bouton);
		}
		return panel;
	}

	// Méthode pour créer un bouton avec texte sur deux lignes (pour la page Biens)
	public static JButton creerBoutonPrimaireBiens(String texte) {
		JButton button = new JButton();

		// Formater le texte pour les noms longs (ajouter un retour à la ligne)
		String texteFormate = formaterTexteBouton(texte);
		boolean estMultiLignes = texteFormate.contains("\n");
		button.setText("<html><center>" + texteFormate.replace("\n", "<br>") + "</center></html>");

		button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		button.setForeground(Color.WHITE);  
		button.setBackground(new Color(0, 0, 139));  
		button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
		button.setFocusPainted(false);
		button.setOpaque(true);  

		// Calculer la largeur nécessaire pour le texte
		Font font = new Font("Segoe UI", Font.PLAIN, 14);
		FontMetrics fm = new JLabel().getFontMetrics(font);

		int buttonWidth;
		if (estMultiLignes) {
			// Si le texte est sur deux lignes, calculer la largeur de la ligne la plus longue
			String[] lignes = texteFormate.split("\n");
			int largeurMax = 0;
			for (String ligne : lignes) {
				int largeur = fm.stringWidth(ligne);
				if (largeur > largeurMax) {
					largeurMax = largeur;
				}
			}
			buttonWidth = Math.max(140, largeurMax + 32); // Au moins 140px, ou texte + padding
		} else {
			// Texte sur une ligne, calculer la largeur nécessaire
			int textWidth = fm.stringWidth(texte);
			buttonWidth = Math.max(140, textWidth + 32); // Au moins 140px, ou texte + padding
		}

		int buttonHeight = estMultiLignes ? 50 : 30; // Plus haut si multi-lignes

		button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
		button.setMinimumSize(new Dimension(buttonWidth, buttonHeight));
		button.setMaximumSize(new Dimension(buttonWidth, buttonHeight));

		button.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				button.setBackground(new Color(70, 130, 180));  
				button.setForeground(Color.WHITE);
			}
			public void mouseExited(MouseEvent evt) {
				button.setBackground(new Color(0, 0, 139));  
				button.setForeground(Color.WHITE);
			}
		});

		return button;
	}

	private static String formaterTexteBouton(String texte) {
		if (texte.length() > 12 && texte.contains(" ")) {
			int indexEspace = texte.indexOf(" ");
			String partie1 = texte.substring(0, indexEspace);
			String partie2 = texte.substring(indexEspace + 1);
			return partie1 + "\n" + partie2;
		}
		return texte;
	}
	
	
    public static DefaultTableModel creerTableModelNonEditable(String[] colonnes) {
        return new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

   
    public static void ajouterDoubleClic( JTable table, int colonneId, Consumer<String> action) {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {

                    int row = table.getSelectedRow();
                    Object valeur = table.getValueAt(row, colonneId);

                    if (valeur != null) {
                        action.accept(valeur.toString());
                    }
                }
            }
        });
    }
    
    public static String fmt(Object o) {
        return o == null ? "" : o.toString();
    }

    public static String ouiNon(boolean b) {
        return b ? "Oui" : "Non";
    }

    public static String euros(double d) {
        return String.format("%.2f €", d);
    }
    
    public static Object[][] bienToTable(BienLouable b) {
        return new Object[][]{{
                b.getNumero_fiscale(),
                b.getType(),
                b.getSurface(),
                b.getNb_pieces(),
                b.getBatiment(),
                b.getEtage(),
                b.getPorte()
        }};
    }

    public static Object[][] batimentToTable(Batiment b) {
        return new Object[][]{{
                b.getIdBatiment(),
                b.getAdresse(),
                b.getVille(),
                b.getCodePostale(),
                fmt(b.getPeriodeDeConstruction())
        }};
    }

    public static Object[][] compteursToTable(List<Compteur> liste) {
        return liste.stream().map(c -> new Object[]{
                c.getId_compteur(),
                c.getType(),
                c.getIndex(),
                c.getId_variable(),
                c.getNumero_fiscale(),
                c.getIdBatiment(),
                ouiNon(c.isArchiver())
        }).toArray(Object[][]::new);
    }

    public static Object[][] contratToTable(ContratDeLocation c) {
        return new Object[][]{{
                c.getIdContrat(),
                c.getDate_debut(),
                c.getTrimestre(),
                c.getDate_sortie(),
                euros(c.getLoyer_aPayer()),
                c.getIRL(),
                c.getProvisions_charges(),
                ouiNon(c.isSolde_tout_compte_effectue()),
                c.getDuree(),
                c.getNumero_fiscale(),
                c.getId_locataire(),
                ouiNon(c.isArchive())
        }};
    }
    
    public static Object[][] loyersToTable(List<Loyer> loyers) {
        return loyers.stream()
                .map(l -> new Object[]{
                        l.getIdLocataire(),
                        l.getNumero_fiscale(),
                        fmt(l.getDate_paiement()),
                        euros(l.getMontant_provision()),
                        euros(l.getMontant_loyer()),
                        l.getMois(),
                        euros(l.getMontant_regularisation()),
                        ouiNon(l.isArchive())
                })
                .toArray(Object[][]::new);
    }


    
    public static Object[][] facturesToTable(List<Facture> factures) {
        return factures.stream()
                .map(f -> new Object[]{
                        f.getNumero(),
                        fmt(f.getDate_facture()),
                        f.getTypeFacture(),
                        euros(f.getMontant()),
                        f.getMode_paiement(),
                        f.getNumero_devis(),
                        f.getNature(),
                        fmt(f.getDate_devis()),
                        ouiNon(f.getPayer_par_locataire()),
                        f.getSiret(),
                        f.getNumero_fiscale(),
                        ouiNon(f.isArchive())
                })
                .toArray(Object[][]::new);
    }
    
    public static void rendreNonEditable(JTextField... champs) {
        for (JTextField champ : champs) champ.setEditable(false);
    }

    public static void rendreNonEditable(JComboBox<?>... combos) {
        for (JComboBox<?> combo : combos) combo.setEnabled(false);
    }

    public static JComboBox<String> creerComboBoxAvecItems(String... items) {
        JComboBox<String> combo = new JComboBox<>();
        for (String item : items) combo.addItem(item);
        return combo;
    }

    public static void ajouterChampFormulaire(JPanel panel, String label, JComponent champ) {
        panel.add(creerLabel(label));
        panel.add(champ);
    }

    public static String formatInt(int val) { return String.valueOf(val); }
    public static String formatDouble(double val) { return String.valueOf(val); }

    public static boolean champsNonRemplis(JTextField... champs) {
        for (JTextField champ : champs) {
            if (champ.getText().trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }
    
    public static JButton creerBoutonAvecAction(String texte, String name, java.awt.event.ActionListener listener) {
        JButton btn = creerBouton(texte);
        btn.setName(name);
        btn.addActionListener(listener);
        return btn;
    }

    public static void setTailleBouton(JButton btn, int largeur, int hauteur) {
        btn.setPreferredSize(new Dimension(largeur, hauteur));
        btn.setMinimumSize(new Dimension(largeur, hauteur));
    }

    public static JTable creerTableauAvecScroll(String[] colonnes, String titreScroll, JPanel parent) {
        DefaultTableModel model = creerTableModelNonEditable(colonnes);
        JTable table = new JTable(model);
        styliserTable(table);

        if (parent != null) {
            parent.add(creerScrollPane(table, titreScroll), BorderLayout.CENTER);
        }

        return table;
    }


    

	public static JPanel creerLigne(String label, JComponent comp) {
		JPanel p = new JPanel(new BorderLayout(10, 5));
		p.setOpaque(false);

		JLabel lbl = Utils.creerLabel(label);
		lbl.setPreferredSize(new Dimension(180, 30));

		p.add(lbl, BorderLayout.WEST);
		p.add(comp, BorderLayout.CENTER);

		p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		return p;
	}

}