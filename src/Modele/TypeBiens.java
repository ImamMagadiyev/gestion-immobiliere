package Modele;

public enum TypeBiens {
	APPARTEMENT("Appartement"),
	GARAGE("Garage");

	private final String designation;

	TypeBiens(String designation) {
		this.designation = designation;
	}

	public String getDesignation() {
		return designation;
	}

	@Override
	public String toString() {
		return designation;
	}
}
