package aivlemsa.domain;

import java.util.Date;
import lombok.Data;

@Data
public class GetPayQuery {

    private String id;
    private Long userId;
    private String bookId;
}
