package Modele.dao.requetes.Delete;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Entreprise;
import Modele.dao.requetes.Requete;

public class RequeteDeleteEntreprise implements Requete<Entreprise> {
	@Override
	public String requete() {
		return "DELETE FROM SND5405A.SAE_ENTREPRISE WHERE SIRET = ?";
	}
	@Override
	public void parametres(PreparedStatement prSt, Entreprise e) throws SQLException {
		prSt.setString(1, e.getSiret());
	}
	@Override public void parametres(PreparedStatement prSt, String... id) throws SQLException { }
}