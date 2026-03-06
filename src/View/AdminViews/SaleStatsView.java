package View.AdminViews;
import Controller.ManagerController;
import FileHandler.UserFileHandler;
import View.ManagerView;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public class SaleStatsView extends BorderPane {

    public SaleStatsView() {
        setCenter(salesView());
    }
    final TableView<CustomData> statisticsTable = new TableView<>();
    static ObservableList<CustomData> data = FXCollections.observableArrayList(); // Initialize inside the method
    ManagerController controller = new ManagerController();

    public VBox salesView()
    {
        Label titleStats = new Label("Statistics");
        titleStats.setStyle("-fx-text-fill: rgb(255, 248, 240); -fx-font-size: 25; -fx-font-weight: bold; -fx-padding: 15 ;-fx-font-family: 'Arial'; -fx-background-color: rgb(17, 29, 74)" );
        titleStats.setAlignment(Pos.TOP_CENTER);
        titleStats.setPrefSize(1200, 30);

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


        ObservableList<ManagerController.CustomData> data = controller.getStatisticsData();
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
        Label totalRevenueLabel = new Label("Today's Total Revenue: " + controller.calculateTodayRevenue());
        totalRevenueLabel.setStyle("-fx-background-color: rgb(255, 207, 153); -fx-font-size: 13px; -fx-padding: 10px;");


        VBox statisticsBox = new VBox(30);
        statisticsBox.setMaxSize(1519, 730);
        statisticsBox.setStyle("-fx-background-color: rgb(255, 248, 240); -fx-padding: 30;");
        statisticsBox.getChildren().addAll(titleStats,statsearchBar, statisticsTable,  totalRevenueLabel);
        statisticsBox.setAlignment(Pos.CENTER);

        return statisticsBox;

    }

    public double calculateTodayRevenue() {
        ArrayList <Cashier> cashiers= UserFileHandler.loadCashiers();

        double totalRevenue = 0;
        for (Cashier cashier : cashiers) {
            for (Bill bill : cashier.getAllCashierBills()) {
                totalRevenue += bill.getTotalAmount();
            }
        }
        return totalRevenue;
    }

    public static class CustomData {
        private Item item;
        private Integer quantity;
        private String cashierName;
        private LocalDate Billdate;
        private Integer billID; // Add bill ID
        private LocalDate billDate; // Add bill date

        public CustomData(Item item, Integer quantity, String cashierName, Integer billID, LocalDate billDate) {
            this.item = item;
            this.quantity = quantity;
            this.cashierName = cashierName;
            this.Billdate = LocalDate.now();
            this.billID = item.getItemid();
            this.billDate = LocalDate.now();
        }

        public Item getItem() {
            return item;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public String getCashierName() {
            return cashierName;
        }

        public Integer getBillID() { // Add getter for Bill ID
            return billID;
        }

        public LocalDate getDate() { // Add getter for Bill Date
            return billDate;
        }
    }

    public void addNavBar(HBox navBar)
    {
        setBottom(navBar);
    }

}
