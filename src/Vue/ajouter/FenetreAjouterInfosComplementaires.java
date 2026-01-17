package Vue.ajouter;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Vue.Utils;

public class FenetreAjouterInfosComplementaires extends JInternalFrame {
	private JCheckBox checkBox ;
	private String typeFacture;
	private String numFacture; // Nouvelle variable pour stocker le numéro

	private JTextField idTravaux, Raison, Id_Impots, AnneImpots, TypeImpots, IdAssurance, TypeAssurance,
	Annee, Id_Variables, service, fournisseur, typeVariable;
	
	private Object instances;

	private JPanel conteneurChamps;
	FenetreAjouterFacture parent;
	
	
	
	public FenetreAjouterInfosComplementaires(FenetreAjouterFacture parent, String numFacture) {
		
		super("Ajouter complementaires", true, true, true, true);
		
		this.parent = parent;
		this.numFacture = numFacture;
		this.typeFacture = (String) parent.getComboType().getSelectedItem();
		
		/* ===================== TITRE ===================== */
        JLabel lblTitre = Utils.creerTitre("Ajouter infos complémentaires ");
        add(lblTitre, BorderLayout.NORTH);

        /* ===================== CONTENU ===================== */
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setOpaque(false);
        form.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
		setSize(400, 300);
		setLayout(new BorderLayout());

		conteneurChamps = new JPanel();
		configurerChampsSpecifiques();

		add(conteneurChamps, BorderLayout.CENTER);

		JButton btnEnregistrer = Utils.creerBouton("Enregistrer ces détails");
		;
		btnEnregistrer.addActionListener(e -> dispose());
		add(btnEnregistrer, BorderLayout.SOUTH);
	}

	private void configurerChampsSpecifiques() {
		conteneurChamps.removeAll();
		// Basé sur votre MCD (image_bef50a.png)
		switch (typeFacture) {
		case "Travaux":

			idTravaux = Utils.creerTextField("ID_Travaux", 200, 25);
			Raison = Utils.creerTextField("Raison", 200,25);

			conteneurChamps.add(this.parent.creerLigne("Id Travaux : ", idTravaux));
			conteneurChamps.add(this.parent.creerLigne("Raison : ",Raison));
			break;
		case "Impots":


			Id_Impots = Utils.creerTextField("Id Impots", 200, 25);
			AnneImpots =Utils.creerTextField("Anne", 200, 25);
			TypeImpots = Utils.creerTextField("Type", 200, 25);
			checkBox = new JCheckBox();

			conteneurChamps.add(this.parent.creerLigne("Impots : ",Id_Impots));
			conteneurChamps.add(this.parent.creerLigne("Anne : ",AnneImpots));
			conteneurChamps.add(this.parent.creerLigne("Type : ",TypeImpots));
			conteneurChamps.add(this.parent.creerLigne("Recuperable (0/N) : ",checkBox));

			break;
		case "Assurances":
			IdAssurance = Utils.creerTextField("Id Assurance", 200, 25);
			TypeAssurance = Utils.creerTextField("Type", 200, 25);
			Annee = Utils.creerTextField("Annee", 200, 25);
			conteneurChamps.add(this.parent.creerLigne("Id Assurance : ",IdAssurance));
			conteneurChamps.add(this.parent.creerLigne("Type : ",TypeAssurance));
			conteneurChamps.add(this.parent.creerLigne("Annee : ",Annee));

			break;
		case "Variable":
			Id_Variables = Utils.creerTextField("Id Variable", 200, 25);
			service = Utils.creerTextField("Service", 200, 25);
			fournisseur = Utils.creerTextField("Fournisseur", 200, 25);
			typeVariable = Utils.creerTextField("Type", 200, 25);
			conteneurChamps.add(this.parent.creerLigne("Id Variable : ",Id_Variables));
			conteneurChamps.add(this.parent.creerLigne("Service : ",service));
			conteneurChamps.add(this.parent.creerLigne("Fournisseur : ",fournisseur));
			conteneurChamps.add(this.parent.creerLigne("Type : ",typeVariable));

			break;
		}
	}

	public String getTypeFacture() {
		return typeFacture;
	}

	public int getAnneImpots() {
		int anneImpots = Integer.parseInt(AnneImpots.getText().trim());
		return anneImpots ;	}

	public void setAnneImpots(JTextField anneImpots) {
		AnneImpots = anneImpots;
	}

	public int getAnnee() {
		int anneAssurance = Integer.parseInt(Annee.getText().trim());
		return anneAssurance;
	}



	public JTextField getIdTravaux() {
		return idTravaux;
	}

	public JTextField getRaison() {
		return Raison;
	}

	public JTextField getImpots() {
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

	public String getNumFacture() {
		return numFacture;
	}

	public int getValeurCheckBox() {
		return checkBox.isSelected() ? 1 : 0;
	}

	public Object getInstances() {
		return instances;
	}

	public void setInstances(Object instances) {
		this.instances = instances;
	}
}
