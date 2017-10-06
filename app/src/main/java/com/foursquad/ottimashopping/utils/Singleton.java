package com.foursquad.ottimashopping.utils;

import com.foursquad.ottimashopping.models.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oussemaar on 10/5/17.
 * <In Code I Trust />
 */

public class Singleton {
    private static final Singleton ourInstance = new Singleton();
    public List<Article> articleList = new ArrayList<>();

    public static Singleton getInstance() {
        return ourInstance;
    }

    private Singleton() {
    }
}
