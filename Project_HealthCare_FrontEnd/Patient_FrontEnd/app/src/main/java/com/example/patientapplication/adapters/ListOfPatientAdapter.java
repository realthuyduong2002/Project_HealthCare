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

public class ListOfPatientAdapter extends ArrayAdapter<Patient> {
    private LayoutInflater inflater;

    public ListOfPatientAdapter(Context context, ArrayList<Patient> patients) {
        super(context, 0, patients);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.patient_item, parent, false);
        }

        Patient patient = getItem(position);
        if (patient != null) {
            TextView nameTextView = convertView.findViewById(R.id.tvPatientName);

            nameTextView.setText(patient.getPatientName());
        }

        return convertView;
    }
}
