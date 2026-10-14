package com.example.studentattendanceregister;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.List;

/**
 * Custom ArrayAdapter for displaying Student items in a ListView with a Spinner attendance selector.
 */
public class StudentAdapter extends ArrayAdapter<Student> {

    /**
     * Interface to notify parent activity when any student's attendance status changes.
     */
    public interface OnAttendanceChangeListener {
        void onAttendanceChanged();
    }

    private final Context context;
    private final List<Student> students;
    private final OnAttendanceChangeListener listener;

    public StudentAdapter(@NonNull Context context, @NonNull List<Student> students, OnAttendanceChangeListener listener) {
        super(context, 0, students);
        this.context = context;
        this.students = students;
        this.listener = listener;
    }

    private static class ViewHolder {
        TextView tvStudentName;
        TextView tvStatusBadge;
        Spinner spinnerAttendance;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        View itemView = convertView;

        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.student_item, parent, false);
            holder = new ViewHolder();
            holder.tvStudentName = itemView.findViewById(R.id.tvStudentName);
            holder.tvStatusBadge = itemView.findViewById(R.id.tvStatusBadge);
            holder.spinnerAttendance = itemView.findViewById(R.id.spinnerAttendance);

            // Populate Spinner options ("Present" at index 0, "Absent" at index 1)
            String[] options = new String[]{
                    context.getString(R.string.status_present),
                    context.getString(R.string.status_absent)
            };
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                    context,
                    R.layout.spinner_item,
                    options
            );
            spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
            holder.spinnerAttendance.setAdapter(spinnerAdapter);

            itemView.setTag(holder);
        } else {
            holder = (ViewHolder) itemView.getTag();
        }

        if (position < 0 || position >= students.size()) {
            return itemView;
        }

        Student student = students.get(position);
        holder.tvStudentName.setText(student.getName());

        // Update visual status badge styling
        updateStatusBadge(holder.tvStatusBadge, student.isPresent());

        // CRITICAL: Prevent recursive / misfire callbacks during view binding / recycling
        holder.spinnerAttendance.setOnItemSelectedListener(null);

        // Map status: 0 -> Present, 1 -> Absent
        int targetSelection = student.isPresent() ? 0 : 1;
        holder.spinnerAttendance.setSelection(targetSelection, false);

        // Attach new listener after setting selection
        holder.spinnerAttendance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int selectedPosition, long id) {
                boolean newIsPresent = (selectedPosition == 0);

                // Only update and trigger callbacks if status actually changed
                if (student.isPresent() != newIsPresent) {
                    student.setPresent(newIsPresent);
                    updateStatusBadge(holder.tvStatusBadge, newIsPresent);

                    if (listener != null) {
                        listener.onAttendanceChanged();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No action needed
            }
        });

        return itemView;
    }

    /**
     * Helper method to update the visual status badge styling (colors, text, background).
     */
    private void updateStatusBadge(TextView tvStatusBadge, boolean isPresent) {
        if (isPresent) {
            tvStatusBadge.setText(R.string.status_present);
            tvStatusBadge.setTextColor(ContextCompat.getColor(context, R.color.status_present));
            tvStatusBadge.setBackgroundResource(R.drawable.bg_status_present);
        } else {
            tvStatusBadge.setText(R.string.status_absent);
            tvStatusBadge.setTextColor(ContextCompat.getColor(context, R.color.status_absent));
            tvStatusBadge.setBackgroundResource(R.drawable.bg_status_absent);
        }
    }
}
