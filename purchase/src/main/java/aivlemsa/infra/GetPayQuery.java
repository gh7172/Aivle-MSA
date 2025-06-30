package aivlemsa.infra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPayQuery {
    private String id;
    private Long userId;
    private String bookId;
    private String state;
}
