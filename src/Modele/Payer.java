package Modele;

public class Payer {
	
	private String id_charges;
	private String id_locataire;
	
	public Payer(String id_charges, String id_locataire) {
		super();
		this.id_charges = id_charges;
		this.id_locataire = id_locataire;
	}

	public String getId_charges() {
		return id_charges;
	}

	public void setId_charges(String id_charges) {
		this.id_charges = id_charges;
	}

	public String getId_locataire() {
		return id_locataire;
	}

	public void setId_locataire(String id_locataire) {
		this.id_locataire = id_locataire;
	}

	@Override
	public String toString() {
		return "Payer [id_charges=" + id_charges + ", id_locataire=" + id_locataire + "]";
	}
	
	
}
