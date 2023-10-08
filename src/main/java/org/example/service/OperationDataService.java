package org.example.service;

import lombok.Getter;
import org.example.entity.Person;
import org.example.repository.OperationDataRepository;
import org.example.utils.Operation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OperationDataService implements OperationDataRepository {

    @Getter
    private final Map<String, List<Map<UUID, Map<Operation, Double>>>> operationData
            = new HashMap<>();

    private static OperationDataService operationDataService;

    private OperationDataService() {
    }

    public static OperationDataService getOperationDataService() {
        if (operationDataService == null) {
            operationDataService = new OperationDataService();
        }
        return operationDataService;
    }


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
