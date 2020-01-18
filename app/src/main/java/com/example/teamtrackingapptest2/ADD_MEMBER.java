package com.example.teamtrackingapptest2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ADD_MEMBER extends AppCompatActivity {
    EditText editTextName;
    EditText editTextPot;
    EditText editTextYear;
    EditText editTextBranch;
    EditText editTextProjects;
    EditText editTextDateOfJoin;
    Button buttonAdd;

    //String domain=spinnerDomain.getSelectedItem().toString();
    DatabaseReference databaseMembers = FirebaseDatabase.getInstance().getReference("Students");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__member);
        editTextName = (EditText) findViewById(R.id.name);
        editTextPot = (EditText) findViewById(R.id.position);
        editTextBranch = (EditText) findViewById(R.id.branch);
        editTextProjects = (EditText) findViewById(R.id.projects);
        editTextDateOfJoin = (EditText) findViewById(R.id.datejoined);
        editTextYear = (EditText) findViewById(R.id.year);
        buttonAdd = (Button) findViewById(R.id.button_add_member);


        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMember();
            }
        });

    }

    private void addMember() {
        String name = editTextName.getText().toString().trim();
        String pot = editTextPot.getText().toString().trim();
        String branch = editTextBranch.toString().trim();
        String year = editTextYear.getText().toString().trim();
        String projects = editTextProjects.getText().toString().trim();
        String dateOfJoin = editTextDateOfJoin.getText().toString().trim();


        if (!TextUtils.isEmpty(name)) {
            String id = databaseMembers.push().getKey();
            //Members members;
            Student student = new Student(id, name, pot, branch, year, dateOfJoin, projects);
            databaseMembers.child(name).setValue(student);
            Toast.makeText(this, "Student Added", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this, "Add a name first", Toast.LENGTH_LONG).show();
        }

    }
}