package Modele.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Modele.CompteurBien;
import Modele.dao.requetes.Select.RequeteSelectAllCompteurBien;

public class DaoCompteurBien extends DaoModele<CompteurBien> implements Dao<CompteurBien> {

	@Override
	public void create(CompteurBien compteurBien) throws SQLException {
		// Vue en lecture seule
	}

	@Override
	public void update(CompteurBien compteurBien) throws SQLException {
		// Vue en lecture seule
	}

	@Override
	public void delete(CompteurBien compteurBien) throws SQLException {
		// Vue en lecture seule
	}

	@Override
	protected CompteurBien creerInstance(ResultSet rs) throws SQLException {
		return new CompteurBien(
			rs.getString("NUMERO_FISCALE"),
			rs.getInt("INDICE"),
			rs.getInt("INDEX_VAL"),
			rs.getDouble("PRIX_UNITAIRE"),
			rs.getString("TYPE_"),
			rs.getDouble("CONSOMMATION"),
			rs.getDouble("PRIX_TOTAL")
		);
	}

	@Override
	public List<CompteurBien> findAll() throws SQLException {
		return this.find(new RequeteSelectAllCompteurBien());
	}

	@Override
	public CompteurBien findById(String... id) throws SQLException {
		// Vue en lecture seule
		return null;
	}

}
