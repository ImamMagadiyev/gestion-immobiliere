package Vue.Modification;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import Vue.ajouter.FenetreAjoutBase;

/**
 * Classe de base abstraite pour toutes les fenêtres de modification.
 * 
 * Fournit une structure commune à toutes les fenêtres de modification :
 * - Change le titre et le label
 * - Transforme le bouton "Ajouter" en bouton "Modifier"
 * - Initialise le nom du bouton
 * 
 * Les subclasses doivent :
 * 1. Appeler le super() du constructeur correspondant à l'ajout
 * 2. Passer "Modifier" comme action
 * 3. Implémenter le remplissage des champs spécifiques à l'entité
 */
public abstract class FenetreModificationBase extends FenetreAjoutBase {

    private static final long serialVersionUID = 1L;

    protected JButton btnModifier;

    /**
     * Initialise une fenêtre de modification avec les paramètres de base.
     * Le titre et le label de titre sont automatiquement mis à jour,
     * et le bouton "Ajouter" est transformé en bouton "Modifier".
     * 
     * @param titre_fenetre Titre de la fenêtre (ex: "Modifier un bâtiment")
     * @param titre_contenu Label du contenu (ex: "Modifier bâtiment")
     * @param width Largeur de la fenêtre
     * @param height Hauteur de la fenêtre
     * @param desktop Le JDesktopPane parent
     * @param nomBouton Nom unique du bouton (ex: "btnModifierBatiment")
     */
    protected FenetreModificationBase(String titre_fenetre, String titre_contenu, 
                                      int width, int height, JDesktopPane desktop, 
                                      String nomBouton) {
        super(titre_fenetre, titre_contenu, width, height, desktop);
        
        // Récupérer le bouton "Ajouter" et le transformer en "Modifier"
        btnModifier = panelBoutons.getComponent(0) instanceof JButton 
            ? (JButton) panelBoutons.getComponent(0) 
            : null;
        
        if (btnModifier == null) {
            btnModifier = new JButton("Modifier");
            panelBoutons.add(btnModifier, 0);
        } else {
            btnModifier.setText("Modifier");
            btnModifier.setName(nomBouton);
        }
    }

    /**
     * Retourne le bouton "Modifier".
     */
    public JButton getBtnModifier() {
        return btnModifier;
    }
}
