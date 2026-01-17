package Modele.dao.requetes.Delete;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Modele.Compteur;
import Modele.dao.requetes.Requete;

public class RequeteDeleteCompteur implements Requete<Compteur> {

	@Override
	public String requete() {
		return "DELETE FROM SND5405A.SAE_COMPTEUR WHERE ID_COMPTEUR = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, Compteur c) throws SQLException {
		prSt.setString(1, c.getId_compteur());
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		// TODO
	}
}
