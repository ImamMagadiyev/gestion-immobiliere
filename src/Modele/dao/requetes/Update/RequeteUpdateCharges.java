package Modele.dao.requetes.Update;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Charges;
import Modele.dao.requetes.Requete;

public class RequeteUpdateCharges implements Requete<Charges> {
	@Override
	public String requete() {
		return "UPDATE SND5405A.SAE_CHARGES "
				+ "SET ANNEE = ?, TAXE_ORDINAIRES = ?, TAXES_AUTRES = ?, TYPE = ? "
				+ "WHERE ID_CHARGES = ?";
	}
	@Override
	public void parametres(PreparedStatement prSt, Charges c) throws SQLException {
		prSt.setInt(1, c.getAnnee());
		prSt.setDouble(2, c.getTaxe_ordinaire());
		prSt.setDouble(3, c.getTaxes_autres());
		prSt.setString(4, c.getType());
		prSt.setString(5, c.getId_charges());
	}
	@Override public void parametres(PreparedStatement prSt, String... id) throws SQLException { }
}