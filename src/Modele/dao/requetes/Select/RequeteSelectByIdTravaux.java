package Modele.dao.requetes.Select;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Travaux;
import Modele.dao.requetes.Requete;

public class RequeteSelectByIdTravaux implements Requete<Travaux> {
	@Override
	public String requete() {
		return "SELECT * FROM SND5405A.SAE_TRAVAUX WHERE ID_TRAVAUX = ?";
	}
	@Override
	public void parametres(PreparedStatement prSt, Travaux c) throws SQLException {
		prSt.setString(1, c.getId_Travaux());
	}
	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}
}

