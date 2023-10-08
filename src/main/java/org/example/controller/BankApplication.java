package org.example.controller;

import org.example.entity.Person;
import org.example.service.OperationDataService;
import org.example.service.PersonDataService;
import org.example.utils.Operation;
import org.example.utils.Validator;

import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

public class BankApplication {

    private Person person;
    private final PersonDataService personDataService = PersonDataService.getPersonDataService();
    private final OperationDataService operationDataService = OperationDataService.getOperationDataService();
    private final Scanner scanner = new Scanner(System.in);


    public void printMainMenu() {
        System.out.println("\n\t\t\tДобро пожаловать в Online Bank\n");
        System.out.println("\t\t" + "#".repeat(40) + "\n");
        printSelectionMenu();

    }

    private void printLoginMenu() {

        System.out.print("\nВведите ваш Логин: ");
        String username = scanner.nextLine();

        System.out.print("\nВведите ваш Пароль: ");
        String password = scanner.nextLine();


        while (!personDataService.isContainPerson(username)) {
            System.out.println("\n\u001B[31m" + "Вы ввели некоректные данный, повторите попытку!" + "\u001B[0m");

            System.out.print("\nВведите ваш Логин: ");
            username = scanner.nextLine();


            System.out.print("\nВведите ваш Пароль: ");
            password = scanner.nextLine();

        }

        person = personDataService.getPerson(username, password).orElseThrow();
        printPersonalArea();


    }

    private void printRegMenu() {


        System.out.print("\nВведите Логин: ");
        String username = scanner.nextLine();

        while (!Validator.isCorrectUserName(username)) {
            System.out.print("\nВведите Корректный Логин: ");
            username = scanner.nextLine();
        }


        System.out.print("\nВведите Пароль: ");
        String password = scanner.nextLine();

        while (!Validator.isCorrectPassword(password)) {
            System.out.print("\nВведите Корректный Пароль: ");
            password = scanner.nextLine();
        }


        Person person = new Person(username, password);

        personDataService.addPerson(person);
        this.person = person;
        printPersonalArea();
    }

    private void printPersonalArea() {

        while (true) {
            System.out.println("\n\t\t" + "#".repeat(40));
            System.out.println("\n\t\tЛичный кабинет пользователя - " + person.getUsername());
            System.out.println("\nТекущий баланс - " + person.getMoney() + " Рублей");

            System.out.println("\nДоступные операции:");
            System.out.println("\t 1 - Снять деньги");
            System.out.println("\t 2 - Пополнить счет");
            System.out.println("\t 3 - Взять кредит");
            System.out.println("\t 4 - Получить выписку о операциях");
            System.out.println("\t 0 - Выйти из личного кабинета");
            System.out.print("\nВыбирите и введите цифру для дальнейших действий: ");
            String input = scanner.nextLine();

            switch (input) {
                case ("1") -> {

                    System.out.print("\nВведите сумму, которую Вы желаете снять: ");
                    String money = scanner.nextLine();

                    while (Validator.isCorrectNumber(money)) {
                        System.out.print("\nВведите Корректную сумму, которую Вы желаете снять: ");
                        money = scanner.nextLine();
                    }

                    if (person.getMoney() < Double.parseDouble(money)) {
                        System.out.println("\n\u001B[31m" + "Недостаточно средст!" + "\u001B[0m");

                    } else {
                        operationDataService.addOperationData(person, Operation.withdraw, Double.parseDouble(money));

                    }

                }
                case ("2") -> {

                    System.out.print("\nВведите сумму, на которую Вы желаете пополнить счет: ");
                    String money = scanner.nextLine();

                    while (Validator.isCorrectNumber(money)) {
                        System.out.print("\nВведите Корректную сумму, на которую Вы желаете пополнить счет: ");
                        money = scanner.nextLine();
                    }

                    operationDataService.addOperationData(person, Operation.replenishment, Double.parseDouble(money));
                }
                case ("3") -> {

                    System.out.print("\nВведите сумму, на которую Вы желаете взять кредит: ");
                    String money = scanner.nextLine();

                    while (Validator.isCorrectNumber(money)) {
                        System.out.print("\nВведите Корректную сумму, на которую Вы желаете взять кредит: ");
                        money = scanner.nextLine();
                    }

                    operationDataService.addOperationData(person, Operation.credit, Double.parseDouble(money));
                }
                case ("4") -> printStatement(person);
                case ("0") -> {
                    printMainMenu();
                    return;
                }

                default -> {
                    System.out.println("\n\u001B[31m" + "Вы ввели некоректные данный, повторите попытку!" + "\u001B[0m");
                }
            }


        }
    }

    private void printSelectionMenu() {

        System.out.println("\t 1 - Войти");
        System.out.println("\t 2 - Зарегестрироваться");
        System.out.println("\t 0 - Завершить работу");
        System.out.print("\nВыбирите и введите цифру для входа в личный кабинет: ");


        String input = scanner.nextLine();


        switch (input) {
            case ("1") -> printLoginMenu();
            case ("2") -> printRegMenu();
            case ("0") -> System.exit(0);
            default -> {
                System.out.println("\n\u001B[31m" + "Вы ввели некоректные цифру, повторите попытку!" + "\u001B[0m");
                printSelectionMenu();
            }
        }


    }


    public void printStatement(Person person) {

        String username = person.getUsername();

        System.out.println("\n\t\t\t\tВыписка об операциям");
        System.out.println("\t\t\t" + "#".repeat(30) + "\n");
        System.out.println("\t\t\tИдентификатор\t\t\t\tДействие\t Сумма\n");

        for (Map<UUID, Map<Operation, Double>> i : operationDataService.getOperationData().get(username)) {

            for (Map.Entry<UUID, Map<Operation, Double>> j : i.entrySet()) {
                System.out.print(j.getKey() + "  ");

                for (Map.Entry<Operation, Double> k : j.getValue().entrySet()) {
                    System.out.println(k.getKey() + "\t\t" + k.getValue());
                }
            }
        }


    }

}
