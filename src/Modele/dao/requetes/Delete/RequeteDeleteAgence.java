package Modele.dao.requetes.Delete;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Agence;
import Modele.dao.requetes.Requete;

/**
 * Requête permettant de supprimer une agence de la table "AGENCE" en fonction de son numéro de facture.
 */
public class RequeteDeleteAgence implements Requete<Agence> {

    @Override
    public String requete() {
        return "DELETE FROM SND5405A.SAE_AGENCE WHERE NUMERO = ?";  // Requête de suppression SQL
    }

    @Override
    public void parametres(PreparedStatement prSt, Agence donnee) throws SQLException {
        prSt.setString(1, donnee.getNumero());  // Paramétrer le numéro de la facture
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        // Méthode vide, elle n'est pas utilisée ici, mais doit être implémentée pour respecter l'interface.
    }
}
