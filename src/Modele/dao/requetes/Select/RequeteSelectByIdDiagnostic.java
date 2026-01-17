package Modele.dao.requetes.Select;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Diagnostic;
import Modele.dao.requetes.Requete;

public class RequeteSelectByIdDiagnostic implements Requete<Diagnostic> {
	@Override
	public String requete() {
		return "SELECT * FROM SND5405A.SAE_DIAGNOSTICS WHERE REFERENCE = ?";
	}
	@Override
	public void parametres(PreparedStatement prSt, Diagnostic d) throws SQLException {
		prSt.setString(1, d.getReference());
	}
	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}
}