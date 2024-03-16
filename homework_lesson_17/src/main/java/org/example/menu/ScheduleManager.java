package org.example.menu;

import org.example.scheduleDAO.ScheduleDaoImpl;
import org.example.model.Schedule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import java.util.Scanner;
public class ScheduleManager implements Manager {
    private ScheduleDaoImpl scheduleDao;
    private Scanner scanner;

    public ScheduleManager(ScheduleDaoImpl scheduleDao) {
        this.scheduleDao = scheduleDao;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void manage() {
        while (true) {
            System.out.println("\nУправление расписанием:");
            System.out.println("1. Показать всё расписание");
            System.out.println("2. Добавить запись в расписание");
            System.out.println("3. Изменить запись в расписание");
            System.out.println("4. Удалить запись из расписания");
            System.out.println("5. Вернуться в предыдущее меню");
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
                    return;
                default:
                    System.out.println("Неверный ввод, попробуйте еще раз.");
                    break;
            }
        }
    }

    @Override
    public void showAll() {
        List<Schedule> scheduleList = scheduleDao.findAll();
        if (scheduleList.isEmpty()) {
            System.out.println("Расписание пусто.");
        } else {
            for (Schedule schedule : scheduleList) {
                System.out.println("ID: " + schedule.getScheduleId() + ", ID Сотрудника: " + schedule.getStaffId() + ", Дата: " + schedule.getWorkDate() + ", Начало: " + schedule.getStartTime() + ", Конец: " + schedule.getEndTime());
            }
        }
    }

    @Override
    public void addNew() {
        System.out.println("Добавление новой записи в расписание:");
        System.out.print("Введите ID сотрудника: ");
        int staffId = scanner.nextInt();
        System.out.print("Введите дату работы (ГГГГ-ММ-ДД): ");
        String workDate = scanner.next();
        System.out.print("Введите время начала (ЧЧ:ММ): ");
        String startTime = scanner.next();
        System.out.print("Введите время окончания (ЧЧ:ММ): ");
        String endTime = scanner.next();

        Schedule schedule = new Schedule(0, staffId, LocalDate.parse(workDate), LocalTime.parse(startTime), LocalTime.parse(endTime));
        scheduleDao.save(schedule);
        System.out.println("Запись в расписание успешно добавлена.");
    }

    @Override
    public void update() {
        System.out.print("Введите ID записи в расписании, которую хотите обновить: ");
        int scheduleId = scanner.nextInt();
        System.out.print("Введите новый ID сотрудника: ");
        int staffId = scanner.nextInt();
        System.out.print("Введите новую дату работы (ГГГГ-ММ-ДД): ");
        String workDate = scanner.next();
        System.out.print("Введите новое время начала (ЧЧ:ММ): ");
        String startTime = scanner.next();
        System.out.print("Введите новое время окончания (ЧЧ:ММ): ");
        String endTime = scanner.next();

        Schedule schedule = new Schedule(scheduleId, staffId, LocalDate.parse(workDate), LocalTime.parse(startTime), LocalTime.parse(endTime));
        scheduleDao.update(schedule);
        System.out.println("Запись в расписание успешно обновлена.");
    }

    @Override
    public void delete() {
        System.out.print("Введите ID записи в расписании, которую хотите удалить: ");
        int scheduleId = scanner.nextInt();

        scheduleDao.delete(scheduleId);
        System.out.println("Запись в расписание успешно удалена.");
    }
}