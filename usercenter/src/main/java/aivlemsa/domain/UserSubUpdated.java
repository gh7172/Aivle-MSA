package aivlemsa.domain;

import aivlemsa.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class UserSubUpdated extends AbstractEvent {

    private Long userId;
    private Date subscriptionExpiryDate;
}
