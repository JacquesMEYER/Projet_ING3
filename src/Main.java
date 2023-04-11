

import affichage.LoginPage;
import affichage.pageAcceuil;
import controller.LoginController;
import controller.MessageController;
import model.Client;

//Main.java
public class Main {
    public static void main(String[] args) {
        Client model = new Client();

        LoginController loginController = new LoginController(model);
        LoginPage view = new LoginPage(loginController);
        loginController.setView(view);
/*
        MessageController messageController = new MessageController(model);
        pageAcceuil view2 = new pageAcceuil(messageController);
        messageController.setView(view2);
*/
    }
}