import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class Discarded extends Application {

    private TextField display;
    private StringBuilder input;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Calculator");

        display = new TextField();
        display.setEditable(false);
        display.setPrefHeight(60);
        display.setStyle("-fx-font-size: 24px; -fx-alignment: center-right;");
        GridPane.setMargin(display, new Insets(5));

        input = new StringBuilder();

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(5));
        gridPane.setVgap(2);
        gridPane.setHgap(2);

// Set column constraints to evenly distribute the columns
for (int i = 0; i < 4; i++) {
    ColumnConstraints columnConstraints = new ColumnConstraints();
    columnConstraints.setHgrow(Priority.ALWAYS); // Allow column to grow or shrink
    gridPane.getColumnConstraints().add(columnConstraints);
}



        // Merge first row for the display area
        gridPane.add(display, 0, 0, 4, 1);

        // Create buttons for numbers and operators
        Button[] buttons = new Button[20];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new Button();
            buttons[i].setPrefSize(60, 60);
            final int index = i;
            buttons[i].setOnAction(e -> handleButtonClick(buttons[index].getText()));
        }

        buttons[0].setText("7");
        buttons[1].setText("8");
        buttons[2].setText("9");
        buttons[3].setText("/");
        buttons[4].setText("4");
        buttons[5].setText("5");
        buttons[6].setText("6");
        buttons[7].setText("*");
        buttons[8].setText("1");
        buttons[9].setText("2");
        buttons[10].setText("3");
        buttons[11].setText("-");
        buttons[12].setText("0");
        buttons[13].setText(".");
        buttons[14].setText("=");
        buttons[15].setText("+");
        buttons[16].setText("C");
        buttons[17].setText("(");
        buttons[18].setText(")");
        buttons[19].setText("Back");

        // Add buttons to the grid pane
        int buttonIndex = 0;
        for (int row = 1; row <= 5; row++) {
            for (int col = 0; col < 4; col++) {
                gridPane.add(buttons[buttonIndex], col, row);
                buttonIndex++;
            }
        }

        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.setWidth(250); // Set the preferred width of the window
        primaryStage.setHeight(250); // Set the preferred height of the window
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void handleButtonClick(String buttonText) {
        switch (buttonText) {
            case "=":
                evaluateExpression();
                break;
            case "C":
                clearDisplay();
                break;
            case "Back":
                removeLastCharacter();
                break;
            default:
                addToInput(buttonText);
                break;
        }
    }

    private void addToInput(String text) {
        input.append(text);
        display.setText(input.toString());
    }

    private void evaluateExpression() {
        try {
            String expression = input.toString();
            String result = evaluateExpression(expression);
            display.setText(result);
        } catch (Exception e) {
            display.setText("Error");
        }
    }

    private String evaluateExpression(String expression) {
        // Evaluate the expression using your preferred method or library
        // For simplicity, we'll just use the built-in JavaScript engine
        javafx.scene.web.WebEngine webEngine = new javafx.scene.web.WebEngine();
        String result = webEngine.executeScript("eval(" + expression + ")").toString();
        return result;
    }

    private void clearDisplay() {
        input.setLength(0);
        display.clear();
    }

    private void removeLastCharacter() {
        if (input.length() > 0) {
            input.deleteCharAt(input.length() - 1);
            display.setText(input.toString());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

