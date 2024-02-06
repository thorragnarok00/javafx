import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class RegistrationFormApp extends Application {

    private StackPane stackPane; // StackPane to hold the registration form and message pane

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Registration Form");

        // Load the image
        Image image = new Image("IMG_0230.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(400); // Adjust the image width

        // Title label
        Label titleLabel = new Label("Register now!");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Text fields
        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        TextField emailField = new TextField();
        PasswordField passwordField = new PasswordField();

        // Labels for text fields
        Label firstNameLabel = new Label("First Name:");
        Label lastNameLabel = new Label("Last Name:");
        Label emailLabel = new Label("Email:");
        Label passwordLabel = new Label("Password:");

        // Button
        Button registerButton = new Button("Register");
        registerButton.setOnAction(e -> showRegistrationMessage());

        // Create a grid pane for labels and text fields
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));
        gridPane.setAlignment(Pos.CENTER); // Align the grid pane to center

        // Add labels and text fields to the grid pane
        gridPane.add(firstNameLabel, 0, 0);
        gridPane.add(firstNameField, 1, 0);
        gridPane.add(lastNameLabel, 0, 1);
        gridPane.add(lastNameField, 1, 1);
        gridPane.add(emailLabel, 0, 2);
        gridPane.add(emailField, 1, 2);
        gridPane.add(passwordLabel, 0, 3);
        gridPane.add(passwordField, 1, 3);

        // VBox to hold the title, grid pane, and register button
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(0, 40, 0, 40)); // Adjust the padding
        vbox.getChildren().addAll(titleLabel, gridPane, registerButton);

        // Custom SplitPane implementation
        Pane splitPane = new Pane();
        splitPane.setPrefWidth(800); // Set the preferred width for the split pane

        // Position the image view on the left side
        imageView.setLayoutX(0);
        imageView.setLayoutY(0);
        imageView.setFitHeight(400);

        // Position the registration section on the right side
        vbox.setLayoutX(400);
        vbox.setLayoutY(0);
        vbox.setPrefWidth(400);
        vbox.setMinHeight(400);

        splitPane.getChildren().addAll(imageView, vbox);

        stackPane = new StackPane(splitPane);
        Scene scene = new Scene(stackPane, 800, 400);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void showRegistrationMessage() {
        // Create a new pane to display the registration message
        StackPane messagePane = new StackPane();
        messagePane.setStyle("-fx-background-color: rgba(224, 224, 224, 0.8)"); // Set the background color and opacity
    
        // Create the rectangle for the message box
        Rectangle messageBox = new Rectangle(200, 100);
        messageBox.setFill(Color.WHITE); // Set the fill color of the rectangle
        messageBox.setStroke(Color.BLACK); // Set the stroke color of the rectangle
        messageBox.setStrokeWidth(2); // Set the stroke width of the rectangle
    
        // Create the message label
        Label messageLabel = new Label("Thanks for registering!");
        messageLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        messageLabel.setAlignment(Pos.CENTER); // Center the label text
    
        // Create the "Register again" button
        Button registerAgainButton = new Button("Register again");
        registerAgainButton.setOnAction(e -> {
            stackPane.getChildren().remove(messagePane); // Remove the message pane from the stack pane
        });
    
        // Create a VBox to hold the message label and register again button
        VBox messageContent = new VBox(10);
        messageContent.setAlignment(Pos.CENTER);
        messageContent.getChildren().addAll(messageLabel, registerAgainButton);
    
        // Add the message content VBox to the message pane
        messagePane.getChildren().add(messageContent);
    
        // Set the message box as the background of the message pane
        messagePane.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(5), Insets.EMPTY)));
        messagePane.setMinSize(messageBox.getWidth(), messageBox.getHeight());
    
        // Position the message pane in the center of the stack pane
        StackPane.setAlignment(messagePane, Pos.CENTER);
    
        // Add the message pane to the stack pane
        stackPane.getChildren().add(messagePane);
    }
    
}    
