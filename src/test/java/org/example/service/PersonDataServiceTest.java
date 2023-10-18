package org.example.service;

import org.example.entity.Person;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PersonDataServiceTest {

    @Test
    void addPersonTest() {
        Person person = new Person("testing", "qwerty");
        PersonDataService personDataService = PersonDataService.getPersonDataService();

        personDataService.addPerson(person);


        Optional<Long> id = personDataService.getPersonId(person);

        long PersonId = -1;

        if (id.isPresent()) {
            PersonId = id.get();
        }

        assertEquals(person.getUsername(), personDataService.getPerson(PersonId).get().getUsername());
        assertEquals(person.getPassword(), personDataService.getPerson(person.getUsername(), person.getPassword()).get().getPassword());
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