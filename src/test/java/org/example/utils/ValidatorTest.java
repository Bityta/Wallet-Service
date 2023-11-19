package org.example.utils;

import org.example.entity.Person;
import org.example.service.PersonDataService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    @Test
    void lengthPassportTest() {
        //создать хорошую валидацию паролей и логинов.
        assertFalse(Validator.isCorrectPassword("1234"));
        assertTrue(Validator.isCorrectPassword("//////"));
    }

    @Test
    void lengthUserNameTest() {
        assertTrue(Validator.isCorrectUserName("Name"));
        assertFalse(Validator.isCorrectUserName("nam"));
    }

    @Test
    void firstUpperUserNameTest() {
        assertTrue(Validator.isCorrectUserName("Name"));
        assertFalse(Validator.isCorrectUserName("name"));
    }

    @Test
    void firstLetterUserNameTest() {
        assertTrue(Validator.isCorrectUserName("Name"));
        assertFalse(Validator.isCorrectUserName("1name"));
    }


    @Test
    void freeUserNameTest() {
        final PersonDataService personData = PersonDataService.getPersonDataService();
        Person person = new Person("Bityta", "1488cc");

        personData.addPerson(person);

        assertTrue(Validator.isCorrectUserName("Bitytq"));
        assertFalse(Validator.isCorrectUserName("Bityta"));
    }

    @Test
    void numberTest() {

        assertTrue(Validator.isCorrectNumber("142.12"));
        assertFalse(Validator.isCorrectNumber("13.fa"));

    }

    @Test
    void PositiveNumberTest() {
        assertFalse(Validator.isPositiveNumber(-10.));
        assertTrue(Validator.isPositiveNumber(10.));
    }

}