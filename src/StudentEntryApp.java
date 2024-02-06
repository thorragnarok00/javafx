import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StudentEntryApp extends Application {

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Student New Entry");

        //Labels
        Label firstName = new Label("First Name");
        Label middleName = new Label("Middle Name");
        Label lastName = new Label("Last Name");
        Label gender = new Label("Gender");
        Label contact = new Label("Contact Number");
        Label email = new Label("Email Address");
        Label address = new Label("Address");

        //Text fields
        TextField firstNameField = new TextField();
        TextField middleNameField = new TextField();
        TextField lastNameField = new TextField();
        TextField contactField = new TextField();
        TextField emailField = new TextField();
        TextArea addressField = new TextArea(); // Use TextArea for multi-line input
        addressField.setPrefRowCount(2); // Set the preferred row count to 2

        //Radio buttons
        RadioButton maleRadioButton = new RadioButton("Male");
        RadioButton femaleRadioButton = new RadioButton("Female");
        ToggleGroup genderToggleGroup = new ToggleGroup();
        maleRadioButton.setToggleGroup(genderToggleGroup);
        femaleRadioButton.setToggleGroup(genderToggleGroup);

        //HBox for gender selection
        HBox genderBox = new HBox(10);
        genderBox.getChildren().addAll(maleRadioButton, femaleRadioButton);

        //VBox to hold the labels and text fields
        VBox vbox = new VBox(5);
        vbox.setAlignment(Pos.CENTER_LEFT);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(
                firstName, firstNameField,
                middleName, middleNameField,
                lastName, lastNameField,
                gender, genderBox,
                contact, contactField,
                email, emailField,
                address, addressField
        );

        // Save button
Button save = new Button("Save");
save.setAlignment(Pos.CENTER);
save.setPadding(new Insets(5)); // Adjust the padding to reduce the height
save.setMinWidth(60);
save.setStyle("-fx-font-size: 12px; -fx-content-display: center;"); // Center the label

        HBox.setMargin(save, new Insets(0, 10, 10, 0));

        //HBox with spacer and save button
        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
        buttonBox.getChildren().addAll(new Region(), save);

        //BorderPane as the root container
        BorderPane root = new BorderPane();
        root.setTop(vbox);
        root.setBottom(buttonBox);
        
        Scene scene = new Scene(root, 300, 440);
primaryStage.setScene(scene);
primaryStage.setResizable(true); // Allow resizing

// Set minimum height and width
double minHeight = 480;
double minWidth = 320;
primaryStage.setMinHeight(minHeight);
primaryStage.setMinWidth(minWidth);

// Listener for height resizing
primaryStage.heightProperty().addListener((observable, oldValue, newValue) -> {
    if (newValue.doubleValue() < minHeight) {
        primaryStage.setHeight(minHeight);
    }
});

// Listener for width resizing
primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> {
    if (newValue.doubleValue() < minWidth) {
        primaryStage.setWidth(minWidth);
    }
});

primaryStage.show();
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}