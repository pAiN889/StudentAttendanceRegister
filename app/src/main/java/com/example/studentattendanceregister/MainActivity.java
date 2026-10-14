package com.example.studentattendanceregister;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Main Activity for managing the Student Attendance Register application.
 */
public class MainActivity extends AppCompatActivity {

    private EditText etStudentName;
    private Button btnAddStudent;
    private TextView tvTotalCount;
    private TextView tvPresentCount;
    private TextView tvAbsentCount;
    private ListView lvStudents;
    private TextView tvEmptyList;
    private Button btnResetRegister;

    private List<Student> studentList;
    private StudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Ensure status bar icons (clock, battery, signal) are dark for light background legibility
        WindowInsetsControllerCompat controller = WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        if (controller != null) {
            controller.setAppearanceLightStatusBars(true);
        }

        setContentView(R.layout.activity_main);

        // Apply system bar insets + layout margin padding for clean edge spacing
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            int contentPadding = (int) (16 * getResources().getDisplayMetrics().density);
            v.setPadding(
                    systemBars.left + contentPadding,
                    systemBars.top + contentPadding,
                    systemBars.right + contentPadding,
                    systemBars.bottom + contentPadding
            );
            return insets;
        });

        // Initialize UI Views
        initViews();

        // Initialize Data and Adapter
        studentList = new ArrayList<>();
        adapter = new StudentAdapter(this, studentList, this::updateSummary);

        lvStudents.setAdapter(adapter);
        lvStudents.setEmptyView(tvEmptyList);

        // Event Listeners
        btnAddStudent.setOnClickListener(v -> addStudent());
        btnResetRegister.setOnClickListener(v -> confirmResetRegister());

        // Initial summary display
        updateSummary();
    }

    /**
     * Finds and binds view references from layout.
     */
    private void initViews() {
        etStudentName = findViewById(R.id.etStudentName);
        btnAddStudent = findViewById(R.id.btnAddStudent);
        tvTotalCount = findViewById(R.id.tvTotalCount);
        tvPresentCount = findViewById(R.id.tvPresentCount);
        tvAbsentCount = findViewById(R.id.tvAbsentCount);
        lvStudents = findViewById(R.id.lvStudents);
        tvEmptyList = findViewById(R.id.tvEmptyList);
        btnResetRegister = findViewById(R.id.btnResetRegister);
    }

    /**
     * Reads student name input, validates, creates new Student object,
     * updates adapter, clears input, and displays Toast feedback.
     */
    private void addStudent() {
        String nameInput = etStudentName.getText().toString().trim();

        if (nameInput.isEmpty()) {
            Toast.makeText(this, getString(R.string.toast_enter_name), Toast.LENGTH_SHORT).show();
            return;
        }

        // New student initially defaults to Absent
        Student student = new Student(nameInput);
        studentList.add(student);

        adapter.notifyDataSetChanged();
        updateSummary();

        etStudentName.setText("");
        Toast.makeText(this, getString(R.string.toast_student_added), Toast.LENGTH_SHORT).show();
    }

    /**
     * Recalculates total, present, and absent student counts and updates UI elements.
     * Guarantees invariant: Total = Present + Absent.
     */
    private void updateSummary() {
        int total = studentList.size();
        int presentCount = 0;

        for (Student student : studentList) {
            if (student.isPresent()) {
                presentCount++;
            }
        }

        int absentCount = total - presentCount;

        tvTotalCount.setText(String.valueOf(total));
        tvPresentCount.setText(String.valueOf(presentCount));
        tvAbsentCount.setText(String.valueOf(absentCount));
    }

    /**
     * Displays confirmation AlertDialog before resetting student register.
     */
    private void confirmResetRegister() {
        if (studentList.isEmpty()) {
            Toast.makeText(this, getString(R.string.toast_register_empty), Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle(R.string.dialog_reset_title)
                .setMessage(R.string.dialog_reset_message)
                .setPositiveButton(R.string.dialog_reset, (dialog, which) -> resetRegister())
                .setNegativeButton(R.string.dialog_cancel, (dialog, which) -> dialog.dismiss())
                .show();
    }

    /**
     * Clears all student records, notifies adapter, and refreshes summary.
     */
    private void resetRegister() {
        studentList.clear();
        adapter.notifyDataSetChanged();
        updateSummary();
    }
}
