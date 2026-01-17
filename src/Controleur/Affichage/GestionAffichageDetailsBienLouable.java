package Controleur.Affichage; 

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import Modele.Batiment;
import Modele.BienLouable;
import Modele.Compteur;
import Modele.ContratDeLocation;
import Modele.Facture;
import Modele.Loyer;
import Modele.dao.DaoBatiment;
import Modele.dao.DaoBienLouable;
import Modele.dao.DaoCompteur;
import Modele.dao.DaoContratDeLocation;
import Modele.dao.DaoFacture;
import Modele.dao.DaoLoyer;
import Vue.Affichage.FenetreAffichageDetailsBienLouable;

public class GestionAffichageDetailsBienLouable implements ActionListener {

    private final DaoBienLouable daoBien;
    private final DaoBatiment daoBat;
    private final DaoCompteur daoComp;
    private final DaoContratDeLocation daoCon;
    private final DaoLoyer daoLoyer;
    private final DaoFacture daoFac;

    public GestionAffichageDetailsBienLouable(
            DaoBienLouable daoBien,
            DaoBatiment daoBat,
            DaoCompteur daoComp,
            DaoContratDeLocation daoCon,
            DaoLoyer daoLoyer,
            DaoFacture daoFac
    ) {
        this.daoBien = daoBien;
        this.daoBat = daoBat;
        this.daoComp = daoComp;
        this.daoCon = daoCon;
        this.daoLoyer = daoLoyer;
        this.daoFac = daoFac;
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	 public void ouvrirDetail(String numeroFiscal) {
	        try {
	            // 🔹 Récupération du bien
	            BienLouable bien = daoBien.findById(numeroFiscal);
	            if (bien == null) {
	                throw new IllegalArgumentException("Bien louable introuvable : " + numeroFiscal);
	            }

	            // 🔹 Récupération du bâtiment
	            Batiment batiment = daoBat.findById(bien.getBatiment());

	            // 🔹 Tous les compteurs (depuis DAO)
	            List<Compteur> tousLesCompteurs = daoComp.findAll();
	            List<Compteur> compteursAssocies = tousLesCompteurs.stream()
	                    .filter(c -> c.getNumero_fiscale() != null) // sécurité null
	                    .filter(c -> c.getNumero_fiscale().equals(numeroFiscal))
	                    .toList();

	            // 🔹 Tous les contrats
	            List<ContratDeLocation> tousLesContrats = daoCon.findAll();
	            ContratDeLocation contratActif = tousLesContrats.stream()
	                    .filter(Objects::nonNull)
	                    .filter(c -> numeroFiscal.equals(c.getNumero_fiscale()))
	                    .findFirst()
	                    .orElse(null);

	            // 🔹 Tous les loyers
	            List<Loyer> tousLesLoyers = daoLoyer.findAll();
	            List<Loyer> loyersAssocies = tousLesLoyers.stream()
	                    .filter(Objects::nonNull)
	                    .filter(l -> numeroFiscal.equals(l.getNumero_fiscale()))
	                    .toList();

	            // 🔹 Toutes les factures
	            List<Facture> toutesLesFactures = daoFac.findAll();
	            List<Facture> facturesAssocies = toutesLesFactures.stream()
	                    .filter(Objects::nonNull)
	                    .filter(f -> numeroFiscal.equals(f.getNumero_fiscale()))
	                    .toList();

	            // 🔹 Création et affichage de la vue
	            FenetreAffichageDetailsBienLouable fenetre = new FenetreAffichageDetailsBienLouable(
	                    bien,
	                    batiment,
	                    compteursAssocies,
	                    contratActif,
	                    loyersAssocies,
	                    facturesAssocies
	            );

	            fenetre.setVisible(true);

	        } catch (SQLException e) {
	            e.printStackTrace();
	            javax.swing.JOptionPane.showMessageDialog(
	                    null,
	                    "Erreur lors du chargement du détail du bien : " + e.getMessage()
	            );
	        }
	    }
}