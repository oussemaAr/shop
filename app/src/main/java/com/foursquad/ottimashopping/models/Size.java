package com.foursquad.ottimashopping.models;

/**
 * Created by oussemaar on 8/7/17.
 * <In Code I Trust />
 */

public class Size {
    private String size;
    private boolean isDisponible;

    public Size() {
    }

    public Size(String size, boolean isDisponible) {
        this.size = size;
        this.isDisponible = isDisponible;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public boolean getIsDisponible() {
        return isDisponible;
    }

    public void setIsDisponible(boolean isDisponible) {
        this.isDisponible = isDisponible;
    }
}
