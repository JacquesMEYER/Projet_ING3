package view;

import controller.MessageController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GifButtons extends JDialog {
    public GifButtons(MessageController messageController) {
        setTitle("Choose GIF");
        setSize(250, 150);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());

        JButton happyButton = new JButton("Happy");
        JButton surprisedButton = new JButton("Surprised");
        JButton angryButton = new JButton("Angry");
        JButton sadButton = new JButton("Sad");

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

        add(happyButton);
        add(surprisedButton);
        add(angryButton);
        add(sadButton);

        setLocationRelativeTo(null);
        setModal(true);
    }
}
