package Modele.dao.requetes.Update;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Diagnostic;
import Modele.dao.requetes.Requete;

public class RequeteUpdateDiagnostic implements Requete<Diagnostic> {
    @Override
    public String requete() {
        return "UPDATE SND5405A.SAE_DIAGNOSTICS SET DATE_VALIDITE = ?, TYPE_DIAGNOSTIC = ?, NUMERO_FISCALE = ?, SIRET = ? WHERE REFERENCE = ?";
    }
    @Override
    public void parametres(PreparedStatement prSt, Diagnostic d) throws SQLException {
        prSt.setDate(1, Date.valueOf(d.getDate_valide()));    
        prSt.setString(2, d.getType_diagnostic());           
        prSt.setString(3, d.getNumero_Fiscale());            
        prSt.setString(4, d.getSiret());                    
        prSt.setString(5, d.getReference());                
    }

    @Override public void parametres(PreparedStatement prSt, String... id) throws SQLException { }
}