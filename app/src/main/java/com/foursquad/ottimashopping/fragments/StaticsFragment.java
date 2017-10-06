package com.foursquad.ottimashopping.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foursquad.ottimashopping.R;
import com.foursquad.ottimashopping.models.Article;
import com.foursquad.ottimashopping.utils.Singleton;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class StaticsFragment extends Fragment implements OnChartValueSelectedListener {

    private PieChart mChart;
    private List<PieEntry> yvalues = new ArrayList<>();

    public StaticsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_statics, container, false);
        mChart = rootView.findViewById(R.id.chart1);
        mChart.setUsePercentValues(true);
        mChart.setRotationEnabled(true);
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.e("TAG", "onStart: 1");
        setData();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    private void setData() {
        yvalues.clear();
        for (Article article : Singleton.getInstance().articleList) {
            yvalues.add(new PieEntry(article.getQuantity(), article.getTitle()));
        }

        PieDataSet dataSet = new PieDataSet(yvalues, "Listes Articles");
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        PieData data = new PieData();
        data.setDataSet(dataSet);
        data.setValueFormatter(new PercentFormatter());
        Description description = new Description();
        data.setValueTextSize(13f);
        data.setValueTextColor(Color.DKGRAY);
        mChart.setDescription(description);
        mChart.setDrawHoleEnabled(true);
        mChart.setTransparentCircleRadius(30f);
        mChart.setHoleRadius(30f);
        mChart.setData(data);
    }

}
