package Modele.dao.requetes.Delete;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Modele.Caution;
import Modele.dao.requetes.Requete;

public class RequeteDeleteCaution implements Requete<Caution> {

	@Override
	public String requete() {
		return "DELETE FROM SND5405A.SAE_CAUTION WHERE ID_CAUTION = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, Caution c) throws SQLException {
		prSt.setString(1, c.getId_caution());
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		// TODO
	}
}
