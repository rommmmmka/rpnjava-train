import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;

public class Main {
    static Train train;

    public static void printMenu(String[] options) {
        for (String s: options)
            System.out.println(s);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        train = new Train();
        while (true) {
            String[] options = {
                    "1 - Добавить вагон",
                    "2 - Добавить пассажира",
                    "3 - Вывести список пассажиров и багажа",
                    "4 - Вывести список вагонов",
                    "5 - Сохранить в файл",
                    "6 - Загрузить из файла",
                    "0 - Выйти"
            };
            printMenu(options);
            try {
                int option = Integer.parseInt(scanner.nextLine());
                switch (option){
                    case 1: addRailcar(scanner); break;
                    case 2: addPassenger(scanner); break;
                    case 3: printPassengersAndBaggage(scanner); break;
                    case 4: printRailcars(scanner); break;
                    case 5: saveToFile(scanner); break;
                    case 6: loadFromFile(); break;
                    case 0: System.exit(1);
                    default: throw new IOException();
                }
            }
            catch (Exception e ){
                System.out.println("Неверное число");
            }
        }
    }

    private static void addRailcar(Scanner scanner) {
        try {
            System.out.print("Введите модель вагона: ");
            String model = scanner.nextLine();
            System.out.print("Введите максимальное число пассажиров: ");
            int passengersMax = Integer.parseInt(scanner.nextLine());
            System.out.print("Введите уровень комфортности вагона: ");
            int comfortLevel = Integer.parseInt(scanner.nextLine());
            train.addRailcar(new Railcar(model, passengersMax, comfortLevel));
            System.out.println("Вагон добавлен!");
        }
        catch (Exception e) {
            System.out.println("Ошибка: введены недопустимые данные");
        }
    }

    private static void addPassenger(Scanner scanner) {
        try {
            System.out.println("Выберите вагон");
            for (int i = 0; i < train.getRailcars().size(); i++) {
                System.out.println((i + 1) + " - " + train.getRailcars().get(i));
            }
            int option = Integer.parseInt(scanner.nextLine()) - 1;
            if (option < 0 || option >= train.getRailcars().size()) {
                System.out.println("Такой вагон не существует");
                return;
            }
            Railcar r = train.getRailcars().get(option);
            System.out.println("Введите фамилию: ");
            String lastName = scanner.nextLine();
            System.out.println("Введите имя: ");
            String firstName = scanner.nextLine();
            System.out.println("Введите отчество: ");
            String patronymic = scanner.nextLine();
            Passenger p = new Passenger(lastName, firstName, patronymic);
            System.out.print("Введите количество багажа: ");
            int baggageCount = Integer.parseInt(scanner.nextLine());
            for (int i = 1; i <= baggageCount; i++) {
                System.out.print("Введите вес багажа №" + i + ": ");
                int weight = Integer.parseInt(scanner.nextLine());
                p.addBaggage(new Baggage(weight));
            }
            r.addPassenger(p);
        }
        catch (Exception e) {
            System.out.println("Ошибка: введены недопустимые данные");
        }
    }

    private static void printPassengersAndBaggage(Scanner scanner) {
        int maxPassengers = 0, passengers = 0, baggage = 0, baggageWeight = 0;
        for (Railcar r: train.getRailcars()) {
            maxPassengers += r.getPassengersMax();
            for (Passenger p: r.getPassengers()) {
                passengers++;
                baggage += p.getBaggage().size();
                for (Baggage b: p.getBaggage())
                    baggageWeight += b.getWeight();
            }
        }
        System.out.println("Вместимость поезда: " + maxPassengers + " пассажиров");
        System.out.println("Всего пассажиров: " + passengers);
        System.out.println("Всего багажа: " + baggage);
        System.out.println("Суммарный вес багажа: " + baggageWeight + "кг");
        System.out.println("Выберите вагон для отображения пассажиров и багажа в нём:");
        for (int i = 0; i < train.getRailcars().size(); i++) {
            System.out.println(" " + (i + 1) + " - " + train.getRailcars().get(i));
        }
        System.out.println(" 0 - В меню");
        try {
            int option = Integer.parseInt(scanner.nextLine()) - 1;
            if (option == -1)
                return;
            if (option < 0 || option >= train.getRailcars().size())
                throw new Exception();
            printRailcarPassengersAndBaggage(option);
        }
        catch (Exception e) {
            System.out.println("Введён неверный номер вагона");
        }
    }

    private static void printRailcarPassengersAndBaggage(int railcarNumber) {
        try {
            for (Passenger p: train.getRailcars().get(railcarNumber).getPassengers()) {
                System.out.println(p.toString());
                for (Baggage b: p.getBaggage())
                    System.out.println(" " + b.toString());
            }
        }
        catch (Exception e) {
            System.out.println("Введён неверный номер вагона");
        }

    }

    private static void printRailcars(Scanner scanner) {
        String[] options = {
                "Выберите способ вывода списка вагонов:",
                "1 - По порядку",
                "2 - По уровню комфортности",
                "3 - По диапазону числа пассажиров",
        };
        printMenu(options);
        try {
            int option = Integer.parseInt(scanner.nextLine());
            switch (option){
                case 1: printRailcarsDefault(); break;
                case 2: printRailcarsComfort(); break;
                case 3: printRailcarsPassengers(scanner); break;
                default: throw new Exception();
            }
        }
        catch (Exception e){
            System.out.println("Неверное число");
        }
    }

    private static void printRailcarsDefault() {
        for (Railcar i: train.getRailcars()) {
            System.out.println(i);
        }
    }

    private static void printRailcarsComfort() {
        List<Railcar> railcars = new ArrayList<>(train.getRailcars());
        Collections.sort(railcars);
        for (Railcar i: railcars) {
            System.out.println(i);
        }
    }
    private static void printRailcarsPassengers(Scanner scanner) {
        try {
            System.out.print("Минимальное число пассажиров: ");
            int minPassengers = Integer.parseInt(scanner.nextLine());
            System.out.print("Максимальное число пассажиров: ");
            int maxPassengers = Integer.parseInt(scanner.nextLine());
            for (Railcar i: train.getRailcars()) {
                if (i.getPassengers().size() >= minPassengers && i.getPassengers().size() <= maxPassengers)
                    System.out.println(i);
            }
        }
        catch (Exception e) {
            System.out.println("Ошибка: введены недопустимые данные");
        }
    }

    private static void saveToFile(Scanner scanner) {
        try{
            System.out.print("Введите имя файла: ");
            String fileName = scanner.nextLine() + ".impact";
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(train);
            objectOutputStream.close();
            System.out.println("Успешно сохранено!");
        }
        catch (Exception e) {
            System.out.println("Ошибка сохранения!");
        }
    }

    private static void loadFromFile() {
        try {
            FileInputStream fileInputStream = new FileInputStream("C:\\Users\\Username\\Desktop\\save.ser");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            train = (Train) objectInputStream.readObject();
            objectInputStream.close();
            System.out.println("Успешно загружено!");
        }
        catch (Exception e) {
            System.out.println("Ошибка загрузки!");
        }
    }
}
