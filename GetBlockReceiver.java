import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;

public class GetBlockReceiver {
    public static void main(String[] args) throws IOException {
        GetBlockReceiver sas =new GetBlockReceiver();
        sas.callGetBlockReceiversApi();
    }
    public void callGetBlockReceiversApi() throws IOException {
        System.out.println("GetMail Test");
        String url = "https://api-mail.cloud.toast.com/email/v1.4/appKeys/{APPKEY}/block-receivers"; // {appKey} 변경 필요
        String query = "?mailAddress=hankyul.lee@nhnent.com";
        String result = this.getResultString(query, url);
        System.out.println("result : " + toPrettyFormat(result));
    }
    private String getResultString(String query, String urlString) {
        StringBuilder builder = new StringBuilder();
        try {
            URL url = new URL(urlString+query);
            System.out.println("url : " + url.toString());
            URLConnection connection = url.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection)connection;
            httpConnection.setRequestMethod("GET");
            int responseCode=httpConnection.getResponseCode();
            System.out.println("Response Code : " + responseCode);
            httpConnection.setConnectTimeout(5000);
            httpConnection.setReadTimeout(5000);

            String line = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            while ((line = in.readLine()) != null) {
                builder.append(line);
            }
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
