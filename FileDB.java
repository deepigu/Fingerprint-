import java.io.*;

public class FileDB {

    static String EMP_FILE = "employees.txt";
    static String LOG_FILE = "entry_logs.txt";

    // Save employee (append mode)
    public static void saveEmployee(int id, String name, String template) {
        try (FileWriter fw = new FileWriter(EMP_FILE, true)) {
            fw.write(id + "," + name + "," + template + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Get fingerprint template by ID
    public static String getTemplate(int id) {
        try (BufferedReader br = new BufferedReader(new FileReader(EMP_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (Integer.parseInt(data[0]) == id) {
                    return data[2];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Log access
    public static void log(int id, String status) {
        try (FileWriter fw = new FileWriter(LOG_FILE, true)) {
            fw.write(id + "," + status + "," + System.currentTimeMillis() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Count employees
    public static int countEmployees() {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(EMP_FILE))) {
            while (br.readLine() != null) {
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    // List all employees
    public static void listEmployees() {
        try (BufferedReader br = new BufferedReader(new FileReader(EMP_FILE))) {
            String line;
            System.out.println("\nRegistered Employees:");
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                System.out.println("ID: " + data[0] + ", Name: " + data[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
