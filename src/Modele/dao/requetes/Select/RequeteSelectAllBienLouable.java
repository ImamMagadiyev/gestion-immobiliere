package Modele.dao.requetes.Select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Modele.BienLouable;
import Modele.dao.requetes.Requete;

public class RequeteSelectAllBienLouable implements Requete<BienLouable> {

    @Override
    public String requete() {
        return "SELECT * FROM SND5405A.SAE_BIEN_LOUABLE ORDER BY 1";
    }

    @Override
    public void parametres(PreparedStatement prSt, BienLouable donnee) throws SQLException { }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException { }
}
