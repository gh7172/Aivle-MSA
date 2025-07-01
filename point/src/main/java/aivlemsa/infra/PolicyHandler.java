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
    PointRepository pointRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='SignedUp'"
    )
    public void wheneverSignedUp_HandleSignedUp(@Payload SignedUp signedUp) {
        SignedUp event = signedUp;
        System.out.println(
            "\n\n##### listener HandleSignedUp : " + signedUp + "\n\n"
        );
        // Sample Logic //
    
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PurchaseRequested'"
    )
    public void wheneverPurchaseRequested_ChargePoint(@Payload BookPurchaseRequested event) {
        log.info("💸 포인트 차감 요청 수신: {}", event);

        pointRepository.findById(event.getUserId()).ifPresent(point -> {
            point.deductPoints(event.getPrice());
            pointRepository.save(point); // 상태 저장
        });
    }

    }


}
//>>> Clean Arch / Inbound Adaptor
