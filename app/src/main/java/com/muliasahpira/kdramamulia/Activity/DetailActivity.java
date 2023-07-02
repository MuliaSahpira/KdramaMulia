package com.muliasahpira.kdramamulia.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.muliasahpira.kdramamulia.API.APIRequestData;
import com.muliasahpira.kdramamulia.API.RetroServer;
import com.muliasahpira.kdramamulia.Model.ModelDrakor;
import com.muliasahpira.kdramamulia.Model.ModelResponse;
import com.muliasahpira.kdramamulia.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    private TextView tvJudul, tvHangul, tvSinopsis, tvRilis, tvGenre, tvRating, tvEpisode, tvPemeran, tvubahGambar;
    ImageView ivGambar;
    private List<ModelDrakor> listDrakor = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String id = intent.getStringExtra("varId");

        Log.d("iddetail", id);
        ivGambar = findViewById(R.id.iv_gambar);
        tvJudul = findViewById(R.id.tv_judul);
        tvHangul = findViewById(R.id.tv_hangul);
        tvSinopsis = findViewById(R.id.tv_sinopsis);
        tvRilis = findViewById(R.id.tv_rilis);
        tvGenre = findViewById(R.id.tv_genre);
        tvRating = findViewById(R.id.tv_rating);
        tvEpisode = findViewById(R.id.tv_episode);
        tvPemeran = findViewById(R.id.tv_pemeran);

        APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = ARD.ardShow(id);

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();
                listDrakor = response.body().getData();

                ModelResponse res = response.body();
                ModelDrakor model = res.getData().get(0);

                tvJudul.setText(model.getJudul());
                tvHangul.setText(model.getHangul());
                tvSinopsis.setText(model.getSinopsis());
                tvRilis.setText(model.getRilis());
                tvGenre.setText(model.getGenre());
                tvRating.setText(model.getRating());
                tvEpisode.setText(model.getEpisode());
                tvPemeran.setText(model.getPemeran());
                Glide.with(DetailActivity.this)
                        .load(model.getGambar())
                        .apply(new RequestOptions().override(350, 550))
                        .into(ivGambar);
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(DetailActivity.this, "Gagal Menghubungi Server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}