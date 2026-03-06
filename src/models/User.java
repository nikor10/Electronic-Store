package models;
import java.io.Serial;
import java.time.LocalDate;
import java.io.Serializable;

public class User implements Serializable {


    @Serial
    private static final long serialVersionUID = -3487702632461648364L;

    public enum Position {
        cashier,
        manager,
        administrator,
    }

    public enum AccessLevel{
        revoked,
        partly,
        full
    }

    public enum Sectors {
        CONSUMER_ELECTRONICS,
        HOME_APPLIANCES,
        COMPUTER_AND_OFFICE,
        GAMING,
        MOBILE_ACCESSORIES,
        CAMERAS_AND_PHOTOGRAPHY,
        SMART_HOME_AND_IOT,
        AUDIO_AND_VIDEO,
        ACCESSORIES_AND_COMPONENTS,
        ALL
    }

    private String username;
    private String password;
    private LocalDate birthday;
    private Position position;
    private AccessLevel accessLevel;
    private Sectors sector;
    private double salary;
    private String email;
    private String phonenr;
    private byte[] profileImage;  // Store image as byte array (alternatively, use a String for file path)

    private static final byte[] DEFAULT_PROFILE_IMAGE = new byte[] {};  // Placeholder for default image data

    // Constructor
    public User(String username, String password, LocalDate birthday, Position position, AccessLevel accessLevel, double salary, String email, String phonenr, Sectors sector) {
        this.username = username;
        this.password = password;
        this.birthday= birthday;
        this.position = position;
        this.accessLevel = accessLevel;
        this.salary = salary;
        this.email = email;
        this.phonenr = phonenr;
        this.sector= sector;
        this.profileImage = DEFAULT_PROFILE_IMAGE;  // Set default image

    }

    // Getters and Setters
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setCashier(){
        this.position = Position.cashier;
    }

    public void setManager(){
        this.position = Position.manager;
    }

    public void setAdministrator(){
        this.position = Position.administrator;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthday(){
        return this.birthday;
    }

    public void setBirthday(LocalDate birthday){
        this.birthday = birthday;
    }

    public Position getPosition(){
        return this.position;
    }

    public void setPosition(Position position){
        this.position = position;
    }

    public AccessLevel getAccessLevel(){
        return this.accessLevel;
    }

    public void setAccessLevel(AccessLevel accessLevel){
        this.accessLevel = accessLevel;
    }

    public double getSalary(){
        return this.salary;
    }

    public void setSalary(double salary){
        this.salary = salary;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPhonenr(){
        return this.phonenr;
    }

    public void setPhonenr(String phonenr){
        this.phonenr = phonenr;
    }

    public byte[] getProfileImage() {
        return this.profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    public Sectors getSector() {
        return sector;
    }

    public void setSector(Sectors sector) {
        this.sector = sector;
    }



}

