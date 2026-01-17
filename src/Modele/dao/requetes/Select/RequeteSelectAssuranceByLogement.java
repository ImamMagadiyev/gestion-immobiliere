package Modele.dao.requetes.Select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Modele.Assurance;
import Modele.dao.requetes.Requete;

/**
 * Requête permettant de sélectionner toutes les assurances associées à un bien (logement).
 */
public class RequeteSelectAssuranceByLogement implements Requete<Assurance> {

    @Override
    public String requete() {
        return "SELECT * FROM snd5405a.sae_ASSURANCE WHERE NUMERO_FISCALE = ?  ORDER BY 1";  // Requête SQL pour récupérer les assurances par ID de bien
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        prSt.setString(1, id[0]);  // Paramétrer l'ID du bien (logement) à récupérer
    }

    @Override
    public void parametres(PreparedStatement prSt, Assurance donnee) throws SQLException {
        // Cette méthode n'est pas utilisée ici car on passe un ID de bien spécifique
    }
}
