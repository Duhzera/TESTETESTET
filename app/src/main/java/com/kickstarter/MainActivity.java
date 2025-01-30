package com.exemplo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    private EditText passwordInput;
    private Button unlockButton;
    private static final String CORRECT_PASSWORD = "1234"; // Senha correta

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        passwordInput = findViewById(R.id.passwordInput);
        unlockButton = findViewById(R.id.unlockButton);

        unlockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userPassword = passwordInput.getText().toString();
                if (userPassword.equals(CORRECT_PASSWORD)) {
                    // Senha correta: libera o acesso
                    Toast.makeText(MainActivity.this, "Senha correta! Acesso liberado.", Toast.LENGTH_SHORT).show();
                    finish(); // Fecha a tela de bloqueio
                } else {
                    // Senha incorreta: exibe mensagem de erro
                    Toast.makeText(MainActivity.this, "Senha incorreta! Tente novamente.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Impede o usuário de voltar sem inserir a senha
        Toast.makeText(this, "Insira a senha para desbloquear.", Toast.LENGTH_SHORT).show();
    }
}
