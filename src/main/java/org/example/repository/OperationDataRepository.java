package org.example.repository;

import org.example.entity.Person;
import org.example.utils.Operation;

public interface OperationDataRepository {
    void addOperationData(Person person, Operation operation, double money);
}
