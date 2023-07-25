package carsharing.businessInterface;

import carsharing.businessLogic.utils.interfaces.CarDAO;
import carsharing.businessLogic.utils.pajos.car;

import java.util.List;
import java.util.Scanner;

public class carHandlers {

    private static final Scanner scanner = inputHandler.scanner;
    private static final CarDAO carDAO = inputHandler.carDAO;

    static void handleCarMenu(int id, String companyName) {
        while (true) {
            System.out.printf("""
                    '%s' company
                    1. Car list
                    2. Create a car
                    0. Back""", companyName);
            int i = Integer.parseInt(scanner.nextLine());
            switch (i) {
                case 0 -> {
                    return;
                }
                case 1 -> handleCarList(id, companyName);
                case 2 -> handleCreateCar(id);
                default -> System.out.println("Unknown input");
            }
        }
    }

    static void handleCarList(int id, String companyName) {
        List<car> cars = carDAO.getAll(id);
        if (cars.isEmpty()) {
            System.out.println("The car list is empty!");
            return;
        }
        System.out.println("'" + companyName + "' cars:");
        for (int i = 0; i < cars.size(); i++) {
            if (!cars.get(i).isRented())
                System.out.println(i + 1 + ". " + cars.get(i).name());
        }
    }

    static void handleCreateCar(int id) {
        System.out.println("Enter the car name:");
        String carName = scanner.nextLine();
        carDAO.create(carName, id);
        System.out.println("The car was added!");
    }
}
