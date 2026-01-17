package Modele.dao.requetes.Update;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Assurance;
import Modele.dao.requetes.Requete;

/**
 * Requête permettant de mettre à jour une assurance dans la table "ASSURANCE".
 */
public class RequeteUpdateAssurance implements Requete<Assurance> {

    @Override
    public String requete() {
        return "UPDATE SND5405A.SAE_ASSURANCE SET TYPE = ?, ANNEE = ?, NUMERO = ? WHERE ID_ASSURANCE = ?";  // Requête UPDATE SQL
    }

    @Override
    public void parametres(PreparedStatement prSt, Assurance donnee) throws SQLException {
        prSt.setString(1, donnee.getType());  // Paramétrer le type de l'assurance
        prSt.setInt(2, donnee.getAnnee());    // Paramétrer l'année de l'assurance
        prSt.setString(3, donnee.getNumero());  // Paramétrer le numéro de la facture associée
        prSt.setString(4, donnee.getId_assurance());  // Paramétrer l'ID de l'assurance à mettre à jour
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        // Cette méthode n'est pas utilisée ici, car on ne paramètre pas avec un identifiant unique comme dans le cas de `SELECT`
    }
}
