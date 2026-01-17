package Modele.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Modele.Diagnostic;
import Modele.dao.requetes.Select.RequeteSelectAllDiagnostic;
import Modele.dao.requetes.Select.RequeteSelectAllDiagnosticArchives;
import Modele.dao.requetes.Archiver.RequeteArchiverDiagnostic;
import Modele.dao.requetes.Restaurer.RequeteRestaurerDiagnostic;
import Modele.dao.requetes.Select.RequeteSelectByIdDiagnostic;
import Modele.dao.requetes.Update.RequeteUpdateDiagnostic;
import Modele.dao.requetes.Delete.RequeteDeleteDiagnostic;
import Modele.dao.requetes.SousProgramme.SousProgramme;
import Modele.dao.requetes.SousProgramme.SousProgrammeInsertDiagnostic;

public class DaoDiagnostic extends DaoModele<Diagnostic> implements Dao<Diagnostic> {

    @Override
    public void create(Diagnostic d) throws SQLException {
        SousProgramme<Diagnostic> sp = new SousProgrammeInsertDiagnostic();
        this.miseAJour(sp, d);
    }

    @Override
    public void update(Diagnostic d) throws SQLException {
        this.miseAJour(new RequeteUpdateDiagnostic(), d);
    }

    @Override
    public void delete(Diagnostic d) throws SQLException {
        this.miseAJour(new RequeteDeleteDiagnostic(), d);
    }
    
    public void archiver(Diagnostic d) throws SQLException {
    	this.miseAJour(new RequeteArchiverDiagnostic(), d);
    }
    
    public void archiverById(String reference) throws SQLException {
    	this.miseAJour(new RequeteArchiverDiagnostic(), reference);
    }
    
    public void restaurer(Diagnostic d) throws SQLException {
    	this.miseAJour(new RequeteRestaurerDiagnostic(), d);
    }
    
    public void restaurerById(String reference) throws SQLException {
    	this.miseAJour(new RequeteRestaurerDiagnostic(), reference);
    }

    @Override
    protected Diagnostic creerInstance(ResultSet rs) throws SQLException {
        return new Diagnostic(
            rs.getString("REFERENCE"),
            rs.getDate("DATE_VALIDITE").toLocalDate(),
            rs.getString("TYPE_DIAGNOSTIC"),
            rs.getString("NUMERO_FISCALE"),
            rs.getString("SIRET"),
            rs.getInt("ARCHIVE") == 1
        );
    }

    @Override
    public List<Diagnostic> findAll() throws SQLException {
        return this.find(new RequeteSelectAllDiagnostic());
    }
    
    public List<Diagnostic> findAllArchiver() throws SQLException {
        return this.find(new RequeteSelectAllDiagnosticArchives());
    }

    @Override
    public Diagnostic findById(String... id) throws SQLException {
        List<Diagnostic> liste = this.find(new RequeteSelectByIdDiagnostic(), id);
        return liste.isEmpty() ? null : liste.get(0);
    }
}
