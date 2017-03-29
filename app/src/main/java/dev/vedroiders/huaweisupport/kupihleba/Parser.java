package dev.vedroiders.huaweisupport.kupihleba;

/**
 * Some lol
 */

import android.annotation.TargetApi;
import android.util.Base64;

import java.nio.charset.StandardCharsets;

@TargetApi(19)
public class Parser {
    private String check(String string) {
        if (string == null)
            return "-";
        return string;
    }

    public String outputParse(Interaction interaction) {
        StringBuilder str = new StringBuilder();

        str.append(check(interaction.type.toString())).append("$")
                .append(check(interaction.login)).append("$")
                .append(check(interaction.email)).append("$")
                .append(check(interaction.password)).append("$")
                .append(check(interaction.number)).append("$")
                .append(check(interaction.model)).append("$")
                .append(check(interaction.message)).append("$");


        return new String(Base64.encode(str.toString().getBytes(), Base64.DEFAULT), StandardCharsets.UTF_8);
    }

    public Interaction inputParse(String data) {
        Interaction interaction = new Interaction();
        String message[] = new String(Base64.decode(data, Base64.DEFAULT), StandardCharsets.UTF_8).split("\\$");

        interaction.type = Interaction.fromString(message[0]);
        interaction.login = message[1];
        interaction.email = message[2];
        interaction.password = message[3];
        interaction.number = message[4];
        interaction.model = message[5];
        interaction.message = message[6];

        return interaction;
    }
}
