package aivlemsa.domain;

import aivlemsa.infra.AbstractEvent;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@Data
@ToString
public class GenerationSucceeded extends AbstractEvent {

    private Long bookId;
    private Long userId;
    private String title;
    private LocalDate publishDate;
    private String text;
    private String summary;
    private String coverImage;
    private String state;
}