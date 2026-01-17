package Modele.dao.requetes.SousProgramme;

import java.sql.CallableStatement;
import java.sql.SQLException;
import Modele.Comporter;

public class SousProgrammeInsertComporter implements SousProgramme<Comporter> {

    @Override
    public String appelSousProgramme() {
        return "{call SND5405A.Insert_Comporter(?, ?)}";  // Appel à la procédure Insert_Comporter
    }

    @Override
    public void parametres(CallableStatement stmt, Comporter comporter) throws SQLException {
        stmt.setString(1, comporter.getIdBatiment());  // p_id_batiment
        stmt.setString(2, comporter.getNumero());  // p_numero_facture
    }

    @Override
    public void parametresSequence(CallableStatement stmt, Comporter donnees) throws SQLException {
        // Non utilisé ici
    }

    @Override
    public void parametresCalcul(CallableStatement stmt, Comporter donnees) throws SQLException {
        // Non utilisé ici
    }
}
