package aivlemsa.domain;

import aivlemsa.domain.*;
import aivlemsa.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class BookPublicationRequested extends AbstractEvent {

    private Long bookId;
    private Long userId;
    private String title;
    private Date publishDate;
    private String summary;
    private String state;

    public BookPublicationRequested(집필 aggregate) {
        super(aggregate);
    }

    public BookPublicationRequested() {
        super();
    }
}
//>>> DDD / Domain Event
