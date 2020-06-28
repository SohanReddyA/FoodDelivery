package com.example.food.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.food.MainActivity;
import com.example.food.R;
import com.example.food.ui.Details;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    EditText User,Password;
    Button Login,Register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        User = findViewById(R.id.UserName);
        Password = findViewById(R.id.Password);
        Login = findViewById(R.id.Login);
        Register = findViewById(R.id.Register);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference mDatabase;
                mDatabase = FirebaseDatabase.getInstance().getReference("Users");
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String u = User.getText().toString();
                        String p = Password.getText().toString();
                        long length = dataSnapshot.getChildrenCount();
                        int flag=0;
                        for(int i=0;i<length;i++)
                        {
                            if(u.equals(dataSnapshot.child(""+i).child("Name").getValue().toString())&&p.equals(dataSnapshot.child(""+i).child("Password").getValue().toString()))
                            {
                                flag=1;
                                Intent j = new Intent(Login.this, MainActivity.class);
                                j.putExtra("Name",dataSnapshot.child(""+i).child("Name").getValue().toString());
                                j.putExtra("Email",dataSnapshot.child(""+i).child("Email").getValue().toString());
                                j.putExtra("ImgUrl",dataSnapshot.child(""+i).child("ImgUrl").getValue().toString());
                                startActivity(j);
                            }
                        }
                        if(flag==0){
                            Toast.makeText(Login.this,"Incorrect Credentials",Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(Login.this, Register.class);
                startActivity(j);
            }
        });
    }
}
