package Modele;

public class Travaux {

	private String Id_Travaux;
	private String raison;
	private String numero; 

	public Travaux(String id_Travaux, String raison, String numero) {
		

		this.Id_Travaux = id_Travaux;
		this.raison = raison;
		this.numero = numero;
	}

	public String getId_Travaux() {
		return Id_Travaux;
	}

	public void setId_Travaux(String id_Travaux) {
		Id_Travaux = id_Travaux;
	}

	public String getRaison() {
		return raison;
	}

	public void setRaison(String raison) {
		this.raison = raison;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}


	@Override
	public String toString() {
		return "Travaux [Id_Travaux=" + Id_Travaux + ", raison=" + raison + ", numero=" + numero + "]";
	}


}
