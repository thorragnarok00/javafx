import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.input.KeyEvent;

public class DashboardController implements Initializable {

    @FXML
    private AnchorPane mainForm;

    @FXML
    private Label username;

    @FXML
    private Button dashboardBtn;

    @FXML
    private Button availableFDBtn;

    @FXML
    private Button orderBtn;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane dashboardForm;

    @FXML
    private Label dashboardNC;

    @FXML
    private Label dashboardTI;

    @FXML
    private Label dashboardTIncome;

    @FXML
    private BarChart<?, ?> dashboardNOCChart;

    @FXML
    private AreaChart<?, ?> dashboardICChart;

    @FXML
    private AnchorPane availableFDForm;

    @FXML
    private TextField availableFDProductID;

    @FXML
    private TextField availableFDProductName;

    @FXML
    private ComboBox<?> availableFDProductType;

    @FXML
    private TextField availableFDProductPrice;

    @FXML
    private ComboBox<?> availableFDProductStatus;

    @FXML
    private Button availableFDAddBtn;

    @FXML
    private Button availableFDUpdateBtn;

    @FXML
    private Button availableFDClearButton;

    @FXML
    private Button availableFDDeleteBtn;

    @FXML
    private TextField availableFD_search;

    @FXML
    private TableView<categories> availableFD_tableView;

    @FXML
    private TableColumn<categories, String> availableFDColumnProductID;

    @FXML
    private TableColumn<categories, String> availableFDColumnProductName;

    @FXML
    private TableColumn<categories, String> availableFDColumnProductType;

    @FXML
    private TableColumn<categories, String> availableFDColumnPrice;

    @FXML
    private TableColumn<categories, String> availableFDColumnStatus;

    @FXML
    private AnchorPane orderForm;

    @FXML
    private TableView<Product> orderTableView;

    @FXML
    private TableColumn<Product, String> orderColumnProductID;

    @FXML
    private TableColumn<Product, String> orderColumnProductName;

    @FXML
    private TableColumn<Product, String> orderColumnType;

    @FXML
    private TableColumn<Product, String> orderColumnPrice;

    @FXML
    private TableColumn<Product, String> orderColumnQuantity;

    @FXML
    private ComboBox<?> orderProductID;

    @FXML
    private ComboBox<?> orderProductName;

    @FXML
    private Spinner<Integer> orderQuantity;

    @FXML
    private Button orderAddBtn;

    @FXML
    private Label orderTotal;

    @FXML
    private TextField orderAmount;

    @FXML
    private Button orderPayBtn;

    @FXML
    private Button orderRemoveBtn;

    @FXML
    private Button orderNewBtn;

    @FXML
    private Label orderBalance;

    public void initialize() {
        availableFD_search = new TextField();
    }

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    public void dashboardNC() {

        String sql = "SELECT COUNT(id) FROM product_info";

        int nc = 0;

        connect = database.connectDb();

        try {

            statement = connect.createStatement();
            result = statement.executeQuery(sql);

            if (result.next()) {
                nc = result.getInt("COUNT(id)");
            }

            dashboardNC.setText(String.valueOf(nc));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dashboardTI() {

        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        String sql = "SELECT SUM(total) FROM product_info WHERE date = '" + sqlDate + "'";

        connect = database.connectDb();

        double ti = 0;

        try {
            statement = connect.createStatement();
            result = statement.executeQuery(sql);

            if (result.next()) {
                ti = result.getDouble("SUM(total)");
            }

            dashboardTI.setText("₱" + String.valueOf(ti));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dashboardTIncome() {

        String sql = "SELECT SUM(total) FROM product_info";

        connect = database.connectDb();

        double ti = 0;

        try {

            statement = connect.createStatement();
            result = statement.executeQuery(sql);

            if (result.next()) {
                ti = result.getDouble("SUM(total)");
            }
            dashboardTIncome.setText("₱" + String.valueOf(ti));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void dashboardNOCCChart() {

        try {

            dashboardNOCChart.getData().clear();

            String sql = "SELECT date, COUNT(id) FROM product_info GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 5";

            connect = database.connectDb();

            XYChart.Series chart = new XYChart.Series();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }

            dashboardNOCChart.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void dashboardICC() {

        dashboardICChart.getData().clear();

        String sql = "SELECT date, SUM(total) FROM product_info GROUP BY date ORDER BY TIMESTAMP(total) ASC LIMIT 7";

        connect = database.connectDb();

        try {

            XYChart.Series chart = new XYChart.Series();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {

                chart.getData().add(new XYChart.Data(result.getString(1), result.getDouble(2)));

            }

            dashboardICChart.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void availableFDAdd() {

        String sql = "INSERT INTO category (product_id, product_name, type, price, status) "
                + "VALUES(?,?,?,?,?)";

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, availableFDProductID.getText());
            prepare.setString(2, availableFDProductName.getText());
            prepare.setString(3, (String) availableFDProductType.getSelectionModel().getSelectedItem());
            prepare.setString(4, availableFDProductPrice.getText());
            prepare.setString(5, (String) availableFDProductStatus.getSelectionModel().getSelectedItem());

            Alert alert;

            if (availableFDProductID.getText().isEmpty()
                    || availableFDProductName.getText().isEmpty()
                    || availableFDProductType.getSelectionModel() == null
                    || availableFDProductPrice.getText().isEmpty()
                    || availableFDProductStatus.getSelectionModel() == null) {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();

            } else {

                String checkData = "SELECT product_id FROM category WHERE product_id = '"
                        + availableFDProductID.getText() + "'";

                connect = database.connectDb();

                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Product ID: " + availableFDProductID.getText() + " already exists!");
                    alert.showAndWait();
                } else {
                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully added!");
                    alert.showAndWait();

                    availableFDShowData();
                    availableFDClear();

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void availableFDUpdate() {

        String sql = "UPDATE category SET product_name = '"
                + availableFDProductName.getText() + "', type = '"
                + availableFDProductType.getSelectionModel().getSelectedItem() + "', price = '"
                + availableFDProductPrice.getText() + "', status = '"
                + availableFDProductStatus.getSelectionModel().getSelectedItem()
                + "' WHERE product_id = '" + availableFDProductID.getText() + "'";

        connect = database.connectDb();

        try {

            Alert alert;

            if (availableFDProductID.getText().isEmpty()
                    || availableFDProductName.getText().isEmpty()
                    || availableFDProductType.getSelectionModel().getSelectedItem() == null
                    || availableFDProductPrice.getText().isEmpty()
                    || availableFDProductStatus.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {

                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Product ID: "
                        + availableFDProductID.getText() + "?");

                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    availableFDShowData();
                    availableFDClear();

                } else {
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled.");
                    alert.showAndWait();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void availableFDDelete() {

        String sql = "DELETE FROM category WHERE product_id = '"
                + availableFDProductID.getText() + "'";

        connect = database.connectDb();

        try {

            Alert alert;

            if (availableFDProductID.getText().isEmpty()
                    || availableFDProductName.getText().isEmpty()
                    || availableFDProductType.getSelectionModel().getSelectedItem() == null
                    || availableFDProductPrice.getText().isEmpty()
                    || availableFDProductStatus.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {

                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Product ID: "
                        + availableFDProductID.getText() + "?");

                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    availableFDShowData();
                    availableFDClear();

                } else {
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled.");
                    alert.showAndWait();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void availableFDClear() {

        availableFDProductID.setText("");
        availableFDProductName.setText("");
        availableFDProductType.getSelectionModel().clearSelection();
        availableFDProductPrice.setText("");
        availableFDProductStatus.getSelectionModel().clearSelection();

    }

    public ObservableList<categories> availableFDListData() {

        ObservableList<categories> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM category";

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            categories cat;

            while (result.next()) {
                cat = new categories(result.getString("product_id"),
                        result.getString("product_name"), result.getString("type"),
                        result.getDouble("price"), result.getString("status"));

                listData.add(cat);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    public void availableFDSearch() {
        availableFD_search.textProperty().addListener((observable, oldValue, newValue) -> {
            FilteredList<categories> filteredData = new FilteredList<>(availableFDList, categories -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseSearch = newValue.toLowerCase();
                return categories.getProductId().toLowerCase().contains(lowerCaseSearch) ||
                        categories.getName().toLowerCase().contains(lowerCaseSearch) ||
                        categories.getType().toLowerCase().contains(lowerCaseSearch) ||
                        categories.getPrice().toString().toLowerCase().contains(lowerCaseSearch) ||
                        categories.getStatus().toLowerCase().contains(lowerCaseSearch);
            });
            SortedList<categories> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(availableFD_tableView.comparatorProperty());
            availableFD_tableView.setItems(sortedData);
        });
    }

    private ObservableList<categories> availableFDList;

    public void availableFDShowData() {
        availableFDList = availableFDListData();

        availableFDColumnProductID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        availableFDColumnProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        availableFDColumnProductType.setCellValueFactory(new PropertyValueFactory<>("type"));
        availableFDColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        availableFDColumnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        availableFD_tableView.setItems(availableFDList);

    }

    public void availableFDSelect() {

        categories catData = availableFD_tableView.getSelectionModel().getSelectedItem();

        int num = availableFD_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        availableFDProductID.setText(catData.getProductId());
        availableFDProductName.setText(catData.getName());
        availableFDProductPrice.setText(String.valueOf(catData.getPrice()));

    }

    private String[] categories = { "Breakfast", "Burgers", "Chicken & Platters", "Drink & Desserts", "McCafé",
            "Fries" };

    public void availableFDType() {
        List<String> listCat = new ArrayList<>();

        for (String data : categories) {
            listCat.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listCat);
        availableFDProductType.setItems(listData);

    }

    private String[] status = { "Available", "Not Available" };

    public void availableFDStatus() {
        List<String> listStatus = new ArrayList<>();

        for (String data : status) {
            listStatus.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listStatus);
        availableFDProductStatus.setItems(listData);

    }

    public void orderAdd() {

        orderCustomerId();
        orderTotal();

        String sql = "INSERT INTO product "
                + "(customer_id, product_id, product_name, type, price, quantity, date) "
                + "VALUES(?,?,?,?,?,?,?)";

        connect = database.connectDb();

        try {
            String orderType = "";
            double orderPrice = 0;

            String checkData = "SELECT * FROM category WHERE product_id = '"
                    + orderProductID.getSelectionModel().getSelectedItem() + "'";

            statement = connect.createStatement();
            result = statement.executeQuery(checkData);

            if (result.next()) {
                orderType = result.getString("type");
                orderPrice = result.getDouble("price");
            }

            prepare = connect.prepareStatement(sql);
            prepare.setString(1, String.valueOf(customerId));
            prepare.setString(2, (String) orderProductID.getSelectionModel().getSelectedItem());
            prepare.setString(3, (String) orderProductName.getSelectionModel().getSelectedItem());
            prepare.setString(4, orderType);

            double totalPrice = orderPrice * qty;

            prepare.setString(5, String.valueOf(totalPrice));

            prepare.setString(6, String.valueOf(qty));

            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());

            prepare.setString(7, String.valueOf(sqlDate));

            prepare.executeUpdate();

            orderDisplayTotal();
            orderDisplayData();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void orderPay() {
        orderCustomerId();
        orderTotal();

        String sql = "INSERT INTO product_info (customer_id, total, date) VALUES(?,?,?)";

        connect = database.connectDb();

        try {

            Alert alert;

            if (balance == 0 || String.valueOf(balance) == "₱0.0" || String.valueOf(balance) == null
                    || totalP == 0 || String.valueOf(totalP) == "₱0.0" || String.valueOf(totalP) == null) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Invalid");
                alert.showAndWait();
            } else {

                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, String.valueOf(customerId));
                    prepare.setString(2, String.valueOf(totalP));

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare.setString(3, String.valueOf(sqlDate));

                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successful!");
                    alert.showAndWait();

                    orderTotal.setText("₱0.0");
                    orderBalance.setText("₱0.0");
                    orderAmount.setText("");

                } else {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled!");
                    alert.showAndWait();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private double totalP = 0;

    public void orderTotal() {
        orderCustomerId();

        String sql = "SELECT SUM(price) FROM product WHERE customer_id = " + customerId;

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                totalP = result.getDouble("SUM(price)");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private double amount;
    private double balance;

    public void orderAmount() {
        orderTotal();

        Alert alert;

        if (orderAmount.getText().isEmpty() || orderAmount.getText() == null
                || orderAmount.getText() == "") {
            alert = new Alert(AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please type the amount!");
            alert.showAndWait();
        } else {
            amount = Double.parseDouble(orderAmount.getText());

            if (amount < totalP) {
                orderAmount.setText("");
            } else {
                balance = (amount - totalP);
                orderBalance.setText("₱" + String.valueOf(balance));
            }
        }
    }

    public void orderDisplayTotal() {
        orderTotal();
        orderTotal.setText("₱" + String.valueOf(totalP));

    }

    public ObservableList<Product> orderListData() {

        orderCustomerId();

        ObservableList<Product> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM product WHERE customer_id = " + customerId;

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            Product prod;

            while (result.next()) {
                prod = new Product(result.getInt("id"),
                        result.getString("product_id"),
                        result.getString("product_name"),
                        result.getString("type"),
                        result.getDouble("price"),
                        result.getInt("quantity"));

                listData.add(prod);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }

    public void orderRemove() {

        String sql = "DELETE FROM product WHERE id = " + item;

        connect = database.connectDb();

        try {
            Alert alert;

            if (item == 0 || String.valueOf(item) == null || String.valueOf(item) == "") {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select the item first");
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to Remove Item: " + item + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Removed!");
                    alert.showAndWait();

                    orderDisplayData();
                    orderDisplayTotal();

                    orderAmount.setText("");
                    orderBalance.setText("₱ 0.0");

                } else {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled!");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int item;

    public void orderSelectData() {

        Product prod = orderTableView.getSelectionModel().getSelectedItem();
        int num = orderTableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        item = prod.getId();
    }

    private ObservableList<Product> orderData;

    public void orderDisplayData() {
        orderData = orderListData();

        orderColumnProductID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        orderColumnProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        orderColumnType.setCellValueFactory(new PropertyValueFactory<>("type"));
        orderColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        orderColumnQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        orderTableView.setItems(orderData);

    }

    public void newOrder() {
        orderTableView.getItems().clear();
    }
    

    private int customerId;

    public void orderCustomerId() {

        String sql = "SELECT customer_id FROM product";

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                customerId = result.getInt("customer_id");
            }

            String checkData = "SELECT customer_id FROM product_info";

            statement = connect.createStatement();
            result = statement.executeQuery(checkData);

            int customerInfoId = 0;

            while (result.next()) {
                customerInfoId = result.getInt("customer_id");
            }

            if (customerId == 0) {
                customerId += 1;
            } else if (customerId == customerInfoId) {
                customerId += 1;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void orderProductId() {

        String sql = "SELECT product_id FROM category WHERE status = 'Available'";

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ObservableList listData = FXCollections.observableArrayList();

            while (result.next()) {
                listData.add(result.getString("product_id"));
            }

            orderProductID.setItems(listData);

            orderProductName();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void orderProductName() {

        String sql = "SELECT product_name FROM category WHERE product_id = '"
                + orderProductID.getSelectionModel().getSelectedItem() + "'";

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ObservableList listData = FXCollections.observableArrayList();

            while (result.next()) {
                listData.add(result.getString("product_name"));
            }

            orderProductName.setItems(listData);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private SpinnerValueFactory<Integer> spinner;

    public void orderSpinner() {
        spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, 0);

        orderQuantity.setValueFactory(spinner);
    }

    private int qty;

    public void orderQuantity() {
        qty = orderQuantity.getValue();
    }

    public void switchForm(ActionEvent event) {

        if (event.getSource() == dashboardBtn) {
            dashboardForm.setVisible(true);
            availableFDForm.setVisible(false);
            orderForm.setVisible(false);

            dashboardBtn.setStyle("-fx-background-color: #FFC72C; -fx-text-fill: #fff; -fx-border-width: 0px;");
            availableFDBtn.setStyle("-fx-background-color: transparent; -fx-border-width: 1px; -fx-text-fill: #000;");
            orderBtn.setStyle("-fx-background-color: transparent; -fx-border-width: 1px; -fx-text-fill: #000;");

            dashboardNC();
            dashboardTI();
            dashboardTIncome();
            dashboardNOCCChart();
            dashboardICC();

        } else if (event.getSource() == availableFDBtn) {
            dashboardForm.setVisible(false);
            availableFDForm.setVisible(true);
            orderForm.setVisible(false);

            availableFDBtn.setStyle("-fx-background-color: #FFC72C; -fx-text-fill: #fff; -fx-border-width: 0px;");
            dashboardBtn.setStyle("-fx-background-color: transparent; -fx-border-width: 1px; -fx-text-fill: #000;");
            orderBtn.setStyle("-fx-background-color: transparent; -fx-border-width: 1px; -fx-text-fill: #000;");

            availableFDShowData();
            availableFDSearch();

        } else if (event.getSource() == orderBtn) {
            dashboardForm.setVisible(false);
            availableFDForm.setVisible(false);
            orderForm.setVisible(true);

            orderBtn.setStyle("-fx-background-color: #FFC72C; -fx-text-fill: #fff; -fx-border-width: 0px;");
            availableFDBtn.setStyle("-fx-background-color: transparent; -fx-border-width: 1px; -fx-text-fill: #000;");
            dashboardBtn.setStyle("-fx-background-color: transparent; -fx-border-width: 1px; -fx-text-fill: #000;");

            orderProductId();
            orderProductName();
            orderSpinner();
            orderDisplayData();
            orderDisplayTotal();
            newOrder();
        }

    }

    public void logout() {

        try {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {

                logout.getScene().getWindow().hide();

                Parent root = FXMLLoader.load(getClass().getResource("fxml/Doc.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setTitle("Restaurant Management System");
                stage.setScene(scene);
                stage.show();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void displayUsername() {
        String user = data.username;
        user = user.substring(0, 1).toUpperCase() + user.substring(1);
        username.setText(user);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        dashboardNC();
        dashboardTI();
        dashboardTIncome();
        dashboardNOCCChart();
        dashboardICC();

        displayUsername();
        availableFDStatus();
        availableFDType();

        availableFDShowData();

        orderProductId();
        orderProductName();
        orderSpinner();
        orderDisplayData();
        orderDisplayData();
        orderDisplayTotal();

    }

}
