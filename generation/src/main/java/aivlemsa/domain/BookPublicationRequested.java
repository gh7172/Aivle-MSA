package aivlemsa.domain;

import aivlemsa.infra.AbstractEvent;

import java.time.LocalDate;
import java.util.*;
import lombok.*;

import javax.persistence.*;

@Data
@ToString
public class BookPublicationRequested extends AbstractEvent {

    private Long bookId;
    private Long userId;
    private String title;
    private LocalDate publishDate;
    private String text;
    private String state;

}
