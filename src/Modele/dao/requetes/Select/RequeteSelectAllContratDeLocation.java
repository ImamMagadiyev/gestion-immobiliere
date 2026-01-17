package Modele.dao.requetes.Select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Modele.ContratDeLocation;
import Modele.dao.requetes.Requete;

public class RequeteSelectAllContratDeLocation implements Requete<ContratDeLocation> {

    @Override
    public String requete() {
        return "SELECT ID_CONTRAT, " +
               "TO_CHAR(DATE_DEBUT, 'YYYY-MM-DD') AS DATE_DEBUT, " +
               "TRIMESTRE, " +
               "TO_CHAR(DATE_SORTIE, 'YYYY-MM-DD') AS DATE_SORTIE, " +
               "LOYER_APAYER, IRL, PROVISIONS_CHARGES, SOLDE_TOUT_COMPTE_EFFECTUE, " +
               "DUREE, NUMERO_FISCALE, ID_LOCATAIRE, ARCHIVE " +
               "FROM SND5405A.SAE_CONTRAT_DE_LOCATION " +
               "WHERE ARCHIVE = 0 " +
               "ORDER BY ID_CONTRAT";
    }

    @Override
    public void parametres(PreparedStatement prSt, ContratDeLocation donnee) throws SQLException { }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException { }
}
