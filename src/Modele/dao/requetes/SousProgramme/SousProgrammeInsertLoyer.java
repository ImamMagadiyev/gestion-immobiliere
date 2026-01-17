package Modele.dao.requetes.SousProgramme;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;

import Modele.Loyer;

public class SousProgrammeInsertLoyer implements SousProgramme<Loyer> {

    @Override
    public String appelSousProgramme() {
        return "{call SND5405A.sae_Insert_Loyer(?, ?, ?, ?, ?, ?, ?, ?)}";
    }

    @Override
    public void parametres(CallableStatement stmt, Loyer l) throws SQLException {

        stmt.setString(1, l.getIdLocataire());
        stmt.setString(2, l.getNumero_fiscale());
        stmt.setDate(3, Date.valueOf(l.getDate_paiement()));
        stmt.setDouble(4, l.getMontant_provision());
        stmt.setDouble(5, l.getMontant_loyer());
        stmt.setString(6, l.getMois());
        stmt.setDouble(7, l.getMontant_regularisation());
        stmt.setInt(8, 0); 
    }

    @Override
    public void parametresSequence(CallableStatement stmt, Loyer l) throws SQLException { }

    @Override
    public void parametresCalcul(CallableStatement stmt, Loyer l) throws SQLException { }
}
