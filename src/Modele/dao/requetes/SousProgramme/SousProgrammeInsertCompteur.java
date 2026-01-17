package Modele.dao.requetes.SousProgramme;

import java.sql.CallableStatement;
import java.sql.SQLException;
import Modele.Compteur;
import java.sql.Types;


public class SousProgrammeInsertCompteur implements SousProgramme<Compteur> {

    @Override
    public String appelSousProgramme() {
        return "{call SND5405A.SAE_Insert_Compteur(?, ?, ?, ?, ?, ?, ?)}";  
    }


    @Override
    public void parametres(CallableStatement stmt, Compteur compteur) throws SQLException {

        stmt.setString(1, compteur.getId_compteur());
        stmt.setString(2, compteur.getType());
        stmt.setDouble(3, compteur.getIndex());
        stmt.setString(4, compteur.getId_variable());

        // NUMERO_FISCALE
        if (compteur.getNumero_fiscale() != null) {
            stmt.setString(5, compteur.getNumero_fiscale());
        } else {
            stmt.setNull(5, Types.VARCHAR);
        }

        // ID_BATIMENT
        if (compteur.getIdBatiment() != null) {
            stmt.setString(6, compteur.getIdBatiment());
        } else {
            stmt.setNull(6, Types.VARCHAR);
        }

        // ARCHIVE (boolean → NUMBER)
        stmt.setInt(7, compteur.isArchiver() ? 1 : 0);
    }


    @Override
    public void parametresSequence(CallableStatement stmt, Compteur donnees) throws SQLException {
    }

    @Override
    public void parametresCalcul(CallableStatement stmt, Compteur donnees) throws SQLException {
    }
}
