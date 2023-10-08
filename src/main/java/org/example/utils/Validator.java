package org.example.utils;

import org.example.service.PersonDataService;

/**
 * Валидация полей класса Person.
 */
public class Validator {


    /**
     * Проверка корректности Пароля.
     *
     * @param password Пароль.
     * @return true - если пароль корректный, иначе false.
     */
    public static boolean isCorrectPassword(String password) {
        if (password.length() < 6) {
            System.out.println("\n\u001B[31m" + "Слишком короткий пароль, используйте минимум 6 символов!" + "\u001B[0m");
            return false;
        }

        return true;
    }

    /**
     * Проверка корректности Имя.
     *
     * @param username Имя.
     * @return true - если имя корректное, иначе false.
     */

    public static boolean isCorrectUserName(String username) {

        final PersonDataService personData = PersonDataService.getPersonDataService();

        if (username.length() < 4) {
            System.out.println("\n\u001B[31m" + "Слишком короткое имя, используйте минимум 4 символа!" + "\u001B[0m");
            return false;
        }

        char firstLetter = username.charAt(0);

        int ASCIILetterA = 'A';
        if (firstLetter <= ASCIILetterA) {
            System.out.println("\n\u001B[31m" + "Имя должно начинаться с буквы!" + "\u001B[0m");
            return false;
        }

        if (firstLetter != Character.toUpperCase(firstLetter)) {
            System.out.println("\n\u001B[31m" + "Имя должно начинаться с большой буквы!" + "\u001B[0m");
            return false;
        }

        if (personData.isContainPerson(username)) {
            System.out.println("\n\u001B[31m" + "Пользователь с таким именем уже создан!" + "\u001B[0m");
            return false;
        }

        return true;

    }

    /**
     * Проверка коректности вводимного числа.
     *
     * @param number - Число
     * @return true - если имя корректное, иначе false.
     */
    public static boolean isCorrectNumber(String number) {

        try {
            Double.parseDouble(number);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }

    }
}
