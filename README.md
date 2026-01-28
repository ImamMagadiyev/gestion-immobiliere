# Gestion Locative - Système de Gestion Immobilière

## 📋 À propos du projet

Application de gestion immobilière et locative développée en Java. Le système permet de gérer les biens immobiliers, les locataires, les contrats de location, les factures, les compteurs et les archives.

## 👥 Membres du groupe

- **HISABU Nathan Tekeste**
- **MAGADIYEV Imam**
- **SENTAYEHU Yeadonaye Ashenafi**
- **KONE Fode**
- **BASSET Adrien**
- **HILAIRE Kenny**

## 🛠️ Environnement technique

- **Langage** : Java 11
- **Interface graphique** : Swing (JFrame, JInternalFrame, JPanel)
- **Architecture** : MVC (Modèle-Vue-Contrôleur)
- **Base de données** : Oracle JDBC
- **Build** : javac

## 📁 Structure du projet

```
sae_2025_e_magadiyev_hisabu_basset_sentayehu_hilaire/
├── src/
│   ├── Controleur/              # Contrôleurs (gestion des actions)
│   │   ├── GestionConnexion.java
│   │   ├── GestionFenetrePrincipale.java
│   │   ├── TableUtils.java
│   │   ├── UtilsFormulaire.java
│   │   ├── Affichage/           # Contrôleurs d'affichage
│   │   │   ├── Gestion_Calcul_IRL.java
│   │   │   ├── Gestion_Calculer_Charges.java
│   │   │   ├── GestionAffichageDetailsBienLouable.java
│   │   │   ├── GestionAfficherCaution.java
│   │   │   ├── GestionAfficherCompteur.java
│   │   │   ├── GestionAfficherDiagnostics.java
│   │   │   └── GestionAfficherReleveCompteur.java
│   │   ├── Ajouter/             # Contrôleurs d'ajout d'entités
│   │   │   ├── GestionAjouterBatiment.java
│   │   │   ├── GestionAjouterBienLouable.java
│   │   │   ├── GestionAjouterCaution.java
│   │   │   ├── GestionAjouterCompteurBatiment.java
│   │   │   ├── GestionAjouterCompteurBienLouable.java
│   │   │   ├── GestionAjouterContratDeLocation.java
│   │   │   ├── GestionAjouterDiagnostic.java
│   │   │   ├── GestionAjouterEntreprise.java
│   │   │   ├── GestionAjouterFacture.java
│   │   │   ├── GestionAjouterLocataire.java
│   │   │   ├── GestionAjouterLocation.java
│   │   │   ├── GestionAjouterReleveCompteur.java
│   │   │   ├── GestionAjouterTravaux.java
│   │   │   └── GestionControleurAjoutBase.java
│   │   └── Modifier/            # Contrôleurs de modification
│   │       └── GestionModificationFacture.java
│   ├── Modele/                  # Modèles de données
│   │   ├── Agence.java
│   │   ├── Assurance.java
│   │   ├── Batiment.java
│   │   ├── BienLouable.java
│   │   ├── BienLouableQuotite.java
│   │   ├── Caution.java
│   │   ├── Charges.java
│   │   ├── ChargesLot.java
│   │   ├── Comporter.java
│   │   ├── Compteur.java
│   │   ├── CompteurBien.java
│   │   ├── CompteurLot.java
│   │   ├── Conclure.java
│   │   ├── ContratDeLocation.java
│   │   ├── Diagnostic.java
│   │   ├── Entreprise.java
│   │   ├── Facture.java
│   │   ├── Impots.java
│   │   ├── Locataire.java
│   │   ├── Loyer.java
│   │   ├── Payer.java
│   │   ├── ReleveCompteur.java
│   │   ├── Travaux.java
│   │   ├── TypeBiens.java
│   │   ├── Variable.java
│   │   └── dao/                 # Accès aux données
│   │       ├── Dao.java
│   │       └── requetes/        # Requêtes SQL
│   ├── Vue/                     # Interfaces graphiques
│   │   ├── FenetrePrincipale.java   # Fenêtre principale
│   │   ├── FenetreConnexion.java    # Fenêtre de connexion
│   │   ├── Utils.java           # Utilitaires UI
│   │   ├── Affichage/           # Fenêtres d'affichage
│   │   ├── ajouter/             # Fenêtres d'ajout
│   │   ├── Calculer/            # Fenêtres de calcul
│   │   └── Modification/        # Fenêtres de modification
│   └── icon/                    # Ressources graphiques
├── bin/                         # Fichiers compilés
│   ├── Controleur/
│   ├── Modele/
│   ├── Vue/
│   └── icon/
├── Documents/                   # Fichiers de données (CSV)
│   ├── loyer.csv
│   └── LoyersFinale.csv
├── sql/                         # Scripts SQL
│   ├── creationTables.sql
│   ├── Delete.sql
│   ├── procedureInsert.sql
│   └── triggers.sql
├── autre/
│   └── ojdbc/                   # Drivers Oracle JDBC
├── Rapport/                     # Rapports générés
├── Test/                        # Tests unitaires
│   └── testApplication.java
└── README.md
```

## 🎯 Fonctionnalités principales

### Gestion des biens

- Ajouter, modifier, afficher des bâtiments
- Gérer les biens louables (appartements, maisons, etc.)
- Consulter les détails et caractéristiques

### Gestion des locataires

- Ajouter et modifier les informations des locataires
- Gérer les cautionnements
- Suivre les historiques

### Gestion des locations

- Créer et gérer les contrats de location
- Suivre les paiements de loyers
- Gérer les provisions de charges

### Gestion financière

- Enregistrer et suivre les factures
- Traiter les factures de travaux, impôts, assurances, variables
- Gérer les compteurs (eau, gaz, électricité)
- Calculer les charges et régularisations

### Archives

- Archiver les locations, locataires, contrats
- Historique des transactions
- Diagnostic et cautions archivées

## 🚀 Compilation et exécution

### Compilation

```bash
javac -d bin -cp "bin;autre/ojdbc/*" src/Vue/FenetreConnexion.java
```

### Exécution

```bash
java -cp "bin;autre/ojdbc/*" Vue.FenetreConnexion
```

## 📊 Architecture

L'application suit le pattern **MVC** :

- **Modèle (M)** : Classes dans `Modele/` représentant les entités métier et les DAOs (Data Access Objects)
- **Vue (V)** : Classes dans `Vue/` gérant l'interface graphique avec Swing
- **Contrôleur (C)** : Classes dans `Controleur/` orchestrant les interactions entre Vue et Modèle

## 🔧 Qualité du code

Le projet intègre les bonnes pratiques suivantes :

- Gestion des ressources avec try-with-resources
- Spécification de la `Locale` dans les parseurs de dates
- Default cases dans tous les switch pour éviter les cas non gérés
- Commentaires explicatifs sur les méthodes complexes
- Séparation claire des responsabilités

## 📝 Notes de développement

- Les fichiers CSV peuvent être importés via le bouton "Importer CSV" de la page d'accueil
- La connexion à la base de données est obligatoire au démarrage
- Les tableaux de données se mettent à jour automatiquement après chaque modification
- Les fenêtres internes (JInternalFrame) permettent de modifier plusieurs entités simultanément

## 📦 Dépendances

- **Oracle JDBC Driver** (`ojdbc*.jar` dans `autre/ojdbc/`)
- **Bibliothèque standard Java 11**

## 📄 Licence

Projet académique - SAE 2025

---

**Dernière mise à jour** : Janvier 2026
