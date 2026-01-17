package Modele.dao.requetes.Select;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Payer;
import Modele.dao.requetes.Requete;

public class RequeteSelectByIdPayer implements Requete<Payer> {

	@Override
	public String requete() {
		return "SELECT * FROM SND5405A.SAE_PAIEMENT "
				+ "WHERE ID_CHARGES = ? AND ID_LOCATAIRE = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, Payer p) throws SQLException {
		prSt.setString(1, p.getId_charges());
		prSt.setString(2, p.getId_locataire());
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]); // ID_CHARGES
		prSt.setString(2, id[1]); // ID_LOCATAIRE
	}
}
