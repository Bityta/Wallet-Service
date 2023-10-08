package org.example.entity;


import lombok.*;
import org.example.utils.Operation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Person {

    private String username;
    private String password;
    private double money;
    private List<Map<UUID, Map<Operation, Double>>> transactions = new ArrayList<>();

    public Person(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void addMoney(double money) {
        this.money += money;
    }

    public void diffMoney(double money) {
        this.money -= money;
    }

    public void addTransaction(Map<UUID, Map<Operation, Double>> UUIDHashMap) {
        transactions.add(UUIDHashMap);
    }


}

