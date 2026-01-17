package Modele.dao.requetes.Restaurer;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Facture;
import Modele.dao.requetes.Requete;

public class RequeteRestaurerFacture implements Requete<Facture> {

    @Override
    public String requete() {
        return "UPDATE SND5405A.SAE_FACTURE SET ARCHIVE = 0 WHERE NUMERO = ?";
    }

    @Override
    public void parametres(PreparedStatement ps, Facture f) throws SQLException {
        ps.setString(1, f.getNumero());
    }

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
	    prSt.setString(1, id[0]);

	}
}

