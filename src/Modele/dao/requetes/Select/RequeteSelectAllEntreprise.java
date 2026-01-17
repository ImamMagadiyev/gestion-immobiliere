package Modele.dao.requetes.Select;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Entreprise;
import Modele.dao.requetes.Requete;

public class RequeteSelectAllEntreprise implements Requete<Entreprise> {
	@Override
	public String requete() {
		return "SELECT * FROM SND5405A.SAE_ENTREPRISE ORDER BY 1";
	}
	@Override public void parametres(PreparedStatement prSt, Entreprise d) throws SQLException { }
	@Override public void parametres(PreparedStatement prSt, String... id) throws SQLException { }
}