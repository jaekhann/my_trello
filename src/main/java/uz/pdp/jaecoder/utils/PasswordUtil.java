package uz.pdp.jaecoder.utils;

import org.mindrot.jbcrypt.BCrypt;

import java.util.Objects;

public final class PasswordUtil {
    private PasswordUtil() {
        throw new IllegalStateException("Utility Class");
    }

    public static String hash(String password) {
        Objects.requireNonNull(password, "password cannot be null");
        return BCrypt.hashpw(password, BCrypt.gensalt(16));
    }

    public static boolean checkPassword(String rawPassword, String hashedPassword) {
        Objects.requireNonNull(rawPassword, "password cannot be null");
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }
}
