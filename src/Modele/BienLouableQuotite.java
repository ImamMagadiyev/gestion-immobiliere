package Modele;

public class BienLouableQuotite {
	
	private String numero_fiscale;
	private String type;
	private double surface;
	private int nombre_pieces;
	private int etage;
	private String porte;
	private String id_batiment;
	private double quotite;
	
	public BienLouableQuotite(String numero_fiscale, String type, double surface, int nombre_pieces,
			int etage, String porte, String id_batiment, double quotite) {
		this.numero_fiscale = numero_fiscale;
		this.type = type;
		this.surface = surface;
		this.nombre_pieces = nombre_pieces;
		this.etage = etage;
		this.porte = porte;
		this.id_batiment = id_batiment;
		this.quotite = quotite;
	}

	public String getNumero_fiscale() {
		return numero_fiscale;
	}

	public void setNumero_fiscale(String numero_fiscale) {
		this.numero_fiscale = numero_fiscale;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getSurface() {
		return surface;
	}

	public void setSurface(double surface) {
		this.surface = surface;
	}

	public int getNombre_pieces() {
		return nombre_pieces;
	}

	public void setNombre_pieces(int nombre_pieces) {
		this.nombre_pieces = nombre_pieces;
	}

	public int getEtage() {
		return etage;
	}

	public void setEtage(int etage) {
		this.etage = etage;
	}

	public String getPorte() {
		return porte;
	}

	public void setPorte(String porte) {
		this.porte = porte;
	}

	public String getId_batiment() {
		return id_batiment;
	}

	public void setId_batiment(String id_batiment) {
		this.id_batiment = id_batiment;
	}

	public double getQuotite() {
		return quotite;
	}

	public void setQuotite(double quotite) {
		this.quotite = quotite;
	}

	@Override
	public String toString() {
		return "BienLouableQuotite [numero_fiscale=" + numero_fiscale + ", type=" + type + ", surface=" + surface
				+ ", nombre_pieces=" + nombre_pieces + ", etage=" + etage + ", porte=" + porte + ", id_batiment="
				+ id_batiment + ", quotite=" + quotite + "]";
	}
}
