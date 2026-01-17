package Modele.dao.requetes.Update;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Entreprise;
import Modele.dao.requetes.Requete;

public class RequeteUpdateEntreprise implements Requete<Entreprise> {
	@Override
	public String requete() {
		return "UPDATE SND5405A.SAE_ENTREPRISE "
				+ "SET NOM = ?, VILLE = ?, MAIL_ENTREPRISE = ?, ADRESSE = ?, "
				+ "SPECIALITE = ?, CODE_POSTALE = ? "
				+ "WHERE SIRET = ?";
	}
	@Override
	public void parametres(PreparedStatement prSt, Entreprise e) throws SQLException {
		prSt.setString(1, e.getNom());
		prSt.setString(2, e.getVille());
		prSt.setString(3, e.getMail());
		prSt.setString(4, e.getAdresse());
		prSt.setString(5, e.getSpecialite());
		prSt.setString(6, e.getCode_postale());
		prSt.setString(7, e.getSiret());
	}
	@Override public void parametres(PreparedStatement prSt, String... id) throws SQLException { }
}