package aivlemsa.domain;

import aivlemsa.domain.*;
import aivlemsa.infra.AbstractEvent;

import java.time.LocalDate;
import java.util.*;
import lombok.*;

@Data
@ToString
public class GenerationSucceeded extends AbstractEvent {

    private Long bookId;
    private String summary;
    private String state;
    private Long userId;
    private String title;
    private LocalDate publishDate;
    private String text;
    private String coverImage;
}
