package Modele.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Modele.Charges;
import Modele.dao.requetes.Delete.RequeteDeleteCharges;
import Modele.dao.requetes.Select.RequeteSelectAllCharges;
import Modele.dao.requetes.Select.RequeteSelectByIdCharges;
import Modele.dao.requetes.SousProgramme.SousProgramme;
import Modele.dao.requetes.SousProgramme.SousProgrammeInsertCharges;
import Modele.dao.requetes.Update.RequeteUpdateCharges;

public class DaoCharges extends DaoModele<Charges> implements Dao<Charges> {

    @Override
    public void create(Charges c) throws SQLException {
        SousProgramme<Charges> sp = new SousProgrammeInsertCharges();
        this.miseAJour(sp, c);
    }

    @Override
    public void update(Charges c) throws SQLException {
        this.miseAJour(new RequeteUpdateCharges(), c);
    }

    @Override
    public void delete(Charges c) throws SQLException {
        this.miseAJour(new RequeteDeleteCharges(), c);
    }

    @Override
    protected Charges creerInstance(ResultSet rs) throws SQLException {
        try {
            String idCharges = rs.getString("ID_CHARGES");
            int annee = rs.getInt("ANNEE");
            double taxeOrdinaire = rs.getDouble("TAXE_ORDINAIRES");
            double taxesAutres = rs.getDouble("TAXES_AUTRES");
            String type = rs.getString("TYPE");
            return new Charges(idCharges, annee, taxeOrdinaire, taxesAutres, type);
        } catch (SQLException e) {
            try {
                String idCharges = rs.getString("ID_CHARGES");
               
                
                int annee = rs.getInt("ANNEE");
                double taxeOrdinaire = Double.parseDouble(rs.getString("TAXE_ORDINAIRES").trim());
                double taxesAutres = Double.parseDouble(rs.getString("TAXES_AUTRES").trim());
                String type = rs.getString("TYPE");
                return new Charges(idCharges, annee, taxeOrdinaire, taxesAutres, type);
            } catch (Exception parseError) {
                throw new SQLException("Failed to create Charges instance", parseError);
            }
        }
    }

    @Override
    public List<Charges> findAll() throws SQLException {
        return this.find(new RequeteSelectAllCharges());
    }

    @Override
    public Charges findById(String... id) throws SQLException {
        List<Charges> liste = this.find(new RequeteSelectByIdCharges(), id);
        return liste.isEmpty() ? null : liste.get(0);
    }
}