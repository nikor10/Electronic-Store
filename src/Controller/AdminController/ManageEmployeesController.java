package Controller.AdminController;

import FileHandler.UserFileHandler;
import View.AdminViews.EmployeeListView;
import View.AdminViews.ManageEmployeesView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import models.Administrator;
import models.Cashier;
import models.Manager;
import models.User;
import models.User.Position;

import java.time.LocalDate;

import static Controller.LoginController.showAlert;

public class ManageEmployeesController {
    EmployeeListView employeeView;

    public ManageEmployeesView getView() {
        return view;
    }

    ManageEmployeesView view = new ManageEmployeesView();

    public ManageEmployeesController(User selectedUser) {
        setUpEventHandlers();
    }

    private void setUpEventHandlers() {
        view.getRegisterUser().setOnAction(e -> {

            // Get user input
            String username = view.getUsernameField().getText();
            String password = view.getPasswordField().getText();
            String email = view.getEmailField().getText();
            String phone = view.getPhoneField().getText();
            LocalDate birthday = view.getDateOfBirthPicker().getValue();
            String position = view.getPositionField().getText();
            String accessLevel = view.getAccessLevelField().getText();
            String sector = view.getSectorField().getText();
            double salary = Double.parseDouble(view.getSalaryField().getText());
            if (username.isEmpty() || password.isEmpty() || email.isEmpty() || phone.isEmpty() || position.isEmpty() || accessLevel.isEmpty()|| sector.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Validate the required fields properly.");
                return;
            }
            // Map position and access level enums
            Position userPosition;
            User.AccessLevel userAccessLevel;
            Cashier.Sectors userSector;
            try {
                userPosition = Position.valueOf(position.toUpperCase());
                userAccessLevel = User.AccessLevel.valueOf(accessLevel.toUpperCase());
                userSector = Cashier.Sectors.valueOf(sector.toUpperCase());

            } catch (IllegalArgumentException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Enter correct format.");
                return;
            }
            User newUser = new User(username, password, birthday, userPosition, userAccessLevel, salary, email, phone,userSector);
            employeeView.getEmployees().add(newUser);
            switch(userPosition)
            {
                case Position.cashier:
                    UserFileHandler.saveCashier((Cashier) newUser);
                    break;
                case Position.manager:
                    UserFileHandler.saveManager((Manager) newUser);
                    break;
                case Position.administrator:
                    UserFileHandler.saveAdministrator((Administrator) newUser);
                    break;
                default:
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid input.");
                    return;
            }

            clearFields();

            // Show success message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Registration successful.");
        });
    }

    private void clearFields() {
        view.getUsernameField().clear();
        view.getPasswordField().clear();
        view.getEmailField().clear();
        view.getPhoneField().clear();
        view.getDateOfBirthPicker().setValue(null);
        view.getPositionField().clear();
        view.getAccessLevelField().clear();
        view.getSalaryField().clear();
        view.getSectorField().clear();
    }
}
