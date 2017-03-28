package dev.vedroiders.huaweisupport;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {

    private Consumer consumer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initDeviceInfo();
    }

    private void initDeviceInfo() {
        ((EditText) findViewById(R.id.model)).setText(
                Build.MODEL + " " + Build.PRODUCT
        );
    }

    public void onRegistrationClick(View view) {
        if (!checkMail()) {
            notifyIncorrectMail();
            return;
        }
        if (!checkPass()) {
            notifyIncorrectPass();
            return;
        }
        if (!checkDoublePass()) {
            notifyIncorrectDoublePass();
            return;
        }

        consumer = new Consumer(
                ((EditText) findViewById(R.id.model)).getText().toString(),
                ((EditText) findViewById(R.id.email)).getText().toString(),
                ((EditText) findViewById(R.id.pass)).getText().toString(),
                ((EditText) findViewById(R.id.name)).getText().toString(),
                ((EditText) findViewById(R.id.phone)).getText().toString()
        );

        if (DataLoader.registerProfile(consumer)) {
            notifySuccessfulRegistration();
            DataLoader.saveProfile(consumer);
            setResult(RESULT_OK, new Intent().putExtra("consumer", consumer));
            finish();
        }
        else {
            notifyUnsuccessfulRegistration();
        }

    }


    private void notifyUnsuccessfulRegistration() {
        Toast.makeText(this, "При регистрации произошла ошибка!", Toast.LENGTH_SHORT).show();
    }

    private void notifyIncorrectDoublePass() {
        Toast.makeText(this, "Пароли не совпадают!", Toast.LENGTH_SHORT).show();
    }

    private void notifySuccessfulRegistration() {
        Toast.makeText(this, "Регистрация прошла успешно!", Toast.LENGTH_SHORT).show();
    }


    private void notifyIncorrectPass() {
        Toast.makeText(this, "Поле \"Пароль\" заполнено неверно!", Toast.LENGTH_SHORT).show();
    }

    private void notifyIncorrectMail() {
        Toast.makeText(this, "Поле \"Почта\" заполнено неверно!", Toast.LENGTH_SHORT).show();
    }

    private boolean checkMail() {
        String s = ((EditText) findViewById(R.id.email)).getText().toString();
        return s.length() >= 5
                && s.contains("@")
                && s.contains(".")
                && DataLoader.checkMail();
    }

    private boolean checkPass() {
        String s = ((EditText) findViewById(R.id.pass)).getText().toString();
        return s.length() >= 8;
    }

    private boolean checkDoublePass() {
        return ((EditText) findViewById(R.id.pass)).getText().toString().equals(((EditText)
                findViewById(R.id.pass2)).getText().toString());
    }

}
