package Modele.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Modele.Facture;
import Modele.dao.requetes.Archiver.RequeteArchiverFacture;
import Modele.dao.requetes.Restaurer.RequeteRestaurerFacture;
import Modele.dao.requetes.Delete.RequeteDeleteFacture;
import Modele.dao.requetes.Select.RequeteSelectAllFacture;
import Modele.dao.requetes.Select.RequeteSelectAllFactureArchives;
import Modele.dao.requetes.Select.RequeteSelectByIdFacture;
import Modele.dao.requetes.SousProgramme.SousProgramme;
import Modele.dao.requetes.SousProgramme.SousProgrammeInsertFacture;
import Modele.dao.requetes.Update.RequeteUpdateFacture;

public class DaoFacture extends DaoModele<Facture> implements Dao<Facture> {

    @Override
    public void create(Facture f) throws SQLException {
        SousProgramme<Facture> sp = new SousProgrammeInsertFacture();
        this.miseAJour(sp, f);
    }

    @Override
    public void update(Facture f) throws SQLException {
        this.miseAJour(new RequeteUpdateFacture(), f);
    }

    @Override
    public void delete(Facture f) throws SQLException {
        this.miseAJour(new RequeteDeleteFacture(), f);
    }
    
    public void archiver(Facture f) throws SQLException {
    	this.miseAJour(new RequeteArchiverFacture(), f);
    }
    
    public void archiverById(String numero) throws SQLException {
    	this.miseAJour(new RequeteArchiverFacture(), numero);
    }
    
    public void restaurer(Facture f) throws SQLException {
    	this.miseAJour(new RequeteRestaurerFacture(), f);
    }
    
    public void restaurerById(String numero) throws SQLException {
    	this.miseAJour(new RequeteRestaurerFacture(), numero);
    }

    @Override
    protected Facture creerInstance(ResultSet rs) throws SQLException {
        return new Facture(
            rs.getString("NUMERO"),
            rs.getDate("DATE_FACTURE"),
            rs.getString("TYPE_FACTURE"),
            rs.getDouble("MONTANT"),
            rs.getString("MODE_PAIEMENT"),
            rs.getString("NUMERO_DEVIS"),
            rs.getString("NATURE"),
            rs.getDate("DATE_DEVIS"),
            rs.getBoolean("PAYER_PAR_LOCATAIRE"),
            rs.getString("SIRET"),
            rs.getString("NUMERO_FISCALE"),
            rs.getDouble("MONTANT_DEVIS"),
            rs.getInt("ARCHIVE") == 1
        );
    }

    @Override
    public List<Facture> findAll() throws SQLException {
        return this.find(new RequeteSelectAllFacture());
    }
    
    public List<Facture> findAllArchiver() throws SQLException {
        return this.find(new RequeteSelectAllFactureArchives());
    }

    @Override
    public Facture findById(String... id) throws SQLException {
        List<Facture> liste = this.find(new RequeteSelectByIdFacture(), id);
        return liste.isEmpty() ? null : liste.get(0);
    }

}
