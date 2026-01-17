package Vue.Modification;

import javax.swing.*;
import Controleur.GestionFenetrePrincipale;
import Modele.Locataire;
import Vue.ajouter.FenetreLocataireAjouter;

public class FenetreModifierLocataire extends FenetreLocataireAjouter {

    private static final long serialVersionUID = 1L;

    private Locataire loc;
    private JButton btnModifier;
    private GestionFenetrePrincipale gfp;
    
    /**
     * Initialise la fenêtre pour modifier un locataire existant,
     * préremplit les champs avec les informations actuelles
     * et configure le bouton pour valider les modifications.
     */


    public FenetreModifierLocataire(GestionFenetrePrincipale gfp, Locataire loc) {
        super(); 
        this.loc = loc;
        this.gfp = gfp;

        setTitle("Modifier un locataire");
        getTitre().setText("Modifier un locataire");


        getIdField().setText(loc.getIdLocataire());
        getIdField().setEditable(false); 
        getNomField().setText(loc.getNom());
        getPrenomField().setText(loc.getPrenom());
        getDateNaissanceField().setText(loc.getDate_naissance());
        getGenreCombo().setSelectedItem(loc.getGenre());
        getEmailField().setText(loc.getEmail());
        getAdresseField().setText(loc.getAdresse());
        getVilleField().setText(loc.getVille());
        getCodePostalField().setText(loc.getCode_postale());
        getTelephoneField().setText(loc.getTelephone());


        getBtnAjouter().setText("Modifier");
        getBtnAjouter().setName("btnModifierLocataire");
        btnModifier = getBtnAjouter();
        
        // Remove the GestionAjouterLocataire controller that was added by parent constructor
        // and add the proper GestionModifierLocataire controller
        new Controleur.Modifier.GestionModifierLocataire(this);
    }

    // ---------------- Getters ----------------
    public JButton getBtnModifier() {
        return btnModifier;
    }

    public Locataire getLocataire() {
        return loc;
    }
    
    public GestionFenetrePrincipale getGfp() {
        return gfp;
    }
    
}
