package Modele.dao.requetes.Delete;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Payer;
import Modele.dao.requetes.Requete;

public class RequeteDeletePayer implements Requete<Payer> {

	@Override
	public String requete() {
		//Meme chose pour ce, je ne croit pa qu'on aurait besoin de supprimer une paiement qui a deja ete fait
		return "DELETE FROM SND5405A.SAE_PAYER "
				+ "WHERE ID_CHARGES = ? AND ID_LOCATAIRE = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, Payer p) throws SQLException {
		prSt.setString(1, p.getId_charges());
		prSt.setString(2, p.getId_locataire());
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException { }
}
