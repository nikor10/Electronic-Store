package models;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class Manager extends User implements Serializable{


    @Serial
    private static final long serialVersionUID = 245875221692891321L;

    private enum sector{

    };
    //constructor
    public Manager(String username, String password, LocalDate birthday, String email, String phoneNr) {
        super(username, password, birthday, Position.manager, AccessLevel.full, 50000, email, phoneNr, Sectors.ALL);
        
    }


}