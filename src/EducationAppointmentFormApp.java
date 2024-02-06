import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.*;
import java.util.List;
import java.util.Optional;
import javafx.util.Callback;

public class EducationAppointmentFormApp extends Application {

    private TableView<Appointment> tableView;
    private ObservableList<Appointment> appointments;
    private TextField searchField;
    private TextField titleField;
    private TextField nameField;
    private TextField emailField;
    private TextField phoneField;
    private ComboBox<String> courseTypeComboBox;
    private ComboBox<String> phoneLocationComboBox;
    private ComboBox<String> hoursComboBox;
    private CheckBox termsCheckBox;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Education Appointment Form");

        AnchorPane root = new AnchorPane();
        root.setId("parent");

        //Pane to hold the form elements
        VBox formPane = new VBox(10);
        formPane.setPadding(new Insets(20));
        formPane.setId("child");

        Label titleLabel = new Label("Education Appointment Form");
        titleLabel.setId("title");

        titleField = new TextField();
        titleField.setPromptText("Title");

        nameField = new TextField();
        nameField.setPromptText("Your Name");

        emailField = new TextField();
        emailField.setPromptText("Email");
        
        phoneField = new TextField();
        phoneField.setPromptText("Phone Number");

        courseTypeComboBox = new ComboBox<>();
        courseTypeComboBox.getItems().addAll(
                "Lecture",
                "Laboratory",
                "Lecture Lab",
                "Seminar",
                "Studio",
                "Independent Study",
                "Research",
                "Internship/Practicum");
        courseTypeComboBox.setPromptText("Course Type");
        courseTypeComboBox.setPrefWidth(350);

        //Create a custom cell factory to set the text color
        courseTypeComboBox.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item);
                            setStyle("-fx-text-fill: black;"); //Set the text color of the dropdown items
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });

        //Create a custom cell factory for the selected item
        courseTypeComboBox.setButtonCell(new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item);
                    setStyle("-fx-text-fill: black;"); //Set the text color of the selected item in the text field
                } else {
                    setText(null);
                }
            }
        });

        Label locationLabel = new Label("How would you like to be located?");
        locationLabel.setId("located");

        phoneLocationComboBox = new ComboBox<>();
        phoneLocationComboBox.getItems().addAll("By Phone", "By Email");
        phoneLocationComboBox.setPromptText("By Phone");
        phoneLocationComboBox.setPrefWidth(350);

        //Create a custom cell factory to set the text color
        phoneLocationComboBox.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item);
                            setStyle("-fx-text-fill: black;"); //Set the text color of the dropdown items
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });

        //Create a custom cell factory for the selected item
        phoneLocationComboBox.setButtonCell(new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item);
                    setStyle("-fx-text-fill: black;"); //Set the text color of the selected item in the text field
                } else {
                    setText(null);
                }
            }
        });

       hoursComboBox = new ComboBox<>();
       hoursComboBox.getItems().addAll(
                "8am - 10 am",
                "9am - 11am",
                "10am - 12pm",
                "1pm - 3pm",
                "2pm - 4pm",
                "3pm - 5pm",
                "4pm - 6pm",
                "5pm - 7pm",
                "6pm - 8pm",
                "7pm- 9pm",
                "8pm - 10pm");
        hoursComboBox.setPromptText("Hours: 8am - 10pm");
        hoursComboBox.setPrefWidth(350);

        //Create a custom cell factory to set the text color
        hoursComboBox.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item);
                            setStyle("-fx-text-fill: black;"); //Set the text color of the dropdown items
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });

        //Create a custom cell factory for the selected item
        hoursComboBox.setButtonCell(new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item);
                    setStyle("-fx-text-fill: black;"); //Set the text color of the selected item in the text field
                } else {
                    setText(null);
                }
            }
        });

       termsCheckBox = new CheckBox("I agree to the Terms and Conditions");
       termsCheckBox.setId("tnc");
       
       Button requestButton = new Button("Request an Appointment");
       requestButton.setOnAction(e -> {
        if (!termsCheckBox.isSelected()) {
            showAlert("Terms and Conditions", "Please agree to the Terms and Conditions.");
            return;
        }
        
        String title = titleField.getText();
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String courseType = courseTypeComboBox.getValue();
        String location = phoneLocationComboBox.getValue();
        String hours = hoursComboBox.getValue();
        boolean agreedToTerms = termsCheckBox.isSelected();
        
        Appointment appointment = new Appointment(title, name, email, phone, courseType, location, hours, agreedToTerms);
        appointments.add(appointment);
        try {
            saveAppointmentsToCSV(appointments);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        clearFormFields();

        //Reset the text color of the selected item in the text field to gray
        courseTypeComboBox.setButtonCell(new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item);
                    setStyle("-fx-text-fill: gray;"); // Set the text color of the selected item in the text field to gray
                } else {
                    setText(null);
                }
            }
        });

        //Reset the text color of the selected item in the text field to gray
        phoneLocationComboBox.setButtonCell(new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item);
                    setStyle("-fx-text-fill: gray;"); // Set the text color of the selected item in the text field to gray
                } else {
                    setText(null);
                }
            }
        });

        //Reset the text color of the selected item in the text field to gray
        hoursComboBox.setButtonCell(new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item);
                    setStyle("-fx-text-fill: gray;"); // Set the text color of the selected item in the text field to gray
                } else {
                    setText(null);
                }
            }
        });

        System.out.println("Appointment Request:");
        System.out.println("Title: " + title);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Phone Number: " + phone);
        System.out.println("Course Type: " + courseType);
        System.out.println("Location: " + location);
        System.out.println("Hours: " + hours);
        System.out.println("Agreed to Terms: " + agreedToTerms);
        
        loadAppointmentsFromCSV();
        displayAppointmentsInTableView();
        });
        
        formPane.getChildren().addAll(
                titleLabel,
                titleField,
                nameField,
                emailField,
                phoneField,
                courseTypeComboBox,
                locationLabel,
                phoneLocationComboBox,
                hoursComboBox,
                termsCheckBox,
                requestButton
        );

        formPane.setSpacing(30);
        formPane.setAlignment(Pos.TOP_LEFT);

        root.getChildren().add(formPane);

        tableView = new TableView<>();
        tableView.setPlaceholder(new Label("No appointments"));
        tableView.setPrefWidth(600);
        tableView.setPrefHeight(620);

        //Set the size of columns to constrained size
        TableColumn<Appointment, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setPrefWidth(0.15 * tableView.getPrefWidth()); //Set the width as a percentage of the tableView width
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Appointment, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setPrefWidth(0.15 * tableView.getPrefWidth()); 
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Appointment, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setPrefWidth(0.15 * tableView.getPrefWidth()); 
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Appointment, String> phoneColumn = new TableColumn<>("Phone Number");
        phoneColumn.setPrefWidth(0.15 * tableView.getPrefWidth()); 
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        TableColumn<Appointment, String> courseTypeColumn = new TableColumn<>("Course Type");
        courseTypeColumn.setPrefWidth(0.1 * tableView.getPrefWidth());
        courseTypeColumn.setCellValueFactory(new PropertyValueFactory<>("courseType"));

        TableColumn<Appointment, String> locationColumn = new TableColumn<>("Location");
        locationColumn.setPrefWidth(0.1 * tableView.getPrefWidth()); 
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

        TableColumn<Appointment, String> hoursColumn = new TableColumn<>("Hours");
        hoursColumn.setPrefWidth(0.1 * tableView.getPrefWidth()); 
        hoursColumn.setCellValueFactory(new PropertyValueFactory<>("hours"));

        List<TableColumn<Appointment, ?>> columns = tableView.getColumns();
        columns.add(titleColumn);
        columns.add(nameColumn);
        columns.add(emailColumn);
        columns.add(phoneColumn);
        columns.add(courseTypeColumn);
        columns.add(locationColumn);
        columns.add(hoursColumn);

        //Set column resize policy to adjust column widths based on content
        tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        //Set preferred width for each column
        titleColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.05));
        nameColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.25));
        emailColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.27));
        phoneColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.12));
        courseTypeColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.20));
        locationColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));
        hoursColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.11));

        //Display appointments in TableView
        tableView.setItems(appointments);

        //Set the selection mode to multiple
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //Context menu for delete functionality
        ContextMenu contextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(e -> {
            ObservableList<Appointment> selectedAppointments = tableView.getSelectionModel().getSelectedItems();
            if (!selectedAppointments.isEmpty()) {
                String confirmationMessage;
                if (selectedAppointments.size() == 1) {
                    confirmationMessage = "Are you sure you want to delete this appointment?";
                } else {
                    confirmationMessage = "Are you sure you want to delete the selected appointments?";
                }
        
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Confirmation");
                confirmationAlert.setHeaderText(null);
                confirmationAlert.setContentText(confirmationMessage);

                Optional<ButtonType> result = confirmationAlert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    appointments.removeAll(selectedAppointments);
                    try {
                        saveAppointmentsToCSV(appointments);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        
        contextMenu.getItems().add(deleteMenuItem);
        tableView.setContextMenu(contextMenu);

        //Search field
        searchField = new TextField();
        searchField.setPromptText("Search");
        searchField.setPrefWidth(200);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterAppointments(newValue);
        });

        Label searchLabel = new Label("Appointment List");
        searchLabel.setId("search-label");

        searchField = new TextField();
        searchField.setPromptText("Search");
        searchField.setPrefWidth(200);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterAppointments(newValue);
        });

        VBox tablePane = new VBox(10);
        tablePane.setPrefWidth(950);
        tablePane.setPadding(new Insets(20));
        tablePane.getChildren().addAll(searchLabel, searchField, tableView);
        tablePane.setId("tableview");

        AnchorPane.setRightAnchor(tablePane, 70d);
        AnchorPane.setTopAnchor(tablePane, 30d);
        AnchorPane.setBottomAnchor(tablePane, 30d);

        root.getChildren().add(tablePane);

        Scene scene = new Scene(root, 900, 600);

        scene.getStylesheets().add("login.css");

        AnchorPane.setLeftAnchor(formPane, 70d);
        AnchorPane.setTopAnchor(formPane, 30d);
        AnchorPane.setBottomAnchor(formPane, 30d);

        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();

        loadAppointmentsFromCSV();
        displayAppointmentsInTableView();
    }

    private void clearFormFields() {
        clearTextField(titleField);
        clearTextField(nameField);
        clearTextField(emailField);
        clearTextField(phoneField);
        resetComboBox(courseTypeComboBox, "Course Type");
        resetComboBox(phoneLocationComboBox, "By Phone");
        resetComboBox(hoursComboBox, "Hours: 8am - 10pm");
        termsCheckBox.setSelected(false);
    }
    
    private void clearTextField(TextField textField) {
        if (!textField.getText().isEmpty()) {
            textField.clear();
        } else {
            textField.setPromptText(textField.getPromptText());
        }
    }
    
    private void resetComboBox(ComboBox<String> comboBox, String promptText) {
        comboBox.getSelectionModel().clearSelection();
        comboBox.setValue(null);
        comboBox.getEditor().clear();
    
        //Check if prompt item exists in the list
        boolean promptItemExists = comboBox.getItems().stream()
                .anyMatch(item -> item.equals(promptText));
    
        if (!promptItemExists) {
            //Add prompt item to the list
            comboBox.getItems().add(0, promptText);
        }
    
        //Select the prompt item or the first item if available
        comboBox.getSelectionModel().selectFirst();
    }
    
    private void loadAppointmentsFromCSV() {
        appointments = FXCollections.observableArrayList();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/database/AppointmentList.csv"))) {
            String line;
            //Read the header line
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 8) {
                    String title = values[0];
                    String name = values[1];
                    String email = values[2];
                    String phone = values[3];
                    String courseType = values[4];
                    String location = values[5];
                    String hours = values[6];
                    boolean agreedToTerms = Boolean.parseBoolean(values[7]);
                    Appointment appointment = new Appointment(title, name, email, phone, courseType, location, hours, agreedToTerms);
                    appointments.add(appointment);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void saveAppointmentsToCSV(List<Appointment> appointments) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/database/AppointmentList.csv"))) {
            writer.write("Title,Name,Email,Phone Number,Course Type,Location,Hours,Agreed to Terms");
            writer.newLine();
            for (Appointment appointment : appointments) {
                writer.write(appointment.getTitle() + "," +
                             appointment.getName() + "," +
                             appointment.getEmail() + "," +
                             appointment.getPhone() + "," +
                             appointment.getCourseType() + "," +
                             appointment.getLocation() + "," +
                             appointment.getHours() + "," +
                             appointment.isAgreedToTerms());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void displayAppointmentsInTableView() {
        tableView.setItems(appointments);
    }
    
    private void filterAppointments(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            tableView.setItems(appointments);
            return;
        }

        ObservableList<Appointment> filteredList = FXCollections.observableArrayList();
        for (Appointment appointment : appointments) {
            if (appointment.getTitle().toLowerCase().contains(searchTerm.toLowerCase()) ||
                    appointment.getName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                    appointment.getEmail().toLowerCase().contains(searchTerm.toLowerCase()) ||
                    appointment.getPhone().toLowerCase().contains(searchTerm.toLowerCase()) ||
                    appointment.getCourseType().toLowerCase().contains(searchTerm.toLowerCase()) ||
                    appointment.getLocation().toLowerCase().contains(searchTerm.toLowerCase()) ||
                    appointment.getHours().toLowerCase().contains(searchTerm.toLowerCase())) {
                filteredList.add(appointment);
            }
        }
        tableView.setItems(filteredList);
    }

    public static void main(String[] args) {
        launch(args);
    }
}