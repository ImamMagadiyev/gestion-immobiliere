package Modele.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Modele.Compteur;
import Modele.dao.requetes.Select.*;
import Modele.dao.requetes.Update.RequeteUpdateCompteur;
import Modele.dao.requetes.Delete.RequeteDeleteCompteur;
import Modele.dao.requetes.SousProgramme.SousProgrammeInsertCompteur;
import Modele.dao.requetes.Archiver.RequeteArchiverCompteur;
import Modele.dao.requetes.Restaurer.RequeteRestaurerCompteur;

public class DaoCompteur extends DaoModele<Compteur> implements Dao<Compteur> {

    @Override
    public void create(Compteur c) throws SQLException {
        SousProgrammeInsertCompteur sp = new SousProgrammeInsertCompteur();
        this.miseAJour(sp, c);
    }

    @Override
    public void update(Compteur c) throws SQLException {
        this.miseAJour(new RequeteUpdateCompteur(), c);
    }

    @Override
    public void delete(Compteur c) throws SQLException {
        this.miseAJour(new RequeteDeleteCompteur(), c);
    }

    public void archiver(Compteur c) throws SQLException {
        this.miseAJour(new RequeteArchiverCompteur(), c);
    }

    public void archiverById(String idCompteur) throws SQLException {
        this.miseAJour(new RequeteArchiverCompteur(), idCompteur);
    }
    
    public void restaurer(Compteur c) throws SQLException {
        this.miseAJour(new RequeteRestaurerCompteur(), c);
    }

    public void restaurerById(String idCompteur) throws SQLException {
        this.miseAJour(new RequeteRestaurerCompteur(), idCompteur);
    }

    @Override
    protected Compteur creerInstance(ResultSet rs) throws SQLException {
        return new Compteur(
            rs.getString("ID_COMPTEUR"),
            rs.getString("TYPE_"),
            rs.getDouble("INDEX_VAL"),
            rs.getString("ID_VARIABLE"),
            rs.getString("NUMERO_FISCALE"),
            rs.getString("ID_BATIMENT"),
            rs.getInt("ARCHIVE") == 1
        );
    }

    @Override
    public List<Compteur> findAll() throws SQLException {
        return this.find(new RequeteSelectAllCompteur());
    }

    @Override
    public Compteur findById(String... id) throws SQLException {
        List<Compteur> liste = this.find(new RequeteSelectByIdCompteur(), id);
        return liste.isEmpty() ? null : liste.get(0);
    }


    // Compteurs archivés
    public List<Compteur> findAllArchives() throws SQLException {
        return this.find(new RequeteSelectAllCompteurArchives());
    }

    // Par numéro fiscale
    public Compteur findByNumeroFiscale(String numeroFiscale) throws SQLException {
        List<Compteur> liste = this.find(new RequeteSelectAllByNumeroFiscaleCompteur(), numeroFiscale);
        return liste.isEmpty() ? null : liste.get(0);
    }

    public List<Compteur> findAllByNumeroFiscale(String numeroFiscale) throws SQLException {
        return this.find(new RequeteSelectAllByNumeroFiscaleCompteur(), numeroFiscale);
    }

    // Par bâtiment
    public Compteur findByBatiment(String idBatiment) throws SQLException {
        List<Compteur> liste = this.find(new RequeteSelectAllByBatimentCompteur(), idBatiment);
        return liste.isEmpty() ? null : liste.get(0);
    }

    public List<Compteur> findAllByBatiment(String idBatiment) throws SQLException {
        return this.find(new RequeteSelectAllByBatimentCompteur(), idBatiment);
    }
}
