package aivlemsa.domain;

import java.util.Date;
import lombok.Data;

@Data
public class GetSubscribeQuery {

    private Long userId;
    private Date subscriptionExpiryDate;
}
