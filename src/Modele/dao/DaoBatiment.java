package Modele.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Modele.Batiment;
import Modele.dao.requetes.Select.RequeteSelectAllBatiment;
import Modele.dao.requetes.Select.RequeteSelectByIdBatiment;
import Modele.dao.requetes.Update.RequeteUpdateBatiment;
import Modele.dao.requetes.Delete.RequeteDeleteBatiment;
import Modele.dao.requetes.SousProgramme.SousProgramme;
import Modele.dao.requetes.SousProgramme.SousProgrammeInsertBatiment;

public class DaoBatiment extends DaoModele<Batiment> implements Dao<Batiment> {

    @Override
    public void create(Batiment b) throws SQLException {
        SousProgramme<Batiment> sp = new SousProgrammeInsertBatiment();
        this.miseAJour(sp, b);
    }

    @Override
    public void update(Batiment b) throws SQLException {
        this.miseAJour(new RequeteUpdateBatiment(), b);
    }

    @Override
    public void delete(Batiment b) throws SQLException {
        this.miseAJour(new RequeteDeleteBatiment(), b);
    }

    @Override
    protected Batiment creerInstance(ResultSet rs) throws SQLException {
        return new Batiment(
            rs.getString("ID_BATIMENT"),
            rs.getString("ADRESSE"),
            rs.getString("VILLE"),
            rs.getString("CODE_POSTALE"),
            rs.getDate("PERIODEDECONSTRUCTION").toLocalDate()
        );
    }

    @Override
    public List<Batiment> findAll() throws SQLException {
        return this.find(new RequeteSelectAllBatiment());
    }

    @Override
    public Batiment findById(String... id) throws SQLException {
        List<Batiment> liste = this.find(new RequeteSelectByIdBatiment(), id);
        return liste.isEmpty() ? null : liste.get(0);
    }
}
