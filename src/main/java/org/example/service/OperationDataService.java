package org.example.service;


import lombok.Getter;
import lombok.Setter;
import org.example.entity.Person;
import org.example.repository.OperationDataRepository;
import org.example.utils.ConnectionManager;
import org.example.utils.Operation;

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
     * Экземпляр класса, хранящие URL, USERNAME, PASSWORD Базы данных
     */
   

    /**
     * Приватный конструктор.
     */
    private OperationDataService() {

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

        final String sql = "INSERT INTO WallerService.operation(id, idperson, operation, money)" + "VALUES(?,?,?,?)";

        Connection connection = null;
        try {

            connection = ConnectionManager.open();
            connection.setAutoCommit(false);

            Optional<Long> sendPerson = personDataService.getPersonId(person);

            if (sendPerson.isEmpty()) {
                System.err.println("Пользователь не найден");
                return;
            }

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(UUID.randomUUID()));
            preparedStatement.setLong(2, sendPerson.get());
            preparedStatement.setString(3, String.valueOf(operation));
            preparedStatement.setDouble(4, money);

            preparedStatement.executeUpdate();

            connection.commit();
            System.out.println("\nДействие успешно завершено.");

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
                    connection.close();

                }
            } catch (SQLException closeException) {
                System.err.println("Ошибка при закрытии соединения: " + closeException.getMessage());
            }
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
     *
     * @param person - пользователь
     */
    @Override
    public void printOperation(Person person) {

        final String sql = "SELECT * FROM WallerService.operation WHERE idperson=?";

        Connection connection = null;

        try {
            connection = ConnectionManager.open();

            connection.setAutoCommit(false);

            Optional<Long> sendPerson = personDataService.getPersonId(person);

            if (sendPerson.isEmpty()) {
                System.err.println("Пользователь не найден");
                return;
            }
            long id = sendPerson.get();

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                System.out.println(resultSet.getString(1) + " " + resultSet.getString(3) + " " + resultSet.getDouble(4));

            }

            connection.commit();
            System.out.println("\nДействие успешно завершено" +
                    ".");

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
                    connection.close();

                }
            } catch (SQLException closeException) {
                System.err.println("Ошибка при закрытии соединения: " + closeException.getMessage());
            }
        }

    }
}
