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
        System.out.println(
            "\n\n##### listener HandleSignedUp : " + signedUp + "\n\n"
        );

        // Sample Logic //
        Point point = new Point();
        point.setUserId(signedUp.getId());

        int totalPoints = 1000; // 기본 1000포인트
        if (signedUp.getIsKtCustomer() != null && signedUp.getIsKtCustomer()) {
            totalPoints += 4000; // KT 고객이면 4000포인트 추가
        }
        point.setPoints(totalPoints);

        pointRepository.save(point);
    }
}
//>>> Clean Arch / Inbound Adaptor
