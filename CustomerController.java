package customermanager;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * Controller: responds to button actions, validates input,
 * calls the service and gives feedback to the user.
 */
public class CustomerController {

    private final TextField nameField;
    private final ComboBox<String> provinceBox;
    private final Label status;
    private final TableView<Customer> table;
    private final CustomerService service;

    /**
     * Creates the controller.
     *
     * @param nameField   the name input
     * @param provinceBox the province choice list
     * @param status      the feedback label
     * @param table       the customer table
     * @param service     the service that holds the data
     */
    public CustomerController(TextField nameField, ComboBox<String> provinceBox,
                              Label status, TableView<Customer> table,
                              CustomerService service) {
        this.nameField = nameField;
        this.provinceBox = provinceBox;
        this.status = status;
        this.table = table;
        this.service = service;
    }

    /** Validates the form, saves the customer and clears the form on success. */
    public void handleSave() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            showStatus("Enter the customer name.", true);
            nameField.requestFocus();
            return;
        }

        String province = provinceBox.getValue();
        if (province == null) {
            showStatus("Choose a province.", true);
            provinceBox.requestFocus();
            return;
        }

        try {
            service.addCustomer(name, province);
        } catch (RuntimeException ex) {
            // Technical details are hidden from the user; the form keeps its values.
            System.err.println("Save failed: " + ex.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Save failed");
            alert.setHeaderText("Customer could not be saved");
            alert.setContentText("Check the details and try again.");
            alert.showAndWait();
            return;
        }

        showStatus("Customer saved.", false);
        nameField.clear();
        provinceBox.setValue(null);
        nameField.requestFocus();
    }

    /** Asks for confirmation, then deletes the selected customer. */
    public void handleDelete() {
        Customer selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showStatus("Select a customer first.", true);
            return;
        }

        ButtonType delete = new ButtonType("Delete");
        Alert ask = new Alert(Alert.AlertType.CONFIRMATION,
                "Delete the selected customer?", delete, ButtonType.CANCEL);
        ask.setHeaderText("Confirm deletion");

        if (ask.showAndWait().orElse(ButtonType.CANCEL) == delete) {
            service.deleteCustomer(selected);
            showStatus("Customer deleted.", false);
        } else {
            showStatus("Deletion cancelled.", false);
        }
    }

    /**
     * Shows a message in the status label with an error or success style.
     *
     * @param message the text to display
     * @param error   true for an error style, false for a success style
     */
    private void showStatus(String message, boolean error) {
        status.getStyleClass().removeAll("status-error", "status-success");
        status.getStyleClass().add(error ? "status-error" : "status-success");
        status.setText(message);
    }
}
