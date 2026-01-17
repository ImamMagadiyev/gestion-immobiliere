package Modele.dao.requetes.Select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Modele.Caution;
import Modele.dao.requetes.Requete;

public class RequeteSelectByIdCaution implements Requete<Caution> {

	@Override
	public String requete() {
	    return "SELECT ID_CAUTION, NOM, PRENOM, " +
	           "TO_CHAR(DATE_NAISSANCE, 'YYYY-MM-DD') AS DATE_NAISSANCE, " +
	           "ADRESSE, VILLE, CODE_POSTALE, PROFESSION, TY_CONTRAT_TRAVAIL, " +
	           "ID_CONTRAT, ADRESSEELECTRONIQUE, TEL, QUALITE_DU_BAILLEUR, ARCHIVE " +
	           "FROM SND5405A.SAE_CAUTION " +
	           "WHERE ID_CAUTION = ?";
	}


    @Override
    public void parametres(PreparedStatement prSt, Caution c) throws SQLException {
        prSt.setString(1, c.getId_caution());
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        prSt.setString(1, id[0]);
    }
}
