package Modele.dao.requetes.Select;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Loyer;
import Modele.dao.requetes.Requete;

public class RequeteSelectAllLoyer implements Requete<Loyer> {

	@Override
	public String requete() {
		return "SELECT " +
				"ID_LOCATAIRE, " +
				"NUMERO_FISCALE, " +
				"DATE_PAIEMENT, " +
				"MONTANT_PROVISION, " +
				"MONTANT_LOYER, " +
				"MOIS, " +
				"MONTANT_REGULARISATION, " +
				"ARCHIVE " +
				"FROM SND5405A.SAE_LOYER " +
				"WHERE ARCHIVE = 0 " +
				"ORDER BY 3";
	}

	@Override
	public void parametres(PreparedStatement prSt, Loyer d) throws SQLException { }

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException { }
}
