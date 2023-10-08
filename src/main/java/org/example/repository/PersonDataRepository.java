package org.example.repository;

import org.example.entity.Person;

import java.util.Optional;

public interface PersonDataRepository {

    Optional<Person> getPerson(long id);

    Optional<Person> getPerson(String username, String password);

    void addPerson(Person person);

    boolean isContainPerson(Person person);

    boolean isContainPerson(String username);

}
