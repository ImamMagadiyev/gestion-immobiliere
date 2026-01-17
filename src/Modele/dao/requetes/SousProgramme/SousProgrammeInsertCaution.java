package Modele.dao.requetes.SousProgramme;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;

import Modele.Caution;

public class SousProgrammeInsertCaution implements SousProgramme<Caution> {

    @Override
    public String appelSousProgramme() {
        return "{call SND5405A.sae_insert_caution(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

    }

    @Override
    public void parametres(CallableStatement stmt, Caution c) throws SQLException {
        stmt.setString(1, c.getId_caution());
        stmt.setString(2, c.getNom());
        stmt.setString(3, c.getPrenom());
        if (c.getDate_naissance() != null) {
            stmt.setDate(4, Date.valueOf(c.getDate_naissance()));
        } else {
            stmt.setDate(4, null);
        }
        stmt.setString(5, c.getAdresse());
        stmt.setString(6, c.getVille());
        stmt.setString(7, c.getCode_postale());
        stmt.setString(8, c.getProfession());
        stmt.setString(9, c.getType_contrat_travail());
        stmt.setString(10, c.getIdContrat());
        stmt.setString(11, c.getAdresseElectronique());
        stmt.setString(12, c.getNumeroTel());
        stmt.setString(13, c.getQualiteBailleur());
        stmt.setInt(14, 0);
    }

    @Override
    public void parametresSequence(CallableStatement stmt, Caution donnees) throws SQLException { }

    @Override
    public void parametresCalcul(CallableStatement stmt, Caution donnees) throws SQLException { }
}
