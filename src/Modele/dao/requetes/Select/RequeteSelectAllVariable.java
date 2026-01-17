package Modele.dao.requetes.Select;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Variable;
import Modele.dao.requetes.Requete;

public class RequeteSelectAllVariable implements Requete<Variable> {
    @Override
    public String requete() {
        return "SELECT * FROM SND5405A.SAE_VARIABLE ORDER BY ID_VARIABLE";
    }
    @Override public void parametres(PreparedStatement prSt, Variable d) throws SQLException { }
    @Override public void parametres(PreparedStatement prSt, String... id) throws SQLException { }
}