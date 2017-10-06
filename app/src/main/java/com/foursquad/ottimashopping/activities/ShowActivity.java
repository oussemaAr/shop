package com.foursquad.ottimashopping.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.foursquad.ottimashopping.R;
import com.foursquad.ottimashopping.adapters.ColorPickerAdapter;
import com.foursquad.ottimashopping.models.Article;
import com.foursquad.ottimashopping.utils.CheckView;

import java.util.Locale;

public class ShowActivity extends AppCompatActivity {
    private Article article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Toolbar toolbar = findViewById(R.id.toolbar_parent);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Affichage Article");

        }
        article = (Article) getIntent().getSerializableExtra("data");
        initView();
    }

    private void initView() {
        TextView marque = findViewById(R.id.marque);
        marque.setText(article.getTitle());
        TextView produit = findViewById(R.id.product_type);
        produit.setText(article.getType());
        TextView priceB = findViewById(R.id.buy_price);
        priceB.setText(String.format("%s€", String.valueOf(article.getBuyPrice())));
        TextView priceS = findViewById(R.id.sell_price);
        priceS.setText(String.format("%s€", String.valueOf(article.getSellPrice())));
        ImageView imageView = findViewById(R.id.image_preview);
        Glide.with(this)
                .load(article.getImageUri())
                .into(imageView);
        RecyclerView recyclerView = findViewById(R.id.recycler_color);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new ColorPickerAdapter(this, article.getColors(), 0));
        CheckView xs = findViewById(R.id.xs);
        CheckView s = findViewById(R.id.s);
        CheckView m = findViewById(R.id.m);
        CheckView l = findViewById(R.id.l);
        CheckView xl = findViewById(R.id.xl);
        if (article.getSizes() != null) {
            if (article.getSizes().contains("xs"))
                xs.setStatus(true);
            if (article.getSizes().contains("s"))
                s.setStatus(true);
            if (article.getSizes().contains("m"))
                m.setStatus(true);
            if (article.getSizes().contains("l"))
                l.setStatus(true);
            if (article.getSizes().contains("xl"))
                xl.setStatus(true);
        }

        TextView qte = findViewById(R.id.qte);
        qte.setText(String.format(Locale.getDefault(), "Quantité %1$d", article.getQuantity()));
        SeekBar seekBar = findViewById(R.id.seek_bar);
        seekBar.setProgress(article.getQuantity());
        seekBar.setEnabled(false);

    }

    public static void startActivity(Context context, Article article) {
        Intent intent = new Intent(context, ShowActivity.class);
        intent.putExtra("data", article);
        context.startActivity(intent);
    }
}
