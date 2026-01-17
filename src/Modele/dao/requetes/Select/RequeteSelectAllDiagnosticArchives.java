package Modele.dao.requetes.Select;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Diagnostic;
import Modele.dao.requetes.Requete;

public class RequeteSelectAllDiagnosticArchives implements Requete<Diagnostic> {
    @Override
    public String requete() {
        return "SELECT * FROM SND5405A.SAE_DIAGNOSTICS WHERE ARCHIVE = 1 ORDER BY 1";
    }
    @Override public void parametres(PreparedStatement prSt, Diagnostic d) throws SQLException { }
    @Override public void parametres(PreparedStatement prSt, String... id) throws SQLException { }
}