package com.foursquad.ottimashopping.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.foursquad.ottimashopping.R;
import com.foursquad.ottimashopping.models.Article;
import com.foursquad.ottimashopping.utils.Singleton;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatistiqueFragment extends Fragment {

    private View rootView;
    private int total = 0;

    public StatistiqueFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_statistique, container, false);
        addTableTitle();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        for (Article article1 : Singleton.getInstance().articleList) {
            total += article1.getQuantity();
        }

        for (Article article1 : Singleton.getInstance().articleList) {
            addTableRow(article1);
        }
    }

    private void addTableRow(Article article) {
        final TableLayout table = rootView.findViewById(R.id.my_table);
        final TableRow tr = (TableRow) getLayoutInflater().inflate(R.layout.table_row_item, null);

        TextView tv;

        tv = tr.findViewById(R.id.cell_1);
        tv.setText(article.getTitle());

        tv = tr.findViewById(R.id.cell_2);
        tv.setText(String.valueOf(article.getQuantity()));

        tv = tr.findViewById(R.id.cell_3);
        tv.setText(String.valueOf((article.getQuantity() * 100) / total));

        table.addView(tr);

        tv = new TextView(getActivity());
        tv.setBackgroundColor(Color.parseColor("#80808080"));
        tv.setHeight(TableRow.LayoutParams.WRAP_CONTENT);
        table.addView(tv);

        registerForContextMenu(tr);
    }

    private void addTableTitle() {
        final TableLayout table = rootView.findViewById(R.id.my_table);
        final TableRow tr = (TableRow) getLayoutInflater().inflate(R.layout.table_row_item, null);

        TextView tv;

        tv = tr.findViewById(R.id.cell_1);
        tv.setText("Désignation");

        tv = tr.findViewById(R.id.cell_2);
        tv.setText("N° Pièces");

        tv = tr.findViewById(R.id.cell_3);
        tv.setText("% par rapport Achat");

        table.addView(tr);

        tv = new TextView(getActivity());
        tv.setBackgroundColor(Color.parseColor("#80808080"));
        tv.setHeight(TableRow.LayoutParams.WRAP_CONTENT);
        table.addView(tv);

        registerForContextMenu(tr);
    }

}
