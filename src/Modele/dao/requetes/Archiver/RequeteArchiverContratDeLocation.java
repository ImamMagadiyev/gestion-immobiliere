package Modele.dao.requetes.Archiver;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.ContratDeLocation;
import Modele.dao.requetes.Requete;

public class RequeteArchiverContratDeLocation implements Requete<ContratDeLocation> {

    @Override
    public String requete() {
        return "UPDATE SND5405A.SAE_CONTRAT_DE_LOCATION SET ARCHIVE = 1 WHERE ID_CONTRAT = ?";
    }

    @Override
    public void parametres(PreparedStatement ps, ContratDeLocation contrat) throws SQLException {   }

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
	    prSt.setString(1, id[0]);

	}
}

