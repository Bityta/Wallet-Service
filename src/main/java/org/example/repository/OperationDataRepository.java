package org.example.repository;

import org.example.entity.Person;
import org.example.utils.Operation;

import java.util.Map;

/**
 * Репозиторий Базы Данных, хранящий операции пользователей.
 */
public interface OperationDataRepository {

    /**
     * Добавление операции в базу данных.
     *
     * @param person    Пользователь.
     * @param operation Операция.
     * @param balance   Сумма операции.
     */
    void addOperation(Person person, Operation operation, double balance);

    /**
     * Получение всех операции пользователя
     *
     * @param person - пользователь
     */
    void getOperation(Person person);
}
