package Modele.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Modele.Agence;
import Modele.dao.requetes.Select.RequeteSelectAllAgence;
import Modele.dao.requetes.Select.RequeteSelectByIdAgence;
import Modele.dao.requetes.Update.RequeteUpdateAgence;
import Modele.dao.requetes.Delete.RequeteDeleteAgence;
import Modele.dao.requetes.SousProgramme.SousProgramme;
import Modele.dao.requetes.SousProgramme.SousProgrammeInsertAgence;


public class DaoAgence extends DaoModele<Agence> implements Dao<Agence> {

	// Insérer une nouvelle agence dans la base de données
	@Override
	public void create(Agence agence) throws SQLException {
		SousProgramme<Agence> sousProgramme = new SousProgrammeInsertAgence();
		this.miseAJour(sousProgramme, agence);  // Utiliser la méthode générique miseAJour
	}

	// Mettre à jour une agence dans la base de données
	@Override
	public void update(Agence agence) throws SQLException {
		this.miseAJour(new RequeteUpdateAgence(), agence);
	}

	//Supprimer une ligne de la table Agence
	@Override
	public void delete(Agence obj) throws SQLException {
		this.miseAJour(new RequeteDeleteAgence(), obj);
	}

	// Créer une instance d'Agence à partir des résultats de la requête
	@Override
	protected Agence creerInstance(ResultSet curseur) throws SQLException{
		Agence agence = null;
		agence = new Agence(curseur.getString("numero"));
		return agence;
	}

	// Trouver toutes les agences dans la base de données
	@Override
	public List<Agence> findAll() throws SQLException {
		return this.find(new RequeteSelectAllAgence());
	}

	// Trouver une agence par son ID
	@Override
	public Agence findById(String... id) throws SQLException {
		List<Agence> agence = this.find(new RequeteSelectByIdAgence(), id);
		if (agence.isEmpty()) {
			return null;
		}
		return agence.get(0); // Retourner la première agence trouvée
	}

}
