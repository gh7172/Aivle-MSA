package aivlemsa.infra;

import aivlemsa.config.kafka.KafkaProcessor;
import aivlemsa.domain.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    Repository Repository;

    @Autowired
    UserLibraryRepository userLibraryRepository;

    @Autowired
    SubscriptionModelRepository subscriptionModelRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(value = KafkaProcessor.INPUT, condition = "headers['type']=='PurchasePaymentSucceeded'")
    public void wheneverPurchasePaymentSucceeded_UpdateLibrary(@Payload PurchasePaymentSucceeded event) {
        if (event == null || event.getUserId() == null || event.getBookId() == null) return;
        UserLibrary.updateLibrary(event);
    }

    @StreamListener(value = KafkaProcessor.INPUT, condition = "headers['type']=='UserSubUpdated'")
    public void wheneverUserSubUpdated_UpdateUserSubscription(@Payload UserSubUpdated event) {
        if (event == null || event.getUserId() == null || event.getSubscriptionExpiryDate() == null) return;
        SubscriptionModel.updateUserSubscription(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
