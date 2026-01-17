package Modele.dao.requetes.Select;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Charges;
import Modele.dao.requetes.Requete;

public class RequeteSelectAllCharges implements Requete<Charges> {
    @Override
    public String requete() {
        return "SELECT * FROM SND5405A.SAE_CHARGES ORDER BY 1";
    }
    @Override public void parametres(PreparedStatement prSt, Charges d) throws SQLException { }
    @Override public void parametres(PreparedStatement prSt, String... id) throws SQLException { }
}