package Modele.dao.requetes.Select;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Impots;
import Modele.dao.requetes.Requete;

public class RequeteSelectAllImpots implements Requete<Impots> {
    @Override
    public String requete() {
    	return "SELECT I.* FROM SND5405A.SAE_FACTURE F JOIN SND5405A.SAE_IMPOTS I ON F.NUMERO = I.NUMERO WHERE F.TYPE_FACTURE = 'Impots' ORDER BY I.NUMERO";
    }
    
    @Override public void parametres(PreparedStatement prSt, Impots d) throws SQLException { }
    @Override public void parametres(PreparedStatement prSt, String... id) throws SQLException { }
}