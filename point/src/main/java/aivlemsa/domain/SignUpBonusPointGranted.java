package aivlemsa.domain;

import aivlemsa.domain.*;
import aivlemsa.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class SignUpBonusPointGranted extends AbstractEvent {

    private Long id;

    public SignUpBonusPointGranted(Point aggregate) {
        super(aggregate);
    }

    public SignUpBonusPointGranted() {
        super();
    }
}
//>>> DDD / Domain Event
