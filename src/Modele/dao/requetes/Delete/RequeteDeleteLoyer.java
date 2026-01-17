package Modele.dao.requetes.Delete;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Loyer;
import Modele.dao.requetes.Requete;

public class RequeteDeleteLoyer implements Requete<Loyer> {
  @Override
  public String requete() {
    return "DELETE FROM SND5405A.SAE_LOYER "
        + "WHERE ID_LOCATAIRE = ? AND NUMERO_FISCALE = ? AND DATE_PAIEMENT = TO_DATE(?, 'YYYY-MM-DD')";
  }
  @Override
  public void parametres(PreparedStatement prSt, Loyer l) throws SQLException {
    prSt.setString(1, l.getIdLocataire());
    prSt.setString(2, l.getNumero_fiscale());
    prSt.setString(3, l.getDate_paiement().toString());
  }
  @Override public void parametres(PreparedStatement prSt, String... id) throws SQLException { }
}
