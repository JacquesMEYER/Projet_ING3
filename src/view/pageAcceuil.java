package view;

import controller.MessageController;

import javax.swing.*;
import java.net.MalformedURLException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.Timer;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

public class pageAcceuil extends JFrame implements ActionListener {
    private  MessageController messageController;

    static JEditorPane chatArea;
    JPanel userButtonsPanel;
    JTextField messageField;
    JButton sendButton;
    JLabel userTypeLabel;
    Vector<JButton> userButtons;
    //static Set<String> nomsCo = new HashSet<>(Arrays.asList("jac","rom","alix","ethan"));

    Color bleuclair = new Color(234, 242, 248);
    Color bleufonce2 = new Color(31, 97, 141);
    HTMLEditorKit editorKit;
    HTMLDocument doc;


    private static Set<String> nomsCo = new HashSet<>(Arrays.asList("-"));

    public void updateUserButtons(Set<String> nomsCo, JPanel userButtonsPanel, Set<String> vrmtCo) {

        userButtonsPanel.removeAll();
        String nomV2 = "";
        for (String nom : nomsCo) {
            if (!Objects.equals(nom, "unknown")){
                if (nom == messageController.getModel().getUser().getUsername()) {
                    nomV2 = nom + " (me)";
                } else nomV2 = nom;
                JButton userButton = new JButton(nomV2);
                if (vrmtCo.contains(nom)) {
                    userButton.setBackground(Color.GREEN);
                } else userButton.setBackground(Color.PINK);
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
        }

        userButtonsPanel.revalidate();
        userButtonsPanel.repaint();
    }

    public pageAcceuil(MessageController messageController) {
        this.messageController = messageController;
        // Création de la fenêtre principale
        setTitle("Messagerie Instantanée");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Création du panneau principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Création du panneau de gauche pour la liste des utilisateurs et la barre de recherche
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());


        //Bouton profil
        JButton profilButton = new JButton("\ud83d\udc64");
        profilButton.setBackground(bleufonce2);
        profilButton.setForeground(bleuclair);
        leftPanel.add(profilButton, BorderLayout.NORTH);
        profilButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame f = new ProfilWindow();
                f.setVisible(true);
            }
        });

        JPanel listPanel= new JPanel();
        listPanel.setLayout(new BorderLayout());
        JLabel userListLabel = new JLabel("         User List       ");
        listPanel.add(userListLabel, BorderLayout.NORTH);
        userButtonsPanel = new JPanel();
        userButtonsPanel.setLayout(new BoxLayout(userButtonsPanel, BoxLayout.Y_AXIS));
        userButtons = new Vector<>();
        JScrollPane userButtonScrollPane = new JScrollPane(userButtonsPanel);




        for (String nom : nomsCo) {
            JButton userButton = new JButton(nom);

            userButtons.add(userButton); //ajout au vecteur
            userButtonsPanel.add(userButton);// ajout du vecteur au left pannel
            userButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Fermer la page d'accueil lorsqu'un bouton utilisateur est cliqué
                    parametter parametter = new parametter(userButton.getText(), messageController);
                    parametter.setVisible(true);
                }
            });
        }
        listPanel.add(userButtonScrollPane, BorderLayout.CENTER);

        //creation de la barre de recherche
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
        JTextField searchField = new JTextField();
        searchPanel.add(searchField, BorderLayout.CENTER);
        JButton searchButton = new JButton("\uD83D\uDD0E");
        searchPanel.add(searchButton, BorderLayout.EAST);

        //definir la focntion qui permet de rechercher un utilisateur
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTerm = searchField.getText().toLowerCase(); //obtenir le texte de recherche saisi et le convertir en minuscules pour une correspondance insensible à la casse
                for (JButton userButton : userButtons) { //parcourir tous les boutons d'utilisateur
                    String buttonText = userButton.getText().toLowerCase(); //obtenir le texte du bouton et le convertir en minuscules pour une correspondance insensible à la casse
                    if (buttonText.contains(searchTerm)) { //vérifier si le terme de recherche est contenu dans le texte du bouton
                        userButton.setVisible(true); //afficher le bouton utilisateur correspondant
                    } else {
                        userButton.setVisible(false); //masquer les boutons utilisateur qui ne correspondent pas
                    }
                }            }
        });



        listPanel.add(searchPanel, BorderLayout.SOUTH);
        leftPanel.add(listPanel, BorderLayout.CENTER);
        mainPanel.add(leftPanel, BorderLayout.WEST);


        // Création du panneau central pour le salon de discussion et les messages
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        chatArea = new JEditorPane();

        chatArea.setEditable(false);
        chatArea.setContentType("text/html");
        editorKit = new HTMLEditorKit();
        doc = new HTMLDocument();
        chatArea.setEditorKit(editorKit);
        chatArea.setDocument(doc);
        chatArea.setBackground(bleuclair);
        JScrollPane chatScrollPane = new JScrollPane(chatArea);
        centerPanel.add(chatScrollPane, BorderLayout.CENTER);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Création du panneau inférieur pour le champ de texte et le bouton d'envoi
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        messageField = new JTextField();

        // Création d'un bouton permettant d'envoyer un GIF
        JButton showGifButton = new JButton("GIF");
        bottomPanel.add(showGifButton, BorderLayout.WEST);

        showGifButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GifButtons gifButtons = new GifButtons(messageController);
                gifButtons.setVisible(true);
            }
        });

        bottomPanel.add(messageField, BorderLayout.CENTER);
        sendButton = new JButton("\u27a4");
        sendButton.setForeground(Color.WHITE);
        sendButton.setBackground(new Color(18, 91, 218, 255));

        // Création d'un JPanel pour afficher le type d'utilisateur
        JPanel userTypePanel = new JPanel();
        userTypePanel.setLayout(new BorderLayout());
        userTypeLabel = new JLabel("(" + messageController.getModel().getUser().getUserType()+")");
        userTypePanel.add(userTypeLabel, BorderLayout.NORTH);


        userTypePanel.add(userTypeLabel, BorderLayout.NORTH);

        // Ajout du JPanel userTypePanel au panneau principal
        mainPanel.add(userTypePanel, BorderLayout.NORTH);

        // quand la touche entrée est pressée dans le champ de text, le bouton send est déclenché
        messageField.addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(KeyEvent e) {
                // Vérifiez si la touche pressée est "Entrée"
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    // Déclenchez l'événement de clic sur le bouton
                    sendButton.doClick();
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
                // méthode obligatoire pour utiliser KeyListener MAIS pas utiliser pour nous
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // méthode obligatoire pour utiliser KeyListener MAIS pas utiliser pour nous
            }
        });

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage(messageField.getText());

            }
        });


        bottomPanel.add(sendButton, BorderLayout.EAST);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Ajout du panneau principal à la fenêtre
        setContentPane(mainPanel);

        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        startTimer();

    }

    private void sendMessage(String mess) {

        String message = messageField.getText();
        //chatArea.append("Moi : " + mess + "\n");
        messageController.sendMessage(message);
        messageField.setText("");
    }

    public void addGif(String urlMessage, boolean isLeft) {
        System.out.println("TEST");
        String gifUrl = "";
        if (Objects.equals(urlMessage, "GIFhappy")) {
            gifUrl = "https://media.giphy.com/media/5GoVLqeAOo6PK/giphy.gif";
        } else if (Objects.equals(urlMessage, "GIFsurprised")) {
            gifUrl = "https://media.giphy.com/media/l3q2K5jinAlChoCLS/giphy.gif";
        } else if (Objects.equals(urlMessage, "GIFangry")) {
            gifUrl = "https://media.giphy.com/media/3t7RAFhu75Wwg/giphy.gif";
        } else if (Objects.equals(urlMessage, "GIFsad")) {
            gifUrl = "https://media.giphy.com/media/d2lcHJTG5Tscg/giphy.gif";
        }

        String currentTime = getCurrentTime();
        String align = isLeft ? "left" : "right";
        String style = String.format("<div style='text-align: %s; max-width: 100%%; margin-bottom: 5px;'><img src='%s' width='300' height='200'><br><span style='font-size: 0.8em; color: white;display: inline-flex;'>%s</span></div>", align, gifUrl, currentTime);
        try {
            editorKit.insertHTML(doc, doc.getLength(), style, 0, 0, null);
            chatArea.setCaretPosition(chatArea.getDocument().getLength());
        } catch (BadLocationException | IOException e) {
            e.printStackTrace();
        }
    }

    public void addMessage(String message) {
        //chatArea.append(message + "\n");
        sendMessageLeft(message+ "\n");
    }

    public void setNomsCo(Set<String> nomsCo) {
        pageAcceuil.nomsCo = nomsCo;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }


    public JPanel getUserButtonsPanel() {
        return userButtonsPanel;
    }

    public void startTimer() {
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nomsCo = messageController.getNomsCo();
                updateUserButtons(nomsCo, userButtonsPanel, messageController.getVrmtCo());
            }
        });
        timer.start();
    }
    public void updateUserTypeLabel() {//lulkvjh
        userTypeLabel.setText("(" + messageController.getModel().getUser().getUserType() + ")");
    }


    public static String getCurrentTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return LocalTime.now().format(formatter);
    }

    public void sendMessageRight(String mess) {
        String currentTime = getCurrentTime();
        String rightStyle = "<div style='text-align: right; background-color: #85C1E9; color:white; max-width: 50px; padding: 5px 10px; border:0px black solid; margin-left:350px; margin-bottom: 5px;'>%s<br><span style='font-size: 0.8em; color: white;display: inline-flex; '>%s</span></div><br>";
        appendMessage(mess, currentTime, rightStyle);
    }
    //    background-size: 10px; display: inline-block;
    public void sendMessageLeft(String mess) {
        String currentTime = getCurrentTime();
        String leftStyle = "<div style=' text-align: left; background-color: #3498DB; color:white; max-width: 50px; padding: 5px 10px; border:0px black solid; margin-right:350px; margin-bottom: 5px;'>%s<br><span style='font-size: 0.8em; color: white; display: inline-flex;'>%s</span></div>";

        appendMessage(mess, currentTime, leftStyle);
    }

    public void sendMessageCenter(String mess) {
        String currentTime = getCurrentTime();
        String centerStyle = "<div style='text-align: center; font-style: italic; display: inline-block;  max-width: 100%%; padding: 3px 10px;  margin-bottom: 5px;'>%s<span style='font-size: 0.8em; color: white; padding-left: 5px;'>%s</span></div><br>";
        appendMessage(mess, currentTime, centerStyle);
    }

    private void appendMessage(String mess, String currentTime, String style) {
        try {
            String formattedMessage = String.format(style, mess, currentTime);
            editorKit.insertHTML(doc, doc.getLength(), formattedMessage, 0, 0, null);
            chatArea.setCaretPosition(chatArea.getDocument().getLength());
        } catch (BadLocationException | IOException e) {
            e.printStackTrace();
        }
    }
}



