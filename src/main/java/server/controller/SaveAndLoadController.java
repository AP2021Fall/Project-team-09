package server.controller;

import model.Board;
import model.Task;
import model.Team;
import model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class SaveAndLoadController {


    public static void save() {
        try {
            File file = new File(EnvironmentVariables.getInstance().getString("SAVE_FILE"));
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            HashMap<String, Serializable> savingObjects = new HashMap<>();

            //ADD NEW OBJECTS FOR SAVE _____________________
            savingObjects.put("users", User.getAllUsers());
            savingObjects.put("teams", Team.getTeams());
            savingObjects.put("admin", User.getAdmin());
            savingObjects.put("tasks", Task.getAllTask());
            savingObjects.put("teamId", Team.getIdCounter());
            savingObjects.put("taskId", Task.getIdCounter());
            savingObjects.put("boardId", Board.getIdCounter());
            //_______________________________________________

            oos.writeObject(savingObjects);
            oos.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        try {
            File file = new File(EnvironmentVariables.getInstance().getString("SAVE_FILE"));
            if (!file.exists()) {
                User.setAdmin(new User("admin", "admin", "admin@gmail.com"));
                return;
            }
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            HashMap<String, Serializable> savingObjects = (HashMap<String, Serializable>) ois.readObject();

            //ADD NEW OBJECTS FOR SAVE _____________________
            User.setUsers((ArrayList<User>) savingObjects.get("users"));
            Team.setTeams((ArrayList<Team>) savingObjects.get("teams"));
            Task.setAllTask((ArrayList<Task>) savingObjects.get("tasks"));
            Task.setIdCounter((int) savingObjects.get("taskId"));
            Team.setIdCounter((int) savingObjects.get("teamId"));
            Board.setIdCounter((int) savingObjects.get("boardId"));
            User.setAdmin((User) savingObjects.get("admin"));
            //_______________________________________________

            ois.close();
            fis.close();
        } catch (Exception e) {
            User.setAdmin(new User("admin", "admin", "admin@gmail.com"));
            e.printStackTrace();
        }
    }
}
