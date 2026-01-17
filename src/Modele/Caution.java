package Modele;

public class Caution {

	private String id_caution;
	private String nom;
	private String prenom;
	private String adresse;
	private String ville;
	private String code_postale;
	private String profession;
	private String type_contrat_travail;
	private String date_naissance;
	private String idContrat; 
	private String adresseElectronique;
	private String numeroTel;
	private String qualiteBailleur;
	private boolean archive;

	public Caution(String id_caution, String nom, String prenom, String adresse, String ville, String code_postale,
			String profession, String type_contrat_travail, String date_naissance, String idContrat, String adresseElectronique, String numeroTel, String qualiteBailleur, boolean archive) {

		this.id_caution = id_caution;
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.ville = ville;
		this.code_postale = code_postale;
		this.profession = profession;
		this.type_contrat_travail = type_contrat_travail;
		this.date_naissance = date_naissance;
		this.idContrat = idContrat;
		this.adresseElectronique = adresseElectronique;
		this.numeroTel = numeroTel;
		this.qualiteBailleur = qualiteBailleur;
		this.archive = archive;
	}

	// Getters
	public String getId_caution() {
		return id_caution;
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public String getAdresse() {
		return adresse;
	}

	public String getVille() {
		return ville;
	}

	public String getCode_postale() {
		return code_postale;
	}

	public String getProfession() {
		return profession;
	}

	public String getType_contrat_travail() {
		return type_contrat_travail;
	}

	public String getDate_naissance() {
		return date_naissance;
	}

	public String getIdContrat() {
		return idContrat;
	}
	
	public String getAdresseElectronique() {
		return adresseElectronique;
	}

	public String getNumeroTel() {
		return numeroTel;
	}

	public String getQualiteBailleur() {
		return qualiteBailleur;
	}

	public boolean isArchive() {
		return archive;
	}

	// Setters
	public void setId_caution(String id_caution) {
		this.id_caution = id_caution;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
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

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public void setType_contrat_travail(String type_contrat_travail) {
		this.type_contrat_travail = type_contrat_travail;
	}

	public void setDate_naissance(String date_naissance) {
		this.date_naissance = date_naissance;
	}

	public void setIdContrat(String idContrat) {
		this.idContrat = idContrat;
	}
	
	public void setAdresseElectronique(String adresseElectronique) {
		this.adresseElectronique = adresseElectronique;
	}

	public void setNumeroTel(String numeroTel) {
		this.numeroTel = numeroTel;
	}

	public void setQualiteBailleur(String qualiteBailleur) {
		this.qualiteBailleur = qualiteBailleur;
	}

	public void setArchive(boolean archive) {
		this.archive = archive;
	}

	@Override
	public String toString() {
		return "Caution [id_caution=" + id_caution + ", nom=" + nom + ", prenom=" + prenom + ", adresse=" + adresse
				+ ", ville=" + ville + ", code_postale=" + code_postale + ", profession=" + profession
				+ ", type_contrat_travail=" + type_contrat_travail + ", date_naissance=" + date_naissance
				+ ", idContrat=" + idContrat + ", adresseElectronique=" + adresseElectronique + ", numeroTel="
				+ numeroTel + ", qualiteBailleur=" + qualiteBailleur + ", archive=" + archive + "]";
	}

	
}
