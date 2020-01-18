package com.example.teamtrackingapptest2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class CALENDAR extends AppCompatActivity {

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
    EditText events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        db= FirebaseDatabase.getInstance().getReference("Students");
        db1= FirebaseDatabase.getInstance().getReference("Events");

        Log.d("day",String.valueOf(day));


        Log.d("month",String.valueOf(month+1));
        Log.d("year",String.valueOf(year));
        tv_date= findViewById(R.id.tv_date);
        events=findViewById(R.id.events);

        btn_date_picker=findViewById(R.id.btn_date_picker);

        btn_date_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar=Calendar.getInstance();
                day=calendar.get(Calendar.DAY_OF_MONTH);
                month=calendar.get(Calendar.MONTH);
                year=calendar.get(Calendar.YEAR);
                DatePickerDialog dialog=new DatePickerDialog(CALENDAR.this,mDateSetListener,year,month,day);
                dialog.show();

            }
        });
        mDateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int myear, int mmonth, int mdayOfMonth) {
                String date=mdayOfMonth+"-"+(mmonth+1)+"-"+year;
                date1=date;
                tv_date.setText(date);

            }
        };
        newlist=new ArrayList<>();

        rv=findViewById(R.id.rv_students);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter= new Adapter(CALENDAR.this,newlist);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Student s=dataSnapshot1.getValue(Student.class);
                    newlist.add(s);
                }
                adapter= new Adapter(CALENDAR.this,newlist);
                rv.setAdapter(adapter);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(CALENDAR.this,"Opps there was an error",Toast.LENGTH_LONG).show();

            }
        });

        selection=new ArrayList<>();

        submit=findViewById(R.id.btn_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(CALENDAR.this,"hello"+events1,Toast.LENGTH_LONG).show();
                if(!TextUtils.isEmpty(date1))
                {
                    String events1=events.getText().toString().trim();
                    selection=adapter.getSelected();
                    db1.push();
                    eventsadd ev=new eventsadd(events1,selection);
                    db1.child(date1).setValue(ev);


                    tv_date.setText(null);
                    Toast.makeText(CALENDAR.this,"Event added",Toast.LENGTH_LONG).show();

                }
                else
                    Toast.makeText(CALENDAR.this,"Select a date first",Toast.LENGTH_LONG).show();



            }
        });






    }
}

