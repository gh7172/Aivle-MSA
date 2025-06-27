package aivlemsa.domain;

import aivlemsa.domain.*;
import aivlemsa.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class ChargedPoints extends AbstractEvent {

    private Long userId;
    private Integer points;

    public ChargedPoints(Point aggregate) {
        super(aggregate);
    }

    public ChargedPoints() {
        super();
    }
}
//>>> DDD / Domain Event
