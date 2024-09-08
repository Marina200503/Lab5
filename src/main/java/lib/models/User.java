package lib.models;

import java.io.Serializable;

public class User implements Serializable {
    private String userName;
    private String password;


    private int user_id;
    public User(String userName, String password, int user_id) {
        this.userName = userName;
        this.password = password;
        this.user_id = user_id;
    }
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }


    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public int getUser_id() {
        return user_id;
    }
}
