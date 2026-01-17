package Modele.dao.requetes.SousProgramme;

import java.sql.CallableStatement;
import java.sql.SQLException;

/**
 * Interface définissant un sous-programme SQL générique.
 * Chaque sous-programme spécifique devra implémenter cette interface.
 */
public interface SousProgramme<T> {

    /**
     * Retourne l'appel du sous-programme SQL sous forme de chaîne de caractères.
     *
     * @return Appel de la procédure stockée dans la base de données.
     */
    String appelSousProgramme();

    /**
     * Paramètre la `CallableStatement` avec les valeurs à passer au sous-programme.
     *
     * @param stmt `CallableStatement` à paramétrer.
     * @param donnee Objet qui contient les données à insérer dans le sous-programme.
     * @throws SQLException Si une erreur SQL survient lors du paramétrage.
     */
    void parametres(CallableStatement stmt, T donnee) throws SQLException;

    /**
     * Méthode optionnelle pour paramétrer des séquences, si nécessaire.
     * Par exemple, pour des sous-programmes qui nécessitent l'utilisation de séquences SQL.
     *
     * @param stmt `CallableStatement` pour la séquence.
     * @param donnee Objet contenant les données nécessaires.
     * @throws SQLException Si une erreur survient lors du paramétrage de la séquence.
     */
    default void parametresSequence(CallableStatement stmt, T donnee) throws SQLException {
        // Par défaut, la méthode peut être vide. Les sous-programmes peuvent l'implémenter si nécessaire.
    }

    /**
     * Méthode optionnelle pour effectuer des calculs spécifiques avant l'exécution du sous-programme,
     * si nécessaire.
     *
     * @param stmt `CallableStatement` pour les calculs.
     * @param donnee Objet contenant les données nécessaires pour les calculs.
     * @throws SQLException Si une erreur survient lors du calcul.
     */
    default void parametresCalcul(CallableStatement stmt, T donnee) throws SQLException {
        // Par défaut, la méthode peut être vide. Les sous-programmes peuvent l'implémenter si nécessaire.
    }
}
