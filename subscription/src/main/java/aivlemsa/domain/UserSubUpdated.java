package aivlemsa.domain;

import aivlemsa.domain.*;
import aivlemsa.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class UserSubUpdated extends AbstractEvent {

    private Long userId;
    private Date subscriptionExpiryDate;

    public UserSubUpdated(Subscribe aggregate) {
        super(aggregate);
    }

    public UserSubUpdated() {
        super();
    }
}
//>>> DDD / Domain Event
