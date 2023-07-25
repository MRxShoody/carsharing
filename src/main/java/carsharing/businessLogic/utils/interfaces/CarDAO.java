package carsharing.businessLogic.utils.interfaces;

import carsharing.businessLogic.utils.pajos.car;

import java.util.List;

public interface CarDAO {


    public List<car> getAll(int id);

    public List<car> getAll();

    public car get(int id);

    public car get(String name);

    public void create(String car, int ID);

    public void delete(String name);

    public void delete(int id);

    public void rentedToggle(int id, boolean isRented);
}
