package Modele.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Modele.Entreprise;
import Modele.dao.requetes.Select.RequeteSelectAllEntreprise;
import Modele.dao.requetes.Select.RequeteSelectByIdEntreprise;
import Modele.dao.requetes.Update.RequeteUpdateEntreprise;
import Modele.dao.requetes.Delete.RequeteDeleteEntreprise;
import Modele.dao.requetes.SousProgramme.SousProgramme;
import Modele.dao.requetes.SousProgramme.SousProgrammeInsertEntreprise;

public class DaoEntreprise extends DaoModele<Entreprise> implements Dao<Entreprise> {

    @Override
    public void create(Entreprise e) throws SQLException {
        SousProgramme<Entreprise> sp = new SousProgrammeInsertEntreprise();
        this.miseAJour(sp, e);
    }

    @Override
    public void update(Entreprise e) throws SQLException {
        this.miseAJour(new RequeteUpdateEntreprise(), e);
    }

    @Override
    public void delete(Entreprise e) throws SQLException {
        this.miseAJour(new RequeteDeleteEntreprise(), e);
    }

    @Override
    protected Entreprise creerInstance(ResultSet rs) throws SQLException {
        return new Entreprise(
            rs.getString("SIRET"),
            rs.getString("NOM"),
            rs.getString("VILLE"),
            rs.getString("MAIL_ENTREPRISE"),
            rs.getString("ADRESSE"),
            rs.getString("SPECIALITE"),
            rs.getString("CODE_POSTALE")
        );
    }

    @Override
    public List<Entreprise> findAll() throws SQLException {
        return this.find(new RequeteSelectAllEntreprise());
    }

    @Override
    public Entreprise findById(String... id) throws SQLException {
        List<Entreprise> liste = this.find(new RequeteSelectByIdEntreprise(), id);
        return liste.isEmpty() ? null : liste.get(0);
    }
}
