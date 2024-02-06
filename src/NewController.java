import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

public class NewController implements Initializable {

    @FXML
    private TextField textFirstName;

    @FXML
    private TextField textMiddleName;

    @FXML
    private TextField textLastName;

    @FXML
    private RadioButton radioMale;

    @FXML
    private RadioButton radioFemale;

    @FXML
    private Button buttonSave;

    @FXML
    private TableView<Student> tableStudent;

    @FXML
    private TableColumn<Student, String> columnLastName;

    @FXML
    private TableColumn<Student, String> columnFirstName;

    @FXML
    private TableColumn<Student, String> columnMIddleName;

    @FXML
    private TableColumn<Student, String> columnGender;

    @FXML
    private TextField textSearch;

    @FXML
    void handleSave(ActionEvent event) {

        String gender = "";
        if (radioFemale.isSelected()) {
            gender = "Female";
        } else if (radioMale.isSelected()) {
            gender = "Male";
        }

        data.add(new Student(
            textLastName.getText(),
            textFirstName.getText(),
            textMiddleName.getText(),
            gender
        ));

        try {
            writeCSV();
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }

    @FXML
    void searchStudent(KeyEvent event) {
        String searchText = textSearch.getText().toLowerCase();
    
        //Create a filtered list based on the search criteria
        FilteredList<Student> filteredList = data.filtered(student ->
                student.getLastName().toLowerCase().contains(searchText) ||
                student.getFirstName().toLowerCase().contains(searchText) ||
                student.getMiddleName().toLowerCase().contains(searchText) ||
                student.getGender().toLowerCase().contains(searchText)
        );
    
        //Set the filtered list as the new data source for the table
        tableStudent.setItems(filteredList);

    }

    static ObservableList<Student> data;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnLastName.setCellValueFactory(new PropertyValueFactory<Student, String>("lastName"));
        columnFirstName.setCellValueFactory(new PropertyValueFactory<Student, String>("firstName"));
        columnMIddleName.setCellValueFactory(new PropertyValueFactory<Student, String>("middleName"));
        columnGender.setCellValueFactory(new PropertyValueFactory<Student, String>("gender"));
        
        data = FXCollections.observableArrayList();
        tableStudent.setItems(data);

        try {
            loadCSV();
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }

    public static void loadCSV() throws IOException{
        BufferedReader br = new BufferedReader(new FileReader("src/database/Student.csv"));
        String row;
        br.readLine();
        while ((row = br.readLine()) != null) {
            String[] line = row.split(",");
            Student student = new Student(line[0], line[1], line[2], line[3]);
            data.add(student);
        }
        br.close();
    }
    

    public static void writeCSV() throws IOException {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter("src/database/Student.csv", true));
            if (!data.isEmpty()) {
                Student student = data.get(data.size() - 1);
                bw.write(student.getLastName() + "," + student.getFirstName() + "," + student.getMiddleName() + "," + student.getGender());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                bw.close();
            }
        }
    }
}