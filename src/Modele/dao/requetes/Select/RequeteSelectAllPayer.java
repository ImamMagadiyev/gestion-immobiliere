package Modele.dao.requetes.Select;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Payer;
import Modele.dao.requetes.Requete;

public class RequeteSelectAllPayer implements Requete<Payer> {

	@Override
	public String requete() {
		return "SELECT * FROM SND5405A.SAE_PAYER ORDER BY 1";
	}

	@Override
	public void parametres(PreparedStatement prSt, Payer donnee) throws SQLException { }

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException { }
}
