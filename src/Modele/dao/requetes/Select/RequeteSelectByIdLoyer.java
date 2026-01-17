package Modele.dao.requetes.Select;


import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import Modele.Loyer;
import Modele.dao.requetes.Requete;

public class RequeteSelectByIdLoyer implements Requete<Loyer> {

	@Override
	public String requete() {
	    return "SELECT id_locataire, numero_fiscale, TO_CHAR(DATE_PAIEMENT, 'YYYY-MM-DD') AS DATE_PAIEMENT, " +
	           "montant_provision, montant_loyer, mois, montant_regularisation, archive " +
	           "FROM SND5405A.SAE_LOYER " +
	           "WHERE ID_LOCATAIRE = ? AND NUMERO_FISCALE = ? AND DATE_PAIEMENT = TO_DATE(?, 'YYYY-MM-DD') " +
	           "ORDER BY 3";
	}


	@Override
	public void parametres(PreparedStatement prSt, Loyer l) throws SQLException {
		prSt.setString(1, l.getIdLocataire());
		prSt.setString(2, l.getNumero_fiscale());
		prSt.setString(3, l.getDate_paiement().toString());
	}
	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]); 
		prSt.setString(2, id[1]); 
		prSt.setString(3, id[2]);	
        
	}

}

