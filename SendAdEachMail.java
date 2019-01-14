import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;

public class SendAdEachMail {
    public static void main(String[] args) {
        SendAdEachMail sas = new SendAdEachMail();
        sas.callSendAdEachEmailApi();
    }

    public void callSendAdEachEmailApi() {
        String url = "https://api-mail.cloud.toast.com/email/v1.4/appKeys/{APPKEY}/sender/ad-eachMail"; // {appKey} 변경 필요

        Map<String, Object> receiverMap = new HashMap<String, Object>();  //받는 사람
        receiverMap.put("receiveMailAddr", "hankyul.lee@nhnent.com"); // {receiveMailAddr} 변경 필요
        receiverMap.put("receiveType", "MRT0");   //수신자 타입 (MRT0 : 받는사람 , MRT1 : 참조, MRT2 : 숨은참조)
        List<Map<String, Object>> receiverList = new ArrayList<Map<String, Object>>();
        receiverList.add(receiverMap);

        Map<String, Object> data = new HashMap<String, Object>();         //보내는 사람
        data.put("senderAddress", "hankyul.lee@nhnent.com"); // {senderAddress} 변경 필요
        data.put("receiverList", receiverList);
        data.put("title", "(광고)title");
        data.put("body", "body");

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
            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);
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

