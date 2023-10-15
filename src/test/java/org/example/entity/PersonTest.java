package org.example.entity;

import org.example.utils.Operation;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {


    @Test
    void addBalanceTest() {
        Person person = new Person("Bityta", "qwerty");
        person.addMoney(10);
        assertEquals(10, person.getBalance());
    }

    @Test
    void diffBalanceTest() {
        Person person = new Person("Bityta", "qwerty");
        person.setBalance(10);

        person.diffMoney(10);
        assertEquals(0, person.getBalance());
    }

    @Test
    void addTransactionTest() {
        Person person = new Person("Bityta", "qwerty");

        UUID uuid = UUID.randomUUID();

        List<Map<UUID, Map<Operation, Double>>> transaction = new ArrayList<>();

        Map<UUID, Map<Operation, Double>> test1 = new HashMap<>();
        Map<Operation, Double> test2 = new HashMap<>();

        test2.put(Operation.replenishment, 200.);
        test1.put(uuid, test2);

        transaction.add(test1);
        person.addTransaction(test1);

        assertEquals(person.getTransactions(), transaction);
    }

    @Test
    void getBalanceTest() {

        Person person = new Person("Bityta", "qwerty");
        person.setBalance(10);

        assertEquals(10, person.getBalance());
    }


    @Test
    void getUserNameTest() {
        Person person = new Person("Bityta", "qwerty");

        assertEquals("qwerty", person.getPassword());
    }


    @Test
    void getPasswordTest() {
        Person person = new Person("Bityta", "qwerty");

        assertEquals("Bityta", person.getUsername());
    }

    @Test
    void constructorTest() {
        Person person = new Person("Bityta", "qwerty", 20.1);
        assertEquals("Bityta", person.getUsername());
        assertEquals("qwerty", person.getPassword());
        assertEquals(20.1, person.getBalance());
    }

    @Test
    void toStringTest() {

        Person person = new Person("Bityta", "qwerty");
        person.setBalance(200);
        assertEquals("Person(username=Bityta, password=qwerty, balance=200.0, transactions=[])", person.toString());
    }

}