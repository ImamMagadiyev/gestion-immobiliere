package Modele.dao.requetes.Delete;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Modele.ContratDeLocation;
import Modele.dao.requetes.Requete;

public class RequeteDeleteContratDeLocation implements Requete<ContratDeLocation> {

    @Override
    public String requete() {
        return "DELETE FROM SND5405A.SAE_CONTRAT_DE_LOCATION WHERE ID_CONTRAT = ?";
    }

    @Override
    public void parametres(PreparedStatement prSt, ContratDeLocation c) throws SQLException {
        prSt.setString(1, c.getIdContrat());
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException { }
}
