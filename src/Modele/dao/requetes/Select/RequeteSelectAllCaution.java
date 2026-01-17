package Modele.dao.requetes.Select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Modele.Caution;
import Modele.dao.requetes.Requete;

public class RequeteSelectAllCaution implements Requete<Caution> {

    @Override
    public String requete() {
        return "SELECT * FROM SND5405A.SAE_CAUTION WHERE ARCHIVE = 0 ORDER BY 1";
    }

    @Override
    public void parametres(PreparedStatement prSt, Caution donnee) throws SQLException { }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException { }
}
