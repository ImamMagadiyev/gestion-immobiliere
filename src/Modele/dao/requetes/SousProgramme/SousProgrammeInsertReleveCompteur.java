package Modele.dao.requetes.SousProgramme;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import Modele.ReleveCompteur;

public class SousProgrammeInsertReleveCompteur implements SousProgramme<ReleveCompteur> {

	@Override
	public String appelSousProgramme() {
		return "{call SND5405A.SAE_INSERT_RELEVE_COMPTEUR(?, ?, ?)}";
	}

	@Override
	public void parametres(CallableStatement stmt, ReleveCompteur r) throws SQLException {
		String idCompteur = r.getId_compteur();
		LocalDate dateReleve = r.getDate_releve();
		int indice = r.getIndice();
		
		try {
			Date sqlDate = (dateReleve == null) ? null : Date.valueOf(dateReleve);
			stmt.setString(1, idCompteur);
			stmt.setDate(2, sqlDate);
			stmt.setInt(3, indice);
		} catch (Exception e) {
			System.err.println("[ERROR PARAMS] Erreur conversion: " + e.getMessage());
			e.printStackTrace();
			throw new SQLException("Erreur conversion: " + e.getMessage(), e);
		}
	}

	@Override public void parametresSequence(CallableStatement stmt, ReleveCompteur d) throws SQLException { }
	@Override public void parametresCalcul(CallableStatement stmt, ReleveCompteur d) throws SQLException { }
}