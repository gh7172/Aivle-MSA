package aivlemsa.external;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PaymentSucceeded {
    private String purchaseId; // ← 이게 꼭 필요함!
    private Long userId;
    private Integer points;

    public PaymentSucceeded() {
        // Kafka 역직렬화용 기본 생성자
    }

    public boolean validate() {
        return purchaseId != null && !purchaseId.isEmpty();
    }

    public String getPurchaseId() {
        return this.purchaseId;
    }
}
