package aivlemsa.domain;

import aivlemsa.domain.*;
import aivlemsa.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class BookPurchaseRequested extends AbstractEvent {

    private String id;
    private Long userId;
    private String bookId;
    private String state;

    public BookPurchaseRequested(Pay aggregate) {
        super(aggregate);
    }

    public BookPurchaseRequested() {
        super();
    }
}
//>>> DDD / Domain Event
