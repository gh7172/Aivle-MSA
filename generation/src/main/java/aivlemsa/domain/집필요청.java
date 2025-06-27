package aivlemsa.domain;

import aivlemsa.GenerationApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "집필요청_table")
@Data
//<<< DDD / Aggregate Root
public class 집필요청 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookId;

    private String summary;

    private String imageUrl;

    public static 집필요청Repository repository() {
        집필요청Repository 집필요청Repository = GenerationApplication.applicationContext.getBean(
            집필요청Repository.class
        );
        return 집필요청Repository;
    }

    //<<< Clean Arch / Port Method
    public void requestBookPublication(
        RequestBookPublicationCommand requestBookPublicationCommand
    ) {
        //implement business logic here:

        GenerationSucceeded generationSucceeded = new GenerationSucceeded(this);
        generationSucceeded.publishAfterCommit();
        GenerationFailed generationFailed = new GenerationFailed(this);
        generationFailed.publishAfterCommit();
    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
