package model;

import java.util.ArrayList;
import java.util.HashMap;

public class DataBase {
    private static DataBase dataBase = new DataBase();
    private ArrayList<User> allUsers ;

    private DataBase() {
        this.allUsers = new ArrayList<>();
    }


    public static DataBase getDataBase() {
        return dataBase;
    }

    public ArrayList<User> getAllUsers() {
        return allUsers;
    }
}
