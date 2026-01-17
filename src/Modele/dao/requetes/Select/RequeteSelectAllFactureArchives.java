package Modele.dao.requetes.Select;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Facture;
import Modele.dao.requetes.Requete;

public class RequeteSelectAllFactureArchives implements Requete<Facture> {
    @Override
    public String requete() {
        return "SELECT * FROM SND5405A.SAE_FACTURE WHERE ARCHIVE = 1 ORDER BY 1";
    }
    
    @Override public void parametres(PreparedStatement prSt, Facture d) throws SQLException { }
    @Override public void parametres(PreparedStatement prSt, String... id) throws SQLException { }
}