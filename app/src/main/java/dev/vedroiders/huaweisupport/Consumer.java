package dev.vedroiders.huaweisupport;

import java.io.Serializable;

/**
 * Created by mike on 28.03.17.
 */
class Consumer implements Serializable {

    private String model = "";
    private String email = "";
    private String pass = "";
    private String name = "";
    private String phone = "";

    public Consumer(String model, String email, String pass, String name, String phone) {
        this.model = model;
        this.email = email;
        this.pass = pass;
        this.name = name;
        this.phone = phone;
    }


    public String getModel() {
        return model;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}
