package Modele.dao.requetes.Select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Modele.Conclure;
import Modele.dao.requetes.Requete;

public class RequeteSelectByIdConclure implements Requete<Conclure> {

	@Override
	public String requete() {
		return "SELECT * FROM SND5405A.SAE_CONCLURE "
				+ "WHERE ID_LOCATAIRE = ? AND ID_CONTRAT = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, Conclure c) throws SQLException {
		prSt.setString(1, c.getIdLocataire());
		prSt.setString(2, c.getIdContrat());
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]); // ID_LOCATAIRE
		prSt.setString(2, id[1]); // ID_CONTRAT
	}
}
