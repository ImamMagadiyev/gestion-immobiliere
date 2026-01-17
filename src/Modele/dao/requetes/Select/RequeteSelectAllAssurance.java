package Modele.dao.requetes.Select;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Assurance;
import Modele.dao.requetes.Requete;

/**
 * Requête permettant de sélectionner toutes les assurances de la table "ASSURANCE".
 */
public class RequeteSelectAllAssurance implements Requete<Assurance> {

    @Override
    public String requete() {
        return "SELECT A.* FROM SND5405A.SAE_FACTURE F JOIN SND5405A.SAE_ASSURANCE A ON F.NUMERO = A.NUMERO WHERE F.TYPE_FACTURE = 'Assurances' ORDER BY A.NUMERO";  // Requête SELECT SQL avec JOIN pour récupérer les factures d'assurance
    }

    @Override
    public void parametres(PreparedStatement prSt, Assurance donnee) throws SQLException {
        // Pas de paramètres ici car on récupère toutes les assurances
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        // Pas de paramètres ici non plus
    }

}
