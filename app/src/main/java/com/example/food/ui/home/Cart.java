package com.example.food.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.food.R;
import com.example.food.ui.Details;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Cart extends Fragment {
    CartAdapter.RecyclerViewClickListener listener;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<CartItem> listItems;
    Button Clear,Place;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_cart, container, false);
        setOnClickListener();
        recyclerView = (RecyclerView) root.findViewById(R.id.CartItems);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        listItems = new ArrayList<>();
        Clear = root.findViewById(R.id.ClearCart);
        Place = root.findViewById(R.id.PlaceOrder);
        Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("Cart").removeValue();
                Toast.makeText(getActivity().getApplicationContext(), "Cart Cleared", Toast.LENGTH_SHORT).show();
                HomeFragment det = new HomeFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, det);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        Place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("Cart").removeValue();
                Toast.makeText(getActivity().getApplicationContext(), "Order Placed", Toast.LENGTH_SHORT).show();
                HomeFragment det = new HomeFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, det);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        DatabaseReference Items;
        Items = FirebaseDatabase.getInstance().getReference();

        Items.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.child("Cart").getChildren()) {
                    String id= child.child("id").getValue().toString();
                    String Name= dataSnapshot.child("Items").child(id).child("Name").getValue().toString();
                    String Price= dataSnapshot.child("Items").child(id).child("Price").getValue().toString();
                    String ImgUrl= dataSnapshot.child("Items").child(id).child("ImgUrl").getValue().toString();
                    CartItem listItem = new CartItem(Name,Price,ImgUrl,child.child("Quantity").getValue().toString());
                    listItems.add(listItem);
                }
                adapter = new CartAdapter(listItems,getActivity().getApplicationContext(),listener);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return root;
    }
    private void setOnClickListener(){
        listener = new CartAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (view.getId() == R.id.mapBtn) {
                    View v = recyclerView.getChildAt(position);
                    EditText Quantity = v.findViewById(R.id.CartQuantity);
                    FirebaseDatabase.getInstance().getReference("Cart").child("" + position).child("Quantity").setValue(Quantity.getText().toString());
                    Toast.makeText(getActivity().getApplicationContext(), "Quantity Updated", Toast.LENGTH_SHORT).show();
                }
                else if (view.getId() == R.id.mapBtn2) {
                    listItems.remove(position);
                    adapter.notifyItemRemoved(position);
                    FirebaseDatabase.getInstance().getReference("Cart").child("" + position).removeValue();
                    Toast.makeText(getActivity().getApplicationContext(), "Item Removed", Toast.LENGTH_SHORT).show();

                }
            }
        };
    }
}