package aivlemsa.domain;

import aivlemsa.infra.AbstractEvent;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

//<<< DDD / Domain Event
@Data
@ToString
public class GenerationSucceeded extends AbstractEvent {

    private Long bookId;
    private Long userId;
    private String title;
    private LocalDate publishDate;
    private String text;
    private String summary;
    private String coverImage;
    private String state;

    public GenerationSucceeded(Generate aggregate) {
        super(aggregate);
    }

    public GenerationSucceeded() {
        super();
    }
}
//>>> DDD / Domain Event
