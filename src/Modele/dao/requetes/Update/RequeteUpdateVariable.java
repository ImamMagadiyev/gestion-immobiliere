package Modele.dao.requetes.Update;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Variable;
import Modele.dao.requetes.Requete;

public class RequeteUpdateVariable implements Requete<Variable> {
	@Override
	public String requete() {
		return "UPDATE SND5405A.SAE_VARIABLE "
				+ "SET SERVICE = ?, FOURNISSEUR = ?, EAU_GAZ_ELECTRICITE = ?, NUMERO = ? "
				+ "WHERE ID_VARIABLE = ?";
	}
	@Override
	public void parametres(PreparedStatement prSt, Variable v) throws SQLException {
		prSt.setString(1, v.getService());
		prSt.setString(2, v.getFournisseur());
		prSt.setString(3, v.getEau_gaz_electricite());
		prSt.setString(4, v.getNumero());
		prSt.setString(5, v.getId_Variable());
	}
	@Override public void parametres(PreparedStatement prSt, String... id) throws SQLException { }
}