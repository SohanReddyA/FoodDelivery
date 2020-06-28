package com.example.food.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.food.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private ClickListener listener;
    List<Item> listItems;
    Context context;

    public Adapter(List<Item> listItems, Context context,ClickListener listener) {
        this.listItems = listItems;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item listItem=listItems.get(position);
        holder.ItemName.setText(listItem.getItemName());
        holder.ItemPrice.setText(listItem.getItemPrice());
        Glide.with(context)
                .load(listItem.getItemImgUrl())
                .centerCrop()
                .into(holder.ItemPic);

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public interface ClickListener{
        void onClick(View v,int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView ItemName,ItemPrice;
        public ImageView ItemPic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ItemName= (TextView) itemView.findViewById(R.id.ItemName);

            ItemPrice=(TextView) itemView.findViewById(R.id.ItemPrice);

            ItemPic=(ImageView) itemView.findViewById(R.id.ItemPic);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v,getAdapterPosition());
        }
    }
}

