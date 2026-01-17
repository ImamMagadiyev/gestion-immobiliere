package Modele.dao.requetes.Update;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import Modele.Locataire;
import Modele.dao.requetes.Requete;

public class RequeteUpdateLocataire implements Requete<Locataire> {

	@Override
	public String requete() {
		return "UPDATE SND5405A.SAE_LOCATAIRE "
				+ "SET NOM = ?, PRENOM = ?, DATE_NAISSANCE = ?, GENRE = ?, EMAIL = ?, "
				+ "ADRESSE = ?, VILLE = ?, CODE_POSTAL = ?, TELEPHONE = ? "
				+ "WHERE ID_LOCATAIRE = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, Locataire loc) throws SQLException {
		prSt.setString(1, loc.getNom());
		prSt.setString(2, loc.getPrenom());
		java.sql.Date dateNaissance = parseDateToSqlDate(loc.getDate_naissance());
		prSt.setDate(3, dateNaissance);
		prSt.setString(4, String.valueOf(loc.getGenre()));
		prSt.setString(5, loc.getEmail());
		prSt.setString(6, loc.getAdresse());
		prSt.setString(7, loc.getVille());
		prSt.setString(8, loc.getCode_postale());
		prSt.setString(9, loc.getTelephone());
		prSt.setString(10, loc.getIdLocataire());
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException { }

	private java.sql.Date parseDateToSqlDate(String dateStr) throws SQLException {
		if (dateStr == null || dateStr.isBlank()) {
			return null;
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			sdf.setLenient(false);
			java.util.Date utilDate = sdf.parse(dateStr);
			return new java.sql.Date(utilDate.getTime());
		} catch (ParseException e) {
			throw new SQLException(
				"Format de date invalide (attendu yyyy-MM-dd) : " + dateStr, e
			);
		}
	}
}
