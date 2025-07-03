package aivlemsa.domain;

import aivlemsa.domain.*;
import aivlemsa.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class SignedUp extends AbstractEvent {

    private Long id;
    private Boolean isKtCustomer;
}
