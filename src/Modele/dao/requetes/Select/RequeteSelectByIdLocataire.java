package Modele.dao.requetes.Select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Modele.Locataire;
import Modele.dao.requetes.Requete;

public class RequeteSelectByIdLocataire implements Requete<Locataire> {

    @Override
    public String requete() {
    	return "SELECT " +
                "ID_LOCATAIRE, " +
                "NOM, " +
                "PRENOM, " +
                "TO_CHAR(DATE_NAISSANCE, 'YYYY-MM-DD') AS DATE_NAISSANCE, " +
                "GENRE, " +
                "EMAIL, " +
                "TELEPHONE, " +
                "ADRESSE, " +
                "VILLE, " +
                "CODE_POSTAL, " +
                "ARCHIVE " +
               "FROM SND5405A.SAE_LOCATAIRE " +
               "WHERE ID_LOCATAIRE = ?";
    } 

    @Override
    public void parametres(PreparedStatement prSt, Locataire loc) throws SQLException {
        prSt.setString(1, loc.getIdLocataire());
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        prSt.setString(1, id[0]);
    }
}
