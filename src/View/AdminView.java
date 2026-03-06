package View;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import static javafx.scene.paint.Color.rgb;


public class AdminView extends Application {
    Color darkRed = rgb(146, 20, 12); //dark red
    Color lightOrange = rgb(255, 207, 153); //light orange
    Color darkBlue = rgb(17, 29, 74); //dark blue
    Color bgColor = rgb(255, 248, 240); //light bg color
    DropShadow shadow = new DropShadow(10, 5, 5, Color.GRAY);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane layout = new BorderPane();
        layout.setStyle("-fx-background-color: rgb(255, 248, 240)");

        layout.setCenter(editProfileDetails());

        HBox navBar = navigationBar();
        layout.setBottom(navBar);

        Scene scene = new Scene(layout, 1519, 730);
        stage.setScene(scene);
        stage.setTitle("Admin View");
        stage.show();
    }

    private Node loginAdminView() {
        Color darkRed = Color.rgb(146, 20, 12);
        Color lightOrange = Color.rgb(255, 207, 153);
        Color darkBlue = Color.rgb(17, 29, 74);
        Color bgColor = Color.rgb(255, 248, 240);

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: rgb(255, 248, 240);");

        VBox contentArea = new VBox();
        contentArea.setSpacing(20);
        contentArea.setAlignment(Pos.TOP_CENTER);
        contentArea.setPadding(new Insets(10, 20, 10, 20)); // Padding for left and right spacing

        // Greeting label, now centered
        Label greeting = new Label("Welcome Admin");
        greeting.setEffect(shadow);
        greeting.setStyle("-fx-text-fill: rgb(146, 20, 12); -fx-font-size: 30; -fx-font-weight: bold; -fx-font-family: 'Arial'");


        FlowPane employeeListPane = new FlowPane();
        employeeListPane.setStyle("-fx-background-color: rgb(17, 29, 74);");
        Label employeeListLabel = new Label("Employee List");
        employeeListLabel.setStyle("-fx-text-fill: white; -fx-font-size: 20; -fx-font-family: 'Arial';");
        employeeListPane.getChildren().add(employeeListLabel);

        // HBox for the greeting and the Employee List label
        HBox header = new HBox();
        header.setSpacing(20);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(0, 20, 0, 20));
        header.getChildren().addAll(greeting);

        contentArea.getChildren().add(header);
        contentArea.getChildren().add(employeeListPane); // Add Employee List pane below the greeting

        TableView<String> employeeTable = new TableView<>();
        employeeTable.setMinWidth(700); // Ensure table width exceeds visible area for horizontal scrolling
        employeeTable.setPrefHeight(700); // Table height for better fit

        TableColumn<String, String> usernameCol = new TableColumn<>("Username");
        TableColumn<String, String> dobCol = new TableColumn<>("Date of Birth");
        TableColumn<String, String> positionCol = new TableColumn<>("Position");
        TableColumn<String, String> accessLevelCol = new TableColumn<>("Access Level");
        TableColumn<String, String> salaryCol = new TableColumn<>("Salary");
        TableColumn<String, String> emailCol = new TableColumn<>("Email");
        TableColumn<String, String> phoneCol = new TableColumn<>("Phone No");

        employeeTable.setStyle("-fx-background-color: rgb(255, 207, 153); -fx-font-size: 12;");

        usernameCol.setPrefWidth(100);
        dobCol.setPrefWidth(100);
        positionCol.setPrefWidth(100);
        accessLevelCol.setPrefWidth(100);
        salaryCol.setPrefWidth(100);
        emailCol.setPrefWidth(100);
        phoneCol.setPrefWidth(100);

        employeeTable.getColumns().addAll(usernameCol, dobCol, positionCol, accessLevelCol, salaryCol, emailCol, phoneCol);

        ScrollPane tableScrollPane = new ScrollPane();
        tableScrollPane.setContent(employeeTable);
        tableScrollPane.setFitToHeight(false);
        tableScrollPane.setFitToWidth(true);
        tableScrollPane.setPrefViewportHeight(500);
        tableScrollPane.setStyle("-fx-background-color: white; -fx-border-color: gray;");

        VBox.setVgrow(tableScrollPane, Priority.ALWAYS);

        contentArea.getChildren().add(tableScrollPane);

        HBox actionButtons = new HBox();
        actionButtons.setSpacing(10);
        actionButtons.setAlignment(Pos.CENTER);


        Button modifyEmployee = new Button("Manage Employee");
        modifyEmployee.setStyle("-fx-text-fill: rgb(146, 20, 12); -fx-font-size: 20; -fx-font-weight: bold; -fx-font-family: 'Arial'");


        actionButtons.getChildren().addAll(modifyEmployee);
        contentArea.getChildren().add(actionButtons);

        HBox searchBox = new HBox();
        searchBox.setSpacing(10);
        searchBox.setAlignment(Pos.CENTER);

        TextField searchField = new TextField();
        searchField.setPromptText("Search employee...");
        searchField.setPrefWidth(200);

        Button searchButton = new Button("Search");
        searchButton.setStyle("-fx-background-color: rgb(17, 29, 74); -fx-text-fill: white;");

        searchBox.getChildren().addAll(searchField, searchButton);
        contentArea.getChildren().add(searchBox);

        root.setCenter(contentArea);


        return root;
    }

    public Node SaleStatsViewNode() {

        // Color palette
        Color darkRed = Color.rgb(146, 20, 12);
        Color lightOrange = Color.rgb(255, 207, 153);
        Color darkBlue = Color.rgb(17, 29, 74);

        VBox root = new VBox();
        root.setStyle("-fx-background-color: rgb(255, 248, 240);");
        root.setSpacing(10);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(10));

        // Title section
        Label title = new Label("Sale Statistics");
        title.setStyle("-fx-background-color: rgb(17, 29, 74); -fx-text-fill: white; -fx-font-size: 24; -fx-font-weight: bold;");
        title.setAlignment(Pos.CENTER);
        title.setPrefWidth(360);
        title.setPadding(new Insets(10));

        // Filters section in VBox
        VBox filters = new VBox();
        filters.setSpacing(10);
        filters.setAlignment(Pos.TOP_LEFT);

        Button sectorFilter = new Button("Sector a-z");
        Button productFilter = new Button("Prod Name a-z");
        Button cashierFilter = new Button("Cashier a-z");
        sectorFilter.setStyle("-fx-background-color: rgb(146, 20, 12); -fx-text-fill: white;");
        productFilter.setStyle("-fx-background-color: rgb(146, 20, 12); -fx-text-fill: white;");
        cashierFilter.setStyle("-fx-background-color: rgb(146, 20, 12); -fx-text-fill: white;");

        filters.getChildren().addAll(sectorFilter, productFilter, cashierFilter);

        // Filters and Search Section in HBox
        HBox filtersAndSearch = new HBox();
        filtersAndSearch.setSpacing(20);
        filtersAndSearch.setAlignment(Pos.TOP_CENTER);
        filtersAndSearch.setPadding(new Insets(10));
        filtersAndSearch.getChildren().add(filters);

        HBox searchBox = new HBox();
        searchBox.setSpacing(10);
        searchBox.setAlignment(Pos.CENTER);

        TextField searchCashier = new TextField();
        searchCashier.setPromptText("product name");
        searchCashier.setStyle("-fx-font-size: 14;");
        searchCashier.setPrefWidth(150);

        Button searchBtn = new Button("Search");
        searchBtn.setEffect(shadow);
        searchBtn.setStyle("-fx-background-color: rgb(146, 20, 12); -fx-text-fill: rgb(255,207,153)");

        searchBox.getChildren().addAll(searchCashier, searchBtn);
        filtersAndSearch.getChildren().add(searchBox);

        // Table section
        TableView<String> saleTable = new TableView<>();
        saleTable.setStyle("-fx-background-color: rgb(255, 207, 153); -fx-font-size: 12;");

        TableColumn<String, String> prodNameCol = new TableColumn<>("Prod Name");
        TableColumn<String, String> quantityCol = new TableColumn<>("Quantity");
        TableColumn<String, String> cashierCol = new TableColumn<>("Cashier");
        TableColumn<String, String> totalPriceCol = new TableColumn<>("Total Price");
        TableColumn<String, String> sectionCol = new TableColumn<>("Section");
        TableColumn<String, String> dateCol = new TableColumn<>("Date");

        prodNameCol.setPrefWidth(70);
        quantityCol.setPrefWidth(60);
        cashierCol.setPrefWidth(70);
        totalPriceCol.setPrefWidth(70);
        sectionCol.setPrefWidth(60);
        dateCol.setPrefWidth(60);

        saleTable.getColumns().addAll(prodNameCol, quantityCol, cashierCol, totalPriceCol, sectionCol, dateCol);
        saleTable.setPrefHeight(300);

        // Totals section
        VBox totals = new VBox();
        totals.setSpacing(5);
        totals.setStyle("-fx-background-color: rgb(245, 245, 245); -fx-border-color: gray;");
        totals.setPadding(new Insets(10));
        totals.setAlignment(Pos.TOP_LEFT);
        totals.setPrefWidth(360);

        Label totalIncome = new Label("Total income:");
        Label totalCosts = new Label("Total costs:");
        Label totalProfit = new Label("Total profit:");
        totalIncome.setStyle("-fx-font-size: 14;");
        totalCosts.setStyle("-fx-font-size: 14;");
        totalProfit.setStyle("-fx-font-size: 14;");

        totals.getChildren().addAll(totalIncome, totalCosts, totalProfit);

        // Adding everything to the root VBox
        root.getChildren().addAll(title, filtersAndSearch, saleTable, totals);

        return root;
    }

    public Node profilePane()
    {
        VBox root = new VBox(20);
        root.setAlignment(Pos.TOP_CENTER);

        //profile bar
        HBox titleBar = new HBox(20);
        Label profile = new Label("Profile");
        profile.setStyle("-fx-font-size: 30; -fx-font-weight: bold; -fx-text-fill: rgb(255,207,153)");
        profile.setAlignment(Pos.BASELINE_CENTER);
        titleBar.setStyle("-fx-background-color: rgb(17,29,74)");
        titleBar.setPrefHeight(60);
        titleBar.getChildren().addAll(profile);
        titleBar.setAlignment(Pos.CENTER);
        root.getChildren().add(titleBar);
        //hello user
        Label helloLabel = new Label("Hello admin_name");
        helloLabel.setStyle("-fx-font-size: 20; -fx-text-fill: rgb(137,149,194);");
        helloLabel.setAlignment(Pos.CENTER);
        root.getChildren().add(helloLabel);

        //user info box
        ImageView profileImage = new ImageView("View/Images/profileBoxIcon.png");
        profileImage.setPreserveRatio(true);
        profileImage.setFitHeight(127);
        profileImage.setFitWidth(132);
        Label profileInfo = new Label("Username:\nPosition:\nDate of birth:");
        profileInfo.setStyle("-fx-font-size: 20");

        HBox profileInfoBox = new HBox(25);
        profileInfoBox.setPadding(new Insets(30));
        profileInfoBox.setStyle("-fx-background-color: rgb(255,207,153)");
        profileInfoBox.setAlignment(Pos.CENTER_LEFT);
        profileInfoBox.getChildren().addAll(profileImage, profileInfo);
        root.getChildren().add(profileInfoBox);

        Line line = new Line();
        line.setStartX(0);
        line.setEndX(300);
        line.setStrokeWidth(1);
        line.setStyle("-fx-stroke: black;");
        root.getChildren().add(line);

        //info
        Label info = new Label("Password:\nAcces level:\nSalary:\nEmail:\nPhone nr:");
        info.setStyle("-fx-font-size: 20; -fx-text-fill: black;");
        info.setAlignment(Pos.TOP_LEFT);
        HBox infoRow = new HBox(25);
        infoRow.setPadding(new Insets(15));
        infoRow.getChildren().addAll(info);
        root.getChildren().add(infoRow);

        //buttons
        Button editProfile = new Button("Edit Profile");
        editProfile.setPrefSize(125, 30);
        editProfile.setStyle("-fx-background-color: #91140c ;-fx-text-fill: white; -fx-font-size: 20");
        Button logoutBtn = new Button("Logout");
        logoutBtn.setPrefSize(125, 30);
        logoutBtn.setStyle("-fx-background-color: rgb(17,29,74); -fx-text-fill: rgb(255,207,153); -fx-font-size: 20");

        HBox buttonRow = new HBox(50);
        buttonRow.setPadding(new Insets(20));
        buttonRow.setAlignment(Pos.CENTER);
        buttonRow.getChildren().addAll(editProfile, logoutBtn);
        root.getChildren().add(buttonRow);
        return root;
    }


    public Node productdbPane() {
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: rgb(255, 248, 240);");

        // Header
        Label title = new Label("Product Database");
        title.setStyle("-fx-font-weight: bold; -fx-text-fill: rgb(255,207,153); -fx-font-size: 30");
        title.setAlignment(Pos.BOTTOM_CENTER);

        Rectangle titleBG = new Rectangle(350, 90);
        titleBG.prefHeight(90);
        titleBG.setFill(darkBlue);

        StackPane titleStack = new StackPane(titleBG, title);
        titleStack.setAlignment(Pos.CENTER);
        titleStack.prefWidthProperty().bind(root.widthProperty());
        root.getChildren().add(titleStack);

        // Product table
        TableView<String> productTable = new TableView<>();
        TableColumn<String, String> productName = addTableColumn("Name");
        TableColumn<String, String> productId = addTableColumn("ID");
        TableColumn<String, String> productQuantity = addTableColumn("Quantity");
        TableColumn<String, String> productSection = addTableColumn("Section");
        TableColumn<String, String> productPrice = addTableColumn("Price");
        TableColumn<String, String> productSupplier = addTableColumn("Supplier");

        productTable.getColumns().addAll(productName, productId, productQuantity, productSection, productPrice);
        productTable.setPrefWidth(360);
        productTable.setEditable(true);
        productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        productTable.setStyle("-fx-background-color: rgb(255, 207, 153); -fx-border-color: black");
        productTable.setEffect(shadow);
        root.getChildren().add(productTable);

        // Total product costs
        FlowPane costPane = new FlowPane();
        costPane.setPadding(new Insets(10));
        costPane.setStyle("-fx-background-color: grey; -fx-border-color: black; -fx-border-width: 2;");
        costPane.setAlignment(Pos.BASELINE_LEFT); // Align the contents to the left

        Label totalCostsLabel = new Label("Total Product Costs:");
        totalCostsLabel.setStyle("-fx-text-fill: white; -fx-font-size: 18;");

        costPane.getChildren().add(totalCostsLabel);
        root.getChildren().add(costPane);

        // Search and View Suppliers button
        Button searchBtn = new Button("Search");
        searchBtn.setEffect(shadow);
        searchBtn.setStyle("-fx-background-color: rgb(146, 20, 12); -fx-text-fill: rgb(255,207,153)");

        Button viewSuppliersBtn = new Button("View Suppliers");
        viewSuppliersBtn.setEffect(shadow);
        viewSuppliersBtn.setStyle("-fx-background-color: rgb(17,29,74); -fx-text-fill: rgb(255,207,153)");

        TextField searchField = new TextField();
        searchField.setPromptText("Search product ID...");

        HBox searchBox = new HBox(15, searchField, searchBtn, viewSuppliersBtn);
        searchBox.setSpacing(15);
        searchBox.setAlignment(Pos.TOP_RIGHT);
        root.getChildren().add(searchBox);

        return root;
    }

    public Node editProfileDetails() {
        VBox root = new VBox(20);
        root.setPadding(new Insets(10));
        root.setStyle("-fx-background-color: rgb(255, 248, 240);");

        // Back button stack setup
        Button backBtn = new Button(" Back");
        backBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: rgb(137,149,194); -fx-font-size: 17");
        Rectangle backBG = new Rectangle(65, 30);
        backBtn.setAlignment(Pos.CENTER);
        backBG.setFill(Color.rgb(32, 55, 140));
        backBG.setArcHeight(40);
        backBG.setArcWidth(40);

        StackPane backStack = new StackPane(backBG, backBtn);
        backStack.setAlignment(Pos.CENTER);
        backStack.setPrefWidth(100);
        backStack.setPadding(new Insets(10));

        // Top bar
        Label title = new Label("Manage Employees");
        title.setStyle("-fx-font-size: 25; -fx-text-fill: rgb(255,207,153); -fx-font-weight: italic");
        title.setAlignment(Pos.CENTER);

        HBox titleHBox = new HBox(backStack, title);
        titleHBox.setAlignment(Pos.CENTER_LEFT);
        titleHBox.setSpacing(25);
        titleHBox.setPadding(new Insets(10));
        titleHBox.setStyle("-fx-background-color: rgb(17,29,74)");
        root.getChildren().add(titleHBox);

        // Editable profile box
        GridPane infoGrid = new GridPane();
        infoGrid.setStyle("-fx-background-color: rgb(255,207,153)");
        infoGrid.setHgap(10);
        infoGrid.setVgap(10);
        infoGrid.setPadding(new Insets(20));

        String[] labels = {"Username:", "Date of birth:","Position:", "Access level:","Salary", "Email","Phone nr:"};
        for (int i = 0; i < labels.length; i++) {
            ImageView icon = new ImageView(new Image("View/Images/editIcon.png"));
            icon.setFitWidth(20);
            icon.setFitHeight(20);

            Label label = new Label(labels[i]);
            label.setStyle("-fx-font-size: 16; -fx-text-fill: black;");

            infoGrid.add(icon, 0, i);
            infoGrid.add(label, 1, i);
        }

        root.getChildren().add(infoGrid);


        Button addEmployeeButton = new Button("Register Employee");
        addEmployeeButton.setStyle("-fx-background-color: rgb(165, 42, 42); -fx-text-fill: white; -fx-font-size: 14;");
        addEmployeeButton.setPrefWidth(150);

        Button createEmployeeButton = new Button("Modify Employee");
        createEmployeeButton.setStyle("-fx-background-color: rgb(165, 42, 42); -fx-text-fill: white; -fx-font-size: 14;");
        createEmployeeButton.setPrefWidth(150);

        Button deleteEmployeeButton = new Button("Delete Employee");
        deleteEmployeeButton.setStyle("-fx-background-color: rgb(165, 42, 42); -fx-text-fill: white; -fx-font-size: 14;");
        deleteEmployeeButton.setPrefWidth(150);

        // HBox to contain the buttons
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(15));
        buttonBox.getChildren().addAll(addEmployeeButton, createEmployeeButton, deleteEmployeeButton);

        root.getChildren().add(buttonBox);
        root.setAlignment(Pos.TOP_CENTER);

        return root;
    }



    public HBox navigationBar() {
        ImageView employeesListIcon = createIcon("/View/images/employeesListIcon.png");
        ImageView productDataIcon = createIcon("/View/images/productDataIcon.png");
        ImageView statisticsIcon = createIcon("/View/images/statisticsIcon.png");
        ImageView profileIcon = createIcon("/View/images/profileIcon.png");

        employeesListIcon.setFitWidth(32);
        employeesListIcon.setFitHeight(32);

        productDataIcon.setFitWidth(35);
        productDataIcon.setFitHeight(35);

        statisticsIcon.setFitWidth(34);
        statisticsIcon.setFitHeight(34);

        profileIcon.setFitWidth(35);
        profileIcon.setFitHeight(35);

        HBox navBar = new HBox();
        Button checkOutNB = new Button();
        checkOutNB.setPrefSize(98.25, 53);
        checkOutNB.setStyle("-fx-background-color: rgb(17,29,74)");
        checkOutNB.setGraphic(employeesListIcon);

        Button productDbNB = new Button();
        productDbNB.setPrefSize(98.25, 53);
        productDbNB.setStyle("-fx-background-color: rgb(17,29,74)");
        productDbNB.setGraphic(productDataIcon);

        Button statisticsNB = new Button();
        statisticsNB.setPrefSize(98.25, 53);
        statisticsNB.setStyle("-fx-background-color: rgb(17,29,74)");
        statisticsNB.setGraphic(statisticsIcon);

        Button profileNB = new Button();
        profileNB.setPrefSize(98.25, 53);
        profileNB.setStyle("-fx-background-color: rgb(17,29,74)");
        profileNB.setGraphic(profileIcon);

        navBar.setStyle("-fx-background-color: rgb(17,29,74)");
        navBar.setAlignment(Pos.BOTTOM_CENTER);
        navBar.getChildren().addAll(checkOutNB, productDbNB, statisticsNB, profileNB);
        return navBar;
    }




    public TableColumn addTableColumn(String colName) {
        TableColumn<String, String> col = new TableColumn<>(colName);
        col.setStyle("-fx-background-color: rgb(255, 207, 153); -fx-border-color: black");
        return col;
    }

    private ImageView createIcon(String path) {
        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream(path)));
        icon.setFitWidth(32);
        icon.setFitHeight(29);
        return icon;
    }
}


