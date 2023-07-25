package carsharing.businessLogic.utils.interfaces;

import carsharing.businessLogic.utils.pajos.company;

import java.util.List;

public interface CompanyDAO {

    public List<company> getAll();

    public company get(int id);

    public company get(String name);

    public void create(String name);

    public void delete(String name);

    public void delete(int id);
}
