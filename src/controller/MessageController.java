package controller;

import model.Client;
import affichage.pageAcceuil;

import java.util.Observable;

public class MessageController extends BaseController {

    private pageAcceuil view;

    public  MessageController(Client model) {
        super(model);

    }
    public void setView(pageAcceuil view) {
        this.view = view;
    }
    public void sendMessage(String message) {
        model.sendMessage(message);
    }

    @Override
    public void update(Observable o, Object arg) {
        String message = (String) arg;
        view.addMessage(message);
    }
}
