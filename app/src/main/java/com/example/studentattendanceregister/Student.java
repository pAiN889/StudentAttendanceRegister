package com.example.studentattendanceregister;

/**
 * Model class representing a student and their attendance status.
 */
public class Student {
    private String name;
    private boolean isPresent;

    /**
     * Constructor for creating a new student record.
     * New students default to Absent (isPresent = false).
     *
     * @param name Full name of the student.
     */
    public Student(String name) {
        this.name = name;
        this.isPresent = false; // Initial status is Absent
    }

    /**
     * Constructor allowing initial attendance status specification.
     *
     * @param name      Full name of the student.
     * @param isPresent Initial attendance status.
     */
    public Student(String name, boolean isPresent) {
        this.name = name;
        this.isPresent = isPresent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }
}
