package Modele.dao.requetes.Restaurer;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Caution;
import Modele.dao.requetes.Requete;

public class RequeteRestaurerCaution implements Requete<Caution> {

    @Override
    public String requete() {
        return "UPDATE SND5405A.SAE_CAUTION SET ARCHIVE = 0 WHERE ID_CAUTION = ?";
    }

    @Override
    public void parametres(PreparedStatement ps, Caution c) throws SQLException {
        ps.setString(1, c.getId_caution());
    }

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
	    prSt.setString(1, id[0]);

	}
}

