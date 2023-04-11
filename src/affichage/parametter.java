package affichage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;


public class parametter extends JFrame implements ActionListener, Observer {
    JButton ban, stat, user_type;
    int userType = 0;
        public parametter(){
            super("Parametter");
            JPanel parpan = new JPanel(new GridLayout(3, 1));
            ban = new JButton("Ban");
            ban.setBackground(Color.PINK);

            user_type = new JButton("user-type");
            user_type.setBackground(Color.PINK);

            stat = new JButton("stat");
            stat.setBackground(Color.PINK);

            parpan.add(ban);parpan.add(stat);parpan.add(user_type);

            // Définir la couleur d'arrière-plan de la fenêtre et du JPanel
            parpan.setBackground(Color.white);
            getContentPane().add(parpan, BorderLayout.CENTER);
            getContentPane().setBackground(Color.blue);

            // Définir les propriétés de la fenêtre JFrame
            pack();
            setVisible(true);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

    @Override
    public void actionPerformed(ActionEvent e) {
            if (e.getSource() == ban){
                if (userType != 1){
                    JOptionPane.showMessageDialog(this, "YOU CANNOT");
                }
                else{
                    dispose();
                }
            }
             if (e.getSource() == stat){
                 if (userType != 1){
                     JOptionPane.showMessageDialog(this, "YOU CANNOT");
                 }
             }
            if (e.getSource() == user_type){
                if (userType != 1){
                    JOptionPane.showMessageDialog(this, "YOU CANNOT");
                }
            }

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}

