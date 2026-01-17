package Vue.Modification;


import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDesktopPane;

import Controleur.GestionFenetrePrincipale;
import Controleur.Modifier.GestionModifierBienLouable;
import Modele.Batiment;
import Modele.BienLouable;
import Modele.dao.DaoBatiment;
import Vue.Utils;
import Vue.ajouter.FenetreAjouterBienLouable;


public class FenetreModifierBienLouable extends FenetreAjouterBienLouable {

    private static final long serialVersionUID = 1L;
    private JButton btnModifier, btnModifierCompteur;

    /**
     * Initialise la fenêtre pour modifier un bien louable existant,
     * préremplit les champs avec les informations du bien et du bâtiment associé,
     * configure les boutons pour valider les modifications ou modifier les compteurs,
     * et connecte le formulaire au contrôleur dédié pour gérer les actions.
     */
    public FenetreModifierBienLouable(GestionFenetrePrincipale gfp, JDesktopPane desktop, BienLouable bien) throws SQLException {
        super(gfp, desktop);
        btnAjouterCompteur.setEnabled(true);

        setTitle("Modifier un bien louable");
        titre.setText("Modifier bien louable");

        /**
         * Vérification de la présence d'un bâtiment associé au bien.
         * Si aucun bâtiment n'est lié, on empêche l'ouverture de la fenêtre.
         */
        if (bien.getBatiment() == null || bien.getBatiment().isEmpty())
            throw new SQLException("Le BienLouable n'a pas d'ID de bâtiment !");

        /**
         * Récupération du bâtiment associé via le DAO afin de remplir
         * automatiquement les champs adresse, ville et code postal.
         */
        DaoBatiment daoBat = new DaoBatiment();
        Batiment bat = daoBat.findById(bien.getBatiment());
        if (bat == null)
            throw new SQLException("Aucun bâtiment trouvé pour l'ID " + bien.getBatiment());

        getChampSurface().setText(String.valueOf(bien.getSurface()));
        getChampPieces().setText(String.valueOf(bien.getNb_pieces()));
        getAdresseField().setText(bat.getAdresse()); getAdresseField().setEditable(false);
        getCodePostalField().setText(bat.getCodePostale()); getCodePostalField().setEditable(false);
        getVilleField().setText(bat.getVille()); getVilleField().setEditable(false);

        getChampNumeroFiscal().setText(bien.getNumero_fiscale()); getChampNumeroFiscal().setEditable(false);
        getChampType().setSelectedItem(bien.getType()); getChampType().setEnabled(false);
        getChampBatiment().setSelectedItem(bat.getIdBatiment()); getChampBatiment().setEnabled(false);

        getChampEtage().setText(String.valueOf(bien.getEtage()));
        getChampPorte().setText(bien.getPorte());

        // Boutons spécifiques modification
        getBtnAjouter().setText("Modifier"); 
        getBtnAjouter().setName("btnModifierBien");
        this.btnModifier = getBtnAjouter();

        btnModifierCompteur = Utils.creerBouton("Modifier un compteur");
        btnModifierCompteur.setName("btnModifierCompteur");

        panelBoutons.removeAll();
        panelBoutons.add(btnModifier);
        panelBoutons.add(getBtnAjouterCompteur());
        panelBoutons.add(btnModifierCompteur);
        panelBoutons.add(getBtnAnnuler());
        
        revalidate();
        repaint();

        new GestionModifierBienLouable(this, bien);
    }

    public JButton getBtnModifier() { return btnModifier; }
    public JButton getBtnModifierCompteur() { return btnModifierCompteur; }

    public JDesktopPane getDesktop() {
        return this.desktop;
    }
}
