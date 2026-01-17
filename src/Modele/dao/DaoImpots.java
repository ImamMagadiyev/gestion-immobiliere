package Modele.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Modele.Impots;
import Modele.dao.requetes.Select.RequeteSelectAllImpots;
import Modele.dao.requetes.Select.RequeteSelectByIdImpots;
import Modele.dao.requetes.Update.RequeteUpdateImpots;
import Modele.dao.requetes.Delete.RequeteDeleteImpots;
import Modele.dao.requetes.SousProgramme.SousProgramme;
import Modele.dao.requetes.SousProgramme.SousProgrammeInsertImpots;

public class DaoImpots extends DaoModele<Impots> implements Dao<Impots> {

    @Override
    public void create(Impots i) throws SQLException {
        SousProgramme<Impots> sp = new SousProgrammeInsertImpots();
        this.miseAJour(sp, i);
    }

    @Override
    public void update(Impots i) throws SQLException {
        this.miseAJour(new RequeteUpdateImpots(), i);
    }

    @Override
    public void delete(Impots i) throws SQLException {
        this.miseAJour(new RequeteDeleteImpots(), i);
    }

    @Override
    protected Impots creerInstance(ResultSet rs) throws SQLException {
        return new Impots(
            rs.getString("ID_IMPOTS"),
            rs.getDouble("RECUPERABLE_IMPOT"),
            rs.getInt("ANNEE"),
            rs.getString("TYPE"),
            rs.getString("NUMERO")
        );
    }

    @Override
    public List<Impots> findAll() throws SQLException {
        return this.find(new RequeteSelectAllImpots());
    }

    @Override
    public Impots findById(String... id) throws SQLException {
        List<Impots> liste = this.find(new RequeteSelectByIdImpots(), id);
        return liste.isEmpty() ? null : liste.get(0);
    }
}
