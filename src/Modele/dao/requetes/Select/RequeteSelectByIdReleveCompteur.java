package Modele.dao.requetes.Select;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.ReleveCompteur;
import Modele.dao.requetes.Requete;

public class RequeteSelectByIdReleveCompteur implements Requete<ReleveCompteur> {
	@Override
	public String requete() {
		return "SELECT * FROM SND5405A.SAE_RELEVE_COMPTEUR WHERE ARCHIVE = 0 AND ID_COMPTEUR = ? AND DATE_RELEVE = ?";
	}
	@Override
	public void parametres(PreparedStatement prSt, ReleveCompteur r) throws SQLException {
		prSt.setString(1, r.getId_compteur());
		prSt.setDate(2, Date.valueOf(r.getDate_releve()));
	}
	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]); // ID_COMPTEUR
		prSt.setString(2, id[1]); // DATE_RELEVE
	}
}