package Modele.dao.requetes.Select;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Compteur;
import Modele.dao.requetes.Requete;

public class RequeteSelectAllCompteur implements Requete<Compteur> {
    @Override
    public String requete() {
        return "SELECT * FROM SND5405A.SAE_COMPTEUR WHERE ARCHIVE = 0 ORDER BY 1";
    }
    @Override public void parametres(PreparedStatement prSt, Compteur d) throws SQLException { }
    @Override public void parametres(PreparedStatement prSt, String... id) throws SQLException { }
}