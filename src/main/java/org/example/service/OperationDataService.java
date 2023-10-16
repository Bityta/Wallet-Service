package org.example.service;

import lombok.Getter;
import org.example.entity.Person;
import org.example.repository.OperationDataRepository;
import org.example.utils.Operation;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;

/**
 * Сервис,который взаимодействует с данными всех операций, реализующий интерфей OperationDataRepository.
 */
@Getter
public class OperationDataService implements OperationDataRepository {

    /**
     * Сервис таблицы с людьми.
     */
    private final PersonDataService personDataService = PersonDataService.getPersonDataService();

    /**
     * Экземпляр класса.
     */
    private static OperationDataService operationDataService;

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
     * Приватный конструктор.
     * При создание экземпляра класа, без значения URL, USERNAME и PASSWORD
     * из applications.properties
     * При отсутвсие нужных полей - выскакивает ошибка.
     */
    private OperationDataService() {
        final String path = "src/main/resources/application.properties";

        Properties property = new Properties();

        try (FileInputStream file = new FileInputStream(path)) {
            property.load(file);
            URL = property.getProperty("URL");
            USERNAME = property.getProperty("USERNAME");
            PASSWORD = property.getProperty("PASSWORD");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Получения экземпляра данного класса. (Singleton)
     *
     * @return Экземпляр класса.
     */
    public static OperationDataService getOperationDataService() {
        if (operationDataService == null) {
            operationDataService = new OperationDataService();
        }
        return operationDataService;
    }

    /**
     * Реалезация класса, добавляющий операцию в базу данных.
     *
     * @param person    Пользователь.
     * @param operation Операция.
     * @param money     Сумма операции.
     */
    @Override
    public void addOperation(Person person, Operation operation, double money) {

        final String sql = "INSERT INTO WallerService.operation(id, idperson, operation, money)"
                + "VALUES(?,?,?,?)";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(UUID.randomUUID()));
            preparedStatement.setLong(2, personDataService.getPersonId(person).get());
            preparedStatement.setString(3, String.valueOf(operation));
            preparedStatement.setDouble(4, money);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("SQL Exception " + e.getMessage());
            throw new RuntimeException();
        }

        switch (operation) {
            case credit, replenishment -> {
                person.addMoney(money);
                personDataService.updatePerson(person);
            }
            case withdraw -> {
                person.diffMoney(money);
                personDataService.updatePerson(person);
            }

        }
    }


    /**
     * Вывод всех операции
     * @param person - пользователь
     */
    @Override
    public void printOperation(Person person) {

        final String sql = "SELECT * FROM WallerService.operation WHERE idperson=?";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {


            long id = personDataService.getPersonId(person).get();

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {

                System.out.println(resultSet.getString(1) + " " + resultSet.getString(3) + " " + resultSet.getDouble(4));


            }


        } catch (SQLException e) {
            System.out.println("SQL Exception " + e.getMessage());
            throw new RuntimeException();
        }

    }
}
