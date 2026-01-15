import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainGUI {

    private JFrame frame;

    public MainGUI() {
        frame = new JFrame("Company Fingerprint Access");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        // Welcome Label
        JLabel welcome = new JLabel("Company Fingerprint Access System", SwingConstants.CENTER);
        welcome.setFont(new Font("Arial", Font.BOLD, 18));
        frame.add(welcome, BorderLayout.NORTH);

        // Buttons Panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));

        JButton registerBtn = new JButton("Register Employee");
        JButton enterBtn = new JButton("Enter Company");
        JButton totalBtn = new JButton("Check Total Employees");
        JButton listBtn = new JButton("List Employees");
        JButton exitBtn = new JButton("Exit");

        panel.add(registerBtn);
        panel.add(enterBtn);
        panel.add(totalBtn);
        panel.add(listBtn);
        panel.add(exitBtn);

        frame.add(panel, BorderLayout.CENTER);

        // Action Listeners
        registerBtn.addActionListener(e -> registerMultipleEmployees());
        enterBtn.addActionListener(e -> authenticateEmployee());
        totalBtn.addActionListener(e -> checkTotalEmployees());
        listBtn.addActionListener(e -> listEmployees());
        exitBtn.addActionListener(e -> frame.dispose());

        frame.setVisible(true);
    }

    // ---------------------- MULTIPLE EMPLOYEE REGISTRATION ----------------------
    private void registerMultipleEmployees() {
        String more;
        do {
            JTextField idField = new JTextField();
            JTextField nameField = new JTextField();
            JTextField fingerField = new JTextField();

            Object[] message = {
                    "Employee ID:", idField,
                    "Name:", nameField,
                    "Fingerprint Input:", fingerField
            };

            int option = JOptionPane.showConfirmDialog(frame, message, "Register Employee", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    int id = Integer.parseInt(idField.getText());
                    String name = nameField.getText();
                    String finger = fingerField.getText();

                    EmployeeService.register(id, name, finger);
                    JOptionPane.showMessageDialog(frame, "✅ Employee Registered Successfully");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "❌ Error: " + ex.getMessage());
                }
            } else {
                break; // Cancel clicked
            }

            more = JOptionPane.showInputDialog(frame, "Do you want to register another employee? (yes/no)").toLowerCase();
        } while (more.equals("yes"));
    }

    // ---------------------- AUTHENTICATE EMPLOYEE ----------------------
    private void authenticateEmployee() {
        JTextField idField = new JTextField();
        JTextField fingerField = new JTextField();

        Object[] message = {
                "Employee ID:", idField,
                "Fingerprint Input:", fingerField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Authenticate Employee", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText());
                String finger = fingerField.getText();
                boolean result = EmployeeService.authenticate(id, finger);
                if (result) {
                    JOptionPane.showMessageDialog(frame, "✅ ACCESS GRANTED");
                } else {
                    JOptionPane.showMessageDialog(frame, "❌ ACCESS DENIED");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "❌ Error: " + ex.getMessage());
            }
        }
    }

    // ---------------------- CHECK TOTAL EMPLOYEES ----------------------
    private void checkTotalEmployees() {
        int total = FileDB.countEmployees();
        JOptionPane.showMessageDialog(frame, "Total employees registered: " + total);
    }

    // ---------------------- LIST EMPLOYEES ----------------------
    private void listEmployees() {
        StringBuilder sb = new StringBuilder();
        try {
            java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader("employees.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                sb.append("ID: ").append(data[0]).append(", Name: ").append(data[1]).append("\n");
            }
            br.close();
            JOptionPane.showMessageDialog(frame, sb.toString(), "Registered Employees", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "❌ Error: " + e.getMessage());
        }
    }

    // ---------------------- MAIN ----------------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGUI::new);
    }
}
