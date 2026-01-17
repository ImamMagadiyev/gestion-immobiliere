package Modele.dao.requetes.SousProgramme;

import java.sql.CallableStatement;
import java.sql.SQLException;
import Modele.Variable;

public class SousProgrammeInsertVariable implements SousProgramme<Variable> {

    @Override
    public String appelSousProgramme() {
        return "{call SND5405A.SAE_INSERT_VARIABLE(?, ?, ?, ?, ?, ?)}";

    }

    @Override
    public void parametres(CallableStatement stmt, Variable v) throws SQLException {
        stmt.setString(1, v.getId_Variable());
        stmt.setString(2, v.getService());
        stmt.setString(3, v.getFournisseur());
        stmt.setString(4, v.getEau_gaz_electricite());
        stmt.setString(5, v.getNumero());
        stmt.setDouble(6, v.getPrix_unitaire());
    }

    @Override public void parametresSequence(CallableStatement stmt, Variable d) throws SQLException { }
    @Override public void parametresCalcul(CallableStatement stmt, Variable d) throws SQLException { }
}