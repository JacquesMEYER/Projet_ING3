package server;

import DAO.ConnectionDB;
import DAO.UserDAO;
import model.IPAddress;
import view.pageAcceuil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class Server {

    private static final int SERVER_PORT = 9999;
    private static final String SERVER_IP =IPAddress.getIpAddress().getHostAddress(); // retourne l'adress ip de ton ordi

    static Set<String> bannedUser = new HashSet<>();

    static Set<ClientHandler> clientHandlers = new HashSet<>();
    //static DriverManager DriverManager;

    public static void main(String[] args) {
        System.out.println("Démarrage du serveur...");

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT, 50, InetAddress.getByName(SERVER_IP))) {
            while (true) {
                bannedUser.add("test");

                Socket clientSocket = serverSocket.accept();
                System.out.println("model.Client connecté : " + clientSocket.getInetAddress());

                PrintWriter clientWriter = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String username = input.readLine();
                ClientHandler clientHandler = new ClientHandler(clientSocket, clientWriter, username);
                //broadcastMessage("* " + username + " has entered the chat *");
                //Server.afficherQuiEstCo();

                clientHandlers.add(clientHandler);

                Thread clientThread = new Thread(clientHandler);
                clientThread.start();

            }
        } catch (IOException e) {
            System.err.println("Erreur lors de l'ouverture du socket serveur : " + e.getMessage());
        }
    }

    public static void sendMessageTo(String username, String message, String userSender) {

        for (ClientHandler handler : clientHandlers) {
            if (handler.getUsername().equalsIgnoreCase(username)) {
                handler.getWriter().println(userSender+" (private message from " + userSender + ") " + message);

                break;
            }
        }
        for (ClientHandler handler : clientHandlers) {
            if (handler.getUsername().equalsIgnoreCase(userSender)) {
                handler.getWriter().println(userSender+" (private message to " + username + ") " + message);
                break;
            }
        }
    }

    public static void broadcastMessage(String message) {
        for (ClientHandler handler : clientHandlers) {
            if (bannedUser.contains(handler.getUsername())) {
                handler.getWriter().println("* pas de message pour toi, t'es banni!!!!! *");
            }
            else {
                handler.getWriter().println(message);
            }
        }

    }

    public static void addBanned(String username) {
        bannedUser.add(username);
    }

    public static void unBan(String username) {
        bannedUser.remove(username);
    }

    public static Boolean IsBanned(String username) {
        for (String banned : bannedUser) {
            if (username.equalsIgnoreCase(banned)) {
                return true;
            }
        }
        return false;
    }

    public static void sendMessageTBanned(String username) {
        for (ClientHandler handler : clientHandlers) {
            if (handler.getUsername().equalsIgnoreCase(username)) {
                handler.getWriter().println("* pas de message pour toi, t'es banni *");
            }
        }
    }

    public static void afficherQuiEstCo() { //sans affichage , que pour les boutons

        String noms = "";
        int i = 0;
        for (ClientHandler handler : clientHandlers) {
            if (i == 0) {
                noms = noms + handler.getUsername();
            } else noms = noms + " " + handler.getUsername();
            i++;
        }
        // System.out.println(noms);
        //System.out.println(userSender);
        for (ClientHandler handler : clientHandlers) {
            // if (handler.getUsername().equalsIgnoreCase(userSender)) {
            handler.getWriter().println("co:" + noms);
            //}
        }
    }

    public static void afficherQuiEstCo2(String userSender) { //qui affiche dans la ocnv qui est co

        String noms = "";
        int i = 0;
        for (ClientHandler handler : clientHandlers) { //pour eviter l espace au debut
            if (i == 0) {
                noms = noms + handler.getUsername();
            } else noms = noms + " " + handler.getUsername();
            i++;
        }
        System.out.println(noms);
        System.out.println(userSender);
        for (ClientHandler handler : clientHandlers) {
            if (handler.getUsername().equalsIgnoreCase(userSender)) {
                handler.getWriter().println("les utilisateurs co sont :" + noms);
            }
        }
    }

    public static void isValidUser(String username, String password) {
        boolean isValid = false;

        try {
            Connection conn = ConnectionDB.getConnection();
            UserDAO userDao = new UserDAO(conn);
            isValid = userDao.isValidUser(username, password);

            if(isValid) {
                for (ClientHandler handler : clientHandlers) {
                    if (handler.getUsername().equalsIgnoreCase("unknown")) {
                        handler.getWriter().println("The user has an account");
                        handler.setUsername(username);
                        broadcastMessage("* " + username + " has entered the chat *");

                        break;
                    }
                }

            } else {
                for (ClientHandler handler : clientHandlers) {
                    if (handler.getUsername().equalsIgnoreCase("unknown")) {
                        handler.getWriter().println("The user has no account");
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void inscription(String username, String password) {
        // utilisation du UserDAO
        try {
            Connection conn = ConnectionDB.getConnection();
            UserDAO userDao = new UserDAO(conn);

            userDao.addUser(username, password);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public static void changeType(String targetUsername, String type, String userSender) {

        for (ClientHandler handler : clientHandlers) {
            if (handler.getUsername().equalsIgnoreCase(targetUsername)) {
                broadcastMessage("* "+ userSender+" set "+targetUsername+" to "+type+" *" );
                handler.getWriter().println("/changeType:" + type);
                break;
            }
        }
    }
    public static void displayGif(String gif, String userSender){
        broadcastMessage("/GIF: " + gif + " " + userSender);
    }
}