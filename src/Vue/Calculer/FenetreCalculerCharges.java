package Vue.Calculer;

import javax.swing.*;
import java.awt.*;
import Vue.Utils;
import Controleur.GestionFenetrePrincipale;
import Controleur.Affichage.Gestion_Calculer_Charges;

public class FenetreCalculerCharges extends JInternalFrame {

    private static final long serialVersionUID = 1L;
    
    private JComboBox<String> comboLoc;
    private JComboBox<String> comboBien;
    private JComboBox<String> comboAnnee;
    private JTextField champProvisions;
    private JTextField champCharges;
    private JTextField champResultat;
    private JButton btnCalculer;
    

	private JButton btnValider;
    private JButton btnAnnuler;
    
    
    private GestionFenetrePrincipale gfp;
    private Gestion_Calculer_Charges gestion;

    /**
     * FenetreCalculerCharges : JInternalFrame pour la régularisation des charges.
     * 
     * Cette fenêtre affiche un formulaire pour choisir un locataire, un bien et une année,
     * puis saisir le total des provisions et le montant réel des charges.
     * Le champ résultat est en lecture seule et sert à afficher le calcul.
     * 
     * Les boutons "Calculer" et "Valider" déclenchent des actions gérées par gfp,
     * et "Annuler" ferme simplement la fenêtre.
     */
    public FenetreCalculerCharges(GestionFenetrePrincipale gfp) {
        super("Calculer les charges", true, true, true, true);
        this.gfp = gfp;
    	this.gestion = new Gestion_Calculer_Charges(this);
    	
        setSize(750, 650);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 240, 240));
        
        // Ligne bleue en haut comme dans les autres pages
        JPanel ligneHaut = new JPanel();
        ligneHaut.setBackground(new Color(0, 102, 204));
        ligneHaut.setPreferredSize(new Dimension(getWidth(), 2));
        add(ligneHaut, BorderLayout.NORTH);
        
        // Titre avec style harmonisé
        JLabel labelTitre = Utils.creerTitre("Régularisation des charges");
        
        JPanel panneauTitre = new JPanel(new BorderLayout());
        panneauTitre.setBackground(Color.WHITE);
        panneauTitre.add(labelTitre, BorderLayout.CENTER);
        panneauTitre.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        add(panneauTitre, BorderLayout.NORTH);

        // Zone de contenu avec le formulaire
        JPanel zoneContenu = new JPanel(new BorderLayout());
        zoneContenu.setBackground(new Color(240, 240, 240));
        zoneContenu.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JPanel panneauFormulaire = new JPanel(new GridBagLayout());
        panneauFormulaire.setBackground(Color.WHITE);
        panneauFormulaire.setPreferredSize(new Dimension(650, 500));
        panneauFormulaire.setMinimumSize(new Dimension(650, 480));
        panneauFormulaire.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(35, 40, 35, 40)
        ));

        GridBagConstraints contraintes = new GridBagConstraints();
        contraintes.insets = new Insets(10, 10, 10, 10);
        contraintes.anchor = GridBagConstraints.WEST;
        contraintes.fill = GridBagConstraints.HORIZONTAL;

        // Locataire
        contraintes.gridx = 0; 
        contraintes.gridy = 0; 
        contraintes.weightx = 0;
        JLabel labelLocataire = Utils.creerLabel("Locataire :");
        labelLocataire.setFont(new Font("Segoe UI", Font.BOLD, 13));
        labelLocataire.setForeground(new Color(60, 60, 60));
        panneauFormulaire.add(labelLocataire, contraintes);
        contraintes.gridx = 1; 
        contraintes.weightx = 1.0;
        this.comboLoc = Utils.creerComboBox(150, 25);
        comboLoc.setPreferredSize(new Dimension(0, 35));
        comboLoc.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        panneauFormulaire.add(comboLoc, contraintes);

        // Bien louable
        contraintes.gridx = 0; 
        contraintes.gridy = 1; 
        contraintes.weightx = 0;
        JLabel labelBien = Utils.creerLabel("Bien louable :");
        labelBien.setFont(new Font("Segoe UI", Font.BOLD, 13));
        labelBien.setForeground(new Color(60, 60, 60));
        panneauFormulaire.add(labelBien, contraintes);
        contraintes.gridx = 1; 
        contraintes.weightx = 1.0;
        this.comboBien = Utils.creerComboBox(150, 25);
        comboBien.setPreferredSize(new Dimension(0, 35));
        comboBien.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        panneauFormulaire.add(comboBien, contraintes);

        // Année concernée
        contraintes.gridx = 0; 
        contraintes.gridy = 2; 
        contraintes.weightx = 0;
        JLabel labelAnnee = Utils.creerLabel("Année concernée :");
        labelAnnee.setFont(new Font("Segoe UI", Font.BOLD, 13));
        labelAnnee.setForeground(new Color(60, 60, 60));
        panneauFormulaire.add(labelAnnee, contraintes);
        contraintes.gridx = 1; 
        contraintes.weightx = 1.0;
        this.comboAnnee = Utils.creerComboBox(150, 25);
        comboAnnee.setPreferredSize(new Dimension(0, 35));
        comboAnnee.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        panneauFormulaire.add(comboAnnee, contraintes);

        // Séparateur avec style amélioré
        contraintes.gridx = 0; 
        contraintes.gridy = 3; 
        contraintes.gridwidth = 2;
        contraintes.fill = GridBagConstraints.HORIZONTAL;
        contraintes.insets = new Insets(25, 0, 25, 0);
        JSeparator separateur = new JSeparator();
        separateur.setForeground(new Color(0, 102, 204));
        separateur.setBackground(new Color(0, 102, 204));
        separateur.setPreferredSize(new Dimension(0, 2));
        panneauFormulaire.add(separateur, contraintes);

        // Montant total des provisions versées
        contraintes.insets = new Insets(10, 10, 10, 10);
        contraintes.gridx = 0; 
        contraintes.gridy = 4; 
        contraintes.gridwidth = 1; 
        contraintes.weightx = 0;
        JLabel labelProvisions = Utils.creerLabel("Montant total des provisions versées :");
        labelProvisions.setFont(new Font("Segoe UI", Font.BOLD, 13));
        labelProvisions.setForeground(new Color(60, 60, 60));
        panneauFormulaire.add(labelProvisions, contraintes);
        contraintes.gridx = 1; 
        contraintes.weightx = 1.0;
        this.champProvisions = Utils.creerTextField("Montant total provisions charges", 150, 25);
        champProvisions.setPreferredSize(new Dimension(0, 38));
        champProvisions.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        champProvisions.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        panneauFormulaire.add(champProvisions, contraintes);

        // Montant réel des charges payées
        contraintes.gridx = 0; 
        contraintes.gridy = 5; 
        contraintes.weightx = 0;
        JLabel labelCharges = Utils.creerLabel("Montant réel des charges payées :");
        labelCharges.setFont(new Font("Segoe UI", Font.BOLD, 13));
        labelCharges.setForeground(new Color(60, 60, 60));
        panneauFormulaire.add(labelCharges, contraintes);
        contraintes.gridx = 1; 
        contraintes.weightx = 1.0;
        this.champCharges = Utils.creerTextField("Montant réel des charges", 150, 25);
        champCharges.setPreferredSize(new Dimension(0, 38));
        champCharges.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        champCharges.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        panneauFormulaire.add(champCharges, contraintes);

        // Résultat avec mise en valeur
        contraintes.gridx = 0; 
        contraintes.gridy = 6; 
        contraintes.weightx = 0;
        contraintes.insets = new Insets(25, 10, 10, 10);
        JLabel labelResultat = Utils.creerLabel("Résultat :");
        labelResultat.setFont(new Font("Segoe UI", Font.BOLD, 15));
        labelResultat.setForeground(new Color(0, 102, 204));
        panneauFormulaire.add(labelResultat, contraintes);
        contraintes.gridx = 1; 
        contraintes.weightx = 1.0;
        this.champResultat = Utils.creerTextField("Résultat", 150, 25);
        champResultat.setEditable(false);
        champResultat.setPreferredSize(new Dimension(0, 42));
        champResultat.setFont(new Font("Segoe UI", Font.BOLD, 14));
        champResultat.setForeground(new Color(0, 102, 204));
        champResultat.setBackground(new Color(240, 248, 255));
        champResultat.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 102, 204), 2),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        panneauFormulaire.add(champResultat, contraintes);

        // Boutons avec meilleur espacement
        contraintes = new GridBagConstraints();
        contraintes.gridx = 0; 
        contraintes.gridy = 7; 
        contraintes.gridwidth = 2;
        contraintes.anchor = GridBagConstraints.CENTER; 
        contraintes.fill = GridBagConstraints.NONE;
        contraintes.insets = new Insets(30, 0, 10, 0);
        JPanel panneauBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 0));
        panneauBoutons.setOpaque(false);
        
        btnCalculer = Utils.creerBoutonPrimaire("Calculer");
        btnCalculer.setName("btnCalculer_Charges");
        btnCalculer.setPreferredSize(new Dimension(130, 38));
        btnCalculer.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnCalculer.addActionListener(gfp);
        btnCalculer.addActionListener(gestion);
        
        btnValider = Utils.creerBoutonPrimaire("Valider");
        btnValider.setName("btnValider_Charges");
        btnValider.setPreferredSize(new Dimension(130, 38));
        btnValider.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnValider.addActionListener(gfp);
        btnValider.addActionListener(gestion);

        btnAnnuler = Utils.creerBouton("Annuler");
        btnAnnuler.setName("btnAnnuler_Charges_Calc");
        btnAnnuler.addActionListener(e -> dispose());
        btnAnnuler.addActionListener(gestion);
        
        panneauBoutons.add(btnCalculer);
        panneauBoutons.add(btnValider);
        panneauBoutons.add(btnAnnuler);
        panneauFormulaire.add(panneauBoutons, contraintes);

        // Enveloppe pour centrer le formulaire
        JPanel enveloppe = new JPanel(new GridBagLayout());
        enveloppe.setOpaque(false);
        GridBagConstraints cEnv = new GridBagConstraints();
        cEnv.gridx = 0; 
        cEnv.gridy = 0; 
        cEnv.weightx = 1.0; 
        cEnv.weighty = 1.0; 
        cEnv.anchor = GridBagConstraints.CENTER;
        enveloppe.add(panneauFormulaire, cEnv);
        zoneContenu.add(enveloppe, BorderLayout.CENTER);

        add(zoneContenu, BorderLayout.CENTER);
        this.gestion.chargerBiens();
        this.gestion.chargerLocataires();
        this.gestion.chargerAnnees();
    }
    
    // Getters
    public JComboBox<String> getComboLoc() {
        return comboLoc;
    }
    
    public JComboBox<String> getComboBien() {
        return comboBien;
    }
    
    public JComboBox<String> getComboAnnee() {
        return comboAnnee;
    }
    
    public JTextField getChampProvisions() {
        return champProvisions;
    }
    
    public JTextField getChampCharges() {
        return champCharges;
    }
    
    public JTextField getChampResultat() {
        return champResultat;
    }
    
    public JButton getBtnCalculer() {
		return btnCalculer;
	}

	public void setBtnCalculer(JButton btnCalculer) {
		this.btnCalculer = btnCalculer;
	}

	public JButton getBtnValider() {
		return btnValider;
	}

	public void setBtnValider(JButton btnValider) {
		this.btnValider = btnValider;
	}

	public JButton getBtnAnnuler() {
		return btnAnnuler;
	}

	public void setBtnAnnuler(JButton btnAnnuler) {
		this.btnAnnuler = btnAnnuler;
	}

	public GestionFenetrePrincipale getGfp() {
		return gfp;
	}
	
}
