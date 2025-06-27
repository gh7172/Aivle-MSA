package aivlemsa.domain;

import aivlemsa.domain.*;
import aivlemsa.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class BookPublicationRequested extends AbstractEvent {

    private Long bookId;
    private Long userId;
    private String title;
    private Date publishDate;
    private String summary;
    private String state;
}
