//package Controller.AdminController;
//
//import View.AdminViews.AdminProfileView;
//import View.AdminViews.EditAdminProfileView;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//import models.Administrator;
//
//public class AdminProfileController {
//
//    private AdminProfileView view=new AdminProfileView();
//    private final AdminProfileView profileView;
//    private final Administrator admin;
//   // private final Stage stage;
//
//    public AdminProfileView getProfileView() {
//        return profileView;
//    }
//
//    public AdminProfileController(Administrator admin) {
//        if (admin == null) {
//            throw new IllegalArgumentException("Admin cannot be null");
//        }
//        this.admin = admin;
//       // this.stage = stage;
//
//        // Initialize the profile view
//        this.profileView = new AdminProfileView();
//
//        // Set up the data
//        setUp();
//
//        // Set up button handlers
//        setUpEventHandlers();
//    }
//
//    private void setUp() {
//        profileView.getHelloLabel().setText("Hello " + admin.getUsername());
//        profileView.getUsernameLabel().setText("Username: " + admin.getUsername());
//        profileView.getPositionLabel().setText("Position: " + admin.getPosition());
//        profileView.getBirthDateLabel().setText("Date of birth: " + admin.getBirthday().toString());
//        profileView.getEmailLabel().setText("Email: " + admin.getEmail());
//        profileView.getPhoneNrLabel().setText("Phone nr: " + admin.getPhonenr());
//
//        // Load profile image
//        byte[] profileImageBytes = admin.getProfileImage();
//        if (profileImageBytes != null) {
//            profileView.loadProfileImage(profileImageBytes);
//        }
//    }
//
//    private void setUpEventHandlers() {
//        // Handle "Edit Profile" button click
//        profileView.getEditAdminProfile().setOnAction(e -> openEditProfileView());
//    }
//
//    private void openEditProfileView() {
//        // Create EditAdminProfileController and its view
//        AdminEditProfileController editProfileController = new AdminEditProfileController(admin);
//        EditAdminProfileView editProfileView = editProfileController.getView();
//
//        // Switch to the EditProfileView
//        Scene scene = new Scene(editProfileView, 800, 600);
//        stage.setScene(scene);
//        stage.setTitle("Edit Profile");
//  }
//
//    public AdminProfileView getView() {
//        return profileView;
//    }
//}



package Controller.AdminController;

import View.AdminViews.AdminProfileView;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import models.Administrator;
import models.Administrator;
import models.User;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;

public class AdminProfileController{
    private Administrator admin;
    private final AdminProfileView view = new AdminProfileView();
  //  Circle clip = new Circle(50, 50, 50);

    public AdminProfileController(Administrator admin) {
     this.admin = admin;
        if (admin == null) {
            throw new IllegalArgumentException("admin cannot be null");
        }
        setUp();
    }

    private void setUp() {
        System.out.println(admin.getUsername());
        view.getHelloLabel().setText("Hello " + admin.getUsername());
        view.getUsernameLabel().setText("Username: " + admin.getUsername());
        view.getPositionLabel().setText("Position: " + admin.getAccessLevel().toString());
        view.getBirthDateLabel().setText("Date of birth: " + admin.getBirthday().toString());
        view.getEmailLabel().setText("Email: " + admin.getEmail());
        view.getPhoneNrLabel().setText("Phone nr: " + admin.getPhonenr());

//        byte[] profileImageBytes = admin.getProfileImage();
//        if (profileImageBytes != null) {
//            InputStream inputStream = new ByteArrayInputStream(profileImageBytes);
//            Image profileImage = new Image(inputStream);
//            view.getProfileImage().setImage(profileImage);
//        }
    }

    public AdminProfileView getView() {
        return view;
    }

    public void refreshProfileView() {
        view.getHelloLabel().setText("Hello " + admin.getUsername());
        view.getUsernameLabel().setText("Username: " + admin.getUsername());
        view.getPositionLabel().setText("Position: " + admin.getAccessLevel().toString());
        view.getBirthDateLabel().setText("Date of birth: " + admin.getBirthday().toString());
        view.getEmailLabel().setText("Email: " + admin.getEmail());
        view.getPhoneNrLabel().setText("Phone nr: " + admin.getPhonenr());
    }



}
