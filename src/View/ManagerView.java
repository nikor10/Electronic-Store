package View;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.scene.chart.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import models.*;
import Controller.ManagerController;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.lang.Number;



public class ManagerView extends Application {


    private Manager user;

    public Button getLogoutBtn() {
        return logoutBtn;
    }

    private Button logoutBtn = new Button("Logout");

    public ManagerView(Manager manager){
        this.user = manager;
    }

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");




    ManagerController managerController = new ManagerController();
    public static void main(String[] args) {
        launch(args);
    }




    @Override
    public void start(Stage managerStage){

        BorderPane mainLayout = new BorderPane();
        Scene mainScene = new Scene(mainLayout, 1519, 730);


//----------------------------------------Labels for all -------------------------------------------------
        //title label
        Label titleSList = new Label("Supplier List");
        titleSList.setStyle("-fx-text-fill: rgb(255, 248, 240); -fx-font-size: 25; -fx-font-weight: bold; -fx-padding: 15 ;-fx-font-family: 'Arial'; -fx-background-color: rgb(17, 29, 74)" );
        titleSList.setAlignment(Pos.TOP_CENTER);
        titleSList.setPrefSize(1200, 30);

        Label titleprodDB = new Label("Product Inventory");
        titleprodDB.setStyle("-fx-text-fill: rgb(255, 248, 240); -fx-font-size: 25; -fx-font-weight: bold; -fx-padding: 15 ;-fx-font-family: 'Arial'; -fx-background-color: rgb(17, 29, 74);" );
        titleprodDB.setAlignment(Pos.TOP_CENTER);
        titleprodDB.setPrefSize(1200, 30);

        Label titleStats = new Label("Statistics");
        titleStats.setStyle("-fx-text-fill: rgb(255, 248, 240); -fx-font-size: 25; -fx-font-weight: bold; -fx-padding: 15 ;-fx-font-family: 'Arial'; -fx-background-color: rgb(17, 29, 74)" );
        titleStats.setAlignment(Pos.TOP_CENTER);
        titleStats.setPrefSize(1200, 30);

        Label titleProfile = new Label("Profile");
        titleProfile.setStyle("-fx-text-fill: rgb(255, 248, 240); -fx-font-size: 25; -fx-font-weight: bold; -fx-padding: 15 ;-fx-font-family: 'Arial'; -fx-background-color: rgb(17, 29, 74)" );
        titleProfile.setAlignment(Pos.TOP_CENTER);
        titleProfile.setPrefSize(1200, 30);


        Label titleAddProduct = new Label("Add Product");
        titleAddProduct.setStyle("-fx-text-fill: rgb(255, 248, 240); -fx-font-size: 25; -fx-font-weight: bold; -fx-padding: 15 ;-fx-font-family: 'Arial'; -fx-background-color: rgb(17, 29, 74)" );
        titleAddProduct.setAlignment(Pos.TOP_CENTER);
        titleAddProduct.setPrefSize(1200, 30);


        Label titleVisualStat = new Label("Visual Statistics");
        titleVisualStat.setStyle("-fx-text-fill: rgb(255, 248, 240); -fx-font-size: 25; -fx-font-weight: bold; -fx-padding: 15 ;-fx-font-family: 'Arial'; -fx-background-color: rgb(17, 29, 74)" );
        titleVisualStat.setAlignment(Pos.CENTER);
        titleVisualStat.setPrefSize(1200, 30);


//-----------------------------profile---------------------------------------------------------------------------------------------


        Label helloLabel = new Label("Hello " + user.getUsername() +"!!");
        //Label helloLabel = new Label("Hello profileHello" );
        helloLabel.setStyle("-fx-font-size: 20; -fx-text-fill: rgb(137,149,194);");

        //user info box
        ImageView profileImage = new ImageView("View/Images/profileBoxIcon.png"); //default img
        profileImage.setPreserveRatio(true);
        profileImage.setFitHeight(77);
        profileImage.setFitWidth(92);
        Label UsernameLabel = new Label("Username: " + user.getUsername());
        Label PositionLabel = new Label("Position: " + user.getPosition());
        Label DateOfBirthLabel = new Label("Date of Birth: "+ user.getBirthday());

        VBox profileInfo = new VBox(5);
        profileInfo.getChildren().addAll(helloLabel, UsernameLabel, PositionLabel, DateOfBirthLabel);
        profileInfo.setStyle("-fx-font-size: 15");

        HBox profileInfoBox = new HBox(15);
        profileInfoBox.setPadding(new Insets(30));
        profileInfoBox.setStyle("-fx-background-color: rgb(255,207,153); -fx-max-width: 400");
        profileInfoBox.setAlignment(Pos.CENTER_LEFT);
        profileInfoBox.getChildren().addAll(profileImage, profileInfo);

        Line line = new Line();
        line.setStartX(0);
        line.setEndX(300);
        line.setStrokeWidth(1);
        line.setStyle("-fx-stroke: rgb(17, 29, 74);");

        //info
        Label profileEmail = new Label("Email: "+user.getEmail());
        Label profilePhoneNr = new Label("Phone nr: "+ user.getPhonenr());
        VBox info = new VBox(10);
        info.setStyle("-fx-font-size: 20; -fx-text-fill: rgb(17, 29, 74); -fx-max-width: 400");
        info.setAlignment(Pos.TOP_LEFT);
        info.getChildren().addAll(profileEmail, profilePhoneNr);
        info.setPadding(new Insets(15));

//-------------------------edit profile view ---------------------------------------------------------------------------------------

        VBox editProfileBox = new VBox(50);
        editProfileBox.setMaxSize(1519, 730);
        editProfileBox.setStyle("-fx-background-color: rgb(255, 248, 240); -fx-padding: 30;");
        Scene editProfileScene = new Scene(editProfileBox, 1519, 730);

        //back button stack setup
        Button addBackAdd = new Button("Back");
        addBackAdd.setOnAction(e -> managerStage.setScene(mainLayout.getScene()));
        addBackAdd.setStyle("-fx-min-width:100; -fx-font-size: 14px; -fx-background-color: rgb(17, 29, 74); -fx-text-fill: rgb(255, 207, 153); -fx-border-color: rgb(255, 207, 153)");
        HBox profileBacButtonContainer = new HBox();
        profileBacButtonContainer.getChildren().addAll(addBackAdd);


        //top bar
        Label titleEdit = new Label("Profile Edit");
        titleEdit.setStyle("-fx-text-fill: rgb(255, 248, 240); -fx-font-size: 25; -fx-font-weight: bold; -fx-padding: 15 ;-fx-font-family: 'Arial'; -fx-background-color: rgb(17, 29, 74)" );
        titleEdit.setAlignment(Pos.TOP_CENTER);
        titleEdit.setPrefSize(1200, 30);


        //editable profile box
        VBox credentialSection = new VBox(10);
        credentialSection.setStyle("-fx-background-color: rgb(255,207,153)");
        credentialSection.setPadding(new Insets(20));

        ManagerController controller = new ManagerController();

        Button updateButton = new Button("Update");
        updateButton.setStyle("-fx-background-color: rgb(165, 42, 42); -fx-text-fill: white; -fx-font-size: 16; -fx-padding: 10;");
        updateButton.setPrefWidth(100);

        // Add an upload button for the profile picture in the edit profile section
        final byte[][] imageBytes = {null};
        Button uploadImageBtn = new Button("Change profile image");
        uploadImageBtn.setStyle("-fx-background-color: rgb(17,29,74); -fx-text-fill: white; -fx-padding: 10; -fx-gap-start-and-end-margin: 10");
        uploadImageBtn.setOnAction(e -> {
            // mundson perdorusin t zgjedhi nje img file
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
            File selectedFile = fileChooser.showOpenDialog(managerStage); //hap popupin q t zgjedhesh foton

            if (selectedFile != null) {
                try {
                    // Convert selected file into  Image
                    Image newProfileImage = new Image(selectedFile.toURI().toString());
                    // Update the profileImage ImageView
                    profileImage.setImage(newProfileImage);
                    // Convert the selected image to a byte array and update the User model
                    InputStream inputStream = new FileInputStream(selectedFile);
                    imageBytes[0] = inputStream.readAllBytes();
                    inputStream.close();
                } catch (IOException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Image Upload Error");
                    alert.setHeaderText("Failed to upload the image");
                }
            }
        });


// input fields and labels
        Label usernameE = new Label("Username: " + user.getUsername());
        TextField usernameField = new TextField(user.getUsername());
        usernameE.setStyle("-fx-font-size: 15");
        usernameField.setPromptText("Username");
        usernameField.setStyle("-fx-background-color: rgb(255,207,153); -fx-font-size: 15; -fx-border-color: transparent transparent black transparent; -fx-border-width: 0 0 1 0;");
        usernameField.setVisible(false);
        ImageView iconUsername = managerController.createEditIcon("/View/Images/editIcon.png");
        Button usernameEdit = new Button();
        usernameEdit.setStyle("-fx-background-color: rgb(255,207,153); -fx-font-size: 15");
        usernameEdit.setGraphic(iconUsername);

        Label passwordE = new Label("Password: " + user.getPassword());
        TextField passwordField = new TextField(user.getPassword());
        passwordE.setStyle("-fx-font-size: 15");
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-background-color: rgb(255,207,153); -fx-font-size: 15; -fx-border-color: transparent transparent black transparent; -fx-border-width: 0 0 1 0;");
        passwordField.setVisible(false);
        ImageView iconPsw = managerController.createEditIcon("/View/Images/editIcon.png");
        Button passwordEdit = new Button();
        passwordEdit.setStyle("-fx-background-color: rgb(255,207,153); -fx-font-size: 15");
        passwordEdit.setGraphic(iconPsw);

        Label dateOfBirthE = new Label("Date of Birth: " + user.getBirthday().format(dateFormatter));
        dateOfBirthE.setStyle("-fx-font-size: 15");
        DatePicker dateOfBirthPicker = new DatePicker(user.getBirthday());
        dateOfBirthPicker.setStyle("-fx-font-size: 15");
        dateOfBirthPicker.setPromptText("Date of Birth");
        dateOfBirthPicker.setVisible(false);
        ImageView iconbday = managerController.createEditIcon("/View/Images/editIcon.png");
        Button bdayEdit = new Button();
        bdayEdit.setStyle("-fx-background-color: rgb(255,207,153); -fx-font-size: 15");
        bdayEdit.setGraphic(iconbday);
        dateOfBirthPicker.setVisible(false);

        Label emailE = new Label("e-mail: " + user.getEmail());
        emailE.setStyle("-fx-font-size: 15");
        TextField emailField = new TextField(user.getEmail());
        emailField.setPromptText("e-mail");
        emailField.setStyle("-fx-background-color: rgb(255,207,153); -fx-font-size: 15; -fx-border-color: transparent transparent black transparent; -fx-border-width: 0 0 1 0;");
        emailField.setVisible(false);
        ImageView iconemail = managerController.createEditIcon("/View/Images/editIcon.png");
        Button emailEdit = new Button();
        emailEdit.setStyle("-fx-background-color: rgb(255,207,153); -fx-font-size: 15");
        emailEdit.setGraphic(iconemail);

        Label phoneNrE = new Label("Phone Number: " + user.getPhonenr());
        phoneNrE.setStyle("-fx-font-size: 15");
        TextField phoneField = new TextField(user.getPhonenr());
        phoneField.setPromptText("Phone Number");
        phoneField.setStyle("-fx-background-color: rgb(255,207,153); -fx-font-size: 15; -fx-border-color: transparent transparent black transparent; -fx-border-width: 0 0 1 0;");
        phoneField.setVisible(false);
        ImageView iconPhNr = managerController.createEditIcon("/View/Images/editIcon.png");
        Button phoneEdit = new Button();
        phoneEdit.setStyle("-fx-background-color: rgb(255,207,153); -fx-font-size: 15");
        phoneEdit.setGraphic(iconPhNr);



// HBox for each field
        HBox usernameBox = new HBox(1, usernameEdit, usernameE, usernameField);
        HBox passwordBox = new HBox(1, passwordEdit, passwordE, passwordField);
        HBox bdayBox = new HBox(1, bdayEdit, dateOfBirthE, dateOfBirthPicker);
        HBox emailBox = new HBox(1, emailEdit, emailE, emailField);
        HBox phoneBox = new HBox(1, phoneEdit, phoneNrE, phoneField);

// Handle Edit Button Actions
        usernameEdit.setOnAction(e -> {
            usernameE.setVisible(false);
            usernameField.setVisible(true);
        });

        passwordEdit.setOnAction(e -> {
            passwordE.setVisible(false);
            passwordField.setVisible(true);
        });

        bdayEdit.setOnAction(e -> {
            dateOfBirthE.setVisible(false);
            dateOfBirthPicker.setVisible(true);
        });

        emailEdit.setOnAction(e -> {
            emailE.setVisible(false);
            emailField.setVisible(true);
        });

        phoneEdit.setOnAction(e -> {
            phoneNrE.setVisible(false);
            phoneField.setVisible(true);
        });

        // Update Button Action
        updateButton.setOnAction(e -> {
            try {
                // Gather input from fields
                String newUsername = usernameField.getText();
                String newPassword = passwordField.getText();
                LocalDate newDateOfBirth = dateOfBirthPicker.getValue();
                String newEmail = emailField.getText();
                String newPhoneNr = phoneField.getText();

                // Validate inputs
                if (newDateOfBirth == null || newDateOfBirth.getYear() < 1925 || newDateOfBirth.getYear() > 2009) {
                    throw new IllegalArgumentException("Date of Birth must be between 1925 and 2009.");
                }

                if (!newPhoneNr.matches("\\d{10}")) {
                    throw new IllegalArgumentException("Phone Number must be exactly 10 digits.");
                }

                // Update the user profile (controller handles the logic)
                controller.updateManagerProfile(user, newUsername, newPassword, newDateOfBirth, newEmail, newPhoneNr, imageBytes[0]);

                // Update the UI with the new data
                usernameE.setText("Username: " + user.getUsername());
                passwordE.setText("Password: " + user.getPassword());
                dateOfBirthE.setText("Date of Birth: " + user.getBirthday().format(dateFormatter));
                emailE.setText("e-mail: " + user.getEmail());
                phoneNrE.setText("Phone Number: " + user.getPhonenr());

                // Update profile page UI
                helloLabel.setText("Hello " + user.getUsername());
                UsernameLabel.setText("Username: " + user.getUsername());
                DateOfBirthLabel.setText("Date of birth: " + dateOfBirthE.getText());
                profileEmail.setText("Email: " + user.getEmail());
                profilePhoneNr.setText("Phone Number: " + user.getPhonenr());

                // Toggle visibility back to labels
                usernameE.setVisible(true);
                usernameField.setVisible(false);
                passwordE.setVisible(true);
                passwordField.setVisible(false);
                dateOfBirthE.setVisible(true);
                dateOfBirthPicker.setVisible(false);
                emailE.setVisible(true);
                emailField.setVisible(false);
                phoneNrE.setVisible(true);
                phoneField.setVisible(false);

            } catch (IllegalArgumentException ex) {
                // Show error message in a popup
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText(null);
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        });

// Add all components to a VBox
        credentialSection.getChildren().addAll(usernameBox, passwordBox, emailBox, bdayBox, phoneBox, uploadImageBtn);
        credentialSection.setPadding(new Insets(15));
        credentialSection.setAlignment(Pos.CENTER);
        credentialSection.setMaxSize(500,1000);

        editProfileBox.getChildren().addAll(titleEdit, profileBacButtonContainer, credentialSection, updateButton);
        editProfileBox.setAlignment(Pos.CENTER);

        //buttons
        Button editProfile = new Button("Edit Profile");
        editProfile.setPrefSize(125, 30);
        editProfile.setStyle("-fx-background-color: #91140c ;-fx-text-fill: white; -fx-font-size: 20;");
        editProfile.setOnAction(e -> managerStage.setScene(editProfileScene));


        logoutBtn.setPrefSize(125, 30);
        logoutBtn.setStyle("-fx-background-color: rgb(17,29,74); -fx-text-fill: rgb(255,207,153); -fx-font-size: 20;");
        logoutBtn.setOnAction(e -> {

        });
        HBox bottomButtons = new HBox(50, logoutBtn, editProfile);
        bottomButtons.setAlignment(Pos.CENTER);
//---------------------------------------------------table and search for supplier------------------------------------------------------------------------------------------------------------------

        // Search Box
        TextField searchBoxS = new TextField();
        searchBoxS.setPromptText("Search");
        searchBoxS.setStyle("-fx-max-width:400; -fx-font-size: 14px; -fx-background-color: rgb(146, 20, 12); -fx-text-fill: rgb(255, 207, 153); -fx-border-color: rgb(255, 207, 153);");
        searchBoxS.setAlignment(Pos.CENTER);
        HBox searchBoxSContainer = new HBox();
        searchBoxSContainer.getChildren().addAll(searchBoxS);
        searchBoxSContainer.setAlignment(Pos.TOP_RIGHT);


        //table for suppliers
        TableView<SupplierTable> supplierTable;

        //table columns
        TableColumn<SupplierTable, Integer> SupplierNumber = new TableColumn<>("Num");
        SupplierNumber.setMinWidth(80);
        SupplierNumber.setCellValueFactory(new PropertyValueFactory<SupplierTable, Integer>("number"));
        SupplierNumber.setStyle("-fx-background-color:rgb(255, 248, 240);-fx-border-color: rgb(255, 207, 153); -fx-text-fill: rgb(146, 20, 12);");

        TableColumn<SupplierTable, String> SupplierName = new TableColumn<>("Supplier");
        SupplierName.setMinWidth(120);
        SupplierName.setCellValueFactory(new PropertyValueFactory<SupplierTable, String>("name"));
        SupplierName.setStyle("-fx-background-color: rgb(255, 248, 240); -fx-border-color: rgb(255, 207, 153); -fx-text-fill: rgb(146, 20, 12);");

        TableColumn<SupplierTable, String> Products = new TableColumn<>("Products");
        Products.setMinWidth(230);
        Products.setCellValueFactory(new PropertyValueFactory<SupplierTable, String>("products"));
        Products.setStyle("-fx-background-color: rgb(255, 248, 240); -fx-border-color: rgb(255, 207, 153); -fx-text-fill: rgb(146, 20, 12);");

        TableColumn<SupplierTable, String> SupplierContact = new TableColumn<>("Contact");
        SupplierContact.setMinWidth(120);
        SupplierContact.setCellValueFactory(new PropertyValueFactory<SupplierTable, String>("supplierContact"));
        SupplierContact.setStyle("-fx-background-color: rgb(255, 248, 240); -fx-border-color: rgb(255, 207, 153); -fx-text-fill: rgb(146, 20, 12);");

        //creating tableview
        supplierTable = new TableView<>();
        supplierTable.getColumns().addAll(SupplierNumber, SupplierName, Products, SupplierContact);
        supplierTable.setMaxWidth(120);
        supplierTable.setMinHeight(20);
        supplierTable.setMinSize(550, 260);


        searchBoxS.textProperty().addListener((observable, oldValue, newValue) -> {
            managerController.filterSupplierList(newValue); // Call controller method to handle the logic
        });

        // Set the filtered data to the TableView
        supplierTable.setItems(managerController.getFilteredData());

//-------------------------------------table for products-----------------------------------------

        // Search Box
        TextField searchBoxP = new TextField();
        searchBoxP.setPromptText("Search");
        searchBoxP.setStyle("-fx-max-width:400; -fx-font-size: 14px; -fx-background-color: rgb(146, 20, 12); -fx-text-fill: rgb(255, 207, 153); -fx-border-color: rgb(255, 207, 153);");
        searchBoxP.setAlignment(Pos.CENTER);
        HBox searchBoxPContainer = new HBox();
        searchBoxPContainer.getChildren().addAll(searchBoxP);
        searchBoxPContainer.setAlignment(Pos.TOP_RIGHT);

        //table for product
        TableView<Item> productsTable;
        //creating tableview
        productsTable = new TableView<>();
        productsTable.setMaxSize(750, 200);
        productsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        productsTable.setEditable(true);

        //table columns
        TableColumn<Item, String> itemName = new TableColumn<>("Name");
        itemName.setMinWidth(110);
        itemName.setCellValueFactory(new PropertyValueFactory<Item, String>("itemName"));
        itemName.setStyle("-fx-background-color: rgb(255, 248, 240);-fx-border-color: rgb(255, 207, 153); -fx-text-fill: rgb(146, 20, 12);");

        TableColumn<Item, String> itemid = new TableColumn<>("itemID");
        itemid.setMinWidth(100);
        itemid.setCellValueFactory(new PropertyValueFactory<Item, String>("itemid"));
        itemid.setStyle("-fx-background-color: rgb(255, 248, 240); -fx-border-color: rgb(255, 207, 153); -fx-text-fill: rgb(146, 20, 12);");

        TableColumn<Item, Integer> stock = new TableColumn<>("stock");
        stock.setMinWidth(100);
        stock.setStyle("-fx-background-color: rgb(255, 248, 240); -fx-border-color: rgb(255, 207, 153); -fx-text-fill: rgb(146, 20, 12);");
        stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        stock.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        stock.setOnEditCommit(event -> {
            Item selectedItem = event.getRowValue();
            int newQuantity = event.getNewValue();
            managerController.updateItemQuantity(selectedItem, newQuantity);
        });


        TableColumn<Item, String> section = new TableColumn<>("section");
        section.setMinWidth(100);
        section.setCellValueFactory(new PropertyValueFactory<Item, String>("section"));
        section.setStyle("-fx-background-color: rgb(255, 248, 240); -fx-border-color: rgb(255, 207, 153); -fx-text-fill: rgb(146, 20, 12)");

        TableColumn<Item, String> supplier = new TableColumn<>("supplier");
        supplier.setMinWidth(100);
        supplier.setCellValueFactory(new PropertyValueFactory<Item, String>("supplier"));
        supplier.setStyle("-fx-background-color: rgb(255, 248, 240); -fx-border-color: rgb(255, 207, 153); -fx-text-fill: rgb(146, 20, 12)");

        TableColumn<Item, Double> purchasedPrice = new TableColumn<>("purchased price");
        purchasedPrice.setMinWidth(120);
        purchasedPrice.setCellValueFactory(new PropertyValueFactory<Item, Double>("stock"));
        purchasedPrice.setStyle("-fx-background-color: rgb(255, 248, 240); -fx-border-color: rgb(255, 207, 153); -fx-text-fill: rgb(146, 20, 12)");

        TableColumn<Item, Double> sellingPrice = new TableColumn<>("selling price");
        sellingPrice.setMinWidth(120);
        sellingPrice.setCellValueFactory(new PropertyValueFactory<Item, Double>("sellingPrice"));
        sellingPrice.setStyle("-fx-background-color: rgb(255, 248, 240); -fx-border-color: rgb(255, 207, 153); -fx-text-fill: rgb(146, 20, 12)");


        productsTable.getColumns().addAll(itemName, itemid, stock, section, supplier, purchasedPrice, sellingPrice);


        searchBoxP.textProperty().addListener((observable, oldValue, newValue) -> {
            productsTable.setItems(managerController.getFilteredProductData(newValue));
        });

        managerController.setProductsTable(productsTable);


        // Set the filtered data to the TableView
        productsTable.setItems(managerController.getFilteredProductData(searchBoxP.getText()));



//---------------delete row in product db------------------------------------------------------------

        Button deleteButton = new Button("Delete  Row");
        deleteButton.setStyle("-fx-min-width:100; -fx-font-size: 14px; -fx-background-color: rgb(146, 20, 12); -fx-text-fill: rgb(255, 207, 153); -fx-border-color: rgb(255, 207, 153)");
        deleteButton.setOnAction(event -> {
            Item selectedItem = productsTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                managerController.deleteItem(selectedItem); // Delete the selected item
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Item deleted successfully!", ButtonType.OK);
                alert.getDialogPane().setStyle("-fx-background-color: white; -fx-font-size: 14px; -fx-text-fill: black;");
                alert.show();
            }
        });

        // Set the TableView to be editable
        productsTable.setEditable(true);


//----------------------add new Product ------------------------------------------------
        TextField newProductNameInput = new TextField();
        newProductNameInput.setPromptText("product name");
        newProductNameInput.setStyle("-fx-background-color: rgb(255, 207, 153); -fx-max-width: 400");

        TextField newProductIDInput = new TextField();
        newProductIDInput.setPromptText("product id");
        newProductIDInput.setStyle("-fx-background-color: rgb(255, 207, 153); -fx-max-width: 400");

        TextField newProductQuantityInput = new TextField();
        newProductQuantityInput.setPromptText("product quantity");
        newProductQuantityInput.setStyle("-fx-background-color: rgb(255, 207, 153); -fx-max-width: 400");

        TextField newProductSectionInput = new TextField();
        newProductSectionInput.setPromptText("product section");
        newProductSectionInput.setStyle("-fx-background-color: rgb(255, 207, 153); -fx-max-width: 400");

        TextField newProductSupplierInput = new TextField();
        newProductSupplierInput.setPromptText("product supplier");
        newProductSupplierInput.setStyle("-fx-background-color: rgb(255, 207, 153); -fx-max-width: 400");

        TextField newProductPriceBought = new TextField();
        newProductPriceBought.setPromptText("price bought");
        newProductPriceBought.setStyle("-fx-background-color: rgb(255, 207, 153); -fx-max-width: 400");


        TextField newProductPriceSold = new TextField();
        newProductPriceSold.setPromptText("price sold");
        newProductPriceSold.setStyle("-fx-background-color: rgb(255, 207, 153); -fx-padding: 10; -fx-max-width: 400");

        TextField newProductSupplierContact = new TextField();
        newProductSupplierContact.setPromptText("supplier contact");
        newProductSupplierContact.setStyle("-fx-background-color: rgb(255, 207, 153); -fx-padding: 10; -fx-max-width: 400");

        VBox inputs = new VBox(15);
        inputs.getChildren().addAll(newProductNameInput, newProductIDInput, newProductQuantityInput, newProductSectionInput,newProductSupplierInput, newProductPriceBought, newProductPriceSold, newProductSupplierContact);

        Button addNewProd = new Button("Add");
        addNewProd.setStyle("-fx-min-width:100; -fx-font-size: 14px; -fx-background-color: rgb(146, 20, 12); -fx-text-fill: rgb(255, 207, 153); -fx-border-color: rgb(255, 207, 153)");
        addNewProd.setOnAction(event -> {
            try {
                String newPname = newProductNameInput.getText();
                int newPid = Integer.parseInt(newProductIDInput.getText());
                int newPquantity = Integer.parseInt(newProductQuantityInput.getText());
                String newPsection = newProductSectionInput.getText();
                String newPsupplier = newProductSupplierInput.getText();
                double newPpurchasedPrice = Double.parseDouble(newProductPriceBought.getText());
                double newPsellingPrice = Double.parseDouble(newProductPriceSold.getText());
                String newPsupplierContact = newProductSupplierContact.getText();

                managerController.addButton(newPname, newPid, newPquantity, newPsection, newPsupplier, newPpurchasedPrice, newPsellingPrice, newPsupplierContact);

                // Clear input fields after addition
                newProductNameInput.clear();
                newProductIDInput.clear();
                newProductQuantityInput.clear();
                newProductSectionInput.clear();
                newProductSupplierInput.clear();
                newProductPriceBought.clear();
                newProductPriceSold.clear();
                newProductSupplierContact.clear();
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.getDialogPane().setStyle("-fx-background-color: white; -fx-font-size: 14px; -fx-text-fill: black;");
                alert.setContentText("Please ensure all fields are filled out correctly.");
                alert.show();
            }
        });


        Button BackAdd = new Button("Back");
        BackAdd.setOnAction(e -> managerStage.setScene(mainLayout.getScene()));
        BackAdd.setStyle("-fx-min-width:100; -fx-font-size: 14px; -fx-background-color: rgb(17, 29, 74); -fx-text-fill: rgb(255, 207, 153); -fx-border-color: rgb(255, 207, 153)");

//--------------------------------------vbox in addProduct------------------------------------------------
        VBox addProductPage = new VBox(40);
        addProductPage.setMaxSize(1519, 730);
        addProductPage.setStyle("-fx-background-color: rgb(255, 248, 240); -fx-padding: 30;");
        addProductPage.getChildren().addAll(titleAddProduct, BackAdd, inputs, addNewProd);
        Scene addProductScene = new Scene(addProductPage, 1519, 730);


//---------------------------------VBox for search and addbutton------------------------

        Region spacer = new Region();
        Button AddPageButton = new Button("Add Product");
        AddPageButton.setOnAction(e -> managerStage.setScene(addProductScene));
        AddPageButton.setStyle("-fx-min-width:100; -fx-font-size: 14px; -fx-background-color: rgb(146, 20, 12); -fx-text-fill: rgb(255, 207, 153); -fx-border-color: rgb(255, 207, 153)");
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox insideProducts = new HBox(15);
        insideProducts.getChildren().addAll(AddPageButton, deleteButton);
        insideProducts.setAlignment(Pos.CENTER);

//-----------------------------sales stats--------------------------------------------------


        // Item Name column
        TableColumn<ManagerController.CustomData, String> itemNameColumn = new TableColumn<>("Item Name");
        itemNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItem().getItemName()));
        itemNameColumn.setStyle("-fx-background-color: rgb(255, 248, 240); -fx-border-color: rgb(255, 207, 153); -fx-text-fill: rgb(146, 20, 12);");
        itemNameColumn.setMinWidth(110);

        // Quantity Sold column
        TableColumn<ManagerController.CustomData, Integer> quantitySoldColumn = new TableColumn<>("Quantity Sold");
        quantitySoldColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
        quantitySoldColumn.setStyle("-fx-background-color: rgb(255, 248, 240); -fx-border-color: rgb(255, 207, 153); -fx-text-fill: rgb(146, 20, 12);");
        quantitySoldColumn.setMinWidth(70);

        // Cashier Name column
        TableColumn<ManagerController.CustomData, String> cashierNameColumn = new TableColumn<>("Cashier Name");
        cashierNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCashierName()));
        cashierNameColumn.setStyle("-fx-background-color: rgb(255, 248, 240); -fx-border-color: rgb(255, 207, 153); -fx-text-fill: rgb(146, 20, 12);");
        cashierNameColumn.setMinWidth(110);

        // Price Sold column
        TableColumn<ManagerController.CustomData, Double> priceSoldColumn = new TableColumn<>("Price Sold");
        priceSoldColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getItem().getSellingPrice() * cellData.getValue().getQuantity()).asObject());
        priceSoldColumn.setStyle("-fx-background-color: rgb(255, 248, 240); -fx-border-color: rgb(255, 207, 153); -fx-text-fill: rgb(146, 20, 12);");
        priceSoldColumn.setMinWidth(70);

        // Bill ID column
        TableColumn<ManagerController.CustomData, Integer> billIdColumn = new TableColumn<>("Bill ID");
        billIdColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getBillID()).asObject()
        );billIdColumn.setStyle("-fx-background-color: rgb(255, 248, 240); -fx-border-color: rgb(255, 207, 153); -fx-text-fill: rgb(146, 20, 12);");
        billIdColumn.setMinWidth(70);

        // Bill Date column
        TableColumn<ManagerController.CustomData, String> billDateColumn = new TableColumn<>("Bill Date");
        billDateColumn.setCellValueFactory(cellData -> {
            LocalDate billDate = cellData.getValue().getDate();
            String formattedDate = (billDate != null) ? billDate.toString() : "Unknown"; // Default to "Unknown" if null
            return new SimpleStringProperty(formattedDate);
        });
        billDateColumn.setStyle("-fx-background-color: rgb(255, 248, 240); -fx-border-color: rgb(255, 207, 153); -fx-text-fill: rgb(146, 20, 12);");
        billDateColumn.setMinWidth(110);

        TableView<ManagerController.CustomData> statisticsTable = new TableView<>();
        statisticsTable.getColumns().addAll(itemNameColumn, quantitySoldColumn, cashierNameColumn, priceSoldColumn, billIdColumn, billDateColumn);
        statisticsTable.setMinHeight(20);
        statisticsTable.setMaxHeight(120);
        statisticsTable.setMinSize(540, 260);

        TextField statsearchBar = new TextField();
        statsearchBar.setPromptText("Search...");
        statsearchBar.setStyle("-fx-max-width:400; -fx-font-size: 14px; -fx-background-color: rgb(146, 20, 12); -fx-text-fill: rgb(255, 207, 153); -fx-border-color: rgb(255, 207, 153)");
        statsearchBar.setAlignment(Pos.CENTER);
        HBox searchBoxStatContainer = new HBox();
        searchBoxStatContainer.getChildren().addAll(statsearchBar);
        searchBoxStatContainer.setAlignment(Pos.TOP_RIGHT);


        ObservableList<ManagerController.CustomData> data = managerController.getStatisticsData();
        // Create a FilteredList wrapping the data
        FilteredList<ManagerController.CustomData> filteredData = new FilteredList<>(data, p -> true);

        // Set up search bar filtering
        statsearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(customData -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Show all data if no query is provided
                }
                String lowerCaseFilter = newValue.toLowerCase();

                // Check if any field matches the filter
                return customData.getItem().getItemName().toLowerCase().contains(lowerCaseFilter) ||
                        String.valueOf(customData.getQuantity()).contains(lowerCaseFilter) ||
                        customData.getCashierName().toLowerCase().contains(lowerCaseFilter) ||
                        String.valueOf(customData.getItem().getSellingPrice() * customData.getQuantity()).contains(lowerCaseFilter) ||
                        String.valueOf(customData.getBillID()).contains(lowerCaseFilter) ||
                        (customData.getDate() != null && customData.getDate().toString().contains(lowerCaseFilter));
            });
        });

        // Wrap the FilteredList in a SortedList for sorting
        SortedList<ManagerController.CustomData> sortedData = new SortedList<>(filteredData);

        // Bind the comparator of the SortedList to the TableView comparator
        sortedData.comparatorProperty().bind(statisticsTable.comparatorProperty());

        // Set the sorted and filtered data to the TableView
        statisticsTable.setItems(sortedData);
        statisticsTable.setMaxSize(570, 260);
        Label totalRevenueLabel = new Label("Today's Total Revenue: " + managerController.calculateTodayRevenue());
        totalRevenueLabel.setStyle("-fx-background-color: rgb(255, 207, 153); -fx-font-size: 13px; -fx-padding: 10px;");


        //visualStat scene
        BorderPane visualStatLayout = new BorderPane();
        Scene VisualStatScene = new Scene(visualStatLayout, 1519, 730);

        //button that will send you to the statVisual Scene
        Button visualRepresentationButton = new Button("Visual Representation");
        visualRepresentationButton.setStyle("-fx-background-color: rgb(146, 20, 12); -fx-text-fill: rgb(255, 207, 153); -fx-border-width: 2px; -fx-font-size: 14px; -fx-padding: 5px;");
        visualRepresentationButton.setOnAction(e -> managerStage.setScene(VisualStatScene));

//-------------------------visualRep Scene----------------------------------------------------------------------------------------------------------------------

        Button statBacButton = new Button("Back");
        statBacButton.setOnAction(e -> managerStage.setScene(mainLayout.getScene()));
        statBacButton.setStyle("-fx-min-width:100; -fx-font-size: 14px; -fx-background-color: rgb(17, 29, 74); -fx-text-fill: rgb(255, 207, 153); -fx-border-color: rgb(255, 207, 153)");
        HBox statBacButtonContainer = new HBox();
        statBacButtonContainer.getChildren().addAll(statBacButton);
        statBacButtonContainer.setAlignment(Pos.TOP_LEFT);


        Label RevenueText = new Label("Today's Revenue: " + managerController.calculateTodayRevenue());
        RevenueText.setStyle("-fx-background-color: rgb(156, 169, 217); -fx-border-radius: 10; -fx-text-fill: rgb(17, 29, 74);");
        RevenueText.setAlignment(Pos.TOP_LEFT);

        //------- weekly graph-------------------------------------------------

        // Create the bar chart
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Days of the Week");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Total Revenue ($)");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Weekly Revenue");

        // Create the chart series
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Weekly Revenue");


        Map<String, Double> weeklyRevenue = managerController.getWeeklyRevenue();
        // Ensure all days of the week are present
        series.setName("Weekly Revenue");

        // Add each day's revenue to the series
        for (DayOfWeek day : DayOfWeek.values()) {
            String dayName = day.name(); // E.g., MONDAY, TUESDAY, etc.
            Number revenue = weeklyRevenue.getOrDefault(dayName, 0.0); // Use 0.0 if no data
            XYChart.Data<String, Number> dataPoint = new XYChart.Data<>(dayName, revenue);
            series.getData().add(dataPoint);
        }

        barChart.getData().add(series);

        series.getData().forEach(data1 -> {
            data1.getNode().setStyle("-fx-bar-fill: rgb(146, 20, 12);");
        });


        VBox weeklyGraph = new VBox(10);
        weeklyGraph.getChildren().add(barChart);
//---------------------------------------------------------------------------------

        // Create the bar chart
        CategoryAxis xYAxis = new CategoryAxis();
        xYAxis.setLabel("Months of Year");

        NumberAxis yYAxis = new NumberAxis();
        yYAxis.setLabel("Total Revenue ($)");

        BarChart<String, Number> YbarChart = new BarChart<>(xYAxis, yYAxis);
        YbarChart.setTitle("Yearly Revenue");

        // Create the chart series
        XYChart.Series<String, Number> Yseries = new XYChart.Series<>();
        Yseries.setName("Yearly Revenue");

        Map<String, Double> monthlyRevenue = managerController.getMonthlyRevenue();
        // Add each month's revenue to the series
        for (Month month : Month.values()) {
            String monthName = month.name(); // E.g., JANUARY, FEBRUARY, etc.
            Number revenue = monthlyRevenue.getOrDefault(monthName, 0.0); // Use 0.0 if no data
            Yseries.getData().add(new XYChart.Data<>(monthName, revenue));
        }

        YbarChart.getData().add(Yseries);

        Yseries.getData().forEach(data2 -> {
            data2.getNode().setStyle("-fx-bar-fill: rgb(146, 20, 12);");
        });


        VBox monthlyGraph = new VBox(10);
        monthlyGraph.getChildren().add(YbarChart);

        Button weeklyButton = new Button("Weekly");
        weeklyButton.setStyle("-fx-background-color: transparent; -fx-text-fill: rgb(255, 207, 153);");
        Button monthlyButton = new Button("Yearly");
        monthlyButton.setStyle("-fx-background-color: transparent; -fx-text-fill: rgb(255, 207, 153);");

        weeklyButton.setOnAction(event -> {
            visualStatLayout.setCenter(weeklyGraph); // Change content to Weekly Graph
            weeklyButton.setStyle("-fx-background-color: rgb(146, 20, 12); -fx-text-fill: rgb(255, 207, 153);"); // Change color of Weekly button
            monthlyButton.setStyle("-fx-background-color: transparent; -fx-text-fill: rgb(255, 207, 153);"); // Reset Monthly button color
        });

        monthlyButton.setOnAction(event -> {
            visualStatLayout.setCenter(monthlyGraph); // Change content to Monthly Graph
            monthlyButton.setStyle("-fx-background-color: rgb(146, 20, 12); -fx-text-fill: rgb(255, 207, 153);"); // Change color of Monthly button
            weeklyButton.setStyle("-fx-background-color: transparent; -fx-text-fill: rgb(255, 207, 153);"); // Reset Weekly button color
        });

        // weekly Graph as default
        visualStatLayout.setCenter(weeklyGraph);
        weeklyButton.setStyle("-fx-background-color: rgb(146, 20, 12); -fx-text-fill: rgb(255, 207, 153);");



        VBox greyContainer = new VBox(10);
        greyContainer.setStyle("-fx-background-color: rgb(195, 197, 201); -fx-min-height: 50; -fx-min-width: 50; -fx-padding: 10px;");
        greyContainer.getChildren().addAll(RevenueText, weeklyButton, monthlyButton);


        VBox statVisualVBox = new VBox(20);
        statVisualVBox.getChildren().addAll(titleVisualStat, statBacButtonContainer, greyContainer);
        statVisualVBox.setStyle("-fx-background-color: rgb(255, 248, 240); -fx-padding: 30;");
        statVisualVBox.setMaxSize(1519, 730);
        statVisualVBox.setAlignment(Pos.CENTER);
        visualStatLayout.setTop(statVisualVBox);



//----------------nav bar--------------------------------------------------------------------------------------------------------

        //hbox for the navbar
        HBox navBar = new HBox();
        navBar.setStyle("-fx-background-color: rgb(17, 29, 74);");
        navBar.setSpacing(0);
        navBar.setAlignment(Pos.BOTTOM_CENTER);

        ImageView supplyIcon = controller.createIcon("/View/Images/supplyIcon.png");
        ImageView productdbIcon = controller.createIcon("/View/Images/productDataIcon.png");
        ImageView statisticsIcon = controller.createIcon("/View/Images/statisticsIcon.png");
        ImageView profileIcon = controller.createIcon("/View/Images/profileIcon.png");

        supplyIcon.setFitWidth(32);
        supplyIcon.setFitHeight(29);
        productdbIcon.setFitWidth(37);
        productdbIcon.setFitHeight(37);
        statisticsIcon.setFitWidth(34);
        statisticsIcon.setFitHeight(34);
        profileIcon.setFitWidth(35);
        profileIcon.setFitHeight(35);

        //navbar buttons
        Button supplyNav = new Button();
        supplyNav.setPrefSize(98.25, 53);
        supplyNav.setStyle("-fx-background-color: rgb(17,29,74)");
        supplyNav.setGraphic(supplyIcon);

        Button productdbNav = new Button();
        productdbNav.setPrefSize(98.25, 53);
        productdbNav.setStyle("-fx-background-color: rgb(17,29,74)");
        productdbNav.setGraphic(productdbIcon);

        Button statisticsNav = new Button();
        statisticsNav.setPrefSize(98.25, 53);
        statisticsNav.setStyle("-fx-background-color: rgb(17,29,74)");
        statisticsNav.setGraphic(statisticsIcon);

        Button profileNav = new Button();
        profileNav.setPrefSize(98.25, 53);
        profileNav.setStyle("-fx-background-color: rgb(17,29,74)");
        profileNav.setGraphic(profileIcon);

        navBar.getChildren().addAll(supplyNav, productdbNav, statisticsNav, profileNav);
        mainLayout.setBottom(navBar);

//-----------------------------------Vbox for all main pages---------------------------------------------
        //vbox for everything in supplier
        VBox supplierDBox = new VBox(40);
        supplierDBox.setStyle("-fx-background-color: rgb(255, 248, 240); -fx-padding: 30;");
        supplierDBox.setMaxSize(1519, 730);
        supplierDBox.getChildren().addAll(titleSList, searchBoxS, supplierTable);
        supplierDBox.setAlignment(Pos.CENTER);

        //vbox product db
        VBox  prodDBox= new VBox(40);
        prodDBox.setStyle("-fx-background-color: rgb(255, 248, 240); -fx-padding: 30;");
        prodDBox.setMaxSize(1519, 730);
        prodDBox.getChildren().addAll(titleprodDB,searchBoxP, insideProducts ,productsTable);
        prodDBox.setAlignment(Pos.CENTER);

        //vbox stats
        VBox statisticsBox = new VBox(30);
        statisticsBox.setMaxSize(1519, 730);
        statisticsBox.setStyle("-fx-background-color: rgb(255, 248, 240); -fx-padding: 30;");
        statisticsBox.getChildren().addAll(titleStats, visualRepresentationButton, statsearchBar, statisticsTable,  totalRevenueLabel);
        statisticsBox.setAlignment(Pos.CENTER);

        //vbox profile
        VBox profileBox = new VBox(20);
        profileBox.setMaxSize(1519, 730);
        profileBox.setStyle("-fx-background-color: rgb(255, 248, 240); -fx-padding: 30;");
        profileBox.getChildren().addAll(titleProfile, helloLabel, profileInfoBox, line, info, bottomButtons);
        profileBox.setAlignment(Pos.CENTER);



//-------------------------------------------
        //navbar functional
        supplyNav.setOnAction(e -> mainLayout.setCenter(supplierDBox));
        productdbNav.setOnAction(e -> mainLayout.setCenter(prodDBox));
        statisticsNav.setOnAction(e -> mainLayout.setCenter(statisticsBox));
        profileNav.setOnAction(e -> mainLayout.setCenter(profileBox));

        mainLayout.setCenter(supplierDBox);
        managerStage.setTitle("Manager");
        managerStage.setScene(mainScene);
        managerStage.show();


    }


}


