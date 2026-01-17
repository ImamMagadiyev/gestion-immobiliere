package Modele.dao.requetes.SousProgramme;

import java.sql.CallableStatement;
import java.sql.SQLException;
import Modele.Impots;

public class SousProgrammeInsertImpots implements SousProgramme<Impots> {

	@Override
	public String appelSousProgramme() {
		return "{call SND5405A.sae_Insert_Impots(?, ?, ?, ?, ?)}";

	}

	@Override
	public void parametres(CallableStatement stmt, Impots i) throws SQLException {
		stmt.setString(1, i.getIdImpots());
		stmt.setDouble(2, i.getRecuperable_impot());
		stmt.setInt(3, i.getAnnee());
		stmt.setString(4, i.getType());
		stmt.setString(5, i.getNumero());
	}

	@Override public void parametresSequence(CallableStatement stmt, Impots d) throws SQLException { }
	@Override public void parametresCalcul(CallableStatement stmt, Impots d) throws SQLException { }
}