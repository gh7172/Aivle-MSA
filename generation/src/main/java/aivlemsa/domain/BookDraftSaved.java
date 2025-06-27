package aivlemsa.domain;

import aivlemsa.domain.*;
import aivlemsa.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class BookDraftSaved extends AbstractEvent {

    private Long bookId;
    private Long userId;
    private String title;
    private Date publishDate;
    private String summary;

    public BookDraftSaved() {
        super();
    }
}
//>>> DDD / Domain Event
