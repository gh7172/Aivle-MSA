package aivlemsa.domain;

import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "GetPay_table")
@Data
public class GetPay {

    @Id
    private String id;  // Purchase ID 와 동일하게 사용

    private Long userId;
    private String bookId;
    private String status;

    public GetPay() {}

    public GetPay(String id, Long userId, String bookId, String status) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.status = status;
    }
}
