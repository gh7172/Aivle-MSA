package aivlemsa.domain;

import aivlemsa.infra.AbstractEvent;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "Purchase_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;
    private Long bookId;
    private String status;  // 구매 상태: REQUESTED, SUCCEEDED, FAILED 등
    private Integer price;
    // 생성자 호출 시 구매 요청 이벤트 발행
    @PostPersist
    public void onPostPersist() {
        BookPurchaseRequested event = new BookPurchaseRequested(this);
        event.publishAfterCommit();
    }

    // 구매 상태 업데이트 메서드
    public void updateStatus(String newStatus) {
        this.status = newStatus;
        // 여기서 상태 변경 이벤트를 추가할 수도 있음
        // 예: PurchaseStatusUpdated event = new PurchaseStatusUpdated(this);
        // event.publishAfterCommit();
    }
}


