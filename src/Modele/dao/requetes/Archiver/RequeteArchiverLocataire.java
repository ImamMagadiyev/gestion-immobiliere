package Modele.dao.requetes.Archiver;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Locataire;
import Modele.dao.requetes.Requete;

public class RequeteArchiverLocataire implements Requete<Locataire> {

    @Override
    public String requete() {
        return "UPDATE SND5405A.SAE_LOCATAIRE SET ARCHIVE = 1 WHERE ID_LOCATAIRE = ?";
    }

    @Override
    public void parametres(PreparedStatement ps, Locataire locataire) throws SQLException {
        ps.setString(1, locataire.getIdLocataire());
    }

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
	    prSt.setString(1, id[0]);

	}
}

