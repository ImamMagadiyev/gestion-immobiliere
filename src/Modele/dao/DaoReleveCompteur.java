package Modele.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import Modele.ReleveCompteur;
import Modele.dao.requetes.Select.RequeteSelectAllReleveCompteur;
import Modele.dao.requetes.Select.RequeteSelectByIdReleveCompteur;
import Modele.dao.requetes.SousProgramme.SousProgrammeInsertReleveCompteur;

public class DaoReleveCompteur extends DaoModele<ReleveCompteur> implements Dao<ReleveCompteur> {

    @Override
    public void create(ReleveCompteur r) throws SQLException {
        SousProgrammeInsertReleveCompteur sp = new SousProgrammeInsertReleveCompteur();
        this.miseAJour(sp, r);
    }

    @Override
    public void update(ReleveCompteur r) throws SQLException {
        throw new UnsupportedOperationException("Modification des relevés interdite.");
    }

    @Override
    public void delete(ReleveCompteur r) throws SQLException {
        throw new UnsupportedOperationException("Suppression des relevés interdite.");
    }

    @Override
    protected ReleveCompteur creerInstance(ResultSet rs) throws SQLException {
        return new ReleveCompteur(
            rs.getString("ID_COMPTEUR"),
            rs.getString("DATE_RELEVE"),
            rs.getInt("INDICE")
        );
    }

    @Override
    public List<ReleveCompteur> findAll() throws SQLException {
        return this.find(new RequeteSelectAllReleveCompteur());
    }

    @Override
    public ReleveCompteur findById(String... id) throws SQLException {
        List<ReleveCompteur> liste = this.find(new RequeteSelectByIdReleveCompteur(), id);
        return liste.isEmpty() ? null : liste.get(0);
    }

    public void deleteByIdAndDate(String idCompteur, LocalDate dateReleve) throws SQLException {
        String sql = "DELETE FROM SND5405A.SAE_RELEVE_COMPTEUR WHERE ID_COMPTEUR = ? AND DATE_RELEVE = ?";
        PreparedStatement st = UtOracleDataSource.getConnectionBD().prepareStatement(sql);
        st.setString(1, idCompteur);
        st.setDate(2, Date.valueOf(dateReleve));
        st.executeUpdate();
        st.close();
    }
}
