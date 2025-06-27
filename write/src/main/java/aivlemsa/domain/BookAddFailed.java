package aivlemsa.domain;

import aivlemsa.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class BookAddFailed extends AbstractEvent {

    private Long bookId;
    private String userId;
    private String title;
    private String publishDate;
    private String plot;
    private String summary;
    private String coverImage;
    private String state;
}