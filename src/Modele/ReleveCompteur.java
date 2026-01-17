package Modele;

import java.time.LocalDate;

import Vue.Utils;

public class ReleveCompteur {

    private String id_compteur; 
    private LocalDate date_releve; 
    private int indice;

    public ReleveCompteur(String id_compteur, LocalDate date_releve, int indice) {
        this.id_compteur = id_compteur;
        this.date_releve = date_releve;
        this.indice = indice;
    }

    public ReleveCompteur(String id_compteur, String date_releveStr, int indice) {
        this.id_compteur = id_compteur;
        this.date_releve = Utils.parseLocalDate(date_releveStr);
        this.indice = indice;
    }

    // Getters
    public String getId_compteur() {
        return id_compteur;
    }

    public LocalDate getDate_releve() {
        return date_releve;
    }

    public int getIndice() {
        return indice;
    }

    // Setters
    public void setId_compteur(String id_compteur) {
        this.id_compteur = id_compteur;
    }

    public void setDate_releve(LocalDate date_releve) {
        this.date_releve = date_releve;
    }

    public void setDate_releve(String date_releveStr) {
        this.date_releve = Utils.parseLocalDate(date_releveStr);
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    @Override
    public String toString() {
        return "ReleveCompteur [id_compteur=" + id_compteur + ", date_releve=" + date_releve + ", indice=" + indice + "]";
    }
}
