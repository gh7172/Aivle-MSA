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
    private String status;      // Requested, Succeeded, Failed

    private String title;
    private String result;
    private String errorMessage;

    @Column(length = 50000)
    private String imageUrl;

    protected Generate() {}


    //<<< Clean Arch / Port Method
    public Generate(Long bookId, String summary, String title) {
        this.bookId = bookId;
        this.summary = summary;
        this.title = title;
        this.status = "REQUESTED";
    }

    public void markSucceeded(String generatedText) {
        this.status = "SUCCEEDED";
        this.result = generatedText;
    }

    public void markFailed(String error) {
        this.status = "FAILED";
        this.errorMessage = error;
    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
