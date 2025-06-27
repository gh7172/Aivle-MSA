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
    PayRepository payRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {
    }

    @StreamListener(value = KafkaProcessor.INPUT, condition = "headers['type']=='PurchasePaymentSucceeded'")
    public void wheneverPurchasePaymentSucceeded_UpdateState(
            @Payload PurchaseUpdatedCommand purchasePaymentSucceeded) {
        PurchaseUpdatedCommand event = purchasePaymentSucceeded;
        System.out.println(
                "\n\n##### listener UpdateState : " +
                        purchasePaymentSucceeded +
                        "\n\n");

        // Sample Logic //
        GetPay.updateState(event);
    }

    @StreamListener(value = KafkaProcessor.INPUT, condition = "headers['type']=='PurchasePaymentFailed'")
    public void wheneverPurchasePaymentFailed_UpdateState(
            @Payload BookPurchaseCommand purchasePaymentFailed) {
        BookPurchaseCommand event = purchasePaymentFailed;
        System.out.println(
                "\n\n##### listener UpdateState : " + purchasePaymentFailed + "\n\n");

        // Sample Logic //
        GetPay.updateState(event);
    }
}
// >>> Clean Arch / Inbound Adaptor
