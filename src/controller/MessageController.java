package controller;

import view.pageAcceuil;
import model.Client;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Observable;
import java.util.Set;

public class MessageController extends BaseController {

    private pageAcceuil view;
    private Set<String> nomsCo = new HashSet<>(Arrays.asList(model.getUser().getUsername()));
    private Set<String> vrmtCo = new HashSet<>();


    public MessageController(Client model) {
        super(model);
        model.addObserver(this);
    }

    public void setView(pageAcceuil view) {
        this.view = view;
    }

    public void sendMessage(String message) {
        model.sendMessage(message);
    }


    public Set<String> getNomsCo() {
        return nomsCo;
    }

    public Set<String> getVrmtCo() {
        return vrmtCo;
    }

    @Override
    public void update(Observable o, Object arg) {
        String message = (String) arg;

        if (message.startsWith("co:")) {
            String[] noms = message.substring("co:".length()).split(" ");
            nomsCo.addAll(Arrays.asList(noms));
            vrmtCo = new HashSet<>(Arrays.asList(noms));
            view.setNomsCo(nomsCo);
            view.updateUserButtons(nomsCo, view.getUserButtonsPanel(), vrmtCo); // Appelez la méthode updateUserButtons ici
        } else if (message.equals("The user has an account")) {
        } else if (message.equals("The user has no account")) {
        } else view.addMessage(message);
    }
}
