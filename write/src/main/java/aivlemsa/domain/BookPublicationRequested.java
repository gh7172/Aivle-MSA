package aivlemsa.domain;

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
    private LocalDate publishDate;
    private String text;
    private String state;

    public BookPublicationRequested(Write aggregate) {
        super(aggregate);
    }

    public BookPublicationRequested() {
        super();
    }
}
//>>> DDD / Domain Event
