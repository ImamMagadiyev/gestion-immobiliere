package Modele.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Modele.Caution;
import Modele.dao.requetes.Select.RequeteSelectAllCaution;
import Modele.dao.requetes.Select.RequeteSelectAllCautionArchives;
import Modele.dao.requetes.Select.RequeteSelectByIdCaution;
import Modele.dao.requetes.Archiver.RequeteArchiverCaution;
import Modele.dao.requetes.Restaurer.RequeteRestaurerCaution;
import Modele.dao.requetes.Update.RequeteUpdateCaution;
import Modele.dao.requetes.Delete.RequeteDeleteCaution;
import Modele.dao.requetes.SousProgramme.SousProgramme;
import Modele.dao.requetes.SousProgramme.SousProgrammeInsertCaution;

public class DaoCaution extends DaoModele<Caution> implements Dao<Caution> {

    @Override
    public void create(Caution c) throws SQLException {
        SousProgramme<Caution> sp = new SousProgrammeInsertCaution();
        this.miseAJour(sp, c);
    }

    @Override
    public void update(Caution c) throws SQLException {
        this.miseAJour(new RequeteUpdateCaution(), c);
    }

    @Override
    public void delete(Caution c) throws SQLException {
        this.miseAJour(new RequeteDeleteCaution(), c);
    }
    
    public void archiver(Caution c) throws SQLException {
    	this.miseAJour(new RequeteArchiverCaution(), c);
    }
    
    public void archiverById(String IdCaution) throws SQLException {
    	this.miseAJour(new RequeteArchiverCaution(), IdCaution);
    }
    
    public void restaurer(Caution c) throws SQLException {
    	this.miseAJour(new RequeteRestaurerCaution(), c);
    }
    
    public void restaurerById(String IdCaution) throws SQLException {
    	this.miseAJour(new RequeteRestaurerCaution(), IdCaution);
    }


    @Override
    protected Caution creerInstance(ResultSet rs) throws SQLException {
        return new Caution(
            rs.getString("ID_CAUTION"),
            rs.getString("NOM"),
            rs.getString("PRENOM"),
            rs.getString("ADRESSE"),
            rs.getString("VILLE"),
            rs.getString("CODE_POSTALE"),
            rs.getString("PROFESSION"),
            rs.getString("TY_CONTRAT_TRAVAIL"),
            rs.getString("DATE_NAISSANCE"),
            rs.getString("ID_CONTRAT"),
            rs.getString("ADRESSEELECTRONIQUE"),
            rs.getString("TEL"),
            rs.getString("QUALITE_DU_BAILLEUR"),
            rs.getInt("ARCHIVE") == 1
        );
    }

    @Override
    public List<Caution> findAll() throws SQLException {
        return this.find(new RequeteSelectAllCaution());
    }

    public List<Caution> findAllArchives() throws SQLException {
        return this.find(new RequeteSelectAllCautionArchives());
    }
    
    @Override
    public Caution findById(String... id) throws SQLException {
        List<Caution> liste = this.find(new RequeteSelectByIdCaution(), id);
        return liste.isEmpty() ? null : liste.get(0);
    }
}
