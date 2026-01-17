package Modele;

public class Entreprise {

	private String siret;
	private String nom;
	private String ville;
	private String mail;
	private String adresse;
	private String specialite;
	private String code_postale;


	public Entreprise(String siret, String nom, String ville, String mail, String adresse, String specialite,
			String code_postale) {
		this.siret = siret;
		this.nom = nom;
		this.ville = ville;
		this.mail = mail;
		this.adresse = adresse;
		this.specialite = specialite;
		this.code_postale = code_postale;
	}


	// -------------------
	// Getters
	// -------------------

	public String getSiret() {
		return siret;
	}

	public String getNom() {
		return nom;
	}

	public String getVille() {
		return ville;
	}	

	public String getMail() {
		return mail;
	}

	public String getAdresse() {
		return adresse;
	}

	public String getSpecialite() {
		return specialite;
	}

	public String getCode_postale() {
		return code_postale;
	}


	// ---------------------
	// Setters
	// ---------------------

	public void setSiret(String siret) {
		this.siret = siret;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public void setSpecialite(String specialite) {
		this.specialite = specialite;
	}

	public void setCode_postale(String code_postale) {
		this.code_postale = code_postale;
	}


	@Override
	public String toString() {
		return "Entreprise [siret=" + siret + ", nom=" + nom + ", ville=" + ville + ", mail=" + mail + ", adresse="
				+ adresse + ", specialite=" + specialite + ", code_postale=" + code_postale + "]";
	}

}
