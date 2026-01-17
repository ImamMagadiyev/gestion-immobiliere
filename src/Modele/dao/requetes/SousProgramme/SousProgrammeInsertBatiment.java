package Modele.dao.requetes.SousProgramme;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Date;

import Modele.Batiment;

public class SousProgrammeInsertBatiment implements SousProgramme<Batiment> {

    @Override
    public String appelSousProgramme() {
        return "{call SND5405A.SAE_Insert_Batiment(?, ?, ?, ?, ?)}";
    }

    @Override
    public void parametres(CallableStatement stmt, Batiment b) throws SQLException {
        stmt.setString(1, b.getIdBatiment());
        stmt.setString(2, b.getAdresse());
        stmt.setString(3, b.getVille());
        stmt.setString(4, b.getCodePostale());
        stmt.setDate(5, Date.valueOf(b.getPeriodeDeConstruction()));
    }

    @Override public void parametresSequence(CallableStatement stmt, Batiment d) throws SQLException { }
    @Override public void parametresCalcul(CallableStatement stmt, Batiment d) throws SQLException { }
}
