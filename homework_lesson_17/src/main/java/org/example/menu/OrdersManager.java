package org.example.menu;

import org.example.orderdeatilsDAO.OrderDetailsDaoImpl;
import org.example.ordersDAO.OrdersDaoImpl;
import org.example.model.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import java.util.Scanner;
public class OrdersManager implements Manager {
    private OrdersDaoImpl ordersDao;
    private Scanner scanner;

    private OrderDetailsManager orderDetailsManager;

    public OrdersManager(OrdersDaoImpl ordersDao, OrderDetailsDaoImpl orderDetailsDao) {
        this.ordersDao = ordersDao;
        this.orderDetailsManager = new OrderDetailsManager(orderDetailsDao);
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void manage() {
        while (true) {
            System.out.println("\nУправление заказами:");
            System.out.println("1. Показать все заказы");
            System.out.println("2. Добавить новый заказ");
            System.out.println("3. Изменить заказ");
            System.out.println("4. Удалить заказ");
            System.out.println("5. Управление деталями заказа");
            System.out.println("6. Вернуться в предыдущее меню");
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
                    orderDetailsManager.manage();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Неверный ввод, попробуйте еще раз.");
                    break;
            }
        }
    }

    @Override
    public void showAll() {
        List<Order> orders = ordersDao.findAll();
        if (orders.isEmpty()) {
            System.out.println("Список заказов пуст.");
        } else {
            for (Order order : orders) {
                System.out.println("Заказ ID: " + order.getOrderId() + ", Клиент ID: " + order.getClientId() + ", Сотрудник ID: " + order.getStaffId() + ", Дата заказа: " + order.getOrderDate() + ", Общая стоимость: " + order.getTotalPrice());
            }
        }
    }

    @Override
    public void addNew() {
        System.out.println("Добавление нового заказа:");
        System.out.print("Введите ID клиента: ");
        int clientId = scanner.nextInt();
        System.out.print("Введите ID сотрудника: ");
        int staffId = scanner.nextInt();
        System.out.print("Введите общую стоимость заказа: ");
        BigDecimal totalPrice = scanner.nextBigDecimal();

        LocalDate orderDate = LocalDate.now();

        Order order = new Order(0, clientId, staffId, orderDate, totalPrice);
        ordersDao.save(order);
        System.out.println("Новый заказ успешно добавлен.");
    }

    @Override
    public void update() {
        System.out.print("Введите ID заказа, который хотите обновить: ");
        int orderId = scanner.nextInt();
        System.out.print("Введите новый ID клиента: ");
        int clientId = scanner.nextInt();
        System.out.print("Введите новый ID сотрудника: ");
        int staffId = scanner.nextInt();
        System.out.print("Введите новую общую стоимость заказа: ");
        BigDecimal totalPrice = scanner.nextBigDecimal();

        Order order = new Order(orderId, clientId, staffId, LocalDate.now(), totalPrice);
        System.out.println("Заказ успешно обновлен.");
    }


    @Override
    public void delete() {
        System.out.print("Введите ID заказа, который хотите удалить: ");
        int orderId = scanner.nextInt();
        ordersDao.delete(orderId);
        System.out.println("Заказ успешно удален.");
    }
}