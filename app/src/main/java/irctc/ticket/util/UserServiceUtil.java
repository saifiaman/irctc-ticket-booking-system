package irctc.ticket.util;

import org.mindrot.jbcrypt.BCrypt;

public class UserServiceUtil {
    public static String hashPassword(String plainPassword) {
        // Implement password hashing logic here
        // This is a placeholder for actual hashing logic
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        // Implement password checking logic here
        // This is a placeholder for actual password checking logic
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

}
