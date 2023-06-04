package database;

import managers.PasswordManager;
import models.User;
import models.UserRole;
import server.ServerUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс для взаимодействий с таблицей пользователей из базы данных.
 */
public class UserDatabaseManager {
    private final ConnectionManager connectionManager;

    public UserDatabaseManager(String url, String login, String password) {
        connectionManager = new ConnectionManager(url, login, password);
    }

    public UserDatabaseManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public Connection getConnection() throws SQLException {
        return connectionManager.getConnection();
    }

    public int changeUserRole(int userId, UserRole userRole) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE users SET role = ?::user_role " +
                        "WHERE id = ?"
        );

        statement.setString(1, userRole.toString());
        statement.setInt(2, userId);

        int res = statement.executeUpdate();
        connection.close();
        return res;
    }

    public int addUser(User user) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO USERS(name, password_digest, salt, role)" +
                        "VALUES (?, ?, ?, 'USER_MIN') RETURNING id");

        statement.setString(1, user.getName());
        String salt = PasswordManager.getSalt();
        statement.setString(2, PasswordManager.getHash(user.getPassword(), salt));
        statement.setString(3, salt);

        ResultSet result = statement.executeQuery();

        connection.close();

        result.next();

        return result.getInt(1);
    }

    public ServerUser getUser(int id) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM users WHERE id = ?");

        statement.setInt(1, id);

        ResultSet result = statement.executeQuery();

        connection.close();

        result.next();

        String name = result.getString("name");
        String password_digest = result.getString("password_digest");
        String salt = result.getString("salt");
        String role = result.getString("role");


        return new ServerUser(id, name, password_digest, salt, UserRole.valueOf(role));
    }

    public ServerUser getUser(String name) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM users WHERE name = ?");

        statement.setString(1, name);

        ResultSet result = statement.executeQuery();

        connection.close();

        result.next();

        int id = result.getInt("id");
        String password_digest = result.getString("password_digest");
        String salt = result.getString("salt");
        String role = result.getString("role");


        return new ServerUser(id, name, password_digest, salt, UserRole.valueOf(role));
    }

    public ServerUser getUser(String name, String password) throws SQLException {
        ServerUser user = getUser(name);
        String salt = user.getSalt();
        String password_digest = PasswordManager.getHash(password, salt);
        if (password_digest.equals(user.getPasswordDigest())) {
            return user;
        }
        return null;
    }


    public String getUserSalt(String name) throws SQLException {
        ServerUser user = getUser(name);

        return user.getSalt();
    }

    public int getUserId(String name) throws SQLException {
        ServerUser user = getUser(name);
        return user.getId();
    }

    public UserRole getUserRole(String name) throws SQLException {
        ServerUser user = getUser(name);

        return user.getRole();
    }

    public boolean isAdmin(int id) {
        try {
            ServerUser user = getUser(id);
            return user.getRole().equals(UserRole.ADMIN);
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean checkUserName(String name) {
        try {
            return getUser(name) != null;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean checkUserId(int id) {
        try {
            return getUser(id) != null;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean checkUserPass(String name, String password) {
        try {
            return getUser(name, password) != null;
        } catch (SQLException e) {
            return false;
        }
    }

}
