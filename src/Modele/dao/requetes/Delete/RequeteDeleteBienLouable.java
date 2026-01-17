package Modele.dao.requetes.Delete;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Modele.BienLouable;
import Modele.dao.requetes.Requete;

public class RequeteDeleteBienLouable implements Requete<BienLouable> {

    @Override
    public String requete() {
        return "DELETE FROM SND5405A.SAE_BIEN_LOUABLE WHERE NUMERO_FISCALE = ?";
    }

    @Override
    public void parametres(PreparedStatement prSt, BienLouable bien) throws SQLException {
        prSt.setString(1, bien.getNumero_fiscale());
        
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
    	//TODO
    }
}
