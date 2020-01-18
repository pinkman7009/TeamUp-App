package com.example.teamtrackingapptest2;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class track extends AppCompatActivity {
    TextView name,present,absent;


    double present1,absent1;
    String name1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("onCreate","onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        Bundle bundle=getIntent().getExtras();
        if(bundle.getString("name")!=null)
            name1=bundle.getString("name");
        present1=bundle.getDouble("present");
        absent1=bundle.getDouble("absent");
        String present3=String.format("%.1f",present1);
        String absent3=String.format("%.1f",absent1);
        name=findViewById(R.id.tv_track_name);
        present=findViewById(R.id.tv_present_percent);
        absent=findViewById(R.id.tv_absent_percent);
        name.setText(name1);
        present.setText(present3+"%");
        absent.setText(absent3+"%");






    }


}
