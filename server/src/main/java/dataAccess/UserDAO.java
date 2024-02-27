package dataAccess;

import model.UserData;

import java.util.HashMap;

public class UserDAO {
    private static HashMap<String,UserData> myUsers = new HashMap<String,UserData>();
    public void addUser(UserData newUser) throws DataAccessException {
        //check if the username already exists
        if (myUsers.get(newUser.getUsername()) == null) {
            myUsers.put(newUser.getUsername(), newUser);
        }
        else {
            throw new DataAccessException("Username already exists");
        }
        //if it doesn't, add the UserData to the HashMap

    }

    public void clearAll() {
        myUsers = new HashMap<String,UserData>();
    }
    //create
    //read
    //update
    //delete
}
