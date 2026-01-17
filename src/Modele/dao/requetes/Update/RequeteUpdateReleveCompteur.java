package Modele.dao.requetes.Update;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.ReleveCompteur;
import Modele.dao.requetes.Requete;

public class RequeteUpdateReleveCompteur implements Requete<ReleveCompteur> {
	@Override
	public String requete() {
		return "UPDATE SND5405A.SAE_RELEVE_COMPTEUR "
				+ "SET INDICE = ? "
				+ "WHERE ID_COMPTEUR = ? AND DATE_RELEVE = ?";
	}
	@Override
	public void parametres(PreparedStatement prSt, ReleveCompteur r) throws SQLException {
		prSt.setInt(1, r.getIndice());
		prSt.setString(2, r.getId_compteur());
		prSt.setDate(2, Date.valueOf(r.getDate_releve()));
	}
	@Override public void parametres(PreparedStatement prSt, String... id) throws SQLException { }
}