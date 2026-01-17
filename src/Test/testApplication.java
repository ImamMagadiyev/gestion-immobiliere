package Test;

import java.sql.Date;
import java.sql.SQLException;

import org.junit.Before;

import Modele.Facture;
import Modele.dao.DaoFacture;
import Vue.ajouter.FenetreAjouterFacture;

public class testApplication {
	
	private Facture facture;
	private DaoFacture daoFacture;
	
	@Before
	
	public void setUp() throws SQLException{
		daoFacture = new DaoFacture();
		facture = new Facture("FAC005",Date.valueOf("2025-12-12"),"Variable",15.5,"CB","DEV001","Consommatio ",
				Date.valueOf("2025-10-10"),true,"SIRET001","BF005",25.0,false);
		daoFacture.create(facture);
	}
}
