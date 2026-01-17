package Modele.dao.requetes.SousProgramme;

import java.sql.CallableStatement;
import java.sql.SQLException;
import Modele.Charges;

public class SousProgrammeInsertCharges implements SousProgramme<Charges> {

    @Override
    public String appelSousProgramme() {
        return "{call SND5405A.Insert_Charges(?, ?, ?, ?, ?)}";
    }

    @Override
    public void parametres(CallableStatement stmt, Charges c) throws SQLException {
        stmt.setString(1, c.getId_charges());
        stmt.setInt(2, c.getAnnee());
        stmt.setDouble(3, c.getTaxe_ordinaire());
        stmt.setDouble(4, c.getTaxes_autres());
        stmt.setString(5, c.getType());
    }

    @Override public void parametresSequence(CallableStatement stmt, Charges d) throws SQLException { }
    @Override public void parametresCalcul(CallableStatement stmt, Charges d) throws SQLException { }
}