import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {

    static final String DB_URL = "jdbc:mysql://localhost:3306/hospital_db";
    static final String USER = "root";
    static final String PASS = "password";
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            System.out.println("Connected to the database!");

            while (true) {
                System.out.println("\n--- Hospital Management System ---");
                System.out.println("1. Add Patient");
                System.out.println("2. Add Doctor");
                System.out.println("3. Book Appointment");
                System.out.println("4. Generate Bill");
                System.out.println("5. View Patients");
                System.out.println("6. View Doctors");
                System.out.println("7. View Appointments");
                System.out.println("8. View Bills");
                System.out.println("9. Exit");
                System.out.print("Enter your choice: ");
                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1 -> addPatient(conn);
                    case 2 -> addDoctor(conn);
                    case 3 -> bookAppointment(conn);
                    case 4 -> generateBill(conn);
                    case 5 -> viewPatients(conn);
                    case 6 -> viewDoctors(conn);
                    case 7 -> viewAppointments(conn);
                    case 8 -> viewBills(conn);
                    case 9 -> {
                        System.out.println("Exiting...");
                        return;
                    }
                    default -> System.out.println("Invalid choice!");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add Patient
    public static void addPatient(Connection conn) throws SQLException {
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("DOB (yyyy-mm-dd): ");
        String dob = sc.nextLine();
        System.out.print("Gender: ");
        String gender = sc.nextLine();
        System.out.print("Contact: ");
        String contact = sc.nextLine();
        System.out.print("Address: ");
        String address = sc.nextLine();

        String sql = "INSERT INTO patients (name, dob, gender, contact, address) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, dob);
            pstmt.setString(3, gender);
            pstmt.setString(4, contact);
            pstmt.setString(5, address);
            pstmt.executeUpdate();
            System.out.println("Patient added successfully!");
        }
    }

    // Add Doctor
    public static void addDoctor(Connection conn) throws SQLException {
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Specialization: ");
        String specialization = sc.nextLine();
        System.out.print("Contact: ");
        String contact = sc.nextLine();

        String sql = "INSERT INTO doctors (name, specialization, contact) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, specialization);
            pstmt.setString(3, contact);
            pstmt.executeUpdate();
            System.out.println("Doctor added successfully!");
        }
    }

    // Book Appointment
    public static void bookAppointment(Connection conn) throws SQLException {
        System.out.print("Patient ID: ");
        int patientId = Integer.parseInt(sc.nextLine());
        System.out.print("Doctor ID: ");
        int doctorId = Integer.parseInt(sc.nextLine());
        System.out.print("Appointment Date (yyyy-mm-dd hh:mm:ss): ");
        String date = sc.nextLine();

        String sql = "INSERT INTO appointments (patient_id, doctor_id, appointment_date) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, patientId);
            pstmt.setInt(2, doctorId);
            pstmt.setString(3, date);
            pstmt.executeUpdate();
            System.out.println("Appointment booked successfully!");
        }
    }

    // Generate Bill
    public static void generateBill(Connection conn) throws SQLException {
        System.out.print("Patient ID: ");
        int patientId = Integer.parseInt(sc.nextLine());
        System.out.print("Amount: ");
        double amount = Double.parseDouble(sc.nextLine());
        String status = "Paid";
        Timestamp billDate = new Timestamp(System.currentTimeMillis());

        String sql = "INSERT INTO billing (patient_id, amount, status, bill_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, patientId);
            pstmt.setDouble(2, amount);
            pstmt.setString(3, status);
            pstmt.setTimestamp(4, billDate);
            pstmt.executeUpdate();
            System.out.println("Bill generated successfully!");
        }
    }

    // View Patients
    public static void viewPatients(Connection conn) throws SQLException {
        String sql = "SELECT * FROM patients";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\n--- Patients ---");
            while (rs.next()) {
                System.out.printf("%d | %s | %s | %s | %s | %s\n",
                        rs.getInt("patient_id"),
                        rs.getString("name"),
                        rs.getDate("dob"),
                        rs.getString("gender"),
                        rs.getString("contact"),
                        rs.getString("address"));
            }
        }
    }

    // View Doctors
    public static void viewDoctors(Connection conn) throws SQLException {
        String sql = "SELECT * FROM doctors";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\n--- Doctors ---");
            while (rs.next()) {
                System.out.printf("%d | %s | %s | %s\n",
                        rs.getInt("doctor_id"),
                        rs.getString("name"),
                        rs.getString("specialization"),
                        rs.getString("contact"));
            }
        }
    }

    // View Appointments
    public static void viewAppointments(Connection conn) throws SQLException {
        String sql = "SELECT a.appointment_id, p.name AS patient, d.name AS doctor, a.appointment_date " +
                     "FROM appointments a " +
                     "JOIN patients p ON a.patient_id = p.patient_id " +
                     "JOIN doctors d ON a.doctor_id = d.doctor_id";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\n--- Appointments ---");
            while (rs.next()) {
                System.out.printf("%d | Patient: %s | Doctor: %s | Date: %s\n",
                        rs.getInt("appointment_id"),
                        rs.getString("patient"),
                        rs.getString("doctor"),
                        rs.getTimestamp("appointment_date"));
            }
        }
    }

    // View Bills
    public static void viewBills(Connection conn) throws SQLException {
        String sql = "SELECT b.bill_id, p.name AS patient, b.amount, b.status, b.bill_date " +
                     "FROM billing b " +
                     "JOIN patients p ON b.patient_id = p.patient_id";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\n--- Bills ---");
            while (rs.next()) {
                System.out.printf("%d | Patient: %s | Amount: %.2f | Status: %s | Date: %s\n",
                        rs.getInt("bill_id"),
                        rs.getString("patient"),
                        rs.getDouble("amount"),
                        rs.getString("status"),
                        rs.getTimestamp("bill_date"));
            }
        }
    }
}
