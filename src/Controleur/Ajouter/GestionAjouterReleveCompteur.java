package Controleur.Ajouter;

import java.time.LocalDate;
import java.util.List;

import Modele.Compteur;
import Modele.ReleveCompteur;
import Modele.dao.DaoCompteur;
import Modele.dao.DaoReleveCompteur;
import Vue.Utils;
import Vue.ajouter.FenetreAjouterReleveCompteur;

public class GestionAjouterReleveCompteur extends GestionControleurAjoutBase<FenetreAjouterReleveCompteur> {

    private List<Compteur> listeCompteurs;

    public GestionAjouterReleveCompteur(FenetreAjouterReleveCompteur vue) {
        super(vue);
        chargerCompteurs();
        verifierEtatDonnees();
    }

    @Override
    protected void enregistrerBoutons() {
        vue.getBtnEnregistrer().addActionListener(this);
        vue.getBtnAnnuler().addActionListener(this);
    }

    @Override
    protected void traiterAction(String nomBouton) {
        if ("btnEnregistrer".equals(nomBouton)) {
            ajouterReleve();
        }
    }

    // =============================
    // Chargement des compteurs
    // =============================
    private void chargerCompteurs() {
        try {
            DaoCompteur dao = new DaoCompteur();
            listeCompteurs = dao.findAll();

            vue.getComboCompteur().removeAllItems();
            vue.getComboCompteur().addItem("Sélectionner un compteur");

            for (Compteur c : listeCompteurs) {
                vue.getComboCompteur().addItem(
                    c.getType() + " - " + c.getId_compteur()
                );
            }

            verifierEtatDonnees();

        } catch (Exception e) {
            vue.afficherErreur("Erreur chargement compteurs : " + e.getMessage());
        }
    }

    // =============================
    // Mise à jour infos compteur
    // =============================
    public void mettreAJourInfosCompteur() {

        int index = vue.getComboCompteur().getSelectedIndex() - 1;

        if (index < 0 || index >= listeCompteurs.size()) {
            vue.getChampIndexAncien().setText("");
            vue.getChampDateDernierReleve().setText("");
            return;
        }

        Compteur compteur = listeCompteurs.get(index);
        vue.getChampIndexAncien().setText(String.valueOf(compteur.getIndex()));

        try {
            DaoReleveCompteur dao = new DaoReleveCompteur();
            List<ReleveCompteur> releves = dao.findAll();

            LocalDate derniereDate = null;

            for (ReleveCompteur r : releves) {
                if (r.getId_compteur().equals(compteur.getId_compteur())) {
                    if (derniereDate == null || r.getDate_releve().isAfter(derniereDate)) {
                        derniereDate = r.getDate_releve();
                    }
                }
            }

            if (derniereDate != null) {
                vue.getChampDateDernierReleve().setText(derniereDate.toString());
            } else {
                vue.getChampDateDernierReleve().setText("Aucun relevé");
            }

        } catch (Exception e) {
            vue.getChampDateDernierReleve().setText("Erreur");
        }
    }

    // =============================
    // VÉRIFICATION DE L'ÉTAT DES DONNÉES
    // =============================
    private void verifierEtatDonnees() {
        try {
            DaoCompteur daoCompteur = new DaoCompteur();
            List<Compteur> listeCompteurs = daoCompteur.findAll();
            
            boolean hasCompteurs = !listeCompteurs.isEmpty();
            
            // Griser le bouton si pas de compteurs
            vue.getBtnEnregistrer().setEnabled(hasCompteurs);
            
            // Afficher un message d'information si nécessaire
            if (!hasCompteurs) {
                vue.afficherErreur("Aucun compteur trouvé dans la base de données. Veuillez d'abord créer des compteurs.");
            }
            
        } catch (Exception ex) {
            System.err.println("Erreur vérification état données : " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // =============================
    // Ajout du relevé
    // =============================
    private void ajouterReleve() {

        // 1. Vérification champs
        if (vue.getComboCompteur().getSelectedIndex() <= 0 ||
            vue.getChampDate().getText().isEmpty() ||
            vue.getChampIndex().getText().isEmpty()) {

            vue.afficherErreur("Veuillez remplir tous les champs.");
            return;
        }

        // 2. Récupération compteur
        Compteur compteur = listeCompteurs.get(
            vue.getComboCompteur().getSelectedIndex() - 1
        );

        LocalDate dateReleve;
        int nouvelIndice;

        // 3. Parsing
        try {
            dateReleve = Utils.parseLocalDate(vue.getChampDate().getText());
            nouvelIndice = Integer.parseInt(vue.getChampIndex().getText());
        } catch (Exception e) {
            vue.afficherErreur("Format de date ou d'indice invalide.");
            return;
        }

        // 4. Vérification index
        if (nouvelIndice < compteur.getIndex()) {
            vue.afficherErreur("Le nouvel indice doit être supérieur ou égal à l'ancien.");
            return;
        }

        try {
            DaoReleveCompteur daoReleve = new DaoReleveCompteur();

            // 5. Recherche du dernier relevé
            LocalDate derniereDate = null;

            for (ReleveCompteur r : daoReleve.findAll()) {
                if (r.getId_compteur().equals(compteur.getId_compteur())) {
                    if (derniereDate == null || r.getDate_releve().isAfter(derniereDate)) {
                        derniereDate = r.getDate_releve();
                    }
                }
            }

            // 6. Vérification date
            if (derniereDate != null && dateReleve.isBefore(derniereDate)) {
                vue.afficherErreur("La date doit être postérieure au dernier relevé.");
                return;
            }

            // 7. Insertion
            ReleveCompteur rc = new ReleveCompteur(
                compteur.getId_compteur(),
                dateReleve,
                nouvelIndice
            );
            daoReleve.create(rc);

            // 8. Mise à jour compteur
            compteur.setIndex(nouvelIndice);
            new DaoCompteur().update(compteur);

            vue.afficherSucces("Relevé enregistré avec succès.");
            vue.dispose();

        } catch (Exception e) {
            vue.afficherErreur("Erreur insertion : " + e.getMessage());
        }
    }
}
