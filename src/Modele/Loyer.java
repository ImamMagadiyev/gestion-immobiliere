package Modele;

import java.time.LocalDate;

public class Loyer {

  private String idLocataire; 
  private String numero_fiscale; 
  private LocalDate date_paiement; 
  private double montant_provision;
  private double montant_loyer;
  private String mois;
  private double montant_regularisation;
  private boolean archive;


  public Loyer(String idLocataire, String numero_fiscale, LocalDate date_paiement, double montant_provision,
      double montant_loyer, String mois, double montant_regularisation, boolean archive) {

    this.idLocataire = idLocataire;
    this.numero_fiscale = numero_fiscale;
    this.date_paiement = date_paiement;
    this.montant_provision = montant_provision;
    this.montant_loyer = montant_loyer;
    this.mois = mois;
    this.montant_regularisation = montant_regularisation;
    this.archive = archive;
  }

  // Getters
  public String getIdLocataire() {
    return idLocataire;
  }

  public String getNumero_fiscale() {
    return numero_fiscale;
  }

  public LocalDate getDate_paiement() {
    return date_paiement;
  }

  public double getMontant_provision() {
    return montant_provision;
  }

  public double getMontant_loyer() {
    return montant_loyer;
  }

  public String getMois() {
    return mois;
  }

  public double getMontant_regularisation() {
    return montant_regularisation;
  }
  
  public boolean isArchive() {
	return archive;
  }

  // Setters
  public void setIdLocataire(String idLocataire) {
    this.idLocataire = idLocataire;
  }

  public void setNumero_fiscale(String numero_fiscale) {
    this.numero_fiscale = numero_fiscale;
  }

  public void setDate_paiement(LocalDate date_paiement) {
    this.date_paiement = date_paiement;
  }

  public void setMontant_provision(double montant_provision) {
    this.montant_provision = montant_provision;
  }

  public void setMontant_loyer(double montant_loyer) {
    this.montant_loyer = montant_loyer;
  }

  public void setMois(String mois) {
    this.mois = mois;
  }

  public void setMontant_regularisation(double montant_regularisation) {
    this.montant_regularisation = montant_regularisation;
  }
  
  public void setArchive(boolean archive) {
	this.archive = archive;
  }

  @Override
  public String toString() {
    return "Loyer [idLocataire=" + idLocataire + ", numero_fiscale=" + numero_fiscale + ", date_paiement="
        + date_paiement + ", montant_provision=" + montant_provision + ", montant_loyer=" + montant_loyer
        + ", mois=" + mois + ", montant_regularisation=" + montant_regularisation + ", archive=" + archive + "]";
  }
}
