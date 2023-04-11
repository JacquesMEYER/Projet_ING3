package affichage;

import controller.MessageController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class pageAcceuil extends JFrame implements ActionListener , Observer {
   private MessageController messageController;
     JTextArea chatArea;
     JTextField messageField;
     JButton sendButton;
     JButton user1, user2, user3;


    public pageAcceuil(MessageController messageController) {
        this.messageController = messageController;
        // Création de la fenêtre principale
        setTitle("Messagerie Instantanée");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Création du panneau principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Création du panneau de gauche pour la liste des utilisateurs et la barre de recherche
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        JLabel userListLabel = new JLabel("Liste des utilisateurs");
        leftPanel.add(userListLabel, BorderLayout.NORTH);
        user1 = new JButton(messageController.getModel().getUser().getUsername());
        //user2 = new JButton("user2");
        //user3 = new JButton("user3");

        leftPanel.add(user1,BorderLayout.NORTH);




        JLabel searchLabel = new JLabel("Rechercher un utilisateur");
        leftPanel.add(searchLabel, BorderLayout.SOUTH);
        JTextField searchField = new JTextField();
        leftPanel.add(searchField, BorderLayout.SOUTH);
        mainPanel.add(leftPanel, BorderLayout.WEST);


        // Création du panneau central pour le salon de discussion et les messages
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane chatScrollPane = new JScrollPane(chatArea);
        centerPanel.add(chatScrollPane, BorderLayout.CENTER);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Création du panneau inférieur pour le champ de texte et le bouton d'envoi
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        messageField = new JTextField();
        bottomPanel.add(messageField, BorderLayout.CENTER);
        sendButton = new JButton("Envoyer");


        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                sendMessage(messageField.getText());
            }
        });
        user1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    parametter parametter = new parametter();
                    parametter.setVisible(true);
            }
        });

        bottomPanel.add(sendButton, BorderLayout.EAST);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Ajout du panneau principal à la fenêtre
        setContentPane(mainPanel);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
        private void sendMessage(String mess) {

        String message = messageField.getText();
        //chatArea.append("Moi : " + mess + "\n");
        messageController.sendMessage(message);
        messageField.setText("");
    }

    public void addMessage(String message) {
        chatArea.append(message + "\n");
    }

    //jsp a quoi servent
    @Override
    public void actionPerformed(ActionEvent e) {

    }
    @Override
    public void update(Observable o, Object arg) {

    }
}



