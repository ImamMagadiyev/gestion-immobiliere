package Modele.dao.requetes.Select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Modele.Batiment;
import Modele.dao.requetes.Requete;

public class RequeteSelectByIdBatiment implements Requete<Batiment> {

	@Override
    public String requete() {
        return "SELECT * FROM SND5405A.SAE_BATIMENT WHERE ID_BATIMENT = ?";
    }

    @Override
    public void parametres(PreparedStatement prSt, Batiment data) throws SQLException {
        prSt.setString(1, data.getIdBatiment());
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        if (id.length == 0) {
            throw new SQLException("ID du bâtiment manquant !");
        }
        prSt.setString(1, id[0]);
    }
}
