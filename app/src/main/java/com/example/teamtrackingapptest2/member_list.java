package com.example.teamtrackingapptest2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class member_list extends AppCompatActivity {
    DatabaseReference databaseReference;
    RecyclerView rv;
    ArrayList<Student> list;
    memebers_list_adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list);
        databaseReference= FirebaseDatabase.getInstance().getReference("Students");
        rv=findViewById(R.id.rv_members_list);
        rv.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Student s=dataSnapshot1.getValue(Student.class);
                    list.add(s);
                }
                adapter= new memebers_list_adapter(member_list.this,list);
                rv.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(member_list.this,"There was an error",Toast.LENGTH_LONG).show();

            }
        });


    }
}