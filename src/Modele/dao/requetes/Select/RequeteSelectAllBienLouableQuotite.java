package Modele.dao.requetes.Select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Modele.BienLouableQuotite;
import Modele.dao.requetes.Requete;

public class RequeteSelectAllBienLouableQuotite implements Requete<BienLouableQuotite> {

    @Override
    public String requete() {
        return "SELECT NUMERO_FISCALE, TYPE, SURFACE, NOMBRE_PIECES, ETAGE, PORTE, ID_BATIMENT, QUOTITE " +
               "FROM SND5405A.V_BIEN_LOUABLE_QUOTITE " +
               "ORDER BY ID_BATIMENT, NUMERO_FISCALE";
    }

    @Override
    public void parametres(PreparedStatement prSt, BienLouableQuotite obj) throws SQLException {
        // Pas de paramètres pour cette requête
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        // Pas de paramètres pour cette requête
    }
}
