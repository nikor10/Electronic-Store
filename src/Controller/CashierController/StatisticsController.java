package Controller.CashierController;

import FileHandler.BillFileHandler;
import View.CashierView.StatisticsView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Bill;
import models.Cashier;
import models.Item;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StatisticsController {
    Cashier cashier;
    private final StatisticsView view = new StatisticsView();
    private PieChart pieChart;

    public StatisticsController(Cashier cashier) {
        this.cashier = cashier;
        setUpLayout();
    }

    private void setUpLayout() {
        view.getProfitDate().setOnAction(e -> {
            String selectedOption = view.getProfitDate().getValue();
            ArrayList<Bill> selectedBills;


            switch (selectedOption) {
                case "Today":
                    selectedBills = cashier.todayBills();
                    extractPieChartData(selectedBills);
                    pieChart.setTitle("Top Items of the Day");
                    break;
                case "All Time":
                    selectedBills = cashier.getAllCashierBills();
                    extractPieChartData(selectedBills);
                    pieChart.setTitle("Top Items of All Time");
                    break;
                default:
                    selectedBills = cashier.todayBills();
                    extractPieChartData(selectedBills);
                    pieChart.setTitle("Top Items of the Day");
                    break;
            }

            if (selectedBills != null) {
                view.getRightLayout().getChildren().clear();
                view.getRightLayout().getChildren().add(pieChart);
                updateDisplay(selectedBills);
            }
        });
    }

    public void updateDisplay(ArrayList<Bill> billsToShow) {
        if(billsToShow.size()>0)
        {
            view.getProfit().setText("Total profit:" + getTotalProfit(billsToShow));
            view.getBillCard().getChildren().clear();

            for (Bill bill : billsToShow) {
                view.getBillCard().getChildren().add(createBillBox(bill));
            }
        }else
        {
            Label noBills = new Label("No bills found.");
            view.getBillCard().getChildren().add(noBills);
        }
    }

    public VBox createBillBox(Bill bill) {
        VBox singleBill = new VBox(15);
        singleBill.setStyle("-fx-background-color: rgb(217,217,217)");
        singleBill.setAlignment(Pos.CENTER_LEFT);

        Label billIdLabel = new Label("Bill " + bill.getBillID());
        billIdLabel.setStyle("-fx-text-fill: black; -fx-font-size: 18;");

        HBox productDetailRow = new HBox(30);
        Label productIdLabel = new Label("Product ID");
        productIdLabel.setStyle("-fx-text-fill: black; -fx-font-size: 18;");
        Label quantityLabel = new Label("Quantity");
        quantityLabel.setStyle("-fx-text-fill: black; -fx-font-size: 18;");
        Label priceLabel = new Label("Price");
        priceLabel.setStyle("-fx-text-fill: black; -fx-font-size: 18;");
        productDetailRow.getChildren().addAll(productIdLabel, quantityLabel, priceLabel);

        VBox itemsBox = getItemRows(bill);

        Label totalAmountLabel = new Label("Total: " + bill.getTotalAmount());
        totalAmountLabel.setStyle("-fx-text-fill: black; -fx-font-size: 18;");

        Label dateLabel = new Label("Date: " + bill.getDate());
        dateLabel.setStyle("-fx-text-fill: black; -fx-font-size: 18;");

        singleBill.getChildren().addAll(billIdLabel, productDetailRow, itemsBox, totalAmountLabel, dateLabel);
        singleBill.setPadding(new Insets(15));
        return singleBill;
    }

    public VBox getItemRows(Bill bill) {
        VBox itemsBox = new VBox(10); // Container for all items
        itemsBox.setStyle("-fx-background-color: rgb(217,217,217)");

        for (Map.Entry<Item, Integer> entry : bill.getItemsSold().entrySet()) {
            Item item = entry.getKey();
            int quantity = entry.getValue();

            HBox itemRow = new HBox(110);
            itemRow.setStyle("-fx-background-color: rgb(235,235,235)");
            itemRow.setAlignment(Pos.CENTER_LEFT);

            Label itemIdLabel = new Label(String.valueOf(item.getItemid()));
            itemIdLabel.setStyle("-fx-text-fill: black; -fx-font-size: 16;");
            Label itemQuantityLabel = new Label(String.valueOf(quantity));
            itemQuantityLabel.setStyle("-fx-text-fill: black; -fx-font-size: 16;");
            Label itemPriceLabel = new Label(String.valueOf(item.getSellingPrice()));
            itemPriceLabel.setStyle("-fx-text-fill: black; -fx-font-size: 16;");

            itemRow.getChildren().addAll(itemIdLabel, itemQuantityLabel, itemPriceLabel);

            itemsBox.getChildren().add(itemRow);
        }
        return itemsBox;
    }

    public void extractPieChartData(ArrayList<Bill> bills) {
        Map<Item, Integer> itemQuantities = new HashMap<>();
        for(Bill bill : bills) {
            Map<Item, Integer> itemsSold = bill.getItemsSold();
            for (Map.Entry<Item, Integer> entry : itemsSold.entrySet()) {
                Item item = entry.getKey();
                int quantity = entry.getValue();
                itemQuantities.put(item, itemQuantities.getOrDefault(item, 0) + quantity);
            }
        }
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<Item, Integer> entry : itemQuantities.entrySet()) {
            Item item = entry.getKey();
            int quantity = entry.getValue();

            PieChart.Data data = new PieChart.Data(item.getItemName(), quantity);
            pieChartData.add(data);
        }
        pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Most Sold Items");
        pieChart.setPrefSize(500, 500);
        pieChart.setStyle("-fx-font-weight: bold; -fx-font-size: 18;");
        pieChart.setPadding(new Insets(10, 20, 0, 10));

    }

    public void loadSampleData() {
        Bill bill1 = new Bill(1, LocalDate.of(2025, 1, 20));
        Bill bill2 = new Bill(2, LocalDate.of(2025, 1, 19));
        Bill bill3 = new Bill(3, LocalDate.of(2025, 7, 20));
        Bill bill4 = new Bill(4, LocalDate.of(2025, 1, 20));
        Bill bill5 = new Bill(5, LocalDate.of(2025, 6, 20));
//        Bill bill6 = new Bill(6, LocalDate.of(2025, 6, 20));
//        Bill bill7 = new Bill(7, LocalDate.of(2025, 1, 20));
//        Bill bill8 = new Bill(8, LocalDate.of(2025, 8, 20));
//        Bill bill9 = new Bill(9, LocalDate.of(2025, 1, 20));
//        Bill bill10 = new Bill(10, LocalDate.of(2025, 12, 20));

        Item [] items = new Item[]{
                new Item("Battery", 1, "Electronics", "Alibaba", 10.0, 20.0, 100),
                new Item("Lamp", 2, "Appliances", "Temu", 5.5, 12.0, 200),
                new Item("iPhone", 3, "Electronics", "Apple", 50.0, 800.0, 50),
                new Item("iPad", 4, "Electronics", "Apple", 70.0, 700.0, 100),
                new Item("XBox", 5, "Electronics", "Microsoft", 30.0, 600.0, 50),
                new Item("SamsungTV", 6, "Electronics", "Samsung", 60.0, 800.0, 30),
                new Item("Laptop", 7, "Electronics", "DELL", 50.0, 1600.0, 50),
                new Item("TV", 8, "Electronics", "LG", 55.0, 900.0, 20),
        };

        bill1.addItem(items[3], 2);
        bill2.addItem(items[1], 3);
        bill3.addItem(items[2], 5);
        bill4.addItem(items[4], 6);
        bill5.addItem(items[5], 1);
        bill1.addItem(items[1], 1);
        bill2.addItem(items[6], 2);
        bill3.addItem(items[3], 5);
        bill4.addItem(items[2], 8);

        BillFileHandler.saveBill(bill1, "bora");
        BillFileHandler.saveBill(bill3, "dea");
        BillFileHandler.saveBill(bill2, "niko");
        BillFileHandler.saveBill(bill4, "bora");
        BillFileHandler.saveBill(bill5, "dea");

    }

    public StatisticsView getView() {
        return view;
    }

    public String getTotalProfit(ArrayList<Bill> billsToShow) {
        double totalProfit = 0;
        for(Bill bill : billsToShow) {
            totalProfit += bill.getTotalAmount();
        }
        return String.valueOf(totalProfit);
    }

}