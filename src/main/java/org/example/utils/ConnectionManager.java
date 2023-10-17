package org.example.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Класс, который обеспечивает подключение к БД
 */
public final class ConnectionManager {

    /**
     * Пароль-ключ по которому храниться значение в конфиг файле
     */
    private static final String PASSWORD_KEY = "db.password";

    /**
     * Название-ключ по которому храниться значение в конфиг файле
     */
    private static final String USERNAME_KEY = "db.username";

    /**
     * URL-ключ по которому храниться значение в конфиг файле
     */
    private static final String URL_KEY = "db.url";

    static {
        loadDriver();
    }

    /**
     * Приватный конструктор
     */
    private ConnectionManager() {
    }

    /**
     * Выполнение подлючения к БД
     *
     * @return connection
     */
    public static Connection open() {
        try {
            return DriverManager.getConnection(
                    PropertiesUtil.get(URL_KEY),
                    PropertiesUtil.get(USERNAME_KEY),
                    PropertiesUtil.get(PASSWORD_KEY)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Подгрузка драйвера PostgreSQL
     */
    private static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}