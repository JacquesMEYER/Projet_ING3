package view;


import controller.LoginController;
import model.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.*;




public class ProfilWindow extends JFrame {
    private JTextArea chatArea;
    private JTextField messageField;
    private JButton logOutButton;
    private JButton returnButton;



    public ProfilWindow() {
        // Création de la fenêtre principale
        setTitle("Profil");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Création du panneau principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());


        returnButton = new JButton("\u2b98 Back");
        leftPanel.add(returnButton, BorderLayout.NORTH);
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        //init parameters panel
        JPanel informationPanel = new JPanel();
        informationPanel.setLayout(new GridLayout( 4,1));
        JButton parameterButton = new JButton("Edit profil \u270E");
        JButton statsButton = new JButton("Stats");
        informationPanel.add(parameterButton);
        informationPanel.add(statsButton);
        leftPanel.add(informationPanel,BorderLayout.CENTER);

        logOutButton= new JButton("Log Out \u274c");
        logOutButton.setForeground(Color.WHITE);
        logOutButton.setBackground(new Color(204, 13, 13));
        leftPanel.add(logOutButton, BorderLayout.SOUTH);
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();
                //System.exit(0);
                Client model = new Client(); //creer le client apres la page login sinon entered trop tot

                LoginController loginController = new LoginController(model);
                LoginPage view = new LoginPage(loginController);
                loginController.setView(view);
            }
        });


        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());

        JLabel informationLabel = new JLabel("Edit Information");
        centerPanel.add(informationLabel, BorderLayout.NORTH);

        JPanel displayPanel = new JPanel(new GridLayout(7, 2));
        JScrollPane paramScrollPane = new JScrollPane(displayPanel);
        centerPanel.add(paramScrollPane,BorderLayout.CENTER);

        JLabel containImageLabel = new JLabel();
        containImageLabel.setVisible(true);
        displayPanel.add(containImageLabel);

        JLabel userImageLabel = new JLabel();
        userImageLabel.setVisible(true);
        userImageLabel.setSize(60,60);
        userImageLabel.setForeground(Color.BLACK);
        containImageLabel.add(userImageLabel);
        JButton imageButton = new JButton("Upload Image \ud83d\udce5");
        displayPanel.add(imageButton);

        imageButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                JFileChooser file = new JFileChooser();
                file.setCurrentDirectory(new File(System.getProperty("user.home")));
                //filter the files
                FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg","gif","png");
                file.addChoosableFileFilter(filter);
                int result = file.showSaveDialog(null);
                //if the user click on save in Jfilechooser
                if(result == JFileChooser.APPROVE_OPTION){
                    File selectedFile = file.getSelectedFile();
                    String path = selectedFile.getAbsolutePath();
                    userImageLabel.setIcon(ResizeImage(path, userImageLabel));
                }
                //if the user click on save in Jfilechooser


                else if(result == JFileChooser.CANCEL_OPTION){
                    System.out.println("No File Select");
                }
            }
        });

        JLabel userNameLabel = new JLabel("Name : ");
        displayPanel.add(userNameLabel);
        JTextField nameField = new JTextField("Romain");
        displayPanel.add(nameField);
        nameField.setSize(200, 24);

        JLabel userPseudoLabel = new JLabel("Pseudo : ");
        displayPanel.add(userPseudoLabel);
        JTextField pseudoField = new JTextField("roro_69");
        displayPanel.add(pseudoField);
        pseudoField.setSize(200, 24);

        JLabel userPWLabel = new JLabel("Password : ");
        userPWLabel.setBackground(Color.BLUE);
        displayPanel.add(userPWLabel);
        JTextField pwField = new JTextField("roro1234");
        displayPanel.add(pwField);
        pwField.setSize(200, 24);

        JLabel userStatusLabel = new JLabel("Status : ");
        displayPanel.add(userStatusLabel);
        //JTextField statusField = new JTextField("online");
        //displayPanel.add(statusField);

        JCheckBox onlineCheckbox = new JCheckBox("online");
        displayPanel.add(onlineCheckbox);
        displayPanel.add(new JLabel());
        JCheckBox offlineCheckbox = new JCheckBox("offline");
        displayPanel.add(offlineCheckbox);
        displayPanel.add(new JLabel());
        JCheckBox awayCheckbox = new JCheckBox("away");
        displayPanel.add(awayCheckbox);
        //fonctions pour avoir seulement une box check a la fois
        ButtonGroup checkBoxGroup = new ButtonGroup();
        checkBoxGroup.add(onlineCheckbox);
        checkBoxGroup.add(offlineCheckbox);
        checkBoxGroup.add(awayCheckbox);




        JButton saveButton= new JButton("Save Changes \u2705");
        saveButton.setForeground(Color.WHITE);
        saveButton.setBackground(new Color(33, 141, 10));

        centerPanel.add(saveButton, BorderLayout.SOUTH);



        //init stats panel
        JLabel statsLabel = new JLabel("Statistics");
        JPanel statsPanel = new JPanel(new GridLayout(3, 2));
        JScrollPane statsScrollPane= new JScrollPane(statsPanel);


        JLabel numUserLabel = new JLabel("Number of user : ");
        statsPanel.add(numUserLabel);
        JLabel numUser = new JLabel("6");
        statsPanel.add(numUser);

        JLabel numberMessLabel = new JLabel("Number of messages : ");
        statsPanel.add(numberMessLabel);
        JLabel numberMess = new JLabel("31");
        statsPanel.add(numberMess);

        JLabel numberOnlineLabel = new JLabel(" Number of people online : ");
        statsPanel.add(numberOnlineLabel);
        JLabel numberOnline = new JLabel("2");
        statsPanel.add(numberOnline);






        statsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                centerPanel.remove(paramScrollPane);
                centerPanel.remove(informationLabel);
                centerPanel.remove(saveButton);

                centerPanel.add(statsLabel, BorderLayout.NORTH);
                centerPanel.add(statsScrollPane,BorderLayout.CENTER);


                setContentPane(mainPanel);


            }
        });

        parameterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                centerPanel.remove(statsLabel);
                centerPanel.remove(statsScrollPane);
                centerPanel.add(informationLabel, BorderLayout.NORTH);
                centerPanel.add(saveButton, BorderLayout.SOUTH);
                centerPanel.add(paramScrollPane, BorderLayout.CENTER);
                setContentPane(mainPanel);


            }
        });


        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(centerPanel, BorderLayout.CENTER);




        // Ajout du panneau principal à la fenêtre
        setContentPane(mainPanel);
    }



    // Methode to resize imageIcon with the same size of a Jlabel
    public ImageIcon ResizeImage(String ImagePath, JLabel label)
    {
        ImageIcon MyImage = new ImageIcon(ImagePath);
        Image img = MyImage.getImage();
        Image newImg = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImg);
        return image;
    }


    /*public static void main(String[] args) {
        ProfilWindow window = new ProfilWindow();
        window.setVisible(true);
    }*/
}

