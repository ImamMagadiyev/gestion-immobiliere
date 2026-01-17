package Modele;

public class Impots {

	private String idImpots;
	private double recuperable_impot;
	private int annee;
	private String type;
	private String numero; // FK

	public Impots(String idImpots, double recuperable_impot, int annee, String type, String numero) {
		this.idImpots = idImpots;
		this.recuperable_impot = recuperable_impot;
		this.annee = annee;
		this.type = type;
		this.numero = numero;
	}

	// Getters
	public String getIdImpots() {
		return idImpots;
	}

	public double getRecuperable_impot() {
		return recuperable_impot;
	}

	public int getAnnee() {
		return annee;
	}

	public String getType() {
		return type;
	}

	public String getNumero() {
		return numero;
	}

	// Setters
	public void setIdImpots(String idImpots) {
		this.idImpots = idImpots;
	}

	public void setRecuperable_impot(double recuperable_impot) {
		this.recuperable_impot = recuperable_impot;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	@Override
	public String toString() {
		return "Impots [idImpots=" + idImpots + ", recuperable_impot=" + recuperable_impot + ", annee=" + annee
				+ ", type=" + type + ", numero=" + numero + "]";
	}
}
