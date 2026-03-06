package Main;

import Controller.AdminController.*;
import Controller.CashierController.*;
import Controller.LoginController;
import FileHandler.BillFileHandler;
import FileHandler.UserFileHandler;
import View.AdminViews.SaleStatsView;
import View.LoginView;
import View.ManagerView;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.Administrator;
import models.Cashier;
import models.Manager;
import models.User;
import java.time.LocalDate;
import Controller.ManagerController;
import java.util.ArrayList;

public class Main extends Application {

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private Stage primaryStage;

    @Override
    public void start(Stage stage) {
        //Cashier cashier = new Cashier("user", "123", LocalDate.of(2000, 5, 2), User.Position.cashier, User.AccessLevel.partly, 67, "user1@example.com", "2453", Cashier.Sectors.GAMING);
        this.primaryStage = stage;

        showLoginView();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void showLoginView() {
        LoginView loginView = new LoginView();
        loginView.getLoginButton().setOnAction(e -> {
            String username = loginView.getUsername().getText();
            String password = loginView.getPassword().getText();

            Toggle selectedToggle = loginView.getOptionGroup().getSelectedToggle();
            if(selectedToggle == null){
                LoginController.showAlert("Please fill in all the fields");
                return;
            }

            String selectedPosition = ((RadioButton) selectedToggle).getText().toLowerCase();
            User.Position position;
            try {
                position = User.Position.valueOf(selectedPosition);
            } catch (IllegalArgumentException ex) {

                LoginController.showAlert("Invalid role selected. Please contact the administrator.");
                return;
            }

            if(username.isEmpty() || password.isEmpty()){
                LoginController.showAlert("Please fill in all the fields");
            }else{
                User authenticated = LoginController.authenticate(username, password, position);
                if(authenticated != null){
                    switch(position){
                        case cashier:
                            navigateToDashboard((Cashier) authenticated);
                            break;
                        case manager:
                            navigateToDashboard((Manager) authenticated);
                            break;
                        case administrator:
                            navigateToDashboard((Administrator) authenticated);
                            break;
                        default:
                            throw new IllegalArgumentException("Unknown position: " + position);
                    }
                }
            }
        });

        Scene scene = new Scene(loginView.getMainLayout(), 1519, 730);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    private void navigateToDashboard(User user) {
        switch (user.getPosition()) {
            case cashier:
                showCheckoutView((Cashier) user);
                break;
            case manager:
                System.out.println("Manager");
                ManagerView managerView = new ManagerView((Manager)user);
                managerView.start(primaryStage);
                managerView.getLogoutBtn().setOnAction(e -> {
                    showLoginView();
                });
                break;
            case administrator:
                showEmployeeListView((Administrator) user);
                break;
            default:
                LoginController.showAlert("Invalid role. Please contact the administrator.");
        }
    }
//----------------------------CASHIER--------------------------------------------
    private void showCheckoutView(Cashier cashier) {
        BillFileHandler.clearAllBills(cashier.getUsername());
        CheckOutController checkOutController = new CheckOutController(cashier);
        checkOutController.getView().addNavBar(navBarCashier(cashier));
        Scene scene = new Scene(checkOutController.getView(), 1519, 695);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Cashier System");
        primaryStage.show();
    }

    private void showProductDbView(Cashier cashier) {
        ProductDatabaseController databaseController = new ProductDatabaseController();
        databaseController.getView().addNavBar(navBarCashier(cashier));
        Scene scene = new Scene(databaseController.getView(), 1519, 695);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Product Database");
        primaryStage.show();
    }

    private void showProfileView(Cashier cashier) {
        ProfileController profileController = new ProfileController(cashier);
        profileController.refreshProfileView();
        profileController.getView().addNavBar(navBarCashier(cashier));
        Scene scene = new Scene(profileController.getView(), 1519, 695);

        profileController.getView().getEditProfile().setOnAction(e -> showEditProfileView(cashier));
        profileController.getView().getLogoutBtn().setOnAction(e -> showLoginView());
        profileController.getView().addNavBar(navBarCashier(cashier));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Your Profile");
        primaryStage.show();
    }


    private void showEditProfileView(Cashier cashier) {
        if (cashier == null) {
            throw new IllegalArgumentException("Cashier cannot be null");
        }
        EditProfileController editProfileController = new EditProfileController(cashier);
        Scene scene = new Scene(editProfileController.getView(), 1519, 695);
        editProfileController.getView().getBackBtn().setOnAction(e -> showProfileView(cashier));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Edit Profile");
        primaryStage.show();
    }

    public void showStatisticsView(Cashier cashier) {
        StatisticsController statisticsController = new StatisticsController(cashier);
        statisticsController.getView().addNavBar(navBarCashier(cashier));
        Scene scene = new Scene(statisticsController.getView(), 1519, 695);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Cashier Statistics");
        primaryStage.show();
    }

    public HBox navBarCashier(Cashier cashier)
    {
        HBox navBar = new HBox();
        navBar.setStyle("-fx-background-color: rgb(17, 29, 74);");
        navBar.setSpacing(0);
        navBar.setAlignment(Pos.BOTTOM_CENTER);

//        ImageView checkoutIcon = createIcon("ElectronicStore_Nikola_Rigo/src/View/Images/checkoutIcon.png");
//        ImageView productdbIcon = createIcon("ElectronicStore_Nikola_Rigo/src/View/Images/productdbIcon.png");
//        ImageView statisticsIcon = createIcon("ElectronicStore_Nikola_Rigo/src/View/Images/statisticsIcon.png");
//        ImageView profileIcon = createIcon("ElectronicStore_Nikola_Rigo/src/View/Images/profileBoxIcon.png");
//
//        checkoutIcon.setFitWidth(32);
//        checkoutIcon.setFitHeight(29);
//        productdbIcon.setFitWidth(37);
//        productdbIcon.setFitHeight(37);
//        statisticsIcon.setFitWidth(34);
//        statisticsIcon.setFitHeight(34);
//        profileIcon.setFitWidth(35);
//        profileIcon.setFitHeight(35);

        //navbar buttons
        Button checkoutNav = new Button("CheckOut");
        checkoutNav.setPrefSize(98.25, 53);
        checkoutNav.setStyle("-fx-background-color: rgb(17,29,74); -fx-text-fill: white");
        //     checkoutNav.setGraphic(checkoutIcon);
        checkoutNav.setOnAction(e -> showCheckoutView(cashier));

        Button productdbNav = new Button("Products");
        productdbNav.setPrefSize(98.25, 53);
        productdbNav.setStyle("-fx-background-color: rgb(17,29,74); -fx-text-fill: white");
        //   productdbNav.setGraphic(productdbIcon);
        productdbNav.setOnAction(e -> showProductDbView(cashier));

        Button statisticsNav = new Button("Statistics");
        statisticsNav.setPrefSize(98.25, 53);
        statisticsNav.setStyle("-fx-background-color: rgb(17,29,74); -fx-text-fill: white");
        //     statisticsNav.setGraphic(statisticsIcon);
        statisticsNav.setOnAction(e -> showStatisticsView(cashier));

        Button profileNav = new Button("Profile");
        profileNav.setPrefSize(98.25, 53);
        profileNav.setStyle("-fx-background-color: rgb(17,29,74); -fx-text-fill: white");
        //     profileNav.setGraphic(profileIcon);
        profileNav.setOnAction(e -> showProfileView(cashier));
//l
        navBar.getChildren().addAll(checkoutNav, productdbNav, statisticsNav, profileNav);
        return navBar;
    }

    public ImageView createIcon(String path) {
        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream(path)));
        icon.setFitWidth(32);
        icon.setFitHeight(29);
        return icon;
    }
    //------------------------------------ADMIN---------------------------------------------------------------------
    private void showEmployeeListView(Administrator admin) {
        EmployeeListController employeeListController = new EmployeeListController(admin);
        employeeListController.getView().getModifyEmployee().setOnAction(e -> showManageEmployeesView(admin));
        employeeListController.getView().addNavBar(navBarAdmin(admin));
        Scene scene = new Scene(employeeListController.getView(), 1519, 695);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Employee List");
        primaryStage.show();

    }

    private void showManageEmployeesView(Administrator admin) {
        EmployeeListController employeeListController = new EmployeeListController(admin);

        User selectedUser = employeeListController.getView().getEmployeeTable().getSelectionModel().getSelectedItem();

        // Pass the selected user to the ManageEmployeesController
        ManageEmployeesController manageEmployeesController = new ManageEmployeesController((User)  admin);
        Scene scene = new Scene(manageEmployeesController.getView(), 1519, 695);

        // Set the back button action to return to the employee list view
        manageEmployeesController.getView().getBackBtn().setOnAction(e -> showEmployeeListView(admin));

        // Set the scene and display it
        primaryStage.setScene(scene);
        primaryStage.setTitle("Manage Employee");
        primaryStage.show();
    }

    public void showProductDatabaseView(Administrator admin) {
        ProductDBController productDBController = new ProductDBController();
        productDBController.getView().getViewSuppliersBtn().setOnAction(e -> showSupplierView());
        productDBController.getView().addNavBar(navBarAdmin(admin));

        Scene scene = new Scene(productDBController.getView(), 1519, 695);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Product Database");
        primaryStage.show();
    }

    private void showSupplierView() {
        SupplierController supplierController = new SupplierController();

        Scene scene = new Scene(supplierController.getSupplierView(), 1519, 695);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Supplier List");
        primaryStage.show();
    }

    public void showStatisticsView(Administrator admin) {
        SaleStatsView view = new SaleStatsView();
        view.addNavBar(navBarAdmin(admin));

        Scene scene=new Scene(view,1519,695);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Statistics");
        primaryStage.show();
    }

//    public void showAdminProfileView(Administrator admin) {
//        AdminProfileController adminProfileController = new AdminProfileController(admin);
//        adminProfileController.getView().addNavBar(navBarAdmin(admin));
//
//        Scene scene=new Scene(adminProfileController.getView(), 1519, 695);
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("Admin Profile");
//        primaryStage.show();
//    }

    private void showProfileViewAdmin(Administrator admin) {
        AdminProfileController profileController = new AdminProfileController(admin);
        profileController.refreshProfileView();
        profileController.getView().addNavBar(navBarAdmin(admin));
        Scene scene = new Scene(profileController.getView(), 1519, 695);

        profileController.getView().getEditAdminProfile().setOnAction(e -> showEditProfileViewAdmin(admin));
        profileController.getView().getLogoutBtn().setOnAction(e -> showLoginView());
        profileController.getView().addNavBar(navBarAdmin(admin));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Your Profile");
        primaryStage.show();
    }

    private void showEditProfileViewAdmin(Administrator admin) {
        if (admin == null) {
            throw new IllegalArgumentException("Admin cannot be null");
        }
        AdminEditProfileController editProfileController = new AdminEditProfileController(admin);
        Scene scene = new Scene(editProfileController.getView(), 1519, 695);
        editProfileController.getView().getBackBtn().setOnAction(e -> showProfileViewAdmin(admin));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Edit Profile");
        primaryStage.show();
    }
    public HBox navBarAdmin(Administrator admin)
    {
        HBox navBar = new HBox();
        navBar.setStyle("-fx-background-color: rgb(17, 29, 74);");
        navBar.setSpacing(0);
        navBar.setAlignment(Pos.BOTTOM_CENTER);
//
//        ImageView checkoutIcon = createIcon("ElectronicStore_Nikola_Rigo/src/View/Images/checkoutIcon.png");
//        ImageView productdbIcon = createIcon("ElectronicStore_Nikola_Rigo/src/View/Images/productdbIcon.png");
//        ImageView statisticsIcon = createIcon("ElectronicStore_Nikola_Rigo/src/View/Images/statisticsIcon.png");
//        ImageView profileIcon = createIcon("ElectronicStore_Nikola_Rigo/src/View/Images/profileBoxIcon.png");
//
//        checkoutIcon.setFitWidth(32);
//        checkoutIcon.setFitHeight(29);
//        productdbIcon.setFitWidth(37);
//        productdbIcon.setFitHeight(37);
//        statisticsIcon.setFitWidth(34);
//        statisticsIcon.setFitHeight(34);
//        profileIcon.setFitWidth(35);
//        profileIcon.setFitHeight(35);

        //navbar buttons
        Button checkoutNav = new Button("CheckOut");
        checkoutNav.setPrefSize(98.25, 53);
        checkoutNav.setStyle("-fx-background-color: rgb(17,29,74); -fx-text-fill: white");
        //     checkoutNav.setGraphic(checkoutIcon);
        checkoutNav.setOnAction(e -> showEmployeeListView(admin));

        Button productdbNav = new Button("Products");
        productdbNav.setPrefSize(98.25, 53);
        productdbNav.setStyle("-fx-background-color: rgb(17,29,74); -fx-text-fill: white");
        //   productdbNav.setGraphic(productdbIcon);
        productdbNav.setOnAction(e -> showProductDatabaseView(admin));

        Button statisticsNav = new Button("Statistics");
        statisticsNav.setPrefSize(98.25, 53);
        statisticsNav.setStyle("-fx-background-color: rgb(17,29,74); -fx-text-fill: white");
        //     statisticsNav.setGraphic(statisticsIcon);
        statisticsNav.setOnAction(e -> showStatisticsView(admin));

        Button profileNav = new Button("Profile");
        profileNav.setPrefSize(98.25, 53);
        profileNav.setStyle("-fx-background-color: rgb(17,29,74); -fx-text-fill: white");
        //     profileNav.setGraphic(profileIcon);
        profileNav.setOnAction(e -> showProfileViewAdmin(admin));

        navBar.getChildren().addAll(checkoutNav, productdbNav, statisticsNav, profileNav);
        return navBar;
    }
}