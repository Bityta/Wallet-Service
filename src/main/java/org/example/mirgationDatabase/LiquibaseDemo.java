package org.example.mirgationDatabase;


import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.example.utils.ConnectionManager;
import org.example.utils.PropertiesUtil;


import java.sql.Connection;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * Класс миграции База данных
 */

public class LiquibaseDemo {

    /**
     * Ключ из файла application.properties
     */
    private static final String PATCH_KEY = "lb.patch";

    /**
     * Экземпляр класса
     */
    private static LiquibaseDemo liquibaseDemo;

    /**
     * Приватный конструктор.
     */
    private LiquibaseDemo() {

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

            connection = ConnectionManager.open();
            connection.setAutoCommit(false);

            final String sql = "CREATE SCHEMA IF NOT EXISTS Service";
            Statement statement = connection.createStatement();
            statement.execute(sql);
            connection.commit();
            System.out.println("Действие успешно завершено");


        } catch (SQLException e) {
            System.err.println("Произошла ошибка " + e.getMessage());

            try {
                connection.rollback();
                System.err.println("Действие отменено");

            } catch (SQLException ex) {
                System.err.println("Ошибка при откате действия");
            }

        } finally {
            try {
                if (connection != null) {


                    Database database = null;
                    try {
                        database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
                        database.setLiquibaseSchemaName("service");
                        Liquibase liquibase = new Liquibase(PropertiesUtil.get(PATCH_KEY), new ClassLoaderResourceAccessor(), database);

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
