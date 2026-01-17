package Modele.dao.requetes.Restaurer;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Diagnostic;
import Modele.dao.requetes.Requete;

public class RequeteRestaurerDiagnostic implements Requete<Diagnostic> {

    @Override
    public String requete() {
        return "UPDATE SND5405A.SAE_DIAGNOSTICS SET ARCHIVE = 0 WHERE REFERENCE = ?";
    }

    @Override
    public void parametres(PreparedStatement ps, Diagnostic d) throws SQLException {
        ps.setString(1, d.getReference());
    }

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
	    prSt.setString(1, id[0]);

	}
}

