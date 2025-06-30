package aivlemsa.infra;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
        var req = Mono.just(new TextRequest(
                "gpt-4o-mini",
                "다음 도서의 내용을 3~4줄 사이의 문장으로 요약해줘:\n" + originalText,
                300
        ));

        TextResponse resp = webClient.post()
                .uri("/completions")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .body(req, TextRequest.class)
                .retrieve()
                .bodyToMono(TextResponse.class)
                .block();

        if (resp == null || resp.getChoices() == null || resp.getChoices().length == 0) {
            throw new IllegalStateException("요약 생성 응답이 유효하지 않습니다.");
        }
        return resp.getChoices()[0].getText().trim();
    }


    public String generateCover(String title, String summary) {
        var req = Mono.just(new ImageRequest(
                "책 제목: " + title + "\n요약: " + summary + "\n이 내용을 바탕으로 책 표지 디자인",
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