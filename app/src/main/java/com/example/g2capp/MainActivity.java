package com.example.g2capp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddDialogBox.AddDialogListener,DeleteDialogBox.DeleteDialogListener{
    ListView listView;
    Button add;
    FirebaseDatabase database;
    ArrayList<String> ArrayList= new ArrayList<>();
    DatabaseReference mref;
    int Position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        add = findViewById(R.id.addbutton);
        ArrayAdapter<String> myAdapter= new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,ArrayList);
        listView = findViewById(R.id.listview);
        listView.setAdapter(myAdapter);

        mref = FirebaseDatabase.getInstance().getReference().child("check_items");
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DeleteDialogBox deleteDialogBox  = new DeleteDialogBox();
                deleteDialogBox.show(getSupportFragmentManager(),"delete dialog");
                Position=i;
//                Toast.makeText(MainActivity.this, "position"+i, Toast.LENGTH_SHORT).show();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddDialogBox addDialogBox = new AddDialogBox();
                addDialogBox.show(getSupportFragmentManager(),"add dialog");
            }
        });
    }
    @Override
    public void applytext(String checkitem) {
        Toast.makeText(this, "Add Item", Toast.LENGTH_SHORT).show();
        database = FirebaseDatabase.getInstance();
        database.getReference().child("check_items").child(checkitem).setValue(checkitem);
    }

    @Override
    public void deletetext(String dis) {
        try {
            database = FirebaseDatabase.getInstance();
            database.getReference().child("check_items").child(ArrayList.get(Position)).removeValue();
            ArrayList.remove(Position);
            ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, ArrayList);
            listView = findViewById(R.id.listview);
            listView.setAdapter(myAdapter);
            Toast.makeText(this, "delete" + ArrayList.get(Position) + dis, Toast.LENGTH_LONG).show();
            myAdapter.notifyDataSetChanged();
            DeleteDialogBox deleteDialogBox = new DeleteDialogBox();
            deleteDialogBox.dismiss();
//            addDialogBox.isHidden();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}