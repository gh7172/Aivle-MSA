package aivlemsa.domain;

import aivlemsa.GenerationApplication;

import javax.persistence.*;

import aivlemsa.infra.OpenAIService;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Entity
@Table(name = "집필요청_table")
@Data
@Slf4j
//<<< DDD / Aggregate Root
public class Generate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookId;
    private String summary;
    private String status;      // Requested, Succeeded, Failed
    private String title;
    private String result;
    @Column(length = 50000)
    private String imageUrl;

    public static GenerateRepository repository() {
        GenerateRepository GenerateRepository = GenerationApplication.applicationContext.getBean(
            GenerateRepository.class
        );
        return GenerateRepository;
    }

    public static OpenAIService openAIService() {
        OpenAIService openAIService = GenerationApplication.applicationContext.getBean(
                OpenAIService.class
        );
        return openAIService;
    }
    //<<< Clean Arch / Port Method
    public static void requestBookPublication(
            RequestBookPublicationCommand requestBookPublicationCommand
    ) {
        //implement business logic here:
//        Generate generate = repository().findById(requestBookPublicationCommand.getBookId())
//                .orElseThrow(() -> new IllegalStateException("Generate not found: " + requestBookPublicationCommand.getBookId()));
        // AI 이미지 생성, 요약 확인 후 분기 Success, Failed
        try {
            // 도서 내용 요약
            String aiSummary = openAIService().generateSummary(requestBookPublicationCommand.getSummary());

            // 도서 표지 생성
            String coverUrl = openAIService().generateCover(requestBookPublicationCommand.getTitle(), requestBookPublicationCommand.getSummary());


            // GenerationSucceeded 이벤트 생성 및 커밋 이후 발행
            GenerationSucceeded generationSucceeded = new GenerationSucceeded();
            generationSucceeded.setBookId(requestBookPublicationCommand.getBookId());
            generationSucceeded.setSummary(aiSummary);
            generationSucceeded.setImageUrl(coverUrl);
            generationSucceeded.setState("GenerationSucceeded");
            generationSucceeded.publishAfterCommit();

            log.info("GenerationSucceeded scheduled for publishAfterCommit for bookId={} summaryLength={} coverUrl={}",
                    requestBookPublicationCommand.getBookId(), aiSummary.length(), coverUrl);

        } catch (Exception e) {
            String error = Optional.ofNullable(e.getMessage()).orElse("Unknown error");

            // GenerationFailed 이벤트 생성 및 커밋 이후 발행
            GenerationFailed generationFailed = new GenerationFailed();
            generationFailed.setBookId(requestBookPublicationCommand.getBookId());
            generationFailed.setState(error);
            generationFailed.publishAfterCommit();

            log.error("GenerationFailed scheduled for publishAfterCommit for bookId={}: {}",
                    requestBookPublicationCommand.getBookId(), error, e);
        }
    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
