package aivlemsa.domain;

import aivlemsa.domain.*;
import aivlemsa.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class BookAdded extends AbstractEvent {

    private Long bookId;
    private String userId;
    private String title;
    private String publishDate;
    private String plot;
    private String summary;
    private String coverImage;
    private String state;

    public BookAdded(도서 aggregate) {
        super(aggregate);
    }

    public BookAdded() {
        super();
    }
}
//>>> DDD / Domain Event
