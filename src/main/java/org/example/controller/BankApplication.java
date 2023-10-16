package org.example.controller;

import org.example.entity.Person;
import org.example.service.OperationDataService;
import org.example.service.PersonDataService;
import org.example.utils.Operation;
import org.example.utils.Validator;

import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

/**
 * Отвечает за данные, вывводимые в консоль и навигацию в приложении.
 */
public class BankApplication {

    /**
     * Текущий авторизированный пользователь.
     */
    private Person person;

    /**
     * Сервис, который осуществляет работу с данными пользователями.
     */
    private final PersonDataService personDataService = PersonDataService.getPersonDataService();

    /**
     * Сервис, который осуществялет работу с операциями пользователей.
     */
    private final OperationDataService operationDataService = OperationDataService.getOperationDataService();

    /**
     * Сканер, считывающий вводимые данные пользователей.
     */
    private final Scanner scanner = new Scanner(System.in);


    /**
     * Выводит Главное меню, после перенаправляет пользователя на функцию - printSelectionMenu.
     */
    public void printMainMenu() {
        System.out.println("\n\t\t\tДобро пожаловать в Online Bank\n");
        System.out.println("\t\t" + "#".repeat(40) + "\n");
        printSelectionMenu();

    }

    /**
     * Выводит меню Входа, в котором пользователь заполняет поле Имя и Пороль.
     * После успешной авторизации, перенаправляет пользователя на функцию - printPersonalArea.
     */
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

    /**
     * Выводит меню Регистриции, в котором пользователь заполняеет поля Имя и Пароль.
     * После усешной регистрации,перенаправляет пользователя на функцию - printPersonalArea.
     **/
    private void printRegMenu() {


        System.out.print("\nВведите Имя: ");
        String username = scanner.nextLine();

        while (!Validator.isCorrectUserName(username)) {
            System.out.print("\nВведите Корректное Имя: ");
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

    /**
     * Выводит меню Личного Кабинета, в котором пользователь видет
     * текущий баланс, а также ряд операций: Снятие денег, Пополнение счета,
     * Взятие кредита, получение Выписки об операциях. А также доступен выход из
     * личного кабинета(перенаправление на фунцию printSelectionMenu).
     */
    private void printPersonalArea() {

        while (true) {
            System.out.println("\n\t\t" + "#".repeat(40));
            System.out.println("\n\t\tЛичный кабинет пользователя - " + person.getUsername());
            System.out.println("\nТекущий баланс - " + person.getBalance() + " Рублей");

            System.out.println("\nДоступные операции:");
            System.out.println("\t 1 - Снять деньги");
            System.out.println("\t 2 - Пополнить счет");
            System.out.println("\t 3 - Взять кредит");
            System.out.println("\t 4 - Получить выписку об операциях");
            System.out.println("\t 0 - Выйти из личного кабинета");
            System.out.print("\nВыбирите и введите цифру для дальнейших действий: ");
            String input = scanner.nextLine();

            switch (input) {
                case ("1") -> {

                    System.out.print("\nВведите сумму, которую Вы желаете снять: ");
                    String money = scanner.nextLine();
                    double sum = 0;

                    while (!Validator.isCorrectNumber(money) || !Validator.isPositiveNumber(sum)) {


                        if (!Validator.isCorrectNumber(money)) {
                            System.out.print("\nВведите Корректную сумму, которую Вы желаете снять: ");
                            money = scanner.nextLine();
                        }

                        sum = Double.parseDouble(money);

                        if (!Validator.isPositiveNumber(sum)) {
                            System.out.print("\nВведите Положительную сумму, которую Вы желаете снять: ");
                            money = scanner.nextLine();
                        }

                        if (person.getBalance() < sum) {
                            System.out.println("\n\u001B[31m" + "Недостаточно средст!" + "\u001B[0m");

                        }
                    }

                    sum = Double.parseDouble(money);
                    operationDataService.addOperation(person, Operation.withdraw, sum);


                }
                case ("2") -> {

                    System.out.print("\nВведите сумму, на которую Вы желаете пополнить счет: ");
                    String money = scanner.nextLine();
                    double sum = 0;

                    while (!Validator.isCorrectNumber(money) || !Validator.isPositiveNumber(sum)) {


                        if (!Validator.isCorrectNumber(money)) {
                            System.out.print("\nВведите Корректную сумму, на которую Вы желаете пополнить счет: ");
                            money = scanner.nextLine();

                        }

                        sum = Double.parseDouble(money);

                        if (!Validator.isPositiveNumber(sum)) {
                            System.out.print("\nВведите Положительную сумму, на которую Вы желаете пополнить счет: ");
                            money = scanner.nextLine();
                        }
                    }

                    sum = Double.parseDouble(money);

                    operationDataService.addOperation(person, Operation.replenishment, sum);
                }
                case ("3") -> {

                    System.out.print("\nВведите сумму, на которую Вы желаете взять кредит: ");
                    String money = scanner.nextLine();

                    double sum = 0;

                    while (!Validator.isCorrectNumber(money) || !Validator.isPositiveNumber(sum)) {

                        if (!Validator.isCorrectNumber(money)) {
                            System.out.print("\nВведите Корректную сумму, на которую Вы желаете взять кредит: ");
                            money = scanner.nextLine();
                        }

                        sum = Double.parseDouble(money);

                        if (!Validator.isPositiveNumber(sum)) {
                            System.out.print("\nВведите Положительную сумму, на которую Вы желаете взять кредит: ");
                            money = scanner.nextLine();
                        }
                    }

                    sum = Double.parseDouble(money);
                    operationDataService.addOperation(person, Operation.credit, Double.parseDouble(money));
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

    /**
     * Выводит меню Входа.
     * Доступные действия: Регистрация, Вход, Завершение работы программы.
     */
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

    /**
     * Выводит Выписку об операции конкретного пользователя.
     * Выписка содерижит: уникальный идентификатор операции, операция, сумма операции.
     *
     * @param person Пользователь.
     */
    private void printStatement(Person person) {

        String username = person.getUsername();

        System.out.println("\n\t\t\t\tВыписка об операциям");
        System.out.println("\t\t\t" + "#".repeat(30) + "\n");
        System.out.println("\t\t\tИдентификатор\t\t\t\tДействие\t Сумма\n");

        operationDataService.getOperation(person);


    }

}
