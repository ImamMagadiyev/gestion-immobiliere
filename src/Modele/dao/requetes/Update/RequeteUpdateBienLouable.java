package Modele.dao.requetes.Update;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Modele.BienLouable;
import Modele.dao.requetes.Requete;

public class RequeteUpdateBienLouable implements Requete<BienLouable> {

    @Override
    public String requete() {
        return "UPDATE SND5405A.SAE_BIEN_LOUABLE "
             + "SET TYPE = ?, SURFACE = ?, NOMBRE_PIECES = ?, ID_BATIMENT = ?, ETAGE = ?, PORTE = ? "
             + "WHERE NUMERO_FISCALE = ?";
    }

    @Override
    public void parametres(PreparedStatement prSt, BienLouable bien) throws SQLException {
        prSt.setString(1, bien.getType());
        prSt.setInt(2, bien.getSurface());
        prSt.setInt(3, bien.getNb_pieces());
        prSt.setString(4, bien.getBatiment());
        prSt.setInt(5, bien.getEtage());
        prSt.setString(6, bien.getPorte());
        prSt.setString(7, bien.getNumero_fiscale());
        
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException { }
}
