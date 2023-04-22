package controller;

import model.Utilisateur;
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

    public LoginController(Client model) {
        super(model);
        model.addObserver(this);
    }

    public void setView(LoginPage view) {
        this.view = view;
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

        if (message.startsWith("The user has an account")) {
            Utilisateur.UserType type = Utilisateur.UserType.valueOf(message.substring(23)); // index du fin de la chaine "The user has an account"
            model.getUser().setUserType(type);
            setUserIsValid(true);
        } else if (message.equals("The user has no account")) {
            setUserIsValid(false);
        }
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
