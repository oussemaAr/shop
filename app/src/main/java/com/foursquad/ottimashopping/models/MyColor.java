package com.foursquad.ottimashopping.models;

import java.io.Serializable;

/**
 * Created by oussemaar on 8/8/17.
 * <In Code I Trust />
 */

public class MyColor implements Serializable {
    public int color;
    public boolean isSelected;

    public MyColor(int color, boolean isSelected) {
        this.color = color;
        this.isSelected = isSelected;
    }

    public MyColor() {
    }
}
