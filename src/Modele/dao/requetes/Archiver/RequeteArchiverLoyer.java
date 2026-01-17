package Modele.dao.requetes.Archiver;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Modele.Loyer;
import Modele.dao.requetes.Requete;

public class RequeteArchiverLoyer implements Requete<Loyer> {

    @Override
    public String requete() {
        return "UPDATE SND5405A.SAE_LOYER SET ARCHIVE = 1 WHERE ID_LOCATAIRE = ? AND NUMERO_FISCALE = ? AND DATE_PAIEMENT = TO_DATE(?, 'YYYY-MM-DD')";
    }

    @Override
    public void parametres(PreparedStatement ps, Loyer l) throws SQLException {    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {    }

    public void parametres(PreparedStatement prSt, String idLocataire, String numeroFiscale, Date datePaiement) throws SQLException {
        System.out.println("=== DEBUG Archiver Loyer (String+Date) ===");
        System.out.println("1. ID_LOCATAIRE: '" + (idLocataire != null ? idLocataire : "NULL") + "'");
        System.out.println("2. NUMERO_FISCALE: '" + (numeroFiscale != null ? numeroFiscale : "NULL") + "'");
        System.out.println("3. DATE_PAIEMENT: " + (datePaiement != null ? datePaiement.toString() : "NULL"));
    	
    	prSt.setString(1, idLocataire);
        prSt.setString(2, numeroFiscale);
        prSt.setDate(3, datePaiement);
    }
}
