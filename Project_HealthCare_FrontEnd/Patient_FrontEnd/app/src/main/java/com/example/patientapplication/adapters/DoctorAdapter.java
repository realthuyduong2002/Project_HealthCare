// DoctorAdapter.java

package com.example.patientapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.patientapplication.R;
import com.example.patientapplication.model.Doctor;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder> {

    public interface OnDoctorClickListener {
        void onDoctorClick(Doctor doctor);
    }

    private List<Doctor> doctorList;
    private OnDoctorClickListener clickListener;

    public DoctorAdapter(List<Doctor> doctorList, OnDoctorClickListener clickListener) {
        this.doctorList = doctorList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor, parent, false);
        return new DoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        Doctor doctor = doctorList.get(position);
        holder.textViewName.setText(doctor.getDoctorName());
        holder.textViewSpecialty.setText(doctor.getSpeciality());

        holder.btnChoose.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onDoctorClick(doctor);
            }
        });
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    public static class DoctorViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewSpecialty;
        Button btnChoose;

        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewSpecialty = itemView.findViewById(R.id.textViewSpecialty);
            btnChoose = itemView.findViewById(R.id.btnChoose);
        }
    }
}
