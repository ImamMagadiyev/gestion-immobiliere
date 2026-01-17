package Modele.dao.requetes.Update;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Agence;
import Modele.dao.requetes.Requete;

/**
 * Requête permettant de mettre à jour une Agence dans la table "Agence".
 */
public class RequeteUpdateAgence implements Requete<Agence> {

    @Override
    public String requete() {
        return "UPDATE SND5405A.SAE_AGENCE SET NUMERO = ? WHERE NUMERO = ?";  // Requête UPDATE SQL
    }

    @Override
    public void parametres(PreparedStatement prSt, Agence donnee) throws SQLException {
        prSt.setString(3, donnee.getNumero());  // Paramétrer le numéro de la facture associée
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        // Cette méthode n'est pas utilisée ici, car on ne paramètre pas avec un identifiant unique comme dans le cas de `SELECT`
    }
}
