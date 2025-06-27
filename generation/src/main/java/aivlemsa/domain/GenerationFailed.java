package aivlemsa.domain;

import aivlemsa.domain.*;
import aivlemsa.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class GenerationFailed extends AbstractEvent {

    private Long bookId;
    private String summary;
    private String imageUrl;
    private String state;

    public GenerationFailed(집필요청 aggregate) {
        super(aggregate);
    }

    public GenerationFailed() {
        super();
    }
}
//>>> DDD / Domain Event
