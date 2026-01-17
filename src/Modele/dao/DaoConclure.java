package Modele.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Modele.Conclure;
import Modele.dao.requetes.Delete.RequeteDeleteConclure;
import Modele.dao.requetes.Select.RequeteSelectAllConclure;
import Modele.dao.requetes.Select.RequeteSelectByIdConclure;
import Modele.dao.requetes.SousProgramme.SousProgramme;
import Modele.dao.requetes.SousProgramme.SousProgrammeInsertConclure;
import Modele.dao.requetes.Update.RequeteUpdateConclure;

public class DaoConclure extends DaoModele<Conclure> implements Dao<Conclure> {

	@Override
	public void create(Conclure c) throws SQLException {
		SousProgramme<Conclure> sp = new SousProgrammeInsertConclure();
		this.miseAJour(sp, c);
	}

	@Override
	public void update(Conclure c) throws SQLException {
		this.miseAJour(new RequeteUpdateConclure(), c);
	}

	@Override
	public void delete(Conclure c) throws SQLException {
		this.miseAJour(new RequeteDeleteConclure(), c);
	}

	@Override
	protected Conclure creerInstance(ResultSet rs) throws SQLException {
		return new Conclure(
				rs.getString("ID_LOCATAIRE"),
				rs.getString("ID_CONTRAT")
				);
	}

	@Override
	public List<Conclure> findAll() throws SQLException {
		return this.find(new RequeteSelectAllConclure());
	}

	@Override
	public Conclure findById(String... id) throws SQLException {
		List<Conclure> liste = this.find(new RequeteSelectByIdConclure(), id);
		return liste.isEmpty() ? null : liste.get(0);
	}
}
