package view;

import controller.LoginController;
import controller.MessageController;
import controller.SignUpController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

public class LoginPage extends JFrame implements ActionListener{

    private LoginController loginController;

    Color bleuclair = new Color(234, 242, 248);
    Color bleufonce1 = new Color(36, 113, 163);
    Color bleufonce2 = new Color(31, 97, 141);
    Color bleufonce3 = new Color(23, 32, 42);
    // Variables pour les champs de texte et le bouton
    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginButton, registerButton;

    public LoginPage(LoginController loginController) {

        // Créer une nouvelle fenêtre JFrame avec un titre
        super("LOGIN");
        this.loginController = loginController;

        // Initialiser les champs de texte et le bouton
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        loginButton.setBackground(bleufonce2);
        loginButton.setForeground(bleuclair);

        registerButton = new JButton("SingUP");
        registerButton.setBackground(bleufonce2);
        registerButton.setForeground(bleuclair);
        // Ajouter des écouteurs d'événements pour le bouton d'inscription
        registerButton.addActionListener(this);
        loginButton.addActionListener(this);
        // Créer un nouveau JPanel pour contenir les champs de texte et le bouton
        JPanel registerPanel = new JPanel(new GridLayout(3, 1));

        JLabel cusername = new JLabel("Username :");
        cusername.setForeground(bleufonce3);
        registerPanel.add(cusername);
        registerPanel.add(usernameField);

        JLabel cpsw = new JLabel("Password :");
        cpsw.setForeground(bleufonce3);
        registerPanel.add(cpsw);
        registerPanel.add(passwordField);

        registerPanel.add(registerButton);
        registerPanel.add(loginButton);

        // Définir la couleur d'arrière-plan de la fenêtre et du JPanel
        registerPanel.setBackground(bleuclair);
        getContentPane().add(registerPanel, BorderLayout.CENTER);
        getContentPane().setBackground(Color.red);

        // Définir les propriétés de la fenêtre JFrame
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {


        if (e.getSource() == loginButton) {

            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            loginController.sendMessage("/testConnexion: " + username + " " + password);

            //  boolean userIsValid = loginController.getUserIsValid();
            Boolean userIsValid = loginController.getUserIsValidWithTimeout(2, TimeUnit.SECONDS);
            if (userIsValid == null) {
                System.out.println("marche pas");

            } else {
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Veuillez saisir un nom d'utilisateur et un mot de passe.");
                } else if (userIsValid) {
                    loginController.setUsername(username);
                    loginController.setUserPassword(password);
                    JOptionPane.showMessageDialog(this, "Connexion sucess !");
                    MessageController messageController = new MessageController(loginController.getModel());
                    pageAcceuil view2 = new pageAcceuil(messageController);
                    messageController.setView(view2);
                    dispose();
                    view2.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Incorrect user or password.");
                }
            }
        }
        MessageController signup;
        if (e.getSource() == registerButton) {

            SignUpController SignUpController = new SignUpController(loginController.getModel());
            SignUP view3 = new SignUP(SignUpController);
            SignUpController.setView(view3);
            dispose();
            view3.setVisible(true);
        }

    }

}
