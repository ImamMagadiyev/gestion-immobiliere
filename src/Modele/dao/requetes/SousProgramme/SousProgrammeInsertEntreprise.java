package Modele.dao.requetes.SousProgramme;

import java.sql.CallableStatement;
import java.sql.SQLException;
import Modele.Entreprise;

public class SousProgrammeInsertEntreprise implements SousProgramme<Entreprise> {

	@Override
	public String appelSousProgramme() {
		return "{call SND5405A.sae_insert_entreprise(?, ?, ?, ?, ?, ?, ?)}";

	}

	@Override
	public void parametres(CallableStatement stmt, Entreprise e) throws SQLException {
		stmt.setString(1, e.getSiret());
		stmt.setString(2, e.getNom());
		stmt.setString(3, e.getVille());
		stmt.setString(4, e.getMail());
		stmt.setString(5, e.getAdresse());
		stmt.setString(6, e.getSpecialite());
		stmt.setString(7, e.getCode_postale());
	}

	@Override public void parametresSequence(CallableStatement stmt, Entreprise d) throws SQLException { }
	@Override public void parametresCalcul(CallableStatement stmt, Entreprise d) throws SQLException { }
}