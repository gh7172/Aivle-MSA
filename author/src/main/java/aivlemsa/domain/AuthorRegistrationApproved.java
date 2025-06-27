package aivlemsa.domain;

import aivlemsa.domain.*;
import aivlemsa.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class AuthorRegistrationApproved extends AbstractEvent {

    private Long id;

    public AuthorRegistrationApproved(작가 aggregate) {
        super(aggregate);
    }

    public AuthorRegistrationApproved() {
        super();
    }
}
//>>> DDD / Domain Event
