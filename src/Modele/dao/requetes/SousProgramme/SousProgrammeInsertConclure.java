package Modele.dao.requetes.SousProgramme;

import java.sql.CallableStatement;
import java.sql.SQLException;

import Modele.Conclure;

public class SousProgrammeInsertConclure implements SousProgramme<Conclure> {

    @Override
    public String appelSousProgramme() {
        return "{call SND5405A.Insert_Conclure(?, ?, ?)}";  // Appel à la procédure Insert_Conclure
    }

    @Override
    public void parametres(CallableStatement stmt, Conclure conclure) throws SQLException {
        stmt.setString(1, conclure.getIdLocataire());  // p_id_locataire
        stmt.setString(2, conclure.getIdContrat());       // p_id_contrat      
    }

    @Override
    public void parametresSequence(CallableStatement stmt, Conclure donnees) throws SQLException {
        // Non utilisé ici
    }

    @Override
    public void parametresCalcul(CallableStatement stmt, Conclure donnees) throws SQLException {
        // Non utilisé ici
    }
}
