import java.util.HashMap;

public class UserDatabase {
    private static HashMap<String,String> users = new HashMap<>();

    public static boolean userExists(String u) {
        return users.containsKey(u);
    }

    public static void addUser(String u, String p) {
        users.put(u,p);
    }

    public static boolean validateLogin(String u, String p) {
        return users.containsKey(u) && users.get(u).equals(p);
    }
}
