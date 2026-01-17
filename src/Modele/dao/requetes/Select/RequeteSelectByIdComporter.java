package Modele.dao.requetes.Select;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Comporter;
import Modele.dao.requetes.Requete;


public class RequeteSelectByIdComporter implements Requete<Comporter> {
	@Override
	public String requete() {
		return "SELECT * FROM SND5405A.SAE_COMPORTER WHERE ID_BATIMENT = ? AND NUMERO = ?";
	}
	@Override
	public void parametres(PreparedStatement prSt, Comporter l) throws SQLException {
	    prSt.setString(1, l.getIdBatiment());
	    prSt.setString(2, l.getNumero());
	}
	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
	    prSt.setString(1, id[0]); // IdBatiment
	    prSt.setString(2, id[1]); // Numero
	}
}


