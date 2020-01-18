package com.example.teamtrackingapptest2;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teamtrackingapptest2.Adapter_name;
import com.example.teamtrackingapptest2.R;
import com.example.teamtrackingapptest2.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class attendance_main extends AppCompatActivity {
    private Button btn_add_student,track;
    DatabaseReference databaseReference,db2,itemRef;
    private EditText name,edt_from,edt_to;
    private Spinner domain;
    private Button add;
    private SearchView searchView;

    TextView tv1;
    RecyclerView rv1;
    ArrayList<Student> list;
    Adapter_name adapter;
    String name1,to,from;
    int total_number,number_present;
    FirebaseDatabase firebaseDatabase;
    ArrayList arrayList;
    Button date_picker_from,date_picker_to;
    DatePickerDialog.OnDateSetListener mDateSetListener_from,mDateSetListner_to;
    Calendar calendar;
    int day,month,year;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_main);
        btn_add_student=findViewById(R.id.button);
        track=findViewById(R.id.btn_track);
        tv1=findViewById(R.id.tv_student_name);
        edt_from=findViewById(R.id.edt_from);
        edt_to=findViewById(R.id.edt_to);
        date_picker_from=findViewById(R.id.btn_date_picker_from);
        date_picker_to=findViewById(R.id.btn_date_picker_to);
        date_picker_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar= Calendar.getInstance();
                day=calendar.get(Calendar.DAY_OF_MONTH);
                month=calendar.get(Calendar.MONTH);
                year=calendar.get(Calendar.YEAR);
                DatePickerDialog dialog=new DatePickerDialog(attendance_main.this,mDateSetListener_from,year,month,day);
                dialog.show();
            }
        });
        mDateSetListener_from=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int jyear, int jmonth, int jdayOfMonth) {
                String date=jdayOfMonth+"-"+(jmonth+1)+"-"+jyear;
                edt_from.setText(date);

            }
        };
        date_picker_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar= Calendar.getInstance();
                day=calendar.get(Calendar.DAY_OF_MONTH);
                month=calendar.get(Calendar.MONTH);
                year=calendar.get(Calendar.YEAR);
                DatePickerDialog dialog=new DatePickerDialog(attendance_main.this,mDateSetListner_to,year,month,day);
                dialog.show();
            }
        });
        mDateSetListner_to=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int myear, int mmonth, int mdayOfMonth) {

                String date=mdayOfMonth+"-"+(mmonth+1)+"-"+myear;
                edt_to.setText(date);

            }
        };

        rv1=findViewById(R.id.rv_name);
        databaseReference= FirebaseDatabase.getInstance().getReference("Students");
        rv1.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Student s=dataSnapshot1.getValue(Student.class);
                    list.add(s);
                }
                adapter= new Adapter_name(attendance_main.this,list,tv1,searchView);
                rv1.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(attendance_main.this,"There was an error",Toast.LENGTH_LONG).show();

            }
        });
        btn_add_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(attendance_main.this, add_student.class);
                startActivity(i);
            }
        });
        searchView=findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;

            }
        });
        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent u = new Intent(attendance_main.this, track.class);
                name1 = tv1.getText().toString().trim();
                from = edt_from.getText().toString().trim();
                to = edt_to.getText().toString().trim();
                if (TextUtils.isEmpty(name1)) {
                    Toast.makeText(attendance_main.this, "Select a name first", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(from)) {
                    Toast.makeText(attendance_main.this, "Select a starting date", Toast.LENGTH_LONG).show();

                } else if (TextUtils.isEmpty(to)) {
                    Toast.makeText(attendance_main.this, "Select an ending date", Toast.LENGTH_LONG).show();

                } else {
                    total_number = 0;
                    number_present = 0;

                    String[] arrTo = to.split("-");
                    String[] arrFrom = from.split("-");
                    int from_day = Integer.parseInt(arrFrom[0]);
                    int from_month = Integer.parseInt(arrFrom[1]);
                    int from_year = Integer.parseInt(arrFrom[2]);
                    int to_day = Integer.parseInt(arrTo[0]);
                    int to_month = Integer.parseInt(arrTo[1]);
                    int to_year = Integer.parseInt(arrTo[2]);
                    if ((from_year > to_year) || (from_year == to_year && from_month > to_month) || (from_year == to_year && from_month == to_month && from_day > to_day)) {
                        Toast.makeText(attendance_main.this, "Starting date cannot be greater than ending date", Toast.LENGTH_LONG).show();

                    } else
                    {
                        final LocalDate toLocalDate = LocalDate.of(Integer.parseInt(arrTo[2]), Integer.parseInt(arrTo[1]), Integer.parseInt(arrTo[0]));
                        final LocalDate fromLocalDate = LocalDate.of(Integer.parseInt(arrFrom[2]), Integer.parseInt(arrFrom[1]), Integer.parseInt(arrFrom[0]));
                        Log.d("from", fromLocalDate.toString());
                        Log.d("to", toLocalDate.toString());

                        for (LocalDate date = fromLocalDate; date.isBefore(toLocalDate.plusDays(1)); date = date.plusDays(1)) {

                            String[] d = date.toString().split("-");
                            int a = Integer.parseInt(d[1]);
                            int b = Integer.parseInt(d[2]);
                            String currentDate = b + "-" + a + "-" + d[0];


                            arrayList = new ArrayList<>();
                            firebaseDatabase = FirebaseDatabase.getInstance();
                            db2 = firebaseDatabase.getReference("Attendance");
                            itemRef = db2.child(currentDate);

                            readData(new FirebaseCallBack() {
                                @Override
                                public void onCallback(ArrayList<String> arrayList, int a, int b, LocalDate date2) {

                                    Log.d("array", arrayList.toString());
                                    if (date2.isEqual(toLocalDate)) {

                                        Log.d("currentdate", date2.toString());
                                        Log.d("total", String.valueOf(a));
                                        Log.d("present", String.valueOf(b));
                                        double d = (double) b;
                                        double present_percent = d / a * 100;
                                        double absent_percent = 100.0 - present_percent;
                                        u.putExtra("name", name1);
                                        u.putExtra("present", present_percent);
                                        u.putExtra("absent", absent_percent);
                                        startActivity(u);


                                    }
                                }
                            }, date);

                        }

                    }
                }
            }

        } );
        tv1.setText(null);
        edt_to.setText(null);
        edt_from.setText(null);
    }
    private void readData(final FirebaseCallBack firebaseCallback, final LocalDate date)
    {
        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                arrayList=new ArrayList<>();
                if(dataSnapshot.getValue()!=null) {
                    total_number++;
                    GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {};
                    arrayList = dataSnapshot.getValue(t);

                }
                if(arrayList.contains(name1))
                {
                    number_present++;
                }
                firebaseCallback.onCallback(arrayList,total_number,number_present, date);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        itemRef.addListenerForSingleValueEvent(valueEventListener);
    }

    private interface FirebaseCallBack{
        void onCallback(ArrayList<String> arrayList, int total_number,int number_present, LocalDate date);
    }
    /*private void addStudent() {
        String Name = name.getText().toString().trim();
        String Domain = domain.getSelectedItem().toString();
        if (!TextUtils.isEmpty(Name)) {
            String id=databaseReference.push().getKey();
            Student student=new Student(Name, id, Domain);
            databaseReference.child(Name).setValue(student);
            Toast.makeText(this,"Student Added",Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this, "Add a name first", Toast.LENGTH_LONG).show();
        }
    }*/
}