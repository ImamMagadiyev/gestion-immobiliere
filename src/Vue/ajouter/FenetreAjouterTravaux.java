package Vue.ajouter;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import Controleur.Ajouter.GestionAjouterTravaux;
import Modele.Facture;
import Vue.Utils;

public class FenetreAjouterTravaux extends JInternalFrame {

    private static final long serialVersionUID = 1L;

    private JTextField champIdTravaux;
    private JTextField champRaison;
    private JComboBox<String> champFacture;

    private JButton btnAjouter;
    private JButton btnAnnuler;

    public FenetreAjouterTravaux() {
        super("Ajouter travaux", true, true, true, true);

        setSize(480, 320);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        // ----------- Titre -----------
        JLabel titre = Utils.creerTitre("Nouveau travaux");
        add(titre, BorderLayout.NORTH);

        // ----------- Formulaire -----------
        JPanel form = new JPanel(new GridLayout(0, 2, 12, 10));
        form.setOpaque(false);
        form.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        champIdTravaux = Utils.creerTextField("ID travaux", 150, 25);
        champRaison    = Utils.creerTextField("Raison des travaux", 150, 25);

        champFacture = new JComboBox<>();
        champFacture.addItem("Sélectionner une facture");
        champFacture.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        form.add(Utils.creerLabel("ID Travaux :"));
        form.add(champIdTravaux);

        form.add(Utils.creerLabel("Raison :"));
        form.add(champRaison);

        form.add(Utils.creerLabel("Facture :"));
        form.add(champFacture);

        add(form, BorderLayout.CENTER);

        // ----------- Boutons -----------
        JPanel boutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 12));
        boutons.setOpaque(false);

        btnAjouter = Utils.creerBouton("Ajouter");
        btnAjouter.setName("btnValiderTravaux");

        btnAnnuler = Utils.creerBouton("Annuler");
        btnAnnuler.setName("btnAnnulerTravaux");

        boutons.add(btnAjouter);
        boutons.add(btnAnnuler);
        add(boutons, BorderLayout.SOUTH);

        // ----------- Contrôleur -----------
        new GestionAjouterTravaux(this);
    }

    /**
     * Remplit la liste déroulante des factures à partir d'une liste de factures.
     * Cette méthode est appelée par le contrôleur après récupération des factures en base.
     */
    public void remplirFacture(List<Facture> liste) {
        champFacture.removeAllItems();
        champFacture.addItem("Sélectionner une facture");

        for (Facture f : liste) {
            champFacture.addItem(f.getNumero());
        }
    }

    // ====== GETTERS ======
    public String getIdTravaux() {
        return champIdTravaux.getText().trim();
    }

    public String getRaison() {
        return champRaison.getText().trim();
    }

    public String getNumeroFacture() {
        return (String) champFacture.getSelectedItem();
    }

    public int getFactureIndex() {
        return champFacture.getSelectedIndex() - 1;
    }

    public JButton getBtnAjouter() {
        return btnAjouter;
    }

    public JButton getBtnAnnuler() {
        return btnAnnuler;
    }

    /**
     * Affiche une boîte de dialogue d'erreur avec un message personnalisé.
     * Utilisée pour signaler une validation incorrecte ou un problème lors de l'ajout.
     */
    public void afficherErreur(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Erreur", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Affiche une boîte de dialogue de confirmation (succès) avec un message personnalisé.
     * Utilisée après une création réussie (ex : ajout en base validé).
     */
    public void afficherSucces(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Succès", JOptionPane.INFORMATION_MESSAGE);
    }
}
