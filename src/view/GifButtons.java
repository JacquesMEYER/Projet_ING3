package view;

import controller.MessageController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GifButtons extends JDialog {
    Color bleuclair = new Color(234, 242, 248);
    Color bleufonce2 = new Color(31, 97, 141);
    public GifButtons(MessageController messageController) {
        setTitle("Choose GIF");
        setSize(250, 250);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel gifPannel = new JPanel(new GridLayout(0, 1));

        JButton happyButton = new JButton("Happy");
        happyButton.setBackground(bleufonce2);
        happyButton.setForeground(bleuclair);

        JButton surprisedButton = new JButton("Surprised");
        surprisedButton.setBackground(bleufonce2);
        surprisedButton.setForeground(bleuclair);

        JButton angryButton = new JButton("Angry");
        angryButton.setBackground(bleufonce2);
        angryButton.setForeground(bleuclair);

        JButton sadButton = new JButton("Sad");
        sadButton.setBackground(bleufonce2);
        sadButton.setForeground(bleuclair);

        gifPannel.add(happyButton);
        gifPannel.add(surprisedButton);
        gifPannel.add(angryButton);
        gifPannel.add(sadButton);

        getContentPane().add(gifPannel, BorderLayout.CENTER);


        happyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageController.sendMessage("/GIF:GIFhappy");
                dispose();
            }
        });

        surprisedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageController.sendMessage("/GIF:GIFsurprised");
                dispose();
            }
        });

        angryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageController.sendMessage("/GIF:GIFangry");
                dispose();            }
        });

        sadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageController.sendMessage("/GIF:GIFsad");
                dispose();            }
        });



        setLocationRelativeTo(null);
        setModal(true);
    }
}
