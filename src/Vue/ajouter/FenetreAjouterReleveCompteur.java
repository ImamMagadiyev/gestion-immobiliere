package Vue.ajouter;

import javax.swing.*;
import java.awt.*;

import Controleur.Ajouter.GestionAjouterReleveCompteur;
import Controleur.GestionFenetrePrincipale;
import Vue.Utils;

public class FenetreAjouterReleveCompteur extends FenetreAjoutBase {

    private static final long serialVersionUID = 1L;

    private GestionFenetrePrincipale gfp;

    private JComboBox<String> comboCompteur;
    private JTextField champDateDernierReleve;
    private JTextField champIndexAncien;
    private JTextField champDate;
    private JTextField champIndex;
  
    private JButton btnEnregistrer;
    private JButton btnAnnuler;

    private GestionAjouterReleveCompteur controleur;

    public FenetreAjouterReleveCompteur(GestionFenetrePrincipale gfp, JDesktopPane desktop) {

        super("Ajouter relevé de compteur", "Nouveau relevé de compteur", 500, 450, desktop);
        
        this.gfp = gfp;

        comboCompteur = new JComboBox<>();
        comboCompteur.addItem("Sélectionner un compteur");
        comboCompteur.setName("comboCompteur");
        comboCompteur.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        comboCompteur.setPreferredSize(new Dimension(200, 25));

        champDateDernierReleve = Utils.creerTextField("Date du dernier relevé", 200, 25);
        champDateDernierReleve.setEditable(false);

        champIndexAncien = Utils.creerTextField("Index actuel", 200, 25);
        champIndexAncien.setEditable(false);

        champDate = Utils.creerTextField("Date du nouveau relevé (YYYY-MM-DD)", 200, 25);
        champIndex = Utils.creerTextField("Nouvel index", 200, 25);

        form.add(creerLigne("Compteur :", comboCompteur, 180));
        form.add(Utils.creerEspacementVertical(8));

        form.add(creerLigne("Date du dernier relevé :", champDateDernierReleve, 180));
        form.add(Utils.creerEspacementVertical(8));

        form.add(creerLigne("Index actuel :", champIndexAncien, 180));
        form.add(Utils.creerEspacementVertical(8));
        
        form.add(creerLigne("Date du nouveau relevé :", champDate, 180));
        form.add(Utils.creerEspacementVertical(8));

        form.add(creerLigne("Nouvel index :", champIndex, 180));

        btnEnregistrer = Utils.creerBoutonPrimaire("Enregistrer");
        btnEnregistrer.setName("btnEnregistrer");
        panelBoutons.add(btnEnregistrer);
        
        btnAnnuler = Utils.creerBouton("Annuler");
        btnAnnuler.setName("btnAnnuler");
        panelBoutons.add(btnAnnuler);

        if (this.getClass() == FenetreAjouterReleveCompteur.class) {
            controleur = new GestionAjouterReleveCompteur(this);
            comboCompteur.addActionListener(e -> controleur.mettreAJourInfosCompteur());
        }
    }

    public JComboBox<String> getComboCompteur() { return comboCompteur; }
    public JTextField getChampDateDernierReleve() { return champDateDernierReleve; }
    public JTextField getChampIndexAncien() { return champIndexAncien; }
    public JTextField getChampDate() { return champDate; }
    public JTextField getChampIndex() { return champIndex; }
    public JButton getBtnEnregistrer() { return btnEnregistrer; }
    public JButton getBtnAnnuler() { return btnAnnuler; }
    public JDesktopPane getDesktop() { return desktop; }
    public GestionFenetrePrincipale getGfp() { return gfp; }

    public void afficherErreur(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Erreur", JOptionPane.WARNING_MESSAGE);
    }

    public void afficherSucces(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Succès", JOptionPane.INFORMATION_MESSAGE);
    }
}
