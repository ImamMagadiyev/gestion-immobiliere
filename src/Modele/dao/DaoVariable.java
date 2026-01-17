package Modele.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Modele.Variable;
import Modele.dao.requetes.Select.RequeteSelectAllVariable;
import Modele.dao.requetes.Select.RequeteSelectByIdVariable;
import Modele.dao.requetes.Update.RequeteUpdateVariable;
import Modele.dao.requetes.Delete.RequeteDeleteVariable;
import Modele.dao.requetes.SousProgramme.SousProgramme;
import Modele.dao.requetes.SousProgramme.SousProgrammeInsertVariable;

public class DaoVariable extends DaoModele<Variable> implements Dao<Variable> {

    @Override
    public void create(Variable v) throws SQLException {
        SousProgramme<Variable> sp = new SousProgrammeInsertVariable();
        this.miseAJour(sp, v);
    }

    @Override
    public void update(Variable v) throws SQLException {
        this.miseAJour(new RequeteUpdateVariable(), v);
    }

    @Override
    public void delete(Variable v) throws SQLException {
        this.miseAJour(new RequeteDeleteVariable(), v);
    }

    @Override
    protected Variable creerInstance(ResultSet rs) throws SQLException {
        return new Variable(
            rs.getString("ID_VARIABLE"),
            rs.getString("SERVICE"),
            rs.getString("FOURNISSEUR"),
            rs.getString("EAU_GAZ_ELECTRICITE"),
            rs.getString("NUMERO"),
            rs.getDouble("PRIX_UNITAIRE")
        );
    }

    @Override
    public List<Variable> findAll() throws SQLException {
        return this.find(new RequeteSelectAllVariable());
    }

    @Override
    public Variable findById(String... id) throws SQLException {
        List<Variable> liste = this.find(new RequeteSelectByIdVariable(), id);
        return liste.isEmpty() ? null : liste.get(0);
    }
}
