package com.foursquad.ottimashopping.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.foursquad.ottimashopping.R;
import com.foursquad.ottimashopping.models.Article;
import com.foursquad.ottimashopping.utils.CheckView;
import com.foursquad.ottimashopping.utils.ItemClickListener;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by oussemaar on 8/7/17.
 * <In Code I Trust />
 */

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.MyViewHolder> {

    private List<Article> articles;
    private Context mContext;
    private ItemClickListener itemClickListener;

    public ItemsAdapter(Context mContext, List<Article> articles) {
        this.articles = articles;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Article article = articles.get(position);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.ic_launcher);
        requestOptions.error(R.drawable.ic_add);
        Glide.with(mContext)
                .setDefaultRequestOptions(requestOptions)
                .load(article.getImageUri())
                .into(holder.imageView);
        holder.title.setText(article.getType());
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        holder.sellP.setText(String.format("%s dt", String.valueOf(decimalFormat.format(article.getFinalPrice()))));
        holder.buyP.setText(String.format("%s€", String.valueOf(decimalFormat.format(article.getBuyPrice()))));
        holder.qte.setText(String.format("Quantité %s", String.valueOf(article.getQuantity())));
        if (article.getSizes() != null) {
            if (article.getSizes().contains("xs"))
                holder.xs.setVisibility(View.VISIBLE);
            if (article.getSizes().contains("s"))
                holder.s.setVisibility(View.VISIBLE);
            if (article.getSizes().contains("m"))
                holder.m.setVisibility(View.VISIBLE);
            if (article.getSizes().contains("l"))
                holder.l.setVisibility(View.VISIBLE);
            if (article.getSizes().contains("xl"))
                holder.xl.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private ImageView imageView;
        private TextView title;
        private TextView sellP;
        private TextView buyP;
        private TextView qte;
        private CheckView xs, s, m, l, xl;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
            title = itemView.findViewById(R.id.marque);
            sellP = itemView.findViewById(R.id.sellP);
            buyP = itemView.findViewById(R.id.buyP);
            qte = itemView.findViewById(R.id.qte);
            xs = itemView.findViewById(R.id.xs);
            s = itemView.findViewById(R.id.s);
            m = itemView.findViewById(R.id.m);
            l = itemView.findViewById(R.id.l);
            xl = itemView.findViewById(R.id.xl);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onItemClick(articles.get(getAdapterPosition()));
        }

        @Override
        public boolean onLongClick(View view) {
            itemClickListener.onItemLongClick(articles.get(getAdapterPosition()));
            return true;
        }
    }
}
