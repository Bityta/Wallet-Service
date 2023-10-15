package org.example.repository;

import org.example.entity.Person;
import org.example.utils.Operation;

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
}
