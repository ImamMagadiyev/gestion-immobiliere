package Modele;

public class Assurance {

	private String id_assurance;
	private String type;
	private int annee;
	private String numero; // FK


	public Assurance(String id_assurance, String type, int annee, String numero) {
		this.id_assurance = id_assurance;
		this.type = type;
		this.annee = annee;
		this.numero = numero;
	}

	// Getters
	public String getId_assurance() {
		return id_assurance;
	}

	public String getType() {
		return type;
	}

	public int getAnnee() {
		return annee;
	}

	public String getNumero() {
		return numero;
	}

	// Setters
	public void setId_assurance(String id_assurance) {
		this.id_assurance = id_assurance;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	@Override
	public String toString() {
		return "Assurance [id_assurance=" + id_assurance + ", type=" + type + ", annee=" + annee + ", numero=" + numero + "]";
	}
}
