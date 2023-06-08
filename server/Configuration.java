package server;

public class Configuration {
    private static String dbUrl;   // url-адрес для подключения к бд
    private static String dbLogin; // логин для подключения к бд
    private static String dbPass;  //пароль для подключения

    public static String getDbUrl() {
        return dbUrl;
    }

    public static void setDbUrl(String dbUrl) {
        Configuration.dbUrl = dbUrl;
    }

    public static String getDbLogin() {
        return dbLogin;
    }

    public static void setDbLogin(String dbLogin) {
        Configuration.dbLogin = dbLogin;
    }

    public static String getDbPass() {
        return dbPass;
    }

    public static void setDbPass(String dbPass) {
        Configuration.dbPass = dbPass;
    }
}