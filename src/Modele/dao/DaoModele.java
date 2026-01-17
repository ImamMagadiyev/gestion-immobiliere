package Modele.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Modele.Loyer;
import Modele.dao.requetes.Requete;
import Modele.dao.requetes.Archiver.RequeteArchiverLoyer;
import Modele.dao.requetes.SousProgramme.SousProgramme;

/**
 * Classe abstraite générique servant de base pour tous les DAO.
 *
 * @param <T> type d'objet métier géré par le DAO
 */
public abstract class DaoModele<T> implements Dao<T> {

    // Connexion partagée
    protected Connection connection;

    /**
     * Crée une instance d'objet métier à partir d'un ResultSet.
     * Chaque DAO doit implémenter cette méthode.
     */
   protected abstract T creerInstance(ResultSet curseur) throws SQLException;

    /**
     * Méthode générique pour exécuter une requête SELECT.
     */
    protected List<T> select(PreparedStatement st) throws SQLException {
        ResultSet rs = st.executeQuery();
        List<T> liste = new ArrayList<>();
        while (rs.next()) {
            liste.add(creerInstance(rs));
        }
        rs.close();
        return liste;
    }

    /**
     * Méthode générique pour exécuter une requête d'insertion, mise à jour ou suppression.
     */
    protected int miseAJour(Requete<T> requete, T objet) throws SQLException {
        PreparedStatement st = UtOracleDataSource.getConnectionBD().prepareStatement(requete.requete());
        requete.parametres(st, objet);
        int nbLignes = st.executeUpdate(); // validé automatiquement
        st.close();
        return nbLignes;
    }
    
    protected int miseAJour(Requete<Loyer> requete, String idLocataire, String numeroFiscale, Date datePaiement) throws SQLException {
        PreparedStatement st = UtOracleDataSource.getConnectionBD().prepareStatement(requete.requete());

        if (requete instanceof RequeteArchiverLoyer) {
            ((RequeteArchiverLoyer) requete).parametres(st, idLocataire, numeroFiscale, datePaiement);
        } else {
            throw new SQLException("La requête ne supporte pas cette surcharge de paramètres");
        }

        int nbLignes = st.executeUpdate();
        st.close();
        return nbLignes;
    }


    
    /**
     * Mise à jour générique avec paramètres simples (UPDATE / DELETE par ID)
     */
    protected int miseAJour(Requete<T> requete, String... params) throws SQLException {
        PreparedStatement st =
            UtOracleDataSource.getConnectionBD().prepareStatement(requete.requete());

        requete.parametres(st, params);

        int nbLignes = st.executeUpdate();
        st.close();
        return nbLignes;
    }



    /**
	 * Méthode qui est appelé pour des insertions de lignes dans la base de données
	 * @param sP
	 * @param objet
	 * @return int
	 * @throws SQLException
	 */
	protected int miseAJour(SousProgramme<T> sP, T objet) throws SQLException {
		CallableStatement cst = null;
		cst = UtOracleDataSource.getConnectionBD().prepareCall(sP.appelSousProgramme());
		sP.parametres(cst,  objet);
		
		// 🔍 DEBUG: Afficher l'objet envoyé
		System.out.println("[DEBUG] Objet envoyé à la BDD: " + objet);
		
		try {
			int nbLignes = cst.executeUpdate();
			cst.close();
			return nbLignes;
		} catch (SQLException e) {
			System.err.println("[ERROR] Erreur SQL lors de la mise à jour: " + e.getMessage());
			System.err.println("[ERROR] Objet problématique: " + objet);
			cst.close();
			throw e;
		}
	}


    /**
     * Méthode générique pour exécuter une requête de recherche avec paramètres.
     */
    public List<T> find(Requete<T> requete, String... params) throws SQLException {
        PreparedStatement st = UtOracleDataSource.getConnectionBD().prepareStatement(requete.requete());
        requete.parametres(st, params);
        List<T> liste = select(st);
        st.close();
        return liste;
    }

    /**
     * Méthode générique pour rechercher un objet par ID.
     */
    public T findById(Requete<T> requete, String... params) throws SQLException {
        List<T> liste = find(requete, params);
        if (liste.isEmpty()) return null;
        return liste.get(0);
    }
}
