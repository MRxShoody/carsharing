package carsharing.businessLogic.AdvancedDaoPattern.company;

import carsharing.businessLogic.AdvancedDaoPattern.dbClient;
import carsharing.businessLogic.utils.interfaces.CompanyDAO;
import carsharing.businessLogic.utils.pajos.company;
import org.h2.jdbcx.JdbcDataSource;

import java.util.List;

public class dbCompanyDAO implements CompanyDAO {

    private final String createCompany = "INSERT INTO COMPANY (NAME) VALUES ('%s')";
    private final String deleteCompanyName = "DELETE FROM COMPANY WHERE NAME = '%s'";
    private final String deleteCompanyID = "DELETE FROM COMPANY WHERE ID = %d";
    private final String getAllCompanies = "SELECT * FROM COMPANY";
    private final String getCompanyName = "SELECT * FROM COMPANY WHERE NAME = '%s'";
    private final String getComapnyID = "SELECT * FROM COMPANY WHERE ID = %d";
    private final String URL = "jdbc:h2:./src/carsharing/db/carsharing";
    private final String createDB = """
            CREATE TABLE IF NOT EXISTS COMPANY (
            ID INT PRIMARY KEY AUTO_INCREMENT,
            NAME VARCHAR(255) NOT NULL UNIQUE)
            """;

    private final dbClient<company> dbClient;

    public dbCompanyDAO() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(URL);

        dbClient = new dbClient<>(dataSource, company.class);
        dbClient.run(createDB);

    }

    @Override
    public List<company> getAll() {
        return dbClient.selectForList(getAllCompanies);
    }

    @Override
    public company get(int id) {
        return dbClient.select(String.format(getComapnyID, id));
    }

    @Override
    public company get(String name) {
        return dbClient.select(String.format(getCompanyName, name));
    }

    @Override
    public void create(String name) {
        dbClient.run(String.format(createCompany, name));
    }

    @Override
    public void delete(String name) {
        dbClient.run(String.format(deleteCompanyName, name));
    }

    @Override
    public void delete(int id) {
        dbClient.run(String.format(deleteCompanyID, id));
    }
}
