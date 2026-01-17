package Modele.dao.requetes.SousProgramme;

import Modele.ContratDeLocation;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SousProgrammeInsertContratDeLocation
        implements SousProgramme<ContratDeLocation> {

    @Override
    public String appelSousProgramme() {
        // Syntaxe standard Oracle avec {call ...}
        // 12 paramètres : ID_CONTRAT + 11 champs (comme la procédure Oracle originale)
        return "{call SND5405A.SAE_Insert_Contrat_De_Location(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    }

    @Override
    public void parametres(CallableStatement stmt, ContratDeLocation c)
            throws SQLException {

        // PARAM 1 : ID_CONTRAT (VARCHAR2 - garder la chaîne complète)
        // Ex: "IMAM10000" reste "IMAM10000"
        stmt.setString(1, c.getIdContrat());
        System.out.println("[PARAM 1] id_contrat = " + c.getIdContrat() + " (VARCHAR2)");

        // PARAM 2 : DATE_DEBUT
        java.sql.Date dateDebut = parseDate(c.getDate_debut());
        stmt.setDate(2, dateDebut);
        System.out.println("[PARAM 2] date_debut = " + dateDebut + " (DATE)");

        // PARAM 3 : TRIMESTRE (NUMBER - convert "T1" -> 1, "T2" -> 2, etc)
        int trimestreNum = convertTrimestreToNumber(c.getTrimestre());
        stmt.setInt(3, trimestreNum);
        System.out.println("[PARAM 3] trimestre = " + trimestreNum + " (NUMBER)");

        // PARAM 4 : DATE_SORTIE
        java.sql.Date dateSortie = parseDate(c.getDate_sortie());
        stmt.setDate(4, dateSortie);
        System.out.println("[PARAM 4] date_sortie = " + dateSortie + " (DATE)");

        // PARAM 5 : LOYER_APAYER
        stmt.setDouble(5, c.getLoyer_aPayer());
        System.out.println("[PARAM 5] loyer_aPayer = " + c.getLoyer_aPayer() + " (NUMBER)");
        
        // PARAM 6 : IRL
        stmt.setDouble(6, c.getIRL());
        System.out.println("[PARAM 6] IRL = " + c.getIRL() + " (NUMBER)");
        
        // PARAM 7 : PROVISIONS_CHARGES
        stmt.setDouble(7, c.getProvisions_charges());
        System.out.println("[PARAM 7] provisions_charges = " + c.getProvisions_charges() + " (NUMBER)");

        // PARAM 8 : SOLDE_TOUT_COMPTE_EFFECTUE (CHAR : convert boolean to '1' or '0')
        String soldeChar = c.isSolde_tout_compte_effectue() ? "1" : "0";
        stmt.setString(8, soldeChar);
        System.out.println("[PARAM 8] solde_tout_compte_effectue = " + soldeChar + " (CHAR)");

        // PARAM 9 : DUREE (NUMBER)
        stmt.setInt(9, c.getDuree());
        System.out.println("[PARAM 9] duree = " + c.getDuree() + " (NUMBER)");
        
        // PARAM 10 : NUMERO_FISCALE
        stmt.setString(10, c.getNumero_fiscale());
        System.out.println("[PARAM 10] numero_fiscale = " + c.getNumero_fiscale() + " (VARCHAR2)");
        
        // PARAM 11 : ID_LOCATAIRE
        stmt.setString(11, c.getId_locataire());
        System.out.println("[PARAM 11] id_locataire = " + c.getId_locataire() + " (VARCHAR2)");

        // PARAM 12 : ARCHIVE (NUMBER : convert boolean to 0 or 1)
        int archiveNum = c.isArchive() ? 1 : 0;
        stmt.setInt(12, archiveNum);
        System.out.println("[PARAM 12] archive = " + archiveNum + " (NUMBER)");
    }

    // Convertir "T1" -> 1, "T2" -> 2, "T3" -> 3, "T4" -> 4
    private int convertTrimestreToNumber(String trimestre) {
        if (trimestre == null || trimestre.isEmpty()) return 1;
        if (trimestre.equalsIgnoreCase("T1")) return 1;
        if (trimestre.equalsIgnoreCase("T2")) return 2;
        if (trimestre.equalsIgnoreCase("T3")) return 3;
        if (trimestre.equalsIgnoreCase("T4")) return 4;
        return 1;  // Défaut
    }

    // Conversion DATE sécurisée (anti ORA-01861 / ORA-01864)
    private Date parseDate(String dateStr) throws SQLException {
        if (dateStr == null || dateStr.isBlank()) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            java.util.Date utilDate = sdf.parse(dateStr);
            return new Date(utilDate.getTime());
        } catch (ParseException e) {
            throw new SQLException(
                "Format de date invalide (attendu yyyy-MM-dd) : " + dateStr,
                e
            );
        }
    }

    @Override
    public void parametresSequence(CallableStatement stmt,
                                   ContratDeLocation donnees)
            throws SQLException { }

    @Override
    public void parametresCalcul(CallableStatement stmt,
                                 ContratDeLocation donnees)
            throws SQLException { }
}
