package com.example.doctorapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctorapplication.R;
import com.example.doctorapplication.model.Appointment;
import com.example.doctorapplication.utils.AppUtils;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {
    private List<Appointment> appointments;
    private Context context;
    private AppointmentAdapter.OnClick onClick;

    public AppointmentAdapter(Context context, List<Appointment> appointments, AppointmentAdapter.OnClick onClick) {
        this.appointments = appointments;
        this.context = context;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public AppointmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appointment_hour, parent, false);
        final AppointmentAdapter.ViewHolder holder = new AppointmentAdapter.ViewHolder(itemView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onClickItem(appointments.get(holder.getLayoutPosition()));
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentAdapter.ViewHolder holder, int position) {
        Appointment appointment = appointments.get(position);
        holder.tvAppointHour.setText(AppUtils.formatDate(appointment.getAppointmentTime(), "hh:mm:ss", "hh:mm"));
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvAppointHour;

        ViewHolder(View itemView) {
            super(itemView);
            tvAppointHour = itemView.findViewById(R.id.tvAppointHour);
        }
    }

    public interface OnClick {
        void onClickItem(Appointment appointment);
    }
}