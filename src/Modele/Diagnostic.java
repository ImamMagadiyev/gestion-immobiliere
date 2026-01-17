package Modele;

import java.time.LocalDate;

public class Diagnostic {

	private String reference;
	private LocalDate date_valide;
	private String type_diagnostic;
	private String numero_fiscale; 
	private String siret;
	private boolean archive;

	public Diagnostic(String reference, LocalDate date_invalide, String type_diagnostic, String bienLouable,
			String entreprise, boolean archive) {
		super();
		this.reference = reference;
		this.date_valide = date_invalide;
		this.type_diagnostic = type_diagnostic;
		this.numero_fiscale = bienLouable;
		this.siret = entreprise;
		this.archive = archive;
	}

	// ------------------------
	//      Getters
	// ------------------------

	public String getReference() {
		return reference;
	}

	public LocalDate getDate_valide() {
		return date_valide;
	}

	public String getType_diagnostic() {
		return type_diagnostic;
	}

	public String getNumero_Fiscale() {
		return numero_fiscale;
	}

	public String getSiret() {
		return siret;
	}

	
	public boolean isArchive() {
		return archive;
	}
	


	// -----------------------
	//        Setters
	// ----------------------

	
	public void setReference(String reference) {
		this.reference = reference;
	}

	public void setDate_valide(LocalDate date_invalide) {
		this.date_valide = date_invalide;
	}

	public void setType_diagntic(String type_diagnostic) {
		this.type_diagnostic = type_diagnostic;
	}

	public void setNumero_Ficale(String numero_fiscale) {
		this.numero_fiscale = numero_fiscale;
	}

	public void setSiret(String siret) {
		this.siret = siret;
	}
	
	public void setArchive(boolean archive) {
		this.archive = archive;
	}

	@Override
	public String toString() {
		return "Diagnostic [reference=" + reference + ", date_invalide=" + date_valide + ", type_diagnostic="
				+ type_diagnostic + ", bienLouable=" + numero_fiscale + ", entreprise=" + siret + ", archive=" + archive + "]";
	}

}
