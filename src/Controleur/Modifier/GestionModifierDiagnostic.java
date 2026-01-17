package Controleur.Modifier;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

import Controleur.Affichage.GestionAfficherDiagnostics;
import Modele.BienLouable;
import Modele.Diagnostic;
import Modele.Entreprise;
import Modele.dao.DaoBienLouable;
import Modele.dao.DaoDiagnostic;
import Modele.dao.DaoEntreprise;
import Vue.Utils;
import Vue.Modification.FenetreModifierDiagnostic;

public class GestionModifierDiagnostic implements ActionListener {

    private FenetreModifierDiagnostic vue;
    private Diagnostic diagnostic;
    private GestionAfficherDiagnostics gestionAfficher;
    private List<BienLouable> listeBienLouable;
    private List<Entreprise> listeEntreprise;

    public GestionModifierDiagnostic(FenetreModifierDiagnostic vue, Diagnostic diagnostic, GestionAfficherDiagnostics gestionAfficher) {
        this.vue = vue;
        this.diagnostic = diagnostic;
        this.gestionAfficher = gestionAfficher;

        vue.getBtnEnregistrer().addActionListener(this);
        vue.getBtnAnnuler().addActionListener(e -> vue.dispose());

        System.out.println("[DEBUG] Initialisation GestionModifierDiagnostic pour le diagnostic : " + diagnostic.getReference());

        chargerBienLouable();
        chargerEntreprise();
        vue.remplirChamps();
        vue.griserComboBox();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Component src = (Component) e.getSource();
        String name = src.getName();
        if (name == null) return;

        switch (name) {
            case "btnModifierDiagnostic":
                System.out.println("[DEBUG] Bouton 'Modifier Diagnostic' cliqué pour le diagnostic : " + diagnostic.getReference());
                modifierDiagnostic();
                break;
        }
    }

    private void chargerBienLouable() {
        try {
            DaoBienLouable dao = new DaoBienLouable();
            listeBienLouable = dao.findAll();
            vue.remplirBienLouable(listeBienLouable);
        } catch (Exception ex) {
            vue.afficherErreur("Erreur chargement bien louable : " + ex.getMessage());
            System.out.println("[DEBUG] Erreur chargement bien louable : " + ex.getMessage());
        }
    }

    private void chargerEntreprise() {
        try {
            DaoEntreprise dao = new DaoEntreprise();
            listeEntreprise = dao.findAll();
            vue.remplirEntreprise(listeEntreprise);
        } catch (Exception ex) {
            vue.afficherErreur("Erreur chargement entreprises : " + ex.getMessage());
            System.out.println("[DEBUG] Erreur chargement entreprises : " + ex.getMessage());
        }
    }

    private void modifierDiagnostic() {
        try {
            LocalDate date;
            try {
                date = Utils.parseLocalDate(vue.getDateValidite());
            } catch (Exception e) {
                vue.afficherErreur("Format date invalide (AAAA-MM-JJ attendu)");
                System.out.println("[DEBUG] Date invalide pour le diagnostic : " + diagnostic.getReference());
                return;
            }

            diagnostic.setDate_valide(date);
            diagnostic.setType_diagntic(vue.getTypeDiagnostic());
            diagnostic.setNumero_Ficale(vue.getNumeroFiscale());
            diagnostic.setSiret(vue.getSiret());

            DaoDiagnostic dao = new DaoDiagnostic();
            dao.update(diagnostic);

            System.out.println("[DEBUG] Diagnostic modifié avec succès : " + diagnostic.getReference());

            vue.afficherSucces("Diagnostic modifié.");
            if (gestionAfficher != null) {
                try {
                    gestionAfficher.chargerDiagnostics();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println("[DEBUG] Erreur rafraîchissement tableau diagnostics : " + ex.getMessage());
                }
            }

        } catch (Exception ex) {
            vue.afficherErreur("Erreur lors de la modification : " + ex.getMessage());
            System.out.println("[DEBUG] Exception modification diagnostic : " + diagnostic.getReference() + " => " + ex.getMessage());
            ex.printStackTrace();
        }

        // Fermer la fenêtre dans tous les cas
        vue.dispose();
    }
}
