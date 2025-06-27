package aivlemsa.domain;

import aivlemsa.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class GenerationFailed extends AbstractEvent {

    private Long bookId;
    private String summary;
    private String imageUrl;
    private String state;
}
