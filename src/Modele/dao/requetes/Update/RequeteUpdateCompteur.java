package Modele.dao.requetes.Update;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Compteur;
import Modele.dao.requetes.Requete;

public class RequeteUpdateCompteur implements Requete<Compteur> {
    @Override
    public String requete() {
        return "UPDATE SND5405A.SAE_COMPTEUR "
             + "SET TYPE_ = ?, INDEX_VAL = ?, ID_VARIABLE = ?, NUMERO_FISCALE = ?, ID_BATIMENT = ? "
             + "WHERE ID_COMPTEUR = ? AND ARCHIVE = 0";
    }
    @Override
    public void parametres(PreparedStatement prSt, Compteur c) throws SQLException {
        prSt.setString(1, c.getType());            
        prSt.setDouble(2, c.getIndex());           
        prSt.setString(3, c.getId_variable());     
        prSt.setString(4, c.getNumero_fiscale());  
        prSt.setString(5, c.getIdBatiment());      
        prSt.setString(6, c.getId_compteur());     
    }

    @Override public void parametres(PreparedStatement prSt, String... id) throws SQLException { }
}