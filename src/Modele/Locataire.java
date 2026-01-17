package Modele;

public class Locataire {

	private String idLocataire;
	private String nom;
	private String prenom;
	private String date_naissance;
	private char genre;
	private String email;
	private String telephone;
	private String adresse;
	private String ville;
	private String code_postale;
	private boolean archive;

	public Locataire(String idLocataire, String nom, String prenom, String date_naissance, char genre, String email,
			String adresse, String ville, String code_postale, String telephone, boolean archive) {
		this.idLocataire = idLocataire;
		this.nom = nom;
		this.prenom = prenom;
		this.date_naissance = date_naissance;
		this.genre = genre;
		this.email = email;
		this.telephone = telephone;
		this.adresse = adresse;
		this.ville = ville;
		this.code_postale = code_postale;
		this.archive = archive;
	}


	// ------------------
	// Getters
	// -------------------

	public String getIdLocataire() {
		return idLocataire;
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public String getDate_naissance() {
		return date_naissance;
	}

	public char getGenre() {
		return genre;
	}

	public String getTelephone() {
		return telephone;
	}

	public String getAdresse() {
		return adresse;
	}

	public String getEmail() {
		return email;
	}

	public String getVille() {
		return ville;
	}

	public String getCode_postale() {
		return code_postale;
	}
	
	public boolean isArchive() {
		return archive;
	}

	// -------------------
	// Setters
	// -------------------
	public void setIdLocataire(String idLocataire) {
		this.idLocataire = idLocataire;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public void setDate_naissance(String date_naissance) {
		this.date_naissance = date_naissance;
	}

	public void setGenre(char genre) {
		this.genre = genre;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public void setCode_postale(String code_postale) {
		this.code_postale = code_postale;
	}
	
	public void setArchive(boolean archive) {
		this.archive = archive;
	}

	@Override
	public String toString() {
		return "Locataire [idLocataire=" + idLocataire + ", nom=" + nom + ", prenom=" + prenom + ", date_naissance="
				+ date_naissance + ", genre=" + genre + ", email=" + email + ", telephone=" + telephone + ", adresse="
				+ adresse + ", ville=" + ville + ", code_postale=" + code_postale + ", archive=" + archive + "]";
	}

}
