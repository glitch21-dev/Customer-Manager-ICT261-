package customermanager;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class App extends Application {

    private static final String PROMPT = "Choose a province";

    @Override
    public void start(Stage stage) {
        CustomerService service = new CustomerService();

        // --- Form: name field ---
        Label nameLabel = new Label("Customer name");
        TextField nameField = new TextField();
        nameField.setPromptText("e.g., Mary Banda");
        nameLabel.setLabelFor(nameField);

        // --- Form: province list ---
        Label provinceLabel = new Label("Province");
        ComboBox<String> provinceBox = new ComboBox<>();
        provinceBox.getItems().addAll(
                "Central", "Copperbelt", "Eastern", "Luapula", "Lusaka",
                "Muchinga", "Northern", "North-Western", "Southern", "Western");
        provinceBox.setPromptText(PROMPT);
        provinceBox.setMaxWidth(Double.MAX_VALUE);
        provinceLabel.setLabelFor(provinceBox);
        // Keeps the prompt text visible again after the form is cleared.
        provinceBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? PROMPT : item);
            }
        });

        // --- Buttons and status ---
        Button saveButton = new Button("Save customer");
        saveButton.setDefaultButton(true);
        Button deleteButton = new Button("Delete selected");
        HBox buttons = new HBox(10, saveButton, deleteButton);

        Label status = new Label();
        status.setWrapText(true);

        // --- Table ---
        TableView<Customer> table = new TableView<>();
        table.setItems(service.getCustomers());
        table.setPlaceholder(new Label("No customers yet."));

        TableColumn<Customer, String> nameCol = new TableColumn<>("Customer name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(260);

        TableColumn<Customer, String> provinceCol = new TableColumn<>("Province");
        provinceCol.setCellValueFactory(new PropertyValueFactory<>("province"));
        provinceCol.setPrefWidth(200);

        table.getColumns().add(nameCol);
        table.getColumns().add(provinceCol);
        VBox.setVgrow(table, Priority.ALWAYS);

        // --- Controller wiring ---
        CustomerController controller =
                new CustomerController(nameField, provinceBox, status, table, service);
        saveButton.setOnAction(event -> controller.handleSave());
        deleteButton.setOnAction(event -> controller.handleDelete());

        // --- Layout ---
        Label title = new Label("Customer Manager");
        title.getStyleClass().add("title-label");

        VBox root = new VBox(10, title, nameLabel, nameField,
                provinceLabel, provinceBox, buttons, status, table);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 520, 620);
        if (App.class.getResource("/style.css") != null) {
            scene.getStylesheets().add(App.class.getResource("/style.css").toExternalForm());
        } else {
            System.err.println("style.css not found; using default look.");
        }

        stage.setTitle("Customer Manager - Evidence Chisenga (202308123)");
        stage.setScene(scene);
        stage.show();
        Platform.runLater(nameField::requestFocus);
    }

    /**
     * Application entry point.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        launch(args);
    }
}
