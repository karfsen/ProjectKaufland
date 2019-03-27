package sk.itsovy.sk;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class Internet {
    HttpURLConnection connection = null;
    public static double executePost() throws IOException, ParseException {
        URL url = new URL("http://data.fixer.io/api/latest?access_key=9649b7a6f9e70c55a2d27d723265e5d6");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.close();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            JSONParser parser=new JSONParser();
            JSONObject json=(JSONObject) parser.parse(response.toString());
            JSONObject rates=(JSONObject) json.get("rates");
            return (double) rates.get("USD");
        }
    }
