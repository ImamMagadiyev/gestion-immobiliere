package Modele.dao;

import Modele.Assurance;
import Modele.dao.requetes.Delete.RequeteDeleteAssurance;
import Modele.dao.requetes.Select.RequeteSelectAllAssurance;
import Modele.dao.requetes.Select.RequeteSelectByIdAssurance;
import Modele.dao.requetes.Select.RequeteSelectAssuranceByLogement;
import Modele.dao.requetes.SousProgramme.SousProgramme;
import Modele.dao.requetes.SousProgramme.SousProgrammeInsertAssurance;
import Modele.dao.requetes.Update.RequeteUpdateAssurance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DaoAssurance extends DaoModele<Assurance> implements Dao<Assurance> {

	// Insérer une nouvelle assurance dans la base de données
	@Override
	public void create(Assurance assurance) throws SQLException {
		SousProgramme<Assurance> sousProgramme = new SousProgrammeInsertAssurance();
		this.miseAJour(sousProgramme, assurance);  // Utiliser la méthode générique miseAJour
	}

	// Mettre à jour une assurance dans la base de données
	@Override
	public void update(Assurance assurance) throws SQLException {
		this.miseAJour(new RequeteUpdateAssurance(), assurance);
	}

	//Supprimer une ligne de la table Assurance
	@Override
	public void delete(Assurance obj) throws SQLException {
		this.miseAJour(new RequeteDeleteAssurance(), obj);
	}
	
	// Créer une instance d'Assurance à partir des résultats de la requête
	@Override
	protected Assurance creerInstance(ResultSet curseur) throws SQLException{
		Assurance assurance = null;

		assurance = new Assurance(curseur.getString("id_assurance"),
				curseur.getString("type"),
				curseur.getInt("annee"),
				curseur.getString("numero"));

		return assurance;
	}

	// Trouver toutes les assurances dans la base de données
	@Override
	public List<Assurance> findAll() throws SQLException {
		return this.find(new RequeteSelectAllAssurance());
	}

	// Trouver une assurance par son ID
	@Override
	public Assurance findById(String... id) throws SQLException {
		List<Assurance> assurances = this.find(new RequeteSelectByIdAssurance(), id);
		if (assurances.isEmpty()) {
			return null;
		}
		return assurances.get(0);  // Retourner la première assurance trouvée
	}

	// Trouver les assurances associées à un bien spécifique
	public List<Assurance> findByBien(String bienId) throws SQLException {
		return this.find(new RequeteSelectAssuranceByLogement(), bienId);
	}

	// Trouver une seule assurance associée à un bien spécifique
	public Assurance findByBienSingle(String bienId) throws SQLException {
		List<Assurance> assurances = this.find(new RequeteSelectAssuranceByLogement(), bienId);
		if (assurances.isEmpty()) {
			return null;
		}
		return assurances.get(0);  // Retourner la première assurance trouvée
	}

}
