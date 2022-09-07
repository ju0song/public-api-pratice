package pratice.publicapi.Service;

import lombok.AllArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import pratice.publicapi.entity.Api;
import pratice.publicapi.repository.ApiRepository;

@Service
@AllArgsConstructor
public class ApiService {

    private final ApiRepository apiRepository;

    public void parsing(StringBuilder result) {
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
                String product = Object.get("PRDUCT").toString();
                String distb_pd = Object.get("DISTB_PD").toString();

                Api api = Api.builder()
                        .DISTB_PD(distb_pd)
                        .PRDUCT(product)
                        .build();
                apiRepository.save(api);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
