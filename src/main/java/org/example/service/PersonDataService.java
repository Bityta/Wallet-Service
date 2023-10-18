package org.example.service;

import lombok.Getter;
import lombok.Setter;
import org.example.entity.Person;
import org.example.repository.PersonDataRepository;
import org.example.utils.ConnectionManager;


import java.sql.*;
import java.util.Optional;

/**
 * Сервис,который взаимодействует с данными всех пользователей, реализующий интерфей PersonDataRepository.
 */
@Getter
public class PersonDataService implements PersonDataRepository {

    /**
     * Экземпляр класса.
     */
    private static PersonDataService personDataService;


    /**
     * Приватный конструктор.
     */
    private PersonDataService() {
    }

    /**
     * Получения экземпляра данного класса. (Singleton)
     *
     * @return Экземпляр класса.
     */
    public static PersonDataService getPersonDataService() {
        if (personDataService == null) {
            personDataService = new PersonDataService();
        }
        return personDataService;
    }

    /**
     * Добавление Пользователя в базу данных
     *
     * @param person - Добавляемый Пользователь.
     */
    @Override
    public void addPerson(Person person) {

        final String sql = "INSERT INTO WallerService.Person(username, password, balance) "
                + "VALUES(?,?,?)";

        Connection connection = null;

        try {

            connection = ConnectionManager.open();
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, person.getUsername());
            preparedStatement.setString(2, person.getPassword());
            preparedStatement.setDouble(3, person.getBalance());

            preparedStatement.executeUpdate();

            connection.commit();
            System.out.println("\nДействие успешно завершенр.");

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

    /**
     * Получение пользователя по уникальному ID.
     *
     * @param id уникальный ID.
     * @return Данные пользователя.
     */
    @Override
    public Optional<Person> getPerson(long id) {


        final String sql = "SELECT * FROM WallerService.Person WHERE id = ?";

        Connection connection = null;
        Person person = null;

        try {

            connection = ConnectionManager.open();
            connection.setAutoCommit(false);


            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                String username = resultSet.getString(2);
                String password = resultSet.getString(3);
                double balance = resultSet.getDouble(4);

                person = new Person(username, password, balance);

            }

            connection.commit();

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

        System.out.println("Пользователя не найдено!");
        return Optional.ofNullable(person);

    }

    /**
     * Получение пользователя по полю Имя и Пароль.
     *
     * @param username Имя пользователя.
     * @param password Пароль пользователя.
     * @return Данные пользователя.
     */
    @Override
    public Optional<Person> getPerson(String username, String password) {

        return this.getPerson(username);
    }

    /**
     * Получение пользователя по полю Имя.
     *
     * @param username Имя пользователя.
     *                 Данные пользователя.
     */
    private Optional<Person> getPerson(String username) {

        final String sql = "SELECT * FROM WallerService.Person WHERE username = ?";

        Connection connection = null;
        Person person = null;

        try {
            connection = ConnectionManager.open();
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                String password = resultSet.getString(3);
                double balance = resultSet.getDouble(4);

                person = new Person(username, password, balance);

            }
            connection.commit();

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

        return Optional.ofNullable(person);

    }

    /**
     * Проверка на содежание Пользователя в Базе данных.
     *
     * @param person Проверяемый Пользователь.
     * @return true - если содержит данного пользователя, иначе false.
     */
    @Override
    public boolean isContainPerson(Person person) {
        return this.getPerson(person.getUsername(), person.getPassword()).isPresent();
    }

    /**
     * Проверка на содежание Пользователя в Базе данных по полю Имя.
     *
     * @param username Имя Пользователя.
     * @return true - если содержит данного пользователя, иначе false.
     */
    @Override
    public boolean isContainPerson(String username) {

        return this.getPerson(username).isPresent();
    }

    /**
     * Изменение балланс пользователя
     *
     * @param person  - пользователь
     * @param balance - баланс
     */
    @Override
    public void changeBalance(Person person, double balance) {

        Optional<Person> newPerson = this.getPerson(person.getUsername());

        if (newPerson.isPresent()) {
            newPerson.get().setBalance(balance);
            this.updatePerson(newPerson.get());
        }

    }

    /**
     * Обновление данных пользователя
     *
     * @param person - пользователь
     */
    @Override
    public void updatePerson(Person person) {

        final String sql = "UPDATE WallerService.Person SET username=?, password=?, balance=? WHERE username=?";

        Connection connection = null;
        try {

            connection = ConnectionManager.open();
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, person.getUsername());
            preparedStatement.setString(2, person.getPassword());
            preparedStatement.setDouble(3, person.getBalance());
            preparedStatement.setString(4, person.getUsername());


            preparedStatement.executeUpdate();

            connection.commit();

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


    /**
     * Получение ИД пользователя по его сущности
     *
     * @param person - пользователь
     * @return если нашелся - id, иначе Empty
     */
    @Override
    public Optional<Long> getPersonId(Person person) {

        final String sql = "SELECT * FROM WallerService.Person WHERE username=?";

        Connection connection = null;
        long id = 0L;
        try {
            connection = ConnectionManager.open();
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, person.getUsername());


            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                id = resultSet.getLong(1);

            }

            connection.commit();
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


        return Optional.of(id);

    }
}
