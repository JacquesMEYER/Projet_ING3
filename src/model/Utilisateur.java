package model;

public class Utilisateur {
    private String username;
    private String password;
    public enum Status {ONLINE,OFFLINE,AWAY,DEMARRED}
    public enum UserType {CLASSIC,MODERATOR,ADMINISTRATOR}
    private Status status;
    private UserType userType;

    Utilisateur(String username,String mdp){
        this.username=username;
        this.password=mdp; //test
        this.userType= Utilisateur.UserType.ADMINISTRATOR;

    }
    Utilisateur(String pseudo){
        this.username=pseudo;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
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

    public Utilisateur.UserType getUserType() {
        return userType;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
