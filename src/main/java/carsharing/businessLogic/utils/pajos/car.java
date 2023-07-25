package carsharing.businessLogic.utils.pajos;

public record car(int id, String name, int companyId, boolean isRented) {

    public car(int id, String name, int companyId) {
        this(id, name, companyId, false);
    }
}
