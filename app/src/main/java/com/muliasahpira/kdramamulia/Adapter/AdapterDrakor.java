package com.muliasahpira.kdramamulia.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.muliasahpira.kdramamulia.API.APIRequestData;
import com.muliasahpira.kdramamulia.API.RetroServer;
import com.muliasahpira.kdramamulia.Activity.DetailActivity;
import com.muliasahpira.kdramamulia.Activity.MainActivity;
import com.muliasahpira.kdramamulia.Activity.UbahActivity;
import com.muliasahpira.kdramamulia.Model.ModelDrakor;
import com.muliasahpira.kdramamulia.Model.ModelResponse;
import com.muliasahpira.kdramamulia.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterDrakor extends RecyclerView.Adapter<AdapterDrakor.VHDrakor> {
    private Context ctx;
    private List<ModelDrakor> listDrakor;

    public AdapterDrakor(Context ctx, List<ModelDrakor> listDrakor){
        this.ctx = ctx;
        this.listDrakor = listDrakor;
    }

    @NonNull
    @Override
    public VHDrakor onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View varView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_drakor, parent, false);
        return new VHDrakor(varView);
    }

    @Override
    public void onBindViewHolder(@NonNull VHDrakor holder, int position) {
        ModelDrakor MD = listDrakor.get(position);
        holder.tvId.setText(MD.getId());
        holder.tvJudul.setText(MD.getJudul());
        holder.tvHangul.setText(MD.getHangul());
        holder.tvSinopsis.setText(MD.getSinopsis());
        holder.tvRilis.setText(MD.getRilis());
        holder.tvGenre.setText(MD.getGenre());
        holder.tvRating.setText(MD.getRating());
        holder.tvEpisode.setText(MD.getEpisode());
        holder.tvPemeran.setText(MD.getPemeran());
        holder.tvubahGambar.setText(MD.getGambar());
        Glide.with(holder.itemView.getContext())
                        .load(MD.getGambar())
                        .apply(new RequestOptions().override(350, 550))
                        .into(holder.ivGambar);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = MD.getId();

                Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
                intent.putExtra("varId", id);

                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listDrakor.size();
    }

    public class VHDrakor extends RecyclerView.ViewHolder {
        TextView tvId, tvJudul, tvHangul, tvSinopsis, tvRilis, tvGenre, tvRating, tvEpisode, tvPemeran, tvubahGambar;
        ImageView ivGambar;
        public VHDrakor(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tv_id);
            tvJudul = itemView.findViewById(R.id.tv_judul);
            tvHangul = itemView.findViewById(R.id.tv_hangul);
            tvSinopsis = itemView.findViewById(R.id.tv_sinopsis);
            tvRilis = itemView.findViewById(R.id.tv_rilis);
            tvGenre = itemView.findViewById(R.id.tv_genre);
            tvRating = itemView.findViewById(R.id.tv_rating);
            tvEpisode = itemView.findViewById(R.id.tv_episode);
            tvPemeran = itemView.findViewById(R.id.tv_pemeran);
            ivGambar = itemView.findViewById(R.id.iv_gambar);
            tvubahGambar = itemView.findViewById(R.id.tv_ubahGambar);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder pesan = new AlertDialog.Builder(ctx);
                    pesan.setTitle("Perhatian");
                    pesan.setMessage("Anda Memilih Kdrama " +"'"+ tvJudul.getText().toString() +"'"+ ". Operasi apa yang akan dilakukan?");
                    pesan.setCancelable(true);

                    pesan.setNegativeButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            hapusDrakor(tvId.getText().toString());
                            dialogInterface.dismiss();
                        }
                    });

                    pesan.setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent pindah = new Intent(ctx, UbahActivity.class);
                            pindah.putExtra("xId", tvId.getText().toString());
                            pindah.putExtra("xGambar", (String) ivGambar.getTag());
                            pindah.putExtra("xUbahGambar", tvubahGambar.getText().toString());
                            pindah.putExtra("xJudul", tvJudul.getText().toString());
                            pindah.putExtra("xHangul", tvHangul.getText().toString());
                            pindah.putExtra("xSinopsis", tvSinopsis.getText().toString());
                            pindah.putExtra("xRilis", tvRilis.getText().toString());
                            pindah.putExtra("xGenre", tvGenre.getText().toString());
                            pindah.putExtra("xRating", tvRating.getText().toString());
                            pindah.putExtra("xEpisode", tvEpisode.getText().toString());
                            pindah.putExtra("xPemeran", tvPemeran.getText().toString());
                            ctx.startActivity(pindah);
                        }
                    });

                    pesan.show();
                    return false;
                }
            });
        }

        private void hapusDrakor(String idDrakor){
            APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ModelResponse> proses = ARD.ardDelete(idDrakor);

            proses.enqueue(new Callback<ModelResponse>() {
                @Override
                public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                    String kode = response.body().getKode();
                    String pesan = response.body().getPesan();

                    Toast.makeText(ctx, "Kode: " + kode + ", Pesan: " + pesan, Toast.LENGTH_SHORT).show();
                    ((MainActivity) ctx).retrieveDrakor();
                }

                @Override
                public void onFailure(Call<ModelResponse> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal Mengubungi Server!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
