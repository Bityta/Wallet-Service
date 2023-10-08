package org.example.entity;


import lombok.*;
import org.example.utils.Operation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Модель Пользователя, задающая его основые параметры и переменные.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Person {

    /**
     * Имя пользователя.
     */
    private String username;

    /**
     * Пароль пользователя.
     */
    private String password;

    /**
     * Текущий баланс пользователя.
     */
    private double balance;

    /**
     * Переменная, хранящая массив всех операций, производимых пользователем.
     * Сохраняемые данные: уникальный идентификатор операции, операция, сумма операции.
     */
    private List<Map<UUID, Map<Operation, Double>>> transactions = new ArrayList<>();

    /**
     * Конструктор
     *
     * @param username Имя пользователя.
     * @param password Пароль пользователя.
     */
    public Person(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Увелечение баланса.
     *
     * @param balance Прибавляемая Сумма.
     */
    public void addMoney(double balance) {
        this.balance += balance;
    }

    /**
     * Уменьшение баланса.
     *
     * @param balance Убавляемая Сумма.
     */
    public void diffMoney(double balance) {
        this.balance -= balance;
    }

    /**
     * Добавление новой операции.
     *
     * @param UUIDHashMap Словарь хранящий: уникальный идентификатор операции,
     *                    операция, сумма операции.
     */
    public void addTransaction(Map<UUID, Map<Operation, Double>> UUIDHashMap) {
        transactions.add(UUIDHashMap);
    }


}

