package Modele.dao.requetes.Delete;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Travaux;
import Modele.dao.requetes.Requete;

public class RequeteDeleteTravaux implements Requete<Travaux>{
	@Override
    public String requete() {
        return "DELETE FROM SND5405A.SAE_TRAVAUX WHERE ID_TRAVAUX = ?";
    }
    @Override
    public void parametres(PreparedStatement prSt, Travaux v) throws SQLException {
        prSt.setString(1, v.getId_Travaux());
    }
    @Override public void parametres(PreparedStatement prSt, String... id) throws SQLException { }
}
