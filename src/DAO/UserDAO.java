package DAO;

import model.Utilisateur;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.List;

public class UserDAO implements DAO<Utilisateur> {
    private Connection conn;

    public UserDAO(Connection connection){
        this.conn = connection;
    }

    public Utilisateur getUserById(int id) {
        //code pour récupérer un utilisateur en utilisant l'identifiant
        return null;
    }

    public List<Utilisateur> getAllUsers() {
        //code pour récupérer tous les utilisateurs
        return null;
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(password.getBytes());
            byte[] byteData = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : byteData) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addUser(String username, String pwd) throws NoSuchAlgorithmException {
        //méthode pour ajouter un utilisateur dans la base de données
        try {
            Statement stmt = this.conn.createStatement();
            String query = "INSERT INTO user(username, pwd) VALUES('" + username + "', '" + hashPassword(pwd) + "')";
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateUser(Utilisateur user) {
        //code pour mettre à jour un utilisateur
        try {
            Statement stmt = this.conn.createStatement();
            String query = "UPDATE user(username, pwd, status) SET ('" + user.getUsername() + "', '" + user.getPassword() + "', '" + user.getStatus() + "')";
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isValidUser(String username, String password) throws NoSuchAlgorithmException {
        // cryptage du mot de passe écrit par l'utilisateur
        String hashedPwd = hashPassword(password);
        System.out.println(hashedPwd);

        // recherche dans la BDD s'il existe un utilisateur avec le nom et mot de passe entrés
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT pwd FROM user WHERE username = ?");
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();

            if(rs.next()) {
                String correctHashedPwd = rs.getString("pwd");
                System.out.println(correctHashedPwd);

                if(correctHashedPwd.equals(hashedPwd)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUser(Utilisateur user) {
        //code pour supprimer un utilisateur
    }

    @Override
    public Utilisateur get(int id) {
        return null;
    }

    @Override
    public List<Utilisateur> getAll() {
        return null;
    }

    @Override
    public void save(Utilisateur utilisateur) {

    }

    @Override
    public void update(Utilisateur utilisateur) {

    }

    @Override
    public void delete(Utilisateur utilisateur) {

    }
}
