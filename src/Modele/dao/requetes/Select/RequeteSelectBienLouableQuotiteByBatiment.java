package Modele.dao.requetes.Select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Modele.BienLouableQuotite;
import Modele.dao.requetes.Requete;

public class RequeteSelectBienLouableQuotiteByBatiment implements Requete<BienLouableQuotite> {

    @Override
    public String requete() {
        return "SELECT NUMERO_FISCALE, TYPE, SURFACE, NOMBRE_PIECES, ETAGE, PORTE, ID_BATIMENT, QUOTITE " +
               "FROM SND5405A.V_BIEN_LOUABLE_QUOTITE " +
               "WHERE ID_BATIMENT = ? " +
               "ORDER BY ETAGE, PORTE";
    }

    @Override
    public void parametres(PreparedStatement prSt, BienLouableQuotite obj) throws SQLException {
        prSt.setString(1, obj.getId_batiment());
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        prSt.setString(1, id[0]);
    }
}
