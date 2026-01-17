package Modele;

import java.sql.Date;

public class Facture {

	private String numero;
	private Date date_facture;
	private String type_travaux;
	private double montant;
	private String mode_paiement;
	private String numero_devis;
	private String nature;
	private Date date_devis;
	private boolean payer_par_locataire;
	private String siret; 
	private String numero_fiscale;
	private double montantDevis;
	private boolean archive;

	public Facture(String numero, Date date_facture, String type_travaux, double montant, String mode_paiement,
			String numero_devis, String nature, Date date_devis, boolean payer_par_locataire,
			String siret, String numero_fiscale,double mtnDevis, boolean archive) {

		this.numero = numero;
		this.date_facture = date_facture;
		this.type_travaux = type_travaux;
		this.montant = montant;
		this.mode_paiement = mode_paiement;
		this.numero_devis = numero_devis;
		this.nature = nature;
		this.date_devis = date_devis;
		this.payer_par_locataire = payer_par_locataire;
		this.siret = siret;
		this.numero_fiscale = numero_fiscale;
		this.montantDevis = mtnDevis;
		this.archive = archive;
	}

	// Getters
	public String getNumero() {
		return numero;
	}

	public Date getDate_facture() {
		return date_facture;
	}

	public String getType_travaux() {
		return type_travaux;
	}

	public double getMontant() {
		return montant;
	}

	public String getMode_paiement() {
		return mode_paiement;
	}

	public String getNumero_devis() {
		return numero_devis;
	}

	public String getNature() {
		return nature;
	}

	public Date getDate_devis() {
		return date_devis;
	}

	public boolean getPayer_par_locataire() {
		return payer_par_locataire;
	}

	public String getSiret() {
		return siret;
	}

	public String getNumero_fiscale() {
		return numero_fiscale;
	}
	
	public boolean isArchive() {
		return archive;
	}

	// Alias pour getType_travaux() utilisé dans les interfaces
	public String getTypeFacture() {
		return type_travaux;
	}

	// Setters
	public void setNumero(String numero) {
		this.numero = numero;
	}

	public void setDate_facture(Date dateFacture) {
		this.date_facture = dateFacture;
	}

	public void setType_travaux(String type_travaux) {
		this.type_travaux = type_travaux;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public void setMode_paiement(String mode_paiement) {
		this.mode_paiement = mode_paiement;
	}

	public void setNumero_devis(String numero_devis) {
		this.numero_devis = numero_devis;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public void setDate_devis(Date dateDevis) {
		this.date_devis = dateDevis;
	}

	public void setPayer_par_locataire(boolean payer_par_locataire) {
		this.payer_par_locataire = payer_par_locataire;
	}

	public void setSiret(String siret) {
		this.siret = siret;
	}

	public void setNumero_fiscale(String numero_fiscale) {
		this.numero_fiscale = numero_fiscale;
	}

	public void setArchive(boolean archive) {
		this.archive = archive;
	}
	public double getMontantDevis() {
		return this.montantDevis;
	}
	public void setMontantDevis(double mtnDevis) {
		 this.montantDevis =mtnDevis;
	}
	
	@Override
	public String toString() {
		return "Facture [numero=" + numero + ", date_facture=" + date_facture + ", type_travaux=" + type_travaux
				+ ", montant=" + montant + ", mode_paiement=" + mode_paiement + ", numero_devis=" + numero_devis
				+ ", nature=" + nature + ", date_devis=" + date_devis + ", payer_par_locataire=" + payer_par_locataire
				+ ", siret=" + siret + ", numero_fiscale=" + numero_fiscale + ", archive=" + archive + "]";
	}
}
