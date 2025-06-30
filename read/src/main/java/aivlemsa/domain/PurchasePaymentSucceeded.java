package aivlemsa.domain;

import aivlemsa.infra.AbstractEvent;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PurchasePaymentSucceeded extends AbstractEvent {

    private Long userId;
    private String bookId;
} 