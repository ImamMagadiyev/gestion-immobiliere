package Modele.dao.requetes.Delete;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Conclure;
import Modele.dao.requetes.Requete;

public class RequeteDeleteConclure implements Requete<Conclure> {

	@Override
	public String requete() {
		return "DELETE FROM SND5405A.SAE_CONCLURE" +
				" WHERE ID_LOCATAIRE = ? AND ID_CONTRAT = ?";  // Requête de suppression SQL
	}

	@Override
	public void parametres(PreparedStatement prSt, Conclure donnee) throws SQLException {
		prSt.setString(1, donnee.getIdLocataire());  // Paramétrer l'ID du locataire
		prSt.setString(2, donnee.getIdContrat());  // Paramétrer l'ID du contrat de location
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		// TODO
	}
}
