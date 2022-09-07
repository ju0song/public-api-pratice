package pratice.publicapi.Controller;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pratice.publicapi.Service.ApiService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@RestController
 @AllArgsConstructor
 public class ApiController {
    private final ApiService apiService;

    @GetMapping("/api")
    public String load_save() throws IOException, NullPointerException {
        StringBuilder result = new StringBuilder();

            String urla = "http://apis.data.go.kr/1471000/HtfsInfoService2/getHtfsList?ServiceKey=AEwuEzexgJKaPL6%2FnS5eaQp6%2Bq7sD%2BEIyFWTgMwUW1qkvL9ZTs30dx5H1xsZyOzFP9bNyA%3D%3D&numOfRows=99&pageNo=1&type=json";
            URL url = new URL(urla);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            BufferedReader br;
            br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8));
            String returnLine;
            while ((returnLine = br.readLine()) != null) {
                result.append(returnLine).append("\n\r");
            }
            urlConnection.disconnect();
            apiService.parsing(result);
            return result.toString();
        }
    }




