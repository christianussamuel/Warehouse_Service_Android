package com.example.test_bottomnav.Model;

public class ListWarehouse_model {
    private String namaWarehouse;
    private String alamatWarehouse;
    private String latitude;
    private String longitude;

    public ListWarehouse_model(String namaWarehouse, String alamatWarehouse) {
        this.namaWarehouse = namaWarehouse;
        this.alamatWarehouse = alamatWarehouse;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getNamaWarehouse() {
        return namaWarehouse;
    }

    public void setNamaWarehouse(String namaWarehouse) {
        this.namaWarehouse = namaWarehouse;
    }

    public String getAlamatWarehouse() {
        return alamatWarehouse;
    }

    public void setAlamatWarehouse(String alamatWarehouse) {
        this.alamatWarehouse = alamatWarehouse;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
