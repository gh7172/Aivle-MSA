package aivlemsa.domain;

import aivlemsa.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PurchaseUpdatePolicy {

    @Autowired
    PayRepository payRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverBookPurchaseRequested_UpdateState(@Payload BookPurchaseRequested event) {
        if (event == null || event.getId() == null)
            return;

        // PurchaseUpdatedCommand에 맞춰 상태 업데이트
        PurchaseUpdatedCommand command = new PurchaseUpdatedCommand();
        command.setId(event.getId());
        command.setUserId(event.getUserId());
        command.setBookId(event.getBookId());
        // command.setStatus("REQUESTED"); // 상태 필드가 있다면 예시로 추가

        GetPay.updateState(command);
    }
}
