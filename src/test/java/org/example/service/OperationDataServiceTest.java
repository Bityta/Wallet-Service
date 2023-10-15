package org.example.service;

import org.example.entity.Person;
import org.example.utils.Operation;
import org.junit.jupiter.api.Test;

class OperationDataServiceTest {


    @Test
    void addOperationDataTest() {
        OperationDataService operationDataService = OperationDataService.getOperationDataService();
        Person person = new Person("Bityta", "qwerty");

        operationDataService.addOperation(person, Operation.replenishment, 200);
        operationDataService.addOperation(person, Operation.withdraw, 300);
        operationDataService.addOperation(person, Operation.credit, 500);
    }

}