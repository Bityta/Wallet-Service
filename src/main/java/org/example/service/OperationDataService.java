package org.example.service;

import lombok.Getter;
import org.example.entity.Person;
import org.example.repository.OperationDataRepository;
import org.example.utils.Operation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Сервис,который взаимодействует с данными всех операций, реализующий интерфей OperationDataRepository.
 */
public class OperationDataService implements OperationDataRepository {

    /**
     * Словарь, который в виде ключа хранит Имя пользователя,
     * а в виде значения - массив всех операций,
     * производимых пользователем. Сохраняемые данные: уникальный идентификатор операции, операция, сумма операции.

     */
    @Getter
    private final Map<String, List<Map<UUID, Map<Operation, Double>>>> operationData
            = new HashMap<>();

    /**
     * Экземпляр класса.
     */
    private static OperationDataService operationDataService;

    /**
     * Приватный конструктор.
     */
    private OperationDataService() {
    }

    /**
     * Получения экземпляра данного класса. (Singleton)
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
     * @param person Пользователь.
     * @param operation Операция.
     * @param money Сумма операции.
     */
    @Override
    public void addOperationData(Person person, Operation operation, double money) {

        Map<Operation, Double> operationManeyHashMap = new HashMap<>();
        Map<UUID, Map<Operation, Double>> UUIDHashMap = new HashMap<>();
        operationManeyHashMap.put(operation, money);
        UUIDHashMap.put(UUID.randomUUID(), operationManeyHashMap);
        person.addTransaction(UUIDHashMap);

        operationData.put(person.getUsername(), person.getTransactions());


        switch (operation) {
            case credit, replenishment -> person.addMoney(money);
            case withdraw -> person.diffMoney(money);

        }
    }


}
