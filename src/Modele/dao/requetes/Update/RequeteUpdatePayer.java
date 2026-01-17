package Modele.dao.requetes.Update;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Payer;
import Modele.dao.requetes.Requete;

public class RequeteUpdatePayer implements Requete<Payer> {

	@Override
	public String requete() {
		// Donc en vrai on n'as pas besoin de ce fichier
		// Pas de champs à modifier, on garde une requête neutre
		return "UPDATE SND5405A.SAE_PAIEMENT "
		+ "SET ID_CHARGES = ID_CHARGES, ID_LOCATAIRE = ID_LOCATAIRE "
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
