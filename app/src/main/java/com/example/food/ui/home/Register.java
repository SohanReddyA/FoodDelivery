package com.example.food.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.food.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {
    EditText User, Email, ImgUrl, Pass, PassConf;
    Button Reg;

     int confirm = 0, imageTrue = 0;
     long length;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        User = findViewById(R.id.UserNameReg);
        Email = findViewById(R.id.EmailReg);
        ImgUrl = findViewById(R.id.ImgUrlReg);
        Pass = findViewById(R.id.PasswordReg);
        PassConf = findViewById(R.id.PasswordReg2);
        Reg = findViewById(R.id.RegisterReg);
        Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference mDatabase;
                mDatabase = FirebaseDatabase.getInstance().getReference("Users");
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        length = dataSnapshot.getChildrenCount();
                        int flag = 0;
                        for (int i = 0; i < length; i++) {
                            if (User.getText().toString().equals(dataSnapshot.child("" + i).child("Name").getValue().toString())) {
                                flag = 1;
                                Toast.makeText(Register.this, "Username already exists, enter a different name", Toast.LENGTH_SHORT).show();
                            }
                        }
                        if (flag == 0) {
                            if (Email.getText().toString().contains("@")) {
                                if (Pass.getText().toString().equals(PassConf.getText().toString())) {
                                    confirm = 1;
                                    if (ImgUrl.getText().toString().equals("")) {
                                        imageTrue = 0;
                                    } else {
                                        imageTrue = 1;
                                    }
                                } else
                                    Toast.makeText(Register.this, "Passwords Do Not Match", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Register.this, "Enter a valid email", Toast.LENGTH_SHORT).show();
                            }
                        }
                        Check();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


    }

    public void Check () {

        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        if (confirm == 1) {
            mDatabase.child("" + (length )).child("Name").setValue(User.getText().toString());
            mDatabase.child("" + (length )).child("Email").setValue(Email.getText().toString());
            mDatabase.child("" + (length )).child("Password").setValue(Pass.getText().toString());
            if (imageTrue == 1) {
                mDatabase.child("" + (length)).child("ImgUrl").setValue(ImgUrl.getText().toString());
            } else {
                mDatabase.child("" + (length)).child("ImgUrl").setValue("");
            }
            Toast.makeText(Register.this, "Creating Account", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Register.this, Login.class);
            startActivity(i);
        }
    }
}
