package com.example.patientapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.patientapplication.R;
import com.example.patientapplication.model.Patient;

import java.util.ArrayList;

public class PatientInformationAdapter extends ArrayAdapter<Patient> {
    private LayoutInflater inflater;

    public PatientInformationAdapter(Context context, ArrayList<Patient> patients) {
        super(context, 0, patients);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.appointment_item, parent, false);
        }

        Patient patient = getItem(position);
        if (patient != null) {
            TextView nameTextView = convertView.findViewById(R.id.textView_patient_name);
            TextView idTextView = convertView.findViewById(R.id.textView_patient_id);

            nameTextView.setText(patient.getPatientName());
            idTextView.setText("Patient record number: " + patient.getPatientID());
        }

        return convertView;
    }
}
