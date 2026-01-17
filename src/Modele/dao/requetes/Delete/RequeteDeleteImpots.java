package Modele.dao.requetes.Delete;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Impots;
import Modele.dao.requetes.Requete;

public class RequeteDeleteImpots implements Requete<Impots> {
	@Override
	public String requete() {
		return "DELETE FROM SND5405A.SAE_IMPOTS WHERE ID_IMPOTS = ?";
	}
	@Override
	public void parametres(PreparedStatement prSt, Impots i) throws SQLException {
		prSt.setString(1, i.getIdImpots());
	}
	@Override public void parametres(PreparedStatement prSt, String... id) throws SQLException { }
}