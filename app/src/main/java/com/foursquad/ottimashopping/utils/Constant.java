package com.foursquad.ottimashopping.utils;

import com.foursquad.ottimashopping.R;
import com.foursquad.ottimashopping.models.MyColor;

import java.util.ArrayList;

/**
 * Created by oussemaar on 8/11/17.
 * <In Code I Trust />
 */

public class Constant {
    public static ArrayList<MyColor> colorArray = new ArrayList<>();

    public static void fillData() {
        colorArray.clear();
        colorArray.add(new MyColor(R.color.cyan, false));
        colorArray.add(new MyColor(R.color.teal, false));
        colorArray.add(new MyColor(R.color.green, false));
        colorArray.add(new MyColor(R.color.light_blue, false));
        colorArray.add(new MyColor(R.color.red, false));
        colorArray.add(new MyColor(R.color.blue, false));
        colorArray.add(new MyColor(R.color.indigo, false));
        colorArray.add(new MyColor(R.color.purple, false));
        colorArray.add(new MyColor(R.color.pink, false));
        colorArray.add(new MyColor(R.color.deep_purple, false));
        colorArray.add(new MyColor(R.color.deep_orange, false));
        colorArray.add(new MyColor(R.color.light_green, false));
        colorArray.add(new MyColor(R.color.lime, false));
        colorArray.add(new MyColor(R.color.yellow, false));
        colorArray.add(new MyColor(R.color.amber, false));
        colorArray.add(new MyColor(R.color.orange, false));
        colorArray.add(new MyColor(R.color.brown, false));
        colorArray.add(new MyColor(R.color.grey, false));
        colorArray.add(new MyColor(R.color.black, false));
        colorArray.add(new MyColor(R.color.white, false));
    }

    public static ArrayList<MyColor> filterData() {
        ArrayList<MyColor> list = new ArrayList<>();
        for (MyColor myColor : colorArray) {
            if (myColor.isSelected) {
                list.add(myColor);
            }
        }
        return list;
    }


}

