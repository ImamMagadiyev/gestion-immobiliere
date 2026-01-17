package Modele.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Modele.Travaux;
import Modele.dao.requetes.Delete.RequeteDeleteTravaux;
import Modele.dao.requetes.Select.RequeteSelectAllTravaux;
import Modele.dao.requetes.Select.RequeteSelectByIdTravaux;
import Modele.dao.requetes.SousProgramme.SousProgramme;
import Modele.dao.requetes.SousProgramme.SousProgrammeInsertTravaux;
import Modele.dao.requetes.Update.RequeteUpdateTravaux;

public class DaoTravaux extends DaoModele<Travaux> {

    @Override
    public void create(Travaux c) throws SQLException {
        SousProgramme<Travaux> sp = new SousProgrammeInsertTravaux();
        this.miseAJour(sp, c);
    }

    @Override
    public void update(Travaux c) throws SQLException {
        this.miseAJour(new RequeteUpdateTravaux(), c);
    }

    @Override
    public void delete(Travaux c) throws SQLException {
        this.miseAJour(new RequeteDeleteTravaux(), c);
    }
    

    @Override
    protected Travaux creerInstance(ResultSet rs) throws SQLException {
        return new Travaux(
            rs.getString("ID_TRAVAUX"),
            rs.getString("RAISON"),
            rs.getString("NUMERO")
        );
    }

    @Override
    public List<Travaux> findAll() throws SQLException {
        return this.find(new RequeteSelectAllTravaux());
    }

    
    @Override
    public Travaux findById(String... id) throws SQLException {
        List<Travaux> liste = this.find(new RequeteSelectByIdTravaux(), id);
        return liste.isEmpty() ? null : liste.get(0);
    }
    

}


