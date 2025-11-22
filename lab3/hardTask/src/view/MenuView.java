package view;

import controller.TurnstileController;
import model.*;
import model.enums.*;

import java.time.LocalDate;
import java.util.Scanner;

public class MenuView {

    private Scanner scanner = new Scanner(System.in);

    public void start(CardRegistry registry, TurnstileController controller) {
        while (true) {
            System.out.println("\n=== MENU ===");
            System.out.println("1. Create card");
            System.out.println("2. Pass through turnstile");
            System.out.println("3. Show statistics");
            System.out.println("0. Exit");
            System.out.print("> ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> createCard(registry);
                case 2 -> passTurnstile(registry, controller);
                case 3 -> showStats(controller);
                case 0 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void createCard(CardRegistry registry) {
        System.out.println("Select card type: ");
        System.out.println("1. Time-based");
        System.out.println("2. Limited trips");
        System.out.println("3. Accumulative");

        int type = scanner.nextInt();

        System.out.print("Enter ID: ");
        String id = scanner.next();

        switch (type) {
            case 1 -> {
                System.out.println("Category: 1-Student, 2-Pupil, 3-Regular");
                int cat = scanner.nextInt();
                CardCategory category = (cat == 1) ? CardCategory.STUDENT :
                        (cat == 2) ? CardCategory.PUPIL : CardCategory.REGULAR;

                LocalDate expires = LocalDate.now().plusDays(30);
                registry.addCard(new TimeBasedCard(id, category, DurationType.MONTHLY, expires));
            }
            case 2 -> {
                System.out.print("Trips count (5 or 10): ");
                int trips = scanner.nextInt();

                System.out.println("Category: 1-Student, 2-Pupil, 3-Regular");
                int cat = scanner.nextInt();

                CardCategory category = (cat == 1) ? CardCategory.STUDENT :
                        (cat == 2) ? CardCategory.PUPIL : CardCategory.REGULAR;

                registry.addCard(new LimitedTripsCard(id, category, trips));
            }
            case 3 -> {
                System.out.print("Balance: ");
                double balance = scanner.nextDouble();
                registry.addCard(new AccumulativeCard(id, balance));
            }
            default -> System.out.println("Invalid card type.");
        }

        System.out.println("Card created.");
    }

    private void passTurnstile(CardRegistry registry, TurnstileController controller) {
        System.out.print("Enter card ID: ");
        String id = scanner.next();

        TransportCard card = registry.getCard(id);

        if (controller.tryPass(card)) {
            System.out.println("PASS ALLOWED");
        } else {
            System.out.println("PASS DENIED");
        }
    }

    private void showStats(TurnstileController controller) {
        System.out.println("Successful passes: " + controller.getSuccessCount());
        System.out.println("Denied passes: " + controller.getDeniedCount());
    }
}
