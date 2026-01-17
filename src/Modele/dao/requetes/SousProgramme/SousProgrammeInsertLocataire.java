package Modele.dao.requetes.SousProgramme;

import java.sql.CallableStatement;
import java.sql.SQLException;

import Modele.Locataire;

public class SousProgrammeInsertLocataire implements SousProgramme<Locataire> {

    @Override
    public String appelSousProgramme() {
        return "{call SND5405A.SAE_Insert_Locataire(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    }

    @Override
    public void parametres(CallableStatement stmt, Locataire loc) throws SQLException {
    	
    	System.out.println("Locataire debug:");
    	System.out.println("ID: '" + loc.getIdLocataire() + "'");
    	System.out.println("Telephone: '" + loc.getTelephone() + "'");
    	System.out.println("Adresse: '" + loc.getAdresse() + "'");
    	System.out.println("Email: '" + loc.getEmail() + "'");

        stmt.setString(1, loc.getIdLocataire());
        stmt.setString(2, loc.getNom());
        stmt.setString(3, loc.getPrenom());

        // Safe conversion to java.sql.Date
        try {
            java.sql.Date dateNaissance = java.sql.Date.valueOf(loc.getDate_naissance().trim());
            stmt.setDate(4, dateNaissance);
        } catch (IllegalArgumentException e) {
            throw new SQLException("Date de naissance invalide ! Utilisez le format YYYY-MM-DD.", e);
        }

        stmt.setString(5, String.valueOf(loc.getGenre()));
        stmt.setString(6, loc.getEmail());
        stmt.setString(7, loc.getAdresse());
        stmt.setString(8, loc.getVille());
        stmt.setString(9, loc.getCode_postale());
        String tel = loc.getTelephone().trim();
        byte[] telBytes = tel.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        System.out.println("Téléphone: '" + tel + "' | Chars: " + tel.length() + 
                           " | Bytes UTF-8: " + telBytes.length + 
                           " | Hex: " + bytesToHex(telBytes));
        stmt.setString(10, tel);
    }
    
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b & 0xFF));
        }
        return sb.toString();
    }

    @Override
    public void parametresSequence(CallableStatement stmt, Locataire donnees) throws SQLException { }

    @Override
    public void parametresCalcul(CallableStatement stmt, Locataire donnees) throws SQLException { }
}
