package Modele.dao.requetes.Delete;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Variable;
import Modele.dao.requetes.Requete;

public class RequeteDeleteVariable implements Requete<Variable> {
    @Override
    public String requete() {
        return "DELETE FROM SND5405A.SAE_VARIABLE WHERE ID_VARIABLE = ?";
    }
    @Override
    public void parametres(PreparedStatement prSt, Variable v) throws SQLException {
        prSt.setString(1, v.getId_Variable());
    }
    @Override public void parametres(PreparedStatement prSt, String... id) throws SQLException { }
}