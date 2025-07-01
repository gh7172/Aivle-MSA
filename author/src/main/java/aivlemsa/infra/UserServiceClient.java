package aivlemsa.infra;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserServiceClient {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String userServiceUrl = "http://localhost:8086/users/";

    public void setAuthorTrue(Long userId) {
        String url = userServiceUrl + userId + "/author";
        restTemplate.patchForObject(url, null, Void.class);
    }
}
