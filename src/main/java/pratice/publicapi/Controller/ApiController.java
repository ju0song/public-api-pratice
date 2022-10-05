package pratice.publicapi.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pratice.publicapi.Service.ApiService;
import pratice.publicapi.dto.ApiResponseDto;
import pratice.publicapi.repository.ApiRepository;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
public class ApiController {


    private final ApiRepository apiRepository;
    private final ApiService apiService;

    //공공API 데이터 DB 저장
    @GetMapping("/api")
    public ResponseEntity<String> parse() throws IOException, NullPointerException {
        long start = System.currentTimeMillis();
        apiService.parsing();
        long end = System.currentTimeMillis(); // 끝나는 시점 측정
        System.out.println( "실행 시간 : " + ( end - start ) +"ms");
        return ResponseEntity.status(HttpStatus.OK)
                .body("공공 데이터가 담겼습니다");
    }

    @GetMapping("/save")
    public ResponseEntity<List<ApiResponseDto>> load_save(){
        List<ApiResponseDto> friendResponseDtoList = apiService.dto();
        return ResponseEntity.status(HttpStatus.OK)
                .body(friendResponseDtoList);
    }

}