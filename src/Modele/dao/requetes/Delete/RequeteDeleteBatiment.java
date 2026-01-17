package Modele.dao.requetes.Delete;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Batiment;
import Modele.dao.requetes.Requete;

public class RequeteDeleteBatiment implements Requete<Batiment> {

    @Override
    public String requete() {
        return "DELETE FROM SND5405A.SAE_BATIMENT WHERE ID_BATIMENT = ?";  // Requête de suppression SQL
    }

    @Override
    public void parametres(PreparedStatement prSt, Batiment donnee) throws SQLException {
        prSt.setString(1, donnee.getIdBatiment());  // Paramétrer l'ID du Batiment à supprimer
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        // Méthode vide, elle n'est pas utilisée dans ce cas, mais doit être implémentée pour respecter l'interface.
    }
}
