package Modele;

public class Comporter {

	private String idBatiment; // FK
	private String numero; // FK

	public Comporter(String idBatiment, String numero) {
		this.idBatiment = idBatiment;
		this.numero = numero;
	}

	// Getters
	public String getIdBatiment() {
		return idBatiment;
	}

	public String getNumero() {
		return numero;
	}

	// Setters
	public void setIdBatiment(String idBatiment) {
		this.idBatiment = idBatiment;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	@Override
	public String toString() {
		return "Comporter [idBatiment=" + idBatiment + ", numero=" + numero + "]";
	}
}
