import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {

    private static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hospital_db", "root", "your_password");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void addPatient(Scanner sc) {
        System.out.print("Name: "); String name = sc.nextLine();
        System.out.print("Age: "); int age = sc.nextInt(); sc.nextLine();
        System.out.print("Gender: "); String gender = sc.nextLine();
        System.out.print("Contact: "); String contact = sc.nextLine();
        System.out.print("Address: "); String address = sc.nextLine();
        System.out.print("Disease: "); String disease = sc.nextLine();
        String sql = "INSERT INTO patients(name, age, gender, contact, address, disease) VALUES(?,?,?,?,?,?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.setString(3, gender);
            pstmt.setString(4, contact);
            pstmt.setString(5, address);
            pstmt.setString(6, disease);
            pstmt.executeUpdate();
            System.out.println("Patient added successfully!\n");
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private static void viewPatients() {
        String sql = "SELECT * FROM patients";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("---- Patients ----");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("patient_id") +
                                   ", Name: " + rs.getString("name") +
                                   ", Age: " + rs.getInt("age") +
                                   ", Gender: " + rs.getString("gender") +
                                   ", Contact: " + rs.getString("contact") +
                                   ", Disease: " + rs.getString("disease"));
            }
            System.out.println();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.println("=== Hospital Management System ===");
            System.out.println("1. Add Patient");
            System.out.println("2. View Patients");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt(); sc.nextLine();
            switch(choice) {
                case 1: addPatient(sc); break;
                case 2: viewPatients(); break;
                case 3: System.exit(0);
                default: System.out.println("Invalid choice!");
            }
        }
    }
}
