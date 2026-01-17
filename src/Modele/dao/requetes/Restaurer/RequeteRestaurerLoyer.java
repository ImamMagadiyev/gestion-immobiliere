package Modele.dao.requetes.Restaurer;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Loyer;
import Modele.dao.requetes.Requete;

public class RequeteRestaurerLoyer implements Requete<Loyer> {

    @Override
    public String requete() {
        return "UPDATE SND5405A.SAE_LOYER SET ARCHIVE = 0 WHERE ID_LOCATAIRE = ? ";
    }

    @Override
    public void parametres(PreparedStatement ps, Loyer l) throws SQLException {
        ps.setString(1, l.getIdLocataire());
        
    }

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
	    prSt.setString(1, id[0]);
	 

	}
}

