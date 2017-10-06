package com.foursquad.ottimashopping.models;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by oussemaar on 8/7/17.
 * <In Code I Trust />
 */

public class Article implements Serializable {

    @Exclude
    private String id;
    private String title;
    private String type;
    private int quantity;
    private float sellPrice;
    private float buyPrice;
    private String marque;
    private ArrayList<String> sizes;
    private ArrayList<MyColor> colors;
    private String ImageUri;
    private float marqueUp;
    private float finalPrice;

    public Article() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(float sellPrice) {
        this.sellPrice = sellPrice;
    }

    public float getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(float buyPrice) {
        this.buyPrice = buyPrice;
    }

    public ArrayList<String> getSizes() {
        return sizes;
    }

    public void setSizes(ArrayList<String> sizes) {
        this.sizes = sizes;
    }

    public ArrayList<MyColor> getColors() {
        return colors;
    }

    public void setColors(ArrayList<MyColor> colors) {
        this.colors = colors;
    }

    public String getImageUri() {
        return ImageUri;
    }

    public void setImageUri(String imageUri) {
        ImageUri = imageUri;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getMarqueUp() {
        return marqueUp;
    }

    public void setMarqueUp(float marqueUp) {
        this.marqueUp = marqueUp;
    }

    public float getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(float finalPrice) {
        this.finalPrice = finalPrice;
    }

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", quantity=" + quantity +
                ", sellPrice=" + sellPrice +
                ", buyPrice=" + buyPrice +
                ", marque='" + marque + '\'' +
                ", sizes=" + sizes +
                ", colors=" + colors +
                ", ImageUri='" + ImageUri + '\'' +
                '}';
    }
}
