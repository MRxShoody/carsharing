package carsharing.businessInterface;

import carsharing.businessLogic.utils.interfaces.CompanyDAO;
import carsharing.businessLogic.utils.pajos.company;

import java.util.List;
import java.util.Scanner;

import static carsharing.businessInterface.carHandlers.handleCarMenu;

public class companyHandlers {

    private static final CompanyDAO companyDao = inputHandler.companyDAO;
    private static final Scanner scanner = inputHandler.scanner;

    static void handleCompanyMenu() {
        int i;
        do {
            System.out.print("""
                    1. Company list
                    2. Create a company
                    0. Back""");
            i = Integer.parseInt(scanner.nextLine());
            switch (i) {
                case 0 -> {
                    return;
                }
                case 1 -> handleCompanyList(false);
                case 2 -> handleCreateCompany();
                default -> System.out.println("Unknown input");
            }
        } while (true);
    }

    static int handleCompanyList(boolean customerContext) {
        List<company> companies = companyDao.getAll();
        if (companies.isEmpty()) {
            System.out.println("The company list is empty!");
            return 0;
        }
        System.out.println("Choose the company:");
        for (int i = 0; i < companies.size(); i++) {
            System.out.println(i + 1 + ". " + companies.get(i).name());
        }
        System.out.println("0. Back");
        int i = Integer.parseInt(scanner.nextLine());
        if (i == 0) {
            return 0;
        }
        if(!customerContext)
            handleCarMenu(companies.get(i - 1).id(), companies.get(i - 1).name());

        return i;
    }

    private static void handleCreateCompany() {
        System.out.println("Enter the company name:");
        companyDao.create(scanner.nextLine());
        System.out.println("The company was created!");
    }

}
