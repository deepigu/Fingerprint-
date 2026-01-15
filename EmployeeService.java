public class EmployeeService {

    // Register new employee
    public static void register(int id, String name, String fingerInput) {
        String template = FingerprintUtil.generateTemplate(fingerInput);
        FileDB.saveEmployee(id, name, template);
        System.out.println("✅ Employee Registered Successfully");
    }

    // Authenticate employee
    public static boolean authenticate(int id, String fingerInput) {
        String liveTemplate = FingerprintUtil.generateTemplate(fingerInput);
        String savedTemplate = FileDB.getTemplate(id);

        if (savedTemplate != null && savedTemplate.equals(liveTemplate)) {
            FileDB.log(id, "ACCESS_GRANTED");
            return true;
        } else {
            FileDB.log(id, "ACCESS_DENIED");
            return false;
        }
    }
}
