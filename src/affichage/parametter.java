package affichage;

import controller.MessageController;
import model.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;


public class parametter extends JFrame implements ActionListener, Observer {
    JButton ban,unban, stat, user_type;
    int userType = 1;
    private MessageController messageController;

    private String nom;

    public parametter(String nom,MessageController messageController){
        super("Parametter");
        this.messageController = messageController;
        this.nom=nom;
        JPanel parpan = new JPanel(new GridLayout(4, 1));
        ban = new JButton("Ban");
        ban.addActionListener(this);
        ban.setBackground(Color.PINK);

        unban = new JButton("UnBan");
        unban.addActionListener(this);
        unban.setBackground(Color.PINK);

        user_type = new JButton("user-type");
        user_type.addActionListener(this);
        user_type.setBackground(Color.PINK);

        stat = new JButton("stat");
        stat.addActionListener(this);
        stat.setBackground(Color.PINK);

        parpan.add(ban);
        parpan.add(unban);
        parpan.add(stat);
        parpan.add(user_type);

        // Définir la couleur d'arrière-plan de la fenêtre et du JPanel
        parpan.setBackground(Color.white);
        getContentPane().add(parpan, BorderLayout.CENTER);
        getContentPane().setBackground(Color.blue);

        // Définir les propriétés de la fenêtre JFrame
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
/*
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
*/
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        userType = 1;
        if (e.getSource() == ban){
            if (userType != 1){
                JOptionPane.showMessageDialog(this, "YOU CANNOT!");
                dispose();
            }
            else{
                messageController.sendMessage("/ban:"+nom);
                dispose();
            }
        }
        if (e.getSource() == unban){
            if (userType != 1){
                JOptionPane.showMessageDialog(this, "YOU CANNOT!");
                dispose();
            }
            else{
                messageController.sendMessage("/unBan:"+nom);
                dispose();
            }
        }

        if (e.getSource() == stat){
            if (userType != 1){
                JOptionPane.showMessageDialog(this, "YOU CANNOT!");
                dispose();
            }
        }
        if (e.getSource() == user_type){
            if (userType != 1){
                JOptionPane.showMessageDialog(this, "YOU CANNOT!");
                dispose();
            }
        }

    }
    @Override
    public void update(Observable o, Object arg) {

    }
}

