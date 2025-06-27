package aivlemsa.domain;

import aivlemsa.domain.*;
import aivlemsa.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class OnUserLoggedIn extends AbstractEvent {

    private Long userId;
    private Boolean isAuthor;

    public OnUserLoggedIn(유저 aggregate) {
        super(aggregate);
    }

    public OnUserLoggedIn() {
        super();
    }
}
//>>> DDD / Domain Event
