package aivlemsa.domain;

import aivlemsa.domain.*;
import aivlemsa.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class AuthorRegistrationRequested extends AbstractEvent {

    private Long id;

    public AuthorRegistrationRequested(Author aggregate) {
        super(aggregate);
    }

    public AuthorRegistrationRequested() {
        super();
    }
}
//>>> DDD / Domain Event
