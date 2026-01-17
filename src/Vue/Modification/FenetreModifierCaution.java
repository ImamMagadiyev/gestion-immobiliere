package Vue.Modification;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;

import Modele.Caution;
import Vue.Utils;
import Vue.ajouter.FenetreAjouterCaution;

public class FenetreModifierCaution extends FenetreAjouterCaution {

    private Caution caution;

    /**
     * Initialise la fenêtre de modification d'une caution existante.
     * 
     * Cette fenêtre réutilise celle d'ajout de caution, mais :
     * - préremplit les champs avec les données de la caution,
     * - désactive les champs non modifiables,
     * - remplace l'action du bouton "Enregistrer" par une action de modification.
     */
    public FenetreModifierCaution(Frame parent, Caution caution) {
        super(parent);
        this.caution = caution;

        setTitle("Modifier caution");
        Utils.changerTitre(titre, "Modifier caution");

        btnEnregistrer.setText("Modifier");
        btnEnregistrer.setName("btnModifierCaution");

        champIdCaution.setEditable(false);

        remplirChamps();
        griserComboBox();

        /**
         * Suppression des anciens ActionListener hérités de la fenêtre d'ajout.
         * Permet d'éviter que l'action "Ajouter" soit déclenchée lors d'une modification.
         */
        for (var al : btnEnregistrer.getActionListeners()) {
            btnEnregistrer.removeActionListener(al);
        }
    }

    /**
     * Remplit tous les champs du formulaire avec les informations
     * de la caution à modifier.
     * Permet d'afficher les données existantes dès l'ouverture de la fenêtre.
     */
    private void remplirChamps() {
        champIdCaution.setText(caution.getId_caution());
        champNom.setText(caution.getNom());
        champPrenom.setText(caution.getPrenom());
        champAdresse.setText(caution.getAdresse());
        champVille.setText(caution.getVille());
        champCodePostale.setText(caution.getCode_postale());
        champProfession.setText(caution.getProfession());
        champTypeContrat.setSelectedItem(caution.getType_contrat_travail());
        champDateNaissance.setText(caution.getDate_naissance());
        champMail.setText(caution.getAdresseElectronique());
        champTelephone.setText(caution.getNumeroTel());
        champQualite.setText(caution.getQualiteBailleur());
        champIdContrat.setSelectedItem(caution.getIdContrat());
    }

    /**
     * Désactive certaines ComboBox et champs non modifiables
     * tout en conservant une bonne lisibilité visuelle (fond grisé).
     * Utilisé dans le contexte de modification uniquement.
     */
    public void griserComboBox() {
        // Griser visuellement mais garder le texte très visible
        Color couleurGris = new Color(220, 220, 220);
        
        champIdCaution.setBackground(couleurGris);
        champIdCaution.setForeground(Color.BLACK);
        champIdCaution.setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        champTypeContrat.setBackground(couleurGris);
        champTypeContrat.setForeground(Color.BLACK);
        champTypeContrat.setFont(new Font("Segoe UI", Font.BOLD, 13));
        champTypeContrat.setEnabled(false);
        
        champIdContrat.setBackground(couleurGris);
        champIdContrat.setForeground(Color.BLACK);
        champIdContrat.setFont(new Font("Segoe UI", Font.BOLD, 13));
        champIdContrat.setEnabled(false);
    }
}
