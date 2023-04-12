package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Server {

    private static final int SERVER_PORT = 8888;
    private static final String SERVER_IP = "192.168.1.49";

    static Set<String> bannedUser = new HashSet<>();

    static Set<ClientHandler> clientHandlers = new HashSet<>();

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
                broadcastMessage(username + " has entered the chat ");
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
                handler.getWriter().println("(private message from " + userSender + ") " + message);

                break;
            }
        }
        for (ClientHandler handler : clientHandlers) {
            if (handler.getUsername().equalsIgnoreCase(userSender)) {
                handler.getWriter().println("(private message to " + username + ") " + message);
                break;
            }
        }
    }

    public static void broadcastMessage(String message) {
        for (ClientHandler handler : clientHandlers) {
            if (bannedUser.contains(handler.getUsername())) {
                handler.getWriter().println("pas de message pour toi, t'es banni!!!!!");
            } else {
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
                handler.getWriter().println("pas de message pour toi, t'es banni");
            }
        }
    }

    public static void afficherQuiEstCo(String userSender) {

        String noms = "";
        int i=0;
        for (ClientHandler handler : clientHandlers) { //pour eviter l espace au debut
            if(i==0){
                noms = noms + handler.getUsername();
            }else noms=noms +" "+ handler.getUsername();
            i++;
            }
        System.out.println(noms);
        System.out.println(userSender);
        for (ClientHandler handler : clientHandlers) {
            if (handler.getUsername().equalsIgnoreCase(userSender)) {
                handler.getWriter().println("les utilisateurs co sont : " + noms);
            }
        }
    }
}
