package aivlemsa.domain;

import aivlemsa.domain.*;
import aivlemsa.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class SignedUp extends AbstractEvent {

    private Long id;
    private Boolean isKtCustomer;

    public SignedUp(User aggregate) {
        super(aggregate);
        this.isKtCustomer = aggregate.getIsKtCustomer();
    }

    public SignedUp() {
        super();
    }
}
//>>> DDD / Domain Event
