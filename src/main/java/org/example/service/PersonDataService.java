package org.example.service;

import lombok.Getter;
import org.example.entity.Person;
import org.example.repository.PersonDataRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Сервис,который взаимодействует с данными всех пользователей, реализующий интерфей PersonDataRepository.
 */
@Getter
public class PersonDataService implements PersonDataRepository {

    /**
     * Уникальный ID, присваемый каждому пользователю.
     */
    public static long id;

    /**
     * Словарь, который ввиде ключа хранит уникальный ID пользователя,
     * а значение - данные пользователя.
     */
    private final Map<Long, Person> personData = new HashMap<>();

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
     * Добавление Пользователя в баззу данныхю
     *
     * @param person - Добавляемый Пользователь.
     */
    @Override
    public void addPerson(Person person) {
        personData.put(++id, person);
    }

    /**
     * Получение пользователя по уникальному ID.
     *
     * @param id уникальный ID.
     * @return Данные пользователя.
     */
    @Override
    public Optional<Person> getPerson(long id) {
        return Optional.ofNullable(personData.get(id));
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
        for (Map.Entry<Long, Person> value : personData.entrySet()) {
            if (value.getValue().getUsername().equals(username)
                    && value.getValue().getPassword().equals(password)) {
                return Optional.of(value.getValue());
            }

        }
        return Optional.empty();
    }

    /**
     * Получение пользователя по полю Имя.
     *
     * @param username Имя пользователя.
     *                 Данные пользователя.
     */
    private Optional<Person> getPerson(String username) {
        for (Map.Entry<Long, Person> value : personData.entrySet()) {
            if (value.getValue().getUsername().equals(username)) {
                return Optional.of(value.getValue());
            }

        }
        return Optional.empty();
    }

    /**
     * Проверка на содежание Пользователя в Базе данных.
     *
     * @param person Проверяемый Пользователь.
     * @return true - если содержит данного пользователя, иначе false.
     */
    @Override
    public boolean isContainPerson(Person person) {

        for (Map.Entry<Long, Person> value : personData.entrySet()) {
            if (value.getValue() == person) return true;
        }
        return false;
    }

    /**
     * Проверка на содежание Пользователя в Базе данных по полю Имя.
     *
     * @param username Имя Пользователя.
     * @return true - если содержит данного пользователя, иначе false.
     */
    @Override
    public boolean isContainPerson(String username) {

        return getPerson(username).isPresent();
    }

}
