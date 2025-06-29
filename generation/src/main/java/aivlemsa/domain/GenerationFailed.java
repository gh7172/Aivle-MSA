package aivlemsa.domain;

import aivlemsa.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class GenerationFailed extends AbstractEvent {

    private Long bookId;
    private String summary;
    private String imageUrl;
    private String state;

    public GenerationFailed(Generate aggregate) {
        super(aggregate);
    }

    public GenerationFailed() {
        super();
    }
}
//>>> DDD / Domain Event
