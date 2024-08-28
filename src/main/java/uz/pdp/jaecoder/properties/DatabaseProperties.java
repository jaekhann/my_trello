package uz.pdp.jaecoder.properties;

import java.util.ResourceBundle;

public class DatabaseProperties {
    private static ResourceBundle settings;
    public static String url;
    public static String username;
    public static String password;

    static {
        settings = ResourceBundle.getBundle("settings");
        url = settings.getString("database.url");
        username = settings.getString("database.username");
        password = settings.getString("database.password");
    }
}
