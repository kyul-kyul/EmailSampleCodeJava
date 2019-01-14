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
import java.nio.file.Files;

public class DeleteBlockReceiver {

    public static void main(String[] args) {
        DeleteBlockReceiver sas = new DeleteBlockReceiver();
        sas.callDeleteBlockReceiverApi();
    }

    public void callDeleteBlockReceiverApi() {


        String url = "https://api-mail.cloud.toast.com/email/v1.4/appKeys/{APPKEY}/block-receivers"; // {appKey} 변경 필요
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("deleted", "true");

        Map<String, Object> blockReceiver = new HashMap<String, Object>();
        blockReceiver.put("mailAddress", "customer@nhnent.com");
        List<Map<String, Object>> blockReceiverList = new ArrayList<Map<String, Object>>();
        blockReceiverList.add(blockReceiver);
        data.put("blockReceiverList", blockReceiverList);


        Gson gson = new Gson();
        String json = gson.toJson(data);
        System.out.println("json : " + json);
        String result = this.getResultString(json, url);
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
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            OutputStreamWriter out;
            try {
                out = new OutputStreamWriter(connection.getOutputStream());
                out.write(jsonObject);
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

}