package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
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

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String message;

            while ((message = input.readLine()) != null) {

                String[] parts1 = message.split("/");
                String userSender = parts1[0];
                String onlyTheMessage = parts1[1];

                if (onlyTheMessage.startsWith("@")) {
                    String[] parts = onlyTheMessage.split(" ", 2);
                    String targetUsername = parts[0].substring("@".length());
                    String messageToSend = parts[1];
                    Server.sendMessageTo(targetUsername, messageToSend,userSender);
                } else {
                    //ICI ON SEND POUR LA BASE DE DONNEES

                    Server.broadcastMessage(userSender +": " +onlyTheMessage);
                }

                ///on envoit ici le message a la base de donnés

            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la réception du message : " + e.getMessage());
        } finally {
            Server.clientHandlers.remove(this);
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Erreur lors de la fermeture dusocket client : " + e.getMessage());
            }
        }
    }
}