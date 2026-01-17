package Modele.dao.requetes.Delete;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Diagnostic;
import Modele.dao.requetes.Requete;

public class RequeteDeleteDiagnostic implements Requete<Diagnostic> {
	@Override
	public String requete() {
		return "DELETE FROM SND5405A.SAE_DIAGNOSTICS WHERE REFERENCE = ?";
	}
	@Override
	public void parametres(PreparedStatement prSt, Diagnostic d) throws SQLException {
		prSt.setString(1, d.getReference());
	}
	@Override public void parametres(PreparedStatement prSt, String... id) throws SQLException { }
}