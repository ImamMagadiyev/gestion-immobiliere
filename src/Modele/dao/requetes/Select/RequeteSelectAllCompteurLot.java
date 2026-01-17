package Modele.dao.requetes.Select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Modele.CompteurLot;
import Modele.dao.requetes.Requete;

public class RequeteSelectAllCompteurLot implements Requete<CompteurLot> {

	@Override
	public String requete() {
		return "SELECT * FROM SND5405A.V_COMPTEUR_LOT";
	}

	@Override
	public void parametres(PreparedStatement prSt, CompteurLot donnee) throws SQLException { }

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException { }
}
