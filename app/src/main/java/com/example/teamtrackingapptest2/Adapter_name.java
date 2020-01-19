package com.example.teamtrackingapptest2;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_name extends RecyclerView.Adapter<Adapter_name.ViewHolder> implements Filterable {
       private Context context;
       Vibrator vibrator;
       private ArrayList<Student> students;
       private ArrayList<Student> studentsfull;
       public String studentName;
       public TextView textView;
       public SearchView searchView;

       public Adapter_name(Context context, ArrayList<Student> students, TextView textView, SearchView searchView, Vibrator vibrator) {
           this.context = context;
           this.students = students;
           this.vibrator=vibrator;
           this.searchView=searchView;
           studentsfull=new ArrayList<>(students);
           studentName="";
           this.textView=textView;

       }


       @NonNull
       @Override
       public Adapter_name.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
           View v= LayoutInflater.from(context).inflate(R.layout.name_list,parent,false);
           return new ViewHolder(v);
       }

       @Override
       public void onBindViewHolder(@NonNull final Adapter_name.ViewHolder holder, final int position) {
           Student student = students.get(position);
           holder.tv_name_student.setText(student.getName());
           studentName=holder.tv_name_student.getText().toString().trim();
           holder.linearLayout.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   studentName=students.get(position).getName();
                   searchView.setQuery(studentName,false);
                   searchView.clearFocus();
                   textView.setText(studentName);
                   if(Build.VERSION.SDK_INT>=26)
                   {
                       vibrator.vibrate(VibrationEffect.createOneShot(50,30));
                   }
                   else
                   {
                       vibrator.vibrate(50);
                   }

               }
           });


       }
       public String getStudentName()
       {
           return studentName;
       }


       @Override
       public int getItemCount() {
           return students.size();
       }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Student> filteredList=new ArrayList<>();
            if(constraint==null || constraint.length()==0)
            {
                filteredList.addAll(studentsfull );
            }
            else
            {
                String filterpattern=constraint.toString().toLowerCase().trim();
                for(Student student1: studentsfull){
                    if(student1.getName().toLowerCase().contains(filterpattern)){
                        filteredList.add(student1);
                    }
                }
            }
            FilterResults results =new FilterResults();
            results.values=filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            students.clear();
            students.addAll((ArrayList)results.values);
            notifyDataSetChanged();;
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
           TextView tv_name_student;
           LinearLayout linearLayout;
           public ViewHolder(@NonNull View itemView) {
               super(itemView);
               tv_name_student=itemView.findViewById(R.id.tv_name_student);
               linearLayout=itemView.findViewById(R.id.linearlayout);
           }
       }
   }
