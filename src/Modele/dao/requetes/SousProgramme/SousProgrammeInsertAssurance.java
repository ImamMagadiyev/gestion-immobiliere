package Modele.dao.requetes.SousProgramme;

import java.sql.CallableStatement;
import java.sql.SQLException;
import Modele.Assurance;

public class SousProgrammeInsertAssurance implements SousProgramme<Assurance> {

    @Override
    public String appelSousProgramme() {
        return "{call SND5405A.sae_Insert_Assurance(?, ?, ?, ?)}";
  // Appel à la procédure Insert_Assurance
    }

    @Override
    public void parametres(CallableStatement stmt, Assurance assurance) throws SQLException {
        stmt.setString(1, assurance.getId_assurance());  // p_id_assurance
        stmt.setString(2, assurance.getType());         // p_type
        stmt.setLong(3, assurance.getAnnee());           // p_annee
        stmt.setNString(4, assurance.getNumero());       // p_numero
    }

    @Override
    public void parametresSequence(CallableStatement stmt, Assurance donnees) throws SQLException {
        // Non utilisé ici
    }

    @Override
    public void parametresCalcul(CallableStatement stmt, Assurance donnees) throws SQLException {
        // Non utilisé ici
    }
}
