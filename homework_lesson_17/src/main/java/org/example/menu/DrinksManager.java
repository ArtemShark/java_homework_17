package org.example.menu;

import org.example.drinkDAO.DrinksDaoImpl;
import org.example.model.Drink;

import java.math.BigDecimal;
import java.util.List;

import java.util.Scanner;
public class DrinksManager implements Manager {
    private DrinksDaoImpl drinksDao;
    private Scanner scanner;

    public DrinksManager(DrinksDaoImpl drinksDao) {
        this.drinksDao = drinksDao;
        this.scanner = new Scanner(System.in);
    }

    public void manage() {
        while (true) {
            System.out.println("\nВыберите действие для таблицы 'Drinks':");
            System.out.println("1. Показать все напитки");
            System.out.println("2. Добавить новый напиток");
            System.out.println("3. Изменить напиток");
            System.out.println("4. Удалить напиток");
            System.out.println("5. Выход");
            System.out.print("Ваш выбор: ");

            int action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {
                case 1:
                    showAll();
                    break;
                case 2:
                    addNew();
                    break;
                case 3:
                    update();
                    break;
                case 4:
                    delete();
                    break;
                case 5:
                    System.out.println("Выход из программы...");
                    return;
                default:
                    System.out.println("Неверный ввод, попробуйте еще раз.");
                    break;
            }
        }
    }

    @Override
    public void showAll() {
        List<Drink> drinks = drinksDao.findAll();
        if (drinks.isEmpty()) {
            System.out.println("Список напитков пуст.");
        } else {
            for (Drink drink : drinks) {
                System.out.println(drink.getDrinkId() + ": " + drink.getNameEN() + " / " + drink.getNameOtherLanguage() + " - " + drink.getPrice());
            }
        }
    }

    @Override
    public void addNew() {
        System.out.println("Введите название напитка на английском:");
        String nameEN = scanner.nextLine();
        System.out.println("Введите название напитка на другом языке:");
        String nameOtherLanguage = scanner.nextLine();
        System.out.println("Введите цену напитка:");
        BigDecimal price = scanner.nextBigDecimal();
        Drink drink = new Drink(0, nameEN, nameOtherLanguage, price);
        drinksDao.save(drink);
        System.out.println("Напиток добавлен.");
    }

    @Override
    public void update() {
        System.out.println("Введите ID напитка, который нужно обновить:");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Введите новое название напитка на английском:");
        String nameEN = scanner.nextLine();
        System.out.println("Введите новое название напитка на другом языке:");
        String nameOtherLanguage = scanner.nextLine();
        System.out.println("Введите новую цену напитка:");
        BigDecimal price = scanner.nextBigDecimal();
        Drink drink = new Drink(id, nameEN, nameOtherLanguage, price);
        drinksDao.update(drink);
        System.out.println("Напиток обновлен.");
    }
    @Override
    public void delete() {
        System.out.println("Введите ID напитка, который нужно удалить:");
        int id = scanner.nextInt();
        drinksDao.delete(id);
        System.out.println("Напиток удален.");
    }
}

