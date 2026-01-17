package Modele.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Modele.ChargesLot;
import Modele.dao.requetes.Select.RequeteSelectAllChargesLot;

public class DaoChargesLot extends DaoModele<ChargesLot> implements Dao<ChargesLot> {

	@Override
	public void create(ChargesLot chargesLot) throws SQLException {
		// Vue en lecture seule
	}

	@Override
	public void update(ChargesLot chargesLot) throws SQLException {
		// Vue en lecture seule
	}

	@Override
	public void delete(ChargesLot chargesLot) throws SQLException {
		// Vue en lecture seule
	}

	@Override
	protected ChargesLot creerInstance(ResultSet rs) throws SQLException {
		return new ChargesLot(
			rs.getString("NUMERO_FISCALE"),
			rs.getInt("ANNEE"),
			rs.getString("TYPE"),
			rs.getDouble("TAXE_ORDINAIRES_LOT"),
			rs.getDouble("TAXES_AUTRES_LOT"),
			rs.getDouble("TAXE_OM_LOT")
		);
	}

	@Override
	public List<ChargesLot> findAll() throws SQLException {
		return this.find(new RequeteSelectAllChargesLot());
	}

	@Override
	public ChargesLot findById(String... id) throws SQLException {
		// Vue en lecture seule
		return null;
	}

}
