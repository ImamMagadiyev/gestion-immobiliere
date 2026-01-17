package Modele.dao.requetes.Update;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Comporter;
import Modele.dao.requetes.Requete;

public class RequeteUpdateComporter implements Requete<Comporter> {
	@Override
    public String requete() {
        return "UPDATE SND5405A.SAE_Comporter SET id_Batiment = ?, numero = ?";  // Requête UPDATE SQL
    }

    @Override
    public void parametres(PreparedStatement prSt, Comporter donnee) throws SQLException {
    	
    	prSt.setString(1, donnee.getIdBatiment());
        prSt.setString(2, donnee.getNumero()); 

    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        // Cette méthode n'est pas utilisée ici, car on ne paramètre pas avec un identifiant unique comme dans le cas de `SELECT`
    }
}
