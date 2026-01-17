package Modele.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import Modele.Loyer;
import Modele.dao.requetes.Select.RequeteSelectAllLoyer;
import Modele.dao.requetes.Select.RequeteSelectAllLoyerArchives;
import Modele.dao.requetes.Archiver.RequeteArchiverLoyer;
import Modele.dao.requetes.Restaurer.RequeteRestaurerLoyer;
import Modele.dao.requetes.Select.RequeteSelectByIdLoyer;
import Modele.dao.requetes.Update.RequeteUpdateLoyer;
import Modele.dao.requetes.Delete.RequeteDeleteLoyer;
import Modele.dao.requetes.SousProgramme.SousProgramme;
import Modele.dao.requetes.SousProgramme.SousProgrammeInsertLoyer;

public class DaoLoyer extends DaoModele<Loyer> implements Dao<Loyer> {

    @Override
    public void create(Loyer l) throws SQLException {
        SousProgramme<Loyer> sp = new SousProgrammeInsertLoyer();
        this.miseAJour(sp, l);
    }

    @Override
    public void update(Loyer l) throws SQLException {
        this.miseAJour(new RequeteUpdateLoyer(), l);
    }

    @Override
    public void delete(Loyer l) throws SQLException {
        this.miseAJour(new RequeteDeleteLoyer(), l);
    }

    public void archiver(Loyer l) throws SQLException {
        this.miseAJour(new RequeteArchiverLoyer(), l);
    }

    public void archiverById(String idLocataire, String numeroFiscale, LocalDate datePaiement) throws SQLException {
        RequeteArchiverLoyer requete = new RequeteArchiverLoyer();
        Date sqlDate = Date.valueOf(datePaiement);
        this.miseAJour(requete, idLocataire, numeroFiscale, sqlDate);
    }

    public void restaurer(Loyer l) throws SQLException {
        this.miseAJour(new RequeteArchiverLoyer(), l);
    }

    public void restaurerById(String idLocataire) throws SQLException {
        this.miseAJour(new RequeteRestaurerLoyer(), idLocataire);
    }

    @Override
    protected Loyer creerInstance(ResultSet rs) throws SQLException {
        boolean archive;
        try {
            archive = rs.getInt("ARCHIVE") == 1;
            return new Loyer(
                    rs.getString("ID_LOCATAIRE"),
                    rs.getString("NUMERO_FISCALE"),
                    rs.getDate("DATE_PAIEMENT").toLocalDate(),
                    rs.getDouble("MONTANT_PROVISION"),
                    rs.getDouble("MONTANT_LOYER"),
                    rs.getString("MOIS"),
                    rs.getDouble("MONTANT_REGULARISATION"),
                    archive);
        } catch (SQLException e) {
            String archiveStr = rs.getString("ARCHIVE").trim();
            archive = archiveStr.equals("1") || archiveStr.equalsIgnoreCase("true") || archiveStr.equalsIgnoreCase("Y");
            return new Loyer(
                    rs.getString("ID_LOCATAIRE"),
                    rs.getString("NUMERO_FISCALE"),
                    rs.getDate("DATE_PAIEMENT").toLocalDate(),
                    Double.parseDouble(rs.getString("MONTANT_PROVISION").trim()),
                    Double.parseDouble(rs.getString("MONTANT_LOYER").trim()),
                    rs.getString("MOIS"),
                    Double.parseDouble(rs.getString("MONTANT_REGULARISATION").trim()),
                    archive);
        }
    }

    @Override
    public List<Loyer> findAll() throws SQLException {
        return this.find(new RequeteSelectAllLoyer());
    }

    public List<Loyer> findAllArchiver() throws SQLException {
        return this.find(new RequeteSelectAllLoyerArchives());
    }

    @Override
    public Loyer findById(String... id) throws SQLException {
        List<Loyer> liste = this.find(new RequeteSelectByIdLoyer(), id);
        return liste.isEmpty() ? null : liste.get(0);
    }
}
