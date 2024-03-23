package ui;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import com.google.gson.Gson;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Objects;

public class Repl {
    public Object httpHandle(String myUrl, String requestMethod, Object myRequest, Class myclass) { //does this handle all request types?
        try {
            URI uri = new URI("http://localhost:8080/" + myUrl);
            HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection(); //declarations
            http.setRequestMethod(requestMethod);//customizing the http request part
            //System.out.println("RequestMethod was " + requestMethod);
            http.setDoOutput(true);
            //if (!Objects.equals(requestMethod, "DELETE")) {

            //}
            http.addRequestProperty("Content-Type", "application/json"); //customize this more? what even is this?

            /*if (myRequest.getClass() == CreateRequest.class) {
                System.out.println("Adding authorization");
                http.addRequestProperty("Authorization", ((CreateGameRequest) myRequest).getMyToken().getAuthToken());
            } else if (myRequest.getClass() == Request.class && requestMethod != "DELETE") { //something is going wrong here, or in the Server class
                System.out.println("Adding authorization"); //we get here successfully
                http.addRequestProperty("Authorization", ((RequestAndResult.Request) myRequest).getMyToken().getAuthToken());
                //http.setRequestMethod("GET");
            } else if (myRequest.getClass() == JoinGameRequest.class) {
                System.out.println("Adding authorization");
                http.addRequestProperty("Authorization", ((JoinGameRequest) myRequest).getMyToken().getAuthToken());
            } else if (myRequest.getClass() == LogoutRequest.class) {
                System.out.println("Adding authorization");
                http.addRequestProperty("Authorization", ((LogoutRequest) myRequest).getMyToken().getAuthToken());
            }
             */
            //http.setRequestProperty("Authorization", );
            //????
            //how to input parameters and connect them to the HTTP??????
            //var body = Map.of("name", "joe", "type", "cat");
            /*
            if (requestMethod == "GET") {
                http.setRequestMethod(requestMethod);
                System.out.println("RequestMethod set to GET");
            }
             */
            //http.setRequestMethod(requestMethod);
            try (var outputStream = http.getOutputStream()) { //something here makes GET illegal?????
                var jsonBody = new Gson().toJson(myRequest);
                outputStream.write(jsonBody.getBytes());
            }

            // Make the request
            System.out.println("Actual request method was: " + http.getRequestMethod());
            http.connect();
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader inputStreamReader = new InputStreamReader(respBody);
                //System.out.println(new Gson().fromJson(inputStreamReader, Map.class));
                return (new Gson().fromJson(inputStreamReader, myclass));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            //System.out.println(e.getMessage());
            //do nothing (for now)
        }

        return null;
    }
}