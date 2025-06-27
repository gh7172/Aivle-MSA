package aivlemsa.domain;

import aivlemsa.infra.AbstractEvent;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GenerationSucceeded extends AbstractEvent {

    private Long bookId;
    private String summary;
    private String imageUrl;
    private String state;
}