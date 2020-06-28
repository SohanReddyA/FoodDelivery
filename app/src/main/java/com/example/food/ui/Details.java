package com.example.food.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.food.MainActivity;
import com.example.food.R;
import com.example.food.ui.home.Adapter;
import com.example.food.ui.home.Cart;
import com.example.food.ui.home.HomeFragment;
import com.example.food.ui.home.Item;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.MulticastSocket;

public class Details extends Fragment {
    ImageView ItemImg;
    TextView Name,Price;
    EditText quantity;
    Button add;
    Button Cart;
    String id;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_details, container, false);
        ItemImg=root.findViewById(R.id.ImgDisplay);
        Name=root.findViewById(R.id.ItemName2);
        Price=root.findViewById(R.id.ItemPrice2);
        quantity=root.findViewById(R.id.Quantity);
        add=root.findViewById(R.id.Add);
        Cart = root.findViewById(R.id.CartDetails);
        Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart cart  = new Cart();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, cart);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        id=this.getArguments().getString("id");
        DatabaseReference Item;
        Item = FirebaseDatabase.getInstance().getReference("Items").child(id);
        Item.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Name.setText(dataSnapshot.child("Name").getValue().toString());
                Price.setText(dataSnapshot.child("Price").getValue().toString());
                Glide.with(Details.this)
                        .load(dataSnapshot.child("ImgUrl").getValue().toString())
                        .centerCrop()
                        .into(ItemImg);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference CartItem = FirebaseDatabase.getInstance().getReference("Cart");
                final long[] length = new long[1];
                CartItem.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        length[0] = dataSnapshot.getChildrenCount();
                        Run(length[0]);

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        return root;
    }
    void Run(long i){

        DatabaseReference CartItem = FirebaseDatabase.getInstance().getReference("Cart");
        CartItem.child(""+i).child("id").setValue(id);
        CartItem.child(""+i).child("Quantity").setValue(quantity.getText().toString());
        Toast.makeText(getActivity().getApplicationContext(),"Item Added",Toast.LENGTH_SHORT).show();
        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, homeFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}