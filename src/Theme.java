import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Theme extends Application {

    @Override
    public void start(Stage stage) {
        AnchorPane root = new AnchorPane();
        root.setId("parent");
        VBox form = new VBox();
        TextField userName = new TextField();
        PasswordField password = new PasswordField();
        Button button = new Button("Log in");

        StackPane page2 = new StackPane();
        Label welcome =  new Label("Welcome to my App!");
        page2.setId("parent");
        page2.getChildren().add(welcome);

        button.setOnAction(event -> {
            if (userName.getText().equals("Gericho") &&  password.getText().equals("123")) {
                Scene scene2 = new Scene(page2, 400, 300);
                scene2.getStylesheets().add("style1.css");
                stage.setScene(scene2);
            }
        });

        form.getChildren().addAll(userName, password, button);
        form.setSpacing(10);
        form.setAlignment(Pos.CENTER);

        root.getChildren().add(form);

        AnchorPane.setTopAnchor(form, 40d);
        AnchorPane.setRightAnchor(form, 40d);
        AnchorPane.setLeftAnchor(form, 40d);
        AnchorPane.setBottomAnchor(form, 40d);

        Scene scene = new Scene(root, 400, 300);
        scene.getStylesheets().add("style.css");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
