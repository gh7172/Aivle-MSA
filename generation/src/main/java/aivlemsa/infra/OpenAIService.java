package aivlemsa.infra;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// OpenAI API 호출 

@Service
public class OpenAIService {

    private final WebClient webClient;
    private final WebClient imageClient;

    // application.yml에 설정할 API 키
    @Value("${openai.api.key}")
    private String apiKey;

    public OpenAIService(WebClient.Builder webClientBuilder) {
        // Base URL 설정
        this.webClient = webClientBuilder
            .baseUrl("https://api.openai.com/v1")
            .build();

        // 표지 이미지 생성용 WebClient
        this.imageClient = webClientBuilder
            .baseUrl("https://api.openai.com/v1/images")
            .build();
    }

    public String generateSummary(String originalText) {
        var messages = List.of(
            new Message("system", "You are a helpful assistant that summarizes the given book content concisely in 3 to 4 sentences in Korean"),
            new Message("user", originalText)
        );

        var req = Mono.just(new ChatRequest(
            "gpt-4o-mini",
            messages,
            300
        ));

        ChatResponse resp = webClient.post()
            .uri("/chat/completions")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
            .body(req, TextRequest.class)
            .retrieve()
            .bodyToMono(TextResponse.class)
            .block();

        if (resp == null || resp.getChoices() == null || resp.getChoices().isEmpty()) {
            throw new IllegalStateException("요약 생성 응답이 유효하지 않습니다.");
        }
        return resp.getChoices().get(0).getMessage().getContent().trim();
    }


    public String generateCover(String title, String summary) {
        var req = Mono.just(new ImageRequest(
            "A book cover design based on the following information. Title: " + title + "\nSummary : " + summary,
            1,
            "512x512"
        ));

        ImageResponse resp = imageClient.post()
            .uri("/generations")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
            .body(req, ImageRequest.class)
            .retrieve()
            .bodyToMono(ImageResponse.class)
            .block();

        if (resp == null || resp.getData() == null || resp.getData().length == 0) {
            throw new IllegalStateException("표지 생성 응답이 유효하지 않습니다.");
        }
        return resp.getData()[0].getUrl();
    }


    // --- DTO for text completions ---
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class TextRequest {
        private String model;
        private String prompt;
        private int    max_tokens;
    }

    private static class TextResponse {
        private Choice[] choices;
        public Choice[] getChoices() { return choices; }
        public void setChoices(Choice[] choices) { this.choices = choices; }
        public static class Choice {
            private String text;
            public String getText() { return text; }
            public void setText(String text) { this.text = text; }
        }
    }

    // --- DTO for image generations ---
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class ImageRequest {
        private String prompt;
        private int    n;
        private String size;
    }


    private static class ImageResponse {
        private Data[] data;
        public Data[] getData() { return data; }
        public void setData(Data[] data) { this.data = data; }
        public static class Data {
            private String url;
            public String getUrl() { return url; }
            public void setUrl(String url) { this.url = url; }
        }
    }
}
