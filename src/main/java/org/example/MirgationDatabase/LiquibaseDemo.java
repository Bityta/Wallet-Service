package org.example.MirgationDatabase;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Класс миграции База данных
 */

public class LiquibaseDemo {

    /**
     * Переменная содержащая URL - Базы Данных
     */
    private final String URL;

    /**
     * Переменная содержащая Имя Базы Данных
     */
    private final String USERNAME;

    /**
     * Переменная содержащая Пароль Базы Данных
     */
    private final String PASSWORD;

    /**
     * Путь до ChangeLog
     */
    private final String PATH;

    /**
     * Экземпляр класса
     */
    private static LiquibaseDemo liquibaseDemo;

    /**
     * Приватный конструктор.
     * При создание экземпляра класа, без значения URL, USERNAME, PASSWORD и PATH
     * из applications.properties
     * При отсутвсие нужных полей - выскакивает ошибка.
     */
    private LiquibaseDemo() {

        final String path = "src/main/resources/application.properties";

        Properties property = new Properties();

        try (FileInputStream file = new FileInputStream(path)) {
            property.load(file);
            URL = property.getProperty("URL") + property.getProperty("NAME");
            USERNAME = property.getProperty("USERNAME");
            PASSWORD = property.getProperty("PASSWORD");
            PATH = property.getProperty("PATH");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Получения экземпляра данного класса. (Singleton)
     *
     * @return Экземпляр класса.
     */
    public static LiquibaseDemo getLiquibase() {
        if (liquibaseDemo == null) {
            liquibaseDemo = new LiquibaseDemo();
        }
        return liquibaseDemo;
    }

    /**
     * произвести миграцию
     */
    public void migration() {

        Connection connection = null;

        try {

            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            connection.setAutoCommit(false);

            final String sql = "CREATE SCHEMA IF NOT EXISTS Service";
            Statement statement = connection.createStatement();
            statement.execute(sql);
            connection.commit();
            System.out.println("Действие успешно завершена.");


        } catch (SQLException e) {
            System.err.println("Произошла ошибка " + e.getMessage());

            if (connection != null) {

                try {
                    connection.rollback();
                    System.err.println("Действие отменено");

                } catch (SQLException ex) {
                    System.err.println("Ошибка при откате действия");
                }

            }

        } finally {
            try {
                if (connection != null) {


                    Database database = null;
                    try {
                        database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
                        database.setLiquibaseSchemaName("service");
                        Liquibase liquibase = new Liquibase(PATH, new ClassLoaderResourceAccessor(), database);
                        liquibase.update();
                        connection.close();
                    } catch (LiquibaseException e) {
                        throw new RuntimeException(e);
                    }
                }
            } catch (SQLException closeException) {
                System.err.println("Ошибка при закрытии соединения: " + closeException.getMessage());
            }
        }


    }

}
