package Modele.dao.requetes.Delete;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.ReleveCompteur;
import Modele.dao.requetes.Requete;

public class RequeteDeleteReleveCompteur implements Requete<ReleveCompteur> {
	@Override
	public String requete() {
		return "DELETE FROM SND5405A.SAE_RELEVE_COMPTEUR WHERE ID_COMPTEUR = ? AND DATE_RELEVE = ?";
	}
	@Override
	public void parametres(PreparedStatement prSt, ReleveCompteur r) throws SQLException {
		prSt.setString(1, r.getId_compteur());
		prSt.setDate(2, Date.valueOf(r.getDate_releve()));
	}
	@Override public void parametres(PreparedStatement prSt, String... id) throws SQLException { }
}