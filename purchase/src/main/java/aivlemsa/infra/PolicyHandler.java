package aivlemsa.infra;

import aivlemsa.config.kafka.KafkaProcessor;
import aivlemsa.domain.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import aivlemsa.external.PaymentSucceeded;
import aivlemsa.external.Paymentfailed;


@Slf4j
@Service
@RequiredArgsConstructor
public class PolicyHandler {

    private final GetPayRepository getPayRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void onPaymentSucceeded(@Payload PaymentSucceeded event) {
        if (!event.validate()) return;

        log.info("결제 성공 이벤트 수신: {}", event);

        // Purchase 상태를 '성공'으로 업데이트
        getPayRepository.findById(event.getPurchaseId()).ifPresent(purchase -> {
            purchase.setStatus("PAYMENT_SUCCEEDED");
            getPayRepository.save(purchase);
        });
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void onPaymentfailed(@Payload Paymentfailed event) {
        if (!event.validate()) return;

        log.info(" 결제 실패 이벤트 수신: {}", event);

        // Purchase 상태를 '실패'로 업데이트
        getPayRepository.findById(event.getPurchaseId()).ifPresent(purchase -> {
            purchase.setStatus("PAYMENT_FAILED");
            getPayRepository.save(purchase);
        });
    }
}
