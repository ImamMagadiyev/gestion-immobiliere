package Modele.dao.requetes.Select;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Variable;
import Modele.dao.requetes.Requete;

public class RequeteSelectByIdVariable implements Requete<Variable> {
    @Override
    public String requete() {
        return "SELECT * FROM SND5405A.SAE_VARIABLE WHERE ID_VARIABLE = ?";
    }
    @Override
    public void parametres(PreparedStatement prSt, Variable v) throws SQLException {
        prSt.setString(1, v.getId_Variable());
    }
    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        prSt.setString(1, id[0]);
    }
}