package Modele.dao.requetes.Delete;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Comporter;
import Modele.dao.requetes.Requete;

public class RequeteDeleteComporter implements Requete<Comporter> {

    @Override
    public String requete() {
        return "DELETE FROM COMPORTER WHERE ID_BATIMENT = ? AND NUMERO = ?";  // Requête de suppression SQL
    }

    @Override
    public void parametres(PreparedStatement prSt, Comporter donnee) throws SQLException {
        prSt.setString(1, donnee.getIdBatiment());  // Paramétrer l'ID du batiment
        prSt.setString(2, donnee.getNumero());  // Paramétrer le numéro de la facture
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        // TODO
    }
}
