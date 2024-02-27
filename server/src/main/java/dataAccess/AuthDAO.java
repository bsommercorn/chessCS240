package dataAccess;

import model.AuthData;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class AuthDAO {
    //private static ArrayList<Game> myGames = new ArrayList<Game>();
    private static ArrayList<AuthData> myTokens = new ArrayList<AuthData>();
    public AuthData createAuth(String username) throws DataAccessException{
        String newToken = username + "/" + LocalDateTime.now();
        AuthData myToken = new AuthData(newToken, username);
        if (!myTokens.contains(myToken)) {
            myTokens.add(myToken);
        } else {
            throw new DataAccessException("AuthToken already exists");
        }
        return myToken;
    }

    public void clearAll() {
        myTokens = new ArrayList<AuthData>();
    }
    //create
    //read
    //update
    //delete

}