package Controleur.Modifier;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.SwingUtilities;

import Modele.BienLouable;
import Modele.Compteur;
import Modele.dao.DaoBienLouable;
import Modele.dao.DaoCompteur;
import Vue.Modification.FenetreModifierBienLouable;
import Vue.Modification.FenetreModifierCompteurBienLouableTableau;
import Vue.ajouter.FenetreAjouterCompteurBienLouable;

public class GestionModifierBienLouable implements ActionListener {

    private FenetreModifierBienLouable vue;
    private BienLouable bien;

    public GestionModifierBienLouable(FenetreModifierBienLouable vue, BienLouable bien) {
        this.vue = vue;
        this.bien = bien;

        vue.getBtnModifier().addActionListener(this);
        vue.getBtnAjouterCompteur().addActionListener(this);
        vue.getBtnModifierCompteur().addActionListener(this);
        vue.getBtnAnnuler().addActionListener(this);

        System.out.println("[DEBUG] GestionModifierBienLouable initialisé pour le bien : " + bien.getNumero_fiscale());
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object src = e.getSource();
        if (!(src instanceof Component)) return;

        String name = ((Component) src).getName();
        if (name == null) return;

        switch (name) {

            case "btnModifierBien":
                System.out.println("[DEBUG] Bouton 'Modifier bien' cliqué");
                modifierBien();
                break;

            case "btnAjouterCompteur":
                System.out.println("[DEBUG] Bouton 'Ajouter compteur' cliqué");
                Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(vue);
                FenetreAjouterCompteurBienLouable fac =
                        new FenetreAjouterCompteurBienLouable(parentFrame, bien);
                fac.setVisible(true);
                break;

            case "btnModifierCompteur":
                System.out.println("[DEBUG] Bouton 'Modifier compteur' cliqué");
                try {
                    modifierCompteur();
                } catch (SQLException ex) {
                    vue.afficherErreur("Erreur lors de la modification du compteur : " + ex.getMessage());
                }
                break;

            case "btnAnnuler":
                System.out.println("[DEBUG] Bouton 'Annuler' cliqué, fermeture de la fenêtre");
                vue.dispose();
                break;
        }
    }

    private void modifierBien() {
        try {
            bien.setSurface(Integer.parseInt(vue.getSurface()));
            bien.setNb_pieces(Integer.parseInt(vue.getPieces()));
            bien.setEtage(Integer.parseInt(vue.getEtage()));
            bien.setPorte(vue.getPorte());
        } catch (NumberFormatException ex) {
            vue.afficherErreur("Veuillez entrer des nombres valides pour la surface, le nombre de pièces et l'étage.");
            return;
        }

        try {
            DaoBienLouable dao = new DaoBienLouable();
            dao.update(bien);
            System.out.println("[DEBUG] Bien mis à jour dans la base : " + bien.getNumero_fiscale());
            vue.getGfp().afficherTableauBienLouable();
            vue.afficherSucces("Bien modifié.");
        } catch (SQLException ex) {
            vue.afficherErreur("Erreur lors de la modification : " + ex.getMessage());
        }

        vue.dispose();
    }

    private void modifierCompteur() throws SQLException {
        DaoCompteur daoC = new DaoCompteur();
        Compteur compteur = daoC.findByNumeroFiscale(bien.getNumero_fiscale());

        if (compteur == null) {
            vue.afficherErreur("Aucun compteur trouvé.");
            return;
        }

        FenetreModifierCompteurBienLouableTableau fmct =
        	    new FenetreModifierCompteurBienLouableTableau(
        	        bien,
        	        vue.getDesktop()
        	    );

        System.out.println("[DEBUG] Fenêtre modification compteur ouverte pour le compteur : " + compteur.getId_compteur());
        vue.getDesktop().add(fmct);
        fmct.setVisible(true);
    }
}
