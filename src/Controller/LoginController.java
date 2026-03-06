package Controller;

import javafx.scene.control.Alert;
import models.Administrator;
import models.Cashier;
import models.Manager;
import models.User;
import View.LoginView;
import FileHandler.UserFileHandler;

import java.util.ArrayList;

public class LoginController {


    public static User authenticate(String username, String password, User.Position position) {
        if(position == User.Position.cashier){
            Cashier cashier = UserFileHandler.findCashierByUsername(username);
            if(cashier != null && cashier.getPassword().equals(password)){
                return cashier;
            }
        }else if(position == User.Position.manager){
            Manager manager = UserFileHandler.findManagerByUsername(username);
            if(manager != null && manager.getPassword().equals(password)){
                return manager;
            }
        }else if(position == User.Position.administrator){
            Administrator administrator = UserFileHandler.findAdministratorByUsername(username);
            if(administrator != null && administrator.getPassword().equals(password)){
                return administrator;
            }
        }
        return null;
    }
//l
    public static void showAlert(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Login");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
