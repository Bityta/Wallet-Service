package org.example.service;

import lombok.Getter;
import org.example.entity.Person;
import org.example.repository.PersonDataRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public class PersonDataService implements PersonDataRepository {

    public static long id;
    private final Map<Long, Person> personData = new HashMap<>();

    private static PersonDataService personDataService;

    private PersonDataService() {
    }

    public static PersonDataService getPersonDataService() {
        if (personDataService == null) {
            personDataService = new PersonDataService();
        }
        return personDataService;
    }

    @Override
    public void addPerson(Person person) {
        personData.put(++id, person);
    }

    @Override
    public Optional<Person> getPerson(long id) {
        return Optional.ofNullable(personData.get(id));
    }

    @Override
    public Optional<Person> getPerson(String username, String password) {
        for (Map.Entry<Long, Person> value : personData.entrySet()) {
            if (value.getValue().getUsername().equals(username)
                    && value.getValue().getPassword().equals(password)) {
                return Optional.of(value.getValue());
            }

        }
        return Optional.empty();
    }

    private Optional<Person> getPerson(String username) {
        for (Map.Entry<Long, Person> value : personData.entrySet()) {
            if (value.getValue().getUsername().equals(username)) {
                return Optional.of(value.getValue());
            }

        }
        return Optional.empty();
    }

    @Override
    public boolean isContainPerson(Person person) {

        for (Map.Entry<Long, Person> value : personData.entrySet()) {
            if (value.getValue() == person) return true;
        }
        return false;
    }

    @Override
    public boolean isContainPerson(String username) {

        return getPerson(username).isPresent();
    }

}
