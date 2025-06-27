package aivlemsa.domain;

import aivlemsa.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class PurchasePaymentSucceeded extends AbstractEvent {

    private Long userId;
    private Integer points;
}
