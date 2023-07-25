package carsharing.businessInterface;

import carsharing.businessLogic.utils.interfaces.CarDAO;
import carsharing.businessLogic.utils.interfaces.CompanyDAO;
import carsharing.businessLogic.utils.interfaces.CustomerDAO;
import carsharing.businessLogic.utils.pajos.car;
import carsharing.businessLogic.utils.pajos.company;
import carsharing.businessLogic.utils.pajos.customer;

import java.util.List;
import java.util.Scanner;

import static carsharing.businessInterface.carHandlers.handleCarList;

public class customerHandlers {

    private static final Scanner scanner = inputHandler.scanner;
    private static final CustomerDAO customerDAO = inputHandler.customerDAO;
    private static final CompanyDAO companyDAO = inputHandler.companyDAO;
    private static final CarDAO carDAO = inputHandler.carDAO;


    static void handleCustomerList() {
        List<customer> customers = customerDAO.getAll();
        if (customers.isEmpty()) {
            System.out.println("The customer list is empty!");
            return;
        }
        System.out.println("Customer list:");
        for (int i = 0; i < customers.size(); i++) {
            System.out.println(i + 1 + ". " + customers.get(i).name());
        }
        int i = Integer.parseInt(scanner.nextLine());
        handleRentCarMenu(customers.get(i - 1).id());
    }

    static void handleRentCarMenu(int i) {

        while (true) {
            System.out.println("""
                    1. Rent a car
                    2. Return a rented car
                    3. My rented car
                    0. Back""");

            int m = Integer.parseInt(scanner.nextLine());
            switch (m) {
                case 0 -> {
                    return;
                }
                case 1 -> handleRentCar(i);
                case 2 -> handleReturnRentedCar(i);
                case 3 -> handleMyRentedCar(i);
                default -> System.out.println("Unknown input");
            }
        }
    }

    private static void handleRentCar(int id) {
        if (customerDAO.get(id).rentedCarId() != 0) {
            System.out.println("You've already rented a car!");
            return;
        }
        List<company> companies = companyDAO.getAll();

        int i = companyHandlers.handleCompanyList(true);

        System.out.println("Choose a car:");
        int id1 = companies.get(i - 1).id();
        handleCarList(id1, companies.get(i - 1).name());
        System.out.println("0. Back");
        int j = Integer.parseInt(scanner.nextLine());
        if (j == 0) {
            return;
        }
        List<car> cars = carDAO.getAll(id1);
        int d = cars.get(j - 1).id();
        customerDAO.addRentedCar(id, d);
        carDAO.rentedToggle(d, true);
        System.out.println("You rented '" + cars.get(j - 1).name() + "'");
    }

    private static void handleMyRentedCar(int i) {
        int d = customerDAO.get(i).rentedCarId();
        if (d == 0) {
            System.out.println("You didn't rent a car!");
            return;
        }
        String name = carDAO.get(d).name();
        int companyID = carDAO.get(d).companyId();
        String companyName = companyDAO.get(companyID).name();

        System.out.printf("""
                Your rented car:
                %s
                Company:
                %s%n""", name, companyName);
    }

    private static void handleReturnRentedCar(int i) {
        int d = customerDAO.get(i).rentedCarId();
        if (d == 0) {
            System.out.println("You didn't rent a car!");
            return;
        }
        carDAO.rentedToggle(d, false);
        customerDAO.returnRentedCar(i);
        System.out.println("You've returned a rented car!");
    }

    static void handleCreateCustomer() {
        System.out.println("Enter the customer name:");
        String name = scanner.nextLine();
        customerDAO.create(name);
        System.out.println("The customer was added!");
    }
}
