package Modele.dao.requetes.Update;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Impots;
import Modele.dao.requetes.Requete;

public class RequeteUpdateImpots implements Requete<Impots> {
	@Override
	public String requete() {
		return "UPDATE SND5405A.SAE_IMPOTS "
				+ "SET RECUPERABLE_IMPOT = ?, ANNEE = ?, TYPE = ?, NUMERO = ? "
				+ "WHERE ID_IMPOTS = ?";
	}
	@Override
	public void parametres(PreparedStatement prSt, Impots i) throws SQLException {
		prSt.setDouble(1, i.getRecuperable_impot());
		prSt.setInt(2, i.getAnnee());
		prSt.setString(3, i.getType());
		prSt.setString(4, i.getNumero());
		prSt.setString(5, i.getIdImpots());
	}
	@Override public void parametres(PreparedStatement prSt, String... id) throws SQLException { }
}