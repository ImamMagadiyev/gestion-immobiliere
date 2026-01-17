package Modele.dao.requetes.Archiver;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Compteur;
import Modele.dao.requetes.Requete;

public class RequeteArchiverCompteur implements Requete<Compteur> {

    @Override
    public String requete() {
        return "UPDATE SND5405A.SAE_COMPTEUR SET ARCHIVE = 1 WHERE ID_COMPTEUR = ?";
    }

    @Override
    public void parametres(PreparedStatement ps, Compteur c) throws SQLException {
        ps.setString(1, c.getId_compteur());
    }

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
	    prSt.setString(1, id[0]);

	}
}

