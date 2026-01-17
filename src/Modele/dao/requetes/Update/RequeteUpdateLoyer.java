package Modele.dao.requetes.Update;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Loyer;
import Modele.dao.requetes.Requete;

public class RequeteUpdateLoyer implements Requete<Loyer> {
  @Override
  public String requete() {
    return "UPDATE SND5405A.SAE_LOYER "
        + "SET MONTANT_PROVISION = ?, MONTANT_LOYER = ?, MOIS = ?, MONTANT_REGULARISATION = ? "
        + "WHERE ID_LOCATAIRE = ? AND NUMERO_FISCALE = ? AND DATE_PAIEMENT = TO_DATE(?, 'YYYY-MM-DD') ";
  }
  @Override
  public void parametres(PreparedStatement prSt, Loyer l) throws SQLException {
    prSt.setDouble(1, l.getMontant_provision());
    prSt.setDouble(2, l.getMontant_loyer());
    prSt.setString(3, l.getMois());
    prSt.setDouble(4, l.getMontant_regularisation());
    prSt.setString(5, l.getIdLocataire());
    prSt.setString(6, l.getNumero_fiscale());
    prSt.setString(7, l.getDate_paiement().toString());
  }
  @Override public void parametres(PreparedStatement prSt, String... id) throws SQLException { }
}
