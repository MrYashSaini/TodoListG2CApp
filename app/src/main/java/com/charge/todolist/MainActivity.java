package com.charge.todolist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.charge.todolist.account.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements AddDialogBox.AddDialogListener,DeleteDialogBox.DeleteDialogListener{
    private ListView listView;
    private FirebaseDatabase database;
    private ArrayList<String> ArrayList= new ArrayList<>();
    private FirebaseAuth auth;
    private int Position;
    private DeleteDialogBox deleteDialogBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
        ImageView logout = findViewById(R.id.ivMainLogOut);
        Button add = findViewById(R.id.addbutton);
        ArrayAdapter<String> myAdapter= new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, ArrayList);
        listView = findViewById(R.id.listview);
        listView.setAdapter(myAdapter);
        auth = FirebaseAuth.getInstance();

        logout.setOnClickListener(v -> {
            auth.signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });


        DatabaseReference mref = FirebaseDatabase.getInstance().getReference().child(Objects.requireNonNull(auth.getUid())).child("check_items");
        mref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value = snapshot.getValue(String.class);
                ArrayList.add(value);
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            deleteDialogBox  = new DeleteDialogBox();
            deleteDialogBox.show(getSupportFragmentManager(),"delete dialog");
            Position=i;
        });
        add.setOnClickListener(view -> {
            AddDialogBox addDialogBox = new AddDialogBox();
            addDialogBox.show(getSupportFragmentManager(),"add dialog");
        });
    }
    @Override
    public void applytext(String checkitem) {
        database = FirebaseDatabase.getInstance();
        database.getReference().child(Objects.requireNonNull(auth.getUid())).child("check_items").child(checkitem).setValue(checkitem);
    }

    @Override
    public void deletetext(String dis) {
        try {
            database = FirebaseDatabase.getInstance();
            database.getReference().child(Objects.requireNonNull(auth.getUid())).child("check_items").child(ArrayList.get(Position)).removeValue();
            ArrayList.remove(Position);
            ArrayAdapter<String> myAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, ArrayList);
            listView = findViewById(R.id.listview);
            listView.setAdapter(myAdapter);
            myAdapter.notifyDataSetChanged();
            deleteDialogBox.dismiss();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}