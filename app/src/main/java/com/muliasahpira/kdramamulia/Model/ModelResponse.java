package com.muliasahpira.kdramamulia.Model;

import java.util.List;

public class ModelResponse {
    private String kode, pesan;
    private List<ModelDrakor> data;
    private List<ModelPengguna> dataPengguna;

    public String getKode() {
        return kode;
    }

    public String getPesan() {
        return pesan;
    }

    public List<ModelDrakor> getData() {
        return data;
    }

    public List<ModelPengguna> getDataPengguna() {
        return dataPengguna;
    }
}
