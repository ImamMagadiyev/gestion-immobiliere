package Modele.dao.requetes.Delete;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Assurance;
import Modele.dao.requetes.Requete;


public class RequeteDeleteAssurance implements Requete<Assurance> {

    @Override
    public String requete() {
        return "DELETE FROM SND5405A.SAE_ASSURANCE WHERE ID_ASSURANCE = ?";  // Requête de suppression SQL
    }

    @Override
    public void parametres(PreparedStatement prSt, Assurance donnee) throws SQLException {
        prSt.setString(1, donnee.getId_assurance());  // Paramétrer l'ID de l'assurance
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
    	
    }
}
