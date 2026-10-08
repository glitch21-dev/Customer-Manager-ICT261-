package customermanager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Service: holds the application rules and the in-memory customer list.
 * The same ObservableList is attached to the TableView, so any add or
 * remove here updates the table automatically.
 */
public class CustomerService {

    private final ObservableList<Customer> customers = FXCollections.observableArrayList();

    /** @return the observable list that the TableView displays */
    public ObservableList<Customer> getCustomers() {
        return customers;
    }

    /**
     * Creates a customer and adds it to the list.
     *
     * @param name     the customer's name (must not be blank)
     * @param province the customer's province (must not be blank)
     * @throws IllegalArgumentException if the name or province is missing
     */
    public void addCustomer(String name, String province) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Enter the customer name.");
        }
        if (province == null || province.isBlank()) {
            throw new IllegalArgumentException("Choose a province.");
        }
        customers.add(new Customer(name.trim(), province));
    }

    /**
     * Removes a customer from the list.
     *
     * @param customer the customer to remove (must not be null)
     * @throws IllegalArgumentException if no customer is given
     */
    public void deleteCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Select a customer first.");
        }
        customers.remove(customer);
    }
}
