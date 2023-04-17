package controller;

import view.LoginPage;
import model.Client;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;


public class LoginController extends BaseController implements Observer {

    // Attributs et méthodes spécifiques pour les pages d'authentification
    private LoginPage view;
    private BlockingQueue<Boolean> userValidityQueue = new LinkedBlockingQueue<>();
    private Boolean userIsValid;

    public LoginController(Client model) {
        super(model);
        model.addObserver(this);
    }

    public void setView(LoginPage view) {
        this.view = view;
    }

    //possible de le mettre dans classe mere
    public void sendMessage(String message) {
        model.sendMessage(message);
    }

    public void setUsername(String username) {
        model.getUser().setUsername(username);

    }

    public void setUserPassword(String password) {
        model.getUser().setPassword(password);
    }

    @Override
    public void update(Observable o, Object arg) {
        String message = (String) arg;

        if (message.equals("The user has an account")) {
            setUserIsValid(true);
        } else if (message.equals("The user has no account")) {
            setUserIsValid(false);
        }
    }

    public Boolean getUserIsValid() {
        return userIsValid;
    }
    public void setUserIsValid(Boolean bool) {
        userValidityQueue.offer(bool);
    }
    public Boolean getUserIsValidWithTimeout(long timeout, TimeUnit unit) {
        try {
            return userValidityQueue.poll(timeout, unit);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
