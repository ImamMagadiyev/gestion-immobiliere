package Modele.dao;

import Modele.BienLouableQuotite;
import Modele.dao.requetes.Select.RequeteSelectAllBienLouableQuotite;
import Modele.dao.requetes.Select.RequeteSelectBienLouableQuotiteByBatiment;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DaoBienLouableQuotite
        extends DaoModele<BienLouableQuotite>
        implements Dao<BienLouableQuotite> {

    @Override
    public void create(BienLouableQuotite obj) throws SQLException {
        throw new UnsupportedOperationException("La vue V_BIEN_LOUABLE_QUOTITE est en lecture seule");
    }

    @Override
    public void update(BienLouableQuotite obj) throws SQLException {
        throw new UnsupportedOperationException("La vue V_BIEN_LOUABLE_QUOTITE est en lecture seule");
    }

    @Override
    public void delete(BienLouableQuotite obj) throws SQLException {
        throw new UnsupportedOperationException("La vue V_BIEN_LOUABLE_QUOTITE est en lecture seule");
    }

    @Override
    protected BienLouableQuotite creerInstance(ResultSet rs) throws SQLException {
        return new BienLouableQuotite(
            rs.getString("NUMERO_FISCALE"),
            rs.getString("TYPE"),
            rs.getDouble("SURFACE"),
            rs.getInt("NOMBRE_PIECES"),
            rs.getInt("ETAGE"),
            rs.getString("PORTE"),
            rs.getString("ID_BATIMENT"),
            rs.getDouble("QUOTITE")
        );
    }

    @Override
    public List<BienLouableQuotite> findAll() throws SQLException {
        return this.find(new RequeteSelectAllBienLouableQuotite());
    }

    @Override
    public BienLouableQuotite findById(String... id) throws SQLException {
        throw new UnsupportedOperationException("Utilisez findByBatiment() à la place");
    }

    // Récupère tous les biens louables avec quotite pour un bâtiment
    public List<BienLouableQuotite> findByBatiment(String idBatiment) throws SQLException {
        return this.find(new RequeteSelectBienLouableQuotiteByBatiment(), idBatiment);
    }
}
