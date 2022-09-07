package pratice.publicapi.Controller;


import lombok.AllArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pratice.publicapi.entity.Api;
import pratice.publicapi.repository.ApiRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@RestController
 @AllArgsConstructor
 public class ApiController {


     private final ApiRepository apiRepository;

     @GetMapping("/api")
     public String load_save() throws IOException,NullPointerException {
         StringBuilder result = new StringBuilder();
         try {
             String urla = "http://apis.data.go.kr/1471000/HtfsInfoService2/getHtfsList?ServiceKey=AEwuEzexgJKaPYcUDyX8Z5ZLxbtExL6%2FnS5eaQp6%2Bq7sD%2BEIyFWTgMwUW1qkvL9ZTs30dx5H1xsZyOzFP9bNyA%3D%3D&numOfRows=99&pageNo=1&type=json";
             URL url = new URL(urla);
             HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
             urlConnection.setRequestMethod("GET");
             BufferedReader br;
             br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8));
             String returnLine;
             while ((returnLine = br.readLine()) != null) {
                 result.append(returnLine + "\n\r");
             }
             urlConnection.disconnect();

             JSONObject Object;
             //json 객체 생성
             JSONParser jsonParser = new JSONParser();
             //json 파싱 객체 생성
             JSONObject jsonObject = (JSONObject) jsonParser.parse(result.toString());

 //           //데이터 분해
             JSONObject parseResponse = (JSONObject) jsonObject.get("body");

             JSONArray array = (JSONArray) parseResponse.get("items");
             for (int i = 0; i < array.size(); i++) {
                 Object = (JSONObject) array.get(i);
                 Api api = Api.builder()
                         .ENTRPS(Object.get("ENTRPS").toString())
                         .PRDUCT(Object.get("PRDUCT").toString())
                         .build();
                 apiRepository.save(api);
             }
         } catch (Exception e) {
             e.printStackTrace();
         }
         return result.toString();
     }
 }