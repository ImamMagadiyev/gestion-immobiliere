package Modele.dao.requetes.Select;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Batiment;
import Modele.dao.requetes.Requete;

/**
 * Requête permettant de sélectionner tous les bâtiments de la table "BATIMENT".
 */
public class RequeteSelectAllBatiment implements Requete<Batiment> {

    @Override
    public String requete() {
        return "SELECT * FROM snd5405a.SAE_BATIMENT ORDER BY 1";  // Requête SELECT SQL pour récupérer tous les bâtiments
    }

    @Override
    public void parametres(PreparedStatement prSt, Batiment donnee) throws SQLException {
        // Pas de paramètres ici car on récupère tous les bâtiments
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        // Pas de paramètres ici non plus
    }
}
