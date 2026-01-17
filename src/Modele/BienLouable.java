package Modele;

public class BienLouable {

	private String numero_fiscale;
	private String type;
	private int surface;
	private int nb_pieces;
	private String batiment;
	private int etage;
	private String porte;


	public BienLouable(String numero_fiscale, String type, int surface, int nb_pieces, String batiment, int etage, String porte) {
		this.numero_fiscale = numero_fiscale;
		this.type = type;
		this.surface = surface;
		this.nb_pieces = nb_pieces;
		this.batiment = batiment;
		this.etage = etage;
		this.porte = porte;
	}

	// --------------------
	// Getters
	// --------------------

	public String getNumero_fiscale() {
		return numero_fiscale;
	}

	public String getType() {
		return type;
	}

	public int getSurface() {
		return surface;
	}

	public int getNb_pieces() {
		return nb_pieces;
	}

	public String getBatiment() {
		return this.batiment;
	}
	
	public int getEtage() {
		return etage;
	}

	public String getPorte() {
		return porte;
	}
	// ---------------------
	// Setters
	// ---------------------

	public void setNumero_fiscale(String numero_fiscale) {
		this.numero_fiscale = numero_fiscale;
	}


	public void setType(String type) {
		this.type = type;
	}


	public void setSurface(int surface) {
		this.surface = surface;
	}



	public void setNb_pieces(int nb_pieces) {
		this.nb_pieces = nb_pieces;
	}

	public void setBatiment(String batiment) {
		this.batiment = batiment;
	}
	
	public void setEtage(int etage) {
		this.etage = etage;
	}

	public void setPorte(String porte) {
		this.porte = porte;
	}

	@Override
	public String toString() {
		return "BienLouable [numero_fiscale=" + numero_fiscale + ", type=" + type + ", surface=" + surface
				+ ", nb_pièces=" + nb_pieces + ", etage" + etage + "porte" + porte + "]";
	}



}
