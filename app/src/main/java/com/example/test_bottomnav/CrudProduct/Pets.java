package com.example.test_bottomnav.CrudProduct;

import com.google.gson.annotations.SerializedName;

public class Pets {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("warehouse_asal")
    private String warehouse_asal;
    @SerializedName("quantity")
    private String quantity;
    @SerializedName("type")
    private int type;
    @SerializedName("expired")
    private String expired;
    @SerializedName("picture")
    private String picture;
    @SerializedName("love")
    private Boolean love;
    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String massage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWarehouse_asal() {
        return warehouse_asal;
    }

    public void setWarehouse_asal(String species) {
        this.warehouse_asal = species;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String breed) {
        this.quantity = breed;
    }

    public int getType() {
        return type;
    }

    public void setType(int gender) {
        this.type = gender;
    }

    public String getExpired() {
        return expired;
    }

    public void setExpired(String birth) {
        this.expired = birth;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Boolean getLove() {
        return love;
    }

    public void setLove(Boolean love) {
        this.love = love;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }
}
