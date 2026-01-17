package Modele.dao.requetes.SousProgramme;

import java.sql.CallableStatement;
import java.sql.SQLException;
import Modele.Agence;

public class SousProgrammeInsertAgence implements SousProgramme<Agence> {

    @Override
    public String appelSousProgramme() {
        return "{call SND5405A.sae_insert_agence(?)}";  // Appel à la procédure sae_insert_agence
    }

    @Override
    public void parametres(CallableStatement stmt, Agence agence) throws SQLException {
        stmt.setNString(4, agence.getNumero());       // p_numero
    }

    @Override
    public void parametresSequence(CallableStatement stmt, Agence donnees) throws SQLException {
        // Non utilisé ici
    }

    @Override
    public void parametresCalcul(CallableStatement stmt, Agence donnees) throws SQLException {
        // Non utilisé ici
    }
}
