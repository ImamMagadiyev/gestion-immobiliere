package Modele;

public class Compteur {
	
	private String id_compteur;
	private String type;
	private double index;
	private String id_variable;
	private String numero_fiscale; 
	private String idBatiment;
	private boolean archiver;

	public Compteur(String id_compteur, String type, double index,  String id_variable, String numero_fiscale, String idBatiment, boolean archiver) {
		this.id_compteur = id_compteur;
		this.type = type;
		this.index = index;
		this.numero_fiscale = numero_fiscale;
		this.idBatiment = idBatiment;
		this.id_variable = id_variable;
		this.archiver = archiver;
	}

	// Getters
	public String getId_compteur() {
		return id_compteur;
	}

	public String getType() {
		return type;
	}

	public double getIndex() {
		return index;
	}

	public String getNumero_fiscale() {
		return numero_fiscale;
	}

	public String getIdBatiment() {
		return idBatiment;
	}
	
	public String getId_variable() {
		return id_variable;
	}
	

	public boolean isArchiver() {
		return archiver;
	}


	// Setters
	public void setId_compteur(String id_compteur) {
		this.id_compteur = id_compteur;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setIndex(double index) {
		this.index = index;
	}

	public void setNumero_fiscale(String numero_fiscale) {
		this.numero_fiscale = numero_fiscale;
	}

	public void setIdBatiment(String idBatiment) {
		this.idBatiment = idBatiment;
	}
	

	public void setId_variable(String id_variable) {
		this.id_variable = id_variable;
	}
	
	public void setArchiver(boolean archiver) {
		this.archiver = archiver;
	}
	
	@Override
	public String toString() {
		return "Compteur [id_compteur=" + id_compteur + ", type=" + type + ", index=" + index
				+ ", numero_fiscale=" + numero_fiscale + ", idBatiment=" + idBatiment + ", id_variable=" + id_variable + ", archiver=" + archiver + "]";
	}
}
