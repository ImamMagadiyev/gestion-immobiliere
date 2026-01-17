package Modele.dao.requetes.Select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Modele.CompteurBien;
import Modele.dao.requetes.Requete;

public class RequeteSelectAllCompteurBien implements Requete<CompteurBien> {

	@Override
	public String requete() {
		return "SELECT * FROM SND5405A.V_COMPTEUR_BIEN";
	}

	@Override
	public void parametres(PreparedStatement prSt, CompteurBien donnee) throws SQLException { }

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException { }
}
