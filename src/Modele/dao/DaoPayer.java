package Modele.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Modele.Payer;
import Modele.dao.requetes.Select.RequeteSelectAllPayer;
import Modele.dao.requetes.Select.RequeteSelectByIdPayer;
import Modele.dao.requetes.Update.RequeteUpdatePayer;
import Modele.dao.requetes.Delete.RequeteDeletePayer;
import Modele.dao.requetes.SousProgramme.SousProgramme;
import Modele.dao.requetes.SousProgramme.SousProgrammeInsertPayer;

/**
 * En vrai, ce Dao doit nous permettre de faire que des select et insert,
 * mais pas des modifications et suppression des lignes, parce qu'une fois qu'on reçoit
 * un paiement on ne doit pas pouvoir le modifié ni le supprimer
 * (il peut meme y a voir des sanctions de lois)
 * On en discutera avec les profs lors du Sprint (le 08/12/2025)
 */

public class DaoPayer extends DaoModele<Payer> implements Dao<Payer> {

    @Override
    public void create(Payer p) throws SQLException {
        SousProgramme<Payer> sp = new SousProgrammeInsertPayer();
        this.miseAJour(sp, p);
    }

    @Override
    public void update(Payer p) throws SQLException {
        this.miseAJour(new RequeteUpdatePayer(), p);
    }

    @Override
    public void delete(Payer p) throws SQLException {
        this.miseAJour(new RequeteDeletePayer(), p);
    }

    @Override
    protected Payer creerInstance(ResultSet rs) throws SQLException {
        return new Payer(
            rs.getString("ID_CHARGES"),
            rs.getString("ID_LOCATAIRE")
        );
    }

    @Override
    public List<Payer> findAll() throws SQLException {
        return this.find(new RequeteSelectAllPayer());
    }

    @Override
    public Payer findById(String... id) throws SQLException {
        List<Payer> liste = this.find(new RequeteSelectByIdPayer(), id);
        return liste.isEmpty() ? null : liste.get(0);
    }
}
