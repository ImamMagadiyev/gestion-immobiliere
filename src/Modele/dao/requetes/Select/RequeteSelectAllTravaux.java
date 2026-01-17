package Modele.dao.requetes.Select;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Travaux;
import Modele.dao.requetes.Requete;

public class RequeteSelectAllTravaux implements Requete<Travaux> {
	
	@Override
    public String requete() {
    	return "SELECT T.* FROM SND5405A.SAE_FACTURE F JOIN SND5405A.SAE_TRAVAUX T ON F.NUMERO = T.NUMERO WHERE F.TYPE_FACTURE = 'Travaux' ORDER BY T.NUMERO";
    }
    
    @Override public void parametres(PreparedStatement prSt, Travaux d) throws SQLException { }
    
    @Override public void parametres(PreparedStatement prSt, String... id) throws SQLException { }
}

