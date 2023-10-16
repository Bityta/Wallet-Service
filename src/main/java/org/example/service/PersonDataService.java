package org.example.service;

import lombok.Getter;
import org.example.entity.Person;
import org.example.repository.PersonDataRepository;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Optional;
import java.util.Properties;

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
    private PersonDataService() {

        final String path = "/app/resources/application.properties";

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

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, person.getUsername());
            preparedStatement.setString(2, person.getPassword());
            preparedStatement.setDouble(3, person.getBalance());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("SQL Exception " + e.getMessage());
            throw new RuntimeException();
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

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                String username = resultSet.getString(2);
                String password = resultSet.getString(3);
                double balance = resultSet.getDouble(4);

                Person person = new Person(username, password, balance);

                return Optional.of(person);
            } else {
                System.out.println("Пользователя не найдено!");
                return Optional.empty();
            }

        } catch (SQLException e) {
            System.out.println("SQL Exception " + e.getMessage());
            throw new RuntimeException();
        }

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

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                String password = resultSet.getString(3);
                double balance = resultSet.getDouble(4);

                Person person = new Person(username, password, balance);

                return Optional.of(person);
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            System.out.println("SQL Exception " + e.getMessage());
            throw new RuntimeException();
        }

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

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, person.getUsername());
            preparedStatement.setString(2, person.getPassword());
            preparedStatement.setDouble(3, person.getBalance());
            preparedStatement.setString(4, person.getUsername());


            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("SQL Exception " + e.getMessage());
            throw new RuntimeException();
        }
    }

    @Override
    public Optional<Long> getPersonId(Person person) {

        final String sql = "SELECT * FROM WallerService.Person WHERE username=?";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, person.getUsername());


            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                long id = resultSet.getLong(1);


                return Optional.of(id);
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            System.out.println("SQL Exception " + e.getMessage());
            throw new RuntimeException();
        }
    }
}
