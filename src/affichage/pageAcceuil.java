package affichage;
import controller.MessageController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class pageAcceuil extends JFrame implements ActionListener {
   private static MessageController messageController;
     JTextArea chatArea;
    JPanel userButtonsPanel;
     JTextField messageField;
     JButton sendButton;
    Vector<JButton> userButtons;
    //static Set<String> nomsCo = new HashSet<>(Arrays.asList("jac","rom","alix","ethan"));
    private static Set<String> nomsCo = new HashSet<>(Arrays.asList("jac"));

    public void updateUserButtons(Set<String> nomsCo, JPanel userButtonsPanel,Set<String> vrmtCo) {

        userButtonsPanel.removeAll();
        for (String nom : nomsCo) {
            JButton userButton = new JButton(nom);
            if(vrmtCo.contains(nom)) {
                userButton.setBackground(Color.GREEN);
            }else userButton.setBackground(Color.PINK);
            userButtons.add(userButton);

            userButtonsPanel.add(userButton);
            userButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    parametter parametter = new parametter(userButton.getText(), messageController);
                    parametter.setVisible(true);
                }
            });
        }

        userButtonsPanel.revalidate();
        userButtonsPanel.repaint();
    }

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
        JLabel userListLabel = new JLabel("Utilisateurs");
        leftPanel.add(userListLabel, BorderLayout.NORTH);

        userButtonsPanel = new JPanel();
        userButtonsPanel.setLayout(new BoxLayout(userButtonsPanel, BoxLayout.Y_AXIS));
        userButtons = new Vector<>();


        for (String nom : nomsCo) {
            JButton userButton = new JButton(nom);
                userButtons.add(userButton); //ajout au vecteur
                userButtonsPanel.add(userButton);// ajout du vecteur au left pannel
            userButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Fermer la page d'accueil lorsqu'un bouton utilisateur est cliqué
                    parametter parametter = new parametter(userButton.getText(),messageController);
                    parametter.setVisible(true);
                }
            });
    }
        leftPanel.add(userButtonsPanel,BorderLayout.CENTER);
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
                nomsCo = messageController.getNomsCo();

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

    public  void setNomsCo(Set<String> nomsCo) {
        pageAcceuil.nomsCo = nomsCo;
    }


    //jsp a quoi servent
    @Override
    public void actionPerformed(ActionEvent e) {

    }


    public JPanel getUserButtonsPanel() {
        return userButtonsPanel;
    }

}



