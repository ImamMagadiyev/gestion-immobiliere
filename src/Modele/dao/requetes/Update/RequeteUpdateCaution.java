package Modele.dao.requetes.Update;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modele.Caution;
import Modele.dao.requetes.Requete;

public class RequeteUpdateCaution implements Requete<Caution> {

    @Override
    public String requete() {
        return "UPDATE SND5405A.SAE_CAUTION "
             + "SET NOM = ?, PRENOM = ?, DATE_NAISSANCE = TO_DATE(?, 'YYYY-MM-DD'), "
             + "ADRESSE = ?, VILLE = ?, CODE_POSTALE = ?, PROFESSION = ?, "
             + "TY_CONTRAT_TRAVAIL = ?, ID_CONTRAT = ?, ADRESSEELECTRONIQUE = ?, "
             + "TEL = ?, QUALITE_DU_BAILLEUR = ? "
             + "WHERE ID_CAUTION = ?";
    }

    @Override
    public void parametres(PreparedStatement prSt, Caution c) throws SQLException {
        prSt.setString(1, c.getNom());
        prSt.setString(2, c.getPrenom());
        prSt.setString(3, c.getDate_naissance()); // TO_DATE in SQL handles conversion
        prSt.setString(4, c.getAdresse());
        prSt.setString(5, c.getVille());
        prSt.setString(6, c.getCode_postale());
        prSt.setString(7, c.getProfession());
        prSt.setString(8, c.getType_contrat_travail());
        prSt.setString(9, c.getIdContrat());
        prSt.setString(10, c.getAdresseElectronique());
        prSt.setString(11, c.getNumeroTel());
        prSt.setString(12, c.getQualiteBailleur());
        prSt.setString(13, c.getId_caution()); // last parameter is WHERE ID_CAUTION
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException { }
}
