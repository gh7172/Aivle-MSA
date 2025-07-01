package aivlemsa.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
public class RequestBookPublicationCommand {

    private Long bookId;
    private Long userId;
    private String title;
    private String text;
    private LocalDate publishDate;
    private String state;

}
