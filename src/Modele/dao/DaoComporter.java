package Modele.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Modele.Comporter;
import Modele.dao.requetes.Select.RequeteSelectAllComporter;
import Modele.dao.requetes.Select.RequeteSelectByIdComporter;
import Modele.dao.requetes.Update.RequeteUpdateComporter;
import Modele.dao.requetes.Delete.RequeteDeleteComporter;
import Modele.dao.requetes.SousProgramme.SousProgramme;
import Modele.dao.requetes.SousProgramme.SousProgrammeInsertComporter;

public class DaoComporter extends DaoModele<Comporter> implements Dao<Comporter> {

    @Override
    public void create(Comporter l) throws SQLException {
        SousProgramme<Comporter> sp = new SousProgrammeInsertComporter();
        this.miseAJour(sp, l);
    }

    @Override
    public void update(Comporter l) throws SQLException {
        this.miseAJour(new RequeteUpdateComporter(), l);
    }

    @Override
    public void delete(Comporter l) throws SQLException {
        this.miseAJour(new RequeteDeleteComporter(), l);
    }

    @Override
    protected Comporter creerInstance(ResultSet rs) throws SQLException {
        return new Comporter(
            rs.getString("IDBATIMENT"),
            rs.getString("NUMERO")
        );
    }

    @Override
    public List<Comporter> findAll() throws SQLException {
        return this.find(new RequeteSelectAllComporter());
    }

    @Override
    public Comporter findById(String... id) throws SQLException {
        List<Comporter> liste = this.find(new RequeteSelectByIdComporter(), id);
        return liste.isEmpty() ? null : liste.get(0);
    }
}
