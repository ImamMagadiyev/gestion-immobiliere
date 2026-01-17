package Modele.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Modele.BienLouable;
import Modele.dao.requetes.Select.RequeteSelectAllBienLouable;
import Modele.dao.requetes.Select.RequeteSelectByIdBienLouable;
import Modele.dao.requetes.Update.RequeteUpdateBienLouable;
import Modele.dao.requetes.Delete.RequeteDeleteBienLouable;
import Modele.dao.requetes.SousProgramme.SousProgramme;
import Modele.dao.requetes.SousProgramme.SousProgrammeInsertBienLouable;

public class DaoBienLouable extends DaoModele<BienLouable> implements Dao<BienLouable> {

    @Override
    public void create(BienLouable bien) throws SQLException {
        SousProgramme<BienLouable> sp = new SousProgrammeInsertBienLouable();
        this.miseAJour(sp, bien);
    }

    @Override
    public void update(BienLouable bien) throws SQLException {
        this.miseAJour(new RequeteUpdateBienLouable(), bien);
    }

    @Override
    public void delete(BienLouable bien) throws SQLException {
        this.miseAJour(new RequeteDeleteBienLouable(), bien);
    }

    @Override
    protected BienLouable creerInstance(ResultSet rs) throws SQLException {
        return new BienLouable(
            rs.getString("NUMERO_FISCALE"),
            rs.getString("TYPE"),
            rs.getInt("SURFACE"),
            rs.getInt("NOMBRE_PIECES"),
            rs.getString("ID_BATIMENT"),
            rs.getInt("ETAGE"),
            rs.getString("PORTE")
        );
    }

    @Override
    public List<BienLouable> findAll() throws SQLException {
        return this.find(new RequeteSelectAllBienLouable());
    }

    @Override
    public BienLouable findById(String... id) throws SQLException {
        List<BienLouable> liste = this.find(new RequeteSelectByIdBienLouable(), id);
        return liste.isEmpty() ? null : liste.get(0);
    }
}
