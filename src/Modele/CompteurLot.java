package Modele;

import java.time.LocalDate;

public class CompteurLot {

	private String numero_fiscale;
	private String id_batiment;
	private String id_compteur;
	private LocalDate date_releve;
	private String type_compteur;
	private double quotite;
	private double prix_unitaire;
	private double indice_lot;
	private double consommation;
	private double prix_total;

	public CompteurLot() {
	}

	public CompteurLot(String numero_fiscale, String id_batiment, String id_compteur, LocalDate date_releve,
			String type_compteur, double quotite, double prix_unitaire, double indice_lot, double consommation,
			double prix_total) {
		this.numero_fiscale = numero_fiscale;
		this.id_batiment = id_batiment;
		this.id_compteur = id_compteur;
		this.date_releve = date_releve;
		this.type_compteur = type_compteur;
		this.quotite = quotite;
		this.prix_unitaire = prix_unitaire;
		this.indice_lot = indice_lot;
		this.consommation = consommation;
		this.prix_total = prix_total;
	}

	public String getNumero_fiscale() {
		return numero_fiscale;
	}

	public void setNumero_fiscale(String numero_fiscale) {
		this.numero_fiscale = numero_fiscale;
	}

	public String getId_batiment() {
		return id_batiment;
	}

	public void setId_batiment(String id_batiment) {
		this.id_batiment = id_batiment;
	}

	public String getId_compteur() {
		return id_compteur;
	}

	public void setId_compteur(String id_compteur) {
		this.id_compteur = id_compteur;
	}

	public LocalDate getDate_releve() {
		return date_releve;
	}

	public void setDate_releve(LocalDate date_releve) {
		this.date_releve = date_releve;
	}

	public String getType_compteur() {
		return type_compteur;
	}

	public void setType_compteur(String type_compteur) {
		this.type_compteur = type_compteur;
	}

	public double getQuotite() {
		return quotite;
	}

	public void setQuotite(double quotite) {
		this.quotite = quotite;
	}

	public double getPrix_unitaire() {
		return prix_unitaire;
	}

	public void setPrix_unitaire(double prix_unitaire) {
		this.prix_unitaire = prix_unitaire;
	}

	public double getIndice_lot() {
		return indice_lot;
	}

	public void setIndice_lot(double indice_lot) {
		this.indice_lot = indice_lot;
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
		return "CompteurLot [numero_fiscale=" + numero_fiscale + ", id_batiment=" + id_batiment + ", id_compteur="
				+ id_compteur + ", date_releve=" + date_releve + ", type_compteur=" + type_compteur + ", quotite="
				+ quotite + ", prix_unitaire=" + prix_unitaire + ", indice_lot=" + indice_lot + ", consommation="
				+ consommation + ", prix_total=" + prix_total + "]";
	}

}
