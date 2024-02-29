package service;

import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import spark.Request;
import spark.Response;

public class ClearService {
    private GameDAO gameAccess = new GameDAO();
    private AuthDAO tokenAccess = new AuthDAO();
    private UserDAO userAccess = new UserDAO();

    public void clearAll() {
        //System.out.println("gameA");
        gameAccess.clearAll();
        tokenAccess.clearAll();
        userAccess.clearAll();
    }

    public boolean isEmpty() {
        if (gameAccess.getSize() == 0 && tokenAccess.getSize() == 0 && userAccess.getSize() == 0) {
            return true;
        }
        else {
            return false;
        }
    }
}
