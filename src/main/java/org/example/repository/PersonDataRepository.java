package org.example.repository;

import org.example.entity.Person;

import java.io.IOException;
import java.util.Optional;

/**
 * Репозиторий Базы данных, хранящий данные о зарегестрированных пользователях.
 */
public interface PersonDataRepository {

    /**
     * Получение Пользователя по уникальному id.
     *
     * @param id уникальный ID.
     * @return Данные о найдем Пользователе.
     */
    Optional<Person> getPerson(long id);

    /**
     * Получение Пользователя по его полю Имя и Пароль.
     *
     * @param username Имя пользователя.
     * @param password Пароль пользователя.
     * @return Данные о найденом Пользователе.
     */
    Optional<Person> getPerson(String username, String password);

    /**
     * Добавление Пользователя в базу данных.
     *
     * @param person - Добавляемый Пользователь.
     */
    void addPerson(Person person) throws IOException;

    /**
     * Проверка на содержание Пользователя в Базе данных.
     *
     * @param person Проверяемый Пользователь.
     * @return true - если содержит, иначе false.
     */
    boolean isContainPerson(Person person);

    /**
     * Проверка на содежание Пользователя в Базе данных по полю Имя.
     *
     * @param username Имя Пользователя.
     * @return true - если содержит, иначе false.
     */
    boolean isContainPerson(String username);

}
