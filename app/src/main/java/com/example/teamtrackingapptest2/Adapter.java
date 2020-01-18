package com.example.teamtrackingapptest2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private Context context;
    private ArrayList<Student> students;
    public ArrayList<String> selected;

    public Adapter(Context context, ArrayList<Student> students) {
        this.context = context;
        this.students = students;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.students_list,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Student student = students.get(position);
        holder.checkBox.setText(student.getName());
        selected=new ArrayList<>();
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1=holder.checkBox.getText().toString().trim();
                if(holder.checkBox.isChecked()){
                    selected.add(name1);
                }
                else{
                    if(selected.contains(name1))
                    {
                        selected.remove(name1);
                    }
                }
            }
        });

    }
    public ArrayList<String> getSelected()
    {
        return selected;
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox=itemView.findViewById(R.id.checkBox);
        }
    }
}
