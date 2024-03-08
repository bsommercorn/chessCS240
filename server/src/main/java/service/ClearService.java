package service;

import dataAccess.*;
import spark.Request;
import spark.Response;

import java.sql.SQLException;

public class ClearService {
    private GameSQLDAO gameAccess = new GameSQLDAO();
    private AuthSQLDAO tokenAccess = new AuthSQLDAO();
    private UserSQLDAO userAccess = new UserSQLDAO();

    public void clearAll() {
        //System.out.println("gameA");
        try {
            tokenAccess.clearAll();
            gameAccess.clearAll();
            userAccess.clearAll();
        } catch (DataAccessException | SQLException e) {
            //do nothing
        }
    }

    public boolean isEmpty() {
        if (gameAccess.storageSize() == 0 && tokenAccess.storageSize() == 0 && userAccess.storageSize() == 0) {
            return true;
        }
        else {
            return false;
        }
    }
}
