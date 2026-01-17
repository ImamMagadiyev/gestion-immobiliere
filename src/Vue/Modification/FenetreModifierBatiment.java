package Vue.Modification;


import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JTextField;

import Controleur.GestionFenetrePrincipale;
import Controleur.Modifier.GestionModifierBatiment;
import Modele.Batiment;
import Vue.Utils;
import Vue.ajouter.FenetreAjouterBatiment;


public class FenetreModifierBatiment extends FenetreAjouterBatiment {

	private static final long serialVersionUID = 1L;
	private Batiment bat;
    private JButton btnModifier;
    private JButton btnModifierCompteur;
    private GestionFenetrePrincipale gfp;
    
    /**
     * Initialise la fenêtre pour modifier un bâtiment existant,
     * préremplit les champs avec les informations actuelles du bâtiment,
     * configure les boutons pour valider les modifications ou modifier les compteurs,
     * et connecte la fenêtre au contrôleur dédié pour gérer les actions.
     */
    public FenetreModifierBatiment(GestionFenetrePrincipale gfp,Batiment bat, JDesktopPane desktop) {
        super(gfp, desktop); 
        this.bat = bat;
        this.desktop = desktop;
        btnAjouterCompteur.setEnabled(true);
   
        setTitle("Modifier un bâtiment");
        titre.setText("Modifier bâtiment");

        getIdField().setText(bat.getIdBatiment());
        getIdField().setEditable(false);
        getAdresseField().setText(bat.getAdresse());
        getVilleField().setText(bat.getVille());
        getCodePostalField().setText(bat.getCodePostale());
        getPeriodeDeConstructionField().setText(bat.getPeriodeDeConstruction().toString());

        getBtnAjouter().setText("Modifier");
        getBtnAjouter().setName("btnModifierBatiment");
        this.btnModifier = getBtnAjouter();

        btnModifierCompteur = Utils.creerBouton("Modifier un compteur");
        btnModifierCompteur.setName("btnModifierCompteur");

        panelBoutons.removeAll();
        panelBoutons.add(btnModifier);
        panelBoutons.add(getBtnAjouterCompteur());
        panelBoutons.add(btnModifierCompteur);
        panelBoutons.add(getBtnAnnuler());

        this.revalidate();
        this.repaint();

        new GestionModifierBatiment(this, bat);
    }

    public JButton getBtnModifier() { return btnModifier; }
    public JButton getBtnModifierCompteur() { return btnModifierCompteur; }
    public JDesktopPane getDesktop() { return desktop ; }

    public JTextField getIdField() { return super.getIdField(); }
    public JTextField getAdresseField() { return super.getAdresseField(); }
    public JTextField getVilleField() { return super.getVilleField(); }
    public JTextField getCodePostalField() { return super.getCodePostalField(); }
    public JTextField getPeriodeDeConstructionField() { return super.getPeriodeDeConstructionField(); }

}
