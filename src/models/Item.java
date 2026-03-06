package models;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public class Item implements Serializable {

    @Serial
    private static final long serialVersionUID = 4508731311426577693L;
    private String itemName;
    private int itemid;
    private int stock;
    private String section;
    private String supplier;
    private double purchasedPrice;
    private double sellingPrice;



    // Constructor
    public Item(String itemName, int itemid, String section, String supplier, double purchasedPrice, double sellingPrice, int stock) {
        this.itemName = itemName;
        this.itemid = itemid;
        this.section = section;
        this.stock = stock;
        this.supplier = supplier;
        this.purchasedPrice = purchasedPrice;
        this.sellingPrice = sellingPrice;
    }

    // Getters and Setters
    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemid(){
        return this.itemid;
    }

    public void setItemid(int itemid){
        this.itemid = itemid;
    }

    public String getSection() {
        return this.section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSupplier() {
        return this.supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public double getPurchasedPrice() {
        return purchasedPrice;
    }

    public void setPurchasedPrice(double purchasedPrice) {
        this.purchasedPrice = purchasedPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    // Business Logic
    public void updateStock(int quantity) {
        this.stock += quantity;
    }

    public boolean isOutOfStock() {
        return this.stock <= 0;
    }
}

