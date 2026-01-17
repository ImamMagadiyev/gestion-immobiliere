package Controleur.Affichage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import Modele.BienLouable;
import Modele.Compteur;
import Modele.ContratDeLocation;
import Modele.Locataire;
import Modele.ReleveCompteur;
import Modele.Variable;
import Modele.dao.DaoBienLouable;
import Modele.dao.DaoCompteur;
import Modele.dao.DaoContratDeLocation;
import Modele.dao.DaoLocataire;
import Modele.dao.DaoReleveCompteur;
import Modele.dao.DaoVariable;
import Vue.Calculer.FenetreCalculerCharges;

public class Gestion_Calculer_Charges implements ActionListener{

	private FenetreCalculerCharges fen ;
	public Gestion_Calculer_Charges(FenetreCalculerCharges fen) {
		this.fen = fen;

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		String texte_btn = btn.getText();

		switch(texte_btn) {
		case "Annuler":
			this.fen.dispose();
			break;
		case "Valider":
			JOptionPane.showConfirmDialog(fen, "Validation confirmé");
			break;
		case "Calculer":
			try {
				// Récupérer les valeurs sélectionnées
				String idBienLouable = (String) fen.getComboBien().getSelectedItem();
				String anneeStr = (String) fen.getComboAnnee().getSelectedItem();
				
				if (idBienLouable == null || idBienLouable.equals("Tous") || anneeStr == null) {
					JOptionPane.showMessageDialog(fen, "Veuillez sélectionner un bien et une année", 
						"Erreur", JOptionPane.WARNING_MESSAGE);
					break;
				}
				
				int annee = Integer.parseInt(anneeStr);
				
				// Appeler la méthode de calcul
				double chargesReelles = calculerChargesReelles(idBienLouable, annee);
				
				// Afficher le résultat dans le champ
				fen.getChampCharges().setText(String.format("%.2f", chargesReelles));
				
				// Calculer et afficher le résultat (provisions - charges réelles)
				String provisionsStr = fen.getChampProvisions().getText();
				if (!provisionsStr.isEmpty()) {
					try {
						double provisions = Double.parseDouble(provisionsStr);
						double resultat = provisions - chargesReelles;
						fen.getChampResultat().setText(String.format("%.2f", resultat));
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(fen, "Erreur: Provisions invalides", 
							"Erreur", JOptionPane.ERROR_MESSAGE);
					}
				}
				
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(fen, "Erreur lors du calcul: " + ex.getMessage(), 
					"Erreur", JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
			break;
		}

	}

	public void chargerLocataires() {
		try {
			// Récupérer tous les contrats de location
			DaoContratDeLocation daoContrat = new DaoContratDeLocation();
			List<ContratDeLocation> contrats = daoContrat.findAll();
			
			// Récupérer tous les locataires
			DaoLocataire daoLocataire = new DaoLocataire();
			List<Locataire> tousLesLocataires = daoLocataire.findAll();
			
			// Créer un set des IDs de locataires ayant des contrats
			Set<String> idLocatairesAvecContrats = new HashSet<>();
			for (ContratDeLocation c : contrats) {
				if (c.getId_locataire() != null && !c.getId_locataire().isEmpty()) {
					idLocatairesAvecContrats.add(c.getId_locataire());
				}
			}

			JComboBox<String> comboLocataires = fen.getComboLoc();
			comboLocataires.removeAllItems();
			comboLocataires.addItem("Tous"); 

			Set<String> locatairesTrié = new HashSet<>();
			// Ajouter seulement les locataires qui ont des contrats
			for (Locataire l : tousLesLocataires) {
				if (l.getNom() != null && l.getPrenom() != null && 
					idLocatairesAvecContrats.contains(l.getIdLocataire())) {
					locatairesTrié.add(l.getNom() + " " + l.getPrenom());
				}
			}

			for (String nomComplet : locatairesTrié) {
				comboLocataires.addItem(nomComplet);
			}
			
			// Ajouter un listener pour charger le bien louable automatiquement
			comboLocataires.addPopupMenuListener(new PopupMenuListener() {
				@Override
				public void popupMenuWillBecomeVisible(PopupMenuEvent e) {}

				@Override
				public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
					// Quand un locataire est sélectionné, charger son bien
					chargerBiensPourLocataire();
				}

				@Override
				public void popupMenuCanceled(PopupMenuEvent e) {}
			});
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(fen,
					"Erreur lors du chargement des locataires",
					"Erreur",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Charge automatiquement le bien louable et la provision pour le locataire sélectionné
	 */
	public void chargerBiensPourLocataire() {
		try {
			String locataireSelectionne = (String) fen.getComboLoc().getSelectedItem();
			
			// Si "Tous" est sélectionné, charger tous les biens
			if (locataireSelectionne == null || locataireSelectionne.equals("Tous")) {
				chargerBiens();
				fen.getChampProvisions().setText("");
				return;
			}
			
			// Extraire le nom et prénom du locataire
			String[] parts = locataireSelectionne.split(" ");
			if (parts.length < 2) {
				chargerBiens();
				fen.getChampProvisions().setText("");
				return;
			}
			
			String nom = parts[0];
			String prenom = parts[1];
			
			System.out.println("DEBUG: Locataire sélectionné: " + nom + " " + prenom);
			
			// Récupérer tous les contrats de location
			DaoContratDeLocation daoContrat = new DaoContratDeLocation();
			DaoLocataire daoLocataire = new DaoLocataire();
			List<ContratDeLocation> contrats = daoContrat.findAll();
			
			// Trouver l'ID du locataire
			Locataire locataire = null;
			List<Locataire> locataires = daoLocataire.findAll();
			for (Locataire l : locataires) {
				if (l.getNom() != null && l.getPrenom() != null && 
					l.getNom().equalsIgnoreCase(nom) && l.getPrenom().equalsIgnoreCase(prenom)) {
					locataire = l;
					break;
				}
			}
			
			if (locataire == null) {
				System.out.println("❌ Locataire non trouvé");
				chargerBiens();
				fen.getChampProvisions().setText("");
				return;
			}
			
			String idLocataire = locataire.getIdLocataire();
			System.out.println("✓ Locataire ID: " + idLocataire);
			System.out.println("  Total contrats en BD: " + contrats.size());
			
			// DEBUG: Afficher tous les contrats
			for (ContratDeLocation c : contrats) {
				System.out.println("  - Contrat: Id_Locataire=" + c.getId_locataire() + ", Numero_Fiscal=" + c.getNumero_fiscale() + ", Provisions=" + c.getProvisions_charges());
			}
			
			// Trouver les biens louables liés à ce locataire ET récupérer la provision
			Set<String> biensForLocataire = new HashSet<>();
			double provisionTrouvee = 0;
			
			for (ContratDeLocation contrat : contrats) {
				if (contrat.getId_locataire() != null && contrat.getId_locataire().equals(idLocataire)) {
					String numeroFiscale = contrat.getNumero_fiscale();
					System.out.println("  ✓ Contrat trouvé pour ce locataire: Numero_Fiscal=" + numeroFiscale);
					if (numeroFiscale != null && !numeroFiscale.isEmpty()) {
						biensForLocataire.add(numeroFiscale);
						System.out.println("✓ Bien trouvé: " + numeroFiscale);
						
						// Récupérer la provision du contrat
						double provisions = contrat.getProvisions_charges();
						if (provisions > 0) {
							provisionTrouvee = provisions;
							System.out.println("✓ Provision trouvée: " + provisions + "€");
						}
					} else {
						System.out.println("  ❌ Numero_fiscal est null ou vide dans le contrat");
					}
				}
			}
			
			// Remplir le combo des biens
			JComboBox<String> comboBiens = fen.getComboBien();
			comboBiens.removeAllItems();
			comboBiens.addItem("Tous");
			
			for (String bien : biensForLocataire) {
				comboBiens.addItem(bien);
			}
			
			// Sélectionner le premier bien si disponible et charger la provision
			if (biensForLocataire.size() > 0) {
				comboBiens.setSelectedIndex(1); // Index 1 pour éviter "Tous"
				System.out.println("✓ Bien sélectionné automatiquement");
				
				// Charger la provision automatiquement
				if (provisionTrouvee > 0) {
					fen.getChampProvisions().setText(String.format("%.2f", provisionTrouvee));
					System.out.println("✓ Provision chargée automatiquement: " + provisionTrouvee + "€");
				} else {
					fen.getChampProvisions().setText("");
				}
			} else {
				System.out.println("⚠️ Aucun bien trouvé pour ce locataire");
				fen.getChampProvisions().setText("");
			}
			
		} catch (SQLException e) {
			System.err.println("Erreur lors du chargement des biens pour le locataire: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void chargerAnnees() {
		JComboBox<String> comboAnnee = fen.getComboAnnee();
		comboAnnee.removeAllItems();
		
		// Ajouter les années de 2020 à l'année actuelle + 1
		int anneeActuelle = java.time.LocalDate.now().getYear();
		for (int annee = 2020; annee <= anneeActuelle + 1; annee++) {
			comboAnnee.addItem(String.valueOf(annee));
		}
		
		// Sélectionner l'année actuelle par défaut
		comboAnnee.setSelectedItem(String.valueOf(anneeActuelle));
	}

	public void chargerBiens() {

		try {
			//liste de tout les biens
			DaoBienLouable dao = new DaoBienLouable();
			List<BienLouable> biens = dao.findAll();

			JComboBox<String> comboBiens = fen.getComboBien();
			comboBiens.removeAllItems();
			comboBiens.addItem("Tous"); 

			Set<String> bienTrié = new HashSet<>();
			for (BienLouable b : biens) {
				if (b.getNumero_fiscale() != null ) {
					bienTrié.add(b.getNumero_fiscale());
				}
			}

			for (String numB : bienTrié) {
				comboBiens.addItem(numB);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(fen,
					"Erreur lors du chargement des locataires",
					"Erreur",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	/**
	 * Calcule les charges réelles basées sur les relevés de compteurs
	 * @param idBienLouable ID du bien louable (numéro fiscale)
	 * @param annee L'année pour laquelle calculer les charges
	 * @return Le montant total des charges réelles en euros
	 */
	public double calculerChargesReelles(String idBienLouable, int annee) {
		double chargesReelles = 0;
		
		System.out.println("\n========== DEBUG CALCUL CHARGES ==========");
		System.out.println("Bien louable sélectionné: " + idBienLouable);
		System.out.println("Année sélectionnée: " + annee);
		
		try {
			// 1. Récupérer le bien louable pour avoir son bâtiment
			DaoBienLouable daoBien = new DaoBienLouable();
			BienLouable bien = daoBien.findById(idBienLouable);
			
			if (bien == null) {
				System.err.println("❌ Bien louable non trouvé: " + idBienLouable);
				return 0;
			}
			
			System.out.println("✓ Bien trouvé: " + bien.getNumero_fiscale());
			
			String idBatiment = bien.getBatiment();
			
			if (idBatiment == null || idBatiment.trim().isEmpty()) {
				System.err.println("❌ Bâtiment non assigné au bien: " + idBienLouable);
				return 0;
			}
			
			System.out.println("✓ Bâtiment: " + idBatiment);
			
			// 2. Récupérer tous les compteurs du bâtiment
			DaoCompteur daoCompteur = new DaoCompteur();
			List<Compteur> compteurs = daoCompteur.findAll();
			
			System.out.println("✓ Total compteurs en BD: " + compteurs.size());
			System.out.println("  Recherche des compteurs pour le bâtiment: " + idBatiment);
			
			// DEBUG: Afficher tous les compteurs et leur bâtiment
			for (Compteur c : compteurs) {
				System.out.println("  - Compteur: " + c.getId_compteur() + " → Bâtiment: " + c.getIdBatiment() + " (Type: " + c.getType() + ")");
			}
			
			DaoReleveCompteur daoReleve = new DaoReleveCompteur();
			DaoVariable daoVariable = new DaoVariable();
			
			int compteursTrouvés = 0;
			
			// 3. Pour chaque compteur du bâtiment
			for (Compteur compteur : compteurs) {
				// CORRECTION: Vérifier que getIdBatiment() n'est pas null
				if (compteur.getIdBatiment() == null || !compteur.getIdBatiment().equals(idBatiment)) {
					continue; // Passer aux compteurs d'un autre bâtiment
				}
				
				compteursTrouvés++;
				System.out.println("\n  → Compteur #" + compteursTrouvés + ": " + compteur.getId_compteur() + 
					" (Type: " + compteur.getType() + ")");
				
				// 4. Récupérer tous les relevés de ce compteur
				List<ReleveCompteur> relevesCompteur = daoReleve.findAll();
				
				System.out.println("    Total relevés en BD: " + relevesCompteur.size());
				
				// Filtrer pour trouver le premier et dernier relevé de l'année
				ReleveCompteur releveDebut = null;
				ReleveCompteur releveFin = null;
				
				for (ReleveCompteur releve : relevesCompteur) {
					if (releve.getId_compteur() == null || !releve.getId_compteur().equals(compteur.getId_compteur())) {
						continue; // Passer aux relevés d'un autre compteur
					}
					
					if (releve.getDate_releve() == null) {
						continue; // Ignorer les relevés sans date
					}
					
					int an = releve.getDate_releve().getYear();
					
					System.out.println("    - Relevé: " + releve.getDate_releve() + " (année: " + an + ", indice: " + releve.getIndice() + ")");
					
					// Premier relevé de l'année
					if (an == annee) {
						if (releveDebut == null || releve.getDate_releve().isBefore(releveDebut.getDate_releve())) {
							releveDebut = releve;
						}
						
						// Dernier relevé de l'année
						if (releveFin == null || releve.getDate_releve().isAfter(releveFin.getDate_releve())) {
							releveFin = releve;
						}
					}
				}
				
				// 5. Calculer la consommation et le coût
				if (releveDebut != null && releveFin != null && releveDebut != releveFin) {
					int consommation = (int) (releveFin.getIndice() - releveDebut.getIndice());
					
					System.out.println("    ✓ Relevés trouvés: début=" + releveDebut.getDate_releve() + 
						" (indice: " + releveDebut.getIndice() + "), fin=" + releveFin.getDate_releve() + 
						" (indice: " + releveFin.getIndice() + ")");
					System.out.println("    ✓ Consommation: " + consommation);
					
					// Récupérer le tarif associé au compteur (via Variable)
					Variable variable = daoVariable.findById(compteur.getId_variable());
					
					if (variable != null) {
						double tarif = variable.getPrix_unitaire();
						double coutCompteur = consommation * tarif;
						chargesReelles += coutCompteur;
						
						System.out.println("    ✓ Variable trouvée: " + variable.getEau_gaz_electricite() + 
							" (tarif: " + tarif + "€)");
						System.out.println("    ✓ Coût: " + consommation + " × " + tarif + "€ = " + coutCompteur + "€");
					} else {
						System.out.println("    ❌ Variable non trouvée pour ID: " + compteur.getId_variable());
					}
				} else {
					System.out.println("    ❌ Pas de relevés pour l'année " + annee + 
						" (début: " + releveDebut + ", fin: " + releveFin + ")");
				}
			}
			
			System.out.println("\n✓ Compteurs du bâtiment trouvés: " + compteursTrouvés);
			
		} catch (SQLException e) {
			System.err.println("❌ Erreur SQL lors du calcul des charges réelles : " + e.getMessage());
			e.printStackTrace();
		}
		
		System.out.println("\n========== RÉSULTAT FINAL ==========");
		System.out.println("Charges réelles totales = " + chargesReelles + "€");
		System.out.println("====================================\n");
		
		return chargesReelles;
	}
}
