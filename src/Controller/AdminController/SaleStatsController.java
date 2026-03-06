package Controller.AdminController;

import View.AdminViews.SaleStatsView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Bill;
import models.Cashier;
import models.Item;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public class SaleStatsController {

    //private ObservableList<CustomData> statisticsData = FXCollections.observableArrayList();
    //private final ArrayList<Cashier> cashiersBills = Cashier.createSampleCashiers(); // Demo cashiers
    private SaleStatsView view;

   /* public SaleStatsController(SaleStatsView view) {
        this.view = view;
        populateDemoData();
    }*/


    /*private void populateDemoData() {
        statisticsData.clear();
        for (Cashier cashier : cashiersBills) {
            for (Bill bill : cashier.getAllCashierBills()) {
                for (Map.Entry<Item, Integer> entry : bill.getItemsSold().entrySet()) {
                    statisticsData.add(new CustomData(
                            entry.getKey(),
                            entry.getValue(),
                            cashier.getUsername(),
                            bill.getBillID(),
                            bill.getDate()
                    ));
                }
            }
        }
    }


    public ObservableList<CustomData> getStatisticsData() {
        return statisticsData;
    }


    public double calculateTodayRevenue() {
        double totalRevenue = 0;
        LocalDate today = LocalDate.now();
        for (Cashier cashier : cashiers) {
            for (Bill bill : cashier.getAllCashierBills()) {
                if (bill.getDate().isEqual(today)) {
                    totalRevenue += bill.getTotalAmount();
                }
            }
        }
        return totalRevenue;
    }

*/
    public static class CustomData {
        private final Item item;
        private final Integer quantity;
        private final String cashierName;
        private final Integer billID;
        private final LocalDate billDate;

        public CustomData(Item item, Integer quantity, String cashierName, Integer billID, LocalDate billDate) {
            this.item = item;
            this.quantity = quantity;
            this.cashierName = cashierName;
            this.billID = billID;
            this.billDate = billDate;
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

        public Integer getBillID() {
            return billID;
        }

        public LocalDate getBillDate() {
            return billDate;
        }
    }
}
