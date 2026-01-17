package Vue.Modification;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;

import Controleur.Affichage.GestionAfficherDiagnostics;
import Controleur.Modifier.GestionModifierDiagnostic;
import Modele.Diagnostic;
import Vue.Utils;
import Vue.ajouter.FenetreAjouterDiagnostic;


public class FenetreModifierDiagnostic extends FenetreAjouterDiagnostic {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Diagnostic diagnostic;

	/**
	 * Initialise la fenêtre pour modifier un diagnostic existant,
	 * préremplit les champs avec les données actuelles
	 * et configure le bouton pour enregistrer les modifications via le contrôleur dédié.
	 */


	public FenetreModifierDiagnostic(Frame parent, Diagnostic diagnostic, GestionAfficherDiagnostics gestionAfficher) {
		super(parent); 
		this.diagnostic = diagnostic;


		setTitle("Modifier diagnostic");
		Utils.changerTitre(titre, "Modifier diagnostic");

		btnEnregistrer.setText("Modifier");
		btnEnregistrer.setName("btnModifierDiagnostic");

		champReference.setEditable(false);

		for (var al : btnEnregistrer.getActionListeners()) {
			btnEnregistrer.removeActionListener(al);
		}

		new GestionModifierDiagnostic(this, diagnostic, gestionAfficher);

	}

	public void remplirChamps() {
		champReference.setText(diagnostic.getReference());
		champDateValidite.setText(diagnostic.getDate_valide().toString());
		champTypeDiagnostic.setText(diagnostic.getType_diagnostic());

		champNumeroFiscale.setSelectedItem(diagnostic.getNumero_Fiscale());
		champSiret.setSelectedItem(diagnostic.getSiret());
	}

	public void griserComboBox() {
		Color couleurGris = new Color(220, 220, 220);

		champNumeroFiscale.setBackground(couleurGris);
		champNumeroFiscale.setForeground(Color.BLACK);
		champNumeroFiscale.setFont(new Font("Segoe UI", Font.BOLD, 13));
		champNumeroFiscale.setEnabled(false);

		champSiret.setBackground(couleurGris);
		champSiret.setForeground(Color.BLACK);
		champSiret.setFont(new Font("Segoe UI", Font.BOLD, 13));
		champSiret.setEnabled(false);
	}
}

