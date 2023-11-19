package org.example.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.*;

public class JdbcTest {

    private static PostgreSQLContainer<?> postgresContainer;
    private static Connection connection;

    @BeforeAll
    public static void setUp() throws SQLException {
        postgresContainer = new PostgreSQLContainer<>("postgres:latest");
        postgresContainer.start();

        String jdbcUrl = postgresContainer.getJdbcUrl();
        String username = postgresContainer.getUsername();
        String password = postgresContainer.getPassword();


        connection = DriverManager.getConnection(jdbcUrl, username, password);

    }

    @AfterAll
    public static void tearDown() throws SQLException {
        connection.close();
        postgresContainer.stop();
    }

    @Test
    public void testDatabaseConnection() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT 1");

        while (resultSet.next()) {
            int result = resultSet.getInt(1);
            System.out.println(result);
        }

        resultSet.close();
        statement.close();
    }

    //ToDo


}