package Modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;


public interface Requete<T> {

	public abstract String requete();

	public abstract void parametres(PreparedStatement prSt, String... id) throws SQLException;

	public abstract void parametres(PreparedStatement prSt, T data) throws SQLException;
}
