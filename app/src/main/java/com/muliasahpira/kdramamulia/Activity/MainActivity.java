package com.muliasahpira.kdramamulia.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.muliasahpira.kdramamulia.API.APIRequestData;
import com.muliasahpira.kdramamulia.API.RetroServer;
import com.muliasahpira.kdramamulia.Adapter.AdapterDrakor;
import com.muliasahpira.kdramamulia.Model.ModelDrakor;
import com.muliasahpira.kdramamulia.Model.ModelResponse;
import com.muliasahpira.kdramamulia.R;
import com.muliasahpira.kdramamulia.Utility.KendaliLogin;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvDrakor;
    private FloatingActionButton fabTambah;
    private TextView tvWelcome;
    private ProgressBar pbDrakor;
    private RecyclerView.Adapter adDrakor;
    private RecyclerView.LayoutManager lmDrakor;
    private List<ModelDrakor> listDrakor = new ArrayList<>();
    KendaliLogin KL = new KendaliLogin(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (KL.isLogin(KL.keySP_username) == true) {
            setContentView(R.layout.activity_main);

            rvDrakor = findViewById(R.id.rv_drakor);
            fabTambah = findViewById(R.id.fab_tambah);
            pbDrakor = findViewById(R.id.pb_drakor);
            tvWelcome = findViewById(R.id.tv_welcome);

            lmDrakor = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            rvDrakor.setLayoutManager(lmDrakor);

//            tvWelcome.setText("Selamat Datang, " + KL.getPref(KL.keySP_nama_lengkap));
        tvWelcome.setText("Welcome to Kdrama!");

            fabTambah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, TambahActivity.class));
                }
            });
//        }
//        else {
//            startActivity(new Intent(MainActivity.this, LoginActivity.class));
//            finish();
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveDrakor();
    }

    public void retrieveDrakor(){
        pbDrakor.setVisibility(View.VISIBLE);

        APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = ARD.ardRetrieve();

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();
                listDrakor = response.body().getData();

                adDrakor = new AdapterDrakor(MainActivity.this, listDrakor);
                rvDrakor.setAdapter(adDrakor);
                adDrakor.notifyDataSetChanged();

                pbDrakor.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal Menghubungi Server", Toast.LENGTH_SHORT).show();
                pbDrakor.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
//            case R.id.logout:
//                logoutUser();
//                break;
            case R.id.about:
                aboutMe();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

//    private void logoutUser(){
//        KL.setPref(KL.keySP_username,null);
//        KL.setPref(KL.keySP_nama_lengkap, null);
//        KL.setPref(KL.keySP_email, null);
//        startActivity(new Intent(MainActivity.this, LoginActivity.class));
//        finish();
//    }

    private void aboutMe(){
        startActivity(new Intent(MainActivity.this, AboutActivity.class));
//        Intent about = new Intent(MainActivity.this, AboutActivity.class);
//        startActivity(about);
    }
}