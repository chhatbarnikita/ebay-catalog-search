package com.search.ebaycatalog;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{
    private ArrayList<ItemData> itemData;
    private Context context;

    public CustomAdapter(Context context, ArrayList<ItemData> itemData) {
        this.itemData = itemData;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder,final int position) {
        if(itemData.get(position).getImageUrl() != "https://thumbs1.ebaystatic.com/pict/04040_0.jpg" || itemData.get(position).getImageUrl() != null)
            Picasso.with(context).load(itemData.get(position).getImageUrl()).into(holder.image);
        else
            Picasso.with(context).load(R.drawable.ebay_default).into(holder.image);

        holder.title.setText(Html.fromHtml("<h4>" + itemData.get(position).getTitle() + "</h4>"));

        if(itemData.get(position).getFreeShipping() == 0)
            holder.shipping.setText(Html.fromHtml("<b>FREE<b> Shipping"));
        else
            holder.shipping.setText(Html.fromHtml("Ships for <b>$" + itemData.get(position).getFreeShipping().toString() + "</b>"));

        if(itemData.get(position).isTopRatedImage() == "true")
            holder.topRatedItem.setText(Html.fromHtml("<b>Top Rated Listing<b>"));
        else
            holder.topRatedItem.setVisibility(View.INVISIBLE);

        if(itemData.get(position).getCondition().isEmpty())
            holder.condition.setText(Html.fromHtml("<b><i>N/A<i><b>"));
        else
            holder.condition.setText(Html.fromHtml("<b><i>"+ itemData.get(position).getCondition() + "</i></b>"));

        holder.price.setText(Html.fromHtml("<span style='color: #9ACD32'><b>$" + itemData.get(position).getPrice().toString() + "<b></span>"));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailedProductInfo.class);
                intent.putExtra("itemData", itemData.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title, shipping, topRatedItem, condition, price;
        ImageView image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.itemImage);
            title = itemView.findViewById(R.id.itemTitle);
            shipping = itemView.findViewById((R.id.shipping));
            topRatedItem = itemView.findViewById(R.id.topRatedItem);
            condition = itemView.findViewById(R.id.condition);
            price = itemView.findViewById(R.id.price);
        }
    }
}
