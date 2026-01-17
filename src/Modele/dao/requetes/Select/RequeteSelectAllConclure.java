package Modele.dao.requetes.Select;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Conclure;
import Modele.dao.requetes.Requete;

/**
 * Requête permettant de sélectionner toutes les conclusions de la table "CONCLURE".
 */
public class RequeteSelectAllConclure implements Requete<Conclure> {

    @Override
    public String requete() {
        return "SELECT * FROM snd5405a.CONCLURE ORDER BY 1";  // Requête SELECT SQL pour récupérer toutes les conclusions
    }

    @Override
    public void parametres(PreparedStatement prSt, Conclure donnee) throws SQLException {
        // Pas de paramètres ici car on récupère toutes les conclusions
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        // Pas de paramètres ici non plus
    }
}
