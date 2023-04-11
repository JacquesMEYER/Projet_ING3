package controller;
import affichage.LoginPage;
import affichage.pageAcceuil;
import model.Client;

import java.util.Observable;

public class LoginController extends BaseController {
    // Attributs et méthodes spécifiques pour les pages d'authentification
    private LoginPage view;
    public LoginController(Client model) {
        super(model);
    }

    public void setView(LoginPage view) {
        this.view = view;
    }

    @Override
    public void update(Observable o, Object arg) {

   }
}
