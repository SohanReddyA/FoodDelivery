package com.example.food.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.food.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    List<CartItem> listItems;
    Context context;
    RecyclerViewClickListener clickListener;
    Button mapBtn,mapBtn2;
    public CartAdapter(List<CartItem> listItems, Context context,RecyclerViewClickListener listener) {
        this.listItems = listItems;
        this.context = context;
        this.clickListener = listener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cartitem,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem listItem=listItems.get(position);
        holder.ItemName.setText(listItem.getCartName());
        holder.ItemPrice.setText(listItem.getCartPrice());
        Glide.with(context)
                .load(listItem.getCartImgUrl())
                .centerCrop()
                .into(holder.ItemPic);
        holder.ItemQuantity.setText(listItem.getCartQuantity());

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView ItemName,ItemPrice;
        public ImageView ItemPic;
        public EditText ItemQuantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ItemName= (TextView) itemView.findViewById(R.id.CartName);

            ItemPrice=(TextView) itemView.findViewById(R.id.CartPrice);

            ItemPic=(ImageView) itemView.findViewById(R.id.CartPic);
            ItemQuantity = itemView.findViewById(R.id.CartQuantity);
            mapBtn = itemView.findViewById(R.id.mapBtn);
            mapBtn2 = itemView.findViewById(R.id.mapBtn2);
            mapBtn.setOnClickListener(this);
            mapBtn2.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition());
        }
    }

    public interface RecyclerViewClickListener {

        void onClick(View v, int position);
    }
}

