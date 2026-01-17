package Modele.dao.requetes.Update;

import Modele.Batiment;
import Modele.dao.requetes.Requete;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Requête permettant de mettre à jour un bâtiment dans la table "BATIMENT".
 */
public class RequeteUpdateBatiment implements Requete<Batiment> {

    @Override
    public String requete() {
        return "UPDATE SND5405A.SAE_BATIMENT SET ADRESSE = ?, VILLE = ?, CODE_POSTALE = ?, PERIODEDECONSTRUCTION = ? WHERE ID_BATIMENT = ?";  

    }

    @Override
    public void parametres(PreparedStatement prSt, Batiment b) throws SQLException {
        prSt.setString(1, b.getAdresse());  
        prSt.setString(2, b.getVille());   
        prSt.setString(3, b.getCodePostale());  
        if (b.getPeriodeDeConstruction() != null) {
            prSt.setDate(4, java.sql.Date.valueOf(b.getPeriodeDeConstruction()));
        } else {
            prSt.setNull(4, java.sql.Types.DATE);
        }
        prSt.setString(5, b.getIdBatiment());
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        // Pas utilisé dans ce cas
    }
}
