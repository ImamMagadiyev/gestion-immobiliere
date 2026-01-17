package Modele.dao.requetes.Select;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Agence;
import Modele.dao.requetes.Requete;

/**
 * Requête permettant de sélectionner une Agence de la table "SAE_Agence" en fonction de son ID.
 */
public class RequeteSelectByIdAgence implements Requete<Agence> {

    @Override
    public String requete() {
        return "SELECT * FROM snd5405a.SAE_AGENCE WHERE numero = ?";  // Requête SELECT SQL pour récupérer une agence par ID (le numero de la facture)
    }

    @Override
    public void parametres(PreparedStatement prSt, Agence donnee) throws SQLException {
        prSt.setString(1, donnee.getNumero());  // Paramétrer l'ID de l'assurance dans le PreparedStatement
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        prSt.setString(1, id[0]);  // Paramétrer l'ID de l'agence (si fourni sous forme de String)
    }

}
