import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Example  extends Application {
    public void start(Stage stage) {
        Button btn = new Button("Hello Java Fx");
        Scene scene = new Scene(btn, 450,200);
        stage.setTitle("Compro 2 Demo of Java FX");
        stage.setScene(scene);
        
        Image icon = new Image("IMG_0230.png");
        stage.getIcons().add(0, icon);
        stage.show();
        Stage stage1 = new Stage();
        stage1.setTitle("Second Stage");
        stage1.setScene(new Scene(new Button("Hello World"), 100, 200));
        stage1.setX(50);
        stage1.setY(50);
        stage1.show();
    }
    public static void main(String[] args) throws Exception {
        launch(args);
    }
}