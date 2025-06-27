package aivlemsa.domain;

import aivlemsa.PurchaseApplication;
import aivlemsa.external.Point;
import aivlemsa.external.PointService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Pay_table")
@Data
public class GetPay {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private Long userId;
    private String bookId;
    private String state;

    @PostPersist
    public void onPostPersist() {
        // 외부 API 직접 호출 → 좋은 구조는 아님 (예: 포인트 차감)
        Point point = new Point();
        // TODO: point에 값 매핑 필요
        PurchaseApplication.applicationContext
                .getBean(PointService.class)
                .usePurchasePoints(point);

        // 이벤트 발행
        Purchase bookPurchaseRequested = new Purchase(this);
        bookPurchaseRequested.publishAfterCommit();
    }

    public static PayRepository repository() {
        return PurchaseApplication.applicationContext.getBean(PayRepository.class);
    }

    // 결제 성공 이벤트 처리
    public static void updateState(PurchaseUpdatedCommand command) {
        repository().findById(command.getId()).ifPresent(pay -> {
            pay.setState("결제 성공");
            repository().save(pay);
        });
    }

    // 결제 실패 이벤트 처리
    public static void updateState(BookPurchaseCommand command) {
        repository().findById(command.getBookId()).ifPresent(pay -> {
            pay.setState("결제 실패");
            repository().save(pay);
        });
    }
}
