import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.File;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import java.nio.file.Files;

public class CDN {

    public static void main(String[] args) throws IOException{
        CDN sas = new CDN();
        sas.callCDNApi();
    }

    public void callCDNApi() {

        String url = "https://api-gw.cloud.toast.com/tc-cdn/v1.5/appKeys/QwDR4P641WbVSeFB/distributions"; // {appKey} 변경 필요

        Map<String, Object> receiverMap = new HashMap<String, Object>();  //받는 사람
        receiverMap.put("receiveMailAddr", "hankyul.lee@nhnent.com"); // {receiveMailAddr} 변경 필요
        receiverMap.put("receiveType", "MRT0");   //수신자 타입 (MRT0 : 받는사람 , MRT1 : 참조, MRT2 : 숨은참조)
        List<Map<String, Object>> receiverList = new ArrayList<Map<String, Object>>();
        receiverList.add(receiverMap);

        Map<String, Object> receiver = new HashMap<String, Object>();  //받는 사람
        receiver.put("receiveMailAddr", "hankyul.lee@nhnent.com"); // {receiveMailAddr} 변경 필요



        Map<String, Object> data = new HashMap<String, Object>();         //보내는 사람
        data.put("senderAddress", "hankyul.lee@nhnent.com"); // {senderAddress} 변경 필요
        data.put("receiverList", receiverList);
        data.put("title", "title");
        data.put("body", "body");


        Gson gson = new Gson();
        String json = gson.toJson(data);
        System.out.println("json : " + json);
        String result = this.getResultString(json, url);
        System.out.println("result : " + result);
    }

    //List<Integer> fileList=new ArrayList<Integer>();
    //fileList.add(200);
    //data.put("attachFileIdList",fileList);


    //Map<String, Object> templateParameter = new HashMap<String, Object>();
    //templateParameter.put("title_name","클라우드고객1");
    //templateParameter.put("body_content", "test1");

    //data.put("templateId","id");//템플릿 적용
    //data.put("templateParameter",templateParameter);
    //List<String> tagList = new ArrayList<String>();
    //tagList.add("tag1");
    //tagList.add("AND");
    //tagList.add("tag2");
    //data.put("tagExpression",tagList); //태그용
    //data.put("receiverList", receiverList);
    //data.put("receiver",receiverMap); // auth-mail 용



    public void fileUploadApi() throws IOException {
        System.out.println("EmailApiSample Test");
        String url = "https://api-mail.cloud.toast.com/email/v1.4/appKeys/{APPKEY}/attachfile/binaryUpload";
        Map<String, Object> data = new HashMap<String, Object>();
        byte[] array = Files.readAllBytes(new File("C:\\Users\\NHNEnt\\IdeaProjects\\test1\\java1\\src\\123.jpg").toPath());
        data.put("fileName","123.jpg");
        data.put("createUser","hankyul.lee@nhnent.com");
        data.put("fileBody",array);
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