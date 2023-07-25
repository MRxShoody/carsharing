package carsharing.businessLogic.DaoPattern.car;

import carsharing.businessLogic.utils.interfaces.CarDAO;
import carsharing.businessLogic.utils.pajos.car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class carDAOimpl implements CarDAO {

    private final PreparedStatement getAllByCompanyID;
    private final PreparedStatement createCar;
    private final PreparedStatement deleteCarName;
    private final PreparedStatement deleteCarID;
    private final PreparedStatement getAllCars;
    private final PreparedStatement getCarName;
    private final PreparedStatement getCarID;

    public carDAOimpl() {
        try {
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection("jdbc:h2:./src/carsharing/db/carsharing");
            connection.setAutoCommit(true);
            Statement statement = connection.createStatement();
            statement.executeUpdate(
                    """
                            CREATE TABLE IF NOT EXISTS CAR (
                            ID INT PRIMARY KEY AUTO_INCREMENT,
                            NAME VARCHAR(255) UNIQUE NOT NULL,
                            COMPANY_ID INT NOT NULL,
                            CONSTRAINT FK_COMPANY_ID FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID),
                            IS_RENTED BOOLEAN DEFAULT FALSE)
                            """);
            statement.close();
            deleteCarName = connection.prepareStatement("DELETE FROM CAR WHERE NAME = ?");
            createCar = connection.prepareStatement("INSERT INTO CAR (NAME,COMPANY_ID) VALUES (?,?)");
            deleteCarID = connection.prepareStatement("DELETE FROM CAR WHERE ID = ?");
            getAllCars = connection.prepareStatement("SELECT * FROM CAR");
            getCarName = connection.prepareStatement("SELECT * FROM CAR WHERE NAME = ?");
            getCarID = connection.prepareStatement("SELECT * FROM CAR WHERE ID = ?");
            getAllByCompanyID = connection.prepareStatement("SELECT * FROM CAR WHERE COMPANY_ID = ?");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<car> getAll(int id) {
        try {
            List<car> cars = new ArrayList<>();
            getAllByCompanyID.setInt(1, id);
            ResultSet resultSet = getAllByCompanyID.executeQuery();
            while (resultSet.next()) {
                cars.add(new car(resultSet.getInt("ID"), resultSet.getString("NAME"), resultSet.getInt("COMPANY_ID")));
            }
            return cars;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<car> getAll() {
        try {
            List<car> cars = new ArrayList<>();
            ResultSet resultSet = getAllCars.executeQuery();
            while (resultSet.next()) {
                cars.add(new car(resultSet.getInt("ID"), resultSet.getString("NAME"), resultSet.getInt("COMPANY_ID")));
            }
            return cars;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public car get(int id) {
        try {
            getCarID.setInt(1, id);
            ResultSet resultSet = getCarID.executeQuery();
            if (resultSet.next()) {
                return new car(resultSet.getInt("ID"), resultSet.getString("NAME"), resultSet.getInt("COMPANY_ID"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public car get(String name) {
        try {
            getCarName.setString(1, name);
            ResultSet resultSet = getCarName.executeQuery();
            if (resultSet.next()) {
                return new car(resultSet.getInt("ID"), resultSet.getString("NAME"), resultSet.getInt("COMPANY_ID"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(String car, int ID) {
        try {
            createCar.setString(1, car);
            createCar.setInt(2, ID);
            createCar.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String car) {
        try {
            deleteCarName.setString(1, car);
            deleteCarName.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try {
            deleteCarID.setInt(1, id);
            deleteCarID.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Deprecated(since = "1.0.0", forRemoval = true)
    public void rentedToggle(int id, boolean isRented) {

    }
}
