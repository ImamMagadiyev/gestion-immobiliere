package Modele.dao.requetes.SousProgramme;

import java.sql.CallableStatement;
import java.sql.SQLException;
import Modele.Payer;

public class SousProgrammeInsertPayer implements SousProgramme<Payer> {

	@Override
	public String appelSousProgramme() {
		return "{call SND5405A.Insert_Payer(?, ?)}";
	}

	@Override
	public void parametres(CallableStatement stmt, Payer p) throws SQLException {
		stmt.setString(1, p.getId_charges());
		stmt.setString(2, p.getId_locataire());
	}

	@Override
	public void parametresSequence(CallableStatement stmt, Payer donnees) throws SQLException { }

	@Override
	public void parametresCalcul(CallableStatement stmt, Payer donnees) throws SQLException { }
}
