package org.example.service;

import org.example.entity.Person;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PersonDataServiceTest {

    @Test
    void addPersonTest() {
        Person person = new Person("Bityta", "qwerty");
        PersonDataService personDataService = PersonDataService.getPersonDataService();

        personDataService.addPerson(person);

        Optional<Person> OptionalPerson = Optional.of(person);

        assertEquals(OptionalPerson, personDataService.getPerson(2));
        assertEquals(OptionalPerson, personDataService.getPerson(person.getUsername(), person.getPassword()));
        assertEquals(Optional.empty(), personDataService.getPerson("zxc", person.getPassword()));


    }


    @Test
    void isContainPersonTest() {

        Person person = new Person("Test", "qwerty");
        Person person1 = new Person("Bityta1", "qwerty");

        PersonDataService personDataService = PersonDataService.getPersonDataService();
        personDataService.addPerson(person);


        assertTrue(personDataService.isContainPerson(person.getUsername()));
        assertFalse(personDataService.isContainPerson("Bityta1"));
        assertTrue(personDataService.isContainPerson(person));
        assertFalse(personDataService.isContainPerson(person1));


    }

}