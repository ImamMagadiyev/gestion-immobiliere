package Modele.dao.requetes.Select;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Agence;
import Modele.dao.requetes.Requete;

/**
 * Requête permettant de sélectionner toutes les agences de la table "ASSURANCE".
 */
public class RequeteSelectAllAgence implements Requete<Agence> {

    @Override
    public String requete() {
        return "SELECT * FROM snd5405a.SAE_Agence order by 1";  // Requête SELECT SQL pour récupérer toutes les agences
    }

    @Override
    public void parametres(PreparedStatement prSt, Agence donnee) throws SQLException {
        // Pas de paramètres ici car on récupère toutes les agences
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        // Pas de paramètres ici non plus
    }

}
