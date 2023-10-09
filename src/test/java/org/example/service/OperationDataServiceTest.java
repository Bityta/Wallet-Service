package org.example.service;

import org.example.entity.Person;
import org.example.utils.Operation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OperationDataServiceTest {


    @Test
    void addOperationDataTest() {
        OperationDataService operationDataService = OperationDataService.getOperationDataService();
        Person person = new Person("Bityta", "qwerty");

        operationDataService.addOperationData(person, Operation.replenishment, 200);
        operationDataService.addOperationData(person, Operation.withdraw, 300);
        operationDataService.addOperationData(person, Operation.credit, 500);
    }

}