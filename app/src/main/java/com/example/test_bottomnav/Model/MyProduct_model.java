package com.example.test_bottomnav.Model;

public class MyProduct_model {
    private String namaProduct;
    private String type;
    private String jumlah;
    private String deskripsi;

    public MyProduct_model(String namaProduct, String type, String jumlah, String deskripsi) {
        this.namaProduct = namaProduct;
        this.type = type;
        this.jumlah = jumlah;
        this.deskripsi = deskripsi;

    }

    public String getNamaProduct() {
        return namaProduct;
    }

    public void setNamaProduct(String namaProduct) {
        this.namaProduct = namaProduct;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}