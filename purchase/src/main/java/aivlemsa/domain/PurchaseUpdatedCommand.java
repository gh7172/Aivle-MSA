package aivlemsa.domain;

import lombok.Data;
import java.io.Serializable;

@Data
public class PurchaseUpdatedCommand implements Serializable {

    private String id; // 구매 ID
    private Long userId; // 사용자 ID
    private String bookId; // 도서 ID
    private String status; // 상태값 (예: COMPLETED, FAILED)

}
