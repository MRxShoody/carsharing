package carsharing.businessLogic.DaoPattern.company;

import carsharing.businessLogic.utils.interfaces.CompanyDAO;
import carsharing.businessLogic.utils.pajos.company;
import org.h2.jdbcx.JdbcDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class companyDAOimpl implements CompanyDAO {
    private final PreparedStatement createCompany;
    private final PreparedStatement deleteCompanyName;
    private final PreparedStatement deleteCompanyID;
    private final PreparedStatement getAllCompanies;
    private final PreparedStatement getCompanyName;
    private final PreparedStatement getCompanyID;

    public companyDAOimpl() {
        try {
            JdbcDataSource dataSource = new JdbcDataSource();
            dataSource.setURL("jdbc:h2:./src/carsharing/db/carsharing");
            Connection connection = dataSource.getConnection();
            connection.setAutoCommit(true);
            Statement statement = connection.createStatement();
            statement.executeUpdate(
                    """
                            CREATE TABLE IF NOT EXISTS COMPANY (
                            ID INT PRIMARY KEY AUTO_INCREMENT,
                            NAME VARCHAR(255) NOT NULL UNIQUE)
                            """);
            statement.close();
            deleteCompanyName = connection.prepareStatement("DELETE FROM COMPANY WHERE NAME = ?");
            createCompany = connection.prepareStatement("INSERT INTO COMPANY (NAME) VALUES (?)");
            deleteCompanyID = connection.prepareStatement("DELETE FROM COMPANY WHERE ID = ?");
            getAllCompanies = connection.prepareStatement("SELECT * FROM COMPANY");
            getCompanyName = connection.prepareStatement("SELECT * FROM COMPANY WHERE NAME = ?");
            getCompanyID = connection.prepareStatement("SELECT * FROM COMPANY WHERE ID = ?");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<company> getAll() {
        try {
            List<company> companies = new ArrayList<>();
            ResultSet resultSet = getAllCompanies.executeQuery();
            while (resultSet.next()) {
                companies.add(new company(resultSet.getInt("ID"), resultSet.getString("NAME")));
            }

            return companies;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public company get(int id) {
        try {
            getCompanyID.setInt(1, id);
            ResultSet resultSet = getCompanyID.executeQuery();

            if (resultSet.next()) {
                return new company(resultSet.getInt("ID"), resultSet.getString("NAME"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public company get(String name) {
        try {
            getCompanyName.setString(1, name);
            ResultSet resultSet = getCompanyName.executeQuery();

            if (resultSet.next()) {
                return new company(resultSet.getInt("ID"), resultSet.getString("NAME"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void create(String company) {
        try {
            createCompany.setString(1, company);
            createCompany.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String company) {
        try {
            deleteCompanyName.setString(1, company);
            deleteCompanyName.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try {
            deleteCompanyID.setInt(1, id);
            deleteCompanyID.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
