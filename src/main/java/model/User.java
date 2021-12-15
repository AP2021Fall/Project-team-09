package model;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    private ArrayList<User> users = new ArrayList<>();


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
    private static final String USERNAME_PATTERN =
            "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$";

    private static final Pattern pattern = Pattern.compile(USERNAME_PATTERN);
    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

    private static final Pattern patternn = Pattern.compile(PASSWORD_PATTERN);

    public User(String username, String password, String email) {
        this.username=username;
        allUsers.add(this);
        this.password1=password;
        this.email=email;
        allEmail.add(email);
    }


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
    public static boolean checkUserNameFormat(final String username) {
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    public static boolean isEmailValid (String email){
        Pattern pattern = Pattern.compile("([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6})*$");
        Matcher matcher = pattern.matcher(email);
        if(matcher.matches())
            return true;

        return false;

    }

    public static boolean checkPasswordFormat(final String password) {
        Matcher matcher = patternn.matcher(password);
        return matcher.matches();
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
    public static User  createUser(String username, String password, String email) {
        if (usernameExists(username)) {
                if (emailExists(email)) {
                    if (checkUserNameFormat(username)) {
                        if (isEmailValid(email)) {
                            if (checkPasswordFormat(password)) {
                                User user = new User(username, password, email);
                                return new User(username,password,email);
                            } else
                                return null;
                        } else
                            return null;
                    } else
                        return null;
                } else
                    return null;
        } else
            return null;
    }
    public Team[] getTeams() {
        return new Team[0];
    }



    /**
     * @return found user or null if not found
     */
    public static User getUser(String username, String password) {
        return null;
    }

    public String getPassword() {
        return null;
    }

    public boolean passwordInHistory(String newPassword) {
        return false;
    }

    public void setPassword(String newPassword) {

    }

    public void setUsername(String newUsername) {

    }

    public String getUsername() {
        return null;
    }

    public Team[] getTeams() {
        return new Team[0];
    }
}
