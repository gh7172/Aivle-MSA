package aivlemsa.domain;

import aivlemsa.domain.*;
import aivlemsa.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class GenerationSucceeded extends AbstractEvent {

    private Long bookId;
    private String summary;
    private String imageUrl;
    private String state;

    public GenerationSucceeded(집필요청 aggregate) {
        super(aggregate);
    }

    public GenerationSucceeded() {
        super();
    }
}
//>>> DDD / Domain Event
