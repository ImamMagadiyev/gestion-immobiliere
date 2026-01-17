package Modele.dao.requetes.Select;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Facture;
import Modele.dao.requetes.Requete;

public class RequeteSelectByIdFacture implements Requete<Facture> {
    @Override
    public String requete() {
        return "SELECT * FROM SND5405A.SAE_FACTURE WHERE NUMERO = ?";
    }
    @Override
    public void parametres(PreparedStatement prSt, Facture f) throws SQLException {
        prSt.setString(1, f.getNumero());
    }
    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        prSt.setString(1, id[0]);
    }
}