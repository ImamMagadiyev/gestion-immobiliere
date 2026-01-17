package Controleur.Ajouter;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe de base abstraite pour tous les contrôleurs d'ajout.
 * 
 * Fournit une structure commune pour gérer les événements des boutons,
 * notamment le bouton "Annuler" qui ferme toujours la fenêtre.
 * 
 * Les sous-classes doivent implémenter:
 * - enregistrerBoutons() pour attacher les ActionListeners aux boutons
 * - traiterAction() pour gérer les actions spécifiques à chaque fenêtre
 */
public abstract class GestionControleurAjoutBase<T extends Component> implements ActionListener {

    protected T vue;

    /**
     * Constructeur de base qui initialise la vue et enregistre les boutons.
     * @param vue La fenêtre d'ajout associée à ce contrôleur
     */
    public GestionControleurAjoutBase(T vue) {
        this.vue = vue;
        enregistrerBoutons();
    }

    /**
     * Méthode abstraite pour enregistrer les ActionListeners sur les boutons.
     * Chaque sous-classe doit implémenter cette méthode pour attacher
     * this comme ActionListener aux boutons de sa vue spécifique.
     */
    protected abstract void enregistrerBoutons();

    /**
     * Méthode abstraite pour traiter les actions spécifiques.
     * @param nomBouton Le nom du bouton qui a déclenché l'action
     */
    protected abstract void traiterAction(String nomBouton);

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (!(src instanceof Component)) return;

        String name = ((Component) src).getName();
        if (name == null) return;

        // Gestion commune du bouton Annuler
        if (name.contains("Annuler") || name.contains("btnAnnuler")) {
            try {
                vue.getClass().getMethod("dispose").invoke(vue);
            } catch (Exception ex) {
                System.err.println("Impossible de fermer la fenêtre: " + ex.getMessage());
            }
            return;
        }

        // Déléguer les autres actions aux sous-classes
        traiterAction(name);
    }

    /**
     * Méthode utilitaire pour vérifier si des champs sont vides.
     * @param champs Les valeurs des champs à vérifier
     * @return true si au moins un champ est vide, false sinon
     */
    protected boolean champsSontVides(String... champs) {
        for (String champ : champs) {
            if (champ == null || champ.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Méthode utilitaire pour afficher une erreur de champs obligatoires.
     */
    protected void afficherErreurChampsObligatoires() {
        if (vue instanceof javax.swing.JInternalFrame) {
            try {
                vue.getClass().getMethod("afficherErreur", String.class)
                    .invoke(vue, "Veuillez remplir tous les champs obligatoires.");
            } catch (Exception ex) {
                System.err.println("Impossible d'afficher l'erreur: " + ex.getMessage());
            }
        }
    }
}
