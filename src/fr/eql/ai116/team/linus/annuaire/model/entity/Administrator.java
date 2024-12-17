package fr.eql.ai116.team.linus.annuaire.model.entity;

import java.util.Objects;

public class Administrator {
    private String username;
    private String password;
    private String statut ;

    public Administrator() {
    }

    public Administrator(String username, String password,String statut) {
        this.username = username;
        this.password = password;
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "Administrator{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", statut='" + statut + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

}
