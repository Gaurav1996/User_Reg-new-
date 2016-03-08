package gauravagrawal.com.user_registration;

import java.net.HttpURLConnection;
import java.net.URL;

public class FalseAlarmRequest {

    public String createGetRequest(String requestURL) {

        String response = "";
        try {
            URL url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();
            if (responseCode == 204)
                response = "False alarm registered";
            else
                response = "Error Registering";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
