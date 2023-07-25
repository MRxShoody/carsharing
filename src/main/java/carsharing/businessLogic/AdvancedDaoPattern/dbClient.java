package carsharing.businessLogic.AdvancedDaoPattern;

import carsharing.businessLogic.utils.pajos.car;
import carsharing.businessLogic.utils.pajos.customer;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class dbClient<T> {

    private final DataSource dataSource;
    private final Class<T> c;

    public dbClient(DataSource dataSource, Class<T> c) {
        this.c = c;
        this.dataSource = dataSource;
    }

    public void run(String str) {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(str);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public T select(String query) {
        List<T> entities = selectForList(query);
        if (entities.size() == 1) {
            return entities.get(0);
        } else if (entities.size() == 0) {
            return null;
        } else {
            throw new IllegalStateException("Query returned more than one object");
        }
    }

    public List<T> selectForList(String query) {
        List<T> entities = new ArrayList<>();

        try (Connection con = dataSource.getConnection();
             Statement statement = con.createStatement();
             ResultSet resultSetItem = statement.executeQuery(query)
        ) {
            while (resultSetItem.next()) {
                int id = resultSetItem.getInt("id");
                String name = resultSetItem.getString("name");
                T entity;
                if (c.equals(customer.class)) {
                    int rented_car_id = resultSetItem.getInt("rented_car_id");
                    entity = c.getConstructor(int.class, String.class, int.class).newInstance(id, name, rented_car_id);
                } else if (c.equals(car.class)) {
                    int company_id = resultSetItem.getInt("company_id");
                    boolean is_rented = resultSetItem.getBoolean("is_rented");
                    entity = c.getConstructor(int.class, String.class, int.class, boolean.class).newInstance(id, name, company_id, is_rented);

                } else {
                    entity = c.getConstructor(int.class, String.class).newInstance(id, name);
                }
                entities.add(entity);
            }

            return entities;
        } catch (SQLException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            e.printStackTrace();
        }

        return entities;
    }

}
