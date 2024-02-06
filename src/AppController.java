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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

public class AppController implements Initializable {

    @FXML
    private TextField textEmail;

    @FXML
    private TextField textUsername;

    @FXML
    private TextField textCity;

    @FXML
    private Button buttonSave;

    @FXML
    private TableView<User> tableUser;

    @FXML
    private TableColumn<User, String> columnEmail;

    @FXML
    private TableColumn<User, String> columnUsername;

    @FXML
    private TableColumn<User, String> columnCity;

    @FXML
    private TextField textSearch;

    @FXML
    void searchUser(KeyEvent event) {
        String searchText = textSearch.getText().toLowerCase();
    
        //Create a filtered list based on the search criteria
        FilteredList<User> filteredList = userList.filtered(user ->
                user.getEmail().toLowerCase().contains(searchText) ||
                user.getUsername().toLowerCase().contains(searchText) ||
                user.getCity().toLowerCase().contains(searchText)
        );
    
        //Set the filtered list as the new data source for the table
        tableUser.setItems(filteredList);
    }

    static ObservableList<User> userList;

    @FXML
    void saveUser(ActionEvent event) {

        if (!textEmail.getText().isEmpty() && !textUsername.getText().isEmpty() && !textCity.getText().isEmpty()) {
            userList.add(new User(
                textEmail.getText(),
                textUsername.getText(),
                textCity.getText()
            ));
            textEmail.clear();
            textUsername.clear();
            textCity.clear();

            try {
                writeCSV();
            } catch (IOException e) {
                e.printStackTrace(); 
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnCity.setCellValueFactory(new PropertyValueFactory<User, String>("city"));
        columnUsername.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        
        userList = FXCollections.observableArrayList();
        tableUser.setItems(userList);

        try {
            loadCSV();
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }

    public static void loadCSV() throws IOException{
        BufferedReader br = new BufferedReader(new FileReader("src/database/users.csv"));
        String row;
        br.readLine();
        while ((row = br.readLine()) != null) {
            String[] line = row.split(",");
            User user = new User(line[0], line[1], line[2]);
            userList.add(user);
        }
        br.close();
    }

    public static void writeCSV() throws IOException {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter("src/database/users.csv", true));
            if (!userList.isEmpty()) {
                User user = userList.get(userList.size() - 1);
                bw.write(user.getEmail() + "," + user.getUsername() + "," + user.getCity());
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