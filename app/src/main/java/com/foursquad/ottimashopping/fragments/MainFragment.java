package com.foursquad.ottimashopping.fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foursquad.ottimashopping.R;
import com.foursquad.ottimashopping.activities.AddArticle;
import com.foursquad.ottimashopping.activities.ShowActivity;
import com.foursquad.ottimashopping.adapters.ItemsAdapter;
import com.foursquad.ottimashopping.models.Article;
import com.foursquad.ottimashopping.utils.Constant;
import com.foursquad.ottimashopping.utils.ItemClickListener;
import com.foursquad.ottimashopping.utils.Singleton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements ItemClickListener, ValueEventListener {


    private RecyclerView recyclerView;
    private DatabaseReference mReference;
    private List<Article> articleList = new ArrayList<>();
    private View rootView;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);

        recyclerView = rootView.findViewById(R.id.recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        Constant.fillData();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("articles");

        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddArticle.startActivity(getActivity());
            }
        });
        mReference.addValueEventListener(this);

        return rootView;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        articleList.clear();
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            Article article;
            article = snapshot.getValue(Article.class);
            assert article != null;
            article.setId(snapshot.getKey());
            articleList.add(article);
        }
        Singleton.getInstance().articleList = articleList;
        ItemsAdapter adapter = new ItemsAdapter(getActivity(), articleList);
        adapter.setItemClickListener(this);
        recyclerView.setAdapter(adapter);
        float totalBuy = 0, totalSell = 0;
        for (Article article : articleList) {
            totalBuy += article.getBuyPrice() * article.getQuantity();
            totalSell += article.getSellPrice() * article.getQuantity();
        }
        ((TextView) rootView.findViewById(R.id.totalBuy)).setText(String.format("%s€", String.valueOf(totalBuy)));
        ((TextView) rootView.findViewById(R.id.totalSell)).setText(String.format("%stnd", String.valueOf(totalSell)));
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    @Override
    public void onItemClick(Article article) {
        ShowActivity.startActivity(getActivity(), article);
    }

    @Override
    public void onItemLongClick(final Article article) {
        new AlertDialog.Builder(getActivity())
                .setTitle("Suppression article")
                .setMessage("Voulez-vous supprimer cet article ?!")
                .setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mReference.child(article.getId()).removeValue();
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create()
                .show();
    }

}
