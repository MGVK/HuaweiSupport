package dev.vedroiders.huaweisupport.kupihleba;

import java.io.Serializable;


/**
 * The representation of conversation between client and the server.
 * Class is to be serialized and sent to server
 * @author kupihleba, Vladimir Buh
 */
public class Interaction implements Serializable
{
    enum Type
    {
        REGISTER,
        LOGIN,
        DATA
    }

    public Type type;
    public String message;
    public String login;
    public String password;
    public String email;
    public String model;
    public String number;

    @Override
    public Interaction clone(){
        return new Interaction(this);
    }

    public Interaction(Type type, String message, String login, String password, String email, String model, String number) {
        this.type     = type;
        this.message  = message;
        this.login    = login;
        this.password = password;
        this.email    = email;
        this.model    = model;
        this.number   = number;
    }

    public Interaction(Interaction interaction) {
        this.type     = interaction.type;
        this.message  = interaction.message;
        this.login    = interaction.login;
        this.password = interaction.password;
        this.email    = interaction.email;
        this.model    = interaction.model;
        this.number   = interaction.number;
    }

    public Interaction() {
    }

    @Override
    public String toString() {
        return "Interaction{" +
                "type=" + type +
                ", message='" + message + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", model='" + model + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}