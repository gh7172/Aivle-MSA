package aivlemsa.domain;

import aivlemsa.domain.*;
import aivlemsa.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class PurchasePaymentFailed extends AbstractEvent {

    private Long userId;
    private Integer points;
    private String state;
}
