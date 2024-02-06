import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Calc extends Application {

    private TextField display;
    private StringBuilder input;

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Calculator");

        display = new TextField();
        display.setEditable(false);
        display.setPrefHeight(60);
        display.setMaxWidth(240);
        display.setStyle("-fx-font-size: 24px; -fx-alignment: center-right;");
        VBox.setMargin(display, new Insets(5));

        input = new StringBuilder();

        TilePane displayPane = new TilePane();
        displayPane.setAlignment(Pos.CENTER);
        displayPane.getChildren().add(display);
        displayPane.setPadding(new Insets(0));
        displayPane.setHgap(0);
        displayPane.setVgap(0);

        TilePane buttonPane = new TilePane();
        buttonPane.setPadding(new Insets(0));
        buttonPane.setVgap(0);
        buttonPane.setHgap(0);
        buttonPane.setAlignment(Pos.CENTER);

        // Create buttons for numbers and operators
        Button[] buttons = new Button[20];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new Button();
            buttons[i].setPrefSize(60, 60);
            final int index = i;
            buttons[i].setOnAction(e -> handleButtonClick(buttons[index].getText()));
            buttons[i].setStyle("-fx-background-radius: 0");
        }

        buttons[0].setText("AC");
        buttons[0].setStyle("-fx-background-color: orange; ");
        buttons[1].setText("√");
        buttons[2].setText("+/-");
        buttons[3].setText("÷");
        buttons[4].setText("7");
        buttons[5].setText("8");
        buttons[6].setText("9");
        buttons[7].setText("×");
        buttons[8].setText("4");
        buttons[9].setText("5");
        buttons[10].setText("6");
        buttons[11].setText("-");
        buttons[12].setText("1");
        buttons[13].setText("2");
        buttons[14].setText("3");
        buttons[15].setText("+");
        buttons[16].setText("%");
        buttons[17].setText("0");
        buttons[18].setText(".");
        buttons[19].setText("=");

        // Add buttons to the button pane
        buttonPane.getChildren().addAll(buttons);

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER); // Set the alignment to center
        vbox.setPadding(Insets.EMPTY);
        vbox.getChildren().addAll(displayPane, buttonPane);

        Scene scene = new Scene(vbox, 240, 360);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void handleButtonClick(String buttonText) {
        switch (buttonText) {
            case "=":
                evaluateExpression();
                break;
            case "AC":
                clearDisplay();
                break;
            /* 
            case "A":
                removeLastCharacter();
                break;
            */
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

    /* 
    private void removeLastCharacter() {
        if (input.length() > 0) {
            input.deleteCharAt(input.length() - 1);
            display.setText(input.toString());
        }
    }
    */

    public static void main(String[] args) {
        launch(args);
    }
}

