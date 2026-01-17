package Controleur;



import java.awt.Component;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import Modele.Assurance;
import Modele.Batiment;
import Modele.BienLouable;
import Modele.Caution;
import Modele.Compteur;
import Modele.ContratDeLocation;
import Modele.Diagnostic;
import Modele.Entreprise;
import Modele.Facture;
import Modele.Impots;
import Modele.Locataire;
import Modele.Loyer;
import Modele.Travaux;
import Modele.Variable;
import Modele.dao.DaoAssurance;
import Modele.dao.DaoBatiment;
import Modele.dao.DaoBienLouable;
import Modele.dao.DaoCaution;
import Modele.dao.DaoCompteur;
import Modele.dao.DaoContratDeLocation;
import Modele.dao.DaoDiagnostic;
import Modele.dao.DaoEntreprise;
import Modele.dao.DaoFacture;
import Modele.dao.DaoImpots;
import Modele.dao.DaoLocataire;
import Modele.dao.DaoLoyer;
import Modele.dao.DaoTravaux;
import Modele.dao.DaoVariable;
import Vue.Affichage.FenetreAffichageDetailsBienLouable;
import Vue.Affichage.FenetreAfficherCaution;
import Vue.Affichage.FenetreAfficherCompteur;
import Vue.Affichage.FenetreAfficherDiagnostics;
import Vue.Calculer.CalculerIRL;
import Vue.Calculer.FenetreCalculerCharges;
import Vue.FenetreConnexion;
import Vue.FenetrePrincipale;
import Vue.Modification.FenetreModifieFacture;
import Vue.Modification.FenetreModifierBatiment;
import Vue.Modification.FenetreModifierBienLouable;
import Vue.Modification.FenetreModifierContratDeLocation;
import Vue.Modification.FenetreModifierEntreprise;
import Vue.Modification.FenetreModifierLocataire;
import Vue.Modification.FenetreModifierLocation;
import Vue.Utils;
import Vue.ajouter.FenetreAjouterBatiment;
import Vue.ajouter.FenetreAjouterBienLouable;
import Vue.ajouter.FenetreAjouterContratDeLocation;
import Vue.ajouter.FenetreAjouterEntreprise;
import Vue.ajouter.FenetreAjouterFacture;
import Vue.ajouter.FenetreAjouterLocation;
import Vue.ajouter.FenetreLocataireAjouter;


/**
 * Classe pour le contrôleur pour la page principale
 */

public class GestionFenetrePrincipale implements ActionListener {

	private FenetrePrincipale fp;

	// Constructeur prenant en paramètre la fenêtre de connexion
	public GestionFenetrePrincipale(FenetrePrincipale fp) {
		this.fp = fp;
	}

	/**
	 * Méthode appelée à chaque clic de bouton sur la page principale
	 * Pour chaque boutons cliqué, elle exécute l'action appropriée
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		//Récupérer le bouton cliqué
		Object src = e.getSource();
		if (!(src instanceof Component)) {
			return;
		}

		//Récupérer le nom du bouton cliqué
		String name = ((Component) src).getName();

		//Si le bouton n'as pas de nom le clique est ignorer
		if (name == null) {
			return;
		}

		switch (name) {

		// =================================================
		// MENU PRINCIPALE
		// =================================================
		case "btnAccueil":
			//Afficher la page d'acceuil
			fp.montrerPage("ACCUEIL");
			break;

		case "btnBiens":
			// Afficher la page des biens louables et des bâtiments
			fp.montrerPage("BIENS");
			break;

		case "btnLocations":
			// Afficher la page des loyers
			afficherTableauLoyer(); // Charger les données
			fp.montrerPage("LOCATIONS");
			break;

		case "btnBaux":
			// Afficher la page des baux
			afficherTableauBaux(); // Charger les données
			fp.montrerPage("BAUX");
			break;

		case "btnEntreprises":
			// Afficher la page des entreprises
			afficherTableauEntreprise(); // Charger les données
			fp.montrerPage("ENTREPRISES");
			break;

		case "btnFactures":
			// Afficher la page des factures
			afficherTableFactures(); // Charger les données
			fp.montrerPage("FACTURES");
			break;

		case "btnTravaux":
			// Afficher la page des travaux
			fp.montrerPage("TRAVAUX");
			break;

		case "btnLocataires":
			// Afficher la page des locataires
			afficherTableauLocataire(); // Charger les données
			fp.montrerPage("LOCATAIRES");
			break;

		case "btnArchives":
			// Afficher la page des archives
			fp.montrerPage("ARCHIVES");
			break;

		case "btnImporterCSV":
			FileDialog dialog = new FileDialog((Frame) null, "Importer un fichier CSV", FileDialog.LOAD);
			dialog.setFile("*.csv");
			dialog.setVisible(true);


			String fichier = dialog.getFile();
			String dossier = dialog.getDirectory();

			if (fichier != null) {
				File selectedFile = new File(dossier, fichier);
				System.out.println("CSV sélectionné : " + selectedFile.getAbsolutePath());

				DaoLoyer daoLoyer = new DaoLoyer();

				try (BufferedReader br = new BufferedReader(
						new InputStreamReader(new FileInputStream(selectedFile), StandardCharsets.UTF_8))) {

					String line;
					boolean premiereLigne = true;

					while ((line = br.readLine()) != null) {
						if (premiereLigne) {
							premiereLigne = false;
							continue;
						}

						String[] valeurs = line.split(";");

						if (valeurs.length < 8) {
							System.out.println("Ligne ignorée (format incorrect) : " + line);
							continue;
						}

						try {
							LocalDate datePaiement = Utils.parseLocalDate(valeurs[2].trim());

							Loyer loyer = new Loyer(
									valeurs[0],                        // ID_LOCATAIRE
									valeurs[1],                        // NUMERO_FISCALE
									datePaiement,                       // DATE_PAIEMENT (LocalDate)
									Double.parseDouble(valeurs[3]),    // MONTANT_PROVISION
									Double.parseDouble(valeurs[4]),    // MONTANT_LOYER
									valeurs[5],                        // MOIS
									Double.parseDouble(valeurs[6]),    // MONTANT_REGULARISATION
									valeurs[7].equals("1")             // ARCHIVE
									);

							// Insérer dans la base
							daoLoyer.create(loyer);

						} catch (Exception e3) {
							System.out.println("Erreur sur la ligne : " + line);
							e3.printStackTrace();
						}
					}

					System.out.println("Import CSV terminé !");

				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			break;

			// =================================================
			// MENU FICHIER
			// =================================================
		case "itemQuitter":
			// Bouton pour quitté l'application
			// Demande de confirmation avant la déconnexion
			int q = JOptionPane.showConfirmDialog(
					fp,
					"Voulez-vous vraiment quitter ?",
					"Quitter",
					JOptionPane.YES_NO_OPTION
					);
			if (q == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
			break;

		case "itemDeconnexion":
			// Bouton pour fermé la connexion
			// Demande de confirmation avant la déconnexion
			int deco = JOptionPane.showConfirmDialog(
					fp,
					"Voulez-vous vous déconnecter ?",
					"Déconnexion",
					JOptionPane.YES_NO_OPTION
					);
			if (deco == JOptionPane.YES_OPTION) {
				fp.dispose(); // Fermer la fenêtre prinicipale car il n'y a pas de connexion
				new FenetreConnexion().setVisible(true); // Retour à la page de connexion
			}
			break;

			// =================================================
			// PAGE BIENS - BÂTIMENTS
			// =================================================
		case "btnAjouterBatiment":
			// Ouvrir la fenêtre d'ajout d'un bâtiment (JInternalFrame)
			FenetreAjouterBatiment fb = new FenetreAjouterBatiment(this, fp.getDesktopPane());
			rendreVisible(fb); // Utilisation de la méthode rendreVisible() pour ouvrir la fenêtre
			break;

		case "btnModifierBatiment":
			// Appeler la méthode pour modifier un bâtiment sélectionné
			modifierBatiment();
			break;

		case "btnSupprimerBatiment":
			// Appeler la méthode pour supprimer un bâtiment sélectionné
			supprimerBatiment();
			break;

			// =================================================
			// PAGE BIENS - BIENS LOUABLES
			// =================================================
		case "btnAjouterBienLouable":
			// Vérifier qu'il y a des bâtiments avant d'ouvrir la fenêtre
			try {
				Modele.dao.DaoBatiment daoBatiment = new Modele.dao.DaoBatiment();
				java.util.List<Modele.Batiment> listeBatiments = daoBatiment.findAll();

				if (listeBatiments.isEmpty()) {
					javax.swing.JOptionPane.showMessageDialog(fp, "Aucun bâtiment trouvé dans la base de données. Veuillez d'abord créer des bâtiments.", "Information", javax.swing.JOptionPane.INFORMATION_MESSAGE);
					break;
				}
			} catch (Exception ex) {
				javax.swing.JOptionPane.showMessageDialog(fp, "Erreur lors de la vérification des bâtiments : " + ex.getMessage(), "Erreur", javax.swing.JOptionPane.ERROR_MESSAGE);
				break;
			}

			// Ouvrir la fenêtre d'ajout d'une bien louable seulement si des bâtiments existent
			FenetreAjouterBienLouable fabl = new FenetreAjouterBienLouable(this,fp.getDesktopPane());
			rendreVisible(fabl); // Utilisation de la méthode rendreVisible() pour ouvrir la fenêtre
			break;

		case "btnModifierBienLouable":
			// Appeler la méthode pour modifier un bien loubale sélectionné
			modifierBienLouable();
			break;

		case "btnSupprimerBienLouable":
			// Appeler la méthode pour supprimer un bien louable sélectionné
			supprimerBienLouable();
			break;

		case "btnAfficherDiagnostics":
			// Ouvrir la fenêtre qui affiche les diagnostics
			FenetreAfficherDiagnostics fads = new FenetreAfficherDiagnostics(fp.getDesktopPane());
			rendreVisible(fads); // Utilisation de la méthode rendreVisible() pour ouvrir la fenêtre
			break;

		case "btnAfficherCompteur":
			FenetreAfficherCompteur fac = new FenetreAfficherCompteur(this, fp.getDesktopPane());
			rendreVisible(fac);
			break;


			// -------------------- PAGE LOCATION --------------------
		case "btnAjouterFacture_Loc":
			// TODO : Bouton à  implémentée
			System.out.println("btnAjouterFacture_Loc : action non implémentée!!");
			break;

		case "btnMonLocataire":
			// Ouvrir la fenêtre qui affiche les compteurs
			FenetreAfficherCompteur fac2 = new FenetreAfficherCompteur(this, fp.getDesktopPane());
			rendreVisible(fac2); // Utilisation de la méthode rendreVisible() pour ouvrir la fenêtre
			break;

			// =================================================
			// PAGE LOCATIONS
			// =================================================
		case "btnArchiver_Locations":
			// Archiver une location sélectionnée
			archiverLocation();
			break;

		case "btnInserer_Locations":
			// Vérifier qu'il y a des locataires et des biens avant d'ouvrir la fenêtre
			try {
				Modele.dao.DaoLocataire daoLoc = new Modele.dao.DaoLocataire();
				Modele.dao.DaoBienLouable daoBien = new Modele.dao.DaoBienLouable();

				java.util.List<Modele.Locataire> listeLocataires = daoLoc.findAll();
				java.util.List<Modele.BienLouable> listeBiens = daoBien.findAll();

				if (listeLocataires.isEmpty() && listeBiens.isEmpty()) {
					javax.swing.JOptionPane.showMessageDialog(fp, "Aucun locataire et aucun bien trouvé dans la base de données. Veuillez d'abord créer des locataires et des biens.", "Information", javax.swing.JOptionPane.INFORMATION_MESSAGE);
					break;
				} else if (listeLocataires.isEmpty()) {
					javax.swing.JOptionPane.showMessageDialog(fp, "Aucun locataire trouvé dans la base de données. Veuillez d'abord créer des locataires.", "Information", javax.swing.JOptionPane.INFORMATION_MESSAGE);
					break;
				} else if (listeBiens.isEmpty()) {
					javax.swing.JOptionPane.showMessageDialog(fp, "Aucun bien trouvé dans la base de données. Veuillez d'abord créer des biens.", "Information", javax.swing.JOptionPane.INFORMATION_MESSAGE);
					break;
				}
			} catch (Exception ex) {
				javax.swing.JOptionPane.showMessageDialog(fp, "Erreur lors de la vérification des données : " + ex.getMessage(), "Erreur", javax.swing.JOptionPane.ERROR_MESSAGE);
				break;
			}

			// Ouvrir la fenêtre d'ajout de loyer seulement si les données sont disponibles
			FenetreAjouterLocation FAL = new FenetreAjouterLocation(this,fp.getDesktopPane());
			rendreVisible(FAL); // Utilisation de la méthode rendreVisible() pour ouvrir la fenêtre
			break;

		case "btnModifier_Locations":
			// Appeler la méthode pour modifier un loyer sélectionné
			modifierLocation();
			break;

		case "btnSupprimer_Locations":
			// Appeler la méthode pour supprimer un loyer sélectionné
			supprimerLocation();
			break;

			// =================================================
			// PAGE LOCATAIRES
			// =================================================
		case "btnAjouterLocataire":
			// Ouvrir la fenêtre d'ajout de locataire
			FenetreLocataireAjouter FALoc = new FenetreLocataireAjouter(this);
			rendreVisible(FALoc); // Utilisation de la méthode rendreVisible() pour ouvrir la fenêtre
			break;

		case "btnModifierLocataire":
			// Appeler la méthode pour modifier un locataire sélectionné
			modifierLocataire();
			break;

		case "btnSupprimerLocataire":
			// Appeler la méthode pour supprimer un locataire sélectionné
			supprimerLocataire();
			break;

		case "btnArchiverLocataire":
			// Archiver le locataire sélectionné
			archiverLocataire();
			break;

			// =================================================
			// PAGE ENTREPRISES
			// =================================================
		case "btnAjouterEntreprise":
			// Ouvrir la fenêtre d'ajout d'une entreprise
			FenetreAjouterEntreprise FAE = new FenetreAjouterEntreprise(this,fp.getDesktopPane());
			rendreVisible(FAE); // Utilisation de la méthode rendreVisible() pour ouvrir la fenêtre
			break;

		case "btnModifierEntreprise":
			// Appeler la méthode pour modifier une entreprise sélectionné
			modifierEntreprise();
			break;

		case "btnSupprimerEntreprise":
			// Appeler la méthode pour supprimer une entreprise sélectionné
			supprimerEntreprise();
			break;

			// =================================================
			// PAGE BAUX
			// =================================================
		case "btnAjouter_Baux":
			// Vérifier qu'il y a des locataires et des biens avant d'ouvrir la fenêtre
			try {
				Modele.dao.DaoLocataire daoLoc = new Modele.dao.DaoLocataire();
				Modele.dao.DaoBienLouable daoBien = new Modele.dao.DaoBienLouable();

				List<Modele.Locataire> listeLocataires = daoLoc.findAll();
				List<Modele.BienLouable> listeBiens = daoBien.findAll();

				if (listeLocataires.isEmpty() && listeBiens.isEmpty()) {
					javax.swing.JOptionPane.showMessageDialog(fp, "Aucun locataire et aucun bien trouvé dans la base de données. Veuillez d'abord créer des locataires et des biens.", "Information", javax.swing.JOptionPane.INFORMATION_MESSAGE);
					break;
				} else if (listeLocataires.isEmpty()) {
					javax.swing.JOptionPane.showMessageDialog(fp, "Aucun locataire trouvé dans la base de données. Veuillez d'abord créer des locataires.", "Information", javax.swing.JOptionPane.INFORMATION_MESSAGE);
					break;
				} else if (listeBiens.isEmpty()) {
					javax.swing.JOptionPane.showMessageDialog(fp, "Aucun bien trouvé dans la base de données. Veuillez d'abord créer des biens.", "Information", javax.swing.JOptionPane.INFORMATION_MESSAGE);
					break;
				}
			} catch (Exception ex) {
				javax.swing.JOptionPane.showMessageDialog(fp, "Erreur lors de la vérification des données : " + ex.getMessage(), "Erreur", javax.swing.JOptionPane.ERROR_MESSAGE);
				break;
			}

			// Ouvrir la fenêtre d'ajout de bail seulement si les données sont disponibles
			FenetreAjouterContratDeLocation FAB = new FenetreAjouterContratDeLocation(this, fp.getDesktopPane());
			rendreVisible(FAB); // Utilisation de la méthode rendreVisible() pour ouvrir la fenêtre
			break;

		case "btnModifier_Baux":
			// Appeler la méthode pour modifier un bail sélectionné
			modifierBaux();
			break;

		case "btnAfficherCaution":
			// Ouvrir la fenêtre d'affichage de caution
			FenetreAfficherCaution FAC = new FenetreAfficherCaution(fp.getDesktopPane());
			rendreVisible(FAC); // Utilisation de la méthode rendreVisible() pour ouvrir la fenêtre
			break;
			
		case "btnCalculerIRL" :
			calculIRL();
			break;

		case "btnArchiver_Baux":
			// Appeler la méthode d'archivage d'un bail
			archiverBaux();
			break;

			// =================================================
			// PAGE FACTURES
			// =================================================
		case "btnRechercher_Factures":
			// Appliquer les filtres sur les factures présents dans la table
			JOptionPane.showMessageDialog(fp, "Recherche factures");
			filtreFacture();
			break;

		case "btnCalculerCharges_Factures":
			// Ouvrir la calculatrice des charges
			FenetreCalculerCharges fenetreCalculerCharges = new FenetreCalculerCharges(this);
			rendreVisible(fenetreCalculerCharges); // Utilisation de la méthode rendreVisible() pour ouvrir la fenêtre
			break;

		case "btnArchiver_Factures":
			// Archive une facture sélectionné
			archiverFacture();
			break;

		case "btnModifier_Factures":
			// Appeler la méthode pour modifier une facture sélectionné
			modifierFacture();
			break;

		case "btnSupprimer_Factures":
			// Appeler la méthode pour supprimer une facture sélectionné
			supprimerFacture();
			break;

		case "btnAjouter_Factures":
			// Ouvrir la fenêtre d'ajout de facture
			FenetreAjouterFacture facture = new FenetreAjouterFacture(this);
			rendreVisible(facture); // Utilisation de la méthode rendreVisible() pour ouvrir la fenêtre
			break;

			// =================================================
			// PAGE ARCHIVE
			// =================================================
		case "btnRafraichirArchives_Compteurs":
			// Rafraîchir la liste des compteurs archivés
			afficherTableauCompteursArchives();
			break;

		case "btnRestaurerArchives_Compteurs":
			// Restaurer un compteur archivé
			restaurerArchiveCompteur();
			break;

		case "btnRafraichirArchives_Locataires":
			// Rafraichis la liste des locataires archivées
			afficherTableauLocatairesArchives();
			break;

		case "btnRestaurerArchives_Locataire":
			// Restaurer un locataire archivé
			restaurerArchiveLocataire();
			break;

		case "btnRafraichirArchives_Locations":
			// Rafraichis la liste des loyers archivées
			afficherTableauLocationArchive();
			break;

		case "btnRestaurerArchives_Locations":
			// Restaurer un loyer archivé
			restaurerArchiveLocation();
			break;

		case "btnRafraichirArchives_Diagnostics":
			// Rafraichis la liste des diagnostics archivées
			afficherTableauDiagnosticArchive();
			break;

		case "btnRestaurerArchives_Diagnostics":
			// Restaurer un diagnostic archivé
			restaurerArchiveDiagnostic();
			break;

		case "btnRafraichirArchives_Factures":
			// Rafraichis la liste des factures archivées
			afficherTableauFactureArchive();
			break;

		case "btnRestaurerArchives_Factures":
			// Restaurer un facture archivé
			restaurerArchiveFacture();
			break;

		case "btnRafraichirArchives_Caution":
			// Rafraichis la liste des cautions archivées
			afficherTableauCautionArchives();
			break;

		case "btnRestaurerArchives_Caution":
			// Restaurer une caution archivé
			restaurerArchiveCaution();
			break;

		
		case "btnCalculer_Charges":
			// Ouvrir la fenêtre de calcul des charges
			openCalculerCharges();

		case "btnRafraichirArchives_ContratDeLocation":
			// Rafraichis la liste des cautions archiées
			afficherTableauContratDeLocationArchives();
			break;

		case "btnRestaurerArchives_ContratDeLocation":
			// Restaurer un bail archivé
			restaurerArchiveContratDeLocation();
			break;

		case "btnAjouterFactureAgence":
			// Ouvrir la fenêtre d'ajout d'une facture agence
			openAjouterFacture();
			break;

		case "btnExporterFacturesAgence":
			// Exporter les factures agence
			exporterFacturesAgence();
			break;

		default:
			/**
			 * Cas par défaut, c'est à dire pour une action qu'on n'as pas
			 * implémentée, l'appli vas nous afficher un message "Action non implémentée".
			 */
			System.out.println("CAS PAR DEFAULT : Action non implémentée : " + name);
			break;
		}
	}

	// =========================================================
	// BÂTIMENTS : affichage, modification, suppression
	// =========================================================
	/**
	 * Charge tous les bâtiments de la base de données,
	 * et les affiches dans le tableau
	 */
	public void afficherTableauBatiment() {
		try {
			DaoBatiment daoBat = new DaoBatiment();
			List<Batiment> listeBat = daoBat.findAll();
			DefaultTableModel model = (DefaultTableModel) fp.getTableauBatiment().getModel();
			model.setRowCount(0);
			for (Batiment bat : listeBat) {
				Object[] row = {
						bat.getIdBatiment(),
						bat.getAdresse(),
						bat.getVille(),
						bat.getCodePostale(),
						bat.getPeriodeDeConstruction()
				};
				model.addRow(row);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(fp, "Erreur lors de la récupération des bâtiments : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	/**
	 * Ouvre la fenêtre de modification pour le bâtiment sélectionné
	 * Le formulaire est déjà remplis avec les données du bâtiment sélectionné
	 * La clé primaire n'est pas modifiable
	 */
	public void modifierBatiment() {
		int row = fp.getTableauBatiment().getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(fp, "Veuillez sélectionner un bâtiment à modifier.", "Erreur", JOptionPane.WARNING_MESSAGE);
		}

		String id = fp.getTableauBatiment().getValueAt(row, 0).toString();

		try {
			DaoBatiment daoBat = new DaoBatiment();
			Batiment bat = daoBat.findById(id);

			if (bat == null) {
				JOptionPane.showMessageDialog(fp, "Bâtiment introuvable dans la base.", "Erreur", JOptionPane.ERROR_MESSAGE);
			}

			// Ouvre la fenêtre de modification pré-remplie
			FenetreModifierBatiment fmb = new FenetreModifierBatiment(this, bat, fp.getDesktopPane());
			rendreVisible(fmb);

			try {
				fmb.setSelected(true);
			} catch (java.beans.PropertyVetoException ex) {
				ex.printStackTrace();
			}
			fmb.moveToFront();
		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(fp, "Erreur lors de la récupération du bâtiment : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Supprime le bâtiment sélectionné après la confirmation de l'utilisateur,
	 * et affiche un message de confirmation.
	 *
	 * Rafraichis le tableau (liste des bâtiments) une fois que la suppression est terminée
	 */
	public void supprimerBatiment() {
		int row = fp.getTableauBatiment().getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(fp, "Veuillez sélectionner un bâtiment à supprimer.", "Erreur", JOptionPane.WARNING_MESSAGE);
			return;
		}

		String id = fp.getTableauBatiment().getValueAt(row, 0).toString();

		int confirm = JOptionPane.showConfirmDialog(
				fp,
				"Voulez-vous vraiment supprimer le bâtiment " + id + " ?",
				"Confirmation",
				JOptionPane.YES_NO_OPTION);

		if (confirm != JOptionPane.YES_OPTION) {
			return;
		}

		try {
			DaoBatiment daoBat = new DaoBatiment();
			Batiment bat = daoBat.findById(id);

			if (bat == null) {
				JOptionPane.showMessageDialog(fp, "Bâtiment introuvable dans la base.", "Erreur", JOptionPane.ERROR_MESSAGE);
				return;
			}
			daoBat.delete(bat);
			afficherTableauBatiment();

			JOptionPane.showMessageDialog(fp, "Bâtiment supprimé avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);

		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(fp, "Erreur lors de la suppression du bâtiment : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}

	// ================================================================================
	// BIENS LOUABLES : affichage, modification, suppression, ouvrirDétailsBienLouable
	// ================================================================================
	/**
	 * Charge tous les biens louables de la base de données,
	 * et les affiches dans le tableau
	 */
	public void afficherTableauBienLouable() {
		try {

			DaoBienLouable daoBien = new DaoBienLouable();
			List<BienLouable> listeBiens = daoBien.findAll();
			DefaultTableModel model = (DefaultTableModel) fp.getTableauBienLouable().getModel();
			model.setRowCount(0);
			for (BienLouable bien : listeBiens) {
				Object[] row = {
						bien.getNumero_fiscale(),
						bien.getType(),
						bien.getSurface(),
						bien.getNb_pieces(),
						bien.getBatiment()
				};
				model.addRow(row);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(fp, "Erreur lors de la récupération des biens : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	/**
	 * Ouvre la fenêtre de modification pour le bien louable sélectionné
	 * Le formulaire est déjà remplis avec les données du bien louable sélectionné
	 * La clé primaire n'est pas modifiable
	 */
	public void modifierBienLouable() {
		int row = fp.getTableauBienLouable().getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(fp, "Veuillez sélectionner un bien à modifier.", "Erreur", JOptionPane.WARNING_MESSAGE);
		}

		String numeroFiscal = fp.getTableauBienLouable().getValueAt(row, 0).toString();

		try {
			DaoBienLouable daoBien = new DaoBienLouable();
			BienLouable bien = daoBien.findById(numeroFiscal);

			if (bien == null) {
				JOptionPane.showMessageDialog(fp, "Bien introuvable dans la base.", "Erreur", JOptionPane.ERROR_MESSAGE);
			}

			// Ouvre la fenêtre de modification pré-remplie
			FenetreModifierBienLouable fmbl = new FenetreModifierBienLouable(this,fp.getDesktopPane(), bien);
			rendreVisible(fmbl);

		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(fp, "Erreur lors de la récupération du bien : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Supprime le bien louable sélectionné après confirmation
	 */
	public void supprimerBienLouable() {
		int row = fp.getTableauBienLouable().getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(fp, "Veuillez sélectionner un bien à supprimer.", "Erreur", JOptionPane.WARNING_MESSAGE);
			return;
		}

		String numeroFiscal = fp.getTableauBienLouable().getValueAt(row, 0).toString();

		int confirm = JOptionPane.showConfirmDialog(
				fp,
				"Voulez-vous vraiment supprimer le bien " + numeroFiscal + " ?",
				"Confirmation",
				JOptionPane.YES_NO_OPTION);

		if (confirm != JOptionPane.YES_OPTION) {
			return;
		}

		try {
			DaoBienLouable daoBien = new DaoBienLouable();
			BienLouable bien = daoBien.findById(numeroFiscal);

			if (bien == null) {
				JOptionPane.showMessageDialog(fp, "Bien introuvable dans la base.", "Erreur", JOptionPane.ERROR_MESSAGE);
				return;
			}

			daoBien.delete(bien);

			afficherTableauBienLouable();

			JOptionPane.showMessageDialog(fp, "Bien supprimé avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);

		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(fp, "Erreur lors de la suppression du bien : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
		}


	}


	public void ouvrirDetailBienLouable(String numeroFiscal) {

		try {

			DaoBienLouable daoBien = new DaoBienLouable();
			DaoBatiment daoBat = new DaoBatiment();
			DaoCompteur daoComp = new DaoCompteur();
			DaoContratDeLocation daoCon = new DaoContratDeLocation();
			DaoLoyer daoLoyer = new DaoLoyer();
			DaoFacture daoFac = new DaoFacture();

			BienLouable bien = daoBien.findById(numeroFiscal);
			Batiment batiment = daoBat.findById(bien.getBatiment());

			List<Compteur> compteurs = daoComp.findAll().stream()
					.filter(c -> numeroFiscal.equals(c.getNumero_fiscale()))
					.toList();

			ContratDeLocation contrat = daoCon.findAll().stream()
					.filter(c -> numeroFiscal.equals(c.getNumero_fiscale()))
					.findFirst()
					.orElse(null);

			List<Loyer> loyers = daoLoyer.findAll().stream()
					.filter(l -> numeroFiscal.equals(l.getNumero_fiscale()))
					.toList();

			List<Facture> factures = daoFac.findAll().stream()
					.filter(f -> numeroFiscal.equals(f.getNumero_fiscale()))
					.toList();

			FenetreAffichageDetailsBienLouable fenetre =
					new FenetreAffichageDetailsBienLouable(
							bien, batiment, compteurs, contrat, loyers, factures
							);

			fp.getDesktopPane().add(fenetre);
			fenetre.setVisible(true);
			fenetre.toFront();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					null,
					"Erreur lors de l’ouverture du détail : " + e.getMessage()
					);
		}
	}








	// =========================================================
	// FACTURES : affichage, archivage, modifier, supprimer filtres et chargement des combos
	// =========================================================
	/**
	 * Charge et affiche toutes les factures de la base de données
	 */
	public void afficherTableFactures() {
		try {
			DaoFacture daoFacture = new DaoFacture();
			List<Facture> listeFacture = daoFacture.findAll();
			DefaultTableModel model =
					(DefaultTableModel) fp.getTableauFacture().getModel();
			model.setRowCount(0);

			for (Facture facture : listeFacture) {
				model.addRow(new Object[]{
						facture.getNumero_fiscale(),
						facture.getNumero(),
						facture.getDate_facture(),
						facture.getType_travaux(),
						facture.getMontant(),
						facture.getMode_paiement(),
						facture.getNumero_devis(),
						facture.getNature(),
						facture.getDate_devis(),
						facture.getMontantDevis()
				});
			}
			fp.getTableauFacture().revalidate();
			fp.getTableauFacture().repaint();
			model.fireTableDataChanged(); 

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(fp, "Erreur lors de la récupération des biens : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	/**

	Archive la facture sélectionné*/
	private void archiverFacture() {

		JTable tableFac = fp.getTableauFacture();

		int rowFac = tableFac.getSelectedRow();

		if (rowFac == -1) {
			JOptionPane.showMessageDialog(
					fp,
					"Veuillez sélectionner un locataire à archiver.",
					"Aucune sélection",
					JOptionPane.WARNING_MESSAGE
					);
			return;
		}

		String numero = tableFac.getValueAt(rowFac, 0).toString();

		int confirm = JOptionPane.showConfirmDialog(
				fp,
				"Voulez-vous vraiment archiver ce locataire ?",
				"Confirmation",
				JOptionPane.YES_NO_OPTION
				);

		if (confirm != JOptionPane.YES_OPTION) {
			return;
		}

		try {
			DaoFacture dao = new DaoFacture();
			dao.archiverById(numero);
			afficherTableFactures();

			JOptionPane.showMessageDialog(
					fp,
					"Facture archivé avec succès.",
					"Archivage",
					JOptionPane.INFORMATION_MESSAGE
					);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(
					fp,
					"Erreur lors de l'archivage de la facture.",
					"Erreur",
					JOptionPane.ERROR_MESSAGE
					);
			e.printStackTrace();
		}
	}

	/**
	 * Ouvre la fenêtre de modification de la facture sélectionné
	 */
	private void modifierFacture() {
		int ligne = fp.getTableauFacture().getSelectedRow();
		if (ligne == -1) {
			JOptionPane.showMessageDialog(fp, "Sélectionne une facture");
			return;
		}
		String numeroFacture = fp.getTableauFacture()
				.getValueAt(ligne, 1)
				.toString();

		try {
			DaoFacture dao = new DaoFacture();
			Facture facture = dao.findById(numeroFacture);

			// Ouvre la fenêtre pré-remplie avec les données de la facture sélectionné
			FenetreModifieFacture fenMod = new FenetreModifieFacture(this, facture);
			rendreVisible(fenMod); // Utilisation de la méthode rendreVisible() pour ouvrir la fenêtre

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(fp, "Erreur chargement facture");
		}
	}
	
	private void calculIRL() {
		int ligne = fp.getTableauBaux().getSelectedRow();
		if (ligne == -1) {
			JOptionPane.showMessageDialog(fp, "Sélectionne un bail");
			return;
		}
		String numBail = fp.getTableauBaux().getValueAt(ligne, 0).toString();
		try {
			DaoContratDeLocation daoBaux = new DaoContratDeLocation();
			ContratDeLocation contrat = daoBaux.findById(numBail);
			 if (contrat == null) {
			        JOptionPane.showMessageDialog(
			            fp,
			            "Veuillez sélectionner un contrat avant de calculer l’IRL",
			            "Erreur",
			            JOptionPane.ERROR_MESSAGE
			        );
			        return;
			    }
			CalculerIRL irl = new CalculerIRL(this.fp, this, contrat);
			rendreVisible(irl);
		}catch(SQLException irlExeption) {
			irlExeption.printStackTrace();
			JOptionPane.showMessageDialog(fp, "Erreur chargement du bail");
		}
	}

	/**
	 * Supprime la facture sélectionné
	 */
	private void supprimerFacture() {
		int ligne = fp.getTableauFacture().getSelectedRow();
		if (ligne == -1) {
			JOptionPane.showMessageDialog(fp, "Sélectionne une facture à supprimez");
			return;
		}
		String idFacture = fp.getTableauFacture().getValueAt(ligne, 1).toString();
		String typeFacture = fp.getTableauFacture().getValueAt(ligne, 3).toString();

		int confirm = JOptionPane.showConfirmDialog(
				fp,
				"Voulez-vous vraiment supprimer le bâtiment " + idFacture + " ?",
				"Confirmation",
				JOptionPane.YES_NO_OPTION);

		if (confirm != JOptionPane.YES_OPTION) {
			return;
		}

		try {
			// Étape 1 : On supprime la facture enfant (avec tes méthodes de DAO)
			if (typeFacture.equalsIgnoreCase("Variable")) {
				DaoVariable daoVar = new DaoVariable();
				List<Variable> listeVar = daoVar.findAll();
				for (Variable v : listeVar) {
					if (idFacture.equals(v.getNumero())) {
						daoVar.delete(v);
					}
				}

			} else if (typeFacture.equalsIgnoreCase("Impots")) {
				DaoImpots daoImp = new DaoImpots();
				List<Impots> listeIMP = daoImp.findAll();
				for (Impots i : listeIMP) {
					if (idFacture.equals(i.getNumero())) {
						daoImp.delete(i);
					}
				}
			} else if (typeFacture.equalsIgnoreCase("Travaux")) {
				DaoTravaux daoTrav = new DaoTravaux();
				List<Travaux> listeTrav = daoTrav.findAll();
				for (Travaux t : listeTrav) {
					if (idFacture.equals(t.getNumero())) {
						daoTrav.delete(t);
					}
				}
			} else if (typeFacture.equalsIgnoreCase("Assurance") || typeFacture.equalsIgnoreCase("Assurances")) {
				DaoAssurance daoAss = new DaoAssurance();
				List<Assurance> listeAss = daoAss.findAll();
				for (Assurance a : listeAss) {
					if (idFacture.equals(a.getNumero())) {
						daoAss.delete(a);
					}
				}
			}

			// ETAPE 2: Supprimer la facture parent
			DaoFacture factureD = new DaoFacture();
			Facture facAsupp = factureD.findById(idFacture);
			if (facAsupp != null) {
				factureD.delete(facAsupp);
			}

			afficherTableFactures();
			JOptionPane.showMessageDialog(fp, "Facture supprimée");

		} catch(SQLException excFacDelete) {
			// Si tu as toujours l'erreur ORA-02292 ici, c'est que ton findById(idFacture)
			// ne trouve rien et donc ne supprime pas l'enfant.
			excFacDelete.printStackTrace();
			JOptionPane.showMessageDialog(fp, "Erreur lors de la suppression du bâtiment : " + excFacDelete.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Remplit le comboxBox des biens louables pour le filtrage
	 * Il récupère Uniquement les numéros fiscaux
	 */
	public void chargerBiens(List<Facture> factures) {
		try {
			DaoBienLouable dao = new DaoBienLouable();
			List<BienLouable> biens = dao.findAll();

			JComboBox<String> comboBiens = fp.getcomboBienFactures();
			comboBiens.removeAllItems();
			comboBiens.addItem("Tous"); // Option par défaut

			// Mettre les biens dans un HashSet pour éviter les doublons
			Set<String> bienTrié = new HashSet<>();
			for (BienLouable b : biens) {
				if (b.getNumero_fiscale() != null ) {
					bienTrié.add(b.getNumero_fiscale());
				}
			}

			// Remplir le combo avec les biens du HashSet
			for (String numB : bienTrié) {
				comboBiens.addItem(numB);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(fp,
					"Erreur lors du chargement des locataires",
					"Erreur",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Met à jour le tableau avec une liste filtrée de factures
	 * Utilisé après avoir appliqué des filtres
	 */
	public void mettreAJourTable(List<Facture> factures) {
		DefaultTableModel model = (DefaultTableModel) fp.getTableauFacture().getModel();
		model.setRowCount(0); // Enlevé toutes les lignes de la table

		for (Facture f : factures) {
			model.addRow(new Object[]{
					f.getNumero_fiscale(),
					f.getNumero(),
					f.getDate_facture(),
					f.getType_travaux(),
					f.getMontant(),
					f.getMode_paiement(),
					f.getNumero_devis(),
					f.getNature(),
					f.getDate_devis()
			});
		}
	}

	/**
	 * Remplit le comboBox avec les types (élimine doublons)
	 * Permet le filtrage des factures par type
	 */
	public void chargerTypeFacture() {
		try {
			DaoFacture dao = new DaoFacture();
			List<Facture> factures = dao.findAll();
			JComboBox<String> combo = fp.getComboType();
			combo.removeAllItems();
			combo.addItem("Tous"); // Option par défaut

			// De même on met les factures dans une HashSet pour éviter les doublons
			Set<String> typeTrié = new HashSet<>();
			for (Facture f : factures) {
				if (f.getType_travaux() != null) {
					typeTrié.add(f.getType_travaux());
				}
			}
			for (String type : typeTrié) {
				combo.addItem(type);
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(fp,
					"Erreur lors du chargement des types de facture",
					"Erreur",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

	}

	/**
	 * Applique 3 filtres au même temps sur les factures
	 * - Filtre par type (comboBox)
	 * - Filtre par bien louable (comboBox)
	 * - Filtre par période/année (champ de texte)
	 */
	public void filtreFacture() {
		try {
			
			String typeChoisi = (String) fp.getComboType().getSelectedItem();
			String periode = fp.getChampPeriode();
			String bienChoisi = (String) fp.getcomboBienFactures().getSelectedItem();
			JTabbedPane onglets = fp.getOngletsFactures();

			if (typeChoisi == null || typeChoisi.isEmpty()) {
				JOptionPane.showMessageDialog(fp, "Veuillez choisir un type");
				return;
			}
			
			// 1. On nettoie les onglets secondaires pour éviter les doublons
			while (onglets.getTabCount() > 1) {
				onglets.remove(1);
			}

			afficherFiltrageComplet(typeChoisi, bienChoisi, periode);
			switch (typeChoisi) {
			case "Tous":
				// On recrée tout et on rafraîchit la table principale
				onglets.addTab("Impots", fp.creerOngletImpots());
				onglets.addTab("Travaux", fp.creerOngletTravauxFac());
				onglets.addTab("Variable", fp.creerOngletVariable());
				onglets.addTab("Assurance", fp.creerOngletAssurance());

				onglets.setSelectedIndex(0);
				break;

			case "Travaux":
				// On crée l'onglet et on force le remplissage de la NOUVELLE table
				onglets.addTab("Travaux", fp.creerOngletTravauxFac());
				onglets.setSelectedIndex(1);
				break;

			case "Impots":
				onglets.addTab("Impots", fp.creerOngletImpots());
				onglets.setSelectedIndex(1);
				break;

			case "Variable":
				onglets.addTab("Variable", fp.creerOngletVariable());
				onglets.setSelectedIndex(1);
				break;

			case "Assurance":
				onglets.addTab("Assurance", fp.creerOngletAssurance());
				onglets.setSelectedIndex(1);
				break;
			}

		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	public void afficherFiltrageComplet(String typeCible, String bienCible, String periodeCible) {
		
		try {
			DaoFacture daoFacT = new DaoFacture();
			List<Facture> listeFacture = daoFacT.findAll();

			DefaultTableModel model = (DefaultTableModel) fp.getTableauFacture().getModel();
			model.setRowCount(0);

			for (Facture f : listeFacture) {
			boolean matchesType = typeCible.equals("Tous") ||
					(f.getType_travaux() != null && f.getType_travaux().equalsIgnoreCase(typeCible));//insensible à la casse

			// 2. Filtrage par Bien
			boolean matchesBien = bienCible.equals("Tous") ||
					(f.getNumero_fiscale() != null && f.getNumero_fiscale().equals(bienCible));

			// 3. Filtrage par Période
			boolean matchesPeriode = true;

			if (periodeCible != null && !periodeCible.trim().isEmpty()) {
				if (f.getDate_facture() != null) {
					matchesPeriode = 
						f.getDate_facture().toLocalDate().getYear() ==
						Integer.parseInt(periodeCible);
				} else {
					matchesPeriode = false;
				}
			}

			// Affichage si toutes les conditions sont remplies
			if (matchesType && matchesBien && matchesPeriode) {
				model.addRow(new Object[]{
							f.getNumero_fiscale(),
							f.getNumero(),
							f.getDate_facture(),
							f.getType_travaux(),
							f.getMontant(),
							f.getMode_paiement(),
							f.getNumero_devis(),
							f.getNature(),
							f.getDate_devis()
					});
				}
			}
			fp.getTableauFacture().revalidate();
			fp.getTableauFacture().repaint();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(fp, "Erreur de filtrage : " + e.getMessage());
		}
	}



	public void afficherTableImpots() {
		try {
			//On récupere tous les impots depuis la BDD une seule fois
			DaoImpots daoImpots = new DaoImpots();
			List<Impots> listeImpots = daoImpots.findAll();

			//
			DefaultTableModel model = (DefaultTableModel) fp.getTableauImpot().getModel();
			model.setRowCount(0);

			DefaultTableModel modelPrincipal = (DefaultTableModel) fp.getTableauFacture().getModel();

			for (int i = 0; i < modelPrincipal.getRowCount(); i++) {
				String numFactureVisible = modelPrincipal.getValueAt(i, 1).toString();

				for (Impots impots : listeImpots) {
					if (numFactureVisible.equalsIgnoreCase(impots.getNumero())) {
						model.addRow(new Object[]{
								impots.getNumero(),
								impots.getIdImpots(),
								impots.getAnnee(),
								impots.getType(),
								impots.getRecuperable_impot()
						});
					}
				}
			}


			fp.getTableauImpot().revalidate();
			fp.getTableauImpot().repaint();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(fp, "Erreur lors de la récupération des impots : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	// =========================================================
	// LOCATIONS : affichage, modification, suppression, archivage
	// =========================================================
	/**
	 * Charge et affiche tous les loyers (historique des paiements) dans le tableau
	 */
	public void afficherTableauLoyer() {
		try {
			DaoLoyer daoLoyer = new DaoLoyer();
			List<Loyer> listeLoyer = daoLoyer.findAll();

			DefaultTableModel model = (DefaultTableModel) fp.getTableauLoyer().getModel();
			model.setRowCount(0);

			for (Loyer loy : listeLoyer) {
				Object[] row = {
						loy.getIdLocataire(),
						loy.getNumero_fiscale(),
						loy.getDate_paiement(),
						loy.getMontant_provision(),
						loy.getMontant_loyer(),
						loy.getMois(),
						loy.getMontant_regularisation()
				};
				model.addRow(row);
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(fp,
					"Erreur lors de la récupération des biens : " + e.getMessage(),
					"Erreur", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	/**
	 * Ouvre la fenêtre de modification pour le loyer sélectionné
	 * Les clés primaires ne sont pas modifiables
	 */
	public void modifierLocation() {
		int row = fp.getTableauLoyer().getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(fp,
					"Veuillez sélectionner un loyer à modifier.",
					"Erreur", JOptionPane.WARNING_MESSAGE);
			return;
		}

		String idLocataire = fp.getTableauLoyer().getValueAt(row, 0).toString();
		String numeroFiscal = fp.getTableauLoyer().getValueAt(row, 1).toString();
		Object valeur = fp.getTableauLoyer().getValueAt(row, 2);
		String texteDate = valeur.toString().trim();
		LocalDate datePaiement = Utils.parseLocalDate(texteDate);

		try {
			DaoLoyer daoLoyer = new DaoLoyer();
			Loyer loyer = daoLoyer.findById(idLocataire, numeroFiscal, datePaiement.toString());

			if (loyer == null) {
				JOptionPane.showMessageDialog(fp,
						"Loyer introuvable dans la base.",
						"Erreur", JOptionPane.ERROR_MESSAGE);
				return;
			}

			// La fenêtre qui s'ouvre est pré-rempli avec les données de la facture sélectionné
			FenetreModifierLocation fml = new FenetreModifierLocation(this, fp.getDesktopPane(), loyer);
			rendreVisible(fml); // Utilisation de la méthode rendreVisible() pour ouvrir la fenêtre

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(fp,
					"Erreur lors de la récupération du loyer : " + ex.getMessage(),
					"Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Supprime le loyer sélectionner dans la table
	 * Utilise les clés primaire (idLocataire + numeroFiscale + datePaiement)
	 */
	public void supprimerLocation() {
		int row = fp.getTableauLoyer().getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(fp, "Veuillez sélectionner une Location à supprimer.", "Erreur", JOptionPane.WARNING_MESSAGE);
			return;
		}

		String idLocataire = fp.getTableauLoyer().getValueAt(row, 0).toString();
		String numeroFiscal = fp.getTableauLoyer().getValueAt(row, 1).toString();
		String datePaiement = fp.getTableauLoyer().getValueAt(row, 2).toString();

		int confirm = JOptionPane.showConfirmDialog(
				fp,
				"Voulez-vous vraiment supprimer la Location " + idLocataire + " , " + numeroFiscal +  " , " + datePaiement + " ?",
				"Confirmation",
				JOptionPane.YES_NO_OPTION);

		if (confirm != JOptionPane.YES_OPTION) {
			return;
		}

		try {
			DaoLoyer daoLoyer = new DaoLoyer();
			Loyer loyer = daoLoyer.findById(idLocataire, numeroFiscal, datePaiement);

			if (loyer == null) {
				JOptionPane.showMessageDialog(fp, "Bien introuvable dans la base.", "Erreur", JOptionPane.ERROR_MESSAGE);
				return;
			}

			daoLoyer.delete(loyer);
			afficherTableauLoyer(); // Rafraichis la table une fois qu'il l'as supprimer de la base de données

			JOptionPane.showMessageDialog(fp, "Location supprimé avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE); // Affiche le message de succès

		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(fp, "Erreur lors de la suppression du Location : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Archive la location sélectionné
	 */
	private void archiverLocation() {
		JTable tableLoy = fp.getTableauLoyer();

		int rowLoy = tableLoy.getSelectedRow();

		if (rowLoy == -1) {
			JOptionPane.showMessageDialog(
					fp,
					"Veuillez sélectionner un locataire à archiver.",
					"Aucune sélection",
					JOptionPane.WARNING_MESSAGE
					);
			return;
		}

		String idLoyer = tableLoy.getValueAt(rowLoy, 0).toString();
		String numeroFiscale = tableLoy.getValueAt(rowLoy, 1).toString();
		Object valeur = tableLoy.getValueAt(rowLoy, 2);
		String texteDate = valeur.toString().trim();
		LocalDate datePaiement = Utils.parseLocalDate(texteDate);

		int confirm = JOptionPane.showConfirmDialog(
				fp,
				"Voulez-vous vraiment archiver ce locataire ?",
				"Confirmation",
				JOptionPane.YES_NO_OPTION
				);

		if (confirm != JOptionPane.YES_OPTION) {
			return;
		}

		try {
			DaoLoyer dao = new DaoLoyer();
			dao.archiverById(idLoyer, numeroFiscale, datePaiement);
			afficherTableauLoyer();

			JOptionPane.showMessageDialog(
					fp,
					"Loyer archivé avec succès.",
					"Archivage",
					JOptionPane.INFORMATION_MESSAGE
					);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(
					fp,
					"Erreur lors de l'archivage du loyer.",
					"Erreur",
					JOptionPane.ERROR_MESSAGE
					);
			e.printStackTrace();
		}
	}

	// =========================================================
	// BAUX : affichage, archivage, modification
	// =========================================================
	/**
	 * Charge et affiche tous les baux actifs, c'est à dire ceux qui ne sont pas
	 * archivés.
	 * Filtrage des baux archivés automatique!
	 */
	public void afficherTableauBaux() {
		try {
			DaoContratDeLocation daoContrat = new DaoContratDeLocation();
			List<ContratDeLocation> listeContrats = daoContrat.findAll();

			DefaultTableModel model = (DefaultTableModel) fp.getTableauBaux().getModel();
			model.setRowCount(0);

			for (ContratDeLocation contrat : listeContrats) {

				// Ignorer les baux archivés
				if (contrat.isArchive()) {
					continue;
				}

				Object[] row = {
						contrat.getIdContrat(),
						contrat.getDate_debut(),
						contrat.getTrimestre(),
						contrat.getDate_sortie(),
						contrat.getLoyer_aPayer(),
						contrat.getIRL(),
						contrat.getProvisions_charges(),
						contrat.isSolde_tout_compte_effectue() ? "Oui" : "Non",
								contrat.getDuree(),
								contrat.getNumero_fiscale(),
								contrat.getId_locataire()
				};
				model.addRow(row);
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(fp,
					"Erreur lors de la récupération des baux : " + e.getMessage(),
					"Erreur", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	/**
	 * Archive un bail (NE LE SUPPRIME PAS)
	 * A NOTE : Un bail ne peut pas être supprimer, mais il est archivé
	 * pour conserver l'historique
	 */
	public void archiverBaux() {
		int row = fp.getTableauBaux().getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(fp,
					"Veuillez sélectionner un bail à archiver.",
					"Erreur", JOptionPane.WARNING_MESSAGE);
			return;
		}

		String idContrat = fp.getTableauBaux().getValueAt(row, 0).toString();

		int confirm = JOptionPane.showConfirmDialog(
				fp,
				"Voulez-vous vraiment archiver le bail " + idContrat + " ?",
				"Confirmation d'archivage",
				JOptionPane.YES_NO_OPTION
				);

		if (confirm != JOptionPane.YES_OPTION) {
			return;
		}

		try {
			DaoContratDeLocation daoContrat = new DaoContratDeLocation();
			ContratDeLocation contrat = daoContrat.findById(idContrat);

			if (contrat == null) {
				JOptionPane.showMessageDialog(fp,
						"Bail introuvable.",
						"Erreur", JOptionPane.ERROR_MESSAGE);
				return;
			}

			daoContrat.archiverById(idContrat);
			afficherTableauBaux(); // Rafraichit la table une fois archivé

			JOptionPane.showMessageDialog(fp,
					"Bail archivé avec succès !",
					"Succès", JOptionPane.INFORMATION_MESSAGE);

		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(fp,
					"Erreur lors de l'archivage : " + ex.getMessage(),
					"Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Ouvre la fenêtre de modification pour le bail sélectionné
	 * Attention : On ne peut pas modifier les clés primaires
	 */
	public void modifierBaux() {
		int row = fp.getTableauBaux().getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(fp,
					"Veuillez sélectionner un bail à modifier.",
					"Erreur", JOptionPane.WARNING_MESSAGE);
			return;
		}

		String idContrat = fp.getTableauBaux().getValueAt(row, 0).toString();

		try {
			DaoContratDeLocation daoContrat = new DaoContratDeLocation();
			ContratDeLocation contrat = daoContrat.findById(idContrat);

			if (contrat == null) {
				JOptionPane.showMessageDialog(fp,
						"Bail introuvable.",
						"Erreur", JOptionPane.ERROR_MESSAGE);
				return;
			}

			FenetreModifierContratDeLocation fmb =
					new FenetreModifierContratDeLocation(this, fp.getDesktopPane(), contrat);
			rendreVisible(fmb); // Utilisation de la méthode rendreVisible() pour l'ouverture de la fenêtre

		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(fp,
					"Erreur lors de la récupération du bail : " + ex.getMessage(),
					"Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}

	// =========================================================
	// LOCATAIRES : affichage, modification et archivage
	// =========================================================
	/**
	 * Charge et affiche tous les locataires dans le tableau
	 */
	public void afficherTableauLocataire() {
		try {
			DaoLocataire daoLocataire = new DaoLocataire();
			List<Locataire> listeLocataire = daoLocataire.findAll();

			DefaultTableModel model = (DefaultTableModel) fp.getTableauLocataire().getModel();
			model.setRowCount(0); // Vide d'abord le tableau

			for (Locataire loc : listeLocataire) {
				Object[] row = {
						loc.getIdLocataire(),
						loc.getNom(),
						loc.getPrenom(),
						loc.getDate_naissance(),
						loc.getGenre(),
						loc.getEmail(),
						loc.getAdresse(),
						loc.getVille(),
						loc.getCode_postale(),
						loc.getTelephone()
				};
				model.addRow(row);
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(fp,
					"Erreur lors de la récupération des biens : " + e.getMessage(),
					"Erreur", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	/**
	 * Ouvre la fenêtre de modification pour le locataire sélectionné
	 * Attention : on ne peut pas modifier les clés primaires
	 */
	public void modifierLocataire() {
		int row = fp.getTableauLocataire().getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(fp,
					"Veuillez sélectionner un locataire à modifier.",
					"Erreur", JOptionPane.WARNING_MESSAGE);
			return;
		}

		String idLocataire = fp.getTableauLocataire().getValueAt(row, 0).toString();

		try {
			DaoLocataire daoLocataire = new DaoLocataire();
			Locataire locataire = daoLocataire.findById(idLocataire);

			if (locataire == null) {
				JOptionPane.showMessageDialog(fp,
						"Locataire introuvable dans la base.",
						"Erreur", JOptionPane.ERROR_MESSAGE);
				return;
			}


			// Ouvrir la fenêtre pré-remplie avec les données du locataire
			FenetreModifierLocataire fml = new FenetreModifierLocataire(this, locataire);
			rendreVisible(fml); // Utilisation de la méthode rendreVisible() pour ouvrir la fenêtre

			System.out.println("Avant modification -> idLocataire=" + locataire.getIdLocataire());

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(fp,
					"Erreur lors de la récupération du locataire : " + ex.getMessage(),
					"Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Archive les locataire
	 * Attention, il n'y a pas de suppression pour les locataires
	 * parce qu'il faut garder l'historique
	 */
	private void archiverLocataire() {
		JTable tableLoc = fp.getTableauLocataire();
		int rowLoc = tableLoc.getSelectedRow();
		if (rowLoc == -1) {
			JOptionPane.showMessageDialog(
					fp,
					"Veuillez sélectionner un locataire à archiver.",
					"Aucune sélection",
					JOptionPane.WARNING_MESSAGE
					);
			return;
		}

		String idLocataire = tableLoc.getValueAt(rowLoc, 0).toString();
		int confirm = JOptionPane.showConfirmDialog(
				fp,
				"Voulez-vous vraiment archiver ce locataire ?",
				"Confirmation",
				JOptionPane.YES_NO_OPTION
				);

		if (confirm != JOptionPane.YES_OPTION) {
			return;
		}
		try {
			DaoLocataire dao = new DaoLocataire();
			dao.archiverById(idLocataire);
			afficherTableauLocataire();

			JOptionPane.showMessageDialog(
					fp,
					"Locataire archivé avec succès.",
					"Archivage",
					JOptionPane.INFORMATION_MESSAGE
					);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(
					fp,
					"Erreur lors de l'archivage du locataire.",
					"Erreur",
					JOptionPane.ERROR_MESSAGE
					);
			e.printStackTrace();
		}
	}

	// =========================================================
	// ENTREPRISES : affichage, modification et suppression
	// =========================================================
	/**
	 * Charge depuis la base de données et affiche toutes les entreprises dans le tableau
	 */
	public void supprimerLocataire() {
		int row = fp.getTableauLocataire().getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(fp, "Veuillez sélectionner un locataire à supprimer.", "Erreur", JOptionPane.WARNING_MESSAGE);
			return;
		}

		String idLocataire = fp.getTableauLocataire().getValueAt(row, 0).toString();

		int confirm = JOptionPane.showConfirmDialog(
				fp,
				"Voulez-vous vraiment supprimer le locataire " + idLocataire + " ?",
				"Confirmation",
				JOptionPane.YES_NO_OPTION);

		if (confirm != JOptionPane.YES_OPTION) {
			return;
		}

		try {
			DaoLocataire daoLocataire = new DaoLocataire();
			Locataire locataire = daoLocataire.findById(idLocataire);

			if (locataire == null) {
				JOptionPane.showMessageDialog(fp, "Locataire introuvable dans la base.", "Erreur", JOptionPane.ERROR_MESSAGE);
				return;
			}

			daoLocataire.delete(locataire);

			afficherTableauLocataire();

			JOptionPane.showMessageDialog(fp, "Locataire supprimé avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);

		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(fp, "Erreur lors de la suppression du locataire : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void afficherTableauEntreprise() {
		try {
			DaoEntreprise daoEntreprise = new DaoEntreprise();
			List<Entreprise> listeEntreprises = daoEntreprise.findAll();

			DefaultTableModel model = (DefaultTableModel) fp.getTableauEntreprise().getModel();
			model.setRowCount(0); // Vide d'abord la table

			for (Entreprise ent : listeEntreprises) {
				Object[] row = {
						ent.getSiret(),
						ent.getNom(),
						ent.getVille(),
						ent.getMail(),
						ent.getAdresse(),
						ent.getSpecialite(),
						ent.getCode_postale()
				};
				model.addRow(row);
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(fp,
					"Erreur lors de la récupération des entreprises : " + e.getMessage(),
					"Erreur", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	/**
	 * Ouvre la fenêtre de modification pour l'entreprise sélectionné
	 */
	public void modifierEntreprise() {
		int row = fp.getTableauEntreprise().getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(fp,
					"Veuillez sélectionner une entreprise à modifier.",
					"Erreur", JOptionPane.WARNING_MESSAGE);
			return;
		}

		String siret = fp.getTableauEntreprise().getValueAt(row, 0).toString();

		try {
			DaoEntreprise daoEntreprise = new DaoEntreprise();

			Entreprise entreprise = daoEntreprise.findById(siret);

			if (entreprise != null) {
				// Pré-rempli la page avec les données de l'entreprise
				FenetreModifierEntreprise fme = new FenetreModifierEntreprise(this, fp.getDesktopPane(), entreprise);
				rendreVisible(fme);
			} else {
				JOptionPane.showMessageDialog(fp,
						"Entreprise introuvable dans la base.",
						"Erreur", JOptionPane.ERROR_MESSAGE);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(fp,
					"Erreur lors de la récupération de l'entreprise : " + ex.getMessage(),
					"Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Supprime l'entreprise sélectionné
	 */
	public void supprimerEntreprise() {
		int row = fp.getTableauEntreprise().getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(fp, "Veuillez sélectionner une Entreprise à supprimer.", "Erreur", JOptionPane.WARNING_MESSAGE);
			return;
		}

		String siret = fp.getTableauEntreprise().getValueAt(row, 0).toString();

		// Demande la confirmation à l'utilisateur
		int confirm = JOptionPane.showConfirmDialog(
				fp,
				"Voulez-vous vraiment supprimer l'entreprise " + siret +  " ?",
				"Confirmation",
				JOptionPane.YES_NO_OPTION);

		if (confirm != JOptionPane.YES_OPTION) {
			return;
		}

		try {
			DaoEntreprise daoEntreprise = new DaoEntreprise();
			Entreprise entreprise = daoEntreprise.findById(siret);

			if (entreprise == null) {
				JOptionPane.showMessageDialog(fp, "Entreprise introuvable dans la base.", "Erreur", JOptionPane.ERROR_MESSAGE);
				return;
			}
			daoEntreprise.delete(entreprise);
			afficherTableauEntreprise(); // Rafraichis le tableau

			JOptionPane.showMessageDialog(fp, "Entreprise supprimé avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);

		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(fp, "Erreur lors de la suppression de l'Entreprise : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
		}

	}




	//----------------------- Archives----------------------------------------------------------

	/**
	 * Archive un travail sélectionné (On ne supprime pas les travaux)
	 */

	// =========================================================
	// ARCHIVES : méthodes pour chaque entité (rafraîchissement)
	// =========================================================
	/**
	 * Affiche les competeurs archivés
	 */
	public void afficherTableauCompteursArchives() {
		try {
			DaoCompteur dao = new DaoCompteur();
			List<Compteur> archives = dao.findAllArchives();
			DefaultTableModel model = (DefaultTableModel) fp.getTableauCompteurArchiver().getModel();
			model.setRowCount(0);

			for (Compteur c : archives) {
				model.addRow(new Object[] {
						c.getId_compteur(),
						c.getType(),
						c.getIndex(),
						c.getId_variable(),
						c.getNumero_fiscale(),
						c.getIdBatiment()
				});
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					fp,
					e.getMessage(),
					"Erreur",
					JOptionPane.ERROR_MESSAGE
					);
		}
	}

	/**
	 * Restaure les compteurs archives
	 */
	private void restaurerArchiveCompteur() {

		JTable tableCompArch = fp.getTableauCompteurArchiver();

		int ColonneCompArch = tableCompArch.getSelectedRow();

		if (ColonneCompArch == -1) {
			JOptionPane.showMessageDialog(
					fp,
					"Veuillez sélectionner un compteur à restaurer.",
					"Aucune sélection",
					JOptionPane.WARNING_MESSAGE
					);
			return;
		}

		String idComp = tableCompArch.getValueAt(ColonneCompArch, 0).toString();

		int confirm = JOptionPane.showConfirmDialog(
				fp,
				"Voulez-vous vraiment restaurer ce compteur ?",
				"Confirmation",
				JOptionPane.YES_NO_OPTION
				);

		if (confirm != JOptionPane.YES_OPTION) return;

		try {
			DaoCompteur dao = new DaoCompteur();
			dao.restaurerById(idComp);   
			afficherTableauCautionArchives();

			JOptionPane.showMessageDialog(
					fp,
					"Compteur restaurer avec succès.",
					"Restaurer",
					JOptionPane.INFORMATION_MESSAGE
					);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(
					fp,
					"Erreur lors de la restauration du compteur.",
					"Erreur",
					JOptionPane.ERROR_MESSAGE
					);
			e.printStackTrace();
		}
	}




	/**
	 * Affiche les locataires archivés
	 */
	public void afficherTableauLocatairesArchives() {
		try {
			DaoLocataire dao = new DaoLocataire();
			List<Locataire> archives = dao.findAllArchives();
			DefaultTableModel model = (DefaultTableModel) fp.getTableauLocataireArchives().getModel();
			model.setRowCount(0);

			for (Locataire l : archives) {
				model.addRow(new Object[] {
						l.getIdLocataire(),
						l.getNom(),
						l.getPrenom(),
						l.getDate_naissance(),
						l.getGenre(),
						l.getEmail(),
						l.getAdresse(),
						l.getVille(),
						l.getCode_postale(),
						l.getTelephone()
				});
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					fp,
					e.getMessage(),
					"Erreur",
					JOptionPane.ERROR_MESSAGE
					);

		}}

	private void restaurerArchiveLocataire() {

		JTable tableLocArch = fp.getTableauLocataireArchives();

		int ColonneLocArch = tableLocArch.getSelectedRow();

		if (ColonneLocArch == -1) {
			JOptionPane.showMessageDialog(
					fp,
					"Veuillez sélectionner un locataire à restaurer.",
					"Aucune sélection",
					JOptionPane.WARNING_MESSAGE
					);
			return;
		}

		String idLocataire = tableLocArch.getValueAt(ColonneLocArch, 0).toString();

		int confirm = JOptionPane.showConfirmDialog(
				fp,
				"Voulez-vous vraiment restaurer ce locataire ?",
				"Confirmation",
				JOptionPane.YES_NO_OPTION
				);

		if (confirm != JOptionPane.YES_OPTION) return;

		try {
			DaoLocataire dao = new DaoLocataire();
			dao.restaurerById(idLocataire);   
			afficherTableauLocatairesArchives();

			JOptionPane.showMessageDialog(
					fp,
					"Locataire restaurer avec succès.",
					"Archivage",
					JOptionPane.INFORMATION_MESSAGE
					);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(
					fp,
					"Erreur lors de la restauration du locataire.",
					"Erreur",
					JOptionPane.ERROR_MESSAGE
					);
			e.printStackTrace();
		}
	}



	// --------------- Location -------------------------------------------

	/**
	 * Affiche les loyers archivés
	 */

	public void afficherTableauLocationArchive() {
		try {
			DaoLoyer dao = new DaoLoyer();
			List<Loyer> liste = dao.findAllArchiver();
			DefaultTableModel model = (DefaultTableModel) fp.getTableauLocationArchives().getModel();
			model.setRowCount(0);

			for (Loyer l : liste) {
				model.addRow(new Object[] {
						l.getIdLocataire(),
						l.getNumero_fiscale(),
						l.getDate_paiement(),
						l.getMontant_provision(),
						l.getMontant_loyer(),
						l.getMois(),
						l.getMontant_regularisation()
				});
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					fp,
					"Erreur lors de l'archivage du loyer.",
					"Erreur",
					JOptionPane.ERROR_MESSAGE
					);
			e.printStackTrace();
		}
	}

	public void restaurerArchiveLocation() {

		JTable tableLocArch = fp.getTableauLocationArchives();

		int ColonneLocArch = tableLocArch.getSelectedRow();

		if (ColonneLocArch == -1) {
			JOptionPane.showMessageDialog(
					fp,
					"Veuillez sélectionner une locaion à restaurer.",
					"Aucune sélection",
					JOptionPane.WARNING_MESSAGE
					);
			return;
		}

		String idLocation = tableLocArch.getValueAt(ColonneLocArch, 0).toString();

		int confirm = JOptionPane.showConfirmDialog(
				fp,
				"Voulez-vous vraiment restaurer cette location ?",
				"Confirmation",
				JOptionPane.YES_NO_OPTION
				);

		if (confirm != JOptionPane.YES_OPTION) return;

		try {
			DaoLoyer dao = new DaoLoyer();
			dao.restaurerById(idLocation);   
			afficherTableauLocationArchive();

			JOptionPane.showMessageDialog(
					fp,
					"Location restaurer avec succès.",
					"Archivage",
					JOptionPane.INFORMATION_MESSAGE
					);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(
					fp,
					"Erreur lors de la restauration de la location.",
					"Erreur",
					JOptionPane.ERROR_MESSAGE
					);
			e.printStackTrace();
		}

	}
	/**
	 * Affiche les diagnostics archivés
	 */
	public void afficherTableauDiagnosticArchive() {
		try {
			DaoDiagnostic dao = new DaoDiagnostic();
			List<Diagnostic> liste = dao.findAllArchiver();
			DefaultTableModel model = (DefaultTableModel) fp.getTableauDiagnosticArchives().getModel();
			model.setRowCount(0);

			for (Diagnostic dia : liste) {
				model.addRow(new Object[] {
						dia.getReference(),
						dia.getDate_valide(),
						dia.getType_diagnostic(),
						dia.getNumero_Fiscale(),
						dia.getSiret()
				});

			}
		}catch (Exception e) {
			JOptionPane.showMessageDialog(
					fp,
					e.getMessage(),
					"Erreur",
					JOptionPane.ERROR_MESSAGE
					);
		}
	}

	public void restaurerArchiveDiagnostic() {

		JTable tableDiaArch = fp.getTableauDiagnosticArchives();

		int ColonneDiaArch = tableDiaArch.getSelectedRow();

		if (ColonneDiaArch == -1) {
			JOptionPane.showMessageDialog(
					fp,
					"Veuillez sélectionner un diagnostic à restaurer.",
					"Aucune sélection",
					JOptionPane.WARNING_MESSAGE
					);
			return;
		}

		String idDiagnostic = tableDiaArch.getValueAt(ColonneDiaArch, 0).toString();

		int confirm = JOptionPane.showConfirmDialog(
				fp,
				"Voulez-vous vraiment restaurer ce diagnostic ?",
				"Confirmation",
				JOptionPane.YES_NO_OPTION
				);

		if (confirm != JOptionPane.YES_OPTION) return;

		try {
			DaoDiagnostic dao = new DaoDiagnostic();
			dao.restaurerById(idDiagnostic);   
			afficherTableauLocationArchive();

			JOptionPane.showMessageDialog(
					fp,
					"Diagnostic restaurer avec succès.",
					"Archivage",
					JOptionPane.INFORMATION_MESSAGE
					);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(
					fp,
					"Erreur lors de la restauration du diagnostic.",
					"Erreur",
					JOptionPane.ERROR_MESSAGE
					);
			e.printStackTrace();
		}

	}

	/**
	 * Affiche les facture archivés
	 */
	public void afficherTableauFactureArchive() {
		try {
			DaoFacture dao = new DaoFacture();
			List<Facture> liste = dao.findAllArchiver();
			DefaultTableModel model = (DefaultTableModel) fp.getTableauFactureArchives().getModel();
			model.setRowCount(0);

			for (Facture facture : liste) {
				model.addRow(new Object[] {
						facture.getNumero(),
						facture.getDate_facture(),
						facture.getType_travaux(),
						facture.getMontant(),
						facture.getMode_paiement(),
						facture.getNumero_devis(),
						facture.getNature(),
						facture.getDate_devis(),
				});
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					fp,
					e.getMessage(),
					"Erreur",
					JOptionPane.ERROR_MESSAGE
					);
		}
	}


	public void restaurerArchiveFacture() {

		JTable tableFactArch = fp.getTableauFactureArchives();

		int ColonneFactArch = tableFactArch.getSelectedRow();

		if (ColonneFactArch == -1) {
			JOptionPane.showMessageDialog(
					fp,
					"Veuillez sélectionner une facture à restaurer.",
					"Aucune sélection",
					JOptionPane.WARNING_MESSAGE
					);
			return;
		}

		String idFacture = tableFactArch.getValueAt(ColonneFactArch, 0).toString();

		int confirm = JOptionPane.showConfirmDialog(
				fp,
				"Voulez-vous vraiment restaurer cette facture ?",
				"Confirmation",
				JOptionPane.YES_NO_OPTION
				);

		if (confirm != JOptionPane.YES_OPTION) return;

		try {
			DaoFacture dao = new DaoFacture();
			dao.restaurerById(idFacture);   
			afficherTableauLocationArchive();

			JOptionPane.showMessageDialog(
					fp,
					"Facture restaurer avec succès.",
					"Archivage",
					JOptionPane.INFORMATION_MESSAGE
					);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(
					fp,
					"Erreur lors de la restauration de la facture.",
					"Erreur",
					JOptionPane.ERROR_MESSAGE
					);
			e.printStackTrace();
		}

	}

	public void afficherTableauContratDeLocationArchives() {
		try {
			DaoContratDeLocation dao = new DaoContratDeLocation();
			List<ContratDeLocation> liste = dao.findAllArchive();
			DefaultTableModel model = (DefaultTableModel) fp.getTableauContratDeLocationArchives().getModel();
			model.setRowCount(0);

			for (ContratDeLocation contrat : liste) {
				model.addRow(new Object[] {
						contrat.getIdContrat(),
						contrat.getDate_debut(),
						contrat.getTrimestre(),
						contrat.getDate_sortie(),
						contrat.getLoyer_aPayer(),
						contrat.getIRL(),
						contrat.getProvisions_charges(),
						contrat.isSolde_tout_compte_effectue(),
						contrat.getDuree(),
						contrat.getNumero_fiscale(),
						contrat.getId_locataire()
				});
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					fp,
					e.getMessage(),
					"Erreur",
					JOptionPane.ERROR_MESSAGE
					);
		}
	}
	
	public void restaurerArchiveContratDeLocation() {

		JTable tableContratDeLocationArch = fp.getTableauContratDeLocationArchives();

		int ColonneContratDeLocationArch = tableContratDeLocationArch.getSelectedRow();

		if (ColonneContratDeLocationArch == -1) {
			JOptionPane.showMessageDialog(
					fp,
					"Veuillez sélectionner un contrat de location à restaurer.",
					"Aucune sélection",
					JOptionPane.WARNING_MESSAGE
					);
			return;
		}

		String idContratDeLocation = tableContratDeLocationArch.getValueAt(ColonneContratDeLocationArch, 0).toString();

		int confirm = JOptionPane.showConfirmDialog(
				fp,
				"Voulez-vous vraiment restaurer ce contrat de location ?",
				"Confirmation",
				JOptionPane.YES_NO_OPTION
				);

		if (confirm != JOptionPane.YES_OPTION) {
			return;
		}

		try {
			DaoContratDeLocation dao = new DaoContratDeLocation();
			dao.restaurerById(idContratDeLocation);
			afficherTableauLocationArchive();

			JOptionPane.showMessageDialog(
					fp,
					"Contrat de location restaurer avec succès.",
					"Archivage",
					JOptionPane.INFORMATION_MESSAGE
					);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(
					fp,
					"Erreur lors de la restauration de la facture.",
					"Erreur",
					JOptionPane.ERROR_MESSAGE
					);
			e.printStackTrace();
		}

	}

	// =============================================
	// METHODES POUR LA PAGE FACTURE
	// =============================================
	public void afficherTableauAssurances() {
		try {
			//On récupere tous les impots depuis la BDD une seule fois
			DaoAssurance daoAss = new DaoAssurance();
			List<Assurance> liste =  daoAss.findAll();

			DefaultTableModel model = (DefaultTableModel) fp.getTableauAssurance().getModel();
			model.setRowCount(0);

			DefaultTableModel modelPrincipal = (DefaultTableModel) fp.getTableauFacture().getModel();
			for (int i = 0; i < modelPrincipal.getRowCount(); i++) {
				String numFactureVisible = modelPrincipal.getValueAt(i, 1).toString();

				for (Assurance ass : liste) {
					if (numFactureVisible.equalsIgnoreCase(ass.getNumero())) {
						Object[] row = {
								ass.getId_assurance(),
								ass.getType(),
								ass.getAnnee(),
								ass.getNumero(),
						};
						model.addRow(row);
					}
				}
			}
			fp.getTableauAssurance().revalidate();
			fp.getTableauAssurance().repaint();


		} catch (SQLException e) {
			JOptionPane.showMessageDialog(fp,
					"Erreur lors de la récupération des assurances : " + e.getMessage(),
					"Erreur", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	public void afficherTableauFacturesAgence() {
		try {
			// Récupérer toutes les factures de la BDD
			DaoFacture daoFacture = new DaoFacture();
			List<Facture> listeFactures = daoFacture.findAll();

			DefaultTableModel model = (DefaultTableModel) fp.getTableauAgence().getModel();
			model.setRowCount(0);

			// Filtrer les factures pour afficher seulement les factures agence
			// (celles avec type_travaux = "Agence")
			for (Facture facture : listeFactures) {
				if ("Agence".equalsIgnoreCase(facture.getType_travaux())) {
					Object[] row = {
							facture.getNature(),  // Agence
							facture.getNumero(),
							facture.getDate_facture(),
							facture.getType_travaux(),
							facture.getMontant(),
							facture.getMode_paiement()
					};
					model.addRow(row);
				}
			}
			fp.getTableauAgence().revalidate();
			fp.getTableauAgence().repaint();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(fp,
					"Erreur lors de la récupération des factures agence : " + e.getMessage(),
					"Erreur", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	public void afficherTableTravauxFac() {
		try {
			//On récupere tous les impots depuis la BDD une seule fois
			DaoTravaux daoTravaux = new DaoTravaux(); 
			List<Travaux> listeTravaux = daoTravaux.findAll();

			DefaultTableModel model = (DefaultTableModel) this.fp.
					getTableauTravaux().getModel();
			model.setRowCount(0);

			DefaultTableModel modelPrincipal = (DefaultTableModel) fp.getTableauFacture().getModel();
			for (int i = 0; i < modelPrincipal.getRowCount(); i++) {
				String numFactureVisible = modelPrincipal.getValueAt(i, 1).toString();
				for (Travaux travaux: listeTravaux) {
					if (numFactureVisible.equalsIgnoreCase(travaux.getNumero())) {
						Object[] row = {
								travaux.getNumero(),
								travaux.getRaison(),
								travaux.getId_Travaux()
						};
						model.addRow(row);
					}
				}
			}
			this.fp.getTableauTravaux().revalidate();
			this.fp.getTableauTravaux().repaint();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this.fp, "Erreur lors de la récupération des bâtiments : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	public void afficherTableVariable() {
		try {
			//On récupere tous les impots depuis la BDD une seule fois
			DaoVariable daoVariable = new DaoVariable(); 
			List<Variable> listeVariable = daoVariable.findAll();

			DefaultTableModel model = (DefaultTableModel) this.fp.getTableauVariable().getModel();
			model.setRowCount(0);
			for (Variable variable: listeVariable) {
				System.out.println("DEBUG: Prix unitaire = " + variable.getPrix_unitaire());
				Object[] row = {
						variable.getNumero(),
						variable.getId_Variable(),
						variable.getService(),
						variable.getFournisseur(),
						variable.getEau_gaz_electricite(),
						String.valueOf(variable.getPrix_unitaire())
				};
				model.addRow(row);
			}
			this.fp.getTableauVariable().revalidate();
			this.fp.getTableauVariable().repaint();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this.fp, "Erreur lors de la récupération des variable : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	// ---------------------------------------- Caution ----------------------------------------
	public void afficherTableauCautionArchives() {
		try {
			DaoCaution dao = new DaoCaution();
			List<Caution> archives = dao.findAllArchives();
			DefaultTableModel model = (DefaultTableModel) fp.getTableauCautionArchives().getModel();
			model.setRowCount(0);

			for (Caution c : archives) {
				model.addRow(new Object[] {
						c.getId_caution(),
						c.getNom(),
						c.getPrenom(),
						c.getAdresse(),
						c.getVille(),
						c.getCode_postale(),
						c.getProfession(),
						c.getType_contrat_travail(),
						c.getDate_naissance(),
						c.getIdContrat(),
						c.getAdresseElectronique(),
						c.getNumeroTel(),
						c.getQualiteBailleur()
				});
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					fp,
					e.getMessage(),
					"Erreur",
					JOptionPane.ERROR_MESSAGE
					);
		}
	}

	private void restaurerArchiveCaution() {

		JTable tableCautionArch = fp.getTableauCautionArchives();

		int ColonneCautionArch = tableCautionArch.getSelectedRow();

		if (ColonneCautionArch == -1) {
			JOptionPane.showMessageDialog(
					fp,
					"Veuillez sélectionner une caution à restaurer.",
					"Aucune sélection",
					JOptionPane.WARNING_MESSAGE
					);
			return;
		}

		String idCaution = tableCautionArch.getValueAt(ColonneCautionArch, 0).toString();

		int confirm = JOptionPane.showConfirmDialog(
				fp,
				"Voulez-vous vraiment restaurer cette caution ?",
				"Confirmation",
				JOptionPane.YES_NO_OPTION
				);

		if (confirm != JOptionPane.YES_OPTION) return;

		try {
			DaoCaution dao = new DaoCaution();
			dao.restaurerById(idCaution);   
			afficherTableauCautionArchives();

			JOptionPane.showMessageDialog(
					fp,
					"Locataire restaurer avec succès.",
					"Archivage",
					JOptionPane.INFORMATION_MESSAGE
					);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(
					fp,
					"Erreur lors de la restauration du locataire.",
					"Erreur",
					JOptionPane.ERROR_MESSAGE
					);
			e.printStackTrace();
		}
	}


	public void rendreVisible(JInternalFrame fenetre) {
		fp.getDesktopPane().add(fenetre);

		// Bloquer le contenu principal (toutes les pages + menu + boutons)
		fp.setContenuBloque(true);



		fenetre.setVisible(true);
		try {
			fenetre.setSelected(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// Quand la fenêtre interne se ferme, on débloque le contenu
		fenetre.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
			@Override
			public void internalFrameClosed(javax.swing.event.InternalFrameEvent e) {
				fp.setContenuBloque(false);
			}

			@Override
			public void internalFrameClosing(javax.swing.event.InternalFrameEvent e) {
				fp.setContenuBloque(false);
			}
		});
	}

	// Méthodes d'actions rapides pour la page d'accueil
	public void openAjouterFacture() {
		Vue.ajouter.FenetreAjouterFacture fenetreAjouterFacture = new Vue.ajouter.FenetreAjouterFacture(this);
		fp.getDesktopPane().add(fenetreAjouterFacture);
		fenetreAjouterFacture.setVisible(true);
		try {
			fenetreAjouterFacture.setSelected(true);
		} catch (java.beans.PropertyVetoException ex) {
			ex.printStackTrace();
		}
		fp.setContenuBloque(true);
		
		fenetreAjouterFacture.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
			@Override
			public void internalFrameClosed(javax.swing.event.InternalFrameEvent e) {
				fp.setContenuBloque(false);
			}

			@Override
			public void internalFrameClosing(javax.swing.event.InternalFrameEvent e) {
				fp.setContenuBloque(false);
			}
		});
	}

	public void openAjouterFactureAgence() {
		Vue.ajouter.FenetreAjouterFacture fenetreAjouterFacture = new Vue.ajouter.FenetreAjouterFacture(this);
		fp.getDesktopPane().add(fenetreAjouterFacture);
		fenetreAjouterFacture.setVisible(true);
		try {
			fenetreAjouterFacture.setSelected(true);
		} catch (java.beans.PropertyVetoException ex) {
			ex.printStackTrace();
		}
		fp.setContenuBloque(true);
		
		fenetreAjouterFacture.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
			@Override
			public void internalFrameClosed(javax.swing.event.InternalFrameEvent e) {
				fp.setContenuBloque(false);
				// Rafraîchir le tableau des factures agence après l'ajout
				afficherTableauFacturesAgence();
			}

			@Override
			public void internalFrameClosing(javax.swing.event.InternalFrameEvent e) {
				fp.setContenuBloque(false);
			}
		});
	}

	public void openAjouterLocataire() {
		Vue.ajouter.FenetreLocataireAjouter fenetreAjouterLocataire = new Vue.ajouter.FenetreLocataireAjouter(this);
		fp.getDesktopPane().add(fenetreAjouterLocataire);
		fenetreAjouterLocataire.setVisible(true);
		try {
			fenetreAjouterLocataire.setSelected(true);
		} catch (java.beans.PropertyVetoException ex) {
			ex.printStackTrace();
		}
		fp.setContenuBloque(true);
		
		fenetreAjouterLocataire.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
			@Override
			public void internalFrameClosed(javax.swing.event.InternalFrameEvent e) {
				fp.setContenuBloque(false);
			}

			@Override
			public void internalFrameClosing(javax.swing.event.InternalFrameEvent e) {
				fp.setContenuBloque(false);
			}
		});
	}

	public void openAjouterBien() {
		Vue.ajouter.FenetreAjouterBienLouable fenetreAjouterBien = new Vue.ajouter.FenetreAjouterBienLouable(this, fp.getDesktopPane());
		fp.getDesktopPane().add(fenetreAjouterBien);
		fenetreAjouterBien.setVisible(true);
		try {
			fenetreAjouterBien.setSelected(true);
		} catch (java.beans.PropertyVetoException ex) {
			ex.printStackTrace();
		}
		fp.setContenuBloque(true);
		
		fenetreAjouterBien.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
			@Override
			public void internalFrameClosed(javax.swing.event.InternalFrameEvent e) {
				fp.setContenuBloque(false);
			}

			@Override
			public void internalFrameClosing(javax.swing.event.InternalFrameEvent e) {
				fp.setContenuBloque(false);
			}
		});
	}

	// Méthode pour calculer les charges
	public void openCalculerCharges() {
		FenetreCalculerCharges fenetreCalculerCharges = new FenetreCalculerCharges(this);
		fp.getDesktopPane().add(fenetreCalculerCharges);
		fenetreCalculerCharges.setVisible(true);
		try {
			fenetreCalculerCharges.setSelected(true);
		} catch (java.beans.PropertyVetoException ex) {
			ex.printStackTrace();
		}
		fp.setContenuBloque(true);
		
		fenetreCalculerCharges.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
			@Override
			public void internalFrameClosed(javax.swing.event.InternalFrameEvent e) {
				fp.setContenuBloque(false);
			}

			@Override
			public void internalFrameClosing(javax.swing.event.InternalFrameEvent e) {
				fp.setContenuBloque(false);
			}
		});
	}

	public void exporterFacturesAgence() {
		try {
			JTable tableauAgence = fp.getTableauAgence();
			DefaultTableModel model = (DefaultTableModel) tableauAgence.getModel();

			if (model.getRowCount() == 0) {
				JOptionPane.showMessageDialog(fp,
						"Aucune facture agence à exporter.",
						"Information", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			// Ouvrir un dialogue de sélection de fichier
			FileDialog dialog = new FileDialog((Frame) null, "Exporter les factures agence", FileDialog.SAVE);
			dialog.setFile("factures_agence.csv");
			dialog.setVisible(true);

			String directory = dialog.getDirectory();
			String file = dialog.getFile();

			if (directory == null || file == null) {
				return; // L'utilisateur a annulé
			}

			String filePath = directory + file;

			// Écrire les données dans le fichier CSV
			java.io.FileWriter writer = new java.io.FileWriter(filePath);
			
			// Écrire les en-têtes
			for (int i = 0; i < model.getColumnCount(); i++) {
				if (i > 0) writer.write(",");
				writer.write("\"" + model.getColumnName(i) + "\"");
			}
			writer.write("\n");

			// Écrire les données
			for (int row = 0; row < model.getRowCount(); row++) {
				for (int col = 0; col < model.getColumnCount(); col++) {
					if (col > 0) writer.write(",");
					Object value = model.getValueAt(row, col);
					if (value != null) {
						writer.write("\"" + value.toString().replace("\"", "\"\"") + "\"");
					}
				}
				writer.write("\n");
			}
			writer.close();

			JOptionPane.showMessageDialog(fp,
					"Factures agence exportées avec succès.",
					"Succès", JOptionPane.INFORMATION_MESSAGE);

		} catch (java.io.IOException e) {
			JOptionPane.showMessageDialog(fp,
					"Erreur lors de l'export : " + e.getMessage(),
					"Erreur", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

}
