package aivlemsa.domain;

import aivlemsa.domain.*;
import aivlemsa.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class FailUserLoggedIn extends AbstractEvent {

    private Long id;

    public FailUserLoggedIn(User aggregate) {
        super(aggregate);
    }

    public FailUserLoggedIn() {
        super();
    }
}
//>>> DDD / Domain Event
