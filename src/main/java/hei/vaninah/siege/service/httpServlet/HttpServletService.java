package hei.vaninah.siege.service.httpServlet;

import hei.vaninah.siege.entity.BestSale;
import hei.vaninah.siege.entity.ProcessingTime;
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

    public <T> T[] doGet(String url, Map<String, String> headers, Class<T[]> responseType) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "ApiKey " + headers.get("apiKey"));
        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);

        ResponseEntity<T[]> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            responseType
        );

        return response.getBody();
    }

    public BestSale[] doGetBestSale(String url, Map<String, String> headers) {
        return doGet(url, headers, BestSale[].class);
    }

    public ProcessingTime[] doGetProcessingTime(String url, Map<String, String> headers) {
        return doGet(url, headers, ProcessingTime[].class);
    }
}
