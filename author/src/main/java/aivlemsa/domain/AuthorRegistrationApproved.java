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

    public AuthorRegistrationApproved(Author aggregate) {
        super(aggregate);
        this.id = aggregate.getUserId();
    }

    public AuthorRegistrationApproved() {
        super();
    }
}
//>>> DDD / Domain Event
