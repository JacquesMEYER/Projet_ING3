package DAO;

import model.Utilisateur;

import java.sql.*;
import java.util.List;

public class UserDAO implements DAO<Utilisateur> {
    private Connection conn;

    /* quand il y en aura besoin :
        Connection conn = ConnectionDB.getConnection();
        UserDAO userDao = new UserDAO(conn);
     */

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

    public void addUser(String username, String pwd) {
        //méthode pour ajouter un utilisateur dans la base de données
        try {
            Statement stmt = this.conn.createStatement();
            String query = "INSERT INTO user(username, pwd) VALUES('" + username + "', '" + pwd + "')";
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateUser(Utilisateur user) {
        //code pour mettre à jour un utilisateur
    }

    public boolean isValidUser(String username, String password) {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user WHERE username = ? AND pwd = ?");
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                return true;
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
