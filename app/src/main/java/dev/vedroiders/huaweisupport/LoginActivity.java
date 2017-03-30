package dev.vedroiders.huaweisupport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import dev.vedroiders.huaweisupport.kupihleba.Client;
import dev.vedroiders.huaweisupport.kupihleba.Interaction;

public class LoginActivity extends AppCompatActivity {

    private static final int REGISTRATION = 100;
    private Consumer consumer;
    private EditText loginEdit, passEdit;
    private boolean isLoggingIn = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        DataLoader.FILE_PATH = getApplicationContext().getFilesDir() + "/saves/";

        loginEdit = (EditText) findViewById(R.id.login);
        passEdit = (EditText) findViewById(R.id.pass);

    }

    private void startMainActivity() {
        startActivity(new Intent(this, Main2Activity.class)
                .putExtra("consumer", consumer)
        );
        finish();
    }


    @Override
    protected void onStart() {
        super.onStart();
        if ((consumer = DataLoader.loadProfile()) != null) {
            startMainActivity();
        }
    }

    public void onRegistrationClick(View view) {

        startActivityForResult(new Intent(this, RegistrationActivity.class), REGISTRATION);

    }

    public void onLoginClick(View view) {
        if (isLoggingIn) {
            Toast.makeText(this, "Выполняется вход...", Toast.LENGTH_SHORT).show();
            return;
        }
        isLoggingIn = true;

        checkPassAndMail();


        DataLoader.tryLogin(
                loginEdit.getText().toString(),
                passEdit.getText().toString(), new Client() {
                    @Override
                    public void gotResponse(boolean isOk, Interaction responseQuery) {
                        isLoggingIn = false;
                        if (isOk) {

                            Log.d("got", "gotResponse: " + responseQuery.login);
                            consumer = new Consumer(


                                    responseQuery.model,
                                    responseQuery.email,
                                    responseQuery.password,
                                    responseQuery.login,
                                    responseQuery.number
                            );

                            DataLoader.saveProfile(consumer);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    startMainActivity();
                                }
                            });

                        }
                        else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    setIncorrect();

                                }
                            });
                        }
                    }
                });


    }

    private boolean checkPassAndMail() {
        return passEdit.length() > 0
                && loginEdit.length() > 0;
    }


    private void setIncorrect() {
        Toast.makeText(this, "Неверная пара логин/пароль!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REGISTRATION && resultCode == RESULT_OK) {

            consumer = (Consumer) data.getSerializableExtra("consumer");

            if (consumer != null) {
                startMainActivity();
            }

        }
    }
}
