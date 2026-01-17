package Modele.dao.requetes.Select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Modele.Locataire;
import Modele.dao.requetes.Requete;

public class RequeteSelectAllLocataire implements Requete<Locataire> {

    @Override
    public String requete() {
        // Format DATE_NAISSANCE to only show the date
        return "SELECT ID_LOCATAIRE, NOM, PRENOM, TO_CHAR(DATE_NAISSANCE, 'YYYY-MM-DD') AS DATE_NAISSANCE, " +
               "GENRE, EMAIL, ADRESSE, VILLE, CODE_POSTAL, TELEPHONE, ARCHIVE " +
               "FROM SND5405A.SAE_LOCATAIRE WHERE ARCHIVE = 0 ORDER BY 1";
    }

    @Override
    public void parametres(PreparedStatement prSt, Locataire donnee) throws SQLException { }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException { }
}
