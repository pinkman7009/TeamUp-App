package com.example.teamtrackingapptest2;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class add_student extends AppCompatActivity {
    ArrayList<Student> newlist;
    Button btn_date_picker,submit;
    Calendar calendar;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    TextView tv_date;
    RecyclerView rv;
    DatabaseReference db,db1;
    Adapter adapter;
    ArrayList<String> selection;
    int day,month, year;
    public String date1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_student);
        db= FirebaseDatabase.getInstance().getReference("Students");
        db1= FirebaseDatabase.getInstance().getReference("Attendance");

         Log.d("day",String.valueOf(day));


        Log.d("month",String.valueOf(month+1));
        Log.d("year",String.valueOf(year));
        tv_date= findViewById(R.id.tv_date);

        btn_date_picker=findViewById(R.id.btn_date_picker);

        btn_date_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar=Calendar.getInstance();
                day=calendar.get(Calendar.DAY_OF_MONTH);
                month=calendar.get(Calendar.MONTH);
                year=calendar.get(Calendar.YEAR);
                DatePickerDialog dialog=new DatePickerDialog(add_student.this,mDateSetListener,year,month,day);
                dialog.show();

            }
        });
        mDateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int myear, int mmonth, int mdayOfMonth) {
              String date=mdayOfMonth+"-"+(mmonth+1)+"-"+year;
              tv_date.setText(date);

            }
        };
        newlist=new ArrayList<>();

        rv=findViewById(R.id.rv_students);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter= new Adapter(add_student.this,newlist);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Student s=dataSnapshot1.getValue(Student.class);
                    newlist.add(s);
                }
                adapter= new Adapter(add_student.this,newlist);
                rv.setAdapter(adapter);

            }


        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(add_student.this,"Opps there was an error",Toast.LENGTH_LONG).show();

        }
    });

        selection=new ArrayList<>();
        date1=tv_date.getText().toString().trim();
        submit=findViewById(R.id.btn_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date1=tv_date.getText().toString().trim();

                if(!TextUtils.isEmpty(date1))
                {
                    selection=adapter.getSelected();
                    db1.push();
                    db1.child(date1).setValue(selection);
                    tv_date.setText(null);
                    Toast.makeText(add_student.this,"Attendace taken",Toast.LENGTH_LONG).show();

                }
                else
                    Toast.makeText(add_student.this,"Select a date first",Toast.LENGTH_LONG).show();



            }
        });






}


}


