package models;


import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Administrator extends User implements Serializable {

    @Serial
    private static final long serialVersionUID = -4921527990764310745L;
    private List<User> users;
  
    public Administrator(String username, String password, LocalDate birthday, Position position, AccessLevel accessLevel, double salary, String email, String phonenr) {
        super(username, password, birthday, position, accessLevel, salary, email, phonenr, Sectors.ALL);
    }

    public void addUser(User user) {
        this.users.add(user);
        System.out.println("User " + user.getUsername() + " has been added.");
    }

    public void modifyUser(String username, String newPassword, Position newPosition) {
        for(User user : this.users) {
            if(user.getUsername().equalsIgnoreCase(username)) {
                user.setPassword(newPassword);
                user.setPosition(newPosition);
                System.out.println("User " + user.getUsername() + " has been modified.");
                return;
            }
        }
        System.out.println("User not found.");
    }

    public void removeUser(String username){
        this.users.removeIf(user -> user.getUsername().equalsIgnoreCase(username));
        System.out.println("User " + username + " has been removed.");
    }

    public void viewUsers() {
        System.out.println("List of users:");
        for(User user : this.users) {
            System.out.println(user.getUsername() + " (" + user.getPosition() + ")");
        }
    }
}

