package com.example.teamtrackingapptest2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class memebers_list_adapter extends RecyclerView.Adapter<memebers_list_adapter.ViewHolder> {
    private Context context;
    private ArrayList<Student> students;

    public memebers_list_adapter(Context context, ArrayList<Student> students) {
        this.context = context;
        this.students = students;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.memeber_list_adapter,parent,false);
        return new ViewHolder(v);    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Student student = students.get(position);
        holder.name.setText(student.getName());
        holder.position.setText(student.getPOT());
        holder.branch.setText(student.getBranch());
        holder.year.setText(student.getYear());
        holder.projects.setText(student.getProjects());



    }

    @Override
    public int getItemCount() {
        return students.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,position,year, branch,projects;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.tv_ml_name);
            position=itemView.findViewById(R.id.tv_ml_position);
            branch=itemView.findViewById(R.id.tv_ml_branch);
            year=itemView.findViewById(R.id.tv_ml_year);
            projects=itemView.findViewById(R.id.tv_ml_projects);
        }
    }
}