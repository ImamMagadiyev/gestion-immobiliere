package Modele.dao.requetes.SousProgramme;

import java.sql.CallableStatement;
import java.sql.SQLException;

import Modele.BienLouable;

public class SousProgrammeInsertBienLouable implements SousProgramme<BienLouable> {

    @Override
    public String appelSousProgramme() {
        return "{call SND5405A.SAE_INSERT_BIENLOUABLE(?, ?, ?, ?, ?, ?, ?)}";

    }

    @Override
    public void parametres(CallableStatement stmt, BienLouable bien) throws SQLException {
        stmt.setString(1, bien.getNumero_fiscale());
        stmt.setString(2, bien.getType());
        stmt.setInt(3, bien.getSurface());
        stmt.setInt(4, bien.getNb_pieces());
        stmt.setString(5, bien.getBatiment());
        stmt.setInt(6, bien.getEtage());
        stmt.setString(7, bien.getPorte());
    }

    @Override
    public void parametresSequence(CallableStatement stmt, BienLouable donnees) throws SQLException { }

    @Override
    public void parametresCalcul(CallableStatement stmt, BienLouable donnees) throws SQLException { }
}
