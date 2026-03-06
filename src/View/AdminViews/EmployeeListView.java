package View.AdminViews;

import Controller.ManagerController;
import FileHandler.UserFileHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import models.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class EmployeeListView extends BorderPane {
    Color darkRed = Color.rgb(146, 20, 12);
    Color lightOrange = Color.rgb(255, 207, 153);
    Color darkBlue = Color.rgb(17, 29, 74);
    Color bgColor = Color.rgb(255, 248, 240);
    DropShadow shadow = new DropShadow(10, 5, 5, Color.GRAY);

    Administrator admin;

    private final TableView<User> employeeTable;
    private final ObservableList<User> employees = FXCollections.observableArrayList();
    private ArrayList<Cashier> cashiersDataFromFile = UserFileHandler.loadCashiers();
    private ObservableList<Cashier> cashiers = FXCollections.observableArrayList();;

    // Navigation buttons
    private final Button checkOutNB = new Button();
    private final Button productDbNB = new Button();
    private final Button statisticsNB = new Button();
    private final Button profileNB = new Button();

    TextField searchField = new TextField();


    Button searchButton = new Button("Search");
    Button modifyEmployee = new Button("Manage Employee");

    public Button getDeleteEmployee() {
        return deleteEmployee;
    }

    Button deleteEmployee = new Button("Delete Employee");

    public EmployeeListView(Administrator admin) {
        this.admin = admin;
        // Initialize the employee table and data
        this.employeeTable = new TableView<>();
        this.cashiers = getCashiersData();

        // Set background style
        this.setStyle("-fx-background-color: rgb(255, 248, 240);");

        // Create the content area
        VBox contentArea = createContentArea();
        this.setCenter(contentArea);
    }

    static ObservableList<Cashier> cashierData = FXCollections.observableArrayList();


    private ObservableList<Cashier> getCashiersData()
    {
        for(Cashier cashier: cashiersDataFromFile)
        {

            cashierData.add(new Cashier(cashier.getUsername(), cashier.getPassword(), cashier.getBirthday(), cashier.getPosition(), cashier.getAccessLevel(), cashier.getSalary(), cashier.getEmail(), cashier.getPhonenr(), cashier.getSector()));
        }
        return cashierData;
    }

    private VBox createContentArea() {
        // Welcome Admin Label
        Label greeting = new Label("Welcome Admin");
        greeting.setEffect(shadow);
        greeting.setStyle("-fx-text-fill: rgb(146, 20, 12); -fx-font-size: 38; -fx-font-weight: bold; -fx-font-family: 'Arial'");
        VBox topSection = new VBox(greeting);
        topSection.setAlignment(Pos.CENTER);
        setTop(topSection);

        VBox contentArea = new VBox();
        contentArea.setSpacing(20);
        contentArea.setPadding(new Insets(10));

        // Title

        Label title = new Label("Employee List");
        title.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-background-color: rgb(17, 29, 74); -fx-text-fill: white; -fx-padding: 5px;");
        title.setAlignment(Pos.CENTER);
        title.setMaxWidth(Double.MAX_VALUE);

        // Buttons for Manage and Delete Employee
        modifyEmployee.setStyle("-fx-background-color: rgb(146, 20, 12); -fx-text-fill: white; -fx-font-size: 16; -fx-font-weight: bold;");
        deleteEmployee.setStyle("-fx-background-color: rgb(146, 20, 12); -fx-text-fill: white; -fx-font-size: 16; -fx-font-weight: bold;");
        HBox buttonRow = new HBox(20); // Horizontal box for buttons
        buttonRow.setAlignment(Pos.CENTER); // Center the buttons horizontally
        buttonRow.getChildren().addAll(modifyEmployee, deleteEmployee);

        // Search Box
        searchField.setPromptText("Search employee...");
        searchField.setPrefWidth(200);
        searchButton.setStyle("-fx-background-color: rgb(17, 29, 74); -fx-text-fill: white;");
        HBox searchBox = new HBox(10, searchField, searchButton);
        searchBox.setAlignment(Pos.CENTER);

        // Employee Table
        ScrollPane tableScrollPane = createEmployeeTable();

        // Add components to the content area
        contentArea.getChildren().addAll(title, tableScrollPane, buttonRow, searchBox);

        return contentArea;
    }



    private ScrollPane createEmployeeTable() {
        // Define columns
        TableColumn<User, String> usernameCol = createEditableColumn("Username", "username", 150);
        TableColumn<User, User.Position> positionCol = createPositionColumn();
        TableColumn<User, String> emailCol = createEditableColumn("Email", "email", 200);
        TableColumn<User, Cashier.Sectors> sectorCol = createSectorColumn();
        TableColumn<User, String> phoneCol = createEditableColumn("Phone", "phonenr", 150);
        TableColumn<User, User.AccessLevel> accessLevelCol = createAccessLevelColumn();
        TableColumn<User, Double> salaryCol = createSalaryColumn();
        TableColumn<User, String> passwordCol = createEditableColumn("Password", "password", 150);

        // Add columns to the table
        employeeTable.getColumns().addAll(usernameCol, positionCol, sectorCol, emailCol, phoneCol, accessLevelCol, salaryCol, passwordCol);

        // Set data and styling
        employeeTable.setItems(employees);
        employeeTable.setEditable(true);

        // Load sample data
        loadSampleData();
        for(int i = 0; i < cashiersDataFromFile.size(); i++) {
            employees.add(cashiersDataFromFile.get(i));
        }

        ScrollPane scrollPane = new ScrollPane(employeeTable);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        return scrollPane;
    }

    private TableColumn<User, User.AccessLevel> createAccessLevelColumn() {
        TableColumn<User, User.AccessLevel> column = new TableColumn<>("Access Level");
        column.setCellValueFactory(new PropertyValueFactory<>("accessLevel"));
        column.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(User.AccessLevel.values())));
        column.setOnEditCommit(event -> {
            User user = event.getRowValue();
            user.setAccessLevel(event.getNewValue());
        });
        column.setPrefWidth(150);
        return column;
    }
    private TableColumn<User, Cashier.Sectors> createSectorColumn() {
        TableColumn<User, Cashier.Sectors> column = new TableColumn<>("Sector");
        column.setCellValueFactory(new PropertyValueFactory<>("sector"));
        column.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(Cashier.Sectors.values())));
        column.setOnEditCommit(event -> {
            Cashier user = (Cashier)(event.getRowValue());
                Cashier cashier = (Cashier) user;
                cashier.setSector(event.getNewValue());
        });
        column.setPrefWidth(150);
        return column;
    }


    private TableColumn<User, Double> createSalaryColumn() {
        TableColumn<User, Double> column = new TableColumn<>("Salary");
        column.setCellValueFactory(new PropertyValueFactory<>("salary"));
        column.setCellFactory(TextFieldTableCell.forTableColumn(new javafx.util.converter.DoubleStringConverter()));
        column.setOnEditCommit(event -> {
            User user = event.getRowValue();
            user.setSalary(event.getNewValue());
        });
        column.setPrefWidth(150);
        return column;
    }


    private TableColumn<User, String> createEditableColumn(String title, String property, double width) {
        TableColumn<User, String> column = new TableColumn<>(title);
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        column.setCellFactory(TextFieldTableCell.forTableColumn());
        column.setOnEditCommit(event -> {
            User user = event.getRowValue();
            switch (property) {
                case "username":
                    user.setUsername(event.getNewValue());
                    break;
                case "email":
                    user.setEmail(event.getNewValue());
                    break;
                case "phonenr":
                    user.setPhonenr(event.getNewValue());
                    break;
                default:
                    break;
            }
        });
        column.setPrefWidth(width);
        return column;
    }

    private TableColumn<User, User.Position> createPositionColumn() {
        TableColumn<User, User.Position> column = new TableColumn<>("Position");
        column.setCellValueFactory(new PropertyValueFactory<>("position"));
        column.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(User.Position.values())));
        column.setOnEditCommit(event -> {
            User user = event.getRowValue();
            user.setPosition(event.getNewValue());
        });
        column.setPrefWidth(150);
        return column;
    }

    private void loadSampleData() {
        employees.add(new User("JohnDoe", "password123", LocalDate.of(2004,12,28), User.Position.manager, User.AccessLevel.full, 50000, "john.doe@example.com", "1234567890", User.Sectors.ALL));
        employees.add(new User("JaneSmith", "securepass", LocalDate.of(2004,12,28), User.Position.cashier, User.AccessLevel.full, 35000, "jane.smith@example.com", "9876543210", User.Sectors.GAMING));
    }

    public Button getSearchButton() {
        return searchButton;
    }

    public Administrator getAdmin() {
        return admin;
    }

    public TableView<User> getEmployeeTable() {
        return employeeTable;
    }

    public ObservableList<User> getEmployees() {
        return employees;
    }

    public void setEmployees(ObservableList<User> employees) {
        employeeTable.setItems(employees);
    }

    public Button getCheckOutNB() {
        return checkOutNB;
    }

    public Button getProductDbNB() {
        return productDbNB;
    }

    public Button getStatisticsNB() {
        return statisticsNB;
    }

    public Button getProfileNB() {
        return profileNB;
    }


    public TextField getSearchField() {
        return searchField;
    }

    public Button getModifyEmployee() {
        return modifyEmployee;
    }

    public void addNavBar(HBox navBar)
    {
        setBottom(navBar);
    }

}
