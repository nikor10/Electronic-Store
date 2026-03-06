package models;

import FileHandler.BillFileHandler;
import FileHandler.UserFileHandler;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public class Cashier extends models.User implements Serializable {

    @Serial
    private static final long serialVersionUID = -2473265587726823405L;
    private Sectors sector;
    private ArrayList<Bill> cashierBillList = new ArrayList<>();
    public Cashier(String username, String password, LocalDate birthday, models.User.Position position, models.User.AccessLevel accessLevel, double salary, String email, String phonenr, Sectors sector) {
      
        super(username, password, birthday, position, accessLevel, salary, email, phonenr, sector);
        UserFileHandler.saveCashier(this);
    }
    public Sectors getSector() {
        return sector;
    }

    public void setSector(Sectors sector) {
        this.sector = sector;
    }
//l
    public Bill createNewBill(int id) {
        Bill bill = new Bill(id, LocalDate.now());
        cashierBillList.add(bill);
        System.out.println("New bill created: " + bill);
        return bill;
    }

    public void addItemToBill(Bill bill, Item item, int quantity) {
        if (bill != null) {
            bill.addItem(item, quantity);
        } else {
            System.err.println("Cannot add item to null bill");
        }
    }


    public ArrayList<Bill> todayBills()
    {
        ArrayList<Bill> todaysBills = new ArrayList<>();
        String dateToday = String.valueOf(LocalDate.now());
        for(int i = 0; i < cashierBillList.size(); i++)
        {
            if(LocalDate.parse(dateToday).isEqual(cashierBillList.get(i).getDate()))
            {
                todaysBills.add(cashierBillList.get(i));
                System.out.println("Date today: " + dateToday + " bill date: " + cashierBillList.get(i).getDate());

            }
        }

        if(todaysBills.isEmpty())
            System.out.println("No bills generated today.");
        return todaysBills;
    }

//kk
    private boolean isBillsLoaded = false;

    public ArrayList<Bill> getAllCashierBills() {
        if (!isBillsLoaded) {
            loadCashierBills();
            isBillsLoaded = true;
        }
        return cashierBillList;
    }

    public void loadCashierBills() {
        cashierBillList = BillFileHandler.loadBills(this.getUsername());
        isBillsLoaded = true;
    }

    public void addBillToCashier(Bill bill)
    {
        cashierBillList.add(bill);
        BillFileHandler.saveBill(bill, this.getUsername());
    }

    public void removeBillFromCashier(Bill bill)
    {
        cashierBillList.remove(bill);
    }



}
