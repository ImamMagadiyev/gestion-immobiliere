package Modele;

public class Variable {

	private String Id_Variable;
	private String Service;
	private String fournisseur;
	private String eau_gaz_electricite;
	private String numero; //FK
	private double prix_unitaire;

	public Variable(String id_Variable, String service, String fournisseur, String eau_gaz_electricite, String numero) {
		super();
		this.Id_Variable = id_Variable;
		this.Service = service;
		this.fournisseur = fournisseur;
		this.eau_gaz_electricite = eau_gaz_electricite;
		this.numero = numero;
		this.prix_unitaire = 0;
	}

	public Variable(String id_Variable, String service, String fournisseur, String eau_gaz_electricite, String numero, double prix_unitaire) {
		super();
		this.Id_Variable = id_Variable;
		this.Service = service;
		this.fournisseur = fournisseur;
		this.eau_gaz_electricite = eau_gaz_electricite;
		this.numero = numero;
		this.prix_unitaire = prix_unitaire;
	}

	public String getId_Variable() {
		return Id_Variable;
	}

	public void setId_Variable(String id_Variable) {
		Id_Variable = id_Variable;
	}

	public String getService() {
		return Service;
	}

	public void setService(String service) {
		Service = service;
	}

	public String getFournisseur() {
		return fournisseur;
	}

	public void setFournisseur(String fournisseur) {
		this.fournisseur = fournisseur;
	}

	public String getEau_gaz_electricite() {
		return eau_gaz_electricite;
	}

	public void setEau_gaz_electricite(String eau_gaz_electricite) {
		this.eau_gaz_electricite = eau_gaz_electricite;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public double getPrix_unitaire() {
		return prix_unitaire;
	}

	public void setPrix_unitaire(double prix_unitaire) {
		this.prix_unitaire = prix_unitaire;
	}

	@Override
	public String toString() {
		return "Variable [Id_Variable=" + Id_Variable + ", Service=" + Service + ", fournisseur=" + fournisseur
				+ ", eau_gaz_electricite=" + eau_gaz_electricite + ", numero=" + numero + ", prix_unitaire=" + prix_unitaire + "]";
	}




}
