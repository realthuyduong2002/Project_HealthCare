package com.example.patientapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.patientapplication.R;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {

    private final ArrayList<String> daysOfMonth;
    private final OnItemListener onItemListener;
    private final int currentDayOfMonth; // Store the current day of the month

    public CalendarAdapter(ArrayList<String> daysOfMonth, OnItemListener onItemListener) {
        this.daysOfMonth = daysOfMonth;
        this.onItemListener = onItemListener;
        this.currentDayOfMonth = LocalDate.now().getDayOfMonth(); // Get the current day of the month
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.166666666); // Adjust height for each cell to fit evenly in a 6-row grid
        return new CalendarViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        String dayText = daysOfMonth.get(position);
        holder.dayOfMonths.setText(dayText);

        // Disable the view for days before the current day
        if (!dayText.isEmpty() && Integer.parseInt(dayText) < currentDayOfMonth) {
            holder.itemView.setEnabled(false);
            holder.itemView.setAlpha(0.5f); // Dim the text to indicate it's disabled
        } else {
            holder.itemView.setEnabled(true);
            holder.itemView.setAlpha(1f); // Reset alpha for enabled days
        }
    }

    @Override
    public int getItemCount() {
        return daysOfMonth.size();
    }

    public interface OnItemListener {
        void onItemClick(int position, String dayText);
    }
}
