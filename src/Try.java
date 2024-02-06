import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Try extends Application {

    @Override
    public void start(Stage primaryStage) {
        AnchorPane parent = new AnchorPane();
        parent.setId("parent");
        VBox forms = new VBox();
        forms.setId("child");

        Label form = new Label("EDUCATION APPOINTMENT FORM");
        form.setId("title");

        TextField title = new TextField();
        title.setPromptText("Title");

        TextField name = new TextField();
        name.setPromptText("Your Name");

        TextField email = new TextField();
        email.setPromptText("Email");

        TextField phone = new TextField();
        phone.setPromptText("Phone number");

        ComboBox<String> course = new ComboBox<String>();
        course.setPromptText("Course Type");
        course.setPrefWidth(300);

        Label located = new Label("How would you like to be located?");
        located.setId("located");

        ComboBox<String> byPhone = new ComboBox<String>();
        byPhone.setPromptText("By phone");
        byPhone.setPrefWidth(300);

        ComboBox<String> hours = new ComboBox<String>();
        hours.setPromptText("Hours: 8am-10pm");
        hours.setPrefWidth(300);

        CheckBox termsAndConditions = new CheckBox("I agree to the Terms and Conditions");
        termsAndConditions.setId("tnc");

        Button request = new Button("Request an appointment");

        forms.getChildren().addAll(form, title, name, email, phone, course, located, byPhone, hours, termsAndConditions, request);
        forms.setSpacing(10);
        forms.setAlignment(Pos.CENTER_LEFT);
        parent.getChildren().add(forms);

        Scene primaryScene = new Scene(parent, 900, 600);
        primaryScene.getStylesheets().add("style.css");

        AnchorPane.setLeftAnchor(forms, 70d);
        AnchorPane.setTopAnchor(forms, 20d);
        AnchorPane.setBottomAnchor(forms, 20d);

        primaryStage.setScene(primaryScene);
        primaryStage.setTitle("Education Appointment Form");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}