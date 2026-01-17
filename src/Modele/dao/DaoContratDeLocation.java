package Modele.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Modele.ContratDeLocation;
import Modele.dao.requetes.Archiver.RequeteArchiverContratDeLocation;
import Modele.dao.requetes.Delete.RequeteDeleteContratDeLocation;
import Modele.dao.requetes.Restaurer.RequeteRestaurerContratDeLocation;
import Modele.dao.requetes.Select.RequeteSelectAllContratDeLocation;
import Modele.dao.requetes.Select.RequeteSelectAllContratDeLocationArchives;
import Modele.dao.requetes.Select.RequeteSelectByIdContratDeLocation;
import Modele.dao.requetes.Select.RequeteSelectByIdLocataireContratDeLocation;
import Modele.dao.requetes.SousProgramme.SousProgramme;
import Modele.dao.requetes.SousProgramme.SousProgrammeInsertContratDeLocation;
import Modele.dao.requetes.Update.RequeteUpdateContratDeLocation;

public class DaoContratDeLocation
        extends DaoModele<ContratDeLocation>
        implements Dao<ContratDeLocation> {

    @Override
    public void create(ContratDeLocation contrat) throws SQLException {
        SousProgramme<ContratDeLocation> sp =
                new SousProgrammeInsertContratDeLocation();
        this.miseAJour(sp, contrat);
    }

    @Override
    public void update(ContratDeLocation contrat) throws SQLException {
        this.miseAJour(new RequeteUpdateContratDeLocation(), contrat);
    }

    @Override
    public void delete(ContratDeLocation contrat) throws SQLException {
        this.miseAJour(new RequeteDeleteContratDeLocation(), contrat);
    }

    // Archivage logique
    public void archiver(ContratDeLocation contrat) throws SQLException {
        this.miseAJour(new RequeteArchiverContratDeLocation(), contrat);
    }

    public void archiverById(String idContrat) throws SQLException {
        this.miseAJour(new RequeteArchiverContratDeLocation(), idContrat);
    }
    
    // Restauration logique
    public void restaurer(ContratDeLocation contrat) throws SQLException {
    	this.miseAJour(new RequeteRestaurerContratDeLocation(), contrat);
    }
    
    public void restaurerById(String idContrat) throws SQLException {
    	this.miseAJour(new RequeteRestaurerContratDeLocation(), idContrat);
    }
    
    // Mapping ResultSet → ContratDeLocation
    @Override
    protected ContratDeLocation creerInstance(ResultSet rs) throws SQLException {
        int trimestreNum = rs.getInt("TRIMESTRE");
        String trimestreStr = convertNumberToTrimestre(trimestreNum);
        
        return new ContratDeLocation(
            rs.getString("ID_CONTRAT"),
            rs.getString("DATE_DEBUT"),
            trimestreStr,
            rs.getString("DATE_SORTIE"),
            rs.getDouble("LOYER_APAYER"),
            rs.getDouble("IRL"),
            rs.getDouble("PROVISIONS_CHARGES"),
            rs.getBoolean("SOLDE_TOUT_COMPTE_EFFECTUE"),
            rs.getInt("DUREE"),
            rs.getString("NUMERO_FISCALE"),
            rs.getString("ID_LOCATAIRE"),
            rs.getInt("ARCHIVE") == 1
        );
    }

    private String convertNumberToTrimestre(int numero) {
        switch (numero) {
            case 1: return "T1";
            case 2: return "T2";
            case 3: return "T3";
            case 4: return "T4";
            default: return "T1";
        }
    }

    @Override
    public List<ContratDeLocation> findAll() throws SQLException {
        return this.find(new RequeteSelectAllContratDeLocation());
    }

    public List<ContratDeLocation> findAllArchive() throws SQLException {
        return this.find(new RequeteSelectAllContratDeLocationArchives());
    }

    @Override
    public ContratDeLocation findById(String... id) throws SQLException {
        List<ContratDeLocation> liste =
                this.find(new RequeteSelectByIdContratDeLocation(), id);
        return liste.isEmpty() ? null : liste.get(0);
    }


    // Récupère le dernier contrat actif pour un locataire
    public ContratDeLocation findByIdLocataire(String idLocataire) throws SQLException {
        List<ContratDeLocation> liste =
                this.find(new RequeteSelectByIdLocataireContratDeLocation(), idLocataire);
        return liste.isEmpty() ? null : liste.get(0);
    }

}
