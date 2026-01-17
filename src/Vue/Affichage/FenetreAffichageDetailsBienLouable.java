package Vue.Affichage;

import Vue.Utils;

import java.awt.BorderLayout;

import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import Modele.Batiment;
import Modele.BienLouable;
import Modele.BienLouableQuotite;
import Modele.Compteur;
import Modele.ContratDeLocation;
import Modele.Facture;
import Modele.Loyer;
import Modele.dao.DaoBienLouableQuotite;

public class FenetreAffichageDetailsBienLouable extends JInternalFrame {

    private static final long serialVersionUID = 1L;

    public FenetreAffichageDetailsBienLouable(
            BienLouable bien,
            Batiment batiment,
            List<Compteur> compteurs,
            ContratDeLocation contrat,
            List<Loyer> loyers,
            List<Facture> factures) {

        super("Détail Bien Louable - " + bien.getNumero_fiscale(), true, true, true, true);
        setSize(900, 650);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(mainPanel);

        mainPanel.add(Utils.creerTitre("Détail du bien louable"), BorderLayout.NORTH);

        JTabbedPane onglets = new JTabbedPane();

        onglets.addTab("Bien Louable",
                creerTable("Bien Louable",
                        new String[]{"Numéro fiscal", "Type", "Surface", "Pièces", "Bâtiment", "Étage", "Porte"},
                        Utils.bienToTable(bien)));

        onglets.addTab("Bâtiment",
                creerTable("Bâtiment",
                        new String[]{"ID", "Adresse", "Ville", "Code Postal", "Construction"},
                        Utils.batimentToTable(batiment)));

        onglets.addTab("Compteurs",
                creerTable("Compteurs",
                        new String[]{"ID", "Type", "Index", "Variable", "Num Fiscal", "Bâtiment", "Archivé"},
                        Utils.compteursToTable(compteurs)));

        if (contrat != null) {
            onglets.addTab("Contrat",
                    creerTable("Contrat",
                            new String[]{"ID", "Début", "Trimestre", "Fin", "Loyer", "IRL",
                                    "Charges", "Solde", "Durée", "Num Fiscal", "Locataire", "Archivé"},
                            Utils.contratToTable(contrat)));
        }

        onglets.addTab("Loyers",
                creerTable("Loyers",
                        new String[]{"Locataire", "Num Fiscal", "Date", "Provision", "Loyer",
                                "Mois", "Régularisation", "Archivé"},
                        Utils.loyersToTable(loyers)));

        // Ajouter l'onglet Quotité
        try {
            DaoBienLouableQuotite daoQuotite = new DaoBienLouableQuotite();
            List<BienLouableQuotite> quotites = daoQuotite.findByBatiment(bien.getBatiment());
            
            if (quotites != null && !quotites.isEmpty()) {
                onglets.addTab("Quotité",
                        creerTable("Quotité",
                                new String[]{"Num Fiscal", "Type", "Surface", "Pièces", "Étage", "Porte", "Quotité"},
                                quotiteToTable(quotites)));
            }
        } catch (Exception ex) {
            System.err.println("Erreur chargement quotité : " + ex.getMessage());
            ex.printStackTrace();
        }

        onglets.addTab("Factures",
                creerTable("Factures",
                        new String[]{"Numéro", "Date", "Travaux", "Montant", "Paiement",
                                "Devis", "Nature", "Date Devis", "Locataire", "SIRET", "Num Fiscal", "Archivé"},
                        Utils.facturesToTable(factures)));


        mainPanel.add(onglets, BorderLayout.CENTER);
        setVisible(true);
    }

    private JPanel creerTable(String titre, String[] colonnes, Object[][] data) {
        JTable table = new JTable(Utils.creerTableModelNonEditable(colonnes));
        Utils.styliserTable(table);

        for (Object[] row : data) {
            ((javax.swing.table.DefaultTableModel) table.getModel()).addRow(row);
        }

        JScrollPane scroll = Utils.creerScrollPane(table, titre);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    // Convertir une liste de BienLouableQuotite en tableau 2D
    private Object[][] quotiteToTable(List<BienLouableQuotite> quotites) {
        Object[][] data = new Object[quotites.size()][7];
        for (int i = 0; i < quotites.size(); i++) {
            BienLouableQuotite q = quotites.get(i);
            data[i][0] = q.getNumero_fiscale();
            data[i][1] = q.getType();
            data[i][2] = q.getSurface();
            data[i][3] = q.getNombre_pieces();
            data[i][4] = q.getEtage();
            data[i][5] = q.getPorte();
            data[i][6] = String.format("%.4f", q.getQuotite()); // Afficher avec 4 décimales
        }
        return data;
    }

}
