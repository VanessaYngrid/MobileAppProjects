package com.example.facultyproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facultyproject.model.Faculty;

import java.util.List;

public class FacultyAdapter extends RecyclerView.Adapter<FacultyAdapter.FacultyViewHolder> {

    private List<Faculty> facultyList;
    private AllFacultiesActivity activity;

    public FacultyAdapter(List<Faculty> facultyList, AllFacultiesActivity activity) {
        this.facultyList = facultyList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public FacultyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.faculty_list_item, parent, false);
        return new FacultyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull FacultyViewHolder holder, int position) {
        Faculty faculty = facultyList.get(position);
        holder.bind(faculty);
    }

    @Override
    public int getItemCount() {
        return facultyList.size();
    }


    class FacultyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView facultyIdTextView;
        private TextView facultyLNameTextView;
        private TextView facultyFNameTextView;
        private TextView facultySalaryTextView;
        private TextView facultyBonusTextView;

        public FacultyViewHolder(@NonNull View itemView) {
            super(itemView);
            facultyIdTextView = itemView.findViewById(R.id.faculty_id);
            facultyLNameTextView = itemView.findViewById(R.id.faculty_lname);
            facultyFNameTextView = itemView.findViewById(R.id.faculty_fname);
            facultySalaryTextView = itemView.findViewById(R.id.faculty_salary);
            facultyBonusTextView = itemView.findViewById(R.id.faculty_bonus);
            itemView.setOnClickListener(this);
        }

        public void bind(Faculty faculty) {
            facultyIdTextView.setText(faculty.getF_Id() +"");
            facultyLNameTextView.setText(faculty.getF_Lname());
            facultyFNameTextView.setText(faculty.getF_Fname());
            facultySalaryTextView.setText(faculty.getF_Salary() + "");
            facultyBonusTextView.setText(faculty.getF_bonus() + "");
        }

        @Override
        public void onClick(View v) {
            Faculty faculty = facultyList.get(getAdapterPosition());
            activity.showBonus(faculty.calculate_Bonus());
        }

    }
}