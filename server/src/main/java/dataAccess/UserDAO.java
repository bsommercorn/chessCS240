package dataAccess;

import model.UserData;

import java.util.HashMap;
import java.util.Objects;

public class UserDAO {
    private static HashMap<String,UserData> myUsers = new HashMap<String,UserData>();
    public void addUser(UserData newUser) throws DataAccessException {
        //check if the username already exists
        if (myUsers.get(newUser.getUsername()) == null) {
            myUsers.put(newUser.getUsername(), newUser);
        }
        else {
            throw new DataAccessException("Error: username already exists");
        }
        //if it doesn't, add the UserData to the HashMap

    }

    public void clearAll() {
        myUsers = new HashMap<String,UserData>();
    }

    public UserData verifyUser(String username, String password) throws DataAccessException{
        if (myUsers.get(username) != null && Objects.equals(myUsers.get(username).getPassword(), password)) {
            return myUsers.get(username);
        }
        else {
            throw new DataAccessException("Error: Username or password was incorrect");
        }
    }

    public int getSize() {
        return myUsers.size();
    }
}
