package Modele;

public class CompteurBien {

	private String numero_fiscale;
	private int indice;
	private int index_val;
	private double prix_unitaire;
	private String type;
	private double consommation;
	private double prix_total;

	public CompteurBien() {
	}

	public CompteurBien(String numero_fiscale, int indice, int index_val, double prix_unitaire, String type,
			double consommation, double prix_total) {
		this.numero_fiscale = numero_fiscale;
		this.indice = indice;
		this.index_val = index_val;
		this.prix_unitaire = prix_unitaire;
		this.type = type;
		this.consommation = consommation;
		this.prix_total = prix_total;
	}

	public String getNumero_fiscale() {
		return numero_fiscale;
	}

	public void setNumero_fiscale(String numero_fiscale) {
		this.numero_fiscale = numero_fiscale;
	}

	public int getIndice() {
		return indice;
	}

	public void setIndice(int indice) {
		this.indice = indice;
	}

	public int getIndex_val() {
		return index_val;
	}

	public void setIndex_val(int index_val) {
		this.index_val = index_val;
	}

	public double getPrix_unitaire() {
		return prix_unitaire;
	}

	public void setPrix_unitaire(double prix_unitaire) {
		this.prix_unitaire = prix_unitaire;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getConsommation() {
		return consommation;
	}

	public void setConsommation(double consommation) {
		this.consommation = consommation;
	}

	public double getPrix_total() {
		return prix_total;
	}

	public void setPrix_total(double prix_total) {
		this.prix_total = prix_total;
	}

	@Override
	public String toString() {
		return "CompteurBien [numero_fiscale=" + numero_fiscale + ", indice=" + indice + ", index_val=" + index_val
				+ ", prix_unitaire=" + prix_unitaire + ", type=" + type + ", consommation=" + consommation
				+ ", prix_total=" + prix_total + "]";
	}

}
