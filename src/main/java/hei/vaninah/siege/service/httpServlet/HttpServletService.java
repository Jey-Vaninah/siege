package hei.vaninah.siege.service.httpServlet;

import hei.vaninah.siege.entity.BestSale;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service

public class HttpServletService {
    private final RestTemplate restTemplate = new RestTemplate();

    public BestSale[] doGet(String url, Map<String, String> headers){
        HttpHeaders htppHeaders = new HttpHeaders();
        htppHeaders.set("Authorization", "ApiKey " + headers.get("apiKey"));
        HttpEntity<Void> entity = new HttpEntity<>(htppHeaders);
        System.out.printf("Siege REST API URL: %s\n", url);
        ResponseEntity<BestSale[]> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                BestSale[].class
        );

        return response.getBody();
    }
}
