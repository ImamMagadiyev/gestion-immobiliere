package Vue.Modification;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Vue.Utils;

public class FenetreModifierTravaux extends JInternalFrame {

    private static final long serialVersionUID = 1L;

    private JTextField champIdTravaux;
    private JTextField champRaison;
    
    /**
     * Crée et configure la fenêtre interne pour modifier les informations d'un travail,
     * incluant les champs pour l'ID et la raison des travaux ainsi que les boutons de validation et d'annulation.
     */

    public FenetreModifierTravaux() {
        setTitle("Modifier Travaux");
        setClosable(true);
        setResizable(true);
        setIconifiable(true);
        setSize(600, 400);
        setLayout(new BorderLayout());

        JPanel panneauFormulaire = new JPanel(new GridBagLayout());
        panneauFormulaire.setOpaque(false);
        panneauFormulaire.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints contraintes = new GridBagConstraints();
        contraintes.insets = new Insets(10, 10, 10, 10);
        contraintes.anchor = GridBagConstraints.WEST;
        contraintes.fill = GridBagConstraints.HORIZONTAL;

        contraintes.gridx = 0; contraintes.gridy = 0;
        panneauFormulaire.add(Utils.creerLabel("ID Travaux :"), contraintes);
        contraintes.gridx = 1;
        champIdTravaux = Utils.creerTextField("Entrez l'ID du travail", 200, 25);
        panneauFormulaire.add(champIdTravaux, contraintes);

        contraintes.gridx = 0; contraintes.gridy = 1;
        panneauFormulaire.add(Utils.creerLabel("Raison :"), contraintes);
        contraintes.gridx = 1;
        champRaison = Utils.creerTextField("Entrez la raison des travaux", 200, 25);
        panneauFormulaire.add(champRaison, contraintes);

        JPanel panneauBoutons = Utils.creerPanelBoutons(FlowLayout.CENTER);
        
        JButton boutonValider = Utils.creerBoutonPrimaire("Valider");
        boutonValider.setName("btnValiderTravaux");
        panneauBoutons.add(boutonValider);
        
        JButton boutonAnnuler = Utils.creerBouton("Annuler");
        boutonAnnuler.setName("btnAnnulerTravaux");
        panneauBoutons.add(boutonAnnuler);

        panneauFormulaire.add(panneauBoutons, contraintes);

        add(panneauFormulaire, BorderLayout.CENTER);
    }
}
