package Modele.dao.requetes.Delete;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Modele.Locataire;
import Modele.dao.requetes.Requete;

public class RequeteDeleteLocataire implements Requete<Locataire> {

    @Override
    public String requete() {
        return "DELETE FROM SND5405A.SAE_LOCATAIRE WHERE ID_LOCATAIRE = ?";
    }

    @Override
    public void parametres(PreparedStatement prSt, Locataire loc) throws SQLException {
        prSt.setString(1, loc.getIdLocataire());
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException { }
}
