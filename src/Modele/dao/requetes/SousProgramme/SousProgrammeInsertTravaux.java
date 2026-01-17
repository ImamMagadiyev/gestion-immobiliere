package Modele.dao.requetes.SousProgramme;

import java.sql.CallableStatement;
import java.sql.SQLException;
import Modele.Travaux;

public class SousProgrammeInsertTravaux implements SousProgramme<Travaux>{
	@Override
    public String appelSousProgramme() {
        return "{call SND5405A.SAE_Insert_Travaux(?, ?, ?)}";  

    }

    @Override
    public void parametres(CallableStatement stmt, Travaux travaux ) throws SQLException {
        stmt.setString(1, travaux.getId_Travaux()); 
        stmt.setString(2, travaux.getRaison());       
        stmt.setString(3, travaux.getNumero()); 

    }

    @Override
    public void parametresSequence(CallableStatement stmt, Travaux donnees) throws SQLException {
    }

    @Override
    public void parametresCalcul(CallableStatement stmt, Travaux donnees) throws SQLException {
    }
}

