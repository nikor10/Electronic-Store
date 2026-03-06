package Controller.CashierController;
import Controller.AdminController.ProductDBController;
import View.CashierView.CheckOutView;
import View.CashierView.ProductDatabaseView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import models.Bill;
import models.Cashier;
import models.Item;
import java.io.*;
import FileHandler.BillFileHandler;

import java.util.ArrayList;
import java.util.Optional;

import static FileHandler.BillFileHandler.loadBills;
import static FileHandler.BillFileHandler.saveBill;

public class CheckOutController {
    private final ObservableList<Bill.BillRow> billRows = FXCollections.observableArrayList();
    private CheckOutView view = new CheckOutView();
    private Bill toBePrintedBill;
    private Cashier cashier;
    private ArrayList<Bill> bills;

    private static boolean done = false;

    public CheckOutController(Cashier cashier) {
        this.cashier = cashier;
        bills = loadBills(cashier.getUsername());
        int lastBillID = 1;
        if(bills.isEmpty())
            lastBillID = 1;
        else
            lastBillID = bills.getLast().getBillID() + 1;
        toBePrintedBill = cashier.createNewBill(lastBillID);
        initialize();
    }

    private void initialize() {
        view.getCurrentBill().setItems(billRows);
        view.getCurrentBill().setEditable(true);
        setupEventHandlers();
    }

    private void setupEventHandlers() {
        view.getAddBtn().setOnAction(e -> handleAddItem());
        view.getPrintBillBtn().setOnAction(e -> handlePrintBill());
        view.getDeleteBtn().setOnAction(e -> handleDeleteItem());
    }

    private void handleNewBill() {
        view.getCurrentBill().getItems().clear();
        billRows.clear();
        toBePrintedBill = cashier.createNewBill(getLatestBillID(cashier));
        updateBillInfoLabels();
    }

    private void handleAddItem()
    {
        int productID = Integer.parseInt(view.getProdIdTF().getText());
        String quantityString = view.getQuantityTF().getText().trim();
        if (quantityString.isEmpty() || !quantityString.matches("\\d+")) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid Quantity Input. Please enter a number.");
            return;
        }
        try{
            int quantity = Integer.parseInt(quantityString);
            Optional<Item> optional = searchItemID(productID);
            view.getCurrentBill().refresh();
            if (optional.isPresent()) {
                Item item = optional.get();
                Bill.BillRow newRow = new Bill.BillRow(item, quantity);
                billRows.add(newRow);
                view.getCurrentBill().refresh();
                cashier.addItemToBill(toBePrintedBill, item, quantity);
                updateBillInfoLabels();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Couldn't add item.");
            }
        }catch(NumberFormatException e){
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid Quantity Input.");
        }finally {
            view.getQuantityTF().clear();
            view.getProdIdTF().clear();
            view.getClientCashInput().clear();
        }
    }
        //hello lol
    private void handleDeleteItem()
    {
        Bill.BillRow selectedRow = view.getCurrentBill().getSelectionModel().getSelectedItem();
        if(selectedRow == null)
        {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a row to delete");
            return;
        }
        billRows.remove(selectedRow);
        showAlert(Alert.AlertType.INFORMATION, "Success", "Item removed successfully!");
    }

    private void handlePrintBill() {
        if (getView().getCurrentBill().getItems() != null && !getView().getCurrentBill().getItems().isEmpty()) {
            try {
                BillFileHandler billFileHandler = new BillFileHandler();
                billFileHandler.generateBillFile(toBePrintedBill);

                saveBill(toBePrintedBill, cashier.getUsername());

                System.out.println(BillFileHandler.loadBills(cashier.getUsername()));
                updateBillInfoLabels();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Bill printed successfully!");
                handleNewBill();
                view.getClientCashInput().clear();
            } catch (FileNotFoundException e) {
                cashier.removeBillFromCashier(toBePrintedBill);
                int id = toBePrintedBill.getBillID() + 1;
                toBePrintedBill.setBillId(id);
                showAlert(Alert.AlertType.ERROR, "Error", "File already exists.");
            } catch (IOException e) {
                cashier.removeBillFromCashier(toBePrintedBill);
                showAlert(Alert.AlertType.ERROR, "Error", "File system error: " + e.getMessage());
            } catch (Exception e) {
                cashier.removeBillFromCashier(toBePrintedBill);
                showAlert(Alert.AlertType.ERROR, "Error", "Couldn't print bill.");
                System.err.println(e.getMessage());
            }
        }else
            showAlert(Alert.AlertType.INFORMATION, "Error", "Bill is empty.");
    }

    public ObservableList<Item> getProductList() {
        ObservableList<Item> productList = FXCollections.observableArrayList();
        ProductDatabaseController products = new ProductDatabaseController();
        productList = products.getItemsList();
        return productList;
    }

    public static int getLatestBillID(Cashier cashier){
        int highestID = 0;
        ArrayList<Bill> bills = BillFileHandler.loadBills(cashier.getUsername());
        if(bills != null)
        {
            for(Bill bill : bills)
            {
                if(bill.getBillID() > highestID)
                    highestID = bill.getBillID();
            }
        }
        return highestID + 1;
    }

    private void updateBillInfoLabels() {
        int billID = toBePrintedBill.getBillID();
        double total = toBePrintedBill.getTotalAmount();

        String billInfoText = "Bill ID: " + billID + "\t\tTotal Amount: " + String.format("%.2f", total);
        view.getInformationLabel().setText(billInfoText);

        String cashInput = view.getClientCashInput().getText();
        addCashInputListener();
    }

    private void addCashInputListener() {
        view.getClientCashInput().textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty() || !newValue.matches("\\d*(\\.\\d{0,2})?")) {
                view.getClientChange().setText("Change: --");
            } else {
                try {
                    view.getQuantityTF().setEditable(false);
                    view.getProdIdTF().setEditable(false);
                    double cashInputValue = Double.parseDouble(newValue);
                    double total = toBePrintedBill.getTotalAmount();
                    double change = cashInputValue - total;
                    view.getClientChange().setText("Change: " + String.format("%.2f", change));
                } catch (NumberFormatException e) {
                    view.getClientChange().setText("Change: --");
                }
                finally{
                    view.getQuantityTF().setEditable(true);
                    view.getProdIdTF().setEditable(true);
                }
            }
        });
    }


    private Optional<Item> searchItemID(int productID) {
        for(Item item: getProductList()){
            if(productID == item.getItemid()) {
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public CheckOutView getView() {
        return view;
    }
}