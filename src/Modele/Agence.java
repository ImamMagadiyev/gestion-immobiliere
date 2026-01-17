package Modele;

public class Agence{

	private String numero; // FK

	public Agence(String numero) {
		this.numero = numero;
	}

	// Getter
	public String getNumero() {
		return numero;
	}

	// Setter
	public void setNumero(String numero) {
		this.numero = numero;
	}

	@Override
	public String toString() {
		return "Agence [numero=" + numero + "]";
	}
}
