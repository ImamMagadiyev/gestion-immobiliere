package Vue;

import java.awt.*;
import javax.swing.*;
import Controleur.GestionConnexion;

public class FenetreConnexion extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextField champNomUtilisateur;
    private JPasswordField champMotDePasse;
    private JButton boutonConnexion;
    private JButton boutonAnnuler;

    /**
     * Fenêtre principale de connexion à l'application.
     * Permet à l'utilisateur de saisir son nom et mot de passe pour se connecter.
     * Gère l'affichage du logo, des champs de saisie et des boutons (Connexion/Annuler).
     * Utilise GestionConnexion pour traiter les actions des boutons.
     */


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    FenetreConnexion fenetre = new FenetreConnexion();
                    fenetre.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public FenetreConnexion() {
 
        setTitle("Page de connexion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 500); 
        setLocationRelativeTo(null);
        getContentPane().setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(250, 250, 252));

 
        JLabel labelLogo = chargerLogo();
        GridBagConstraints contraintesLogo = new GridBagConstraints();
        contraintesLogo.gridx = 0;
        contraintesLogo.gridy = 0;
        contraintesLogo.gridwidth = 2;
        contraintesLogo.insets = new Insets(20, 10, 15, 10);
        contraintesLogo.anchor = GridBagConstraints.CENTER;
        add(labelLogo, contraintesLogo);

        GridBagConstraints contraintesTitre = new GridBagConstraints();
        contraintesTitre.gridx = 0;
        contraintesTitre.gridy = 1;
        contraintesTitre.gridwidth = 2;
        contraintesTitre.insets = new Insets(5, 10, 25, 10);
        contraintesTitre.fill = GridBagConstraints.HORIZONTAL;

        JLabel etiquetteTitre = new JLabel("Page de Connexion", SwingConstants.CENTER);
        etiquetteTitre.setFont(new Font("Arial", Font.BOLD, 26));
        etiquetteTitre.setForeground(new Color(60, 60, 60));
        add(etiquetteTitre, contraintesTitre);


        GridBagConstraints contraintesNomLabel = new GridBagConstraints();
        contraintesNomLabel.gridx = 0;
        contraintesNomLabel.gridy = 2;
        contraintesNomLabel.gridwidth = 2;
        contraintesNomLabel.anchor = GridBagConstraints.WEST;
        contraintesNomLabel.insets = new Insets(5, 20, 5, 20);
        add(new JLabel("Nom d'utilisateur :"), contraintesNomLabel);

        GridBagConstraints contraintesNomChamp = new GridBagConstraints();
        contraintesNomChamp.gridx = 0;
        contraintesNomChamp.gridy = 3;
        contraintesNomChamp.gridwidth = 2;
        contraintesNomChamp.insets = new Insets(0, 20, 15, 20);
        contraintesNomChamp.fill = GridBagConstraints.HORIZONTAL;

        champNomUtilisateur = new JTextField(30);
        champNomUtilisateur.setToolTipText("Saisissez votre nom d'utilisateur");
        add(champNomUtilisateur, contraintesNomChamp);

  
        GridBagConstraints contraintesMdpLabel = new GridBagConstraints();
        contraintesMdpLabel.gridx = 0;
        contraintesMdpLabel.gridy = 4;
        contraintesMdpLabel.gridwidth = 2;
        contraintesMdpLabel.anchor = GridBagConstraints.WEST;
        contraintesMdpLabel.insets = new Insets(5, 20, 5, 20);
        add(new JLabel("Mot de passe :"), contraintesMdpLabel);

        GridBagConstraints contraintesMdpChamp = new GridBagConstraints();
        contraintesMdpChamp.gridx = 0;
        contraintesMdpChamp.gridy = 5;
        contraintesMdpChamp.gridwidth = 2;
        contraintesMdpChamp.insets = new Insets(0, 20, 25, 20);
        contraintesMdpChamp.fill = GridBagConstraints.HORIZONTAL;

        champMotDePasse = new JPasswordField(30);
        champMotDePasse.setToolTipText("Saisissez votre mot de passe");
        add(champMotDePasse, contraintesMdpChamp);

        boutonConnexion = new JButton("Se connecter");
        boutonConnexion.setPreferredSize(new Dimension(160, 40));


        boutonAnnuler = new JButton("Annuler");
        boutonAnnuler.setPreferredSize(new Dimension(160, 40));

        JPanel panneauBoutons = new JPanel(new GridLayout(1, 2, 30, 0));
        panneauBoutons.add(boutonConnexion);
        panneauBoutons.add(boutonAnnuler);

        GridBagConstraints contraintesBoutons = new GridBagConstraints();
        contraintesBoutons.gridx = 0;
        contraintesBoutons.gridy = 6;
        contraintesBoutons.gridwidth = 2;
        contraintesBoutons.insets = new Insets(0, 10, 20, 10);
        contraintesBoutons.anchor = GridBagConstraints.CENTER;
        add(panneauBoutons, contraintesBoutons);

 
        GestionConnexion gestionConnexion = new GestionConnexion(this);
        boutonConnexion.addActionListener(gestionConnexion);
        boutonAnnuler.addActionListener(gestionConnexion);
        getRootPane().setDefaultButton(boutonConnexion);

    }


    private JLabel chargerLogo() {
        JLabel labelLogo = new JLabel();

        try {
            String chemin = "src/icon/logo (2).png";
            ImageIcon icon = new ImageIcon(chemin);

            if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
                Image image = icon.getImage();
                int largeur = 100; 
                int hauteur = (int) (icon.getIconHeight() * (largeur / (double) icon.getIconWidth()));

                Image imageRedimensionnee = image.getScaledInstance(largeur, hauteur, Image.SCALE_SMOOTH);
                icon = new ImageIcon(imageRedimensionnee);
                labelLogo.setIcon(icon);
                labelLogo.setHorizontalAlignment(SwingConstants.CENTER);
            }
        } catch (Exception e) {
        	
        }

        return labelLogo;
    }
    
    public String getNomUtilisateur() {
        return champNomUtilisateur.getText();
    }
    
    public String getMdp() {
        return new String(champMotDePasse.getPassword());
    }
}