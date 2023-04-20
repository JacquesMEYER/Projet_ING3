package DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class MessageDAO {
    private Connection conn;

    public MessageDAO(Connection connection){
        this.conn = connection;
    }

    public void deleteMsg(String msg) {

    }

    public void addMsg(String msg) {
        //String[] parts1 = msg.split("¤");
        //String userSender = parts1[0];
        //String onlyTheMessage = parts1[1];

        try {
            Statement stmt = this.conn.createStatement();
            String query = "INSERT INTO message(text) VALUES('" + msg + "')";
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
