package database;
import consoles.Console;
import consoles.StandardConsole;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Класс для работы с ddl-запросами по типу создания таблиц и удаления таблиц.
 */
public class DDLManager {
    private final ConnectionManager connectionManager;
    Console console = new StandardConsole();

    public DDLManager(String url, String login, String password) {
        connectionManager = new ConnectionManager(url, login, password);
    }

    public Connection getConnection() throws SQLException {
        return connectionManager.getConnection();
    }
    public void dropTables() throws SQLException {
        String query = """
                BEGIN;
                
                DROP TABLE IF EXISTS labels;
                DROP TABLE IF EXISTS musicbands;
                DROP TYPE IF EXISTS genre;            
                DROP TABLE IF EXISTS users;
                DROP TABLE IF EXISTS commands;
                                
                END;
                """;
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
        connection.close();
        console.write("Таблицы удалены");
    }

    public void createTables() throws SQLException {
        String query = """
                BEGIN;
                                
                CREATE TYPE genre AS ENUM (
                    'RAP', 'SOUL', 'POP', 'PHONK', 'PROGRESSIVE_ROCK'
                );
                               
                                
                CREATE TABLE IF NOT EXISTS users
                (
                    id              SERIAL PRIMARY KEY,
                    name            VARCHAR(40) UNIQUE      NOT NULL,
                    password_digest VARCHAR(96)             NOT NULL,
                    salt            VARCHAR(10)             NOT NULL,
                    role            VARCHAR(25)             NOT NULL,
                    creation_date   TIMESTAMP DEFAULT NOW() NOT NULL
                );
                                
                CREATE TABLE IF NOT EXISTS commands
                (
                    id            SERIAL PRIMARY KEY,
                    name          VARCHAR(40) UNIQUE NOT NULL,
                    min_user_role VARCHAR(40) NOT NULL
                );
                                
                CREATE TABLE IF NOT EXISTS labels
                (
                  
                    sales     INT NOT NULL
                        CONSTRAINT positive_sales CHECK (sales > 0)
                );
                                
                CREATE TABLE IF NOT EXISTS musicbands
                (
                    id            SERIAL PRIMARY KEY,
                    name          TEXT                    NOT NULL
                        CONSTRAINT not_empty_name CHECK (length(name) > 0),
                    x             INTEGER                 NOT NULL,
                    y             INTEGER                 NOT NULL,
                    creation_date TIMESTAMP DEFAULT NOW() NOT NULL,
                    single_count  INT  NOT NULL,
                    sales        INT,
                    number_of_participants  INT          NOT NULL
                        CONSTRAINT positive_sales CHECK (sales> 0),
                    genre          genre,
                    creator_id    INT                     NOT NULL REFERENCES users (id) ON DELETE CASCADE
                );
                                
                END;""";
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
        connection.close();
        console.write("Таблицы созданы");
    }
}