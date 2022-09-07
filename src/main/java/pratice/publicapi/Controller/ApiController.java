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
import java.util.ArrayList;

@RestController
@AllArgsConstructor
public class ApiController {


    private final ApiRepository apiRepository;

    @GetMapping("/api")
    public String load_save() throws IOException, RuntimeException {
        StringBuilder result = null;

        for (int pageNumber = 1; pageNumber <= 3; pageNumber++) {
            String urla = "http://apis.data.go.kr/1471000/HtfsInfoService2/getHtfsItem?"
                    + "ServiceKey=AEwuEzexgJKaPYcUDyX8Z5ZLxbaQyFWTgMwUW1qkvL9ZTs30dx5H1xsZyOzFP9bNyA%3D%3D"
                    + "&numOfRows=" + 99
                    + "&pageNo=" + pageNumber
                    + "&type=json";

            URL url = new URL(urla);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            BufferedReader br;
            br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            String returnLine;
            result = new StringBuilder();
            ArrayList<StringBuilder> urls = new ArrayList<>();
            while ((returnLine = br.readLine()) != null) {
                result.append(returnLine + "\n\r");
            }
            urls.add(result);
            urlConnection.disconnect();

            try {
                JSONObject Object;
                //json 객체 생성
                JSONParser jsonParser = new JSONParser();
                //json 파싱 객체 생성
                JSONObject jsonObject = (JSONObject) jsonParser.parse(result.toString());

                //데이터 분해
                JSONObject parseResponse = (JSONObject) jsonObject.get("body");
                JSONArray array = (JSONArray) parseResponse.get("items");
                for (int i = 0; i < array.size(); i++) {
                    Object = (JSONObject) array.get(i);

                    String entrps = (String) Object.get("ENTRPS");
                    if (entrps == null) {
                        entrps = "";
                    }

                    String product = (String) Object.get("PRDUCT");
                    if (product == null) {
                        product = "";
                    }

                    String srv_use = (String) Object.get("SRV_USE");
                    if (srv_use == null) {
                        srv_use = "";
                    }

                    Api api = Api.builder()
                            .PRDUCT(product)
                            .ENTRPS(entrps)
                            .SRV_USE(srv_use)
                            .build();
                    apiRepository.save(api);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }
}