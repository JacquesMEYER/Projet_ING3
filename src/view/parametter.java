package view;

import controller.MessageController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static model.Utilisateur.UserType.ADMINISTRATOR;
import static model.Utilisateur.UserType.MODERATOR;

public class parametter extends JFrame implements ActionListener {
    JButton ban, unban, user_type, setClassicButton, setAdminButton, setModeratorButton;
    int userType = 1;
    JPanel userTypeButtonsPanel, cards;
    private MessageController messageController;

    private String nom;

    public parametter(String nom, MessageController messageController) {
        super("Parametter");
        this.messageController = messageController;
        this.nom = nom;
        JPanel parpan = new JPanel(new GridLayout(0, 1)); // Changez le layout en GridLayout avec un nombre variable de lignes
        ban = new JButton("Ban");
        ban.addActionListener(this);
        ban.setBackground(Color.PINK);

        unban = new JButton("UnBan");
        unban.addActionListener(this);
        unban.setBackground(Color.PINK);

        user_type = new JButton("user-type");
        user_type.addActionListener(this);
        user_type.setBackground(Color.PINK);

        parpan.add(ban);
        parpan.add(unban);
        parpan.add(user_type);

        // Créez un nouveau JPanel pour les boutons de type d'utilisateur
        userTypeButtonsPanel = new JPanel(new GridLayout(3, 1));

        setClassicButton = new JButton("Set Classic");
        setClassicButton.addActionListener(this);
        userTypeButtonsPanel.add(setClassicButton);

        setAdminButton = new JButton("Set Admin");
        setAdminButton.addActionListener(this);
        userTypeButtonsPanel.add(setAdminButton);

        setModeratorButton = new JButton("Set Moderator");
        setModeratorButton.addActionListener(this);
        userTypeButtonsPanel.add(setModeratorButton);

        // Créez un JPanel avec un CardLayout pour gérer l'affichage des boutons de type d'utilisateur
        cards = new JPanel(new CardLayout());
        cards.add(new JPanel(), "empty");
        cards.add(userTypeButtonsPanel, "userTypeButtons");

        parpan.add(cards); // Ajoutez le JPanel cards à parpan

        // Définir la couleur d'arrière-plan de la fenêtre et du JPanel
        parpan.setBackground(Color.white);
        getContentPane().add(parpan, BorderLayout.CENTER);
        getContentPane().setBackground(Color.blue);

        // Définir les propriétés de la fenêtre JFrame
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //userType=1;
       if(messageController.getModel().getUser().getUserType()==ADMINISTRATOR){
           userType =1;
       }else if(messageController.getModel().getUser().getUserType()==MODERATOR){
           userType=2;
       }else userType=3;

        if (e.getSource() == ban) {
            if (userType == 3) {
                JOptionPane.showMessageDialog(this, "YOU CANNOT!");
                dispose();
            } else {
                messageController.sendMessage("/ban:" + nom);
                dispose();
            }
        }
        if (e.getSource() == unban) {
            if (userType == 3) {
                JOptionPane.showMessageDialog(this, "YOU CANNOT!");
                dispose();
            } else {
                messageController.sendMessage("/unBan:" + nom);
                dispose();
            }
        }

        if (e.getSource() == user_type) {
            if (userType == 1 ||userType == 2 ) {
                CardLayout cl = (CardLayout) cards.getLayout();
                cl.next(cards); // Basculer entre les cartes (boutons de type dutilisateur et vide) lorsque le bouton user_type est cliqué.
            } else {
                JOptionPane.showMessageDialog(this, "YOU CANNOT!");
            }
        }
        if (e.getSource() == setClassicButton) {
            messageController.sendMessage("/setUserType: " + messageController.getModel().getUser().getUsername() + " classic");
            dispose();
        }

        if (e.getSource() == setAdminButton) {
            messageController.sendMessage("/setUserType: " + messageController.getModel().getUser().getUsername() + " admin");
            dispose();
        }

        if (e.getSource() == setModeratorButton) {
            messageController.sendMessage("/setUserType: " + messageController.getModel().getUser().getUsername() + " moderator");
            dispose();
        }

    }
}