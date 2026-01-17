package Modele.dao.requetes.Select;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Assurance;
import Modele.dao.requetes.Requete;

/**
 * Requête permettant de sélectionner une assurance de la table "ASSURANCE" en fonction de son ID.
 */
public class RequeteSelectByIdAssurance implements Requete<Assurance> {

    @Override
    public String requete() {
        return "SELECT * FROM snd5405a.SAE_ASSURANCE WHERE ID_ASSURANCE = ?";  // Requête SELECT SQL pour récupérer une assurance par ID
    }

    @Override
    public void parametres(PreparedStatement prSt, Assurance donnee) throws SQLException {
        prSt.setString(1, donnee.getId_assurance());  // Paramétrer l'ID de l'assurance dans le PreparedStatement
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        prSt.setString(1, id[0]);  // Paramétrer l'ID de l'assurance (si fourni sous forme de String)
    }
}
