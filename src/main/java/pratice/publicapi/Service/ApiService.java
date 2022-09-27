package pratice.publicapi.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import pratice.publicapi.dto.ApiResponseDto;
import pratice.publicapi.entity.Api;
import pratice.publicapi.repository.ApiRepository;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ApiService {

    private final ApiRepository apiRepository;

    //공공API 데이터 DB 저장
    public String parsing() throws IOException, NullPointerException {
        StringBuilder result = null;

        for (int pageNumber = 1; pageNumber <=354; pageNumber++) {
            String urla = "http://apis.data.go.kr/1471000/HtfsInfoService2/getHtfsItem?"
                    + "ServiceKey=AEwuEzexgJKaPYcUDyX8Z5ZLxbtExL6%2FnS5eaQp6%2Bq7sD%2BEIyFWTgMwUW1qkvL9ZTs30dx5H1xsZyOzFP9bNyA%3D%3D"
                    + "&numOfRows=" + 99
                    + "&pageNo=" + pageNumber
                    + "&type=json";

            URL url = new URL(urla);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(30000);
            urlConnection.setReadTimeout(80000);
            urlConnection.setRequestMethod("GET");
            BufferedReader br;
            br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            String returnLine;
            result = new StringBuilder();
            ArrayList<StringBuilder> urls = new ArrayList<>();
            while ((returnLine = br.readLine()) != null) {
                result.append(returnLine).append("\n\r");
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
                for (java.lang.Object o : array) {
                    Object = (JSONObject) o;

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
                    List<Api> apis = new ArrayList<>();
                    Api api = Api.builder()
                            .ENTRPS(entrps)
                            .PRODUCT(product)
                            .SRV_USE(srv_use)
                            .build();
                            apis.add(api);
                    apiRepository.saveAll(apis);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }

    public List<ApiResponseDto> dto() {


        List<Api> apiList = apiRepository.findAll();
        List<ApiResponseDto> responseDtos = new ArrayList<>();
        for (Api api : apiList) {
            ApiResponseDto apiResponsedto = new ApiResponseDto(api.getPRODUCT(),api.getENTRPS(),api.getSRV_USE());
            responseDtos.add(apiResponsedto);
        }
        return responseDtos;
    }
}

