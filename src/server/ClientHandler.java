package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.util.Arrays;

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private PrintWriter writer;
    private String username;


    public ClientHandler(Socket clientSocket, PrintWriter writer, String username) {
        this.clientSocket = clientSocket;
        this.writer = writer;
        this.username = username;
    }

    public PrintWriter getWriter() {
        return writer;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String message;

            clientSocket.getInetAddress();
            Server.afficherQuiEstCo();

            while ((message = input.readLine()) != null) {
                System.out.println("Message reçu : " + message); // Ajoutez cette ligne

                String[] parts1 = message.split("¤");
                String userSender = parts1[0];
                String onlyTheMessage = parts1[1];

                if (Server.IsBanned(userSender)) {
                    Server.sendMessageTBanned(userSender);
                } else {
                    if (onlyTheMessage.startsWith("@")) {
                        String[] parts = onlyTheMessage.split(" ", 2);
                        String targetUsername = parts[0].substring("@".length());
                        String messageToSend = parts[1];
                        Server.sendMessageTo(targetUsername, messageToSend, userSender);
                    } else if (onlyTheMessage.startsWith("/ban:")) {
                        String[] parts = onlyTheMessage.split(" ", 2);
                        String targetUsername = parts[0].substring("/ban:".length());
                        Server.broadcastMessage("* " + userSender + " has banned " + targetUsername + " *");
                        Server.addBanned(targetUsername);
                    } else if (onlyTheMessage.startsWith("/unBan:")) {
                        String[] parts = onlyTheMessage.split(" ", 2);
                        String targetUsername = parts[0].substring("/unBan:".length());
                        Server.unBan(targetUsername);
                        Server.broadcastMessage("* " + userSender + " has unbanned " + targetUsername + " *");
                    } else if (onlyTheMessage.startsWith("/quiCo")) {
                        Server.afficherQuiEstCo();
                    } else if (onlyTheMessage.startsWith("/co")) {
                        Server.afficherQuiEstCo2(userSender);
                    } else if (onlyTheMessage.startsWith("/testConnexion:")) {
                        System.out.println("Test connexion détecté"); // Ajoutez cette ligne
                        String[] parts = onlyTheMessage.split("\\s+"); // Divise la chaîne en fonction des espaces
                        String nom = parts[1];
                        String mdp = parts[2];
                        Server.isValidUser(nom,mdp);
                    } else if (onlyTheMessage.startsWith("/GIF:")) {
                        //String url = onlyTheMessage.substring("/GIF:".length());
                        String[] parts = onlyTheMessage.split(" ", 2);
                        String url = parts[0].substring("/GIF:".length());
                        Server.displayGif(url, userSender);
                        //System.out.println("hello");
                        // Créer fonction dans server et clientHandler

                    } else if (onlyTheMessage.startsWith("/testInscription:")) {
                        String[] parts = onlyTheMessage.split("\\s+"); // Divise la chaîne en fonction des espaces
                        String nom = parts[1];
                        String mdp = parts[2];
                        Server.inscription(nom,mdp);
                    }else if (onlyTheMessage.startsWith("/setUserType:")) {
                        String[] parts = onlyTheMessage.split("\\s+"); // Divise la chaîne en fonction des espaces
                        String targetUsername = parts[1];
                        String type = parts[2];
                        Server.changeType(targetUsername, type, userSender);
                    }

                    else {
                        Server.broadcastMessage(userSender + ": " + onlyTheMessage);
                    }
                   Server.afficherQuiEstCo();
                }

            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la réception du message : " + e.getMessage());
        } finally {
            Server.broadcastMessage("* " + username + " has left the chat *");
            Server.clientHandlers.remove(this);
            Server.afficherQuiEstCo(); //mettre a jour les boutons des autres

            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Erreur lors de la fermeture dusocket client : " + e.getMessage());
            }
        }
    }

}