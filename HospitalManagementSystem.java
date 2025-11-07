import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {

    private static final String URL = "jdbc:mysql://localhost:3306/hospital_db";
    private static final String USER = "root"; // your MySQL username
    private static final String PASSWORD = "your_password"; // your MySQL password

    private static Connection conn;

    public static void main(String[] args) {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the database!");
            menu();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void menu() {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n=== Hospital Management System ===");
            System.out.println("1. Manage Patients");
            System.out.println("2. Manage Doctors");
            System.out.println("3. Manage Appointments");
            System.out.println("4. Billing");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> managePatients();
                case 2 -> manageDoctors();
                case 3 -> manageAppointments();
                case 4 -> manageBilling();
                case 0 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 0);
    }

    private static void managePatients() {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n--- Patients ---");
            System.out.println("1. Add Patient");
            System.out.println("2. View Patients");
            System.out.println("3. Update Patient");
            System.out.println("4. Delete Patient");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> addPatient();
                case 2 -> viewPatients();
                case 3 -> updatePatient();
                case 4 -> deletePatient();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }

    private static void addPatient() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Age: ");
            int age = sc.nextInt();
            sc.nextLine(); // consume newline
            System.out.print("Enter Gender: ");
            String gender = sc.nextLine();
            System.out.print("Enter Contact: ");
            String contact = sc.nextLine();

            String sql = "INSERT INTO patients (name, age, gender, contact) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.setString(3, gender);
            pstmt.setString(4, contact);

            int rows = pstmt.executeUpdate();
            System.out.println(rows + " patient added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewPatients() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM patients");
            System.out.println("\nID | Name | Age | Gender | Contact");
            while (rs.next()) {
                System.out.printf("%d | %s | %d | %s | %s\n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("contact"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updatePatient() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Patient ID to update: ");
            int id = sc.nextInt();
            sc.nextLine(); // consume newline
            System.out.print("Enter new Name: ");
            String name = sc.nextLine();
            System.out.print("Enter new Age: ");
            int age = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter new Gender: ");
            String gender = sc.nextLine();
            System.out.print("Enter new Contact: ");
            String contact = sc.nextLine();

            String sql = "UPDATE patients SET name=?, age=?, gender=?, contact=? WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.setString(3, gender);
            pstmt.setString(4, contact);
            pstmt.setInt(5, id);

            int rows = pstmt.executeUpdate();
            System.out.println(rows + " patient updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deletePatient() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Patient ID to delete: ");
            int id = sc.nextInt();

            String sql = "DELETE FROM patients WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            int rows = pstmt.executeUpdate();
            System.out.println(rows + " patient deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ----- Doctors CRUD -----
    private static void manageDoctors() {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n--- Doctors ---");
            System.out.println("1. Add Doctor");
            System.out.println("2. View Doctors");
            System.out.println("3. Update Doctor");
            System.out.println("4. Delete Doctor");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> addDoctor();
                case 2 -> viewDoctors();
                case 3 -> updateDoctor();
                case 4 -> deleteDoctor();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }

    private static void addDoctor() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Specialization: ");
            String specialization = sc.nextLine();
            System.out.print("Enter Contact: ");
            String contact = sc.nextLine();

            String sql = "INSERT INTO doctors (name, specialization, contact) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, specialization);
            pstmt.setString(3, contact);

            int rows = pstmt.executeUpdate();
            System.out.println(rows + " doctor added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewDoctors() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM doctors");
            System.out.println("\nID | Name | Specialization | Contact");
            while (rs.next()) {
                System.out.printf("%d | %s | %s | %s\n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("specialization"),
                        rs.getString("contact"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateDoctor() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Doctor ID to update: ");
            int id = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter new Name: ");
            String name = sc.nextLine();
            System.out.print("Enter new Specialization: ");
            String specialization = sc.nextLine();
            System.out.print("Enter new Contact: ");
            String contact = sc.nextLine();

            String sql = "UPDATE doctors SET name=?, specialization=?, contact=? WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, specialization);
            pstmt.setString(3, contact);
            pstmt.setInt(4, id);

            int rows = pstmt.executeUpdate();
            System.out.println(rows + " doctor updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteDoctor() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Doctor ID to delete: ");
            int id = sc.nextInt();

            String sql = "DELETE FROM doctors WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            int rows = pstmt.executeUpdate();
            System.out.println(rows + " doctor deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ----- Appointments CRUD -----
    private static void manageAppointments() {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n--- Appointments ---");
            System.out.println("1. Add Appointment");
            System.out.println("2. View Appointments");
            System.out.println("3. Delete Appointment");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> addAppointment();
                case 2 -> viewAppointments();
                case 3 -> deleteAppointment();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }

    private static void addAppointment() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Patient ID: ");
            int patientId = sc.nextInt();
            System.out.print("Enter Doctor ID: ");
            int doctorId = sc.nextInt();
            System.out.print("Enter Appointment Date (YYYY-MM-DD): ");
            String date = sc.next();

            String sql = "INSERT INTO appointments (patient_id, doctor_id, appointment_date) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, patientId);
            pstmt.setInt(2, doctorId);
            pstmt.setDate(3, Date.valueOf(date));

            int rows = pstmt.executeUpdate();
            System.out.println(rows + " appointment added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewAppointments() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT a.id, p.name AS patient, d.name AS doctor, a.appointment_date " +
                            "FROM appointments a " +
                            "JOIN patients p ON a.patient_id = p.id " +
                            "JOIN doctors d ON a.doctor_id = d.id"
            );
            System.out.println("\nID | Patient | Doctor | Date");
            while (rs.next()) {
                System.out.printf("%d | %s | %s | %s\n",
                        rs.getInt("id"),
                        rs.getString("patient"),
                        rs.getString("doctor"),
                        rs.getDate("appointment_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteAppointment() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Appointment ID to delete: ");
            int id = sc.nextInt();

            String sql = "DELETE FROM appointments WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            int rows = pstmt.executeUpdate();
            System.out.println(rows + " appointment deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ----- Billing -----
    private static void manageBilling() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Patient ID for billing: ");
            int patientId = sc.nextInt();
            System.out.print("Enter Amount: ");
            double amount = sc.nextDouble();

            String sql = "INSERT INTO billing (patient_id, amount, billing_date) VALUES (?, ?, CURDATE())";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, patientId);
            pstmt.setDouble(2, amount);

            int rows = pstmt.executeUpdate();
            System.out.println(rows + " billing record added.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

