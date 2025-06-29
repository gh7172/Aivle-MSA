package aivlemsa.domain;

import aivlemsa.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class GenerationSucceeded extends AbstractEvent {

    private Long bookId;
    private String summary;
    private String imageUrl;
    private String state;

    public GenerationSucceeded(Generate aggregate) {
        super(aggregate);
    }

    public GenerationSucceeded() {
        super();
    }
}
//>>> DDD / Domain Event
