package Controleur.Ajouter;

import Modele.Entreprise;
import Modele.dao.DaoEntreprise;
import Vue.ajouter.FenetreAjouterEntreprise;

public class GestionAjouterEntreprise extends GestionControleurAjoutBase<FenetreAjouterEntreprise> {

    public GestionAjouterEntreprise(FenetreAjouterEntreprise vue) {
        super(vue);
    }

    @Override
    protected void enregistrerBoutons() {
        vue.getBtnAjouter().addActionListener(this);
        vue.getBtnAnnuler().addActionListener(this);
    }

    @Override
    protected void traiterAction(String nomBouton) {
        if ("btnAjouter".equals(nomBouton)) {
            ajouterEntreprise();
        }
    }

    private void ajouterEntreprise() {
        if (champsSontVides(vue.getChampSiret().getText(), vue.getChampNom().getText())) {
            afficherErreurChampsObligatoires();
            return;
        }

        try {
            String siret = vue.getChampSiret().getText().trim();
            String nom = vue.getChampNom().getText().trim();
            String ville = vue.getChampVille().getText().trim();
            String mail = vue.getChampMail().getText().trim();
            String adresse = vue.getChampAdresse().getText().trim();
            String specialite = vue.getChampSpecialite().getText().trim();
            String code_postale = vue.getChampCodePostale().getText().trim();

            Entreprise e = new Entreprise(siret, nom, ville, mail, adresse, specialite, code_postale);

            DaoEntreprise dao = new DaoEntreprise();
            dao.create(e);

            vue.setEntrepriseTemp(e);

            if (vue.getGfp() != null)
                vue.getGfp().afficherTableauEntreprise();

            vue.afficherSucces("Entreprise ajoutée avec succès !\n" + e.toString());
            vue.dispose();

        } catch (Exception ex) {
            vue.afficherErreur("Erreur lors de l'ajout en BD : " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}