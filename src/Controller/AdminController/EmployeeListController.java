package Controller.AdminController;

import View.AdminViews.ManageEmployeesView;
import View.AdminViews.EmployeeListView;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Alert;
import models.Administrator;
import models.User;

public class EmployeeListController {
    Administrator admin;
    private final EmployeeListView view = new EmployeeListView(admin);
    private final FilteredList<User> filteredEmployees;

    public EmployeeListController(Administrator admin) {
        this.admin = admin;
        this.filteredEmployees = new FilteredList<>(view.getEmployees(), p -> true);
        view.getEmployeeTable().setItems(filteredEmployees); // Set the filtered list to the TableView

        setupEventHandlers();
    }

    private void setupEventHandlers() {
        // Manage Employee Button
        view.getSearchButton().setOnAction(e-> applySearchFilter());
        view.getDeleteEmployee().setOnAction(e -> deleteSelectedEmployee());

    }

    private void deleteSelectedEmployee() {
        User selectedUser = view.getEmployeeTable().getSelectionModel().getSelectedItem();

        if (selectedUser != null) {
            // Remove the selected employee from the ObservableList
            view.getEmployees().remove(selectedUser);

            // Show a confirmation message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Employee deleted successfully.");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select an employee to delete.");
            alert.show();
        }
    }

    private void applySearchFilter() {
        String searchQuery = view.getSearchField().getText().toLowerCase();

        // Apply the filter
        filteredEmployees.setPredicate(user -> {
            if (searchQuery == null || searchQuery.isEmpty()) {
                return true; // Show all employees if search field is empty
            }
            return user.getUsername().toLowerCase().contains(searchQuery) ||
                    user.getEmail().toLowerCase().contains(searchQuery) ||
                    user.getPhonenr().toLowerCase().contains(searchQuery) ||
                    user.getPosition().toString().toLowerCase().contains(searchQuery);
        });

        // Show alert if no matches
        if (filteredEmployees.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search Result");
            alert.setHeaderText(null);
            alert.setContentText("No employees found matching the search criteria.");
            alert.show();
        }
    }

    private void openManageEmployee() {
        // Replace the center of AdminView with EditProfileDetailsView
        ManageEmployeesController editEmployee = new ManageEmployeesController(admin);
        view.setCenter(editEmployee.getView());
    }




    public EmployeeListView getView() {
        return view;
    }

}
