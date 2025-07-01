package aivlemsa.domain;

import aivlemsa.GenerationApplication;

import javax.persistence.*;

import aivlemsa.infra.OpenAIService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
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

    private Long userId;

    private String title;

    private LocalDate publishDate;

    @Lob
    private String text;

    @Column(length = 2000)
    private String summary;

    @Column(length = 1000)
    private String coverImage;

    private String state;


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
            String aiSummary = openAIService().generateSummary(requestBookPublicationCommand.getText());

            // 도서 표지 생성
            String coverUrl = openAIService().generateCover(requestBookPublicationCommand.getTitle(), requestBookPublicationCommand.getText());


            // GenerationSucceeded 이벤트 생성 및 커밋 이후 발행
            GenerationSucceeded generationSucceeded = new GenerationSucceeded();
            generationSucceeded.setBookId(requestBookPublicationCommand.getBookId());
            generationSucceeded.setSummary(aiSummary);
            generationSucceeded.setCoverImage(coverUrl);
            generationSucceeded.setText(requestBookPublicationCommand.getText());
            generationSucceeded.setUserId(requestBookPublicationCommand.getUserId());
            generationSucceeded.setPublishDate(requestBookPublicationCommand.getPublishDate());
            generationSucceeded.setTitle(requestBookPublicationCommand.getTitle());
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
            generationFailed.setSummary(null);
            generationFailed.setCoverImage(null);
            generationFailed.setText(requestBookPublicationCommand.getText());
            generationFailed.setUserId(requestBookPublicationCommand.getUserId());
            generationFailed.setPublishDate(requestBookPublicationCommand.getPublishDate());
            generationFailed.setTitle(requestBookPublicationCommand.getTitle());
            generationFailed.publishAfterCommit();

            log.error("GenerationFailed scheduled for publishAfterCommit for bookId={}: {}",
                    requestBookPublicationCommand.getBookId(), error, e);
        }
    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
