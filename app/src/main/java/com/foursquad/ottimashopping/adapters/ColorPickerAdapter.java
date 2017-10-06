package com.foursquad.ottimashopping.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.foursquad.ottimashopping.R;
import com.foursquad.ottimashopping.models.MyColor;

import java.util.ArrayList;


/**
 * Created by oussemaar on 8/8/17.
 * <In Code I Trust />
 */

public class ColorPickerAdapter extends RecyclerView.Adapter<ColorPickerAdapter.MyViewHolder> {

    private ArrayList<MyColor> list;
    private ItemClickListener mClickListener;
    private Context context;
    private int viewType;


    public ColorPickerAdapter(Context context, ArrayList<MyColor> list, int viewType) {
        this.list = list;
        this.context = context;
        this.viewType = viewType;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewHolder;
        if (viewType == 1)
            viewHolder = LayoutInflater.from(parent.getContext()).inflate(R.layout.color_pcker_item, parent, false);
        else
            viewHolder = LayoutInflater.from(parent.getContext()).inflate(R.layout.color_pcker_item_small, parent, false);
        return new MyViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MyColor item = list.get(position);
        GradientDrawable bgShape = (GradientDrawable) holder.layout.getBackground();
        bgShape.setColor(ContextCompat.getColor(context, item.color));
        if (viewType != 0)
            if (item.isSelected)
                holder.imageView.setVisibility(View.VISIBLE);
            else
                holder.imageView.setVisibility(View.GONE);
    }

    @Override
    public int getItemViewType(int position) {
        return viewType;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout layout;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            layout = itemView.findViewById(R.id.color_picker_bg);
            imageView = itemView.findViewById(R.id.color_picker_checked_img);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public MyColor getItem(int id) {
        return list.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

