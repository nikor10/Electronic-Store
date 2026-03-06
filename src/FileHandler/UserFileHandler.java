package FileHandler;

import models.*;
import help.HeaderlessObjectOutputStream;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class UserFileHandler {
    private final static File CASHIERS_FILE = new File("cashier.dat");
    private final static File MANAGER_FILE = new File("manager.dat");
    private final static File ADMINISTRATOR_FILE = new File("administrator.dat");



    public static ArrayList<Cashier> loadCashiers(){
        if(!CASHIERS_FILE.exists() || CASHIERS_FILE.length() == 0){
            seedCashierData();
            System.out.println("File size after seeding " + CASHIERS_FILE.length());

        }

        ArrayList<Cashier> cashiers = new ArrayList<>();
        try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream(CASHIERS_FILE))) {
            Cashier cashier;
            while(true){
                cashier = (Cashier)reader.readObject();
                cashiers.add(cashier);
            }
        }

        catch(EOFException ex){
            System.out.println("End of file cashier");
        }
        catch(ClassNotFoundException ex){
            ex.printStackTrace();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        return cashiers;
    }

    public static ArrayList<Manager> loadManager(){
        if(!MANAGER_FILE.exists() || MANAGER_FILE.length() == 0){
            seedManagerData();
            System.out.println("File size after seeding " + MANAGER_FILE.length());

        }
//l
        ArrayList<Manager> managers = new ArrayList<>();
        try(ObjectInputStream Mreader = new ObjectInputStream(new FileInputStream(MANAGER_FILE))) {
            Manager manager;
            while(true){
                manager = (Manager)Mreader.readObject();
                managers.add(manager);
            }
        }

        catch(EOFException ex){
            System.out.println("End of file ManagerLoad");
        }
        catch(ClassNotFoundException ex){
            ex.printStackTrace();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        return managers;
    }

    public static ArrayList<Administrator> loadAdministrators(){
        if(!ADMINISTRATOR_FILE.exists() || ADMINISTRATOR_FILE.length() == 0){
            seedAdministratorData();
            System.out.println("File size after seeding " + ADMINISTRATOR_FILE.length());

        }

        ArrayList<Administrator> administrators = new ArrayList<>();
        try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream(ADMINISTRATOR_FILE))) {
            Administrator administrator;
            while(true){
                administrator = (Administrator) reader.readObject();
                administrators.add(administrator);
            }
        }

        catch(EOFException ex){
            System.out.println("End of file AdministratorLoad");
        }
        catch(ClassNotFoundException ex){
            ex.printStackTrace();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        return administrators;
    }

    private static void seedCashierData(){
        Cashier [] cashiers=new Cashier[]{
                new Cashier("niko", "niko", LocalDate.of(2003, 6, 10), User.Position.cashier, User.AccessLevel.revoked, 100,"nrigo23@epoka.edu.al", "0693531703", Cashier.Sectors.GAMING),
                new Cashier("bora", "2", LocalDate.of(2004, 6, 13), User.Position.cashier, User.AccessLevel.full, 95,"bdikolli@epoka.edu.al", "0698033588", Cashier.Sectors.CAMERAS_AND_PHOTOGRAPHY),
                new Cashier("dea", "lol", LocalDate.of(2004, 12, 28), User.Position.cashier, User.AccessLevel.partly, 100,"deaplaku123@epoka.edu.al", "0697657412", Cashier.Sectors.MOBILE_ACCESSORIES)
        };

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

        cashiers[1].addBillToCashier(bill1);
        cashiers[2].addBillToCashier(bill3);
        cashiers[0].addBillToCashier(bill2);
        cashiers[1].addBillToCashier(bill4);
        cashiers[2].addBillToCashier(bill5);


        try(FileOutputStream outputStream = new FileOutputStream(CASHIERS_FILE, true)){
            ObjectOutputStream writer = new ObjectOutputStream(outputStream);
            for(Cashier cashier : cashiers){
                writer.writeObject(cashier);
            }
            System.out.println("Seeded cashier data ");
        }
        catch(IOException ex){
            System.out.println("Error reading cashiers data" + ex.getMessage());
        }
    }

    private static void seedManagerData() {
        Manager[] managers = new Manager[]{
                new Manager("niko", "manager", LocalDate.of(2003, 6, 10), "nrigo23@epoka.edu.al", "0693531703"),
                new Manager("bora", "manager", LocalDate.of(1990, 8, 22), "bdikolli@epoka.edu.al", "0690533588")
        };

        try (FileOutputStream outputStream = new FileOutputStream(MANAGER_FILE, true)) {
            ObjectOutputStream writer = new ObjectOutputStream(outputStream);
            for (Manager manager : managers) {
                writer.writeObject(manager);
            }
            System.out.println("Seeded manager data");
        } catch (IOException ex) {
            System.out.println("Error writing managers data: " + ex.getMessage());
        }
    }

    private static void seedAdministratorData() {
        Administrator administrator =  new Administrator("niko", "admin1", LocalDate.of(1975, 1, 5), User.Position.administrator, User.AccessLevel.full, 300, "admin1@company.com", "0691122334");

        try (FileOutputStream outputStream = new FileOutputStream(ADMINISTRATOR_FILE, true)) {
            ObjectOutputStream writer = new ObjectOutputStream(outputStream);
            writer.writeObject(administrator);
            System.out.println("Seeded administrator data");
        } catch (IOException ex) {
            System.out.println("Error writing administrators data: " + ex.getMessage());
        }
    }



    public static boolean saveCashier(Cashier cashier){
        try(FileOutputStream outputStream = new FileOutputStream(CASHIERS_FILE, true)){
            ObjectOutputStream writer;
            if(CASHIERS_FILE.length()>0)
                writer = new HeaderlessObjectOutputStream(outputStream);
            else
                writer = new ObjectOutputStream(outputStream);
            writer.writeObject(cashier);
            return true;
        }
        catch (IOException ex){
            System.out.println("Error saving user data" + ex.getMessage());
            return false;
        }
    }

    public static boolean saveManager(Manager manager){
        try(FileOutputStream outputStream = new FileOutputStream(MANAGER_FILE, true)){
            ObjectOutputStream writer;
            if(MANAGER_FILE.length()>0)
                writer = new HeaderlessObjectOutputStream(outputStream);
            else
                writer = new ObjectOutputStream(outputStream);
            writer.writeObject(manager);
            return true;
        }
        catch (IOException ex){
            System.out.println("Error saving user data" + ex.getMessage());
            return false;
        }
    }

    public static boolean saveAdministrator(Administrator administrator){
        try(FileOutputStream outputStream = new FileOutputStream(ADMINISTRATOR_FILE, true)){
            ObjectOutputStream writer;
            if(ADMINISTRATOR_FILE.length()>0)
                writer = new HeaderlessObjectOutputStream(outputStream);
            else
                writer = new ObjectOutputStream(outputStream);
            writer.writeObject(administrator);
            return true;
        }
        catch (IOException ex){
            System.out.println("Error saving user data" + ex.getMessage());
            return false;
        }
    }

    public static Cashier findCashierByUsername(String username){
        ArrayList<Cashier> cashiers = loadCashiers();
        for(Cashier cashier : cashiers){
            if(cashier.getUsername().equalsIgnoreCase(username)){
                return cashier;
            }
        }
        return null;
    }

    public static Manager findManagerByUsername(String username){
        ArrayList<Manager> managers = loadManager();
        for(Manager manager : managers){
            if(manager.getUsername().equalsIgnoreCase(username)){
                return manager;
            }
        }
        return null;
    }

    public static Administrator findAdministratorByUsername(String username){
        ArrayList<Administrator> administrators = loadAdministrators();
        for(Administrator administrator : administrators){
            if(administrator.getUsername().equalsIgnoreCase(username)){
                return administrator;
            }
        }
        return null;
    }

    public static void updateCashier(Cashier updatedCashier){
        ArrayList<Cashier> cashiers = loadCashiers();
        boolean cashierFound = false;

        for(int i = 0; i < cashiers.size(); i++){
            if(cashiers.get(i).getUsername().equalsIgnoreCase(updatedCashier.getUsername())){
                cashiers.set(i, updatedCashier);
                cashierFound = true;
                break;
            }
        }

        if(cashierFound){
            try(FileOutputStream outputStream = new FileOutputStream(CASHIERS_FILE);
            ObjectOutputStream writer = new ObjectOutputStream(outputStream)){
                for(Cashier cashier : cashiers){
                    writer.writeObject(cashier);
                }
            }catch(IOException ex) {
                System.out.println("Error cashier user data" + ex.getMessage());
            }
        }else{
            System.out.println("User not found");
        }
    }


    public static void updateManager(Manager updatedManager){
        ArrayList<Manager> managers = loadManager();
        boolean managerFound = false;

        for(int i = 0; i < managers.size(); i++){
            if(managers.get(i).getUsername().equalsIgnoreCase(updatedManager.getUsername())){
                managers.set(i, updatedManager);
                managerFound = true;
                break;
            }
        }

        if(managerFound){
            try(FileOutputStream outputStream = new FileOutputStream(MANAGER_FILE);
                ObjectOutputStream writer = new ObjectOutputStream(outputStream)){
                for(Manager manager : managers){
                    writer.writeObject(manager);
                }
            }catch(IOException ex) {
                System.out.println("Error manager user data" + ex.getMessage());
            }
        }else{
            System.out.println("User not found");
        }
    }


    public static void updateAdministrator(Administrator updatedAdministrator){
        ArrayList<Administrator> administrators = loadAdministrators();
        boolean administratorFound = false;

        for(int i = 0; i < administrators.size(); i++){
            if(administrators.get(i).getUsername().equalsIgnoreCase(updatedAdministrator.getUsername())){
                administrators.set(i, updatedAdministrator);
                administratorFound = true;
                break;
            }
        }

        if(administratorFound){
            try(FileOutputStream outputStream = new FileOutputStream(ADMINISTRATOR_FILE);
                ObjectOutputStream writer = new ObjectOutputStream(outputStream)){
                for(Administrator administrator : administrators){
                    writer.writeObject(administrator);
                }

            }catch(IOException ex) {
                System.out.println("Error cashier user data" + ex.getMessage());
            }
        }else{
            System.out.println("User not found");
        }
    }

}