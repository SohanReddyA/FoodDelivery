package com.example.food.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food.R;
import com.example.food.ui.Details;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<Item> listItems;
    Adapter.ClickListener listener;
    ImageButton Cart;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        setOnClickListener();
        recyclerView = (RecyclerView) root.findViewById(R.id.Items);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        listItems = new ArrayList<>();
        Cart = root.findViewById(R.id.CartHome);
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
        DatabaseReference Items;
        Items = FirebaseDatabase.getInstance().getReference("Items");

        Items.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long length = dataSnapshot.getChildrenCount();
                for(int i=0;i<length;i++)
                {
                    String Name= dataSnapshot.child(""+i).child("Name").getValue().toString();
                    String Price= dataSnapshot.child(""+i).child("Price").getValue().toString();
                    String ImgUrl= dataSnapshot.child(""+i).child("ImgUrl").getValue().toString();

                    Item listItem = new Item(Name,Price,ImgUrl);
                    listItems.add(listItem);

                }
                adapter = new Adapter(listItems,getActivity().getApplicationContext(), listener);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return root;
    }

    private void setOnClickListener() {
        listener = new Adapter.ClickListener() {
            @Override
            public void onClick(View v, int position) {
                Details det = new Details();
                Bundle arguments = new Bundle();
                arguments.putString("id", ""+position);
                det.setArguments(arguments);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, det);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        };
    }
}