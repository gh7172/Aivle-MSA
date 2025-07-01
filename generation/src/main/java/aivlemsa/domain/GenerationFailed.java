package aivlemsa.domain;

import aivlemsa.infra.AbstractEvent;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

//<<< DDD / Domain Event
@Data
@ToString
public class GenerationFailed extends AbstractEvent {

    private Long bookId;
    private Long userId;
    private String title;
    private LocalDate publishDate;
    private String text;
    private String summary;
    private String coverImage;
    private String state;

    public GenerationFailed(Generate aggregate) {
        super(aggregate);
    }

    public GenerationFailed() {
        super();
    }
}
//>>> DDD / Domain Event
