package com.example.test_bottomnav.Model;

public class History_model {
    private String namaPengirim;
    private String namaProduct;
    private String jumlah;
    private String tanggal;

    public History_model(String namaPengirim, String namaProduct, String jumlah, String tanggal) {
        this.namaPengirim = namaPengirim;
        this.namaProduct = namaProduct;
        this.jumlah = jumlah;
        this.tanggal = tanggal;

    }

    public String getNamaPengirim() {
        return namaPengirim;
    }

    public void setNamaPengirim(String namaPengirim) {
        this.namaPengirim = namaPengirim;
    }

    public String getNamaProduct() {
        return namaProduct;
    }

    public void setNamaProduct(String namaProduct) {
        this.namaProduct = namaProduct;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}