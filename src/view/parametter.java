package view;

import controller.MessageController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static model.Utilisateur.UserType.ADMINISTRATOR;
import static model.Utilisateur.UserType.MODERATOR;

public class parametter extends JFrame implements ActionListener {
    JButton ban, unban,viewBannedUsers, user_type, setClassicButton, setAdminButton, setModeratorButton;
    Color bleuclair = new Color(234, 242, 248);
    Color bleufonce1 = new Color(36, 113, 163);
    Color bleufonce2 = new Color(31, 97, 141);
    Color bleufonce3 = new Color(23, 32, 42);
    JPanel userTypeButtonsPanel, cards;
    private MessageController messageController;

    private String nom;

    public parametter(String nom, MessageController messageController) {
        super("Parametter");
        this.messageController = messageController;
        this.nom = nom;
        JPanel parpan = new JPanel(new GridLayout(0, 1)); // Changez le layout en GridLayout avec un nombre variable de lignes
       parpan.setPreferredSize(new Dimension(200, 300));

        ban = new JButton("Ban");
        ban.addActionListener(this);
        ban.setBackground(bleufonce1);
        ban.setForeground(bleuclair);

        unban = new JButton("UnBan");
        unban.addActionListener(this);
        unban.setBackground(bleufonce1);
        unban.setForeground(bleuclair);

        viewBannedUsers = new JButton("viewBannedUsers");
        viewBannedUsers.addActionListener(this);
        viewBannedUsers.setBackground(bleufonce1);
        viewBannedUsers.setForeground(bleuclair);

        user_type = new JButton("user-type");
        user_type.addActionListener(this);
        user_type.setBackground(bleufonce1);
        user_type.setForeground(bleuclair);

        parpan.add(ban);
        parpan.add(unban);
        parpan.add(viewBannedUsers);
        parpan.add(user_type);

        // Créez un nouveau JPanel pour les boutons de type d'utilisateur
        userTypeButtonsPanel = new JPanel(new GridLayout(3, 1));
        //userTypeButtonsPanel.setPreferredSize(new Dimension(300, 300));

        setAdminButton = new JButton("Set Admin");
        setAdminButton.addActionListener(this);
        userTypeButtonsPanel.add(setAdminButton);
        setAdminButton.setBackground(bleufonce2);
        setAdminButton.setForeground(bleuclair);

        setModeratorButton = new JButton("Set Moderator");
        setModeratorButton.addActionListener(this);
        userTypeButtonsPanel.add(setModeratorButton);
        setModeratorButton.setBackground(bleufonce2);
        setModeratorButton.setForeground(bleuclair);

        setClassicButton = new JButton("Set Classic");
        setClassicButton.addActionListener(this);
        userTypeButtonsPanel.add(setClassicButton);
        setClassicButton.setBackground(bleufonce2);
        setClassicButton.setForeground(bleuclair);

        // Créez un JPanel avec un CardLayout pour gérer l'affichage des boutons de type d'utilisateur
        cards = new JPanel(new CardLayout());
        cards.add(new JPanel(), "empty");
        cards.add(userTypeButtonsPanel, "userTypeButtons");
       // cards.setSize(200, 100);
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
        int userType;
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
        if (e.getSource() == viewBannedUsers) {
            if (userType == 3) {
                JOptionPane.showMessageDialog(this, "YOU CANNOT!");
                dispose();
            } else {
                messageController.sendMessage("/bannedUsers:");
                dispose();
            }
        }

        if (e.getSource() == user_type) { // Juste le 1 à le droit de faire ça non ?
            if (userType == 1 ||userType == 2 ) {
                CardLayout cl = (CardLayout) cards.getLayout();
                cl.next(cards);
            } else {
                JOptionPane.showMessageDialog(this, "YOU CANNOT!");
            }
        }

        if (e.getSource() == setClassicButton) {

            if (userType == 1 ||userType == 2 ) {
            messageController.sendMessage("/setUserType: " + nom + " classic");
            dispose();
            } else {
                JOptionPane.showMessageDialog(this, "YOU CANNOT!");
            }
        }

        if (e.getSource() == setAdminButton) {
            if (userType == 1 ) {
            messageController.sendMessage("/setUserType: " + nom + " admin");
            dispose();
            } else {
                JOptionPane.showMessageDialog(this, "YOU CANNOT!");
            }
        }

        if (e.getSource() == setModeratorButton) {
            if (userType == 1  ||userType == 2) {
            messageController.sendMessage("/setUserType: " + nom + " moderator");
            dispose();
            } else {
                JOptionPane.showMessageDialog(this, "YOU CANNOT!");
            }
        }
    }
}