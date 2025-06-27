package aivlemsa.domain;

import aivlemsa.domain.*;
import aivlemsa.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class AuthorRegistrationRejected extends AbstractEvent {

    private Long id;

    public AuthorRegistrationRejected(작가 aggregate) {
        super(aggregate);
    }

    public AuthorRegistrationRejected() {
        super();
    }
}
//>>> DDD / Domain Event
