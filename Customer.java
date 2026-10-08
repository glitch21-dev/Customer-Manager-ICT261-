package customermanager;

/**
 * Model class: one customer with a name and a province.
 * Author: Evidence Chisenga (202308123)
 * Immutable on purpose; table columns read it through the getters.
 */
public class Customer {

    private final String name;
    private final String province;

    /**
     * Creates a customer.
     *
     * @param name     the customer's name
     * @param province the customer's province
     */
    public Customer(String name, String province) {
        this.name = name;
        this.province = province;
    }

    /** @return the customer's name */
    public String getName() {
        return name;
    }

    /** @return the customer's province */
    public String getProvince() {
        return province;
    }
}
