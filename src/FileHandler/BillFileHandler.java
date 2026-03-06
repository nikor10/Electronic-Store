package FileHandler;

import help.HeaderlessObjectOutputStream;
import models.Bill;
import models.Item;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

public class BillFileHandler {
//l
    public static ArrayList<Bill> loadBills(String username) {
        String filename = "Bill" + username + ".dat";
        final File BILLS_FILE = new File(filename);
        if(!BILLS_FILE.exists() || BILLS_FILE.length() == 0) {
            System.out.println("File size: " + BILLS_FILE.length());
        }
        ArrayList<Bill> bills = new ArrayList<>();
        try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream(BILLS_FILE))) {
            Bill bill;
            while(true){
                bill = (Bill)reader.readObject();
                bills.add(bill);
                System.out.println("Bill decoded: " + bill.getBillID() + " " + bill.getTotalAmount());
            }
        }
        catch (EOFException e){
            System.out.println("End of file reached");
        }
        catch(ClassNotFoundException ex){
            ex.printStackTrace();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        return bills;
    }

        public static boolean saveBill(Bill bill, String username) {
        String filename = "Bill" + username + ".dat";
        final File BILLS_FILE = new File(filename);

        try(FileOutputStream outputStream = new FileOutputStream(BILLS_FILE, true)){
            ObjectOutputStream writer;

            if(BILLS_FILE.length() > 0)
                writer = new HeaderlessObjectOutputStream(outputStream);
            else
                writer = new ObjectOutputStream(outputStream);
            writer.writeObject(bill);
            return true;
        }
        catch(IOException ex){
            return false;
        }
    }

    public static ArrayList<Bill> getBillsByDate(LocalDate date, String username){
        ArrayList<Bill> billsForDate = new ArrayList<>();
        ArrayList<Bill> allBills = loadBills(username);

        for(Bill bill : allBills){
            if(bill.getDate().equals(date)){
                billsForDate.add(bill);
            }
        }
        return billsForDate;
    }

    public static void clearAllBills(String username) {
        String filename = "Bill"+username+".dat";
        final File BILLS_FILE = new File(filename);
        try(PrintWriter writer = new PrintWriter(BILLS_FILE)){
            writer.print("");
            System.out.println("All bills cleared");
        }catch(FileNotFoundException ex){
            System.out.println("Failed to clear bills: " + ex.getMessage());
        }
    }

    public void generateBillFile(Bill bill) throws FileNotFoundException {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = bill.getDate().format(dateFormatter);
        String fileName = "ElectronicStore_Nikola_Rigo/src/resources/bills/Bill" + bill.getBillID() + "_" + formattedDate + ".txt";
        File file = new File(fileName);

        if (file.exists()) {
            throw new FileNotFoundException("Bill already generated: " + fileName);
        }

        try(PrintWriter writer = new PrintWriter(fileName);){
            writer.println("Bill ID: " + bill.getBillID());
            writer.println("Date: " + formattedDate);
            writer.println("--------------------------------");
            writer.printf("%-20s %-10s %-10s", "Item Name", "Quantity", "Price");
            writer.println();
            for(Map.Entry<Item, Integer> entry : bill.getItemsSold().entrySet()){
                Item item = entry.getKey();
                int stock = entry.getValue();
                double price = item.getSellingPrice() * stock;
                writer.printf("%-20s %-10d %-10.2f", item.getItemName(), stock, price);
                writer.println();
            }
            writer.println("--------------------------------");
            writer.printf("Total Amount: %-17s %.2f", " ", bill.getTotalAmount());
            writer.println();
        }catch(IOException e){
            System.err.println("Error generating bill file: " + e.getMessage());
        }
    }

    public static double getTotalRevenue(String username){
        String filename = "Bill"+username+".dat";
        final File BILLS_FILE = new File(filename);
        ArrayList<Bill> bills = loadBills(username);
        double totalRevenue = 0.0;

        for(Bill bill : bills){
            totalRevenue += bill.getTotalAmount();
        }
        return totalRevenue;
    }

    public static boolean deleteBill(int billID, String username) {
        String filename = "Bill"+username+".dat";
        final File BILLS_FILE = new File(filename);
        ArrayList<Bill> bills = loadBills(username);
        boolean removed = bills.removeIf(bill -> bill.getBillID() == billID);

        if (removed) {
            try (FileOutputStream outputStream = new FileOutputStream(BILLS_FILE);
                 ObjectOutputStream writer = new ObjectOutputStream(outputStream)) {
                for (Bill bill : bills) {
                    writer.writeObject(bill);
                }
                System.out.println("Bill deleted successfully.");
                return true;
            } catch (IOException ex) {
                System.out.println("Failed to delete bill: " + ex.getMessage());
            }
        } else {
            System.out.println("Bill not found.");
        }
        return false;
    }

}
