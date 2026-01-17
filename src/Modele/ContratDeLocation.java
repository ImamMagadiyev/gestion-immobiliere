package Modele;

public class ContratDeLocation {
	
	private String idContrat;
	private String date_debut;
	private String trimestre;
	private String date_sortie;
	private double loyer_aPayer;
	private double IRL;
	private double provisions_charges;
	private boolean solde_tout_compte_effectue;
	private int duree;
	private String numero_fiscale; //FK
	private String id_locataire; //fk
	private boolean archive;
	
	public ContratDeLocation(String idContrat, String date_debut, String trimestre, String date_sortie,
			double loyer_aPayer, double iRL, double provisions_charges, boolean solde_tout_compte_effectue,
			int duree, String numero_fiscale, String id_locataire, boolean archive) {
		super();
		this.idContrat = idContrat;
		this.date_debut = date_debut;
		this.trimestre = trimestre;
		this.date_sortie = date_sortie;
		this.loyer_aPayer = loyer_aPayer;
		this.IRL = iRL;
		this.provisions_charges = provisions_charges;
		this.solde_tout_compte_effectue = solde_tout_compte_effectue;
		this.duree = duree;
		this.numero_fiscale = numero_fiscale;
		this.id_locataire = id_locataire;
		this.archive = archive;
	}

	public String getIdContrat() {
		return idContrat;
	}

	public void setIdContrat(String idContrat) {
		this.idContrat = idContrat;
	}

	public String getDate_debut() {
		return date_debut;
	}

	public void setDate_debut(String date_debut) {
		this.date_debut = date_debut;
	}

	public String getTrimestre() {
		return trimestre;
	}

	public void setTrimestre(String trimestre) {
		this.trimestre = trimestre;
	}

	public String getDate_sortie() {
		return date_sortie;
	}

	public void setDate_sortie(String date_sortie) {
		this.date_sortie = date_sortie;
	}

	public double getLoyer_aPayer() {
		return loyer_aPayer;
	}

	public void setLoyer_aPayer(double loyer_aPayer) {
		this.loyer_aPayer = loyer_aPayer;
	}

	public double getIRL() {
		return IRL;
	}

	public void setIRL(double iRL) {
		IRL = iRL;
	}

	public double getProvisions_charges() {
		return provisions_charges;
	}

	public void setProvisions_charges(double provisions_charges) {
		this.provisions_charges = provisions_charges;
	}

	public boolean isSolde_tout_compte_effectue() {
		return solde_tout_compte_effectue;
	}

	public void setSolde_tout_compte_effectue(boolean solde_tout_compte_effectue) {
		this.solde_tout_compte_effectue = solde_tout_compte_effectue;
	}

	public int getDuree() {
		return duree;
	}

	public void setDuree(int duree) {
		this.duree = duree;
	}

	public String getNumero_fiscale() {
		return numero_fiscale;
	}

	public void setNumero_fiscale(String numero_fiscale) {
		this.numero_fiscale = numero_fiscale;
	}

	public String getId_locataire() {
		return id_locataire;
	}

	public void setId_locataire(String id_locataire) {
		this.id_locataire = id_locataire;
	}

	public boolean isArchive() {
		return archive;
	}

	public void setArchive(boolean archive) {
		this.archive = archive;
	}
	
	@Override
	public String toString() {
		return "ContratDeLocation [" +
			"idContrat=" + idContrat +
			", date_debut=" + date_debut +
			", trimestre=" + trimestre +
			", date_sortie=" + date_sortie +
			", loyer_aPayer=" + loyer_aPayer +
			", IRL=" + IRL +
			", provisions_charges=" + provisions_charges +
			", solde_tout_compte_effectue=" + solde_tout_compte_effectue +
			", duree=" + duree +
			", numero_fiscale=" + numero_fiscale +
			", id_locataire=" + id_locataire +
			", archive=" + archive +
			"]";
	}
		
}
