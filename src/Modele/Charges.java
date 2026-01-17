package Modele;

public class Charges {

    private String id_charges;
    private int annee;
    private double taxe_ordinaire;
    private double taxes_autres;
    private String type;

    public Charges(String id_charges, int annee, double taxe_ordinaire, double taxes_autres, String type) {
        super();
        this.id_charges = id_charges;
        this.annee = annee;
        this.taxe_ordinaire = taxe_ordinaire;
        this.taxes_autres = taxes_autres;
        this.type = type;
    }

    public String getId_charges() {
        return id_charges;
    }

    public void setId_charges(String id_charges) {
        this.id_charges = id_charges;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public double getTaxe_ordinaire() {
        return taxe_ordinaire;
    }

    public void setTaxe_ordinaire(double taxe_ordinaire) {
        this.taxe_ordinaire = taxe_ordinaire;
    }

    public double getTaxes_autres() {
        return taxes_autres;
    }

    public void setTaxes_autres(double taxes_autres) {
        this.taxes_autres = taxes_autres;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Charges [id_charges=" + id_charges + ", annee=" + annee + ", taxe_ordinaire=" + taxe_ordinaire
                + ", taxes_autres=" + taxes_autres + ", type=" + type + "]";
    }
}