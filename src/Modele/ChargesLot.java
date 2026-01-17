package Modele;

public class ChargesLot {

	private String numero_fiscale;
	private int annee;
	private String type;
	private double taxe_ordinaires_lot;
	private double taxes_autres_lot;
	private double taxe_om_lot;

	public ChargesLot() {
	}

	public ChargesLot(String numero_fiscale, int annee, String type, double taxe_ordinaires_lot,
			double taxes_autres_lot, double taxe_om_lot) {
		this.numero_fiscale = numero_fiscale;
		this.annee = annee;
		this.type = type;
		this.taxe_ordinaires_lot = taxe_ordinaires_lot;
		this.taxes_autres_lot = taxes_autres_lot;
		this.taxe_om_lot = taxe_om_lot;
	}

	public String getNumero_fiscale() {
		return numero_fiscale;
	}

	public void setNumero_fiscale(String numero_fiscale) {
		this.numero_fiscale = numero_fiscale;
	}

	public int getAnnee() {
		return annee;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getTaxe_ordinaires_lot() {
		return taxe_ordinaires_lot;
	}

	public void setTaxe_ordinaires_lot(double taxe_ordinaires_lot) {
		this.taxe_ordinaires_lot = taxe_ordinaires_lot;
	}

	public double getTaxes_autres_lot() {
		return taxes_autres_lot;
	}

	public void setTaxes_autres_lot(double taxes_autres_lot) {
		this.taxes_autres_lot = taxes_autres_lot;
	}

	public double getTaxe_om_lot() {
		return taxe_om_lot;
	}

	public void setTaxe_om_lot(double taxe_om_lot) {
		this.taxe_om_lot = taxe_om_lot;
	}

	@Override
	public String toString() {
		return "ChargesLot [numero_fiscale=" + numero_fiscale + ", annee=" + annee + ", type=" + type
				+ ", taxe_ordinaires_lot=" + taxe_ordinaires_lot + ", taxes_autres_lot=" + taxes_autres_lot
				+ ", taxe_om_lot=" + taxe_om_lot + "]";
	}

}
