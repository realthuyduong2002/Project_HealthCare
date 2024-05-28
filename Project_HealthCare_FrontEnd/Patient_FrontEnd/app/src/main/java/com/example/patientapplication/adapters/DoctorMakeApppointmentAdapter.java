package com.example.patientapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.patientapplication.R;
import com.example.patientapplication.activity.ChooseDoctorMakeAppointmentActivity;
import com.example.patientapplication.model.Doctor;

import java.util.ArrayList;

public class DoctorMakeApppointmentAdapter extends ArrayAdapter<Doctor> {
    private LayoutInflater inflater;
    private ChooseDoctorMakeAppointmentActivity activity;

    public DoctorMakeApppointmentAdapter(Context context, ArrayList<Doctor> doctors) {
        super(context, 0, doctors);
        inflater = LayoutInflater.from(context);
        if (context instanceof ChooseDoctorMakeAppointmentActivity) {
            activity = (ChooseDoctorMakeAppointmentActivity) context;
        }
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.doctor_item, parent, false);
        }

        Doctor doctor = getItem(position);
        if (doctor != null) {
            TextView doctorNameTextview = convertView.findViewById(R.id.DoctorName);
            TextView DoctorID = convertView.findViewById(R.id.DoctorID);
            Button btnChoose = convertView.findViewById(R.id.btnChoose);

            doctorNameTextview.setText(doctor.getDoctorName());
            DoctorID.setText(doctor.getDoctorID());
            btnChoose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (activity != null) {
                        activity.onDoctorSelected(doctor);
                    }
                }
            });
        }

        return convertView;
    }
}

