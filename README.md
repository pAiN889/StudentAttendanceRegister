# Student Attendance Register

A clean, modern, and intuitive native Android application designed to track and manage student class attendance in real-time. Built using **Java** and traditional **Android XML Layouts**, this project serves as a comprehensive example of native Android UI development, custom `ArrayAdapter` implementations, state-driven summaries, and safe event handling in recycled views.

---

## 📱 Application Overview

The **Student Attendance Register** allows educators and students to register class members, manage individual attendance statuses (Present vs. Absent), view dynamically updated summary statistics, and manage session data safely.

---

## ✨ Features

- 👤 **Student Registration**: Add new students with instant name validation and whitespace trimming.
- 📊 **Dynamic Attendance Summary**: Real-time counter tracking **Total Students**, **Present**, and **Absent** counts, maintaining the strict invariant:
  $$\text{Total} = \text{Present} + \text{Absent}$$
- 🔄 **Interactive Attendance Selection**: Toggle attendance status per student using interactive `Spinner` controls.
- 🏷️ **Visual Status Badges**: High-contrast, color-coded status badges (Green for Present, Red for Absent) providing instant visual feedback.
- 🗑️ **Reset Register with Confirmation**: Option to clear all records with an `AlertDialog` confirmation prompt to prevent accidental data loss.
- 📭 **Empty State View**: Helpful guidance message automatically displayed whenever the student register is empty.
- ⚡ **Recycled View Safe Handling**: Reliable `Spinner` selection callbacks engineered to avoid recursive trigger loops or mismatched states during list scrolling.

---

## 🛠️ Technical Stack & Tools

| Component | Technology |
| :--- | :--- |
| **Language** | Java 11 |
| **UI Toolkit** | Standard Android XML Views (`AppCompatActivity`, `ListView`, `Spinner`, `EditText`, `Button`) |
| **Adapter Pattern** | Custom `ArrayAdapter<Student>` extending standard ListView capabilities |
| **Dialogs & Feedback** | `AlertDialog` for reset prompts & `Toast` messages for status updates |
| **Build System** | Gradle (Kotlin DSL / Version Catalog) |
| **Min SDK / Target SDK** | Min SDK 26 (Android 8.0) / Target SDK 37 |

---

## 📁 Project Structure

```
StudentAttendanceRegister/
├── app/
│   └── src/
│       └── main/
│           ├── java/com/example/studentattendanceregister/
│           │   ├── MainActivity.java         # Screen controller & summary manager
│           │   ├── Student.java              # Student data model
│           │   └── StudentAdapter.java       # Custom adapter for ListView rows
│           └── res/
│               ├── drawable/                 # Custom vector icons & background shapes
│               ├── layout/
│               │   ├── activity_main.xml     # Main application dashboard layout
│               │   └── student_item.xml      # Custom row layout for individual students
│               └── values/
│                   ├── colors.xml            # Application color palette
│                   └── strings.xml           # UI strings & localization
└── README.md
```

---

## 🚀 Getting Started

### Prerequisites
- **Android Studio** (Ladybug / Hedgehog or newer recommended)
- **JDK 11** or higher
- **Android SDK** API Level 26+

### Installation & Build

1. **Clone or Open the Project** in Android Studio:
   ```bash
   git clone https://github.com/your-username/StudentAttendanceRegister.git
   ```
2. **Sync Gradle**: Open the project in Android Studio and allow Gradle to finish synchronizing dependencies.
3. **Run the App**: Select an Android Virtual Device (AVD) or connected physical device running Android 8.0+ and click **Run (Shift + F10)**.

---

## 📖 How to Use

1. **Register a Student**: Enter a student's full name in the input box and tap **Add Student**.
2. **Mark Attendance**: Locate the student in the list and select **Present** or **Absent** from the adjacent dropdown menu.
3. **Monitor Summary**: Observe the summary card update instantly reflecting total, present, and absent counts.
4. **Reset Register**: Tap **Reset Register** at the bottom and confirm in the dialog to start a new attendance session.

---

## 📄 License

This project is open-source and available under the [MIT License](LICENSE).
