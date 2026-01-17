package Modele.dao.requetes.SousProgramme;

import java.sql.CallableStatement;
import java.sql.SQLException;
import Modele.Diagnostic;

public class SousProgrammeInsertDiagnostic implements SousProgramme<Diagnostic> {

    @Override
    public String appelSousProgramme() {
        return "{call SND5405A.sae_insert_diagnostic(?, ?, ?, ?, ?, ?)}";

    }

    @Override
    public void parametres(CallableStatement stmt, Diagnostic diagnostic) throws SQLException {
        stmt.setString(1, diagnostic.getReference());      
        if (diagnostic.getDate_valide() != null) {
            stmt.setDate(2, java.sql.Date.valueOf(diagnostic.getDate_valide()));
        } else {
            stmt.setNull(2, java.sql.Types.DATE);
        }
        stmt.setString(3, diagnostic.getType_diagnostic());  
        stmt.setString(4, diagnostic.getNumero_Fiscale());  
        stmt.setString(5, diagnostic.getSiret());
        stmt.setInt(6, 0);
    }

    @Override public void parametresSequence(CallableStatement stmt, Diagnostic donnee) throws SQLException { }
    @Override public void parametresCalcul(CallableStatement stmt, Diagnostic donnee) throws SQLException { }
}