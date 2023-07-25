package carsharing.businessLogic.utils.interfaces;

import carsharing.businessLogic.utils.pajos.customer;

import java.util.List;

public interface CustomerDAO {

    public List<customer> getAll();

    public customer get(int id);

    public customer get(String name);

    public void create(String name);

    public void delete(String name);

    public void delete(int id);

    public void addRentedCar(int id, int car);

    public void returnRentedCar(int id);

}
