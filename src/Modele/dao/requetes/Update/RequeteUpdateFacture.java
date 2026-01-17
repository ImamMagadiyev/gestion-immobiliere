package Modele.dao.requetes.Update;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Facture;
import Modele.dao.requetes.Requete;

public class RequeteUpdateFacture implements Requete<Facture> {

	@Override
	public String requete() {    	
		return "UPDATE SND5405A.SAE_FACTURE "
				+ "SET DATE_FACTURE = ?, TYPE_FACTURE = ?, MONTANT = ?, MODE_PAIEMENT = ?, "
				+ "NUMERO_DEVIS = ?, NATURE = ?, DATE_DEVIS = ?, PAYER_PAR_LOCATAIRE = ?, "
				+ "SIRET = ?, NUMERO_FISCALE = ? "
				+ "WHERE NUMERO = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, Facture f) throws SQLException {
		prSt.setDate(1, f.getDate_facture());
		prSt.setString(2, f.getType_travaux());
		prSt.setDouble(3, f.getMontant());
		prSt.setString(4, f.getMode_paiement());
		prSt.setString(5, f.getNumero_devis());
		prSt.setString(6, f.getNature());
		prSt.setDate(7, f.getDate_devis());
		prSt.setBoolean(8, f.getPayer_par_locataire());
		prSt.setString(9, f.getSiret());
		prSt.setString(10, f.getNumero_fiscale());
		prSt.setString(11, f.getNumero());
	}

	@Override public void parametres(PreparedStatement prSt, String... id) throws SQLException { }
}