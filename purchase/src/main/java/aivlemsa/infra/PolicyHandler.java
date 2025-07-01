package aivlemsa.infra;

import aivlemsa.config.kafka.KafkaProcessor;
import aivlemsa.domain.BookPurchaseRequested;
import aivlemsa.domain.GetPayRepository;
import aivlemsa.external.PaymentSucceeded;
import aivlemsa.external.Paymentfailed;
import aivlemsa.domain.PointDeductionRequested;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PolicyHandler {

    private final GetPayRepository getPayRepository;

    /**
     * 결제 성공 이벤트 수신 시 상태 변경
     */
    @StreamListener(KafkaProcessor.INPUT)
    public void onPaymentSucceeded(@Payload PaymentSucceeded event) {
        if (!event.validate()) return;

        log.info("✅ 결제 성공 이벤트 수신: {}", event);

        getPayRepository.findById(event.getPurchaseId()).ifPresent(purchase -> {
            purchase.setStatus("PAYMENT_SUCCEEDED");
            getPayRepository.save(purchase);
        });
    }

    /**
     * 결제 실패 이벤트 수신 시 상태 변경
     */
    @StreamListener(KafkaProcessor.INPUT)
    public void onPaymentFailed(@Payload Paymentfailed event) {
        if (!event.validate()) return;

        log.info("❌ 결제 실패 이벤트 수신: {}", event);

        getPayRepository.findById(event.getPurchaseId()).ifPresent(purchase -> {
            purchase.setStatus("PAYMENT_FAILED");
            getPayRepository.save(purchase);
        });
    }

    /**
     * 구매 요청 수신 → 포인트 차감 이벤트 발행
     */
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PurchaseRequested'"
    )
    public void emitPointDeduction(@Payload BookPurchaseRequested event) {
        log.info("📦 구매 요청 이벤트 수신 → 포인트 차감 요청 발행: {}", event);

        PointDeductionRequested deductionEvent = new PointDeductionRequested();
        deductionEvent.setUserId(event.getUserId());
        deductionEvent.setAmount(event.getPrice());
        deductionEvent.setPurchaseId(event.getId());
        deductionEvent.publishAfterCommit();
    }
}
