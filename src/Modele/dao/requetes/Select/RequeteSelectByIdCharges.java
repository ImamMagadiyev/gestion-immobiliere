package Modele.dao.requetes.Select;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Charges;
import Modele.dao.requetes.Requete;

public class RequeteSelectByIdCharges implements Requete<Charges> {
    @Override
    public String requete() {
        return "SELECT * FROM SND5405A.SAE_CHARGES WHERE ID_CHARGES = ?";
    }
    @Override
    public void parametres(PreparedStatement prSt, Charges c) throws SQLException {
        prSt.setString(1, c.getId_charges());
    }
    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        prSt.setInt(1, Integer.parseInt(id[0]));
    }
}