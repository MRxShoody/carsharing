package carsharing.businessLogic.AdvancedDaoPattern.car;

import carsharing.businessLogic.AdvancedDaoPattern.dbClient;
import carsharing.businessLogic.utils.interfaces.CarDAO;
import carsharing.businessLogic.utils.pajos.car;
import org.h2.jdbcx.JdbcDataSource;

import java.util.List;

public class dbCarDAO implements CarDAO {

    private final String getAllCarID = "SELECT * FROM CAR WHERE COMPANY_ID = '%d'";
    private final String createCar = "INSERT INTO CAR (NAME,COMPANY_ID) VALUES ('%s',%d)";
    private final String deleteCarName = "DELETE FROM CAR WHERE NAME = '%s'";
    private final String deleteCarID = "DELETE FROM CAR WHERE ID = %d";
    private final String getAllCars = "SELECT * FROM CAR";
    private final String getCarName = "SELECT * FROM CAR WHERE NAME = '%s'";
    private final String getCarID = "SELECT * FROM CAR WHERE ID = %d";
    private final String Rented = "UPDATE CAR SET IS_RENTED = TRUE WHERE ID = %d";
    private final String Returned = "UPDATE CAR SET IS_RENTED = FALSE WHERE ID = %d";
    private final String URL = "jdbc:h2:./src/carsharing/db/carsharing";
    private final String createDB = """
            CREATE TABLE IF NOT EXISTS CAR (
            ID INT PRIMARY KEY AUTO_INCREMENT,
            NAME VARCHAR(255) UNIQUE NOT NULL,
            COMPANY_ID INT NOT NULL,
            CONSTRAINT FK_COMPANY_ID FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID),
            IS_RENTED BOOLEAN DEFAULT FALSE)
            """;
    private final dbClient<car> dbClient;

    public dbCarDAO() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(URL);

        dbClient = new dbClient<>(dataSource, car.class);
        dbClient.run(createDB);
    }


    @Override
    public List<car> getAll(int id) {
        return dbClient.selectForList(String.format(getAllCarID, id));
    }

    @Override
    public List<car> getAll() {
        return dbClient.selectForList(getAllCars);
    }

    @Override
    public car get(int id) {
        return dbClient.select(String.format(getCarID, id));
    }

    @Override
    public car get(String name) {
        return dbClient.select(String.format(getCarName, name));
    }

    @Override
    public void create(String car, int ID) {
        dbClient.run(String.format(createCar, car, ID));
    }

    @Override
    public void delete(String car) {
        dbClient.run(String.format(deleteCarName, car));
    }

    @Override
    public void delete(int id) {
        dbClient.run(String.format(deleteCarID, id));
    }
    @Override
    public void rentedToggle(int id, boolean isRented) {
        if (isRented) {
            dbClient.run(String.format(Rented, id));
        } else {
            dbClient.run(String.format(Returned, id));
        }
    }
}
