package model;

public class Utilisateur {
    private String name;
    private String prenom;
    private String username;
    private String password;
    public enum Status {ONLINE,OFFLINE,AWAY,DEMARRED}
    public enum userType {CLASSIC,MODERATOR,ADMINISTRATOR}
    private Status status;
    private userType userType;

    Utilisateur(String pseudo,String mdp){
        this.username=pseudo;
        this.password=mdp;
    }
    Utilisateur(String pseudo){
        this.username=pseudo;

    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void changementStatut(Status status){
        this.status=status;
    }

    public Status getStatus() {
        return status;
    }

    public Utilisateur.userType getUserType() {
        return userType;
    }
}
