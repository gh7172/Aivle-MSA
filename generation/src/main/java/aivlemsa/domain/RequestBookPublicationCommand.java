package aivlemsa.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class RequestBookPublicationCommand {

    private Long bookId;
    private String summary;
    private String imageUrl;
}
