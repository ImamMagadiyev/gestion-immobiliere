package Modele.dao.requetes.Update;

import Modele.ContratDeLocation;
import Modele.dao.requetes.Requete;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class RequeteUpdateContratDeLocation
        implements Requete<ContratDeLocation> {

    @Override
    public String requete() {
        return "UPDATE SND5405A.SAE_CONTRAT_DE_LOCATION SET "
             + "DATE_DEBUT = ?, "
             + "TRIMESTRE = ?, "
             + "DATE_SORTIE = ?, "
             + "LOYER_APAYER = ?, "
             + "IRL = ?, "
             + "PROVISIONS_CHARGES = ?, "
             + "SOLDE_TOUT_COMPTE_EFFECTUE = ?, "
             + "DUREE = ?, "
             + "NUMERO_FISCALE = ?, "
             + "ID_LOCATAIRE = ?, "
             + "ARCHIVE = ? "
             + "WHERE ID_CONTRAT = ?";
    }

    @Override
    public void parametres(PreparedStatement prSt,
                           ContratDeLocation c) throws SQLException {

        // ...existing code...
        java.sql.Date dateDebut = parseDateToSqlDate(c.getDate_debut());
        prSt.setDate(1, dateDebut);
        int trimestreNum = convertTrimestreToNumber(c.getTrimestre());
        prSt.setInt(2, trimestreNum);
        java.sql.Date dateSortie = parseDateToSqlDate(c.getDate_sortie());
        if (dateSortie != null) {
            prSt.setDate(3, dateSortie);
        } else {
            prSt.setNull(3, java.sql.Types.DATE);
        }
        prSt.setDouble(4, c.getLoyer_aPayer());
        prSt.setDouble(5, c.getIRL());
        prSt.setDouble(6, c.getProvisions_charges());
        String soldeChar = c.isSolde_tout_compte_effectue() ? "1" : "0";
        prSt.setString(7, soldeChar);
        prSt.setInt(8, c.getDuree());
        prSt.setString(9, c.getNumero_fiscale());
        prSt.setString(10, c.getId_locataire());
        prSt.setInt(11, c.isArchive() ? 1 : 0);
        prSt.setString(12, c.getIdContrat());
    }

    @Override
    public void parametres(PreparedStatement prSt,
                           String... id) throws SQLException { }

    private java.sql.Date parseDateToSqlDate(String dateStr) throws SQLException {
        if (dateStr == null || dateStr.isBlank()) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            java.util.Date utilDate = sdf.parse(dateStr);
            return new java.sql.Date(utilDate.getTime());
        } catch (ParseException e) {
            throw new SQLException(
                "Format de date invalide (attendu yyyy-MM-dd) : " + dateStr, e
            );
        }
    }

    private int convertTrimestreToNumber(String trimestre) {
        if (trimestre == null || trimestre.isEmpty()) return 1;
        if (trimestre.equalsIgnoreCase("T1")) return 1;
        if (trimestre.equalsIgnoreCase("T2")) return 2;
        if (trimestre.equalsIgnoreCase("T3")) return 3;
        if (trimestre.equalsIgnoreCase("T4")) return 4;
        return 1;
    }
}
