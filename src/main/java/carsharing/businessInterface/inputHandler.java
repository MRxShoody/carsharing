package carsharing.businessInterface;

import carsharing.businessLogic.AdvancedDaoPattern.car.dbCarDAO;
import carsharing.businessLogic.AdvancedDaoPattern.company.dbCompanyDAO;
import carsharing.businessLogic.AdvancedDaoPattern.customer.dbCustomerDAO;
import carsharing.businessLogic.utils.interfaces.CarDAO;
import carsharing.businessLogic.utils.interfaces.CompanyDAO;
import carsharing.businessLogic.utils.interfaces.CustomerDAO;

import java.util.Scanner;

public class inputHandler {

    static CustomerDAO customerDAO;
    static CompanyDAO companyDAO;
    static CarDAO carDAO;
    static final Scanner scanner = new Scanner(System.in);

    public static void inputsHandlerStart() {
        companyDAO = new dbCompanyDAO();
        carDAO = new dbCarDAO();
        customerDAO = new dbCustomerDAO();

        int i;
        while ((i = startPage()) != 0) {
            switch (i) {
                case 1 -> companyHandlers.handleCompanyMenu();
                case 2 -> customerHandlers.handleCustomerList();
                case 3 -> customerHandlers.handleCreateCustomer();
            }
        }
    }

    private static int startPage() {
        System.out.print("""   
                1. Log in as a manager
                2. Log in as a customer
                3. Create a customer
                0. Exit
                """);
        return Integer.parseInt(scanner.nextLine());

    }
}

