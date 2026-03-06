package Controller.CashierController;

import View.CashierView.ProfileView;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import models.Cashier;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ProfileController{
    private final ProfileView view = new ProfileView();
    Cashier cashier;
    Circle clip = new Circle(50, 50, 50);

    public ProfileController(Cashier cashier) {

        if (cashier == null) {
            throw new IllegalArgumentException("Cashier cannot be null");
        }
        this.cashier = cashier;
        setUp();
    }

    private void setUp() {
        view.getHelloLabel().setText("Hello " + cashier.getUsername());
        view.getUsernameLabel().setText("Username: " + cashier.getUsername());
        view.getPositionLabel().setText("Position: " + cashier.getAccessLevel().toString());
        view.getBirthDateLabel().setText("Date of birth: " + cashier.getBirthday().toString());
        view.getEmailLabel().setText("Email: " + cashier.getEmail());
        view.getPhoneNrLabel().setText("Phone nr: " + cashier.getPhonenr());

        byte[] profileImageBytes = cashier.getProfileImage();
        if (profileImageBytes != null) {
            InputStream inputStream = new ByteArrayInputStream(profileImageBytes);
            Image profileImage = new Image(inputStream);
            view.getProfileImage().setImage(profileImage);
            
        }
    }

    public ProfileView getView() {
        return view;
    }

    public void refreshProfileView() {
        view.getHelloLabel().setText("Hello " + cashier.getUsername());
        view.getUsernameLabel().setText("Username: " + cashier.getUsername());
        view.getPositionLabel().setText("Position: " + cashier.getAccessLevel().toString());
        view.getBirthDateLabel().setText("Date of birth: " + cashier.getBirthday().toString());
        view.getEmailLabel().setText("Email: " + cashier.getEmail());
        view.getPhoneNrLabel().setText("Phone nr: " + cashier.getPhonenr());
    }



}
