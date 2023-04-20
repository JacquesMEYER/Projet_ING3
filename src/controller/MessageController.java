package controller;

import model.Client;
import view.pageAcceuil;

import java.util.*;

import static model.Utilisateur.UserType.CLASSIC;
import static model.Utilisateur.UserType.ADMINISTRATOR;
import static model.Utilisateur.UserType.MODERATOR;


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


    public void changeType(String type) {
        if(Objects.equals(type, "classic")){
            model.getUser().setUserType(CLASSIC);
        }
        if(Objects.equals(type, "admin")){
            model.getUser().setUserType(ADMINISTRATOR);
        }
        if(Objects.equals(type, "moderator")){
            model.getUser().setUserType(MODERATOR);
        }
        view.repaint();
        view.updateUserTypeLabel(); // Ajoutez cette ligne pour mettre à jour le label
        System.out.println(model.getUser().getUserType());

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
        System.out.println(message);

        if (message.startsWith("co:")) {
            String[] noms = message.substring("co:".length()).split(" ");
            nomsCo.addAll(Arrays.asList(noms));
            vrmtCo = new HashSet<>(Arrays.asList(noms));
            view.setNomsCo(nomsCo);
            view.updateUserButtons(nomsCo, view.getUserButtonsPanel(), vrmtCo); // Appelez la méthode updateUserButtons ici
        } else if (message.equals("The user has an account")) {
        } else if (message.equals("The user has no account")) {
        } else if (message.startsWith("/GIF:")) {
            String[] parts = message.split("\\s+"); // Divise la chaîne en fonction des espaces
            String url = parts[1];
            String userSender = parts[2];
            if (userSender.equals(getModel().getUser().getUsername())){
                pageAcceuil.addGifRight(url);
            }else  {
                view.sendMessageLeft(userSender);
                pageAcceuil.addGifLeft(url);
            }


        } else if (message.startsWith("/changeType:")) {
            String[] parts = message.split(" ", 2);
            String type = parts[0].substring("/changeType:".length());



            //System.out.println(type);

            changeType(type);
        }
        else {
            if (message.startsWith(model.getUser().getUsername())) {
                view.sendMessageRight(message);
            } else if (message.startsWith("*")) {
                view.sendMessageCenter(message);
            } else {
                view.sendMessageLeft(message);
            }
        }
    }
}
