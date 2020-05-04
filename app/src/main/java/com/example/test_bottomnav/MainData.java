package com.example.test_bottomnav;

public class MainData {
    String nama;
    String alamat;
    String no_hp;


    public MainData(String nama, String alamat, String no_hp) {
        this.nama = nama;
        this.alamat = alamat;
        this.no_hp = no_hp;
    }

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getNo_hp() {
        return no_hp;
    }
}
