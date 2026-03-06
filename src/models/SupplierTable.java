package models;


import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;


public class SupplierTable implements Serializable {

    @Serial
    private static final long serialVersionUID = 9039752863995951156L;
    private int number;
    private String name;
    private ArrayList<String> products;
    private String supplierContact;


    public SupplierTable() {
        this.number = 1;
        this.name = "supplier";
        this.products = new ArrayList<String>();
        this.supplierContact = "test3";
    }


    public SupplierTable(int number, String name, ArrayList<String> products, String supplierContact) {
        this.number = number;
        this.name = name;
        this.products = products;
        this.supplierContact = supplierContact;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<String> products) {
        this.products = products;
    }

    public String getSupplierContact() {
        return supplierContact;
    }

    public void setSupplierContact(String supplierContact) {
        this.supplierContact = supplierContact;
    }
}
