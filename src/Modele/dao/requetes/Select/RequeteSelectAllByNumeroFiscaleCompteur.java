package Modele.dao.requetes.Select;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Compteur;
import Modele.dao.requetes.Requete;

public class RequeteSelectAllByNumeroFiscaleCompteur implements Requete<Compteur> {

    @Override
    public String requete() {
        return "SELECT * FROM SND5405A.SAE_COMPTEUR WHERE NUMERO_FISCALE = ? ORDER BY 1";
    }

    @Override
    public void parametres(PreparedStatement prSt, Compteur c) throws SQLException {
        prSt.setString(1, c.getNumero_fiscale());
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        prSt.setString(1, id[0]);
    }
}
