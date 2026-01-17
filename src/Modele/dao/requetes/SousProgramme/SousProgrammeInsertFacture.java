package Modele.dao.requetes.SousProgramme;

import java.sql.CallableStatement;
import java.sql.SQLException;
import Modele.Facture;

public class SousProgrammeInsertFacture implements SousProgramme<Facture> {


    @Override
    public String appelSousProgramme() {
        return "{call SND5405A.sae_insert_facture(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";


	}


    @Override
    public void parametres(CallableStatement stmt, Facture f) throws SQLException {
        stmt.setString(1, f.getNumero());
        stmt.setDate(2, f.getDate_facture());
        stmt.setString(3, f.getType_travaux());
        stmt.setDouble(4, f.getMontant());
        stmt.setString(5, f.getMode_paiement());
        stmt.setString(6, f.getNumero_devis());
        stmt.setString(7, f.getNature());
        stmt.setDate(8, f.getDate_devis());
        stmt.setBoolean(9, f.getPayer_par_locataire());
        stmt.setString(10, f.getSiret());
        stmt.setString(11, f.getNumero_fiscale());
        stmt.setDouble(12, f.getMontantDevis());
    }

	@Override public void parametresSequence(CallableStatement stmt, Facture d) throws SQLException { }
	@Override public void parametresCalcul(CallableStatement stmt, Facture d) throws SQLException { }
}