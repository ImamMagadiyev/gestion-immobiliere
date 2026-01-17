package Modele.dao.requetes.Select;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.ReleveCompteur;
import Modele.dao.requetes.Requete;

public class RequeteSelectAllReleveCompteur implements Requete<ReleveCompteur> {
	@Override
	public String requete() {
		return "SELECT * FROM SND5405A.SAE_RELEVE_COMPTEUR ORDER BY 1";
	}
	@Override public void parametres(PreparedStatement prSt, ReleveCompteur d) throws SQLException { }
	@Override public void parametres(PreparedStatement prSt, String... id) throws SQLException { }
}