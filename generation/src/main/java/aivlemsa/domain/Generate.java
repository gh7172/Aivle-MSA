package aivlemsa.domain;

import aivlemsa.GenerationApplication;

import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "집필요청_table")
@Data
//<<< DDD / Aggregate Root
public class Generate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookId;

    private String summary;

    private String imageUrl;

    public static GenerateRepository repository() {
        GenerateRepository GenerateRepository = GenerationApplication.applicationContext.getBean(
            GenerateRepository.class
        );
        return GenerateRepository;
    }

    //<<< Clean Arch / Port Method
    public static void requestBookPublication(
            RequestBookPublicationCommand requestBookPublicationCommand
    ) {
        //implement business logic here:
        // AI 이미지 생성, 요약 확인 후 분기 Success, Failed
        if (true){
            GenerationSucceeded generationSucceeded = new GenerationSucceeded();
            generationSucceeded.setBookId(requestBookPublicationCommand.getBookId());
            generationSucceeded.publishAfterCommit();
        }
        else {
            GenerationFailed generationFailed = new GenerationFailed();
            generationFailed.setBookId(requestBookPublicationCommand.getBookId());
            generationFailed.publishAfterCommit();
        }


    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
