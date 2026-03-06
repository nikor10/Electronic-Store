package models;

import FileHandler.BillFileHandler;
import java.io.Serial;
import java.io.Serializable;
import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Bill implements Serializable {
    @Serial
    private static final long serialVersionUID = -1144232846067971669L;

//
//      public void setBillID(int billID) {
//        this.billID = billID;
//    }
    
    private int billID = 0;
    private final LocalDate date;
    private Map<Item, Integer> itemsSold;
    private double totalAmount;

    //constructor
    public Bill(LocalDate date) {
      //  this.billID =
        this.date = date;
        this.itemsSold = new HashMap<Item, Integer>();
        this.totalAmount = 0.00;
    }

    public Bill(int id, LocalDate date) {
        this.billID = id;
        this.date = date;
        this.itemsSold = new HashMap<>();
        this.totalAmount = 0.00;
    }

    public LocalDate getDate() {
        return date;
    }

    public Map<Item, Integer> getItemsSold() {
        return itemsSold;
    }

    public void setItemsSold(Map<Item, Integer> itemsSold) {
        this.itemsSold = itemsSold;
    }
//l
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getBillID() {
        return billID;
    }

    public void addItem(Item item, int stock) {
        if (stock > 0 && stock <= item.getStock()) {
            itemsSold.put(item, itemsSold.getOrDefault(item, 0) + stock);
            item.setStock(item.getStock() - stock);
            totalAmount += item.getSellingPrice() * stock;
            System.out.println("no exception: item stock is" + item.getStock() + ", quantity is: " + stock);
        } else {
            System.out.println(item.getStock() + " " + stock);
            throw new IllegalArgumentException("Invalid stock. Check availability!");
        }
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String toString() {
        return "Bill ID: " + billID + " Date: " + date + " Items: " + itemsSold;
    }

    public void setBillId(int id) {
        billID = id;
    }

    public static class BillRow {
        private final Item item;
        private int quantity;
        private double total;

        public BillRow(Item item, int quantity) {
            this.item = item;
            this.quantity = quantity;
        }

        public String getName() {
            return item.getItemName();
        }

        public int getId() {
            return item.getItemid();
        }

        public double getPrice() {
            return item.getSellingPrice();
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
            updateTotal();
        }

        public int getQuantity() {
            return quantity;
        }

        public double getTotal() {
            return item.getSellingPrice() * quantity;
        }

        public void updateTotal() {
            this.total = item.getSellingPrice() * quantity;
        }

        public Item getItem() {
            return item;
        }
    }
}


