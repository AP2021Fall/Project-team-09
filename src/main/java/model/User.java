package model;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class User {

    private static ArrayList<User> allUsers = new ArrayList<>();
    private ArrayList<String> allEmail = new ArrayList<>();
    private String lastName;
    private String firstname;
    private String birthday;
    private String username;
    private String password1;
    private String password2;
    private String email;
    Type WhoLogin;

    enum WhoLogin {
        teamMember,teamLeader,systemAdministrator
    }

    public User(String username,String password1,String password2, String email ) {
        this.username = username;
        allUsers.add(this);
        this.password1 = password1;
        this.password2 = password2;
        this.email = email;
        allEmail.add(email);
    }

    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword1() {
        return password1;
    }

    public String getPassword2() {
        return password2;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<String> getAllEmail() {
        return allEmail;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public static boolean usernameExists(String username) {
        for(User user : User.getAllUsers()){
            if(user.getUsername().equals(username))
                return false;
        }

        return true;
    }

     public static boolean emailExists(String email) {
        for(User user : User.getAllUsers() ){
            if(email.equals(user.getEmail()))
               return false;
        }
        return true;
    }

    public boolean isEmailValid (String email){
        Pattern pattern = Pattern.compile("([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6})*$");
        Matcher matcher = pattern.matcher(email);
        if(matcher.matches())
            return true;

        return false;

    }
     public boolean isBirthdayvalid (String birthday){
        Pattern pattern = Pattern.compile("([12]\\\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\\\d|3[01]))");
        Matcher matcher = pattern.matcher(birthday);
        if(matcher.matches())
            return true;

        return false;
    }
    public boolean PasswordIntHistory(String newPassword){
        if(password1.equals(password2)){
            return false;
        }
        return true;
    }
    public boolean SameSocendPassword(String password2){
        if(password1.equals(password2)){
            return true;
        }
        return false;
    }
    public Team[] getTeams() {
        return new Team[0];
    }
}
