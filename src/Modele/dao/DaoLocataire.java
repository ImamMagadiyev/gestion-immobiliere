package Modele.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Modele.Locataire;
import Modele.dao.requetes.Select.RequeteSelectAllLocataire;
import Modele.dao.requetes.Select.RequeteSelectAllLocataireArchives;
import Modele.dao.requetes.Select.RequeteSelectByIdLocataire;
import Modele.dao.requetes.Update.RequeteUpdateLocataire;
import Modele.dao.requetes.Archiver.RequeteArchiverLocataire;
import Modele.dao.requetes.Restaurer.RequeteRestaurerLocataire;
import Modele.dao.requetes.Delete.RequeteDeleteLocataire;
import Modele.dao.requetes.SousProgramme.SousProgrammeInsertLocataire;

public class DaoLocataire extends DaoModele<Locataire> implements Dao<Locataire> {

    @Override
    public void create(Locataire locataire) throws SQLException {
        SousProgrammeInsertLocataire sp = new SousProgrammeInsertLocataire();
        this.miseAJour(sp, locataire);
    }

    @Override
    public void update(Locataire locataire) throws SQLException {
        this.miseAJour(new RequeteUpdateLocataire(), locataire);
    }

    @Override
    public void delete(Locataire locataire) throws SQLException {
        this.miseAJour(new RequeteDeleteLocataire(), locataire);
    }

    public void archiver(Locataire locataire) throws SQLException {
        this.miseAJour(new RequeteArchiverLocataire(), locataire);
    }

    public void archiverById(String idLocataire) throws SQLException {
        this.miseAJour(new RequeteArchiverLocataire(), idLocataire);
    }
    
    public void restaurer(Locataire locataire) throws SQLException {
        this.miseAJour(new RequeteRestaurerLocataire(), locataire);
    }

    public void restaurerById(String idLocataire) throws SQLException {
        this.miseAJour(new RequeteRestaurerLocataire(), idLocataire);
    }

    @Override
    protected Locataire creerInstance(ResultSet rs) throws SQLException {
        return new Locataire(
            rs.getString("ID_LOCATAIRE"),
            rs.getString("NOM"),
            rs.getString("PRENOM"),
            rs.getString("DATE_NAISSANCE"),
            rs.getString("GENRE").charAt(0),
            rs.getString("EMAIL"),
            rs.getString("ADRESSE"),
            rs.getString("VILLE"),
            rs.getString("CODE_POSTAL"),
            rs.getString("TELEPHONE"),
            rs.getInt("ARCHIVE") == 1
        );
    }

    @Override
    public List<Locataire> findAll() throws SQLException {
        return this.find(new RequeteSelectAllLocataire());
    }

    public List<Locataire> findAllArchives() throws SQLException {
        return this.find(new RequeteSelectAllLocataireArchives());
    }

    @Override
    public Locataire findById(String... id) throws SQLException {
        List<Locataire> liste = this.find(new RequeteSelectByIdLocataire(), id);
        return liste.isEmpty() ? null : liste.get(0);
    }
}
