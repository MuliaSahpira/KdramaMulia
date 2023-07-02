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
import com.muliasahpira.kdramamulia.Model.ModelResponse;
import com.muliasahpira.kdramamulia.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahActivity extends AppCompatActivity {

    private String yId, yUbahGambar, yGambar, yJudul, yHangul, ySinopsis, yRilis, yGenre, yRating, yEpisode, yPemeran;
    private EditText etJudul, etHangul, etSinopsis, etRilis, etGenre, etRating, etEpisode,  etPemeran, etGambar;
    private Button btnUbah;
    private String gambar, judul, hangul, sinopsis, rilis, genre, rating, episode, pemeran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah);

        Intent ambil = getIntent();
        yId = ambil.getStringExtra("xId");
        yGambar = ambil.getStringExtra("xGambar");
        yUbahGambar = ambil.getStringExtra("xUbahGambar");
        yJudul = ambil.getStringExtra("xJudul");
        yHangul = ambil.getStringExtra("xHangul");
        ySinopsis = ambil.getStringExtra("xSinopsis");
        yRilis = ambil.getStringExtra("xRilis");
        yGenre = ambil.getStringExtra("xGenre");
        yRating = ambil.getStringExtra("xRating");
        yEpisode = ambil.getStringExtra("xEpisode");
        yPemeran = ambil.getStringExtra("xPemeran");

        etGambar = findViewById(R.id.et_gambar);
        etJudul = findViewById(R.id.et_judul);
        etHangul = findViewById(R.id.et_hangul);
        etSinopsis = findViewById(R.id.et_sinopsis);
        etRilis = findViewById(R.id.et_rilis);
        etGenre = findViewById(R.id.et_genre);
        etRating = findViewById(R.id.et_rating);
        etEpisode = findViewById(R.id.et_episode);
        etPemeran = findViewById(R.id.et_pemeran);
        btnUbah = findViewById(R.id.btn_ubah);

        etGambar.setText(yUbahGambar);
        etJudul.setText(yJudul);
        etHangul.setText(yHangul);
        etSinopsis.setText(ySinopsis);
        etRilis.setText(yRilis);
        etGenre.setText(yGenre);
        etRating.setText(yRating);
        etEpisode.setText(yEpisode);
        etPemeran.setText(yPemeran);

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gambar = etGambar.getText().toString();
                judul = etJudul.getText().toString();
                hangul = etHangul.getText().toString();
                sinopsis = etSinopsis.getText().toString();
                rilis = etRilis.getText().toString();
                genre = etGenre.getText().toString();
                rating = etRating.getText().toString();
                episode = etEpisode.getText().toString();
                pemeran = etPemeran.getText().toString();

                if (gambar.trim().isEmpty()){
                    etGambar.setError("Link gambar tidak boleh kosong");
                }
                else if(judul.trim().isEmpty()) {
                    etJudul.setError("Judul tidak boleh kosong");
                } else if (hangul.trim().isEmpty()) {
                    etHangul.setError("Hangul tidak boleh kosong");
                } else if (sinopsis.trim().isEmpty()) {
                    etSinopsis.setError("Sinopsis tidak boleh kosong");
                } else if (rilis.trim().isEmpty()) {
                    etRilis.setError("Tahun Rilis tidak boleh kosong");
                }  else if (genre.trim().isEmpty()) {
                    etGenre.setError("Genre tidak boleh kosong");
                } else if (rating.trim().isEmpty()) {
                    etRating.setError("Rating tidak boleh kosong");
                }  else if (episode.trim().isEmpty()) {
                    etEpisode.setError("Episode tidak boleh kosong");
                } else if (pemeran.trim().isEmpty()) {
                    etPemeran.setError("Pemeran tidak boleh kosong");
                } else {
                    ubahDrakor();
                }
            }
        });
    }

    private void ubahDrakor(){
        APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = ARD.ardUpdate(yId, gambar,judul, hangul, sinopsis, rilis, genre, rating, episode, pemeran);

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(UbahActivity.this, "Kode: " + kode + "Pesan: " + pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(UbahActivity.this, "Gagal Menghubungi Server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}