package Modele.dao.requetes.Update;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Modele.Travaux;
import Modele.dao.requetes.Requete;

public class RequeteUpdateTravaux implements Requete<Travaux> {
	@Override
	public String requete() {
		return "UPDATE SND5405A.SAE_TRAVAUX "
				+ "SET raison = ?"
				+ "WHERE Id_travaux = ?";
	}
	@Override
	public void parametres(PreparedStatement prSt, Travaux c) throws SQLException {
		prSt.setString(1, c.getRaison());
		prSt.setString(2, c.getId_Travaux());
	}
	@Override public void parametres(PreparedStatement prSt, String... id) throws SQLException { }
}

