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

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    SubscribeRepository subscribeRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='SubscriptionPaymentSucceeded'"
    )
    public void wheneverSubscriptionPaymentSucceeded_UpdateState(
        @Payload SubscriptionPaymentSucceeded subscriptionPaymentSucceeded
    ) {
        SubscriptionPaymentSucceeded event = subscriptionPaymentSucceeded;
        System.out.println(
            "\n\n##### listener UpdateState : " +
            subscriptionPaymentSucceeded +
            "\n\n"
        );

        // Sample Logic //
        Subscribe.updateState(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='SubscriptionPaymentFailed'"
    )
    public void wheneverSubscriptionPaymentFailed_UpdateState(
        @Payload SubscriptionPaymentFailed subscriptionPaymentFailed
    ) {
        SubscriptionPaymentFailed event = subscriptionPaymentFailed;
        System.out.println(
            "\n\n##### listener UpdateState : " +
            subscriptionPaymentFailed +
            "\n\n"
        );

        // Sample Logic //
        Subscribe.updateState(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
