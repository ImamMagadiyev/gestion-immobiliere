package Modele.dao.requetes.Select;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Entreprise;
import Modele.dao.requetes.Requete;

public class RequeteSelectByIdEntreprise implements Requete<Entreprise> {
    @Override
    public String requete() {
        return "SELECT * FROM SND5405A.SAE_ENTREPRISE WHERE SIRET = ?";
    }
    @Override
    public void parametres(PreparedStatement prSt, Entreprise e) throws SQLException {
        prSt.setString(1, e.getSiret());
    }
    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        prSt.setString(1, id[0]);
    }
}