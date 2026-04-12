import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeliveryApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static List<Parcel> allParcels = new ArrayList<>();
    private static List<Trackable> trackableItems = new ArrayList<>();

    private static ParcelBox<StandardParcel> standardBox = new ParcelBox<>(100);
    private static ParcelBox<FragileParcel> fragileBox = new ParcelBox<>(50);
    private static ParcelBox<PerishableParcel> perishableBox = new ParcelBox<>(75);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            showMenu();
            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        addParcel();
                        break;
                    case 2:
                        sendParcels();
                        break;
                    case 3:
                        calculateCosts();
                        break;
                    case 4:
                        trackParcels();
                        break;
                    case 5:
                        showBoxContents();
                        break;
                    case 0:
                        running = false;
                        System.out.println("Программа завершена.");
                        break;
                    default:
                        System.out.println("Неверный выбор. Пожалуйста, выберите пункт от 0 до 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите число!");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n=== СЛУЖБА ДОСТАВКИ SHIPIT ===");
        System.out.println("Выберите действие:");
        System.out.println("1 — Добавить посылку");
        System.out.println("2 — Отправить все посылки");
        System.out.println("3 — Посчитать стоимость доставки");
        System.out.println("4 — Трекинг посылок");
        System.out.println("5 — Показать содержимое коробки");
        System.out.println("0 — Завершить");
        System.out.print("Ваш выбор: ");
    }

    private static void addParcel() {
        System.out.println("\n--- Добавление новой посылки ---");
        System.out.println("Выберите тип посылки:");
        System.out.println("1 — Стандартная");
        System.out.println("2 — Хрупкая");
        System.out.println("3 — Скоропортящаяся");
        System.out.print("Ваш выбор: ");

        try {
            int type = Integer.parseInt(scanner.nextLine());

            System.out.print("Введите описание посылки: ");
            String description = scanner.nextLine();

            System.out.print("Введите вес (кг): ");
            int weight = Integer.parseInt(scanner.nextLine());

            System.out.print("Введите адрес доставки: ");
            String address = scanner.nextLine();

            System.out.print("Введите день отправки (число): ");
            int sendDay = Integer.parseInt(scanner.nextLine());

            switch (type) {
                case 1:
                    StandardParcel standard = new StandardParcel(description, weight, address, sendDay);
                    allParcels.add(standard);
                    standardBox.addParcel(standard);
                    System.out.println("Стандартная посылка успешно добавлена!");
                    break;

                case 2:
                    FragileParcel fragile = new FragileParcel(description, weight, address, sendDay);
                    allParcels.add(fragile);
                    fragileBox.addParcel(fragile);
                    trackableItems.add(fragile);
                    System.out.println("Хрупкая посылка успешно добавлена!");
                    break;

                case 3:
                    System.out.print("Введите срок годности (дней): ");
                    int timeToLive = Integer.parseInt(scanner.nextLine());
                    PerishableParcel perishable = new PerishableParcel(description, weight, address, sendDay, timeToLive);
                    allParcels.add(perishable);
                    perishableBox.addParcel(perishable);
                    System.out.println("Скоропортящаяся посылка успешно добавлена!");
                    break;

                default:
                    System.out.println("Неверный тип посылки!");
            }

        } catch (NumberFormatException e) {
            System.out.println("Ошибка: введите корректное число!");
        }
    }

    private static void sendParcels() {
        System.out.println("\n--- Отправка всех посылок ---");

        if (allParcels.isEmpty()) {
            System.out.println("Нет посылок для отправки.");
            return;
        }

        for (Parcel parcel : allParcels) {
            System.out.println();
            parcel.packageItem();
            parcel.deliver();
        }

        System.out.println("\nВсе посылки отправлены!");
    }

    private static void calculateCosts() {
        System.out.println("\n--- Расчет стоимости доставки ---");

        if (allParcels.isEmpty()) {
            System.out.println("Нет посылок для расчета.");
            return;
        }

        int totalCost = 0;
        for (Parcel parcel : allParcels) {
            int cost = parcel.calculateDeliveryCost();
            totalCost += cost;
            System.out.println("Посылка <<" + parcel.getDescription() + ">>: " + cost + " руб.");
        }

        System.out.println("\nОбщая стоимость доставки всех посылок: " + totalCost + " руб.");
    }

    private static void trackParcels() {
        System.out.println("\n--- Трекинг посылок ---");

        if (trackableItems.isEmpty()) {
            System.out.println("Нет посылок, поддерживающих трекинг.");
            return;
        }

        System.out.print("Введите новое местоположение: ");
        String newLocation = scanner.nextLine();

        for (Trackable item : trackableItems) {
            item.reportStatus(newLocation);
        }
    }

    private static void showBoxContents() {
        System.out.println("\n--- Содержимое коробок ---");
        System.out.println("Выберите тип коробки:");
        System.out.println("1 — Стандартные посылки");
        System.out.println("2 — Хрупкие посылки");
        System.out.println("3 — Скоропортящиеся посылки");
        System.out.print("Ваш выбор: ");

        try {
            int type = Integer.parseInt(scanner.nextLine());

            switch (type) {
                case 1:
                    showBoxContent(standardBox, "стандартных");
                    break;
                case 2:
                    showBoxContent(fragileBox, "хрупких");
                    break;
                case 3:
                    showBoxContent(perishableBox, "скоропортящихся");
                    break;
                default:
                    System.out.println("Неверный тип коробки!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: введите число!");
        }
    }

    private static <T extends Parcel> void showBoxContent(ParcelBox<T> box, String typeName) {
        System.out.println("\nКоробка для " + typeName + " посылок:");
        System.out.println("Заполнено: " + box.getCurrentWeight() + " кг из " + box.getMaxWeight() + " кг");
        System.out.println("Количество посылок: " + box.getParcelCount());

        List<T> parcels = box.getAllParcels();
        if (parcels.isEmpty()) {
            System.out.println("Коробка пуста.");
        } else {
            System.out.println("Список посылок:");
            for (int i = 0; i < parcels.size(); i++) {
                T parcel = parcels.get(i);
                System.out.println("  " + (i + 1) + ". " + parcel.getDescription() +
                        " (вес: " + parcel.getWeight() + " кг, адрес: " +
                        parcel.getDeliveryAddress() + ")");
            }
        }
    }
}