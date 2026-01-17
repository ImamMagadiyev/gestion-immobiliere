package Modele.dao.requetes.Update;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Modele.Conclure;
import Modele.dao.requetes.Requete;

public class RequeteUpdateConclure implements Requete<Conclure> {

    @Override
    public String requete() {
        return "UPDATE SND5405A.SAE_CONCLURE "
             + "SET QUOTITE = ? "
             + "WHERE ID_LOCATAIRE = ? AND ID_CONTRAT = ?";
    }

    @Override
    public void parametres(PreparedStatement prSt, Conclure c) throws SQLException {
        prSt.setString(2, c.getIdLocataire());
        prSt.setString(3, c.getIdContrat());
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException { }
}
