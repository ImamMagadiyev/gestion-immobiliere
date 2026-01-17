package Modele.dao.requetes.Select;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Compteur;
import Modele.dao.requetes.Requete;

public class RequeteSelectByIdCompteur implements Requete<Compteur> {
	@Override
	public String requete() {
		return "SELECT * FROM SND5405A.SAE_COMPTEUR WHERE ID_COMPTEUR = ?";
	}
	@Override
	public void parametres(PreparedStatement prSt, Compteur c) throws SQLException {
		prSt.setString(1, c.getId_compteur());
	}
	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}
}