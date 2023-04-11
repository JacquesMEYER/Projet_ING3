package affichage;

import controller.LoginController;
import controller.MessageController;
import controller.SignUpController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class LoginPage extends JFrame implements ActionListener, Observer {

    private LoginController loginController;
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
        registerButton = new JButton("Sign UP");
        registerButton.setBackground(Color.black);
        loginButton.setBackground(Color.black);

        // Ajouter des écouteurs d'événements pour le bouton de connexion
        loginButton.addActionListener(this);
        registerButton.addActionListener(this);
        // Créer un nouveau JPanel pour contenir les champs de texte et le bouton
        JPanel loginPanel = new JPanel(new GridLayout(3, 1));
        loginPanel.add(new JLabel("User name :"));
        loginPanel.add(usernameField);
        loginPanel.add(new JLabel("Password :"));
        loginPanel.add(passwordField);
        loginPanel.add(registerButton);
        loginPanel.add(loginButton);


        // Définir la couleur d'arrière-plan de la fenêtre et du JPanel
        loginPanel.setBackground(Color.white);
        getContentPane().add(loginPanel, BorderLayout.CENTER);
        getContentPane().setBackground(Color.blue);

        // Définir les propriétés de la fenêtre JFrame
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {

        // Vérifier si le bouton de connexion a été cliqué
        if (e.getSource() == loginButton) {
            // Récupérer le nom d'utilisateur et le mot de passe entrés
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Vérifier si les informations de connexion sont correctes
            if (username.equals(loginController.getModel().getUser().getUsername()) && password.equals(loginController.getModel().getUser().getPassword())) {
                JOptionPane.showMessageDialog(this, "Connexion sucess !");

                MessageController messageController = new MessageController(loginController.getModel());
                pageAcceuil view2 = new pageAcceuil(messageController);
                messageController.setView(view2);
                dispose();
                view2.setVisible(true);

            }
            else {
                JOptionPane.showMessageDialog(this, "Incorrect user or password.");
            }

        }
        MessageController signup;
        if (e.getSource() == registerButton){

            SignUpController SignUpController = new SignUpController(loginController.getModel());
            SignUP view3 = new SignUP(SignUpController);
            SignUpController.setView(view3);
            dispose();
            view3.setVisible(true);
        }

    }

    @Override
    public void update(Observable o, Object arg) {

    }

   /* public static void main(String[] args) {
        Affichage.LoginPage loginPage = new Affichage.LoginPage();
    }*/
}
