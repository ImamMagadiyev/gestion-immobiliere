package Modele.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Modele.CompteurLot;
import Modele.dao.requetes.Select.RequeteSelectAllCompteurLot;

public class DaoCompteurLot extends DaoModele<CompteurLot> implements Dao<CompteurLot> {

	@Override
	public void create(CompteurLot compteurLot) throws SQLException {
		// Vue en lecture seule
	}

	@Override
	public void update(CompteurLot compteurLot) throws SQLException {
		// Vue en lecture seule
	}

	@Override
	public void delete(CompteurLot compteurLot) throws SQLException {
		// Vue en lecture seule
	}

	@Override
	protected CompteurLot creerInstance(ResultSet rs) throws SQLException {
		return new CompteurLot(
			rs.getString("NUMERO_FISCALE"),
			rs.getString("ID_BATIMENT"),
			rs.getString("ID_COMPTEUR"),
			rs.getDate("DATE_RELEVE").toLocalDate(),
			rs.getString("TYPE_COMPTEUR"),
			rs.getDouble("QUOTITE"),
			rs.getDouble("PRIX_UNITAIRE"),
			rs.getDouble("INDICE_LOT"),
			rs.getDouble("CONSOMMATION"),
			rs.getDouble("PRIX_TOTAL")
		);
	}

	@Override
	public List<CompteurLot> findAll() throws SQLException {
		return this.find(new RequeteSelectAllCompteurLot());
	}

	@Override
	public CompteurLot findById(String... id) throws SQLException {
		// Vue en lecture seule
		return null;
	}

}
