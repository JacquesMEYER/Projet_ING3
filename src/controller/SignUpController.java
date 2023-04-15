package controller;

import affichage.SignUP;
import model.Client;

import java.util.Observable;

public class SignUpController extends BaseController {
    // Attributs et méthodes spécifiques pour les pages d'authentification
    private SignUP view;
    public SignUpController(Client model) {
        super(model);
    }

    public void setView(SignUP view) {
        this.view = view;
    }
    public void sendMessage(String message) {
        model.sendMessage(message);
    }


    @Override
    public void update(Observable o, Object arg) {

    }
}
