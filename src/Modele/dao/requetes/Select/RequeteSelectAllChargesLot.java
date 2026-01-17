package Modele.dao.requetes.Select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Modele.ChargesLot;
import Modele.dao.requetes.Requete;

public class RequeteSelectAllChargesLot implements Requete<ChargesLot> {

	@Override
	public String requete() {
		return "SELECT * FROM SND5405A.V_CHARGES_LOT";
	}

	@Override
	public void parametres(PreparedStatement prSt, ChargesLot donnee) throws SQLException { }

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException { }
}
