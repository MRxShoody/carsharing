package carsharing.businessLogic.AdvancedDaoPattern.customer;

import carsharing.businessLogic.AdvancedDaoPattern.dbClient;
import carsharing.businessLogic.utils.interfaces.CustomerDAO;
import carsharing.businessLogic.utils.pajos.customer;
import org.h2.jdbcx.JdbcDataSource;

import java.util.List;

public class dbCustomerDAO implements CustomerDAO {

    private final String createCustomer = "INSERT INTO CUSTOMER (NAME) VALUES ('%s')";
    private final String deleteCustomerName = "DELETE FROM CUSTOMER WHERE NAME = '%s'";
    private final String deleteCustomerID = "DELETE FROM CUSTOMER WHERE ID = %d";
    private final String getAllCustomers = "SELECT * FROM CUSTOMER";
    private final String getCustomerName = "SELECT * FROM CUSTOMER WHERE NAME = '%s'";
    private final String getCustomerID = "SELECT * FROM CUSTOMER WHERE ID = %d";

    private final String addRentedCar = "UPDATE CUSTOMER SET RENTED_CAR_ID = %d WHERE ID = %d";
    private final String returnRentedCar = "UPDATE CUSTOMER SET RENTED_CAR_ID = NULL WHERE ID = %d";
    private final String URL = "jdbc:h2:./src/carsharing/db/carsharing";
    private final String createDB = """
            CREATE TABLE IF NOT EXISTS CUSTOMER (
            ID INT PRIMARY KEY AUTO_INCREMENT,
            NAME VARCHAR(255) NOT NULL UNIQUE,
            RENTED_CAR_ID INT DEFAULT NULL,
            CONSTRAINT fk_rented_car foreign key (RENTED_CAR_ID) 
            references CAR(ID)
            ON DELETE CASCADE)
            """;

    private final dbClient<customer> dbClient;

    public dbCustomerDAO() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(URL);

        dbClient = new dbClient<>(dataSource, customer.class);
        dbClient.run(createDB);

    }

    @Override
    public List<customer> getAll() {
        return dbClient.selectForList(getAllCustomers);
    }

    @Override
    public customer get(int id) {
        return dbClient.select(String.format(getCustomerID, id));
    }

    @Override
    public customer get(String name) {
        return dbClient.select(String.format(getCustomerName, name));
    }

    @Override
    public void create(String name) {
        dbClient.run(String.format(createCustomer, name));
    }

    @Override
    public void delete(String name) {
        dbClient.run(String.format(deleteCustomerName, name));
    }

    @Override
    public void delete(int id) {
        dbClient.run(String.format(deleteCustomerID, id));
    }

    @Override
    public void addRentedCar(int id, int car) {
        dbClient.run(String.format(addRentedCar, car, id));
    }

    @Override
    public void returnRentedCar(int id) {
        dbClient.run(String.format(returnRentedCar, id));
    }

}
