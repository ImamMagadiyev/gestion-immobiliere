package Modele;

import java.time.LocalDate;

public class Batiment {

    private String idBatiment;
    private String adresse;
    private String ville;
    private String codePostale;
    private LocalDate periodeDeConstruction;

    public Batiment(String idBatiment, String adresse, String ville,
                    String codePostale, LocalDate periodeDeConstruction) {
        this.idBatiment = idBatiment;
        this.adresse = adresse;
        this.ville = ville;
        this.codePostale = codePostale;
        this.periodeDeConstruction = periodeDeConstruction;
    }

    // -------------------------------
    // Getters
    // -------------------------------

    public String getIdBatiment() {
        return idBatiment;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getVille() {
        return ville;
    }

    public String getCodePostale() {
        return codePostale;
    }

    public LocalDate getPeriodeDeConstruction() {
        return periodeDeConstruction;
    }

    // -------------------------------
    // Setters
    // -------------------------------

    public void setIdBatiment(String idBatiment) {
        this.idBatiment = idBatiment;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public void setCodePostale(String codePostale) {
        this.codePostale = codePostale;
    }

    public void setPeriodeDeConstruction(LocalDate periodeDeConstruction) {
        this.periodeDeConstruction = periodeDeConstruction;
    }

    @Override
    public String toString() {
        return "Batiment [idBatiment=" + idBatiment +
               ", adresse=" + adresse +
               ", ville=" + ville +
               ", codePostale=" + codePostale +
               ", periodeDeConstruction=" + periodeDeConstruction + "]";
    }
}
