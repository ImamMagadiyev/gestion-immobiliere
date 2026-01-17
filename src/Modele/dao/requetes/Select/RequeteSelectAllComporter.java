package Modele.dao.requetes.Select;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Comporter;
import Modele.dao.requetes.Requete;

/**
 * Requête permettant de sélectionner toutes les relations entre les bâtiments et les factures de la table "COMPORTER".
 */
public class RequeteSelectAllComporter implements Requete<Comporter> {

    @Override
    public String requete() {
        return "SELECT * FROM snd5405a.COMPORTER ORDER BY 1";  // Requête SELECT SQL pour récupérer toutes les relations
    }

    @Override
    public void parametres(PreparedStatement prSt, Comporter donnee) throws SQLException {
        // Pas de paramètres ici car on récupère toutes les relations
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        // Pas de paramètres ici non plus
    }
}
