import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.File;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.nio.file.Files;

public class UploadImage {

    public static void main(String[] args) throws IOException{
        UploadImage sas = new UploadImage();
        sas.callUploadImageApi();
    }

    public void callUploadImageApi() throws IOException{
        String url = "https://api-image.cloud.toast.com/image/v2.0/appkeys/cMlp9PRFJd2UahDM/images?path=/1234.jpg&autorename=true"; // {appKey} {tagId} 변경 필요

        Map<String, Object> data = new HashMap<String, Object>();


        Gson gson = new Gson();
        String json = gson.toJson(data);
        System.out.println("json : " + json);
        String result = toPrettyFormat(this.getResultString(json, url));
        System.out.println("result : " + result);
    }



    private String getResultString(String jsonObject, String urlString) {
        StringBuilder builder = new StringBuilder();
        try {
            URL url = new URL(urlString);
            System.out.println("url : " + url.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Authorization", "7SV4Jyh9");
            connection.setRequestProperty("Content-Type", "application/octet-stream");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            DataOutputStream out;
            byte[] sample_array = Files.readAllBytes(new File("C:\\Users\\NHNEnt\\IdeaProjects\\test1\\java1\\src\\123.jpg").toPath());
            try {
                out = new DataOutputStream(connection.getOutputStream());
                out.write(sample_array);
                System.out.println(sample_array);
                out.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            String line = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = in.readLine()) != null) {
                builder.append(line);
            }

            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
    public static String toPrettyFormat(String jsonString)
    {
        JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = gson.toJson(json);
        return prettyJson;
    }

}