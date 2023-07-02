package com.muliasahpira.kdramamulia.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.muliasahpira.kdramamulia.API.APIRequestData;
import com.muliasahpira.kdramamulia.API.RetroServer;
import com.muliasahpira.kdramamulia.Model.ModelPengguna;
import com.muliasahpira.kdramamulia.Model.ModelResponse;
import com.muliasahpira.kdramamulia.R;
import com.muliasahpira.kdramamulia.Utility.KendaliLogin;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private String username, password;
    private List<ModelPengguna> listPengguna = new ArrayList<>();
    KendaliLogin KL = new KendaliLogin(LoginActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = etUsername.getText().toString();
                password = etPassword.getText().toString();

                if (username.trim().isEmpty()){
                    etUsername.setError("Username Tidak Boleh Kosong!");
                } else if (password.trim().isEmpty()) {
                    etPassword.setError("Password Tidak Boleh Kosong!");
                }
                else {
                    loginDrakor();
                }
            }
        });
    }

    private void loginDrakor(){
        APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = ARD.ardLogin(username, password);

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();
                listPengguna = response.body().getDataPengguna();

                if (kode.equals("0")){
                    Toast.makeText(LoginActivity.this, "Error! Username atau Password Salah!", Toast.LENGTH_SHORT).show();
                }
                else {
                    KL.setPref(KL.keySP_username,listPengguna.get(0).getUsername());
                    KL.setPref(KL.keySP_nama_lengkap, listPengguna.get(0).getNama());
                    KL.setPref(KL.keySP_email, listPengguna.get(0).getEmail());

                    Toast.makeText(LoginActivity.this, "Login Sukses", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error! Gagal terhubung ke Server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}