package Modele;

public class Conclure {

	private String idLocataire; // FK
	private String idContrat; // FK

	public Conclure(String idLocataire, String idContrat) {
		this.idLocataire = idLocataire;
		this.idContrat = idContrat;
	}

	// Getters
	public String getIdLocataire() {
		return idLocataire;
	}

	public String getIdContrat() {
		return idContrat;
	}


	// Setters
	public void setIdLocataire(String idLocataire) {
		this.idLocataire = idLocataire;
	}

	public void setIdContrat(String idContrat) {
		this.idContrat = idContrat;
	}

	@Override
	public String toString() {
		return "Conclure [idLocataire=" + idLocataire + ", idContrat=" + idContrat +  "]";
	}
}
