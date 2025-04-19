package hei.vaninah.siege.service.httpServlet;

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
    public static final String API_KEY_PREFIX = "apiKey";

    public <T> T[] doGetList(String url, Map<String, String> headers, Class<T[]> responseType) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", headers.get(API_KEY_PREFIX));
        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);

        ResponseEntity<T[]> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            responseType
        );

        return response.getBody();
    }
}
