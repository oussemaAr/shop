package com.foursquad.ottimashopping.utils;

import com.foursquad.ottimashopping.models.Article;

/**
 * Created by oussemaar on 9/10/17.
 * <In Code I Trust />
 */

public interface ItemClickListener {
    void onItemClick(Article article);
    void onItemLongClick(Article article);
}
