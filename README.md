# Wallet-Service

Консольное Приложение Кошелек (Банк). 

Тут напистаь про запуск приложения.


Архитектура и функции консольного меню:

  Главное меню {
  
    Основное описание проекта
  
  }

Регистрационное меню {

  -Вход в личный кабинет
  -Регистрация
  -Выход в Главное меню
}

Меню личного кабинета {

  Баланс Пользователя

  -Снять деньги
  -Пополнить баланс
  -Взять Кредит
  -Получить выписку об операциях
  -Выход в Регистрационное меню

}


Каждый новый пользователь имеет уникинальный ID, который генерируется при его создание (с помощью статической переменой).
Каждая операция пользователя имеет уникальный идентификатор, который генирируется с помощью класса UUID.


